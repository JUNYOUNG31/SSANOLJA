<template>
  <div
    class="telestation-container"
    id="divdiv"
    style="color: black; overflow: scroll"
  >
    <div
      v-show="gameMode === 'text'"
      style="display: flex; flex-direction: column_reverse; align-items: center"
    >
      <!-- 키워드 입력 -->
      <div>
        {{ textingTime }}
        {{ drawingTime }}
        <button @click="startTexting()">시작</button>
        <button @click="pauseTexting()">정지</button>
      </div>
      <div
        v-show="drawingOrder !== 1"
        style="
          width: 800px;
          height: 400px;
          background-color: white;
          color: black;
        "
      >
        <img :src="recieveDraw" alt="받은그림" />
      </div>
      <div style="width: 800px; text-align: center">
        <label>키워드</label>
        <input
          v-model="keyword"
          class="form-control"
          type="text"
          required
          placeholder="키워드를 입력하세요"
        />
        <br />
      </div>
      <p class="text-center">
        <button
          class="btn btn-lg btn-success"
          @click="[sendMessageToEveryBody('', 'completed'), changeCompleted()]"
          :disabled="myCompleted"
        >
          입력 완료
        </button>
      </p>
    </div>

    <div
      v-show="gameMode === 'drawing'"
      style="display: flex; flex-direction: column; align-items: center"
    >
      {{ drawingTime }}
      <div>{{ receiveKeyword }}</div>
      <div id="gamearea">
        <canvasApi
          ref="canvasApi"
          @canvasCompleted="canvasCompleted"
          @setDrawData="setDrawData"
        ></canvasApi>
      </div>
    </div>

    <div v-show="gameMode === 'album'" style="width: 100%; text-align: center">
      <h2>{{ recieveUsers[0] + "'s Album" }}</h2>
      <button @click="testfn3()" style="display: block; margin: auto">
        클릭
      </button>
      <div
        v-for="(data, index) in recieveAlbum"
        :key="index"
        style="display: flex; flex-direction: column; align-items: center"
      >
        <div style="width: 100%" v-if="index % 2 === 0" id="keyword">
          <div
            class="animate__animated animate__slideInLeft"
            style="display: flex; flex-direction: column; align-items: center"
          >
            <div class="d-flex" style="align-self: start">
              <img
                src="../../assets/inspector.png"
                alt=""
                style="width: 50px"
              />
              <div style="margin: auto 0; font-size: 20px; font-weight: bold">
                {{ recieveUsers[index] }}
              </div>
            </div>
            <div style="align-self: start; display: flex">
              <div class="speech-bubble" style="font-size: 25px">
                <p style="margin: 5px 15px">{{ data }}</p>
              </div>
              <div v-if="index !== 0">
                <button @click="bestPick(index)" style="margin-top: 20px">
                  <v-icon color="blue">{{
                    index === best - 1 ? "mdi-thumb-up" : "mdi-thumb-up-outline"
                  }}</v-icon>
                </button>
                <button @click="worstPick(index)">
                  <v-icon color="red">{{
                    index === worst - 1
                      ? "mdi-thumb-down"
                      : "mdi-thumb-down-outline"
                  }}</v-icon>
                </button>
              </div>
            </div>
          </div>
        </div>
        <div v-else id="draw" style="align-self: end">
          <div
            class="animate__animated animate__slideInRight"
            style="display: flex; flex-direction: column; align-items: center"
          >
            <div class="d-flex flex" style="align-self: end">
              <div style="margin: auto 3px; font-size: 20px; font-weight: bold">
                {{ recieveUsers[index] }}
              </div>
              <img src="../../assets/artist.png" alt="" style="width: 50px" />
            </div>
            <div
              class="mt-3 me-3"
              style="display: flex; flex-direction: column; align-items: center"
            >
              <img :src="data" alt="" style="width: 400px; height: 285px" />
              <div>
                <button @click="bestPick(index)">
                  <v-icon color="blue">{{
                    index === best - 1 ? "mdi-thumb-up" : "mdi-thumb-up-outline"
                  }}</v-icon>
                </button>
                <button @click="worstPick(index)">
                  <v-icon color="red">{{
                    index === worst - 1
                      ? "mdi-thumb-down"
                      : "mdi-thumb-down-outline"
                  }}</v-icon>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div id="next">
        <button
          style="
            display: block;
            margin: auto;
            font-size: 25px;
            background-color: rgb(1, 215, 236);
          "
          v-show="isRoomMaker && round < personnel"
          @click="sendMessageToEveryBody('', 'nextAlbum')"
        >
          다음 앨범
        </button>
        <button
          style="
            display: block;
            margin: auto;
            font-size: 25px;
            background-color: rgb(1, 215, 236);
          "
          v-show="isRoomMaker && round === personnel"
          @click="sendMessageToEveryBody('', 'nextAlbum')"
        >
          결과보기
        </button>
      </div>
      <div v-if="isRoomMaker">
        <button
          class="paper-btn"
          style="width: 100%"
          @click="sendMessageToEveryBody('', 'replay')"
        >
          <span>게임 선택하기</span>
        </button>

        <button
          class="paper-btn btn-secondary"
          style="width: 100%"
          @click="leaveRoom()"
        >
          <span>방나가기</span>
        </button>
      </div>
    </div>

    <div
      v-show="gameMode === 'best'"
      style="display: flex; flex-direction: column; align-items: center"
    >
      <h1>bestPlayer</h1>
      <div v-if="bestVideo">
        <user-video :best-video="bestVideo" />
      </div>
      <p>베스트 플레이어는 {{ bestPlayer }} 입니다.</p>
      <div v-show="bestResultMode === 1">
        <p>{{ bestPreData }}</p>
        <img :src="bestData" alt="" />
      </div>
      <div v-show="bestResultMode === 2">
        <img :src="bestPreData" alt="" />
        <p>{{ bestData }}</p>
      </div>
      <button v-show="isRoomMaker" @click="sendMessageToEveryBody('', 'worst')">
        worst보러가기
      </button>
    </div>

    <div
      v-show="gameMode === 'worst'"
      style="display: flex; flex-direction: column; align-items: center"
    >
      <h1>worstPlayer</h1>
      <div v-if="worstVideo">
        <user-video :worst-video="worstVideo" />
      </div>
      <p>워스트 플레이어는 {{ worstPlayer }} 입니다.</p>
      <div v-show="worstResultMode === 1">
        <p>{{ worstPreData }}</p>
        <img :src="worstData" alt="" />
      </div>
      <div v-show="worstResultMode === 2">
        <img :src="worstPreData" alt="" />
        <p>{{ worstData }}</p>
      </div>
      <!-- <button v-show="isRoomMaker" @click="sendMessageToEveryBody('','room')">다시하기</button> -->
    </div>
  </div>
