<template>
<div v-if="streamManager" style="display: flex; align-items: center;" class="video_div">
	<ov-video :stream-manager="streamManager"/>
	<div v-if="gameSelected == 'Spyfall' && start" class="btn1"><v-btn @click="answerPlayer">지목하기</v-btn></div>
	<div v-if="gameSelected == 'Spyfall' && start" class="btn2"><v-btn @click="votePlayer">투표하기</v-btn></div>
	<p></p>
</div>
</template>

<script>
import OvVideo from './OvVideo';
export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	data () {
		return {			
      answerplayer: null,
      voteplayer : null
		}
	},

	props: {
		streamManager: Object,
		gameSelected : String,
		start : Boolean
	},

	computed: {		
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},		
	},

	methods: {
		getConnectionData () {
		const { connection } = this.streamManager.stream;
		return JSON.parse(connection.data);
		},
		votePlayer () {
			this.voteplayer = this.streamManager
			this.$store.commit('SET_VOTEPLAYER', this.voteplayer)	
		},
		answerPlayer () {
			this.answerplayer = this.streamManager			
			this.$store.commit('SET_ANSWERPLAYER', this.answerplayer)	
		}
	},
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