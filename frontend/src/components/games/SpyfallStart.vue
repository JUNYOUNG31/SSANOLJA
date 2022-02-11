<template>
  <div>
    <div>
      <p>직업을 확인해주세요. 3초 뒤 게임이 시작됩니다</p>
      <div id="job_place_tag"><h3><span>장소</span></h3></div>
      <div id="job_place_tag">
        <img :src="placeSrc" />
      </div>
      <div id="job_place_tag"><h3><span>직업</span></h3></div>
      <div id="job_place_tag"><h3><span>{{job}}</span></h3></div>
    </div>
  </div>
</template>

<script>
import {mapState} from 'vuex';

export default {
  name: "SpyfallStart",
  

  props: {
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
        }, 3000);
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

<style>

</style>