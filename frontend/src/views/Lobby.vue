<template>
  <div class="lobby">
    <div class="myface">
      <img src="../assets/logo.png" alt="logo">
    
    <div id="join" v-if="!session">
			<div id="join-dialog" class="vertical-center">
				<h1>LOBBY</h1>
				<div >
					<p>
						<label>닉네임</label>
						<input v-model="userData.userNickname" class="form-control" type="text" required placeholder="닉네임을 입력하세요">
					</p>
					<p>
						<label>방 참여코드</label>
						<input v-model="joinCode" class="form-control" type="text" required placeholder="참여코드를 입력하세요">
					</p>
					<p class="text-center">
						<button class="btn btn-lg btn-success" @click="$refs.preview.dialog = true">입장하기</button>
					</p>
					<p class="text-center">
						<button class="btn btn-lg btn-success" @click="makeSession(userData.userNickname, joinCode)">방 만들기</button>
					</p>
				</div>
			</div>
		</div>
		</div>
		<preview ref="preview" :joinCode="joinCode" :myUserName="userData.userNickname">
		</preview>
  </div>
</template>

<script>
import axios from 'axios';
import { mapState } from "vuex";
import Preview from '@/components/Preview.vue';

export default {
	name: 'Lobby',

	data () {
		return {
			userData : {
				userName : null,
				userEmail : null,
				userNickname : null,
			},
			joinCode: '',
			myUserName: '',
		}
	},
	components: {
		Preview,
	},
	computed: {
		...mapState([
			"session",
			"publisher",
			"subscribers",
			"mainStreamManager",
		])
	},    
	methods: {
		joinSession(myUserName, joinCode) {
			const payload = {
				"myUserName": userData.userNickname,
				"joinCode": joinCode
			}
			this.$store.dispatch('joinSession', payload)
			this.$router.push('room')			
			//axios({
      //  method: 'get',
      //  url:`local 8080/api/rooms/${joinCode}`,
				// data: joinCode
      //})
			//	.then(res=>{
			//		console.log(res)
			//		this.$store.dispatch('joinSession', payload)
			//		this.$router.push('room')					
			//	})//
		},
		makeSession: function () {
      axios({
        method: 'post',
        url:'local 8080/api/rooms',
      })
        .then(res=>{
          console.log(res)
          // localStorage.setItem('jwt', res.data.token)
          // this.$emit('login')
					const payload = {
						"myUserName": this.userData.userNickname,
						"joinCode": this.joinCode
					}
					this.$store.dispatch('joinSession', payload)
          this.$router.push({
					name: 'Room',
					params: { joinCode: this.joinCode }
					})
        })
        .catch(err=> {
          console.log(err)
        })

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

<style scoped>
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