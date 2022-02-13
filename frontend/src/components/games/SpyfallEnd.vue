<template>
  <div style="display : flex">
    <div v-if="!spyWin && !citizenWin">
      <p>
        스파이가 나타났습니다!
      </p>
      <p>
        스파이가 시민들이 있는 장소를 추리하는 중 입니다
      </p>
      </div>
    <div v-if="spyPlayer" class="child-borders">
      <ov-video :stream-manager="spyPlayer"/>
    </div>

    <div v-if="(!spyWin && !citizenWin) && isSpy" class="place_check">
        <div>              
          <button class="place1 paper-btn" @click="decide('경찰서')"><p>경찰서</p></button>  
          <button class="place2 paper-btn" @click="decide('자동차_정비소')"><p>자동차 정비소</p></button>  
          <button class="place3 paper-btn" @click="decide('학교')"><p>학교</p></button>  
          <button class="place4 paper-btn" @click="decide('레스토랑')"><p>레스토랑</p></button>  
          <button class="place5 paper-btn" @click="decide('영화_촬영소')"><p>영화 촬영소</p></button> 
        </div>
        <div>
          <button class="place6 paper-btn" @click="decide('대사관')"><p>대사관</p></button>  
          <button class="place7 paper-btn" @click="decide('병원')"><p>병원</p></button>  
          <button class="place8 paper-btn" @click="decide('대형마트')"><p>대형마트</p></button>  
          <button class="place9 paper-btn" @click="decide('공연장')"><p>공연장</p></button>  
          <button class="place10 paper-btn" @click="decide('해변')"><p>해변</p></button> 
        </div>
        <div>
          <button class="place11 paper-btn" @click="decide('우주_정거장')"><p>우주 정거장</p></button>  
          <button class="place12 paper-btn" @click="decide('잠수함')"><p>잠수함</p></button>  
          <button class="place13 paper-btn" @click="decide('놀이공원')"><p>놀이공원</p></button>  
          <button class="place14 paper-btn" @click="decide('공항')"><p>공항</p></button>  
          <button class="place15 paper-btn" @click="decide('대학_연구실')"><p>대학 연구실</p></button> 
        </div>
        <div>
          <button class="place16 paper-btn" @click="decide('호텔')"><p>호텔</p></button>  
          <button class="place17 paper-btn" @click="decide('은행')"><p>은행</p></button>  
          <button class="place18 paper-btn" @click="decide('카지노')"><p>카지노</p></button>  
          <button class="place19 paper-btn" @click="decide('회사_송년회')"><p>회사 송년회</p></button>  
          <button class="place20 paper-btn" @click="decide('동물원')"><p>동물원</p></button> 
        </div>
      </div>
      <div v-if="spyWin">
        <p>스파이 승리</p>
      </div>
      <div v-if="citizenWin">
        <p>시민 승리</p>
      </div>
      <div v-if="isRoomMaker & (spyWin || citizenWin)">
        <v-btn style="width:100%;" @click="backToRoom()">
            <span>게임 선택하기</span>
        </v-btn>
      </div>
  </div>
</template>

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
  },

  components: {
    OvVideo,
  },
  computed: {
    ...mapState([
      "session",
      "subscribers",
      "spyWin",
      "citizenWin",
      "isRoomMaker"
    ])
  },
  mounted() {
    
    if(this.session.ee._events["signal:spyWin"] == undefined) {
      this.session.on('signal:spyWin', ()=>{
        this.$store.commit("SPY_WIN")
      })
    }

    if(this.session.ee._events["signal:citizenWin"] == undefined) {
      this.session.on('signal:citizenWin', () =>{
        this.$store.commit("CITIZEN_WIN")
      })
    }
  },

  methods: {
    backToRoom() {
      this.sendMessageToEveryBody('','backToRoom')
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
    }
  }
}
</script>

<style scoped>
.place_check {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.place_check button {
  height: 90px;
  width: 120px;
  border-radius: 10px;
  position: relative;
}
.place_check button > p {
  font-weight: bold;
  margin: 0;
  background-color: rgba(68, 68, 68, 0.3);
}

.place_check button > div {
  height: 90px;
  width: 120px;
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

</style>