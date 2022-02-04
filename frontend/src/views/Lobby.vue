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
						<input v-model="myUserName" class="form-control" type="text" required placeholder="닉네임을 입력하세요">
					</p>
					<p>
						<label>방 참여코드</label>
						<input v-model="joinCode" class="form-control" type="text" required placeholder="참여코드를 입력하세요">
					</p>
					<p class="text-center">
						<button class="btn btn-lg btn-success" @click="$refs.preview.dialog = true">입장하기</button>
					</p>
					<p class="text-center">
						<button class="btn btn-lg btn-success" @click="makeSession(myUserName, joinCode)">방 만들기</button>
					</p>
				</div>
			</div>
		</div>
		</div>
		<preview ref="preview" :joinCode="joinCode" :myUserName="myUserName">
		</preview>
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from 'axios';
import Preview from '@/components/Preview.vue';

export default {
	name: 'Lobby',
	components: {
		Preview,
	},

	data () {
		return {
			myUserName: '',
			joinCode: '',
		}
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
				"myUserName": myUserName,
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
						"myUserName": this.myUserName,
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
		showInsertModal() {
			this.insertModal.show()
		}
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