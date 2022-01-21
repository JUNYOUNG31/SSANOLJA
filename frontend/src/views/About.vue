<template>
	<div id="main-container" class="container">
		<div id="join" v-if="!session">
			<div id="join-dialog" class="jumbotron vertical-center">
				<h1>로비</h1>
				<div class="form-group">
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

		<div id="session" v-if="session">
			<div id="session-header">
				<h1 id="session-title">{{ joinCode }}</h1>
				<input class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="leaveSession" value="나가기">
			</div>
			<!-- 방장 화면: 필요없음 -->
			<!-- <div id="main-video" class="col-md-6">
				<user-video :stream-manager="mainStreamManager"/>
			</div> -->
			<div id="video-container" class="col-md-6">
				<user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)"/>
				<user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click.native="updateMainVideoStreamManager(sub)"/>
			</div>
		</div>
	</div>
</template>

<script>
import { mapState } from "vuex";
import UserVideo from '@/components/Video/UserVideo';

export default {
	name: 'App',

	components: {
		UserVideo,
	},

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

