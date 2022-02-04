<template>
  <div class = "lobby">
    <div class="myface">
      <img src="../assets/logo.png" alt="logo">
				<div id="join" v-if="!session">
					<div id="join-dialog" class="jumbotron vertical-center">
						<h1>LOBBY</h1>
						<div >
							<p>
								<label>닉네임</label>
								<input v-model="myUserName" class="form-control" type="text" required>
							</p>
							<p>
								<label>방 참여코드</label>
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
import axios from 'axios';
import { mapState } from "vuex";
// import UserVideo from '@/components/Video/UserVideo';

export default {
	name: 'Lobby',

	data () {
		return {
			userData : {
				userName : null,
				userEmail : null,
				userNickname : null,
			},
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
		},
		getUserDate() {
			axios({
					method:'GET',
					url: '/sendUser'
			})
			.then(res => {
					this.userData.userName = res.data.userName
					this.userData.userNickname = res.data.userNickname
					this.userData.userEmail = res.data.userEmail
					console.log(this.userData)
			})
			.catch(err => console.log(err))
		}
	},
	created() {
		this.getUserDate()
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