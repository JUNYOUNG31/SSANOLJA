<template>
  <div
<<<<<<< HEAD
    class="telestation-container paper-input"
    style="background: rgb(231, 231, 231); border: 1px solid gray"
    oncontextmenu="return false"
    onselectstart="return false"
    ondragstart="return false"
  >
    <audio
      autoplay
      loop
      onloadstart="this.volume=0.2"
      src="../../assets/telestation_audio/차분한-약간 경쾌-피아노-재즈-G003.mp3"
    ></audio>
    <!-- <h1>텔레스테이션</h1> -->
    <div
      v-show="gameMode === 'text'"
      style="display: flex; flex-direction: column; align-items: center"
=======
    class="telestation-container"
    id="divdiv"
    style="color: black; overflow: scroll"
  >
    <div
      v-show="gameMode === 'text'"
      style="display: flex; flex-direction: column_reverse; align-items: center"
>>>>>>> f3f240190fdaaee4281d584092885b1410d14527
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

<<<<<<< HEAD
    <div
      v-show="gameMode === 'album'"
      style="display: flex; flex-direction: column; align-items: center"
    >
      <div v-for="(data, index) in recieveAlbum" :key="index">
        <div style="display: flex" v-if="index % 2 === 0">
          <p>{{ data }}</p>
          <div v-if="index !== 0">
            <button @click="bestPick(index)">
              <v-icon color="blue">{{
                index === best - 1 ? "mdi-thumb-up" : "mdi-thumb-up-outline"
              }}</v-icon>
            </button>
            <button @click="worstPick(index)">
              <v-icon color="red">{{
                index === worst - 1 ? "mdi-thumb-up" : "mdi-thumb-up-outline"
              }}</v-icon>
            </button>
          </div>
        </div>
        <div v-else>
          <img :src="data" alt="" />
          <div>
            <button @click="bestPick(index)">
              <v-icon color="blue">{{
                index === best - 1 ? "mdi-thumb-up" : "mdi-thumb-up-outline"
              }}</v-icon>
            </button>
            <button @click="worstPick(index)">
              <v-icon color="red">{{
                index === worst - 1 ? "mdi-thumb-up" : "mdi-thumb-up-outline"
              }}</v-icon>
            </button>
          </div>
        </div>
      </div>
      <button
        v-show="isRoomMaker && round < personnel"
        @click="sendMessageToEveryBody('', 'nextAlbum')"
      >
        다음앨범
      </button>
      <button
        v-show="isRoomMaker && round === personnel"
        @click="sendMessageToEveryBody('', 'nextAlbum')"
      >
        결과보기
      </button>
      <p>{{ round }}</p>
    </div>
    <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
    <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
    <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
    <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
    <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
    <!-- <div v-show="gameMode ==='best'" style="display:flex; flex-direction: column; align-items: center;">
=======
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
>>>>>>> f3f240190fdaaee4281d584092885b1410d14527
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
<<<<<<< HEAD
      <button v-show="isRoomMaker" @click="sendMessageToEveryBody('','worst')">worst보러가기</button>
    </div> -->

    <!-- <div v-show="gameMode ==='worst'" style="display:flex; flex-direction: column; align-items: center;">
=======
      <button v-show="isRoomMaker" @click="sendMessageToEveryBody('', 'worst')">
        worst보러가기
      </button>
    </div>

    <div
      v-show="gameMode === 'worst'"
      style="display: flex; flex-direction: column; align-items: center"
    >
