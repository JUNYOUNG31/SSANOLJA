<template>
<div v-if="streamManager" style="display: flex; align-items: center;">
	<ov-video :stream-manager="streamManager"/>
	<!--<div id="p-name"><p>{{ clientData }}</p></div>-->
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