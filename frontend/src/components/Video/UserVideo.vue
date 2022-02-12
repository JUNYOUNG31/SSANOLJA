<template>

<div v-if="streamManager" class="camera">
	<ov-video class="myvideo" :stream-manager="streamManager"/>
	<div id="p-name"><p>{{ clientData }}</p></div>
</div>
</template>

<script>
import OvVideo from './OvVideo';
export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	props: {
		streamManager: Object,
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
	},
};
</script>

<style scoped>
.camera {
	display: flex;
	align-items: center;
	position: relative;
	overflow: hidden;
}
#p-name {
	position: absolute;
	top:150%;
	left:100px;
	z-index: 2;
	color:white;
	background-color: black;
	transition: all .35s;
}
.camera:hover #p-name {
	top: 85%;
}
</style>