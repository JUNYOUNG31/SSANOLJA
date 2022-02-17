<template>
  <div class="spyfallend">
    <div v-if="(!spyWin && !citizenWin) && !isSpy">
      <h4 style="font-family: 'GowunDodum-Regular'">스파이가 나타났습니다!</h4>
      <h4 style="font-family: 'GowunDodum-Regular'">스파이가 시민들이 있는 장소를 추리하는 중 입니다</h4>
      <div v-if="spyPlayer" class="child-borders">
        <ov-video :stream-manager="spyPlayer"/>
      </div>
    </div>
    <div v-if="(!spyWin && !citizenWin) && isSpy" class="place_check">
      <div class="place_check_h">
        <h4 style="font-family: 'GowunDodum-Regular'">당신은 스파이 입니다!</h4>
        <h4 style="font-family: 'GowunDodum-Regular'">시민들이 있는 장소를 추리해서 승리하세요!</h4>
      </div>
      <div>              
        <button class="place1 paper-btn" @click="decide('경찰서')"><h5>경찰서</h5></button>  
        <button class="place2 paper-btn" @click="decide('자동차_정비소')"><h5>자동차 정비소</h5></button>  
        <button class="place3 paper-btn" @click="decide('학교')"><h5>학교</h5></button>  
        <button class="place4 paper-btn" @click="decide('레스토랑')"><h5>레스토랑</h5></button>  
        <button class="place5 paper-btn" @click="decide('영화_촬영소')"><h5>영화 촬영소</h5></button> 
      </div>
      <div>
        <button class="place6 paper-btn" @click="decide('대사관')"><h5>대사관</h5></button>  
        <button class="place7 paper-btn" @click="decide('병원')"><h5>병원</h5></button>  
        <button class="place8 paper-btn" @click="decide('대형마트')"><h5>대형마트</h5></button>  
        <button class="place9 paper-btn" @click="decide('공연장')"><h5>공연장</h5></button>  
        <button class="place10 paper-btn" @click="decide('해변')"><h5>해변</h5></button> 
      </div>
      <div>
        <button class="place11 paper-btn" @click="decide('우주_정거장')"><h5>우주 정거장</h5></button>  
        <button class="place12 paper-btn" @click="decide('잠수함')"><h5>잠수함</h5></button>  
        <button class="place13 paper-btn" @click="decide('놀이공원')"><h5>놀이공원</h5></button>  
        <button class="place14 paper-btn" @click="decide('공항')"><h5>공항</h5></button>  
        <button class="place15 paper-btn" @click="decide('대학_연구실')"><h5>대학 연구실</h5></button> 
      </div>
      <div>
        <button class="place16 paper-btn" @click="decide('호텔')"><h5>호텔</h5></button>  
        <button class="place17 paper-btn" @click="decide('은행')"><h5>은행</h5></button>  
        <button class="place18 paper-btn" @click="decide('카지노')"><h5>카지노</h5></button>  
        <button class="place19 paper-btn" @click="decide('회사_송년회')"><h5>회사 송년회</h5></button>  
        <button class="place20 paper-btn" @click="decide('동물원')"><h5>동물원</h5></button> 
      </div>
    </div>
    <div v-show="spyWin">   
      <canvas id="canvas" v-show="isSpy"></canvas>
      <p>스파이 승리</p>
      <div v-if="spyPlayer" class="child-borders">
        <ov-video :stream-manager="spyPlayer"/>
      </div>
    </div>
    <div v-show="citizenWin">   
      <canvas id="canvas" v-show="!isSpy"></canvas>
      <p>시민 승리</p>
      <div v-if="spyPlayer" class="child-borders">
        <ov-video :stream-manager="spyPlayer"/>
      </div>
    </div>
    <div v-if="isRoomMaker & (spyWin || citizenWin)">
      <button class="paper-btn" style="width:100%;" @click="backToRoom()"><span>게임 선택하기</span></button>
      </div>
    <div v-if="(spyWin || citizenWin)">
      <button class="paper-btn btn-secondary" style="width:100%" @click="leaveRoom()"><span>방나가기</span></button>
    </div>    

  </div>  
</template>

<script src="../../assets/js/confetti_v2.js"></script>
<script>
import { mapState } from 'vuex';
import OvVideo from '../Video/OvVideo.vue';

