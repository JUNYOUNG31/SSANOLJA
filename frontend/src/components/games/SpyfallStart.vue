<template>
  <div>
    <div class="spyfallstart">
      <h1 style="margin-top:30px">SPYFALL</h1>
      <div class="border" style="width:500px; height:500px; background-color: white;">       
        <div class="alert alert-primary" style="width:500px; text-align:center; height:50px">
          <h5 style="margin: 0">장소와 직업을 확인해주세요. 5초 뒤 게임이 시작됩니다</h5>
        </div>
        <div class="citizenpick" v-if="!isSpy">
          <h5 class="border alert-secondary" style="background-color: white;">당신은 시민 입니다. 스파이를 찾아주세요</h5>
          <div class="citizenrule">
            <img :src="placeSrc" style="width:300px" class="border"/>
            <div style="">
              <label for="">장소</label>
              <h4 class="alert">{{place}}</h4>
              <label for="">직업</label>
              <h4 class="alert">{{job}}</h4>    
            </div>
          </div>
        </div>
        <div class="spypick" v-else>
          <img src="../../assets/Spy.jpg" alt="" style="width:380px">
          <h5 class="border">당신은 스파이 입니다. 장소를 추리하세요</h5>
        </div>
      </div>      
    </div>    
  </div>
</template>

<script>
import {mapState} from 'vuex';

export default {
  name: "SpyfallStart",

  props: {
    isSpy : Boolean,
    job: String,
    placeSrc: String,
    place: String,
  },

  computed: {
		...mapState([
      "session",
      "isRoomMaker"
		]),
	},

  mounted() {
    if(this.isRoomMaker) {
      setTimeout(() => {
        const ramdomquestionplayerdata = this.session.streamManagers[Math.floor(Math.random() * this.session.streamManagers.length)]
        const firstquestionplayerdata = JSON.parse(ramdomquestionplayerdata.stream.connection.data)
        this.sendMessageToEveryBody(JSON.stringify(firstquestionplayerdata), 'setFirstQuestionPlayer')
        this.$store.commit("SET_FIRSTQUESTIONPLAYER", ramdomquestionplayerdata)
      }, 5000);
    }    
  },

  methods: {
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
		}
  }
}
</script>

<style scoped>
.spyfallstart {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;  
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
.spyfallstart h5, .spyfallstart h4{
  font-family: 'GowunDodum-Regular';
}
.border {
  border-bottom-left-radius: 9px 255px;
  border-bottom-right-radius: 225px 15px;
  border-top-left-radius: 187px 4px;
  border-top-right-radius: 4px 45px;
  display: flex;
  align-items: center;
  flex-direction: column;
}
.spypick {
  position: relative;
}
.spypick h5 {
  position: absolute;
  top: 87%;
  left: 10%;
  margin: 0;
  background-color: rgb(33 32 32 / 93%);
  border-bottom-right-radius: 656px 39px;
  padding: 13px;
  color: white;  
}
.citizenpick {
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
}
.citizenrule {
  text-align: center;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  width: 100%;
}
.citizenpick h5 {
  padding: 13px;
  margin-top: 0;
  margin-bottom: 30px;
}
.citizenpick h4 {
  padding: 5px;
  background-color: white;
  width: 190px;
  margin-bottom: 50px ;
}
</style>