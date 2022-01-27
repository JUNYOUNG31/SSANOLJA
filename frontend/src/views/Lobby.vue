<template>
  <div class = "lobby">
    <div class="myface">
      <img src="../assets/logo.png" alt="logo">
    
    <div id="join" v-if="!session">
			<div id="join-dialog" class="jumbotron vertical-center">
				<h1>LOBBY</h1>
				<div class="form-group">
					<p>
						<v-btn>닉네임</v-btn>
						<br><br>
						<input v-model="myUserName" class="form-control" type="text" required>
					</p>
					<p>
						<v-btn>방 참여코드</v-btn>
						<br><br>
						<input v-model="joinCode" class="form-control" type="text" required>
					</p>
					<p class="text-center">
						<button class="btn btn-lg btn-success" @click="joinSession()">입장하기</button>
					</p>
				</div>
			</div>
		</div>
		</div>
  </div>
</template>

<script>
import { mapState } from "vuex";
// import UserVideo from '@/components/Video/UserVideo';

export default {
	name: 'Lobby',

	// components: {
	// 	UserVideo,
	// },

	data () {
		return {

			joinCode: 'SessionA',
		}
	},

	computed: {
		...mapState([
			"session",
			"myUserName",
			"mySessionId",
			"publisher",
			"subscribers",
			"mainStreamManager",
		])
	},

	
	methods: {
		joinSession() {
			this.$store.dispatch('joinSession')
      this.$router.push('room')
		},
		leaveSession() {
			this.$store.dispatch('leaveSession')
		},
		updateMainVideoStreamManager(data) {
			this.$store.dispatch('updateMainVideoStreamManager',data)
		}
	}
}
</script>

<style>
.lobby {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;  
  border: hidden;
}
.lobby div input{
  color: black;
}
.lobby .myface {
  width: 300px;
}
.lobby .myface img {
  width: 100%;
}

.go-or-make {
  display: flex;
  justify-content: center;
  align-items: center;  
}
.go-or-make button{
  margin: 10px;
  width: 100px;
}

.lobby > img {
  width: 300px;
  margin-top: 100px;
  margin-bottom: 100px;
}
</style>