>>>>>>> f3f240190fdaaee4281d584092885b1410d14527
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
<<<<<<< HEAD
      <button v-show="isRoomMaker" @click="sendMessageToEveryBody('','room')">다시하기</button>
    </div> -->
    <div v-show="gameMode === 'best'" class="modeBestWorst">
      <div class="word">
        <span>B</span><span>e</span><span>s</span><span>t</span><span>P</span
        ><span>l</span><span>a</span><span>y</span><span>e</span><span>r</span>
      </div>
      <audio
        autoplay
        onloadstart="this.volume=0.3"
        src="../../assets/telestation_audio/등장중일때.mp3"
      ></audio>
      <canvas id="canvas"></canvas>
      <div class="parent1">
        <div class="div1-1">
          <div class="parent2">
            <div
              class="div2-1 paper-input animate__animated animate__animated animate__bounceInDown"
            >
              <div class="">
                <p
                  class="animate__animated animate__pulse animate__infinite"
                  style="
                    text-align: center;
                    margin: 0px 0px 0px 0px;
                    font-size: 35px;
                    font-style: italic;
                    font-weight: bold;
                  "
                >
                  - 강광은 -
                </p>
              </div>
            </div>
            <div class="div2-2">
              <div class="parent3">
                <!-- <button @click="testFt">카메라 보이기</button>
                <div class="div3-1 animate__animated animate__bounceInLeft paper-input" v-if="bestVideo" style="display:flex; flex-direction: column; justify-content: center;">
                  <user-video :best-video="bestVideo" />
                </div> -->
                <!-- <div class="div3-1 animate__animated animate__bounceInLeft paper-input" v-if="bestVideo" style="display:flex; flex-direction: column; justify-content: center;">
                  카메라 div <user-video :best-video="bestVideo" />
                </div> -->
                <div
                  class="div3-2 animate__animated animate__bounceInRight paper-input"
                  style="
                    display: flex;
                    flex-direction: column;
                    justify-content: center;
                  "
                >
                  <div class="parent4">
                    <div
                      class="div4-1 speech-bubble-left"
                      style="
                        display: flex;
                        flex-direction: column;
                        justify-content: center;
                      "
                    >
                      <span style="">이전 데이터 이름 (짱구)</span>
                    </div>
                    <div class="div4-2 speech-bubble-right">
                      <img
                        src="../../assets/telestation_image/gu.jpg"
                        style="float: right; width: 350px; height: 250px"
                      />
                    </div>
                  </div>
                  <div class="parent5">
                    <div class="div5-1 speech-bubble-left">
                      <img
                        src="../../assets/telestation_image/gu.jpg"
                        style="width: 350px; height: 250px"
                      />
                    </div>
                    <div class="div5-2">
                      <span class="speech-bubble-right" style="float: right"
                        >이전 데이터 이름 (짱구)</span
                      >
                    </div>
                  </div>

                  <!-- <div class="parent4" v-show="bestResultMode === 1">
                    <div class="div4-1 speech-bubble-left" style="display:flex; flex-direction: column; justify-content: center;">{{ bestPreData }}</span></div>
                    <div class="div4-2 speech-bubble-right"><img :src="bestData" alt="" style="float:right; width:350px; height:250px;"/></div>
                  </div>
                  <div class="parent5" v-show="bestResultMode === 2">
                    <div class="div5-1 speech-bubble-left"><img :src="bestData" alt="" style=" width:350px; height:250px;" /></div>
                    <div class="div5-2" style="float:right;"><span class="speech-bubble-right" style="float:right">{{ bestPreData }}</span></div>
                  </div> -->
                </div>
              </div>
            </div>
            <div class="div2-3" style="overflow: hidden">
              <button
                class="animate__animated animate__bounceInUp"
                v-show="isRoomMaker"
                @click="sendMessageToEveryBody('', 'worst')"
              >
                worst보러가기
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-show="gameMode === 'worst'" class="modeBestWorst">
      <div class="word">
        <span>W</span><span>o</span><span>r</span><span>s</span><span>t</span
        ><span>P</span><span>l</span><span>a</span><span>y</span><span>e</span
        ><span>r</span>
      </div>
      <audio
        autoplay
        onloadstart="this.volume=0.3"
        src="../../assets/telestation_audio/등장중일때.mp3"
      ></audio>
      <canvas id="canvas"></canvas>
      <div class="parent1">
        <div class="div1-1">
          <div class="parent2">
            <div
              class="div2-1 paper-input animate__animated animate__animated animate__bounceInDown"
            >
              <div class="">
                <p
                  class="animate__animated animate__pulse animate__infinite"
                  style="
                    text-align: center;
                    margin: 0px 0px 0px 0px;
                    font-size: 35px;
                    font-style: italic;
                    font-weight: bold;
                  "
                >
                  - 강광은 -
                </p>
              </div>
            </div>
            <div class="div2-2">
              <div class="parent3">
                <button @click="testFt">카메라 보이기</button>
                <div
                  class="div3-1 animate__animated animate__bounceInLeft paper-input"
                  v-if="bestVideo"
                  style="
                    display: flex;
                    flex-direction: column;
                    justify-content: center;
                  "
                >
                  <user-video :best-video="bestVideo" />
                </div>
                <!-- <div class="div3-1 animate__animated animate__bounceInLeft paper-input" v-if="bestVideo" style="display:flex; flex-direction: column; justify-content: center;">
                  카메라 div <user-video :best-video="worstVideo" />
                </div> -->
                <div
                  class="div3-2 animate__animated animate__bounceInRight paper-input"
                  style="
                    display: flex;
                    flex-direction: column;
                    justify-content: center;
                  "
                >
                  <div class="parent4">
                    <div
                      class="div4-1 speech-bubble-left"
                      style="
                        display: flex;
                        flex-direction: column;
                        justify-content: center;
                      "
                    >
                      <span style="">이전 데이터 이름 (짱구)</span>
                    </div>
                    <div class="div4-2 speech-bubble-right">
                      <img
                        src="../../assets/telestation_image/gu.jpg"
                        style="float: right; width: 350px; height: 250px"
                      />
                    </div>
                  </div>
                  <div class="parent5">
                    <div class="div5-1 speech-bubble-left">
                      <img
                        src="../../assets/telestation_image/gu.jpg"
                        style="width: 350px; height: 250px"
                      />
                    </div>
                    <div class="div5-2">
                      <span class="speech-bubble-right" style="float: right"
                        >이전 데이터 이름 (짱구)</span
                      >
                    </div>
                  </div>

                  <!-- <div class="parent4" v-show="bestResultMode === 1">
                    <div class="div4-1 speech-bubble-left" style="display:flex; flex-direction: column; justify-content: center;">{{ worstPreData }}</span></div>
                    <div class="div4-2 speech-bubble-right"><img :src="worstData" alt="" style="float:right; width:350px; height:250px;"/></div>
                  </div>
                  <div class="parent5" v-show="bestResultMode === 2">
                    <div class="div5-1 speech-bubble-left"><img :src="worstData" alt="" style=" width:350px; height:250px;" /></div>
                    <div class="div5-2" style="float:right;"><span class="speech-bubble-right" style="float:right">{{ worstPreData }}</span></div>
                  </div> -->
                </div>
              </div>
            </div>
            <div class="div2-3" style="overflow: hidden">
              <button
                class="animate__animated animate__bounceInUp"
                v-show="isRoomMaker"
                @click="sendMessageToEveryBody('', 'room')"
              >
                다시하기
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
    <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
    <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
    <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
    <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
