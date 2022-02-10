<template>
  <div style="display : flex">
    <div v-if="spyPlayer">
      <ov-video :stream-manager="spyPlayer"/>
    </div>

    <div class="place_check">
        <div>              
          <button class="place1" @click="decide('경찰서')"><div id="x1" style="display:none"></div><p>경찰서</p></button>  
          <button class="place2" @click="decide('자동차_정비소')"><div id="x2" style="display:none"></div><p>자동차 정비소</p></button>  
          <button class="place3" @click="decide('학교')"><div id="x3" style="display:none"></div><p>학교</p></button>  
          <button class="place4" @click="decide('레스토랑')"><div id="x4" style="display:none"></div><p>레스토랑</p></button>  
          <button class="place5" @click="decide('영화_촬영소')"><div id="x5" style="display:none"></div><p>영화 촬영소</p></button> 
        </div>
        <div>
          <button class="place6" @click="decide('대사관')"><div id="x6" style="display:none"></div><p>대사관</p></button>  
          <button class="place7" @click="decide('병원')"><div id="x7" style="display:none"></div><p>병원</p></button>  
          <button class="place8" @click="decide('대형마트')"><div id="x8" style="display:none"></div><p>대형마트</p></button>  
          <button class="place9" @click="decide('공연장')"><div id="x9" style="display:none"></div><p>공연장</p></button>  
          <button class="place10" @click="decide('해변')"><div id="x10" style="display:none"></div><p>해변</p></button> 
        </div>
        <div>
          <button class="place11" @click="decide('우주_정거장')"><div id="x11" style="display:none"></div><p>우주 정거장</p></button>  
          <button class="place12" @click="decide('잠수함')"><div id="x12" style="display:none"></div><p>잠수함</p></button>  
          <button class="place13" @click="decide('놀이공원')"><div id="x13" style="display:none"></div><p>놀이공원</p></button>  
          <button class="place14" @click="decide('공항')"><div id="x14" style="display:none"></div><p>공항</p></button>  
          <button class="place15" @click="decide('대학_연구실')"><div id="x15" style="display:none"></div><p>대학 연구실</p></button> 
        </div>
        <div>
          <button class="place16" @click="decide('호텔')"><div id="x16" style="display:none"></div><p>호텔</p></button>  
          <button class="place17" @click="decide('은행')"><div id="x17" style="display:none"></div><p>은행</p></button>  
          <button class="place18" @click="decide('카지노')"><div id="x18" style="display:none"></div><p>카지노</p></button>  
          <button class="place19" @click="decide('회사_송년회')"><div id="x19" style="display:none"></div><p>회사 송년회</p></button>  
          <button class="place20" @click="decide('동물원')"><div id="x20" style="display:none"></div><p>동물원</p></button> 
        </div>
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
      spyPlayer : null,
      spyWin : false,
      citizenWin : false,
      
    }
  },

  props: {
    spyName : String,
    place: String,
  },

  components: {
    OvVideo,
  },
  computed: {
    ...mapState([
      "subscribers",
    ])
  },
  mounted() {
    for (let index = 0; index < this.subscribers.length; index++) {
        let nickName = JSON.parse(this.subscribers[index].stream.connection.data)
				if (this.spyName == nickName.clientData) {
          this.spyPlayer = this.subscribers[index]
				}
			}

  },

  methods: {
    // decide(here) {
    //   if (this.place == here) {
    //     this.spyWin = true
    //   } else {
    //     this.citizenWin = true
    //   }
    // }
  }
}
</script>

<style>
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