export default {
  name: 'SpyfallEnd',

  data() {
    return {      
    }
  },

  props: {
    spyPlayer: Object,
    place: String,
    isSpy: Boolean,
    gameRes: Object,
  },

  components: {
    OvVideo,
  },

  computed: {
    ...mapState([
      "publisher",
      "session",
      "subscribers",
      "spyWin",
      "citizenWin",
      "isRoomMaker",
      "myUserName",
      "sendUserEmail"
    ])
  },

  mounted() {
    if(this.session.ee._events["signal:spyWin"] == undefined) {
      this.session.on('signal:spyWin', ()=>{
        this.$store.commit("SPY_WIN")
      })
      // this.effect()
    }

    if(this.session.ee._events["signal:citizenWin"] == undefined) {
      this.session.on('signal:citizenWin', () =>{
        this.$store.commit("CITIZEN_WIN")
      })
      // this.effect()
    }           

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
  },

  updated() {
    if(this.gameRes.jobs[this.myUserName]=="스파이") {
      if(this.citizenWin) {
        this.publisher.stream.applyFilter("GStreamerFilter", {command: "videobalance saturation=0.0"})
        .then(()=>{
          console.log("필터 적용됨")
        })
        .catch(error =>{
          console.error(error)
        })
      }

      if(this.spyWin) {
        if (this.publisher.stream.filter == undefined){
        this.publisher.stream.applyFilter(
          "ChromaFilter",
          {
              "window": {
                  "topRightCornerX": 0,
                  "topRightCornerY": 0,
                  "width": 50,
                  "height": 50
              },
              "backgroundImage": "../../assets/background.jpg"
          });
        }
      }
    }
  },

  // deactivated () {
  //   if(this.gameRes.jobs[this.myUserName]=="스파이") {
  //     this.publisher.stream.removeFilter()
  //     .then(()=>{
  //       console.log("필터 제거됨");
  //     })
  //     .catch(error => {
  //       console.error(error);
  //     })
  //   }
  // },



  methods: {
    effect() {
      
    },

    leaveRoom() {
      this.$store.dispatch('leaveSession')
      this.$router.push({name:'Lobby', params: { sendUserEmail: this.sendUserEmail}})
    },

    backToRoom() {
      if(this.gameRes.jobs[this.myUserName]=="스파이") {
        if(this.publisher.stream.filter){

        this.publisher.stream.removeFilter()
        .then(()=>{
          console.log("필터 제거됨");
        })
        .catch(error => {
          console.error(error);
        })
      }
      }
      this.sendMessageToEveryBody('', 'initSpyfall')
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

    decide(here) {
      if (this.place == here) {
        this.sendMessageToEveryBody('','spyWin')
        this.$store.commit("SPY_WIN")
      } else {
        this.sendMessageToEveryBody('','citizenWin')
        this.$store.commit("CITIZEN_WIN")
      }
      // this.effect()
    }
  }
}
</script>

<style scoped>
.spyfallend {
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
  animation: fadein 2s;
}
@keyframes fadein {
  from {
    opacity:0;
  }
  to {
    opacity:1;
  }
}
.spyfallend h4 {
  font-family: 'GowunDodum-Regular'
}
video {
  width: 350px;  
}
.place_check {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.place_check_h {
  margin-bottom: 20px;
}
.place_check button {
  height: 120px;
  width: 150px;
  position: relative;
}
.place_check button > h5 {
  font-weight: bold;
  color: white;
  margin: 0;
  background-color: rgb(116 116 116 / 83%);
  padding: 4px;
  font-family: 'GowunDodum-Regular';
}
.place_check button > div {
  height: 120px;
  width: 150px;
  background-image: url(../../assets/places_image/x.png);
  background-size: cover;
  background-position: center;
  position: absolute;
  top: 0;
}
.place1 {
  background-image: url(../../assets/places_image/경찰서.jpg);
  background-size: cover;
  background-position: center;
}
.place2 {
  background-image: url(../../assets/places_image/자동차_정비소.jpg);
  background-size: cover;
  background-position: center;
}
.place3 {
  background-image: url(../../assets/places_image/학교.jpg);
  background-size: cover;
  background-position: center;
}
.place4 {
  background-image: url(../../assets/places_image/레스토랑.jpg);
  background-size: cover;
  background-position: center;
}
.place5 {
  background-image: url(../../assets/places_image/영화_촬영소.jpg);
  background-size: cover;
  background-position: center;
}
.place6 {
  background-image: url(../../assets/places_image/대사관.jpg);
  background-size: cover;
  background-position: center;
}
.place7 {
  background-image: url(../../assets/places_image/병원.jpg);
  background-size: cover;
  background-position: center;
}
.place8 {
  background-image: url(../../assets/places_image/대형마트.jpg);
  background-size: cover;
  background-position: center;
}
.place9 {
  background-image: url(../../assets/places_image/공연장.jpg);
  background-size: cover;
  background-position: center;
}
.place10 {
  background-image: url(../../assets/places_image/해변.jpg);
  background-size: cover;
  background-position: center;
}
.place11 {
  background-image: url(../../assets/places_image/우주_정거장.jpg);
  background-size: cover;
  background-position: center;
}
.place12 {
  background-image: url(../../assets/places_image/잠수함.jpg);
  background-size: cover;
  background-position: center;
}
.place13 {
  background-image: url(../../assets/places_image/놀이공원.jpg);
  background-size: cover;
  background-position: center;
}
.place14 {
  background-image: url(../../assets/places_image/공항.jpg);
  background-size: cover;
  background-position: center;
}
.place15 {
  background-image: url(../../assets/places_image/대학_연구실.jpg);
  background-size: cover;
  background-position: center;
}
.place16 {
  background-image: url(../../assets/places_image/호텔.jpg);
  background-size: cover;
  background-position: center;
}
.place17 {
  background-image: url(../../assets/places_image/은행.jpg);
  background-size: cover;
  background-position: center;
}
.place18 {
  background-image: url(../../assets/places_image/카지노.jpg);
  background-size: cover;
  background-position: center;
}
.place19 {
  background-image: url(../../assets/places_image/회사_송년회.jpg);
  background-size: cover;
  background-position: center;
}
.place20 {
  background-image: url(../../assets/places_image/동물원.jpg);
  background-size: cover;
  background-position: center;
}
canvas {
  z-index: 10;
  pointer-events: none;
  position: fixed;
  top: 0;
  right: 0;
  transform: scale(1.1);
  /* display: none; */
}


</style>