=======
      <!-- <button v-show="isRoomMaker" @click="sendMessageToEveryBody('','room')">다시하기</button> -->
    </div>
>>>>>>> f3f240190fdaaee4281d584092885b1410d14527
  </div>
</template>
<script src="../../assets/js/app.js"></script>
<script>
import axios from "axios";
import { mapState } from "vuex";
import CanvasApi from "@/components/CanvasApi";
import UserVideo from "@/components/Video/UserVideo";
<<<<<<< HEAD
import "animate.css"; //글자 css

=======
import "animate.css";
>>>>>>> f3f240190fdaaee4281d584092885b1410d14527
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

<<<<<<< HEAD
      gameMode: "best",
=======
      gameMode: "album",
>>>>>>> f3f240190fdaaee4281d584092885b1410d14527

      drawingOrder: 1 /* 라운드 */,
      completedPlayers: 0 /* 그림 다 그렸거나 or 키워드 입력완료 누른 플레이어 수 */,
      myCompleted: false,
      readyPlayers: 0 /* 전 라운드에서 키워드나 그림정보를 웹소켓으로 받은 플레이어 수*/,
<<<<<<< HEAD
      personnel: 0, //인원수
=======
      personnel: 4, //인원수
>>>>>>> f3f240190fdaaee4281d584092885b1410d14527
      participant: new Map(), // 참가자들
      targetUser: "", // 웹소켓 받는 사람
      receiveKeyword: "", // 받은 키워드
      recieveDraw: "", // 받은 그림
