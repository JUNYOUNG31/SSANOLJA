<template>
  <div class="lobby">
    <div class="myface">
      <img src="../assets/logo.png" alt="logo">
    
    <div id="join" v-if="!session">
			<div id="join-dialog" class="vertical-center">
				<h1>LOBBY</h1>
				<div>
					<p>
						<label>닉네임</label>
					</p>
					<input v-model="userData.userNickname" class="paper-input" style="width:200px" type="text" required placeholder="닉네임을 입력하세요">
					<p>
						<label>방 참여코드</label>
					</p>
					<input v-model="joinCode" class="paper-input" style="width:200px" type="text" required placeholder="참여코드를 입력하세요">
					<p class="text-center">
						<button class="paper-btn btn-lg btn-success" @click="checkRoom(joinCode)">입장하기</button>
					</p>
					<p class="text-center">
						<button class="paper-btn btn-lg btn-success" @click="makeRoom()">방 만들기</button>
					</p>
				</div>
			</div>
		</div>
		</div>
		<preview ref="preview" :joinCode="joinCode" :myUserName="userData.userNickname">
		</preview>
  </div>
</template>

<script>
import axios from "axios";
import { mapState } from "vuex";
import Preview from "@/components/Preview.vue";

export default {
  name: "Lobby",

	data () {
		return {
			userData : {
				userName : null,
				userEmail : null,
				userNickname : null,
			},
			joinCode: '',
			myUserName: '',
			sendUserEmail: '',
		}
	},
	components: {
		Preview,
	},
	computed: {
		...mapState([
			"session",
			"publisher",
			"subscribers",
			"mainStreamManager",
		])
	},    
	methods: {
		checkRoom(joinCode) {
			axios({
				method: 'post',
				url: '/api/users/update',
				data: {
					userEmail: this.userData.userEmail,
					updatedUserNickname: this.userData.userNickname
				}
			}).then(() =>{
				axios({
					method: 'get',
						url:`/api/rooms/${joinCode}`,
					})
					.then(()=>{
						localStorage.clear()
						localStorage.setItem("isRoomMaker", false)
						this.$refs.preview.dialog = true

						// console.log(res.status)
							
					})
					.catch(() => {
						alert('일치하는 방이 존재하지 않습니다.')
					})
			})
		},

		makeRoom() {
			axios({
				method: 'post',
				url: '/api/users/update',
				data: {
					userEmail: this.userData.userEmail,
					updatedUserNickname: this.userData.userNickname
				}
			})
			.then(() => {
				axios({
        method: 'post',
        url:'/api/rooms',
      })
        .then(res=>{
					this.joinCode = res.data
					localStorage.clear()
					localStorage.setItem("isRoomMaker", true)
					this.$refs.preview.dialog = true
        })
        .catch(err=> {
          console.log(err)
        })
			})
			.catch(()=>{
				alert('이미 존재하는 닉네임입니다. 다른 닉네임을 입력해주세요')
			})
    },
		leaveSession() {
				this.$store.dispatch('leaveSession')
		},
		updateMainVideoStreamManager(data) {
				this.$store.dispatch('updateMainVideoStreamManager',data)
		},
		getUserData() {
			this.sendUserEmail = this.$route.params.sendUserEmail
			axios({
					method:'POST',
					url: '/api/login/sendUser',
					data: this.sendUserEmail
			})
			.then(res => {
					this.userData.userName = res.data.userNickname
					this.userData.userNickname = res.data.userNickname
					this.userData.userEmail = res.data.userEmail
					// console.log(this.userData)
			})
			.catch(err => console.log(err))
		}
	},
	mounted() {
		localStorage.clear()
		this.getUserData()
		localStorage.clear()
	}
}
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

.vertical-center {
	text-align: center;
}
.vertical-center input{
	margin: auto;
	margin-bottom: 20px;
}
</style>