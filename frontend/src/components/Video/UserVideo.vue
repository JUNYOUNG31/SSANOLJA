<template>
<div v-if="streamManager" style="display: flex; align-items: center;" class="video_div">
	<ov-video :stream-manager="streamManager" v-if="answerPlayer != streamManager && votePlayer != streamManager"/>
	<div v-else ></div>
	<div v-if="gameSelected == 'Spyfall' && start" class="btn1"><v-btn @click="answerSelect">지목하기</v-btn></div>
	<div v-if="gameSelected == 'Spyfall' && start" class="btn2"><v-btn @click="voteSelect">투표하기</v-btn></div>
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
      answerVideo: null,
      voteVideo : null
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
      "answerPlayer",
      "votePlayer",
		])
	},

	methods: {
	getCircularReplacer() {
		const seen = new WeakSet();
		return (key, value) => {
			if (typeof value === "object" && value !== null) {
				if (seen.has(value)) {
					return;
				}
				seen.add(value);
			}
			return value;
		};
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
			for (let index = 0; index < this.subscribers.length; index++) {
        let nickName = JSON.parse(this.subscribers[index].stream.connection.data)
				if (votedata.clientData == nickName.clientData) {
					this.voteVideo = this.subscribers[index]
				}
			}
			this.$store.commit('SET_ANSWERPLAYER', null)
			this.$store.commit('SET_VOTEPLAYER', this.voteVideo)	
    })

		this.session.on('signal:answerPlayer', (event)=>{
			const answerdata = JSON.parse(event.data)
			for (let index = 0; index < this.subscribers.length; index++) {
        let nickName = JSON.parse(this.subscribers[index].stream.connection.data)
				if (answerdata.clientData == nickName.clientData) {
					this.answerVideo = this.subscribers[index]
				}
			}
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