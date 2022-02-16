<template>
  <div>
    <div v-if="!isStarted">
      <spyfall-start :job="job" :place-src="placeSrc" :place="place" :is-spy="isSpy"></spyfall-start>
    </div>
    <div v-else>
      <div v-if="!isEnded">
        <v-container>
          <v-row class="game_row">
            <v-col cols="10">
              <div>
                <v-row class="child-borders">              
                  <v-col cols="6" class="video_name" popover-top="답변자에게 질문하세요!"><div class="alert alert-secondary">질문자</div></v-col>
                  <v-col cols="6" class="video_name" popover-top="질문에 진실만을 답변해야 합니다."><div class="alert alert-danger" >답변자</div></v-col>
                </v-row>
                <v-row>
                  <v-col class="Question_video" cols="6">
                    <div v-if="questionPlayer" class="child-borders">
                      <ov-video :stream-manager="questionPlayer"/>
                    </div>
                    <div v-else-if="firstQuestionPlayer" class="child-borders">
                      <ov-video :stream-manager="firstQuestionPlayer"/>
                    </div>
                  </v-col>
                  <v-col class="Answer_video" cols="6">
                    <div v-if="answerPlayer" class="child-borders">
                      <ov-video :stream-manager="answerPlayer"/>
                    </div>
                    <div v-else  class="border" style="width:350px; height:263px">    
                      <h4>질문자를 선택해 주세요</h4>
                    </div>
                  </v-col>            
                </v-row>
              </div>
              <div class="place_check" popover-bottom="장소에 X를 표시에 장소를 추리해봅시다">
                <div>              
                  <button class="place1 paper-btn" @click="toggle(1)"><div id="x1" style="display:none"></div><p>경찰서</p></button>  
                  <button class="place2 paper-btn" @click="toggle(2)"><div id="x2" style="display:none"></div><p>자동차 정비소</p></button>  
                  <button class="place3 paper-btn" @click="toggle(3)"><div id="x3" style="display:none"></div><p>학교</p></button>  
                  <button class="place4 paper-btn" @click="toggle(4)"><div id="x4" style="display:none"></div><p>레스토랑</p></button>  
                  <button class="place5 paper-btn" @click="toggle(5)"><div id="x5" style="display:none"></div><p>영화 촬영소</p></button> 
                </div>
                <div>
                  <button class="place6 paper-btn" @click="toggle(6)"><div id="x6" style="display:none"></div><p>대사관</p></button>  
                  <button class="place7 paper-btn" @click="toggle(7)"><div id="x7" style="display:none"></div><p>병원</p></button>  
                  <button class="place8 paper-btn" @click="toggle(8)"><div id="x8" style="display:none"></div><p>대형마트</p></button>  
                  <button class="place9 paper-btn" @click="toggle(9)"><div id="x9" style="display:none"></div><p>공연장</p></button>  
                  <button class="place10 paper-btn" @click="toggle(10)"><div id="x10" style="display:none"></div><p>해변</p></button> 
                </div>
                <div>
                  <button class="place11 paper-btn" @click="toggle(11)"><div id="x11" style="display:none"></div><p>우주 정거장</p></button>  
                  <button class="place12 paper-btn" @click="toggle(12)"><div id="x12" style="display:none"></div><p>잠수함</p></button>  
                  <button class="place13 paper-btn" @click="toggle(13)"><div id="x13" style="display:none"></div><p>놀이공원</p></button>  
                  <button class="place14 paper-btn" @click="toggle(14)"><div id="x14" style="display:none"></div><p>공항</p></button>  
                  <button class="place15 paper-btn" @click="toggle(15)"><div id="x15" style="display:none"></div><p>대학 연구실</p></button> 
                </div>
                <div>
                  <button class="place16 paper-btn" @click="toggle(16)"><div id="x16" style="display:none"></div><p>호텔</p></button>  
                  <button class="place17 paper-btn" @click="toggle(17)"><div id="x17" style="display:none"></div><p>은행</p></button>  
                  <button class="place18 paper-btn" @click="toggle(18)"><div id="x18" style="display:none"></div><p>카지노</p></button>  
                  <button class="place19 paper-btn" @click="toggle(19)"><div id="x19" style="display:none"></div><p>회사 송년회</p></button>  
                  <button class="place20 paper-btn" @click="toggle(20)"><div id="x20" style="display:none"></div><p>동물원</p></button> 
                </div>
              </div>
            </v-col>
            <v-col cols="2" class="right_menu">
              <div class="alert alert-primary" style="height: 125px; margin-bottom:10px">
                <h4>게임 시간</h4>
                <h2>{{MMSS}}</h2>
              </div>
              <div class="alert alert-primary" style="height: 180px; margin-bottom:10px">
                <h4>장소</h4>
                <img :src="placeSrc"  style="width:100px; margin:0; height: 85px" />
                <h5 v-if="!isSpy">{{place}}</h5>
              </div>
              <div class="alert alert-primary" style="height: 100px; margin-bottom:10px" v-if="!isSpy">
                <h4>직업</h4>
                <h4>{{job}}</h4>
              </div>
              <div v-else style="margin-bottom:10px">
                <img src="../../assets/Spy.jpg" alt="spy">
              </div>
              <button class="paper-btn btn-secondary" color="primary" style="width:100%" @click="spyfall" v-if="isSpy" popover-right="장소를 추리하여 게임을 끝냅니다">스파이폴</button>
              <div>                
                <v-dialog v-model="dialog" persistent max-width="1000px">
                  <v-card>         
                    <v-container class="vote">
                      <v-row class="vote_row">
                        <v-col cols="12"></v-col>
                        <v-col cols="5" class="prosecutor">
                          <div class="video_name">
                            <div class="alert alert-secondary" popover-top="용의자를 고발하였습니다">고발자</div>
                          </div>
                          <div v-if="selectPlayer" class="child-borders">
                            <ov-video :stream-manager="selectPlayer"/>
                          </div>
                        </v-col>
                        <v-col cols="2" style="text-align:center">
                        <div id ="time_cnt" class="alert alert-primary">
                          <h4>투표 시간</h4>
                          <h4>{{votetimeCnt}}</h4>
                        </div>                      
                        </v-col>
                        <v-col cols="5" class="suspect">      
                          <div class="video_name">
                            <div class="alert alert-danger" popover-top="용의자로 선택되었습니다">용의자</div>
                          </div>                  
                          <div v-if="votePlayer" class="child-borders">
                            <ov-video :stream-manager="votePlayer"/>
                          </div>
                        </v-col>        
                        <v-col cols="12" style="height:80px">
                          <div class="row flex-spaces" >                 
                            <input class="alert-state" id="alert-1" type="checkbox">
                            <div id="voteCompleted" class="alert alert-secondary dismissible" style="display:none; text-align:center">투표가 완료되었습니다.</div>
                          </div>
                        </v-col>
                        <v-col cols="4" id ="agree">
                          <button class="paper-btn btn-secondary" style="width:100px" @click="voteTrue" 
                          :disabled="isVoted || myUserName == suspectPlayer"><h5>찬성</h5></button>
                        </v-col>    
                        <v-col cols="4" id="vote_cnt" v-if="voteList.voteCnt != streamManager.length-1">
                          <h3 style="margin:15px"> 투표수 {{voteList.voteCnt}}</h3>                   
                        </v-col>
                        <v-col cols="4" id="vote_cnt" v-else>
                          <h3 style="margin:15px">찬성:{{voteList.agreeCnt}} vs 반대:{{voteList.disagreeCnt}}</h3>
                        </v-col>
                        <v-col cols="4" id="disagree" >
                          <button class="paper-btn btn-danger" style="width:100px" @click="voteFalse"
                          :disabled="isVoted || myUserName == suspectPlayer"><h5>반대</h5></button>
                        </v-col>
                      </v-row>
                    </v-container> 
                  </v-card>
                </v-dialog>
              </div>  
              <button class="paper-btn btn-success" @click="openinfo = true" style="width:100%; margin-top:20px">게임설명</button>
              <v-dialog class="infodialog" v-model="openinfo" persistent max-width="750px">
                <v-card  class="scrollx" height="500px" style="padding-right:10px">   
                  <img src="@/assets/description_image/spyfall/스파이폴설명1.png" alt="">
                  <img src="@/assets/description_image/spyfall/스파이폴설명2.png" alt="">
                  <img src="@/assets/description_image/spyfall/스파이폴설명3.png" alt="">
                  <img src="@/assets/description_image/spyfall/스파이폴설명4.png" alt="">
                  <button class="paper-btn btn-success" style="align-self:center" @click="openinfo = false">닫기</button>
                </v-card>
              </v-dialog>
            </v-col>
          </v-row>
        </v-container>
      </div>
      <div v-if="isEnded">
        <spyfallEnd :spy-player="spyPlayer" :place="place" :is-spy="isSpy" :gameRes="gameRes"></spyfallEnd>
      </div>
    </div>
  </div>
