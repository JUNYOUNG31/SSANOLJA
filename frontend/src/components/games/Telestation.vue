<template>
  <div class="telestation-container">
    <h1>텔레스테이션</h1>
    <div v-show="gameMode ==='text'" style="display:flex; flex-direction: column; align-items: center;"> <!-- 키워드 입력 -->
      <div>
        {{textingTime}}
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
      <button class="btn btn-lg btn-success" @click="postTest">입력 완료</button>
      </p>    
    </div>

    <div  v-show="gameMode ==='drawing'" style="display:flex; flex-direction: column; align-items: center;">
      <div style="width:800px; height: 400px; background-color:white; color:black;">

            <div id="gamearea">
                <div>
                  <canvas id="js-paint"></canvas>
                </div>
                <input type="range" id="js-line" min="0.1" max="10.0" step="any" value="2.5"/><!--굵기 조절-->
                <div class="controls">
                  <button id="js-fill">Fill</button>
                  <button id="js-draw">Paint</button>
                  <button id="js-save">Save</button>
                  <button id="js-eraser">eraser</button>
                  <button id="js-reset">reset</button>
                  <button id="js-fillarea">fillarea</button>
                </div>
                <div class="colors"><!-- 색깔 정렬-->
                  <div class="color" style="background-color:rgb(0, 0, 0)"></div>
                  <div class="color" style="background-color:rgb(255,255,255)"></div>
                  <div class="color" style="background-color:rgb(255,59,48)"></div>
                  <div class="color" style="background-color:rgb(255,149,0)"></div>
                  <div class="color" style="background-color:rgb(255,204,0)"></div>
                  <div class="color" style="background-color:rgb(76,217,100)"></div>
                  <div class="color" style="background-color:rgb(90,200,250)"></div>
                  <div class="color" style="background-color:rgb(0,122,255)"></div>
                  <div class="color" style="background-color:rgb(88,86,214)"></div>
                </div>
  </div>

      </div>
      <div>이곳은 키워드</div>
    </div>
  </div>
</template>
<script src = "../../assets/js/app.js"></script>
<script>
import axios from 'axios';
import {mapState} from 'vuex';

export default {
  name: 'Telestation',
  props:{
    streamManager: Object,
    rules: Object,
    gameRes: Object,
  },
  data () {
		return {
			keyword: '',
      drawingTime:0,
      textingTime:0,
      drawingEnabled: false,
      textingEnabled: false,
      gameMode: "drawing",
      drawingOrder:1, /* 라운드 */
      completedPlayers:0,
      personnel:3,
		}
	},
  methods: {
    inputKeyword: function () {
    axios({
					method:'GET',
					url: '/api/telestations/getData'
			})
			.then(res => {
        console.log(res)
        console.log(res.data)
				this.keyword = res.data
        console.log(this.keyword)
			})
			.catch(err => console.log(err))
    },
    postTest: function () {
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
			}).then((res) => {
        this.keyword = res.data.keyword,
        this.userNickname = res.data.userNickname
        console.log("res ----" + res);
        console.log("키워드 " + this.keyword + "닉네임 " + this.userNickname)
      })
			.catch(err => console.log(err))
    },
    startDrawing() {
      this.drawingEnabled = true;
    },
    startTexting() {
      this.textingEnabled = true;
    },
    pauseDrawing(){
      this.drawingEnabled = false;
    },
    pauseTexting(){
      this.textingEnabled = false;
    },
    endTextRound() {
      this.textingEnabled = false;
      this.textingTime = this.rules.textingTime
      axios({
        method:'POST',
        url: '/api/telestations/InputKeyword',
        data: {
          keyword: this.keyword,
          userNickname: this.myUserName,
          roomCode: this.mySessionId,
          drawingOrder: this.drawingOrder,
        }
      })
      .then(()=> {
      })
    },
    sendMessageToEveryBody(data, type) {
      this.session.signal({
          data: data,
          to: [],
          type: type
      })
      .then(() => {})
      .catch(error => {
          console.error(error);
      })
    },

// this.sendMessageToEveryBody(JSON.stringify(this.gameRes), 'gameRes')
  },
  computed: {
    ...mapState([
    "myUserName",
    "mySessionId",
    ]),
  },
  watch: {
    // Drawing
    drawingEnabled: {
      startDrawingHandler(value) {
          if (value) {
              setTimeout(() => {
                  this.drawingTime--;
              }, 1000);
          }
      },
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
    textingEnabled: {
      startTextingHandler(value) {
          if (value) {
              setTimeout(() => {
                  this.textingTime--;
              }, 1000);
          }
      },
    },


    textingTime: {
        handler(value) {
            if (value > 0 && this.textingEnabled) {
                setTimeout(() => {
                    this.textingTime--;
                }, 1000);
            }
            if (value == -1) {
              this.endTextRound()
            }
        },
        immediate: false // This ensures the watcher is triggered upon creation
    }
  },
  mounted() {
    this.textingTime = this.rules.textingTime
    this.drawingTime = this.rules.drawingTime
    this.startTexting()
  }
}


</script>

<style>
.telestation-container {
  height: 100%;
}
</style>