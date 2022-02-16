<template>
  <div class="telestation-container" id="divdiv" style = "color : black; overflow:scroll;">
    <!-- 소원 시작 -->
    <div
      v-show="gameMode === 'text'"
      style="display: flex; flex-direction: column; align-items: center"
    >
      <!-- 키워드 입력 -->
        <div class="progress" style="width:900px; margin-top:10px;">
          <div class="progress-bar" role="progressbar" aria-valuenow="30" aria-valuemin="0" aria-valuemax="30" :style="{width:textingTime*3.4+'%'}">
          </div>
        </div>

      <div class="text-div">
        <div v-show="drawingOrder == 1">
          <img
            src="../../assets/paint/artist.png"
            style="
              width: 80px;
              height: 80px;
              margin: 0 auto;
              margin-bottom: 10px;
            "
          />
          <p class="pstyle">친구에게 전달할 문장을 입력해주세요</p>
          <input
            v-model="keyword"
            class="form-control inline-input"
            type="text"
            required
            placeholder="eX)라이브 방송을 듣는 싸피인"
          />
          <button
            class="paper-btn btn-lg btn-warning writeEnd"
            @click="
              [sendMessageToEveryBody('', 'completed'), changeCompleted()]
            "
            :disabled="myCompleted"
          >
            입력 완료
          </button>
        </div>
        <div v-show="drawingOrder !== 1">
          <div class="receive-draw">
            <img
              :src="recieveDraw"
              alt="받은그림"
              style="border: 1px solid black; width: 700px; height: 500px"
            />
          </div>
          <input
            v-model="keyword"
            class="form-control inline-input"
            type="text"
            required
            placeholder="어떤 그림인지 맞춰주세요!"
          />
          <button
            class="paper-btn btn-lg btn-warning writeEnd"
            @click="
              [sendMessageToEveryBody('', 'completed'), changeCompleted()]
            "
            :disabled="myCompleted"
          >
            입력 완료
          </button>
        </div>
      </div>
    </div>

    <div
      v-show="gameMode === 'drawing'"
      style="display: flex; flex-direction: column; align-items: center"
    >
        <div class="progress" style="width:900px; margin-bottom:40px; margin-top:10px;">
          <div class="progress-bar" role="progressbar" aria-valuenow="100"
          aria-valuemin="0" aria-valuemax="120" :style="{width:drawingTime/1.2+'%'}">
          </div>
        </div>

      <div style="font-size: 35px">{{ receiveKeyword }}</div>
      <div id="gamearea">
        <canvasApi
          ref="canvasApi"
          @canvasCompleted="canvasCompleted"
          @setDrawData="setDrawData"
        ></canvasApi>
      </div>
    </div>

 <!-- 소원 끝 -->


    <div v-show="gameMode ==='album'" style=" width:100%; text-align:center;">
      <h2>{{recieveUsers[0]+"'s Album"}}</h2>
      <!-- <button @click="testfn3()" style="display:block; margin:auto">클릭</button> -->
      <div v-for="(data,index) in recieveAlbum" :key="index" style="display:flex; flex-direction: column; align-items:center;">
        <div style="width:100%;" v-if="index%2 === 0" id="keyword">
          <div class="animate__animated animate__slideInLeft" style="display:flex; flex-direction: column; align-items:center;">
            <div class="d-flex" style="align-self:start;">
              <img src="../../assets/inspector.png" alt="" style="width:50px;">
              <div style="margin:auto 0; font-size:20px; font-weight: bold;">{{recieveUsers[index]}}</div>
            </div>
            <div style="align-self:start; display:flex">
              <div class="speech-bubble" style="font-size: 25px;">
                <p style="margin:5px 15px">{{data}}</p>
              </div>
              <div v-if="index !==0">
                <button @click="bestPick(index)" style="margin-top:20px;">
                  <v-icon color="blue">{{ index === best-1 ? 'mdi-thumb-up' : 'mdi-thumb-up-outline' }}</v-icon>
                </button>
                <button @click="worstPick(index)">
                  <v-icon color="red">{{ index === worst-1 ? 'mdi-thumb-down' : 'mdi-thumb-down-outline' }}</v-icon>
                </button>             
              </div>
            </div>
          </div>
        </div>
        <div v-else id="draw" style="align-self:end;">
          <div class="animate__animated animate__slideInRight" style="display:flex; flex-direction: column; align-items:center;">
            <div class="d-flex flex" style="align-self:end;">
              <div style="margin:auto 3px; font-size:20px; font-weight: bold;">{{recieveUsers[index]}}</div>
              <img src="../../assets/artist.png" alt="" style="width:50px;">
            </div>
              <div class="mt-3 me-3" style="display:flex; flex-direction: column; align-items:center;">
                <img :src="data" alt="" style="width:400px; height:285px;">
                <div>
                  <button @click="bestPick(index)">
                    <v-icon color="blue">{{ index === best-1 ? 'mdi-thumb-up' : 'mdi-thumb-up-outline' }}</v-icon>
                  </button>
                  <button @click="worstPick(index)">
                    <v-icon color="red">{{ index === worst-1 ? 'mdi-thumb-down' : 'mdi-thumb-down-outline' }}</v-icon>
                  </button>
                </div>
              </div>
          </div>
        </div>
      </div>
      <div id="next">
        <button style="display:block; margin:auto; font-size:25px; background-color:rgb(1, 215, 236)" v-show="isRoomMaker && round<personnel" @click="sendMessageToEveryBody('','nextAlbum') ">다음 앨범</button>
        <button style="display:block; margin:auto; font-size:25px; background-color:rgb(1, 215, 236)" v-show="isRoomMaker && round===personnel" @click="sendMessageToEveryBody('','nextAlbum')">결과보기</button>
      </div>
    </div>
    <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
    <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
    <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  게임 모드 === best 시작    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
    <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
    <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->

    <div v-show="gameMode === 'best'" class="modeBestWorst">
      <div class="worst">
        <span>B</span><span>e</span><span>s</span><span>t</span><span>P</span><span>l</span><span>a</span><span>y</span><span>e</span><span>r</span>
      </div>
      <audio v-if="gameMode === 'best'" autoplay onloadstart="this.volume=0.3" src="../../assets/telestation_audio/등장중일때.mp3" ></audio>
      <canvas id="canvas"></canvas>
      <div class="parent1">
        <div class="div1-1">
          <div class="parent2">
            <div class="div2-1 paper-input animate__animated animate__animated animate__bounceInDown">
              <div class="">
                <p class="animate__animated animate__pulse animate__infinite" style="text-align: center; margin: 0px 0px 0px 0px; font-size: 35px; font-style: italic; font-weight: bold;">
                  - {{bestPlayer}} -
                </p>
              </div>
            </div>
            <div class="div2-2">
              <div class="parent3">
                <div class="div3-1 animate__animated animate__bounceInLeft paper-input" v-if="bestVideo" style="display:flex; flex-direction: column; justify-content: center;">
                  <user-video :best-video="bestVideo" />
                </div>
                <div class="div3-2 animate__animated animate__bounceInRight paper-input" style="display: flex; flex-direction: column; justify-content: center;">




                  <div class="parent4" v-show="bestResultMode === 1">
                    <div class="div4-1 speech-bubble-left" style="display:flex; flex-direction: column; justify-content: center;"><span>{{ bestPreData }}</span></div>
                    <div class="div4-2 speech-bubble-right"><img :src="bestData" alt="" style="float:right; width:350px; height:250px;"/></div>
                  </div>






                  <div class="parent5" v-show="bestResultMode === 2">
                    <div class="div5-1 speech-bubble-left"><img :src="bestData" alt="" style=" width:350px; height:250px;" /></div>
                    <div class="div5-2" style="float:right;"><span class="speech-bubble-right" style="float:right">{{ bestPreData }}</span></div>
                  </div>





                </div>
              </div>
            </div>
            <div class="div2-3" style="overflow: hidden">
              <button class="animate__animated animate__bounceInUp" v-show="isRoomMaker" @click="sendMessageToEveryBody('', 'worst')">worst보러가기</button>
            </div>
          </div>
        </div>
      </div>
    </div>


    <div v-show="gameMode === 'worst'" class="modeBestWorst"  style="height: 680px;">
      <div class="word">
        <span>W</span><span>o</span><span>r</span><span>s</span><span>t</span><span>P</span><span>l</span><span>a</span><span>y</span><span>e</span><span>r</span>
      </div>
      <canvas id="canvas"></canvas>
      <div class="parent1" style="height: 630px">
        <div class="div1-1" style="height: 100%">
          <div class="parent2" style="max-height: 640px">
            <div class="div2-1 paper-input animate__animated animate__animated animate__bounceInDown">
              <div>
                <h2 class="animate__animated animate__pulse animate__infinite"
                  style=" text-align: center; margin: 0px 0px 0px 0px; font-size: 35px; font-style: italic; font-weight: bold;">
                  - {{worstPlayer}} -
                </h2>
              </div>
            </div>
            <div class="div2-2">
              <div class="parent3">
                <div class="div3-1 animate__animated animate__bounceInLeft paper-input" v-if="worstVideo" style="display:flex; flex-direction: column; justify-content: center;">
                  <user-video :worst-video="worstVideo" />
                </div>
                <div class="div3-2 animate__animated animate__bounceInRight paper-input" style=" display: flex; flex-direction: column; justify-content: center;">
                   <div class="parent4" v-show="worstResultMode === 1">
                    <div class="div4-1" style="display:flex; flex-direction: column;">
                      <div style=" vertical-align:middle">
                      <img src="../../assets/inspector.png" alt="" style="width:50px; float:left;">
                      <span style="margin:auto 3px; font-size:20px; font-weight: bold; float:left;">{{ preWorstPlayer }}</span>
                      </div>
                      <div class=" speech-bubble-left" style="display:flex; flex-direction: column; justify-content: center;">
                      <div style="margin:auto 0; font-size:20px; font-weight: bold;">{{ worstPreData }}</div>
                      </div>
                    </div>
                    
                    <div class="div4-2" style="display:flex; flex-direction: column;">
                      <div>
                        <img src="../../assets/artist.png" alt="" style="width:50px; float:right;">
                        <div style="margin:auto 3px; font-size:20px; font-weight: bold; float:right; ">{{worstPlayer}}</div>
                      </div>
                      <div class="speech-bubble-right" style=""><img :src="worstData" alt="" style="float:right; width:350px; height:250px;"/></div>
                    </div>
                  </div>

                  <div class="parent5" v-show="worstResultMode === 2">
                    <div class="div5-1" style="display:flex; flex-direction: column;">
                      <div style="display: flex; align-items: center;">
                      <img src="../../assets/inspector.png" alt="" style="width:50px; float:left;">
                      <span style="margin:auto 3px; font-size:20px; font-weight: bold; float:left;">{{ preWorstPlayer }}</span>
                      </div>
                        <div class="speech-bubble-left" style=""><img :src="worstData" alt="" style="float:right; width:350px; height:250px;"/></div>
                      </div>

                    <div class="div5-2" style="display:flex; flex-direction: column;">
                      <div style="display: flex; align-items: center;">
                        <div style="margin:auto 3px; font-size:20px; font-weight: bold; margin-left: uto;">{{worstPlayer}}</div>
                        <img src="../../assets/artist.png" alt="" style="width:50px; margin-left: auto;">
                      </div>
                      <div>
                        <span class="speech-bubble-right" style="float:right">{{ worstPreData }}</span>
                      </div>
                      </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@  버튼 @@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
            <div class="div2-3" style="display: flex; justify-content: center;">
              <button v-if="isRoomMaker" class="paper-btn" style="width:100%;" @click="sendMessageToEveryBody('','replay')">
                <span>게임 선택하기</span>
              </button>
              <button class="paper-btn btn-secondary" style="width:100%" @click="leaveRoom()">
                <span>방 나가기</span>
              </button>
            </div>
            <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
          </div>
        </div>
      </div>
    </div>
    <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
    <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
    <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
    <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
    <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
  </div>
