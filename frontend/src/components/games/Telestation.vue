<template>
  <div class="telestation-container" :class="{'overFlowScroll': this.gameMode==='album'}" id="divdiv">
    <div
      v-show="gameMode === 'text'"
      style="display: flex; flex-direction: column; align-items: center"
    >
        <div class="progress" style="width:900px; margin-top:10px;">
          <div class="progress-bar bar danger" role="progressbar" aria-valuenow="30" aria-valuemin="0" aria-valuemax="30" :style="{width:textingTime*3.4+'%'}">
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
            placeholder="ex)라이브 방송을 듣는 싸피인"
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
        <div class="progress" style="width:900px; margin-top:10px; margin-bottom:15px;">
          <div class="progress-bar bar danger" role="progressbar" aria-valuenow="100"
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

    <div v-show="gameMode ==='album'" style=" width:100%; text-align:center;">
      <h2>{{recieveUsers[0]+"'s Album"}}</h2>
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

    <div v-show="gameMode === 'best'" class="modeBestWorst"  style="height: 680px;">
      <canvas id="canvas"></canvas>
      <div class="word">
        <span>B</span><span>e</span><span>s</span><span>t</span><span>P</span><span>l</span><span>a</span><span>y</span><span>e</span><span>r</span>
      </div>
      <div class="parent1" style="height: 630px">
        <div class="div1-1" style="height: 100%">
          <div class="parent2" style="max-height: 640px">
            <div class="div2-1 paper-input animate__animated animate__animated animate__bounceInDown">
              <div>
                <h2 class="animate__animated animate__pulse animate__infinite" style=" text-align: center; margin: 0px 0px 0px 0px; font-size: 35px; font-style: italic; font-weight: bold;">
                  - {{bestPlayer}} -
                </h2>
              </div>
            </div>
            <div class="div2-2">
              <div class="parent3">
                <div class="div3-1 animate__animated animate__bounceInLeft paper-input" v-if="bestVideo" style="display:flex; flex-direction: column; justify-content: center;">
                  <user-video :best-video="bestVideo" />
                </div>
                <div class="div3-2 animate__animated animate__bounceInRight paper-input" style=" display: flex; flex-direction: column; justify-content: center;">
                   <div class="parent4" v-show="bestResultMode === 1">
                    <div class="div4-1" style="display:flex; flex-direction: column;">
                      <div style="display: flex; align-items: center;">
                      <img src="../../assets/inspector.png" alt="" style="width:50px; float:left;">
                      <span style="margin:auto 3px; font-size:20px; font-weight: bold; float:left;">{{ preBestPlayer }}</span>
                      </div>
                      <div class="speech-bubble-left2" style="text-align: center;">
                        <span style="font-weight: bold; font-size:120%;">{{ bestPreData }}</span>
                      </div>
                    </div>
                    <div class="div4-2" style="display:flex; flex-direction: column;">
                      <div style="display: flex; align-items: center;">
                        <div style="margin:auto 3px; font-size:20px; font-weight: bold; float:right; margin-left:auto; ">{{bestPlayer}}</div>
                        <img src="../../assets/artist.png" alt="" style="width:50px; float:right;">
                      </div>
                      <div class="speech-bubble-right" style=""><img :src="bestData" alt="" style="float:right; width:350px; height:250px;"/></div>
                    </div>
                  </div>
                  <div class="parent5" v-show="bestResultMode === 2">
                    <div class="div5-1" style="display:flex; flex-direction: column;">
                      <div style="display: flex; align-items: center;">
                        <img src="../../assets/inspector.png" alt="" style="width:50px; float:left;">
                        <span style="margin:auto 3px; font-size:20px; font-weight: bold; float:left;">{{ preBestPlayer }}</span>
                      </div>
                        <div class="speech-bubble-left" style="display:flex; flex-direction: column; justify-content: center;">
                          <img :src="bestPreData" alt="" style="float:right; width:350px; height:250px;"/></div>
                        </div>
                      <div class="div5-2" style="display:flex; flex-direction: column;">
                        <div style="display: flex; align-items: center; ">
                            <div style="margin:auto 3px; font-size:20px; font-weight: bold; margin-right: 0px; margin-left:auto;">{{bestPlayer}}</div>
                          <img src="../../assets/artist.png" alt="" style="width:50px;">
                        </div>
                      <div>
                        <span class="speech-bubble-right" style="float:right; font-weight: bold; font-size:120%; text-align: center;">{{ bestData }}</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="div2-3" style="overflow: hidden; margin-top: 10px;">
              <button class="animate__animated animate__bounceInUp paper-btn" style="width:100%;" v-show="isRoomMaker" @click="sendMessageToEveryBody('', 'worst')">worst보러가기</button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div v-show="gameMode === 'worst'" class="modeBestWorst"  style="height: 680px;">
      <div class="word">
        <span>W</span><span>o</span><span>r</span><span>s</span><span>t</span><span>P</span><span>l</span><span>a</span><span>y</span><span>e</span><span>r</span>
      </div>
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
                      <div style="display: flex; align-items: center;">
                      <img src="../../assets/inspector.png" alt="" style="width:50px; float:left;">
                      <span style="margin:auto 3px; font-size:20px; font-weight: bold; float:left;">{{ preWorstPlayer }}</span>
                      </div>
                      <div class="speech-bubble-left2" style="text-align: center;">
                        <span style="font-weight: bold; font-size:120%; ">{{ worstPreData }}</span>
                      </div>
                    </div>
                    
                    <div class="div4-2" style="display:flex; flex-direction: column;">
                      <div style="display: flex; align-items: center;">
                        <div style="margin:auto 3px; font-size:20px; font-weight: bold; float:right; margin-left:auto; ">{{worstPlayer}}</div>
                        <img src="../../assets/artist.png" alt="" style="width:50px; float:right;">
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
                        <div class="speech-bubble-left" style="display:flex; flex-direction: column; justify-content: center;">
                          <img :src="worstPreData" alt="" style="float:right; width:350px; height:250px;"/></div>
                      </div>
                    <div class="div5-2" style="display:flex; flex-direction: column;">
                      <div style="display: flex; align-items: center; ">
                          <div style="margin:auto 3px; font-size:20px; font-weight: bold; margin-right: 0px; margin-left:auto;">{{worstPlayer}}</div>
                        <img src="../../assets/artist.png" alt="" style="width:50px;">
                      </div>
                      <div>
                        <span class="speech-bubble-right" style="float:right; font-weight: bold; font-size:120%; text-align: center;">{{ worstData }}</span>
                      </div>
                      </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="div2-3" style="display: flex; justify-content: center;">
              <button v-if="isRoomMaker" class="paper-btn" style="width:100%;" @click="sendMessageToEveryBody('','replay')"><span>게임 선택하기</span></button>
              <button class="paper-btn btn-secondary" style="width:100%" @click="leaveRoom()"><span>방 나가기</span></button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script type="" src=""></script>
