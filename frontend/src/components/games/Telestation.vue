<template>
  <div class="telestation-container">
    <h1>텔레스테이션</h1>
    <div v-show="gameMode ==='text'" style="display:flex; flex-direction: column; align-items: center;"> <!-- 키워드 입력 -->
      <div>
        {{textingTime}}
        {{drawingTime}}
        <button @click="startTexting()">시작</button>
        <button @click="pauseTexting()">정지</button>
      </div>
      <div v-show="drawingOrder !== 1" style="width:800px; height: 400px; background-color:white; color:black;">
        <img :src="recieveDraw" alt="받은그림">
      </div>
      <div style="width:800px; text-align:center;">
        <label>키워드</label>
        <input v-model="keyword" class="form-control" type="text" required placeholder="키워드를 입력하세요">
        <br>
      </div>
      <p class="text-center">
      <button class="btn btn-lg btn-success" @click="sendMessageToEveryBody('','completed')">입력 완료</button>
      </p>    
    </div>
    <div  v-show="gameMode ==='drawing'" style="display:flex; flex-direction: column; align-items: center;">
      {{drawingTime}}
      <div id="gamearea">
        <canvasApi ref="canvasApi"></canvasApi>
      </div>
      <p class="text-center">
        <button class="btn btn-lg btn-success" @click="this.$refs.canvasAip.canavasComplete()">입력 완료</button>
      </p> 
      </div>
      <div>{{receiveKeyword}}</div>
    
  </div>
</template>
<script src = "../../assets/js/app.js"></script>
<script>
import axios from 'axios';
import {mapState} from 'vuex';
import CanvasApi from '@/components/CanvasApi';

export default {
  name: 'Telestation',
  props:{
    streamManager: Object,
    rules: Object,
    gameRes: Object,
  },
  components:{
    CanvasApi
  },
  data () {
		return {
			keyword: '',
      draw:'',
      drawingTime:this.rules.drawingTime,
      textingTime:this.rules.textingTime,
      voteTime: this.rules.voteTime,
      drawingEnabled: false,
      textingEnabled: false,
      votingEnabled: false,
      gameMode: "drawing",
      drawingOrder:1, /* 라운드 */
      completedPlayers:0, /* 그림 다 그렸거나 or 키워드 입력완료 누른 플레이어 수 */
      readyPlayers:0, /* 전 라운드에서 키워드나 그림정보를 웹소켓으로 받은 플레이어 수*/
      personnel:3,
      participant: new Map(),
      targetUser: '',
      receiveKeyword:'',
      receiveDraw:'',
      dataIndex:''
		}
	},
  methods: {
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
    startAlbum(){
      this.votingEnabled = true;
    },
    pauseAlbum(){
      this.votingEnabled = false;
    },

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
    canvasComplete(){
      this.sendMessageToEveryBody('','completed')
    },
    setDrawData(imageData){
      this.draw = imageData
    },
    // 캔버스 관련 함수 시작

    //라운드 끝내기& 시작 함수 모음 시작
    endTextRound() { // Texting 라운드 끝
      this.pauseTexting()
      this.textingTime = this.rules.textingTime
      axios({
        method:'POST',
        url: '/api/telestations/InputKeyword',
        data: {
          keyword: this.keyword,
          userNickname: this.myUserName,
          roomCode: this.mySessionId,
          drawingOrder: this.drawingOrder,
          personnel: this.personnel,
        }
      })
      .then((res)=> {
        this.targetUser = res.data.userNickname
        this.sendMessageToTargetUser(JSON.stringify(res.data), "keyword", this.participant.get(this.targetUser))
      })
    },
    endDrawRound() { // Drawing 라운드 끝
      this.pauseDrawing()
      this.DrawingTime = this.rules.DrawingTime
      this.$refs.canvasApi.saveClick()
      // canvas에서 그림 축출해서 this.darw에 저장
      axios({
        method:'POST',
        url: '/api/telestations/InputKeyword',
        data: {
          draw: this.draw,
          userNickname: this.myUserName,
          roomCode: this.mySessionId,
          drawingOrder: this.drawingOrder,
          personnel: this.personnel,
          dataIndex: this.dataIndex,
        }
      })
      .then((res)=> {
        this.targetUser = res.data.userNickname
        this.sendMessageToTargetUser(JSON.stringify(res.data), "draw", this.participant.get(this.targetUser))
      })
    },
    startTextRound() {
      this.sendMessageToEveryBody('', "drawingOrder")
      if (this.drawingOrder === this.personnel +1) {
        this.startAlbum()
      }else {
      this.gameMode = 'text'
      this.startTexting()
      }
    },
    startDrawRound() {
      this.sendMessageToEveryBody('', "drawingOrder")
      if (this.drawingOrder === this.personnel +1) {
        this.startAlbum()
      }else {
        this.gameMode = 'drawing'
        this.startDrawing()
      }
    },
    startAlbum(){
      console.log('앨범라운드')
    },
    //라운드 끝내기& 시작 함수 모음 끝



// this.sendMessageToEveryBody(JSON.stringify(this.gameRes), 'gameRes')
  },
  computed: {
    ...mapState([
    "myUserName",
    "mySessionId",
    "subscribers",
    "publisher",
    "session"
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
            if (value === 0) {
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
            if (value === 0) {
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
        if (value === this.personnel && this.gameMode==='drawing') {
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
        if (value === this.personnel && this.gameMode==='drawing') {
          this.readyPlayers = 0
          this.startTextRound()
        }
      }
    }
  },
  mounted() {
    this.startTexting()
    this.getUsers()
    this.session.on('signal:keyword', (event) => { // 입력한 키워드 백에 보내기
      let data = JSON.parse(event.data)
      this.receiveKeyword = data.keyword
      this.dataIndex = data.dataIndex
      this.sendMessageToEveryBody('', "ready")
    })
    this.session.on('signal:draw', (event) => { // 그린 그림 백에 보내기 
      let data = JSON.parse(event.data)
      this.receiveDraw = data.draw
      this.dataIndex = data.dataIndex
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
    this.session.on('signal:drawingOrder', (event) => { // 각 라운드 시작시 라운드 +1
      console.log(event.data)
      this.drawingOrder++
    })
  

    // this.session.on('signal:gameRes', (event)=>{
    //   console.log(event.data)
    //   this.gameRes = JSON.parse(event.data)
    // })
  }
}


</script>

<style>
.telestation-container {
  height: 100%;
}
</style>