</template>
<script type="module" src="https://unpkg.com/wired-elements/lib/wired-slider.js?module"></script>

<script>
import axios from 'axios';
import {mapState} from 'vuex';
import CanvasApi from '@/components/CanvasApi';
import UserVideo from '@/components/Video/UserVideo';
import 'animate.css';
import { WiredSlider } from 'wired-elements/lib/wired-slider.js';

export default {
  name: 'Telestation',
  props:{
    streamManager: Object,
    rules: Object,
    gameRes: Object,
  },
  components:{
    WiredSlider,
    CanvasApi,
    UserVideo
  },
  data () {
		return {
			keyword: '',
      draw:'',
      // 타이머
      drawingTime:this.rules.drawingTime,
      textingTime:this.rules.textingTime,
      voteTime: this.rules.voteTime,
      drawingEnabled: false,
      textingEnabled: false,
      votingEnabled: false,

<<<<<<< HEAD
      gameMode: "worst",
=======
      gameMode: "drawing",
>>>>>>> 4227706a7c5a347ec1ccdd935d26ee46f0f9fd13
      
      drawingOrder:1, /* 라운드 */
      completedPlayers:0, /* 그림 다 그렸거나 or 키워드 입력완료 누른 플레이어 수 */
      myCompleted: false,
      readyPlayers:0, /* 전 라운드에서 키워드나 그림정보를 웹소켓으로 받은 플레이어 수*/
      personnel:0, //인원수
      participant: new Map(), // 참가자들
      targetUser: '', // 웹소켓 받는 사람
      receiveKeyword:'', // 받은 키워드
      recieveDraw:'', // 받은 그림
      recieveAlbum: null,
      recieveUsers: [],
      round:0,
      gameId: this.gameRes.playGameId,
      dataIndex:0, // 앨범 번호
      worst:0,
      best:0,
      worstPlayer:'강광은',
      worstVideo:null,
      bestPlayer:'강광은',
      bestVideo:null,
      worstResultMode:2, //1 이면 키워드 사진 2이면 사진 키워드
      bestResultMode:0,
<<<<<<< HEAD
      worstPreData:'asdf',
      worstData:'data:image/jpeg;base64,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',
      bestPreData:'asdf',
      bestData:'data:image/jpeg;base64,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',
      //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
      //@@@@@@@@@@@@@@@@@  광은 투표 결과에 이름 넣기  @@@@@@@@@@@@@@
      //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
      preBestPlayer:'강광은 이전',
      preWorstPlayer:'강광은 이전',
      //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
      //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
      //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
=======
      worstPreData:'',
      worstData:'',
      bestPreData:'',
      bestData:'',


      
>>>>>>> 4227706a7c5a347ec1ccdd935d26ee46f0f9fd13
		}
	},
  methods: {
    leaveRoom() {
    this.$store.dispatch('leaveSession')
    this.$router.push({name:'Lobby', params: { sendUserEmail: this.sendUserEmail}})
    },
    backToRoom() {
      // this.sendMessageToEveryBody('', 'backToRoom')
      this.keyword= '',
      this.draw='',
      this.round=0,
      this.gameMode= "text",
      this.session.off('signal:keyword')
      this.session.off('signal:draw')
      this.session.off('signal:completed')
      this.session.off('signal:ready')
      this.session.off('signal:nextAlbum')
      this.session.off('signal:result')
      this.session.off('signal:worst')
      this.sendMessageToEveryBody('', 'initTelestation')
    },
    testfn3(){
      this.removeKeyword()
      this.removeDraw()
      this.testfn()
      setTimeout(() => {
        this.testfn2()
      },2500);
    },
    testfn() {
      var index = 0
      var final = parseInt(this.personnel/2)
      var loading = setInterval(function(){
        if (index === final) {
          clearInterval(loading)
        }
        $('#keyword > div').eq(index).fadeIn(1000);index++
        $('#divdiv').scrollTop($('#divdiv')[0].scrollHeight);
        },5000);
    },
    testfn2() {
      var index1 = 0
      var final1 = parseInt(this.personnel/2)
      var loading1 = setInterval(function(){
        if (index1 === final1) {
          clearInterval(loading1)
        }
        if (index1 === final1-1) {
          setTimeout(() =>{
            $('#next').fadeIn(1000)
            $('#divdiv').scrollTop($('#divdiv')[0].scrollHeight);
          },2500)
        }
        $('#draw > div').eq(index1).fadeIn(1000);index1++
        $('#divdiv').scrollTop($('#divdiv')[0].scrollHeight);
        },5000);
    },
    removeKeyword() {
      console.log('리무브 들어왔니?ㄴ')
      $('#keyword > div').hide();
      $('#next').hide();
    },
    removeDraw() {
      $('#draw > div').hide();
    },
    getUsers() { // 참가자 닉네임과 connectinId 딕셔너리화 for(웹소켓)
      let myNickName = JSON.parse(this.publisher.stream.connection.data)
      this.participant.set(myNickName.clientData, this.publisher.stream.connection)
      for (let index = 0; index < this.subscribers.length; index++) {
        let nickName = JSON.parse(this.subscribers[index].stream.connection.data)
        this.participant.set(nickName.clientData, this.subscribers[index].stream.connection)
      }
    },
    sendMessageToEveryBody(data, type) { // 참가자 전원 웹소켓
      this.session.signal({
          data: data,
          to: [],
          type: type
      })
      .then(() => {
      })
      .catch(error => {
          console.error(error);
      })
    },
    sendMessageToTargetUser(data, type, targetUser) { // 특정 참가자 웹소켓
      this.session.signal({
          data: data,
          to: [targetUser],
          type: type
      })
      .then(() => {})
      .catch(error => {
          console.error(error);
      })
    },
    // 타이머 함수 모음 시작
    startDrawing() { //Drawing 타이머 on
      this.drawingEnabled = true;
    },
    startTexting() { //Texting 타이머 on
      this.textingEnabled = true;
    },
    pauseDrawing(){ //Drawing 타이머 off
      this.drawingEnabled = false;
    },
    pauseTexting(){ //Texting 타이머 off
      this.textingEnabled = false;
    },
    worstPick(index) {
      this.worst = index +1
      console.log(this.worst)
    },
    bestPick(index) {
      this.best = index +1
      console.log(this.best)
    },
    changeCompleted() {
      this.myCompleted = true
    },
    canvasCompleted(){
      this.sendMessageToEveryBody('','completed')
      console.log('캔버스에서 완료 버튼 눌렀나 ?')
    },
    setDrawData(imageData){
      console.log('그림데이터 저장완료@@@@@@@@@@@@')
      this.draw = imageData
    },
    endTextRound() { // Texting 라운드 끝
      this.pauseTexting()
      this.textingTime = this.rules.textingTime
      axios({
        method:'POST',
        url: '/api/telestations/saveData',
        data: {
          data: this.keyword,
          userNickname: this.myUserName,
          roomCode: this.mySessionId,
          drawingOrder: this.drawingOrder,
          personnel: this.personnel,
        }
      })
      .then((res)=> {
        this.targetUser = res.data.userNickname
        this.keyword = ''
        this.sendMessageToTargetUser(JSON.stringify(res.data), "keyword", this.participant.get(this.targetUser))
      })
      .catch((err)=> {
        console.log(err)
        console.log('@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@')
      })
    },
    endDrawRound() { // Drawing 라운드 끝
      this.pauseDrawing()
      this.drawingTime = this.rules.drawingTime
      console.log('-------------------------------------------------------------------------------')
      console.log(this.drawingTime, this.rules.drawingTime)
      console.log('-------------------------------------------------------------------------------')
      this.$refs.canvasApi.saveClick()
      console.log(this.drawingOrder)
      console.log(this.myUserName,this.mySessionId,this.drawingOrder,this.personnel,this.draw)
      axios({
        method:'POST',
        url: '/api/telestations/saveData',
        data: {
          data: this.draw,
          userNickname: this.myUserName,
          roomCode: this.mySessionId,
          drawingOrder: this.drawingOrder,
          personnel: this.personnel,
        }
      })
      .then((res)=> {
        this.targetUser = res.data.userNickname
        this.$refs.canvasApi.resetAll()
        this.sendMessageToTargetUser(JSON.stringify(res.data), "draw", this.participant.get(this.targetUser))
      })
    },
    startTextRound() {
      this.myCompleted = false
      this.drawingOrder++;
      if (this.drawingOrder === this.personnel +1) {
        this.startAlbumRound()
      }else {
      this.gameMode = 'text'
      this.startTexting()
      }
    },
    startDrawRound() {
      this.drawingOrder++
      if (this.drawingOrder === this.personnel +1) {
        this.startAlbumRound()
      }else {
        this.gameMode = 'drawing'
        this.startDrawing()
      }
    },
    startAlbumRound(){
      this.round ++;
      console.log("dataIndex" + this.dataIndex);
      console.log("베슽흐" + this.best);
      console.log("워스트" + this.worst);
      axios({
        method:'POST',
        url: '/api/telestations/showAlbum',
        data: {
          userNickname: this.myUserName,
          gameId: this.gameId,
          round: this.round,
          dataIndex: this.dataIndex,
          worstVote: this.worst,
          bestVote: this.best,
        }
      })
      .then((res)=> {
        if (this.round <= this.personnel) {
          this.gameMode = 'album'     
          this.dataIndex = res.data.dataIndex
          this.recieveAlbum = res.data.dataList
          this.recieveUsers = res.data.userNicknameList
          console.log("this.dataIndex"+this.dataIndex);
          console.log("this.recieveAlbum"+this.recieveAlbum);
          this.worst = 0
          this.best = 0
        }else {
          this.bestPlayer = res.data.best.nickname
          this.worstPlayer = res.data.worst.nickname
          this.worstPreData = res.data.worst.preData
          this.worstData = res.data.worst.data
          this.bestPreData = res.data.best.preData
          this.bestData = res.data.best.data
          //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
          //@@@@@@@@@@@@@@@@@  광은 투표 결과에 이름 넣기  @@@@@@@@@@@@@@
          //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
          this.preBestPlayer = res.data.best.preNickname
          this.preWorstPlayer = res.data.worst.preNickname
          //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
          //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
          //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
          if (res.data.best.preDrawingOrder%2 === 1) {
            this.bestResultMode = 1
          }else {
            this.bestResultMode = 2
          }
          if (res.data.worst.preDrawingOrder%2 === 1) {
            this.worstResultMode = 1
          }else {
            this.worstResultMode = 2
          }
          this.gameMode='best'
          this.testfn3()
        }
      })
      .catch((err)=> {
        console.log(err, '앨범에러')
      })
    },
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
            }
            else if (value === 0) {
              this.endDrawRound()
            }
        },
        immediate: false
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
            }
            else if (value === 0) {
              this.endTextRound()
            }
        },
        immediate: false
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
            }
            else if (value === 0) {
              this.endTextRound()
            }
        },
        immediate: false
    },

    completedPlayers: { // 제출완료
      handler(value) {
        if (value === this.personnel && this.gameMode==='text') {
          this.completedPlayers = 0
          this.endTextRound()
        }
        else if (value === this.personnel && this.gameMode==='drawing') {
          this.completedPlayers = 0
          this.endDrawRound()
        }
      }
    },
    readyPlayers: { // 웹소켓 수신 완료 => 다음라운드로 가기위한 변수
      handler(value) {
        if (value === this.personnel && this.gameMode==='text') {
          this.readyPlayers = 0
          this.startDrawRound()
        }
        else if (value === this.personnel && this.gameMode==='drawing') {
          this.readyPlayers = 0
          this.startTextRound()
        }
      }
    },
    bestPlayer: {
      handler() {
        for (let index = 0; index < this.subscribers.length; index++) {
          let nickName = JSON.parse(this.subscribers[index].stream.connection.data)
          if (this.bestPlayer == nickName.clientData) {
            this.bestVideo = this.subscribers[index]
            console.log(nickName,this.bestVideo)
          }
        }
      }
    },
    worstPlayer: {
      handler() {
        for (let index = 0; index < this.subscribers.length; index++) {
          let nickName = JSON.parse(this.subscribers[index].stream.connection.data)
          if (this.worstPlayer == nickName.clientData) {
            this.worstVideo = this.subscribers[index]
            console.log(nickName,this.bestVideo)
          }
        }
      }
    },
  },
  mounted() {
    // $('#divdiv').scrollTop($('divdiv').prop('scrollHeight'));
    this.startTexting()
    this.getUsers()
    this.session.on('signal:keyword', (event) => { // 입력한 키워드 백에 보내기
      let data = JSON.parse(event.data)
      this.receiveKeyword = data.data
      this.sendMessageToEveryBody('', "ready")
    })
    this.session.on('signal:draw', (event) => { // 그린 그림 백에 보내기 
      let data = JSON.parse(event.data)
      this.recieveDraw = data.data
      this.sendMessageToEveryBody('', "ready")
    })
    this.session.on('signal:completed', (event) => { // 입력버튼 누르면 완료된 사람 +1
      console.log(event.data)
      this.completedPlayers++
    })
    this.session.on('signal:ready', (event) => { // 백에서 데이터 받아서 웹소켓 통신하고 나면 +1
      console.log(event.data)
      this.readyPlayers++
    })
    this.session.on('signal:nextAlbum', (event) => { // 그린 그림 백에 보내기 
      console.log(event.data)
      this.startAlbumRound()
    })
    this.session.on('signal:result', (event) => { // 그린 그림 백에 보내기 
      console.log(event.data)
      this.startResult()
    })
    this.session.on('signal:worst', (event) => { // 그린 그림 백에 보내기 
      console.log(event.data)
      this.bestVideo = null
      this.gameMode = 'worst'
    })
    this.session.on('signal:replay', (event) => { // 그린 그림 백에 보내기 
      this.worstVideo = null
<<<<<<< HEAD
      this.backToRoom();
=======
      this.backToRoom()
>>>>>>> 4227706a7c5a347ec1ccdd935d26ee46f0f9fd13
    })
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@     꽃가루날리기 + a @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@     꽃가루날리기 + a @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@     꽃가루날리기 + a @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
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
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
  },
  created() {
    this.personnel = this.subscribers.length
  }
}


