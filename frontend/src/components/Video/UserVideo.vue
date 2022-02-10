<template>
<div v-if="streamManager" style="display: flex; align-items: center;" class="video_div">
	<ov-video :stream-manager="streamManager" v-if="answerPlayer != streamManager && votePlayer != streamManager"/>
	<div v-else ></div>
	<div v-if="gameSelected == 'Spyfall' && start" class="btn1"><v-btn @click="answerSelect">지목하기</v-btn></div>
	<div v-if="gameSelected == 'Spyfall' && start" class="btn2" ><v-btn @click="voteSelect" :disabled="voteClick">투표하기</v-btn></div>
	<div><i v-if="ready" class="fas fa-check-circle"></i></div>
	<p></p>
</div>
</template>

<script>
import OvVideo from './OvVideo';
import {mapState} from 'vuex';
export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	data () {
		return {			
			questionVideo: null,
      answerVideo: null,
      voteVideo : null,
			selectVideo: null,
		}
	},

	props: {
		streamManager: Object,
		gameSelected : String,
		start : Boolean,
		readyList: Array,
	},

	computed: {		
		ready() {
			if (this.readyList.includes(this.clientData)) {
				return true;
			} else{
				return false;
			}
		},
		
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},		
		...mapState([
			"session",
			"subscribers",
			"questionPlayer",
      "answerPlayer",
			"selectPlayer",
      "votePlayer",
			"voteClick"       // 투표버튼 클릭 여부
		])
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
		},

		getConnectionData () {
		const { connection } = this.streamManager.stream;
		return JSON.parse(connection.data);
		},
		voteSelect () {
			this.voteVideo = JSON.parse(this.streamManager.stream.connection.data)
			this.sendMessageToEveryBody(JSON.stringify(this.voteVideo), 'votePlayer')
		},
		answerSelect () {
			this.answerVideo = JSON.parse(this.streamManager.stream.connection.data)		
			this.sendMessageToEveryBody(JSON.stringify(this.answerVideo), 'answerPlayer')
		}
	},
	mounted() {
		this.session.on('signal:votePlayer', (event)=>{
			const votedata = JSON.parse(event.data) 
			const selectdata = JSON.parse(event.from.data)
			for (let index = 0; index < this.subscribers.length; index++) {
        let nickName = JSON.parse(this.subscribers[index].stream.connection.data)
				if (votedata.clientData == nickName.clientData) {
					this.voteVideo = this.subscribers[index]
				}
				if (selectdata.clientData == nickName.clientData) {
					this.selectVideo = this.subscribers[index]
				}
			}
			
			this.$store.commit('SET_SELECTPLAYER', this.selectVideo)
			// this.$store.commit('SET_ANSWERPLAYER', null)
			// this.$store.commit('SET_QUESTIONPLAYER', null)
			this.$store.commit('SET_VOTEPLAYER', this.voteVideo)	
    })

		this.session.on('signal:answerPlayer', (event)=>{
			const questiondata = JSON.parse(event.from.data)
			const answerdata = JSON.parse(event.data)
			for (let index = 0; index < this.subscribers.length; index++) {
        let nickName = JSON.parse(this.subscribers[index].stream.connection.data)
				if (answerdata.clientData == nickName.clientData) {
					this.answerVideo = this.subscribers[index]
				}
				if (questiondata.clientData == nickName.clientData) {
				this.questionVideo = this.subscribers[index]
				}
			}
			this.$store.commit('SET_QUESTIONPLAYER', this.questionVideo)
			this.$store.commit('SET_ANSWERPLAYER', this.answerVideo)
    })		
	}
};
</script>


<style scoped>
.video_div {
	position: relative;
}

.video_div .btn1 {
	position: absolute;
	top: 20%;
	left: 30%;
	display: none;
}
.video_div .btn2 {
	position: absolute;
	top: 60%;
	left: 30%;
	display: none;
}
.video_div:hover .btn1 {
	display: block;
}

.video_div:hover .btn2 {
	display: block;
}

</style>