</template>

<script>
import OvVideo from '../Video/OvVideo.vue';
import SpyfallEnd from './SpyfallEnd.vue';
import SpyfallStart from './SpyfallStart.vue'
import {mapState} from 'vuex';

export default {
  name: 'Spyfall',

  data () {
		return {
      job: null,
      place: null,
      placeSrc: null,
      timerEnabled: true,
      timerCount: 30,
      votetimeCnt: 30,
      voteEnabled: false,
      isVoted : false,
      voteList : {
        voteCnt : 0, 
        agreeCnt: 0,
        disagreeCnt: 0,
      },      
      isEnded: false,
      isStarted: false,
      isSpy: false,
      spyPlayer : null,
      openinfo: false,
      desc: [
        "p1","p2","p3", "p4"
      ],
		}
	},

  props: {
  streamManager: Array,
  rules: Object,
  gameRes: Object
	},

  components: {
		OvVideo,
    SpyfallEnd,
    SpyfallStart,
	},

	computed: {
		...mapState([
      "session",
      "dialog",              // 투표 지목시 투표창 활성
      "firstQuestionPlayer", // 처음 질문하는 사람
      "questionPlayer",      // 질문하는 사람
      "answerPlayer",        // 질문받는 사람
      "selectPlayer",        // 투표를 시작한 사람
      "votePlayer",          // 투표를 지목당한 사람
      "myUserName",
			"mySessionId",
      "subscribers",	
      "publisher",
		]),
    
    suspectPlayer () {
      if (this.votePlayer) {
        return JSON.parse(this.votePlayer.stream.connection.data).clientData      
      }
      return null
    },

    MMSS () {
      return new Date(this.timerCount*1000).toISOString().substr(14, 5)      
    }
	},

  watch: {
    timerEnabled(value) {
      if (value) {
        setTimeout(() => {
            this.timerCount--;
        }, 1000);
      }
    },
    
    timerCount: {
      handler(value) {
        if (value > 0 && this.timerEnabled) {
          setTimeout(() => {
            this.timerCount--;
          }, 1000);
        }
        if(this.timerCount <= 0) {
          if(this.isSpy) {
            this.spyfall()
          }
        }
      },
      immediate: false // 컴포넌트가 생성되자마자 즉시 실행
    },

    voteEnabled(value) {
      if (value) {
        setTimeout(() => {
            this.votetimeCnt--;
        }, 1000);
      }
    },
    votetimeCnt: {
      handler(value) {
        if(value > 0 && this.voteEnabled) {
          setTimeout(() => {
            this.votetimeCnt--;
          }, 1000);
        }
        if(this.votetimeCnt == 0) {          
          setTimeout(()=> {
            this.restart()
          }, 3000);          
        }
      },
      immediate: false // 컴포넌트가 생성되자마자 즉시 실행
    }
  },

    mounted() {
    //초기화
    this.isStarted = false
    this.place = this.gameRes.place
    const placeImg = this.gameRes.place.split(' ').join('_')
    this.placeSrc = require("../../assets/places_image/"+placeImg+".jpg")
    this.job = this.gameRes.jobs[this.myUserName]
    this.timerCount = this.rules.playTime + 5   
    this.play()

    if(this.job === '스파이') {
      this.placeSrc = require("../../assets/places_image/unknown.png")
      this.isSpy = true
    }

    for (let index = 0; index < this.subscribers.length; index++) {
      let nickName = JSON.parse(this.subscribers[index].stream.connection.data)
      if (this.gameRes.jobs[nickName.clientData] == '스파이') {
        this.spyPlayer = this.subscribers[index]
      }
    }

    this.session.once('signal:setFirstQuestionPlayer', (event)=> {
      const firstQuestionPlayerName = JSON.parse(event.data).clientData
      for (let index = 0; index < this.subscribers.length; index++) {        
        let nickName = JSON.parse(this.subscribers[index].stream.connection.data)
        if (firstQuestionPlayerName == nickName.clientData) {
          this.$store.commit("SET_FIRSTQUESTIONPLAYER", this.subscribers[index])
        }
      }
      this.isStarted=true
    })

    this.session.on('signal:votePlayer', ()=> {
      this.pause()
    })

    this.session.on('signal:spyfall', ()=>{
      this.isEnded=true
    })

    this.session.on('signal:voteTrue', (event)=>{
      this.voteList = JSON.parse(event.data)
      this.voteList.voteCnt += 1
      this.voteList.agreeCnt += 1
      if ( this.voteList.voteCnt == this.streamManager.length -1) {    
        // 투표가 끝나고 3초 보여주기  
        const div = document.getElementById('voteCompleted')
        div.style.display = "block"          
        setTimeout(() => {
          // 만약 만장일치일때
          if (this.voteList.agreeCnt == this.streamManager.length - 1) {
            // 스파이가 맞으면 시민 승리
            if (this.gameRes.jobs[this.suspectPlayer] == '스파이') {            
              this.$store.commit("CITIZEN_WIN")
              this.isEnded = true
              this.$store.commit('SET_VOTEPLAYER', null)
              this.$store.commit('SET_SELECTPLAYER', null)
              this.$store.commit('SET_QUESTIONPLAYER', null)
              this.$store.commit('SET_ANSWERPLAYER', null)
            }          
            // 스파이가 아니라면 스파이 승리
            else {
              this.$store.commit("SPY_WIN")
              this.isEnded = true
              this.$store.commit('SET_VOTEPLAYER', null)
              this.$store.commit('SET_SELECTPLAYER', null)
              this.$store.commit('SET_QUESTIONPLAYER', null)
              this.$store.commit('SET_ANSWERPLAYER', null)
            }
          }
          // 만약 만장일치가 아닐때 다시 게임 진행
          else {
            this.restart()
          }
        }, 3000);
      }      
    })
    
    this.session.on('signal:voteFalse', (event)=>{
      this.voteList = JSON.parse(event.data)
      this.voteList.voteCnt += 1
      this.voteList.disagreeCnt += 1
      if ( this.voteList.voteCnt >= this.streamManager.length-1) {
        this.voteList.voteCnt = this.streamManager.length-1
        const div = document.getElementById('voteCompleted')
        div.style.display = "block"
        setTimeout(() => {
          // alert('투표가 완료 되었습니다.')
          this.restart()
        }, 3000);
      }
    })

    this.session.on('signal:restart', (event)=>{
      this.voteList = JSON.parse(event.data)
      this.timerEnabled = true;
      this.$store.commit('SET_VOTEPLAYER', null)
      this.voteList.voteCnt = 0
      this.voteList.agreeCnt = 0
      this.voteList.disagreeCnt = 0
      this.isVoted = false
      this.voteEnabled = false;
      this.votetimeCnt = 30;
      const div = document.getElementById('voteCompleted')
      div.style.display = "none"
    })
  },

	methods: {
    spyfall() {
      this.pause()
      this.sendMessageToEveryBody('','spyfall')
      this.isEnded = true      
    },

    getConnectionData () {
    const { connection } = this.questionVideo.stream;
    return JSON.parse(connection.data);
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

    play() {
      this.timerEnabled = true;      
    },

    pause() {
      this.timerEnabled = false;
      this.voteEnabled = true;
    },
    
    restart() {
      this.sendMessageToEveryBody(JSON.stringify(this.voteList), 'restart')
    },
    
    toggle(num) {
      var select = `x${num}`
      var con = document.getElementById(select)
      con.style.display = (con.style.display!= 'none') ? "none":"block"
    },

    voteTrue() {
      this.isVoted = true
      this.sendMessageToEveryBody(JSON.stringify(this.voteList), 'voteTrue')           
    },

    voteFalse() {
      this.isVoted = true
      this.sendMessageToEveryBody(JSON.stringify(this.voteList), 'voteFalse')       
    },

    voteclose() { // 투표창 끄는 method
      this.timerEnabled = true;
      this.votetimeCnt = 30;
      this.voteEnabled = false;
    }
  }
}
</script>

<style scoped>
.container{
  padding: 0;
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

#game {
  padding: 0;
}
h2, h3 {
  text-align: center;
  margin : 0;
}
h3, h4, h5 {
  font-family: 'GowunDodum-Regular';
}
#questiont_tag {
  border-radius: 5px;
  padding: 0 1em;
  background: #48484d;
}
#timer_tag {
  border-radius: 5px;
  padding: 0 1em;
  background: #48484d;
  margin-bottom: 20px;
}
#job_place_tag {
  border-radius: 5px;
  padding: 30px 1em;
  background: #48484d;
}
#job_place_tag > div{
  margin-top: 20px;
}
.Question_video {
  position: relative;
  padding-top: 0;
  text-align: center;
}
.Answer_video {
  position: relative;
  padding-top: 0;
  display: flex;
  justify-content: center;
  text-align: center;
}
.Answer_video div {
  background-image: url(../../assets/answerplayer.jpg);
  background-position: center;
  background-size: cover;
}
.Answer_video .border h4 {
  position: absolute;
  top: 40%;
  left: 20%;
  margin: 0;
  background-color: rgb(116 116 116 / 65%);
  padding: 13px;
  color: white;
}
#p-name {
  position: absolute;
  bottom: 0px;
  margin: 0 auto;
  height: 21px;
}
#p-name p{  
  margin: 0;
}
video {
  width: 350px;  
}
.video_name {
  padding-bottom: 0;
  padding-top: 10px;
  height: 65px;
  border: 0;
  box-sizing: border-box;
  font-family: 'GowunDodum-Regular';
}
.video_name div {
  border-bottom-left-radius: 15px 255px;
  border-bottom-right-radius: 225px 15px;
  border-top-left-radius: 255px 15px;
  border-top-right-radius: 15px 225px;
  border-style: solid;
  border-width: 2px;
  margin-bottom: 20px;
  padding: 10px;
  width: 100%;
  font-size: 20px;
  text-align: center;
  height: 52px;
  margin: 0;
}
.right_menu {
  display: flex;
  flex-direction: column;   
  border-bottom-left-radius: 15px 255px;
  border-bottom-right-radius: 247px 27px;
  border-top-left-radius: 255px 15px;
  border-top-right-radius: 29px 268px;
  color: #41403e;
  color: var(--primary);
  border-color: #41403e;
  border-color: var(--primary);
  background-image: none;
  border-style: solid;
  border-width: 2px;
  display: inline-block;
  outline: none;
  padding: 0.75rem;
}
.right_menu h3, .right_menu h4, .right_menu h5 {
  margin-bottom: 20px;
  font-family: 'GowunDodum-Regular';
  text-align: center;
}
.place_check {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.place_check button {
  height: 90px;
  width: 120px;
  position: relative;
}
.place_check button > p {
  font-weight: bold;
  font-size: 15px;
  color: white;
  margin: 0;
  background-color: rgb(116 116 116 / 65%);
  padding: 2px;
  font-family: 'GowunDodum-Regular';
}
.place_check button > div {
  height: 90px;
  width: 120px;
  background-image: url(../../assets/places_image/x.png);
  background-size: cover;
  background-position: center;
  position: absolute;
  top: 0;
  left: 0;
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
.v-dialog > * {
  height: 700px;  
}
.prosecutor {
  position: relative;
}
.prosecutor div video {
  width: 100%;
}
.suspect {
  position: relative;
}
.suspect div video {
  width: 100%;
}
.vote v-row v-col {
  padding: 0;
  align-self: center;
}
.v-dialog .v-card {
  background-image: url(../../assets/places_image/투표배경.jpg);
  background-size: cover;  
  background-position: center;
}
#agree {
  text-align: center;
  background-color:rgb(138, 138, 138);
}
#vote_cnt {
  text-align: center;
  background-color:rgb(138, 138, 138);
}
#disagree {
  text-align: center;
  background-color:rgb(138, 138, 138);
}
#time_cnt {
  text-align: center;
}
.scrollx {
  overflow: scroll;
  -ms-overflow-style: none;
  position: relative;
}
.scrollx::-webkit-scrollbar { 
  width: 0 !important;
  display: none; 
}
.scrollx  button {
width: 70px;
position: relative;
top: 0;
left: 92%;
}
</style>