<template>
  <div class="telestation-container" style = "color : red;">
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
      <div>{{receiveKeyword}}</div>
      <div id="gamearea">
        <canvasApi ref="canvasApi" @canvasCompleted="canvasCompleted" @setDrawData="setDrawData"></canvasApi>
      </div>
    </div>


    <div v-show="gameMode ==='album'" style="display:flex; flex-direction: column; align-items: center;">
      <div v-for="(data,index) in recieveAlbum" :key="index">
        <div v-if="index%2 === 0">
            <p>{{data}}</p>
            <button @click="bestPick(index)">좋아요</button>
            <button @click="worstPick(index)">싫어요</button>
        </div>
        <div v-else>
          <img :src="data" alt="">
            <button @click="bestPick(index)">좋아요</button>
            <button @click="worstPick(index)">싫어요</button>
        </div>
      </div>
        <button v-show="isRoomMaker && round<personnel" @click="sendMessageToEveryBody('','nextAlbum')">다음앨범</button>
        <button v-show="isRoomMaker && round===personnel" @click="sendMessageToEveryBody('','nextAlbum')">결과보기</button>
        <p>{{round}}</p>
    </div>

    <div v-show="gameMode ==='best'" style="display:flex; flex-direction: column; align-items: center;">
      <div>bestPlayer</div>
      <div v-show="bestResultMode === 1">
        <p>{{bestPreData}}</p>
        <img :src="bestData" alt="">
      </div>
      <div v-show="bestResultMode === 2">
        <img :src="bestPreData" alt="">
        <p>{{bestData}}</p>
      </div>
      <button v-show="isRoomMaker" @click="sendMessageToEveryBody('','worst')">worst보러가기</button>
    </div>
    
    <div v-show="gameMode ==='worst'" style="display:flex; flex-direction: column; align-items: center;">
      <div>worstPlayer</div>
      <div v-show="worstResultMode === 1">
        <p>{{worstPreData}}</p>
        <img :src="worstData" alt="">
      </div>
      <div v-show="worstResultMode === 2">
        <img :src="worstPreData" alt="">
        <p>{{worstData}}</p>
      </div>
      <button v-show="isRoomMaker" @click="sendMessageToEveryBody('','room')">다시하기</button>
    </div>

    
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
      readyPlayers:0, /* 전 라운드에서 키워드나 그림정보를 웹소켓으로 받은 플레이어 수*/
      personnel:3, //인원수
      participant: new Map(), // 참가자들
      targetUser: '', // 웹소켓 받는 사람
      receiveKeyword:'', // 받은 키워드
      recieveDraw:'', // 받은 그림
      recieveAlbum:null,
      round:0,
      gameId: this.gameRes.playGameId,
      dataIndex: 0, // 앨범 번호
      worst:0,
      best:0,
      worstPlayer:'',
      bestPlayer:'',
      worstResultMode: 0, //1 이면 키워드 사진 2이면 사진 키워드
      bestResultMode:0,
      worstPreData:'',
      worstData:'',
      bestPreData:'',
      bestData:'',
      isRoomMaker:localStorage.getItem('isRoomMaker')
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
    worstPick(index) {
      this.worst = index +1
    },
    bestPick(index) {
      this.best = index +1
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
    canvasCompleted(){
      this.sendMessageToEveryBody('','completed')
      console.log('캔버스에서 완료 버튼 눌렀나 ?')
    },
    setDrawData(imageData){
      console.log('그림데이터 저장완료@@@@@@@@@@@@')
      this.draw = imageData
    },
    // 캔버스 관련 함수 시작

    //라운드 끝내기& 시작 함수 모음 시작
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
    },
    endDrawRound() { // Drawing 라운드 끝
      this.pauseDrawing()
      this.DrawingTime = this.rules.DrawingTime
      this.$refs.canvasApi.saveClick()
      console.log(this.drawingOrder)
      console.log(this.myUserName,this.mySessionId,this.drawingOrder,this.personnel,this.draw)
      // canvas에서 그림 축출해서 this.darw에 저장
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
        this.sendMessageToTargetUser(JSON.stringify(res.data), "draw", this.participant.get(this.targetUser))
      })
    },
    startTextRound() {
      // this.sendMessageToEveryBody('', "drawingOrder")
      this.drawingOrder++;
      if (this.drawingOrder === this.personnel +1) {
        this.startAlbumRound()
      }else {
      this.gameMode = 'text'
      this.startTexting()
      }
    },
    startDrawRound() {
      // this.sendMessageToEveryBody('', "drawingOrder")
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
          console.log("this.dataIndex"+this.dataIndex);
          console.log("this.recieveAlbum"+this.recieveAlbum);
          this.worst = 0
          this.best = 0
        }else {
          this.bestPlayer = res.data.best.nickName
          this.worstPlayer = res.data.worst.nickName
          this.worstPreData = res.data.worst.preData
          this.worstData = res.data.worst.data
          this.bestPreData = res.data.best.preData
          this.bestData = res.data.best.data
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
      })
      .catch((err)=> {
        console.log(err, '앨범에러')
      })
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
    }
  },
  mounted() {
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
      this.gameMode = 'worst'
    })
    this.session.on('signal:result', (event) => { // 그린 그림 백에 보내기 
      this.$router.push({ name: 'Room', params: { joinCode: this.joinCode}})
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