<template>
  <div class="spyfall-container">
    <h1>스파이폴</h1>
  </div>
</template>

<script>
// import OvVideo from '../Video/OvVideo.vue';
export default {
  name: 'Spyfall',

  props: {
  streamManager: Object,
	},
  // components: {
	// 	OvVideo,
	// },
  
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
}
</script>

<style scoped>

.spyfall-container {
  height: 640px;
}
.spyfall-container .container{
  padding: 0;
}

.Question_video {
  position: relative;
}
.Answer_video {
  position: relative;
}
#p-name {
  position: absolute;
  bottom: 0px;
  margin: 0 auto;
  height: 21px;
}
#p-name p{  
  margin: 0;
}
video {
  width: 400px;
}

.right_menu {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.place_check {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.place_check button {
  height: 90px;
  width: 120px;
}
</style>