<template>
  <div class="room"> <!--전체화면-->
    <v-container fluid> <!--게임& 화면들 감싸는 부분-->
      <v-row class="wrap"><!--게임& 화면들 감싸는 부분-->
        <div class="left-cam"><!--왼쪽 카메라모음--><!--20%-->
          <div v-for="user in evenplayer" :key="user.stream.connection.connectionId" class="playercamera">
            <user-video :stream-manager="user" :game-selected="gameSelected" :start="start" :readyList="readyList"/>
          </div>
        </div>     
        <v-col id="game"> <!--가운데 게임화면-->
          <span v-if="start">
            <span v-if="gameSelected == 'Spyfall'">
              <spyfall :stream-manager="streamManagers" :gameRes="gameRes" :rules="rules"></spyfall>
            </span >
            <span v-if="gameSelected == 'Telestation'">
              <telestation :stream-manager="streamManagers" :gameRes="gameRes" :rules="rules"></telestation>
            </span>
          </span>
          <div v-else><!--대기방 게임 초기화면(게임선택하는곳)-->
            <v-row class="control">
              <v-col class="col-10 row-cols-3 gameselect">
                <v-row style="height:100%;">
                  <v-col>
                    <button class="paper-btn" v-bind:class="{'btn-primary': gameSelected == 'Spyfall'}" @click="gameSelect('Spyfall')">스파이폴</button>
                  </v-col>
                  <v-col>
                    <button class="paper-btn" v-bind:class="{'btn-primary': gameSelected == 'Telestation'}" @click="gameSelect('Telestation')">텔레스테이션</button>
                  </v-col>                 
                </v-row> <!--게임 3개 선택하는 부분-->
              </v-col>
              <v-col class="col-2 ready">        
                <v-col >
                  <button class="paper-btn" style="width:100%;" @click="copyJoinCode(mySessionId)">{{ mySessionId }}</button>
                </v-col>
                <v-col v-if="!isRoomMaker">
                  <button class="paper-btn" style="width:100%;" @click="beReady(myUserName)"><span>레디</span></button>
                </v-col>
                <v-col v-if="isRoomMaker">
                  <button class="paper-btn" style="width:100%;" @click="gameStart(gameSelected)" :disabled="!isReadyToStart"><span>시작</span></button>
                </v-col>
              </v-col>              
            </v-row>
            <div class="gameInfo border"> <!--게임설명-->
              <span v-if="gameSelected == 'Spyfall'">
                <spyfallDescription :gameSelected="gameSelected"></spyfallDescription>
              </span>
              <span v-if="gameSelected == 'Telestation'">
                <telestationDescription :gameSelected="gameSelected"></telestationDescription>
              </span>
            </div>
          </div>
        </v-col>
        <div class="right-cam"> <!--오른쪽 카메라모음--><!--20%-->
          <div v-for="user in oddplayer" :key="user.stream.connection.connectionId" class="playercamera">
            <user-video :stream-manager="user" :game-selected="gameSelected" :start="start" :readyList="readyList"/>
          </div>
        </div>
      </v-row>
    </v-container>    
  </div>
</template>

<script>
import UserVideo from '@/components/Video/UserVideo';
import Spyfall from '@/components/games/Spyfall';
import Telestation from '@/components/games/Telestation.vue';
import SpyfallDescription from '@/components/descriptions/SpyfallDescription';
import TelestationDescription from '@/components/descriptions/TelestationDescription';
import { mapState } from "vuex";
import axios from "axios";

