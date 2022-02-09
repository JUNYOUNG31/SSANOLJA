<template>
  <div class="telestation-container">
    <h1>텔레스테이션</h1>
    <!-- <button @click="getUsers">ssss</button> -->
    <div v-show="gameMode ==='text'" style="display:flex; flex-direction: column; align-items: center;"> <!-- 키워드 입력 -->
      <div>
        {{textingTime}}
        {{drawingTime}}
        <button @click="startTexting()">시작</button>
        <button @click="pauseTexting()">정지</button>
      </div>
      <div v-show="drawingOrder !== 1" style="width:800px; height: 400px; background-color:white; color:black;">전 사람이 그린 그림 두두등장!</div>
      <div style="width:800px; text-align:center;">
        <label>키워드</label>
        <input v-model="keyword" class="form-control" type="text" required placeholder="키워드를 입력하세요">
        <br>
        <!-- <input v-model="userData.userName" class="form-control" type="text" required placeholder="닉네임을 입력하세요"> -->
      </div>
      <p class="text-center">
      <button class="btn btn-lg btn-success" @click="sendMessageToEveryBody('','completed')">입력 완료</button>
      </p>    
    </div>
    <div  v-show="gameMode ==='drawing'" style="display:flex; flex-direction: column; align-items: center;">
      {{drawingTime}}
      <div id="gamearea">
        <canvasApi></canvasApi>
      </div>
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
      drawingTime:this.rules.drawingTime,
      textingTime:this.rules.textingTime,
      drawingEnabled: false,
      textingEnabled: false,
      gameMode: "drawing",
      drawingOrder:1, /* 라운드 */
      completedPlayers:0, /* 그림 다 그렸거나 or 키워드 입력완료 누른 플레이어 수 */
      readyPlayers:0, /* 전 라운드에서 키워드나 그림정보를 웹소켓으로 받은 플레이어 수*/
      personnel:2,
      participant: new Map(),
      targetUser: '',
      receiveKeyword:''
		}
	},
  methods: {
    // postTest: function () { // back에 keyword 보내는 함수
    // axios({
		// 		method:'POST',
		// 		url: '/api/telestations/InputKeyword',
    //     data: { 
    //       keyword: this.keyword,
    //       userNickname: this.myUserName,
    //       roomCode: this.mySessionId,
    //       drawingOrder: this.drawingOrder,
    //       personnel: this.personnel,
    //     }
		// 	}).then((res) => {
    //     this.keyword = res.data.keyword,
    //     this.userNickname = res.data.userNickname
    //     console.log("res ----" + res);
    //     console.log("키워드 " + this.keyword + "닉네임 " + this.userNickname)
    //   })
		// 	.catch(err => console.log(err))
    // },
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
    endTextRound() { // Texting 라운드 끝
      this.pauseTexting()
      this.textingTime = this.rules.textingTime
      console.log("3333333333333333333333333333333333")
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
        console.log("---------------------------------------------4")
        this.targetUser = res.data.userNickname
        this.sendMessageToTargetUser(this.keyword, "keyword", this.participant.get(this.targetUser))
      })
    },
    startDrawRound() {
      this.sendMessageToEveryBody('', "drawingOrder")
      this.gameMode = 'drawing'
      this.startDrawing()
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
    sendMessageToTargetUser(data, type, targetUser) { // 참가자 전원 웹소켓
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
    getUsers() { // 참가자 닉네임과 connectinId 딕셔너리화 for(웹소켓)
      let myNickName = JSON.parse(this.publisher.stream.connection.data)
      this.participant.set(myNickName.clientData, this.publisher.stream.connection)
      for (let index = 0; index < this.subscribers.length; index++) {
        let nickName = JSON.parse(this.subscribers[index].stream.connection.data)
        this.participant.set(nickName.clientData, this.subscribers[index].stream.connection)
      }
      console.log(this.publisher)
      console.log(this.participant, '과여양아ㅏ아아아앙')
      console.log(this.participant.get('조성현'))
    },
    startTextRound() {
      console.log('안들어와야해')
    }

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
    // Drawing
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

        },
        immediate: false // This ensures the watcher is triggered upon creation
    },
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
        immediate: false // This ensures the watcher is triggered upon creation
    },

    completedPlayers: {
      handler(value) {
        if (value === this.personnel && this.gameMode==='text') {
          this.completedPlayers = 0
          this.endTextRound()
        }
      }
    },
    readyPlayers: {
      handler(value) {
        if (value === this.personnel && this.gameMode==='text') {
          this.readyPlayers = 0
          this.startDrawRound()
        }
        if (value === this.personnel && this.gameMode==='drawing') {
          this.startTextRound()
        }
      }
    }
  },
  mounted() {
    this.startTexting()
    this.getUsers()
    this.session.on('signal:keyword', (event) => {
      console.log(event.data)
      this.receiveKeyword = event.data
      this.sendMessageToEveryBody('', "ready")
    })
    this.session.on('signal:completed', (event) => {
      console.log(event.data)
      this.completedPlayers++
    })
    this.session.on('signal:ready', (event) => {
      console.log(event.data)
      this.readyPlayers++
    })
    this.session.on('signal:drawingOrder', (event) => {
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