<script>
import axios from 'axios';
import {mapState} from 'vuex';
import CanvasApi from '@/components/CanvasApi';
import UserVideo from '@/components/Video/UserVideo';
import 'animate.css';

export default {
  name: 'Telestation',
  props:{
    streamManager: Object,
    rules: Object,
    gameRes: Object,
  },
  components:{
    CanvasApi,
    UserVideo,
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

      gameMode: "text",
      
      drawingOrder:1, /* 라운드 */
      completedPlayers:0, /* 그림 다 그렸거나 or 키워드 입력완료 누른 플레이어 수 */
      myCompleted: false,
      readyPlayers:0, /* 전 라운드에서 키워드나 그림정보를 웹소켓으로 받은 플레이어 수*/
      personnel:0, //인원수
      participant: new Map(), // 참가자들
      targetUser: '', // 웹소켓 받는 사람
      receiveKeyword:'', // 받은 키워드
      recieveDraw:'', // 받은 그림
      recieveAlbum: ['0','0','0','0','0','0','0','0'],
      recieveUsers: ['0','0','0','0','0','0','0','0'],
      round:0,
      gameId: this.gameRes.playGameId,
      dataIndex:0, // 앨범 번호
      worst:0,
      best:0,
      worstPlayer:'',
      worstVideo:null,
      bestPlayer:'',
      bestVideo:null,
      worstResultMode:0, //1 이면 키워드 사진 2이면 사진 키워드
      bestResultMode:0,
      worstPreData:'',
      worstData:'',
      bestPreData:'',
      bestData:'',
      preBestPlayer:'',
      preWorstPlayer:'',
		}
	},
  methods: {
    leaveRoom() {
    this.worstPlayer = '',
    this.$store.dispatch('leaveSession')
    this.$router.push({name:'Lobby', params: { sendUserEmail: this.sendUserEmail}})
    },
    backToRoom() {
      this.keyword= '',
      this.draw='',
      this.gameMode= "text",
      this.drawingOrder=1,
      this.completedPlayers=0,
      this.myCompleted= false,
      this.readyPlayers=0,
      this.personnel=0,
      this.participant= new Map(),
      this.targetUser= '',
      this.receiveKeyword='',
      this.recieveDraw='',
      this.recieveAlbum= ['0','0','0','0','0','0','0','0'],
      this.recieveUsers= ['0','0','0','0','0','0','0','0'],
      this.round=0,
      this.gameId= this.gameRes.playGameId,
      this.dataIndex=0,
      this.worst=0,
      this.best=0,
      this.worstPlayer='',
      this.worstVideo=null,
      this.bestPlayer='',
      this.bestVideo=null,
      this.worstResultMode=0,
      this.bestResultMode=0,
      this.worstPreData='',
      this.worstData='',
      this.bestPreData='',
      this.bestData='',
      this.session.off('signal:keyword')
      this.session.off('signal:draw')
      this.session.off('signal:completed')
      this.session.off('signal:readyToNext')
      this.session.off('signal:nextAlbum')
      this.session.off('signal:result')
      this.session.off('signal:worst')
      this.sendMessageToEveryBody('', 'initTelestation')
    },
    testfn3(){
      this.removeKeyword()
      this.removeDraw()
      this.testfn2()
    },
    testfn2() {
      var index1 = 0
      var index2 = 0
      var index3 = 0
      var final1 = this.personnel
      var loading1 = setInterval(function(){
        if (index1 === final1) {
            $('#next').fadeIn(1000)
            $('#divdiv').scrollTop($('#divdiv')[0].scrollHeight);
          clearInterval(loading1)
        }else if (index1 % 2 === 0){
          $('#keyword > div').eq(index2).fadeIn(1000);
          index1++;
          index2++;
          $('#divdiv').scrollTop($('#divdiv')[0].scrollHeight);
        }else {
          $('#draw > div').eq(index3).fadeIn(1000);
          index1++
          index3++
          $('#divdiv').scrollTop($('#divdiv')[0].scrollHeight);
        }
        },2000);
    },
    removeKeyword() {
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
      })
    },
    endDrawRound() { // Drawing 라운드 끝
      this.pauseDrawing()
      this.drawingTime = this.rules.drawingTime
      this.$refs.canvasApi.saveClick()
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
        console.log('axios then', res.data)
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
          this.worst = 0
          this.best = 0
        }else {
          this.bestPlayer = res.data.best.nickname
          this.worstPlayer = res.data.worst.nickname
          this.worstPreData = res.data.worst.preData
          this.worstData = res.data.worst.data
          this.bestPreData = res.data.best.preData
          this.bestData = res.data.best.data
          this.preBestPlayer = res.data.best.preNickname
          this.preWorstPlayer = res.data.worst.preNickname
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
        }
        this.testfn3()
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
    this.startTexting()
    this.getUsers()
    this.session.on('signal:keyword', (event) => { // 입력한 키워드 백에 보내기
      let data = JSON.parse(event.data)
      this.receiveKeyword = data.data
      this.sendMessageToEveryBody('', "readyToNext")
    })
    this.session.on('signal:draw', (event) => { // 그린 그림 백에 보내기 
      let data = JSON.parse(event.data)
      this.recieveDraw = data.data
      console.log('여기는 드로우')
      console.log(this.recieveDraw)
      this.sendMessageToEveryBody('', "readyToNext")
    })
    this.session.on('signal:completed', (event) => { // 입력버튼 누르면 완료된 사람 +1
      console.log(event.data)
      this.completedPlayers++
    })
    this.session.on('signal:readyToNext', (event) => { // 백에서 데이터 받아서 웹소켓 통신하고 나면 +1
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
      this.backToRoom();
    })
  },
  created() {
    this.personnel = this.subscribers.length
  },
}
</script>