export default {
  name: "Room", 
  data () {
		return {
      gameSelected: 'Spyfall',
      start : false,
      streamManagers : null,
      rules: null,
      gameRes: null,
      readyList: [],
      playerList: [],
		}
	},

	components: {
		UserVideo,
    Spyfall,
    Telestation,
    SpyfallDescription,
    TelestationDescription,
	},

  computed: {
		...mapState([
			"session",
			"myUserName",
			"mySessionId",
			"publisher",
			"subscribers",
			"mainStreamManager",
      "isRoomMaker"
		]),

    oddplayer: function () {
      return this.subscribers.filter((user, index) => {
        return index % 2 === 1
      })
    },

    evenplayer : function() {
      return this.subscribers.filter((user, index) => {
        return index % 2 === 0
      })
    },

    isReadyToStart() {
      // if (this.readyList.length == (this.subscribers.length - 1)) {
      if (this.readyList.length == 1) {
        return true;
      }
      return false;
    }    
	},
  
  watch: {
    subscribers: {
      handler() {
        this.makePlayerList()
      }
    }
  },

  mounted () {    
    this.session.on('signal:getReadyList', (event)=>{
      if (this.myUserName != JSON.parse(event.from.data).clientData){
        this.sendMessageToEveryBody(JSON.stringify(this.readyList),'sendReadyList')
      }
    })

    this.session.on('signal:sendReadyList', (event)=>{    
      this.readyList = JSON.parse(event.data)
    })

    this.session.on('signal:rules', (event) => {
      this.rules = JSON.parse(event.data)
    })

    this.session.on('signal:gameRes', (event)=>{
      this.gameRes = JSON.parse(event.data)
    })

    this.session.on('signal:gameStart', (event)=>{
      this.streamManagers = this.session.streamManagers
      this.gameSelected = event.data
      this.start = true
      this.readyList=[]
    })

    if(this.session.ee._events["signal:initSpyfall"] == undefined) {
      this.session.on('signal:initSpyfall', ()=>{
        this.start = false
        this.$store.commit('INIT_SPYFALL')
      })
    }

    this.session.on('signal:ready', (event)=>{
      const person = event.data
      if (this.readyList.includes(person)) {
        const idx = this.readyList.indexOf(person)
        this.readyList.splice(idx, 1)
      } else {
        this.readyList.push(person)
      }
    })
    
    setTimeout(()=>{
      this.sendMessageToEveryBody('', 'getReadyList')
    }, 500)
  },

  methods : {
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

    beReady() {
      this.sendMessageToEveryBody(this.myUserName,'ready')
    },

    copyJoinCode(joinCode) {
      const joinCodeToCopy = document.createElement("textarea")
      document.body.appendChild(joinCodeToCopy)
      joinCodeToCopy.value = joinCode
      joinCodeToCopy.select()
      document.execCommand("copy")
      alert('복사되었습니다')
      joinCodeToCopy.style.display = 'none'
    },

    leaveSession() {
      this.$store.dispatch('leaveSession')
      this.$router.push('lobby')
		},

    gameSelect(game) {
      this.gameSelected = game
    },

    gameStart(game) {   
      axios.post(
        '/api/games/rules',
        JSON.stringify({
          // personnel: this.subscribers.length,
          personnel: 3,
          selectedGame: game
        })
      )
      .then(res => {
        this.rules=res.data
        this.sendMessageToEveryBody(JSON.stringify(this.rules), 'rules')
        axios.post(
          '/api/games/start',          
          JSON.stringify({
            // userNicknames : this.playerList,
            userNicknames : ["박준영", "정성우", "김범주"],
            roomCode : this.mySessionId,
            selectedGame: game
          }),
        )
        .then(resp =>{            
          this.gameRes = resp.data
          this.sendMessageToEveryBody(JSON.stringify(this.gameRes), 'gameRes')
          this.sendMessageToEveryBody(this.gameSelected, 'gameStart')
        })
        .catch(error => console.log(error))
      })
      .catch(err =>{
        console.log(err)
        alert('게임 가능한 인원 수는 3명 이상 8명 이하 입니다')
      })
    },

    makePlayerList () {
      this.playerList=[]
      for (let index = 0; index < this.subscribers.length; index++) {
        let participant = JSON.parse(this.subscribers[index].stream.connection.data)
        this.playerList.push(participant.clientData)
      }
    }
  } 
}

</script>
<style scoped>
.room {
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
.left-cam, .right-cam {
  width: 16%;
  height: 100%;
  display: flex;
  justify-content: space-around;
  flex-direction: column;
}
.gameInfo {
  margin-top: 10px;
  height: 570px;
  border-bottom-left-radius: 15px 255px;
  border-bottom-right-radius: 225px 15px;
  border-top-left-radius: 21px 12px;
  border-top-right-radius: 15px 225px;
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
.playercamera {
  position: relative;
}
.right-cam > div, .left-cam > div{
  height: 25%;
}
.playercamera > div{
  height: 100%;
}
.room {
  display: flex;
  height: 100vh;
}
/* 메인 화면 */
.room .container {
  width: 1455px;
  height: 730px;
  margin: auto;
}
.wrap {
  height: 100%;
  width: 100%;
}
/* column 패딩 0 주기 */
.room .container .col {
  padding: 0;
}
/* row 패딩 마진 0 주기 */
.room .container .row {
  padding: 0;
  margin: 0;
}
.gameselect .col button {
  height: 100%;
  width: 100%;
}
.ready .col{
  overflow: hidden;
}
#game {
  padding: 10px;
  border-bottom-left-radius: 15px 255px;
  border-bottom-right-radius: 225px 15px;
  border-top-left-radius: 255px 15px;
  border-top-right-radius: 15px 225px;
  border-color: #41403e;
  border-color: var(--primary);
  border-style: solid;
  border-width: 2px;
}
@media (max-width: 1455px) {
  .room{
    width: 1455px;
  }
}
@media (max-height: 730px) {
  .room {
    height: 730px;
  }
}
</style>