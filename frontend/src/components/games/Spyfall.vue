<template>
  <div class="spyfall-container">
    <v-container>
      <v-row>
        <v-col cols="9">
          <div>질문화면
            <v-row>
              <v-col cols="6">Question</v-col>
              <v-col cols="6">Answer</v-col>
            </v-row>
            <v-row>
              <v-col class="Question_video" cols="6">
                <div v-if="streamManager" >
                  <ov-video :stream-manager="streamManager"/>
                  <div id="p-name"><p>{{ clientData }}</p></div>
                </div>
              </v-col>
              <v-col class="Answer_video" cols="6">
                <div v-if="streamManager" >
                  <ov-video :stream-manager="streamManager"/>
                  <div id="p-name"><p>{{ clientData }}</p></div>
                </div>
              </v-col>              
            </v-row>
          </div>
          <div class="place_check">
            <div>              
              <button class="place1" @click="toggle(1)"><div id="x1" style="display:none"></div><h3>경찰서</h3></button>  
              <button class="place2" @click="toggle(2)"><div id="x2" style="display:none"></div><h3>자동차 정비소</h3></button>  
              <button class="place3" @click="toggle(3)"><div id="x3" style="display:none"></div><h3>학교</h3></button>  
              <button class="place4" @click="toggle(4)"><div id="x4" style="display:none"></div><h3>레스토랑</h3></button>  
              <button class="place5" @click="toggle(5)"><div id="x5" style="display:none"></div><h3>영화 촬영소</h3></button> 
            </div>
            <div>
              <button class="place6" @click="toggle(6)"><div id="x6" style="display:none"></div><h3>대사관</h3></button>  
              <button class="place7" @click="toggle(7)"><div id="x7" style="display:none"></div><h3>병원</h3></button>  
              <button class="place8" @click="toggle(8)"><div id="x8" style="display:none"></div><h3>대형마트</h3></button>  
              <button class="place9" @click="toggle(9)"><div id="x9" style="display:none"></div><h3>공연장</h3></button>  
              <button class="place10" @click="toggle(10)"><div id="x10" style="display:none"></div><h3>해변</h3></button> 
            </div>
            <div>
              <button class="place11 dis" @click="toggle(11)"><div id="x11" style="display:none"></div><h3>우주 정거장</h3></button>  
              <button class="place12" @click="toggle(12)"><div id="x12" style="display:none"></div><h3>잠수함</h3></button>  
              <button class="place13" @click="toggle(13)"><div id="x13" style="display:none"></div><h3>놀이공원</h3></button>  
              <button class="place14" @click="toggle(14)"><div id="x14" style="display:none"></div><h3>공항</h3></button>  
              <button class="place15" @click="toggle(15)"><div id="x15" style="display:none"></div><h3>대학 연구실</h3></button> 
            </div>
            <div>
              <button class="place16" @click="toggle(16)"><div id="x16" style="display:none"></div><h3>호텔</h3></button>  
              <button class="place17" @click="toggle(17)"><div id="x17" style="display:none"></div><h3>은행</h3></button>  
              <button class="place18" @click="toggle(18)"><div id="x18" style="display:none"></div><h3>카지노</h3></button>  
              <button class="place19" @click="toggle(19)"><div id="x19" style="display:none"></div><h3>회사 송년회</h3></button>  
              <button class="place20" @click="toggle(20)"><div id="x20" style="display:none"></div><h3>동물원</h3></button> 
            </div>
          </div>
        </v-col>
        <v-col cols="3" class="right_menu">
          
          <div>
            <div>장소</div>
            <div> {{place}} </div>
            <div>직업</div>
            <div> {{job}} </div>
          </div>
          <div>
            <v-dialog
              v-model="dialog"
              persistent
              max-width="1000px"              
            >
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  color="primary"
                  dark
                  v-bind="attrs"
                  v-on="on"
                >
                  투표
                </v-btn>
              </template>
              <v-card>
                <v-card-title>
                  <span class="text-h5">누가 스파이일까요?</span>
                </v-card-title>
              
                  <v-container class="vote">
                    <v-row>
                      <v-col cols="12">
                        <v-text-field
                          {{ timerCount }}
                        ></v-text-field>
                      </v-col>
                      <v-col cols="5" class="prosecutor">
                      <div v-if="streamManager" >
                        <ov-video :stream-manager="streamManager"/>
                        <div id="p-name"><p>{{ clientData }}</p></div>
                      </div>
                      </v-col>
                      <v-col cols="2">
                        <img src="../../assets/places_image/투표용.jpg" alt="투표용" style="width:100px">
                      </v-col>
                      <v-col cols="5" class="suspect">                        
                        <div v-if="streamManager" >
                          <ov-video :stream-manager="streamManager"/>
                          <div id="p-name"><p>{{ clientData }}</p></div>
                        </div>
                      </v-col>        
                      <v-card-actions>              
                      <v-col cols="5">
                        <v-btn
                          color="blue darken-1"
                          @click="vote_true"
                        >
                          찬성
                        </v-btn>
                      </v-col>    
                      <v-col cols="2">
                        ddd
                      </v-col>
                      <v-col cols="5">
                        <v-btn
                          color="red lighten-1"
                          @click="vote_false"
                        >
                          반대
                        </v-btn>
                      </v-col>          
                  <v-btn
                    color="blue darken-1"
                    text
                    @click="dialog = false"
                  >
                    Close
                  </v-btn>
                </v-card-actions>
                </v-row>
                </v-container>
              </v-card>
            </v-dialog>
          </div>
        </v-col>
      </v-row>
    </v-container>

    
  </div>
</template>

<script>
import OvVideo from '../Video/OvVideo.vue';
import {mapState} from 'vuex';

export default {
  name: 'Spyfall',

  data () {
		return {
      job: this.gameRes.jobs[this.myUserName],
      place: this.gameRes.place,
      timerEnabled: true,
      timerCount: 30,
      dialog: false,
		}
	},

  props: {
  streamManager: Object,
  rules: Object,
  gameRes: Object
	},

  components: {
		OvVideo,
	},
  
	computed: {
		...mapState([
			
			"myUserName",
			"mySessionId",
			
		]),
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},

		
	},

	methods: {
    getConnectionData () {
    const { connection } = this.streamManager.stream;
    return JSON.parse(connection.data);
		},

    play() {
      this.timerEnabled = true;
    },

    pause() {
      this.timerEnabled = false;
    },
    
    toggle(num) {
      var select = `x${num}`
      var con = document.getElementById(select)
      con.style.display = (con.style.display!= 'none') ? "none":"block"
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

        },
        immediate: false // 컴포넌트가 생성되자마자 즉시 실행
    }

  },


  mounted() {
    this.job = this.gameRes.jobs[this.myUserName]
		this.timerCount = this.rules.playTime
    this.play()
  }
  
}
</script>

<style scoped>
.spyfall-container {
  height: 640px;
}
.spyfall-container .container{
  padding: 0;
}

.Question_video {
  position: relative;
}
.Answer_video {
  position: relative;
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
  width: 400px;
}

.right_menu {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.place_check {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.place_check button {
  height: 120px;
  width: 150px;
  border-radius: 10px;
  position: relative;
}
.place_check button > h3 {
  font-weight: bold;
  margin: 0;
  background-color: rgba(68, 68, 68, 0.3);
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
}

.v-dialog .v-card {
  background-image: url(../../assets/places_image/투표배경.jpg);
  background-size: cover;
  
  background-position: center;
}
</style>