<<<<<<< HEAD
      recieveAlbum: null,
=======
      recieveAlbum: [
        "졸라맨",
        "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2F20150401_224%2Fche5466_14278577694929nElY_JPEG%2FXXX.PNG&type=a340",
        "악당이름이 뭐지 ?",
        "https://search.pstatic.net/common/?src=http%3A%2F%2Fcafefiles.naver.net%2FMjAxNzEyMjNfODIg%2FMDAxNTE0MDM4NzU5MzU1.5LoCzGYvjU5pzVOVydLGbnhEKORvoRNJEc9f6cutzzEg.onVUrp8GZ0DA3XUmi0Q2eXLsmjEDfPKNyQwKBYvCFTIg.PNG.kyhk614%2F%25B2%25D9%25C0%25DA_3%25B1%25E2.png&type=a340",
      ],
      recieveUsers: ["조성현", "강광은", "배소원", "박준영"],
>>>>>>> f3f240190fdaaee4281d584092885b1410d14527
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
<<<<<<< HEAD
    };
  },
  methods: {
    testFt() {
      this.bestPlayer = "강광은";
=======
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
>>>>>>> f3f240190fdaaee4281d584092885b1410d14527
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
<<<<<<< HEAD
=======
            this.recieveUsers = res.data.userNicknameList;
>>>>>>> f3f240190fdaaee4281d584092885b1410d14527
            console.log("this.dataIndex" + this.dataIndex);
            console.log("this.recieveAlbum" + this.recieveAlbum);
            this.worst = 0;
            this.best = 0;
<<<<<<< HEAD
=======
            // this.removeKeyword()
            // this.loading = setInterval(this.fadeInKeyword(),5000)
>>>>>>> f3f240190fdaaee4281d584092885b1410d14527
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
<<<<<<< HEAD
=======
    // $('#divdiv').scrollTop($('divdiv').prop('scrollHeight'));
>>>>>>> f3f240190fdaaee4281d584092885b1410d14527
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
<<<<<<< HEAD
    this.session.on("signal:result", (event) => {
      // 그린 그림 백에 보내기
      this.worstVideo = null;
      this.$router.push({ name: "Room", params: { joinCode: this.joinCode } });
=======
    this.session.on("signal:replay", (event) => {
      // 그린 그림 백에 보내기
      this.worstVideo = null;
      this.backToRoom();
>>>>>>> f3f240190fdaaee4281d584092885b1410d14527
    });

    // this.session.on('signal:gameRes', (event)=>{
    //   console.log(event.data)
    //   this.gameRes = JSON.parse(event.data)
    // })

    // 꽃가루날리기
    (function () {
      // globals
      var canvas;
      var ctx;
      var W;
      var H;
      var mp = 150; //max particles
      var particles = [];
      var angle = 0;
      var tiltAngle = 0;
      var confettiActive = true;
      var animationComplete = true;
      var deactivationTimerHandler;
      var reactivationTimerHandler;
      var animationHandler;

      // objects

      var particleColors = {
        colorOptions: [
          "DodgerBlue",
          "OliveDrab",
          "Gold",
          "pink",
          "SlateBlue",
          "lightblue",
          "Violet",
          "PaleGreen",
          "SteelBlue",
          "SandyBrown",
          "Chocolate",
          "Crimson",
        ],
        colorIndex: 0,
        colorIncrementer: 0,
        colorThreshold: 10,
        getColor: function () {
          if (this.colorIncrementer >= 10) {
            this.colorIncrementer = 0;
            this.colorIndex++;
            if (this.colorIndex >= this.colorOptions.length) {
              this.colorIndex = 0;
            }
          }
          this.colorIncrementer++;
          return this.colorOptions[this.colorIndex];
        },
      };

      function confettiParticle(color) {
        this.x = Math.random() * W; // x-coordinate
        this.y = Math.random() * H - H; //y-coordinate
        this.r = RandomFromTo(10, 15); //radius;
        this.d = Math.random() * mp + 10; //density;
        this.color = color;
        this.tilt = Math.floor(Math.random() * 10) - 10;
        this.tiltAngleIncremental = Math.random() * 0.07 + 0.05;
        this.tiltAngle = 0;

        this.draw = function () {
          ctx.beginPath();
          ctx.lineWidth = this.r / 2;
          ctx.strokeStyle = this.color;
          ctx.moveTo(this.x + this.tilt + this.r / 4, this.y);
          ctx.lineTo(this.x + this.tilt, this.y + this.tilt + this.r / 4);
          return ctx.stroke();
        };
      }

      $(document).ready(function () {
        SetGlobals();
        // InitializeButton();
        InitializeConfetti();

        $(window).resize(function () {
          W = window.innerWidth;
          H = window.innerHeight;
          canvas.width = W;
          canvas.height = H;
        });
      });

      function InitializeButton() {
        $("#stopButton").click(DeactivateConfetti);
        $("#startButton").click(RestartConfetti);
      }

      function SetGlobals() {
        canvas = document.getElementById("canvas");
        ctx = canvas.getContext("2d");
        W = window.innerWidth;
        H = window.innerHeight;
        canvas.width = W;
        canvas.height = H;
      }

      function InitializeConfetti() {
        particles = [];
        animationComplete = false;
        for (var i = 0; i < mp; i++) {
          var particleColor = particleColors.getColor();
          particles.push(new confettiParticle(particleColor));
        }
        StartConfetti();
      }

      function Draw() {
        ctx.clearRect(0, 0, W, H);
        var results = [];
        for (var i = 0; i < mp; i++) {
          (function (j) {
            results.push(particles[j].draw());
          })(i);
        }
        Update();

        return results;
      }

      function RandomFromTo(from, to) {
        return Math.floor(Math.random() * (to - from + 1) + from);
      }

      function Update() {
        var remainingFlakes = 0;
        var particle;
        angle += 0.01;
        tiltAngle += 0.1;

        for (var i = 0; i < mp; i++) {
          particle = particles[i];
          if (animationComplete) return;

          if (!confettiActive && particle.y < -15) {
            particle.y = H + 100;
            continue;
          }

          stepParticle(particle, i);

          if (particle.y <= H) {
            remainingFlakes++;
          }
          CheckForReposition(particle, i);
        }

        if (remainingFlakes === 0) {
          StopConfetti();
        }
      }

      function CheckForReposition(particle, index) {
        if (
          (particle.x > W + 20 || particle.x < -20 || particle.y > H) &&
          confettiActive
        ) {
          if (index % 5 > 0 || index % 2 == 0) {
            //66.67% of the flakes
            repositionParticle(
              particle,
              Math.random() * W,
              -10,
              Math.floor(Math.random() * 10) - 20
            );
          } else {
            if (Math.sin(angle) > 0) {
              //Enter from the left
              repositionParticle(
                particle,
                -20,
                Math.random() * H,
                Math.floor(Math.random() * 10) - 20
              );
            } else {
              //Enter from the right
              repositionParticle(
                particle,
                W + 20,
                Math.random() * H,
                Math.floor(Math.random() * 10) - 20
              );
            }
          }
        }
      }
      function stepParticle(particle, particleIndex) {
        particle.tiltAngle += particle.tiltAngleIncremental;
        particle.y += (Math.cos(angle + particle.d) + 3 + particle.r / 2) / 3;
        particle.x += Math.sin(angle);
        particle.tilt = Math.sin(particle.tiltAngle - particleIndex / 3) * 15;
      }

      function repositionParticle(particle, xCoordinate, yCoordinate, tilt) {
        particle.x = xCoordinate;
        particle.y = yCoordinate;
        particle.tilt = tilt;
      }

      function StartConfetti() {
        W = window.innerWidth;
        H = window.innerHeight;
        canvas.width = W;
        canvas.height = H;
        (function animloop() {
          if (animationComplete) return null;
          animationHandler = requestAnimFrame(animloop);
          return Draw();
        })();
      }

      function ClearTimers() {
        clearTimeout(reactivationTimerHandler);
        clearTimeout(animationHandler);
      }

      function DeactivateConfetti() {
        confettiActive = false;
        ClearTimers();
      }

      function StopConfetti() {
        animationComplete = true;
        if (ctx == undefined) return;
        ctx.clearRect(0, 0, W, H);
      }

      function RestartConfetti() {
        ClearTimers();
        StopConfetti();
        reactivationTimerHandler = setTimeout(function () {
          confettiActive = true;
          animationComplete = false;
          InitializeConfetti();
        }, 100);
      }

      window.requestAnimFrame = (function () {
        return (
          window.requestAnimationFrame ||
          window.webkitRequestAnimationFrame ||
          window.mozRequestAnimationFrame ||
          window.oRequestAnimationFrame ||
          window.msRequestAnimationFrame ||
          function (callback) {
            return window.setTimeout(callback, 1000 / 60);
          }
        );
      })();
    })();
    const spans = document.querySelectorAll(".word span");

    spans.forEach((span, idx) => {
      span.addEventListener("click", (e) => {
        e.target.classList.add("active");
      });
      span.addEventListener("animationend", (e) => {
        e.target.classList.remove("active");
      });

      // Initial animation
      setTimeout(() => {
        span.classList.add("active");
      }, 750 * (idx + 1));
    });
  },
  created() {
    this.personnel = this.subscribers.length;
  },
};
</script>

<style scoped>
/* 최종결과 */
/* 꽃가루 날리기 */
canvas {
  z-index: 10;
  pointer-events: none;
  position: fixed;
  top: 0;
  transform: scale(1.1);
}

/* 글자 css */
.animate__animated {
  --animate-duration: 2.5s;
}

.telestation-container {
  height: 100%;
  /* background-color: white; */ /* background-image: url(assets/background.jpg); */
}
.completed {
  /* background: black; */
  cursor: not-allowed;
}
<<<<<<< HEAD

.modeBestWorst {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100%;
  /* background-color: White; */
}

.speech-bubble-left {
  position: relative;
  background: #ffffff;
  border-radius: 0.4em;
  margin-top: 20px;
  margin-left: 15px;
  margin-right: auto;
  min-width: 100px;
  padding: 10px 10px 10px 10px;
}

.speech-bubble-left:after {
  content: "";
  position: absolute;
  top: 0;
  left: 10%;
  width: 0;
  height: 0;
  border: 10px solid transparent;
  border-bottom-color: #ffffff;
=======
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
>>>>>>> f3f240190fdaaee4281d584092885b1410d14527
  border-top: 0;
  border-left: 0;
  margin-left: -10px;
  margin-top: -10px;
}
<<<<<<< HEAD

.speech-bubble-right {
  position: relative;
  background: rgb(254, 240, 27);
  border-radius: 0.4em;
  margin-top: 20px;
  margin-left: auto;
  min-width: 100px;
  padding: 10px 10px 10px 10px;
}

.speech-bubble-right:after {
  content: "";
  position: absolute;
  top: 0;
  right: 10%;
  width: 0;
  height: 0;
  border: 10px solid transparent;
  border-bottom-color: rgb(254, 240, 27);
  border-top: 0;
  border-left: 0;
  margin-left: -10px;
  margin-top: -10px;
  transform: scaleX(-1);
}

.parent1 {
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: 1fr;
  grid-column-gap: 100px;
  grid-row-gap: 0px;
  width: 100%;
  height: 100%;
  /* background-color: green */
}

.div1-1 {
  grid-area: 1 / 1 / 2 / 2;
  /* background-color: rgb(77, 0, 0); */
}

.parent2 {
  display: grid;
  grid-template-columns: 0 1fr;
  grid-template-rows: 1fr 7fr 1fr;
  grid-column-gap: px;
  grid-row-gap: 0px;
  /* padding-top: 20px; */
  /* height: 100%; */
}
.div2-1 {
  margin: auto;
  padding: 7px 70px 7px 70px;
  grid-area: 1 / 2 / 2 / 3;
  background-color: white;

  /* background-color: lightsalmon; */
}
.div2-2 {
  margin-top: 30px;
  grid-area: 2 / 2 / 3 / 3;
  /* background-color: wheat; */
}
.div2-3 {
  grid-area: 3 / 2 / 4 / 3;
  /* background-color: darkgreen; */
}
.div2-3 button {
  display: block;
  margin: auto;
  /* background-color: aliceblue; */
}
.parent3 {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-template-rows: 1fr;
  grid-column-gap: 50px;
  grid-row-gap: 0px;
  height: 100%;
}
.div3-1 {
  grid-area: 1 / 1 / 2 / 2;
  margin-left: 20px;
  background-color: rgba(3, 3, 3, 0.8);
}
.div3-2 {
  grid-area: 1 / 2 / 2 / 3;
  margin-right: 20px;
  background-color: rgb(155, 187, 212);
}
.parent4 {
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: 1fr 4fr;
  grid-column-gap: 0px;
  grid-row-gap: 0x;
}

.div4-1 {
  grid-area: 1 / 1 / 2 / 2;
}
.div4-2 {
  grid-area: 2 / 1 / 3 / 2;
}

.parent5 {
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: 4fr 1fr;
  grid-column-gap: 0px;
  grid-row-gap: 0px;
}

.div5-1 {
  grid-area: 1 / 1 / 2 / 2;
}
.div5-2 {
  grid-area: 2 / 1 / 3 / 2;
}

/* 글자 움직이기 */
.test {
  perspective: 1000px;
}
.word {
  perspective: 1000px;
}

.word span {
  cursor: pointer;
  display: inline-block;
  font-size: 20px;
  font-weight: bold;
  user-select: none;
  line-height: 0.8;
  margin: 10px 0px 15px 0px;
}

.word span:nth-child(1).active {
  animation: balance 1.5s ease-out;
  transform-origin: bottom left;
}
.word span:nth-child(6).active {
  animation: balance 1.5s ease-out;
  transform-origin: bottom left;
}
@keyframes balance {
  0%,
  100% {
    transform: rotate(0deg);
  }
  30%,
  60% {
    transform: rotate(-45deg);
  }
}

.word span:nth-child(2).active {
  animation: shrinkjump 1s ease-in-out;
  transform-origin: bottom center;
}
.word span:nth-child(7).active {
  animation: shrinkjump 1s ease-in-out;
  transform-origin: bottom center;
}
@keyframes shrinkjump {
  10%,
  35% {
    transform: scale(2, 0.2) translate(0, 0);
  }
  45%,
  50% {
    transform: scale(1) translate(0, -150px);
  }
  80% {
    transform: scale(1) translate(0, 0);
  }
}

.word span:nth-child(3).active {
  animation: falling 2s ease-out;
  transform-origin: bottom center;
}
.word span:nth-child(8).active {
  animation: falling 2s ease-out;
  transform-origin: bottom center;
}
.word span:nth-child(11).active {
  animation: falling 2s ease-out;
  transform-origin: bottom center;
}
@keyframes falling {
  12% {
    transform: rotateX(240deg);
  }
  24% {
    transform: rotateX(150deg);
  }
  36% {
    transform: rotateX(200deg);
  }
  48% {
    transform: rotateX(175deg);
  }
  60%,
  85% {
    transform: rotateX(180deg);
  }
  100% {
    transform: rotateX(0deg);
  }
}

.word span:nth-child(4).active {
  animation: rotate 1s ease-out;
}
.word span:nth-child(9).active {
  animation: rotate 1s ease-out;
}
@keyframes rotate {
  20%,
  80% {
    transform: rotateY(180deg);
  }
  100% {
    transform: rotateY(360deg);
  }
}

.word span:nth-child(5).active {
  animation: falling 1.5s linear;
}
.word span:nth-child(10).active {
  animation: balance1 1.5s linear;
}
@keyframes balance1 {
  0%,
  100% {
    transform: rotate(0deg);
  }
  30%,
  60% {
    transform: rotate(45deg);
  }
}
=======
/* #divdiv {
  display:flex;

  flex-direction: column-reverse;

  overflow-y:auto;

  height:100%;
} */
>>>>>>> f3f240190fdaaee4281d584092885b1410d14527
</style>
