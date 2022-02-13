<template>
<div v-if="streamManager" style="display: flex; align-items: center;" class="video_div child-borders">
	<ov-video :stream-manager="streamManager" v-if="answerPlayer != streamManager && questionPlayer != streamManager && firstQuestionPlayer != streamManager"/>
	<div v-else ></div>
	<div v-if="gameSelected == 'Spyfall' && start" class="btn1"><button class="paper-btn" @click="answerSelect" :disabled="isMyself || !(isAnswerPlayer || isFirstQuestionPlayer) || isQuestionPlayer">지목하기</button></div>
	<div v-if="gameSelected == 'Spyfall' && start" class="btn2" ><button class="paper-btn" @click="voteSelect" :disabled="isMyself || voteClick">투표하기</button></div>
	<div v-if="ready"><button class="btn3 paper-btn btn-success">READY!</button></div>
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
			isAnswerPlayer : false,
			isFirstQuestionPlayer : false,
			isMyself: false,
			isQuestionPlayer: false
		}
	},

	props: {
		streamManager: Object,
		gameSelected : String,
		start : Boolean,
		readyList: Array,
	},

	computed: {		
		...mapState([
			"session",
			"subscribers",
			"firstQuestionPlayer",
			"questionPlayer",
			"answerPlayer",
			"selectPlayer",
			"votePlayer",
			"voteClick",       // 투표버튼 클릭 여부
			"myUserName"
		]),
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

	},

	watch: {
		answerPlayer: function() {
			if (this.answerPlayer) {
				if (this.myUserName == JSON.parse(this.answerPlayer.stream.connection.data).clientData) {
					this.isAnswerPlayer = true
				}
				else {
				this.isAnswerPlayer = false
				}
			}
			else {
				this.isAnswerPlayer = false
			}
		},

		firstQuestionPlayer: function() {
			if (this.firstQuestionPlayer) {
				if (this.myUserName == JSON.parse(this.firstQuestionPlayer.stream.connection.data).clientData) {
					this.isFirstQuestionPlayer = true
				}
				else {
				this.isFirstQuestionPlayer = false
				}
			}
			else {
				this.isFirstQuestionPlayer = false
			}			
		},

		questionPlayer: function () {
			if (this.questionPlayer) {
				if (this.clientData == JSON.parse(this.questionPlayer.stream.connection.data).clientData) {
					this.isQuestionPlayer = true
				}
				else {
				this.isQuestionPlayer = false
				}
			}
			else {
				this.isQuestionPlayer = false
			}
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
		},

		getConnectionData () {
		const { connection } = this.streamManager.stream;
		return JSON.parse(connection.data);
		},
		voteSelect () {
			this.$store.commit('SET_VOTECLICK')
			this.voteVideo = JSON.parse(this.streamManager.stream.connection.data)
			this.sendMessageToEveryBody(JSON.stringify(this.voteVideo), 'votePlayer')
		},
		answerSelect () {
			this.answerVideo = JSON.parse(this.streamManager.stream.connection.data)		
			this.sendMessageToEveryBody(JSON.stringify(this.answerVideo), 'answerPlayer')
		}
	},
	mounted() {
		this.isMyself = (this.myUserName === this.clientData)


		// if(this.session.ee._events["signal:votePlayer"] == undefined) {
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
			this.$store.commit('SET_VOTEPLAYER', this.voteVideo)	
    })
		// }


		if(this.session.ee._events["signal:answerPlayer"] == undefined) {

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
				this.$store.commit('SET_FIRSTQUESTIONPLAYER', null)
				this.$store.commit('SET_QUESTIONPLAYER', this.questionVideo)
				this.$store.commit('SET_ANSWERPLAYER', this.answerVideo)
			})		
		}
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
.video_div .btn3 {
	position: absolute;
	bottom: 0;
	left: 35%;
	padding: 7px;
}

</style>