</template>
<script src="../../assets/js/app.js"></script>
<script>
import axios from "axios";
import { mapState } from "vuex";
import CanvasApi from "@/components/CanvasApi";
import UserVideo from "@/components/Video/UserVideo";
import "animate.css";
export default {
  name: "Telestation",
  props: {
    streamManager: Object,
    rules: Object,
    gameRes: Object,
  },
  components: {
    CanvasApi,
    UserVideo,
  },
  data() {
    return {
      keyword: "",
      draw: "",
      // 타이머
      drawingTime: this.rules.drawingTime,
      textingTime: this.rules.textingTime,
      voteTime: this.rules.voteTime,
      drawingEnabled: false,
      textingEnabled: false,
      votingEnabled: false,

      gameMode: "album",

      drawingOrder: 1 /* 라운드 */,
      completedPlayers: 0 /* 그림 다 그렸거나 or 키워드 입력완료 누른 플레이어 수 */,
      myCompleted: false,
      readyPlayers: 0 /* 전 라운드에서 키워드나 그림정보를 웹소켓으로 받은 플레이어 수*/,
      personnel: 4, //인원수
      participant: new Map(), // 참가자들
      targetUser: "", // 웹소켓 받는 사람
      receiveKeyword: "", // 받은 키워드
      recieveDraw: "", // 받은 그림
      recieveAlbum: [
        "졸라맨",
        "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2F20150401_224%2Fche5466_14278577694929nElY_JPEG%2FXXX.PNG&type=a340",
        "악당이름이 뭐지 ?",
        "https://search.pstatic.net/common/?src=http%3A%2F%2Fcafefiles.naver.net%2FMjAxNzEyMjNfODIg%2FMDAxNTE0MDM4NzU5MzU1.5LoCzGYvjU5pzVOVydLGbnhEKORvoRNJEc9f6cutzzEg.onVUrp8GZ0DA3XUmi0Q2eXLsmjEDfPKNyQwKBYvCFTIg.PNG.kyhk614%2F%25B2%25D9%25C0%25DA_3%25B1%25E2.png&type=a340",
      ],
      recieveUsers: ["조성현", "강광은", "배소원", "박준영"],
      round: 0,
      gameId: this.gameRes.playGameId,
      dataIndex: 0, // 앨범 번호
      worst: 0,
      best: 0,
      worstPlayer: "",
      worstVideo: null,
      bestPlayer: "",
      bestVideo: null,
      worstResultMode: 0, //1 이면 키워드 사진 2이면 사진 키워드
      bestResultMode: 0,
      worstPreData: "",
      worstData: "",
      bestPreData: "",
      bestData: "",
      test: 0,
      testp: 4,
    };
  },
  methods: {
    leaveRoom() {
      this.$store.dispatch("leaveSession");
      this.$router.push({
        name: "Lobby",
        params: { sendUserEmail: this.sendUserEmail },
      });
    },
    backToRoom() {
      // this.sendMessageToEveryBody('', 'backToRoom')
      (this.keyword = ""),
        (this.draw = ""),
        (round = 0),
        (gameMode = "album"),
        this.session.off("signal:keyword");
      this.session.off("signal:draw");
      this.session.off("signal:completed");
      this.session.off("signal:ready");
      this.session.off("signal:nextAlbum");
      this.session.off("signal:result");
      this.session.off("signal:worst");
      this.sendMessageToEveryBody("", "initTelestation");
    },
    testfn3() {
      this.removeKeyword();
      this.removeDraw();
      this.testfn();
      setTimeout(() => {
        this.testfn2();
      }, 2500);
    },
    testfn() {
      var index = 0;
      var final = parseInt(this.testp / 2);
      var loading = setInterval(function () {
        if (index === final) {
          clearInterval(loading);
        }
        $("#keyword > div").eq(index).fadeIn(1000);
        index++;
        $("#divdiv").scrollTop($("#divdiv")[0].scrollHeight);
      }, 5000);
    },
    testfn2() {
      var index1 = 0;
      var final1 = parseInt(this.testp / 2);
      var loading1 = setInterval(function () {
        if (index1 === final1) {
          clearInterval(loading1);
        }
        if (index1 === final1 - 1) {
          setTimeout(() => {
            $("#next").fadeIn(1000);
            $("#divdiv").scrollTop($("#divdiv")[0].scrollHeight);
          }, 2500);
        }
        $("#draw > div").eq(index1).fadeIn(1000);
        index1++;
        $("#divdiv").scrollTop($("#divdiv")[0].scrollHeight);
      }, 5000);
    },
    removeKeyword() {
      console.log("리무브 들어왔니?ㄴ");
      $("#keyword > div").hide();
      $("#next").hide();
    },
    removeDraw() {
      $("#draw > div").hide();
    },
    getUsers() {
      // 참가자 닉네임과 connectinId 딕셔너리화 for(웹소켓)
      let myNickName = JSON.parse(this.publisher.stream.connection.data);
      this.participant.set(
        myNickName.clientData,
        this.publisher.stream.connection
      );
      for (let index = 0; index < this.subscribers.length; index++) {
        let nickName = JSON.parse(
          this.subscribers[index].stream.connection.data
        );
        this.participant.set(
          nickName.clientData,
          this.subscribers[index].stream.connection
        );
      }
    },
    sendMessageToEveryBody(data, type) {
      // 참가자 전원 웹소켓
      this.session
        .signal({
          data: data,
          to: [],
          type: type,
        })
        .then(() => {})
        .catch((error) => {
          console.error(error);
        });
    },
    sendMessageToTargetUser(data, type, targetUser) {
      // 특정 참가자 웹소켓
      this.session
        .signal({
          data: data,
          to: [targetUser],
          type: type,
        })
        .then(() => {})
        .catch((error) => {
          console.error(error);
        });
    },
    // 타이머 함수 모음 시작
    startDrawing() {
      //Drawing 타이머 on
      this.drawingEnabled = true;
    },
    startTexting() {
      //Texting 타이머 on
      this.textingEnabled = true;
    },
    pauseDrawing() {
      //Drawing 타이머 off
      this.drawingEnabled = false;
    },
    pauseTexting() {
      //Texting 타이머 off
      this.textingEnabled = false;
    },
    worstPick(index) {
      this.worst = index + 1;
      console.log(this.worst);
    },
    bestPick(index) {
      this.best = index + 1;
      console.log(this.best);
    },
    changeCompleted() {
      this.myCompleted = true;
    },
    // startAlbum(){
    //   this.votingEnabled = true;
    // },
    // pauseAlbum(){
    //   this.votingEnabled = false;
    // },

    // startTimer(){
    //   if (gmaeMode === 'drawing') {
    //     this.drawingEnabled = true;
    //   }
    //   else if (gmaeMode === 'keyword') {
    //     this.textingEnabled = true;
    //   }
    //   else if (gmaeMode === 'vote') {
    //     this.votingEnabled = true;
    //   }
    // },
    // 타이머 함수 모음 끝

    // 캔버스 관련 함수 시작
    canvasCompleted() {
      this.sendMessageToEveryBody("", "completed");
      console.log("캔버스에서 완료 버튼 눌렀나 ?");
    },
    setDrawData(imageData) {
      console.log("그림데이터 저장완료@@@@@@@@@@@@");
      this.draw = imageData;
    },
    // 캔버스 관련 함수 시작

    //라운드 끝내기& 시작 함수 모음 시작
    endTextRound() {
      // Texting 라운드 끝
      this.pauseTexting();
      this.textingTime = this.rules.textingTime;
      axios({
        method: "POST",
        url: "/api/telestations/saveData",
        data: {
          data: this.keyword,
          userNickname: this.myUserName,
          roomCode: this.mySessionId,
          drawingOrder: this.drawingOrder,
          personnel: this.personnel,
        },
      }).then((res) => {
        this.targetUser = res.data.userNickname;
        this.keyword = "";
        this.sendMessageToTargetUser(
          JSON.stringify(res.data),
          "keyword",
          this.participant.get(this.targetUser)
        );
      });
    },
    endDrawRound() {
      // Drawing 라운드 끝
      this.pauseDrawing();
      this.drawingTime = this.rules.drawingTime;
      console.log(
        "-------------------------------------------------------------------------------"
      );
      console.log(this.drawingTime, this.rules.drawingTime);
      console.log(
        "-------------------------------------------------------------------------------"
      );
      this.$refs.canvasApi.saveClick();
      console.log(this.drawingOrder);
      console.log(
        this.myUserName,
        this.mySessionId,
        this.drawingOrder,
        this.personnel,
        this.draw
      );
      // canvas에서 그림 축출해서 this.darw에 저장
      axios({
        method: "POST",
        url: "/api/telestations/saveData",
        data: {
          data: this.draw,
          userNickname: this.myUserName,
          roomCode: this.mySessionId,
          drawingOrder: this.drawingOrder,
          personnel: this.personnel,
        },
      }).then((res) => {
        this.targetUser = res.data.userNickname;
        this.$refs.canvasApi.resetAll();
        this.sendMessageToTargetUser(
          JSON.stringify(res.data),
          "draw",
          this.participant.get(this.targetUser)
        );
      });
    },
    startTextRound() {
      this.myCompleted = false;
      this.drawingOrder++;
      if (this.drawingOrder === this.personnel + 1) {
        this.startAlbumRound();
      } else {
        this.gameMode = "text";
        this.startTexting();
      }
    },
    startDrawRound() {
      // this.sendMessageToEveryBody('', "drawingOrder")
      this.drawingOrder++;
      if (this.drawingOrder === this.personnel + 1) {
        this.startAlbumRound();
      } else {
        this.gameMode = "drawing";
        this.startDrawing();
      }
    },
    startAlbumRound() {
      this.round++;
      console.log("dataIndex" + this.dataIndex);
      console.log("베슽흐" + this.best);
      console.log("워스트" + this.worst);
      axios({
        method: "POST",
        url: "/api/telestations/showAlbum",
        data: {
          userNickname: this.myUserName,
          gameId: this.gameId,
          round: this.round,
          dataIndex: this.dataIndex,
          worstVote: this.worst,
          bestVote: this.best,
        },
      })
        .then((res) => {
          if (this.round <= this.personnel) {
            this.gameMode = "album";
            this.dataIndex = res.data.dataIndex;
            this.recieveAlbum = res.data.dataList;
            this.recieveUsers = res.data.userNicknameList;
            console.log("this.dataIndex" + this.dataIndex);
            console.log("this.recieveAlbum" + this.recieveAlbum);
            this.worst = 0;
            this.best = 0;
            // this.removeKeyword()
            // this.loading = setInterval(this.fadeInKeyword(),5000)
          } else {
            this.bestPlayer = res.data.best.nickname;
            this.worstPlayer = res.data.worst.nickname;
            this.worstPreData = res.data.worst.preData;
            this.worstData = res.data.worst.data;
            this.bestPreData = res.data.best.preData;
            this.bestData = res.data.best.data;
            if (res.data.best.preDrawingOrder % 2 === 1) {
              this.bestResultMode = 1;
            } else {
              this.bestResultMode = 2;
            }
            if (res.data.worst.preDrawingOrder % 2 === 1) {
              this.worstResultMode = 1;
            } else {
              this.worstResultMode = 2;
            }
            this.gameMode = "best";
          }
        })
        .catch((err) => {
          console.log(err, "앨범에러");
        });
    },
    // startResult() {
    //   axios({
    //     method:'POST',
    //     url: '/api/telestations/voteResult',
    //     data: {
    //       gameId: this.gameId
    //     }
    //   })
    //   .then((res)=> {
    //     this.bestPlayer = res.data.best.nickName
    //     this.worstPlayer = res.data.worst.nickName
    //     this.worstPreData = res.data.worst.preData
    //     this.worstData = res.data.worst.data
    //     this.bestPreData = res.data.best.preData
    //     this.bestData = res.data.best.data
    //     if (res.data.best.preDrawingOrder%2 === 1) {
    //       this.bestResultMode = 1
    //     }else {
    //       this.bestResultMode = 2
    //     }
    //     if (res.data.worst.preDrawingOrder%2 === 1) {
    //       this.worstResultMode = 1
    //     }else {
    //       this.worstResultMode = 2
    //     }
    //     this.gameMode='best'
    //   })

    // },

    // this.sendMessageToEveryBody(JSON.stringify(this.gameRes), 'gameRes')
  },
  computed: {
    ...mapState([
      "myUserName",
      "mySessionId",
      "subscribers",
      "publisher",
      "session",
      "isRoomMaker",
    ]),
  },
  watch: {
    // Drawing timer
    drawingEnabled(value) {
      if (value) {
        setTimeout(() => {
          this.drawingTime--;
        }, 1000);
      }
    },

    drawingTime: {
      handler(value) {
        if (value > 0 && this.drawingEnabled) {
          setTimeout(() => {
            this.drawingTime--;
          }, 1000);
        } else if (value === 0) {
          this.endDrawRound();
        }
      },
      immediate: false,
    },
    //Text timer
    textingEnabled(value) {
      if (value) {
        setTimeout(() => {
          this.textingTime--;
        }, 1000);
      }
    },
    textingTime: {
      handler(value) {
        if (value > 0 && this.textingEnabled) {
          setTimeout(() => {
            this.textingTime--;
          }, 1000);
        } else if (value === 0) {
          this.endTextRound();
        }
      },
      immediate: false,
    },
    votingEnabled(value) {
      if (value) {
        setTimeout(() => {
          this.voteTime--;
        }, 1000);
      }
    },
    voteTime: {
      handler(value) {
        if (value > 0 && this.votingEnabled) {
          setTimeout(() => {
            this.voteTime--;
          }, 1000);
        } else if (value === 0) {
          this.endTextRound();
        }
      },
      immediate: false,
    },

    completedPlayers: {
      // 제출완료
      handler(value) {
        if (value === this.personnel && this.gameMode === "text") {
          this.completedPlayers = 0;
          this.endTextRound();
        } else if (value === this.personnel && this.gameMode === "drawing") {
          this.completedPlayers = 0;
          this.endDrawRound();
        }
      },
    },
    readyPlayers: {
      // 웹소켓 수신 완료 => 다음라운드로 가기위한 변수
      handler(value) {
        if (value === this.personnel && this.gameMode === "text") {
          this.readyPlayers = 0;
          this.startDrawRound();
        } else if (value === this.personnel && this.gameMode === "drawing") {
          this.readyPlayers = 0;
          this.startTextRound();
        }
      },
    },
    bestPlayer: {
      handler() {
        for (let index = 0; index < this.subscribers.length; index++) {
          let nickName = JSON.parse(
            this.subscribers[index].stream.connection.data
          );
          if (this.bestPlayer == nickName.clientData) {
            this.bestVideo = this.subscribers[index];
            console.log(nickName, this.bestVideo);
          }
        }
      },
    },
    worstPlayer: {
      handler() {
        for (let index = 0; index < this.subscribers.length; index++) {
          let nickName = JSON.parse(
            this.subscribers[index].stream.connection.data
          );
          if (this.worstPlayer == nickName.clientData) {
            this.worstVideo = this.subscribers[index];
            console.log(nickName, this.bestVideo);
          }
        }
      },
    },
  },
  mounted() {
    // $('#divdiv').scrollTop($('divdiv').prop('scrollHeight'));
    this.startTexting();
    this.getUsers();
    this.session.on("signal:keyword", (event) => {
      // 입력한 키워드 백에 보내기
      let data = JSON.parse(event.data);
      this.receiveKeyword = data.data;
      this.sendMessageToEveryBody("", "ready");
    });
    this.session.on("signal:draw", (event) => {
      // 그린 그림 백에 보내기
      let data = JSON.parse(event.data);
      this.recieveDraw = data.data;
      this.sendMessageToEveryBody("", "ready");
    });
    this.session.on("signal:completed", (event) => {
      // 입력버튼 누르면 완료된 사람 +1
      console.log(event.data);
      this.completedPlayers++;
    });
    this.session.on("signal:ready", (event) => {
      // 백에서 데이터 받아서 웹소켓 통신하고 나면 +1
      console.log(event.data);
      this.readyPlayers++;
    });
    this.session.on("signal:nextAlbum", (event) => {
      // 그린 그림 백에 보내기
      console.log(event.data);
      this.startAlbumRound();
    });
    this.session.on("signal:result", (event) => {
      // 그린 그림 백에 보내기
      console.log(event.data);
      this.startResult();
    });
    this.session.on("signal:worst", (event) => {
      // 그린 그림 백에 보내기
      console.log(event.data);
      this.bestVideo = null;
      this.gameMode = "worst";
    });
    this.session.on("signal:replay", (event) => {
      // 그린 그림 백에 보내기
      this.worstVideo = null;
      this.backToRoom();
    });

    // this.session.on('signal:gameRes', (event)=>{
    //   console.log(event.data)
    //   this.gameRes = JSON.parse(event.data)
    // })
  },
  created() {
    this.personnel = this.subscribers.length;
  },
};
</script>

<style scoped>
.telestation-container {
  height: 100%;
}
.completed {
  background: black;
  cursor: not-allowed;
}
#keyword > div {
  transition: all 2s;
}
#divdiv {
  -ms-overflow-style: none;
}
#divdiv::-webkit-scrollbar {
  display: none;
}

.animate__bounceInRight {
  --animate-duration: 2.5s;
}
.animate__bounceInLeft {
  --animate-duration: 2.5s;
}
.animate__slideInDown {
  --animate-duration: 1.7s;
}
.speech-bubble {
  position: relative;
  background: yellow;
  border-radius: 0.4em;
  margin-top: 20px;
  margin-left: 15px;
  min-width: 100px;
}

.speech-bubble:after {
  content: "";
  position: absolute;
  top: 0;
  left: 20%;
  width: 0;
  height: 0;
  border: 10px solid transparent;
  border-bottom-color: yellow;
  border-top: 0;
  border-left: 0;
  margin-left: -10px;
  margin-top: -10px;
}
/* #divdiv {
  display:flex;

  flex-direction: column-reverse;

  overflow-y:auto;

  height:100%;
} */
</style>
