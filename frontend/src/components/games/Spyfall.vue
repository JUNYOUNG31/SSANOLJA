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
              <button class="btn btn-lg btn-primary">1장소</button>  
              <button class="btn btn-lg btn-primary">2장소</button>  
              <button class="btn btn-lg btn-primary">3장소</button>  
              <button class="btn btn-lg btn-primary">4장소</button>  
              <button class="btn btn-lg btn-primary">5장소</button> 
            </div>
            <div>
              <button class="btn btn-lg btn-primary">1장소</button>  
              <button class="btn btn-lg btn-primary">2장소</button>  
              <button class="btn btn-lg btn-primary">3장소</button>  
              <button class="btn btn-lg btn-primary">4장소</button>  
              <button class="btn btn-lg btn-primary">5장소</button> 
            </div>
            <div>
              <button class="btn btn-lg btn-primary">1장소</button>  
              <button class="btn btn-lg btn-primary">2장소</button>  
              <button class="btn btn-lg btn-primary">3장소</button>  
              <button class="btn btn-lg btn-primary">4장소</button>  
              <button class="btn btn-lg btn-primary">5장소</button> 
            </div>
            <div>
              <button class="btn btn-lg btn-primary">1장소</button>  
              <button class="btn btn-lg btn-primary">2장소</button>  
              <button class="btn btn-lg btn-primary">3장소</button>  
              <button class="btn btn-lg btn-primary">4장소</button>  
              <button class="btn btn-lg btn-primary">5장소</button> 
            </div>
          </div>
        </v-col>
        <v-col cols="3" class="right_menu">
          <div>타이머</div>
          <div>
            <div>장소</div>
            <div> {{place}} </div>
            <div>직업</div>
            <div> {{job}} </div>
          </div>
          <div>투표</div>
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
      place: this.gameRes.place
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
	},
  mounted() {
    this.job = this.gameRes.jobs[this.myUserName]
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
  height: 90px;
  width: 120px;
}
</style>