<style scoped>
.telestation-container {
  height: 100%;
  overflow: hidden;
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

.modeBestWorst {
  display: flex;
  flex-direction: column;
  align-items: center;

}

.speech-bubble-left {
  position: relative;
  background: #ffffff;
  border-radius: 0.4em;
  margin-top: 15px;
  margin-left: 15px;
  margin-right: auto;
  min-width: 100px;
  padding: 10px 10px 10px 10px;
}

.speech-bubble-left:after {
  content: "";
  position: absolute;
  top: 0;
  left: 30px;
  width: 0;
  height: 0;
  border: 10px solid transparent;
  border-bottom-color: #ffffff;
  border-top: 0;
  border-left: 0;
  margin-left: -10px;
  margin-top: -8px;
}
.speech-bubble-left2 {
  position: relative;
  background: #ffffff;
  border-radius: 0.4em;
  margin-top: 15px;
  margin-left: 15px;
  margin-right: auto;
  min-width: 100px;
  padding: 10px 10px 10px 10px;
}

.speech-bubble-left2:after {
  content: "";
  position: absolute;
  top: 0;
  left: 30px;
  width: 0;
  height: 0;
  border: 10px solid transparent;
  border-bottom-color: #ffffff;
  border-top: 0;
  border-left: 0;
  margin-left: -10px;
  margin-top: -8px;
}

.speech-bubble-right {
  position: relative;
  background: rgb(254, 240, 27);
  border-radius: 0.4em;
  margin-top: 15px;
  margin-left: auto;
  margin-right: 10px;
  min-width: 100px;
  padding: 10px 10px 10px 10px;
}

.speech-bubble-right:after {
  content: "";
  position: absolute;
  top: 0;
  right: 20px;
  width: 0;
  height: 0;
  border: 10px solid transparent;
  border-bottom-color: rgb(254, 240, 27);
  border-top: 0;
  border-left: 0;
  margin-left: -10px;
  margin-top: -8px;
  transform: scaleX(-1);
}

.parent1 {
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: 1fr;
  grid-column-gap: 100px;
  grid-row-gap: 0px;
  width: 100%;

}

.div1-1 {
  grid-area: 1 / 1 / 2 / 2;
  height: 650px;
}

.parent2 {
  display: grid;
  grid-template-columns: 0 1fr;
  grid-template-rows: 1fr 7fr 1fr;
  grid-column-gap: 0px;
  grid-row-gap: 0px;
  height: 650px;
}
.div2-1 {
  margin: auto;
  padding: 7px 70px 7px 70px;
  
  grid-area: 1 / 2 / 2 / 3;
}
.div2-2 {
  grid-area: 2 / 2 / 3 / 3;
}
.div2-3 {
  grid-area: 3 / 2 / 4 / 3;
}
.div2-3 button {
  display: block;
  margin: auto;
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
  max-height: 450px;
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
  grid-template-rows: 3fr 1fr;
  grid-column-gap: 0px;
  grid-row-gap: 0px;
  max-height: 450px;
}

.div5-1 {
  grid-area: 1 / 1 / 2 / 2;
  margin-bottom: 5px;
}
.div5-2 {
  grid-area: 2 / 1 / 3 / 2;
}

.overFlowScroll {
  color : black;
  overflow:scroll;
}
</style>
