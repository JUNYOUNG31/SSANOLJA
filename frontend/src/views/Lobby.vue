<template>
  <div class="lobby">
    <div class="myface">
      <img src="../assets/logo.png" alt="logo" />

      <div id="join" v-if="!session">
        <div id="join-dialog" class="vertical-center">
          <h1>LOBBY</h1>
          <div>
            <p>
              <label>닉네임</label>
              <input v-model="userData.userName" class="form-control" type="text" required placeholder="닉네임을 입력하세요"/>
            </p>
            <p>
              <label>방 참여코드</label>
              <input v-model="joinCode" class="form-control" type="text" required placeholder="참여코드를 입력하세요"/>
            </p>
            <p class="text-center">
              <button class="btn btn-lg btn-success" @click="checkNicknameCheckRoom(joinCode)">입장하기</button>
            </p>
            <p class="text-center">
              <button class="btn btn-lg btn-success" @click="checkNicknameMakeRoom()">방 만들기</button>
            </p>
          </div>
        </div>
      </div>
    </div>
    <preview ref="preview" :joinCode="joinCode" :myUserName="userData.userName">
    </preview>
  </div>
</template>

<script>
import axios from "axios";
import { mapState } from "vuex";
import Preview from "@/components/Preview.vue";

export default {
  name: "Lobby",

  data() {
    return {
      userData: {
        userName: null,
        userEmail: null,
      },
      joinCode: "",
      myUserName: "",
      sendUserEmail: "",
    };
  },
  components: {
    Preview,
  },
  computed: {
    ...mapState(["session", "publisher", "subscribers", "mainStreamManager"]),
  },
  methods: {
    checkNicknameCheckRoom() {
      console.log(this.userData.userName);
      axios({
        method: "post",
        url: "/api/users/update",
        data: {
          userEmail: this.userData.userEmail,
          updatedUserNickname: this.userData.userName,
        },
      })
        .then((res) => {
          console.log(res.statusText);
          this.checkRoom();
        })
        .catch((err) => {
          console.log(err);
          alert(
            "닉네임이 입력 되지 않았거나 중복 되었습니다.\n닉네임을 다시 입력해주세요."
          );
        });
    },
    checkRoom(joinCode) {
      axios({
        method: "get",
        url: `/api/rooms/${joinCode}`,
      })
        .then(() => {
          console.log(this.$refs.preview);
          localStorage.setItem("isRoomMaker", false);
          this.$refs.preview.dialog = true;

          // console.log(res.status)
        })
        .catch(() => {
          alert("일치하는 방이 존재하지 않습니다.");
        });
    },
    checkNicknameMakeRoom() {
      axios({
        method: "post",
        url: "/api/users/update",
        data: {
          userEmail: this.userData.userEmail,
          updatedUserNickname: this.userData.userName,
        },
      })
        .then((res) => {
          console.log(res.statusText);
          this.makeRoom();
        })
        .catch((err) => {
          console.log(err);
          alert(
            "닉네임이 입력 되지 않았거나 중복 되었습니다.\n닉네임을 다시 입력해주세요."
          );
        });
    },
    makeRoom() {
      axios({
        method: "post",
        url: "/api/rooms",
      })
        .then((res) => {
          this.joinCode = res.data;
          localStorage.setItem("isRoomMaker", true);
          this.$refs.preview.dialog = true;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    leaveSession() {
      this.$store.dispatch("leaveSession");
    },
    updateMainVideoStreamManager(data) {
      this.$store.dispatch("updateMainVideoStreamManager", data);
    },
    getUserData() {
      this.userData.userEmail = this.$route.params.userEmail;
      this.userData.userName = this.$route.params.userName;
    },
  },
  mounted() {
    this.getUserData();
  },
};
</script>

<style scoped>
.lobby {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border: hidden;
}
.lobby div input {
  color: black;
}
.lobby .myface {
  width: 300px;
}
.lobby .myface img {
  width: 100%;
}
.go-or-make {
  display: flex;
  justify-content: center;
  align-items: center;
}
.go-or-make button {
  margin: 10px;
  width: 100px;
}
.lobby > img {
  width: 300px;
  margin-top: 100px;
  margin-bottom: 100px;
}
</style>