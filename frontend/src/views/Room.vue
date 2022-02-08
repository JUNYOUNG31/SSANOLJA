<template>
  <div class="room"> <!--전체화면-->
    <v-container fluid> <!--게임& 화면들 감싸는 부분-->
      <v-row class="wrap"><!--게임& 화면들 감싸는 부분-->

        <div class="left-cam"><!--왼쪽 카메라모음--><!--20%-->
          <div class="playercamera">
            <user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)"/>
          </div>
          <div v-for="user in oddplayer" :key="user.stream.connection.connectionId" class="playercamera">
            <user-video :stream-manager="user" @click.native="updateMainVideoStreamManager(user)"/>
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
                <v-col >
                  <v-btn style="width:100%;">
                    <span>레디</span>
                    </v-btn>
                </v-col>
                <v-col>
                  <v-btn style="width:100%;" @click="gameStart(gameSelected)">
                    <span>시작</span>
                  </v-btn>
                </v-col>
              </v-col>
              
            </v-row>

            <div class="gameInfo">게임설명 <!--게임설명-->
            </div>
          </div>

            <!-- <v-btn @click="socketTest()">소켓 테스트</v-btn>
            <p>{{message}}</p> -->
        </v-col>


          <div class="right-cam"> <!--오른쪽 카메라모음--><!--20%-->
            <div v-for="user in evenplayer" :key="user.stream.connection.connectionId" class="playercamera">
              <user-video :stream-manager="user" @click.native="updateMainVideoStreamManager(user)"/>
            </div>
          </div>
      </v-row>
    </v-container>    
  </div>
</template>


<script>
import UserVideo from '@/components/Video/UserVideo';
import Spyfall from '@/components/games/Spyfall';
import { mapState } from "vuex";
import Fakeartist from '@/components/games/Fakeartist.vue';
import Telestation from '@/components/games/Telestation.vue';
import axios from "axios";
export default {
  name: "Room", 
  data () {
		return {
      gameSelected: '',
      message: null,
      start : false,
      spyFallVideo : null,
      rules: null,
      gameRes: null,

		}
	},

	components: {
		UserVideo,
    Spyfall,
    Fakeartist,
    Telestation,
	},

  computed: {
		...mapState([
			"session",
			"myUserName",
			"mySessionId",
			"publisher",
			"subscribers",
			"mainStreamManager",
		]),
    oddplayer: function () {
      return this.subscribers.filter((user, index) => {
        return index % 2 === 1
      })
    }
    ,
    evenplayer : function() {
      return this.subscribers.filter((user, index) => {
        return index % 2 === 0
      })
    },
    
	},
  mounted () {
    this.session.on('signal:session-test', (event) => {
    console.log(event.data, '이것은 데이터'); // Message
    console.log(event.from, '이것은 메시지 보낸사람'); // Connection object of the sender
    console.log(event.type, '이것은 메시지 타입'); // The type of message
    this.message=event.data
    
});
  },
  methods : {
    socketTest() {
      this.$store.dispatch('socketTest') // 소켓 테스트
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
      this.spyFallVideo = this.mainStreamManager
    },
    gameStart(game) {
      
      axios.post(
        '/api/games/rules',

        JSON.stringify({
          personnel: 6, // userNicknames의 길이로 대체
          selectedGame: game
        })
      )
      .then(res => {
          this.rules=res.data
          axios
          .post(
            '/api/games/start',
            
            JSON.stringify({
              userNicknames : ["조성현", "정성우", "박준영", "김범주","붕어뺭","강광은"],
              roomCode : this.mySessionId,
              selectedGame: game
            }),
          )
          .then(resp =>{
            
            this.gameRes = resp.data
            this.start = true
            
            console.log("gameRes 확인 " + this.gameRes)
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
