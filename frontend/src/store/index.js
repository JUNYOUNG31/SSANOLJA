import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';

axios.defaults.headers.post['Content-Type'] = 'application/json';

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
		OPENVIDU_SERVER_URL : "https://i6e106.p.ssafy.io:9002",
    OPENVIDU_SERVER_SECRET : "MY_SECRET",
    OV: undefined,
    session: undefined,
    mainStreamManager: undefined,
    publisher: undefined,
    subscribers: [],																							//publisher 추가
		firstQuestionPlayer: null,																		// 처음 질문하는 사람
		questionPlayer: null,																					// 질문하는 사람
		answerPlayer: null,																						// 질문받는 사람
		selectPlayer: null,																						// 투표지목한 사람
		votePlayer: null,																							// 투표당하는 사람
		dialog : false,																								// 투표 화면
		citizenWin : false,																						// 시민 승리
		spyWin : false,																								// 스파이 승리
		voteClick : false,																						// 1인당 투표 횟수 1
    mySessionId: '',
    myUserName: '',
		isRoomMaker: localStorage.getItem('isRoomMaker') === 'true',  //방장
		sendUserEmail: ''																							// 방나가기 위한 params
  },

  mutations: {
		SET_ROOMMAKER: function(state) {
			state.isRoomMaker = true
		},

		SET_GUEST: function(state) {
			state.isRoomMaker = false
		},

		CHANGE_JOININFO: function(state, data) {
			state.mySessionId = data.sessionId
			state.myUserName = data.userName
		},

    GET_OVOBJ: function(state) {
      state.OV = new OpenVidu();
    },

    INIT_SESSION: function (state) {      
      state.session = state.OV.initSession();      
    },
    
    WARN_EXCEPTION: function (state) {
      state.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});
    },

    LEAVE_SESSION: function (state) {
			if (state.session) state.session.disconnect();
			state.session = undefined;
			state.mainStreamManager = undefined;
			state.publisher = undefined;
			state.subscribers = [];
			state.OV = undefined;
    },

    UPDATE_MAINVIDEO_STREAMMANAGER: function (state, stream) {
      state.mainStreamManager = stream;
    },

		SET_FIRSTQUESTIONPLAYER: function(state, value) {
			state.firstQuestionPlayer = value
		},

		SET_ANSWERPLAYER: function(state, value) {
			state.answerPlayer = value;
		},

		SET_VOTEPLAYER: function(state, value) {
			state.votePlayer = value;
			if (state.votePlayer == null) {
				state.dialog = false
			}
			if (state.votePlayer != null) {
				state.dialog = true
			}
		},

		SET_SELECTPLAYER: function(state, value) {
			state.selectPlayer = value;
		},

		SET_QUESTIONPLAYER: function(state, value) {
			state.questionPlayer = value;
		},
		SET_VOTECLICK: function (state) {
			state.voteClick = true
		},
		
		CITIZEN_WIN: function(state) {
			state.citizenWin = true
		},

		SPY_WIN: function(state) {
			state.spyWin = true
		},

		INIT_SPYFALL: function(state) {
			state.spyWin = false
			state.citizenWin = false
			state.firstQuestionPlayer = null
			state.questionPlayer = null
			state.answerPlayer = null
			state.selectPlayer = null
			state.votePlayer = null
			state.dialog = false
			state.voteClick = false
			state.session.off('signal:voteTrue')
			state.session.off('signal:voteFalse')
			state.session.off('signal:spyfall')
			state.session.off('signal:restart')
		},

		SET_SENDUSEREMAIL: function(state, value) {
			state.sendUserEmail = value
		}
  },	

  actions: {
    joinSession: function ({ commit, dispatch, state}, data) {
			commit("CHANGE_JOININFO", data)
      commit("GET_OVOBJ")	
      commit("INIT_SESSION")		
      state.session.on('streamCreated', ({ stream }) => {
        const subscriber = state.session.subscribe(stream);
				state.subscribers.push(subscriber);
			});
      state.session.on('streamDestroyed', ({ stream }) => {
        const index = state.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
          state.subscribers.splice(index, 1);
				}
			});
      state.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});      
      dispatch('getToken', state.mySessionId).then(token =>{
				state.session.connect(token, { clientData: state.myUserName })
					.then(() => {
						let publisher = state.OV.initPublisher(undefined, data.publishInfo);  
						state.mainStreamManager = publisher;
						state.subscribers.push(publisher) // subscribers 에 publisher 추가
						state.publisher = publisher;  
						state.session.publish(state.publisher);
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
        });
			window.addEventListener('beforeunload', this.leaveSession);
		},

		leaveSession: function ({state }) {
      if (state.session) state.session.disconnect();
			state.session = undefined;
			state.mainStreamManager = undefined;
			state.publisher = undefined;
			state.subscribers = [];
			state.OV = undefined;
			window.removeEventListener('beforeunload', this.leaveSession);
		},

		updateMainVideoStreamManager: function ({ commit, state }, stream) {   
			if (state.mainStreamManager === stream) return;
      commit('UPDATE_MAINVIDEO_STREAMMANAGER')			
		},
		
		getToken: function ({ dispatch }, data) {
      return dispatch('createSession', data).then(sessionId => dispatch('createToken', sessionId));		
		},

		createSession: function ({ state }, sessionId) {
			const promise = new Promise((resolve, reject) => {
				axios.post(
					`${state.OPENVIDU_SERVER_URL}/openvidu/api/sessions`, 
					JSON.stringify({customSessionId: sessionId,}),{	
						auth: {
						username: 'OPENVIDUAPP',
						password: state.OPENVIDU_SERVER_SECRET,
					},
				})
				.then(response => response.data)
				.then(data => resolve(data.id))
				.catch(error => {
					if (error.response.status === 409) {
						resolve(sessionId);
					} else {
						console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${state.OPENVIDU_SERVER_URL}`);
						if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${state.OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${state.OPENVIDU_SERVER_URL}"`)) {
							location.assign(`${state.OPENVIDU_SERVER_URL}/accept-certificate`);
						}
						reject(error.response);
					}
				});
			});
      return promise
		},

		createToken: function ({ state }, sessionId ) {
			return new Promise((resolve, reject) => {
				axios.post(
					`${state.OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
					JSON.stringify({
						"type": "WEBRTC",
						"role": "PUBLISHER",
						"kurentoOptions": {
							"allowedFilters": ["GStreamerFilter", "FaceOverlayFilter","ChromaFilter"]
						}
					}),
					{
						auth: {
							username: 'OPENVIDUAPP',
							password: state.OPENVIDU_SERVER_SECRET,
						},
					}
				)
				.then(response => response.data)
				.then(data => resolve(data.token))
				.catch(error => reject(error.response));
			});
		},

		setRoomMaker: function({ commit }, flag) {
			if (flag) {
				commit("SET_ROOMMAKER")
			}else {
				commit("SET_GUEST")
			}
		}
  },

  modules: {
  }
})