</script>

<style scoped>
.telestation-container {
  height: 100%;
  background-color: rgb(245, 245, 245);
}
.completed {
  background: black;
  cursor: not-allowed;
}
#keyword > div {
  transition: all 2s;
}
#divdiv{ -ms-overflow-style: none; }
#divdiv::-webkit-scrollbar{ display:none; }

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
	border-radius: .4em;
  margin-top:20px;
  margin-left:15px;
  min-width: 100px;
}

.speech-bubble:after {
	content: '';
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
.pstyle {
  font-size: 25px;
  font-weight: bold;
  text-align: center;
}
.inline-input {
  display: inline-block;
  margin-right: 10px;
  width: 700px;
  height: 40px;
  font-size: 18px;
}
.text-div {
  height: 600px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.receive-draw {
  width: 800px;
  height: 533px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
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
/* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 광은 수정 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */
.modeBestWorst {
  display: flex;
  flex-direction: column;
  align-items: center;
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
  left: 30%;
  width: 0;
  height: 0;
  border: 10px solid transparent;
  border-bottom-color: #ffffff;
  border-top: 0;
  border-left: 0;
  margin-left: -10px;
  margin-top: -10px;
}

.speech-bubble-right {
  position: relative;
  background: rgb(254, 240, 27);
  border-radius: 0.4em;
  margin-top: 20px;
  margin-left: auto;
  margin-right: 10px;
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
/* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */
.parent1 {
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: 1fr;
  grid-column-gap: 100px;
  grid-row-gap: 0px;
  width: 100%;
  /* height: 100%; */
  /* background-color: green */
}

.div1-1 {
  grid-area: 1 / 1 / 2 / 2;
  /* background-color: rgb(77, 0, 0); */
  height: 650px;
}

.parent2 {
  display: grid;
  grid-template-columns: 0 1fr;
  grid-template-rows: 1fr 7fr 1fr;
  grid-column-gap: 0px;
  grid-row-gap: 0px;
  /* padding-top: 20px; */
  height: 650px;
  /* background-color: blanchedalmond; */
}
.div2-1 {
  margin: auto;
  padding: 7px 70px 7px 70px;
  
  grid-area: 1 / 2 / 2 / 3;
  /* background-color: white; */

  /* background-color: lightsalmon; */
}
.div2-2 {
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
  /* background-color: rgba(3, 3, 3, 0.8); */
}
.div3-1 {
  grid-area: 1 / 1 / 2 / 2;
  margin-left: 20px;
  /* background-color: rgba(3, 3, 3, 0.8); */
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
  /* grid-template-rows: 3fr 1fr; */
  grid-column-gap: 0px;
  grid-row-gap: 0px;
}

.div5-1 {
  grid-area: 1 / 1 / 2 / 2;
  margin-bottom: 20px;
}
.div5-2 {
  grid-area: 2 / 1 / 3 / 2;
  padding-bottom: 20px;
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
</style>
