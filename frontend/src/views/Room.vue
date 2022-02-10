<template>
  <div class="room"> <!--전체화면-->
    <v-container fluid> <!--게임& 화면들 감싸는 부분-->
      <v-row class="wrap"><!--게임& 화면들 감싸는 부분-->
        <div class="left-cam"><!--왼쪽 카메라모음--><!--20%-->
          <!-- <div class="playercamera">
            <user-video :stream-manager="publisher" :game-selected="gameSelected" :start="start" :readyList="readyList"/> -->
          <!-- </div> -->
          <div v-for="user in evenplayer" :key="user.stream.connection.connectionId" class="playercamera">
            <user-video :stream-manager="user" :game-selected="gameSelected" :start="start" :readyList="readyList"/>
          </div>
        </div>     
        <v-col id="game"> <!--가운데 게임화면-->
          <span v-if="start">
            <span v-if="gameSelected == 'Spyfall'">
              <spyfall :stream-manager="spyFallVideo" :gameRes="gameRes" :rules="rules"></spyfall>
            </span >
            <span v-if="gameSelected == 'Fakeartist'">
              <fakeartist :stream-manager="spyFallVideo" :gameRes="gameRes" :rules="rules"></fakeartist>
            </span>
            <span v-if="gameSelected == 'Telestation'">
              <telestation :stream-manager="spyFallVideo" :gameRes="gameRes" :rules="rules"></telestation>
            </span>
          </span>
          <div v-else><!--대기방 게임 초기화면(게임선택하는곳)-->
            <v-row class="control">
              <v-col class="col-10 row-cols-3 gameselect">
                  <v-row style="height:100%;">
                    <v-col>
                      <v-btn v-bind:class="{'grey': gameSelected == 'Spyfall'}" @click="gameSelect('Spyfall')">스파이폴</v-btn>
                    </v-col>
                    <v-col>
                      <v-btn v-bind:class="{'grey': gameSelected == 'Fakeartist'}" @click="gameSelect('Fakeartist')">가예누가</v-btn>
                    </v-col>
                    <v-col>
                      <v-btn v-bind:class="{'grey': gameSelected == 'Telestation'}" @click="gameSelect('Telestation')">텔레스테이션</v-btn>
                    </v-col>                 
                  </v-row> <!--게임 3개 선택하는 부분-->
              </v-col>
              <v-col class="col-2 ready">        
                <v-col >
                  <v-btn style="width:100%;">
                    <span>
                      <v-btn @click="copyJoinCode(mySessionId)">{{ mySessionId }}</v-btn>
                    </span>
                  </v-btn>
                </v-col>
                <v-col v-if="!isRoomMaker">
                  <v-btn style="width:100%;" @click="beReady(myUserName)" :disabled="isReadyToStart">
                    <span>레디</span>
                  </v-btn>
                </v-col>
                <v-col v-if="isRoomMaker">
                  <v-btn style="width:100%;" @click="gameStart(gameSelected)" :disabled="!isReadyToStart">
                    <span>시작</span>
                  </v-btn>
                </v-col>
              </v-col>              
            </v-row>
            <div class="gameInfo">게임설명 <!--게임설명-->
            <span v-if="gameSelected == 'Spyfall'">
              <spyfallDescription :gameSelected="gameSelected"></spyfallDescription>
            </span>
            <span v-if="gameSelected == 'Fakeartist'">
              <fakeartistDescription :gameSelected="gameSelected"></fakeartistDescription>
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
import Fakeartist from '@/components/games/Fakeartist.vue';
import Telestation from '@/components/games/Telestation.vue';
import SpyfallDescription from '@/components/descriptions/SpyfallDescription';
import FakeartistDescription from '@/components/descriptions/FakeartistDescription';
import TelestationDescription from '@/components/descriptions/TelestationDescription';
import { mapState } from "vuex";
import axios from "axios";

export default {
  name: "Room", 
  data () {
		return {
      gameSelected: 'Spyfall',
      start : false,
      spyFallVideo : null,
      rules: null,
      gameRes: null,
      readyList: []
		}
	},

	components: {
		UserVideo,
    Spyfall,
    Fakeartist,
    Telestation,
    SpyfallDescription,
    FakeartistDescription,
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
    // data에 userNicknames 배열이 생기면 활성화
    isReadyToStart() {
      // if (this.readyList.length == (this.userNicknames.length - 1)) {
      if (this.readyList.length == 0) {
        return true;
      }
      return false;
      
    }
    
	},
  mounted () {
    // 방 입장시 준비된 사람들 리스트를 받아옴
    this.sendMessageToEveryBody('getReadyList', 'getReadyList')

    this.session.on('signal:getReadyList', ()=>{
      let readyListToString = this.readyList.toString()
      this.sendMessageToEveryBody(readyListToString,'sendReadyList')
    })

    this.session.on('signal:sendReadyList', (event)=>{
      this.readyList = event.data.split(",")
    })

    this.session.on('signal:rules', (event) => {
    this.rules = JSON.parse(event.data)
    }),

    this.session.on('signal:gameRes', (event)=>{
      this.gameRes = JSON.parse(event.data)
    })

    this.session.on('signal:gameStart', (event)=>{
      this.spyFallVideo = this.session.streamManagers
      this.gameSelected = event.data
      this.start = true
      if (this.gameSelected == "Spyfall") {
        const firstquestionplayerdata = this.session.streamManagers[Math.floor(Math.random() * this.session.streamManagers.length)]
        this.$store.commit("SET_FIRSTQUESTIONPLAYER", firstquestionplayerdata)
      }
      this.beReady() // 게임 시작시 레디 해제
    })

    this.session.on('signal:backToLobby', ()=>{
      this.start = false
    })

    this.session.on('signal:ready', (event)=>{
      const person = event.data
      console.log(person)
      if (this.readyList.includes(person)) {
        const idx = this.readyList.indexOf(person)
        this.readyList.splice(idx, 1)
      } else {
        this.readyList.push(person)
      }
      console.log(this.readyList)
    })
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
          personnel: 3, // userNicknames의 길이로 대체
          selectedGame: game
        })
      )
      .then(res => {
          this.rules=res.data
          this.sendMessageToEveryBody(JSON.stringify(this.rules), 'rules')
          axios
          .post(
            '/api/games/start',
            
            JSON.stringify({
              userNicknames : ["정성우", "박준영", "김범주"],
              roomCode : this.mySessionId,
              selectedGame: game
            }),
          )
          .then(resp =>{
            
            this.gameRes = resp.data
            this.sendMessageToEveryBody(JSON.stringify(this.gameRes), 'gameRes')
            this.sendMessageToEveryBody(this.gameSelected, 'gameStart')
            // this.start = true
          })
          .catch(error => console.log(error))

      })
      .catch(err =>{
        console.log(err)
        alert('게임 가능한 인원 수는 3명 이상 8명 이하 입니다')
      })
    },
  }
}

</script>
<style scoped>
/* 배조 */
.left-cam,
.right-cam {
  width: 16%;
  height: 100%;
  display: flex;
  justify-content: space-around;
  flex-direction: column;
}

.gameInfo{
  height: 560px;
}


/* 배조 */
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
.right-cam > div,
.left-cam > div{
  height: 25%;
}
.playercamera > div{
  height: 100%;
}
.room div {
  border: 1px solid white;  
}
.room{
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
