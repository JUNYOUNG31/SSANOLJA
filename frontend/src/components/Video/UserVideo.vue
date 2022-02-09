<template>

<div v-if="streamManager" style="display: flex; align-items: center;">
	<ov-video :stream-manager="streamManager"/>
	<!--<div id="p-name"><p>{{ clientData }}</p></div>-->
	<div><i v-if="ready" class="fas fa-check-circle"></i></div>
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

		
	},

	methods: {
			getConnectionData () {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
	},
};
</script>