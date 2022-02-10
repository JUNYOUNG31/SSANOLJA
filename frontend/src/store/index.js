import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';

axios.defaults.headers.post['Content-Type'] = 'application/json';

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
		OPENVIDU_SERVER_URL : "https://i6e106.p.ssafy.io:9002",
    // OPENVIDU_SERVER_URL : "https://" + location.hostname + ":4443",
    OPENVIDU_SERVER_SECRET : "MY_SECRET",
    OV: undefined,
    session: undefined,
    mainStreamManager: undefined,
    publisher: undefined,
    subscribers: [],
		questionPlayer: null,
		answerPlayer: null,
		selectPlayer: null,
		votePlayer: null,		
		dialog : false,
		voteClick : false,
		citizenWin : false,
		spyWin : false,
    mySessionId: '',
    myUserName: '',
		isRoomMaker: localStorage.getItem('isRoomMaker') ==='true',

  },
  mutations: {
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
      // --- Leave the session by calling 'disconnect' method over the Session object ---
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

		SET_ANSWERPLAYER: function(state, value) {
			state.answerPlayer = value;
			console.log(state.answerPlayer)
		},
		SET_VOTEPLAYER: function(state, value) {
			state.votePlayer = value;
			if (state.votePlayer == null) {
				state.dialog = false
			}
			if (state.votePlayer != null) {
				state.dialog = true
			}
			state.voteClick = true;
			console.log(state.votePlayer)
		},
		SET_SELECTPLAYER: function(state, value) {
			state.selectPlayer = value;
			console.log(state.selectPlayer)
		},
		SET_QUESTIONPLAYER: function(state, value) {
			state.questionPlayer = value;
			console.log(state.questionPlayer)
		},
		CITIZEN_WIN: function(state) {
			state.citizenWin = true
		},

		SPY_WIN: function(state) {
			state.spyWin = true
		},		    
  },
  actions: {
    joinSession: function ({ commit, dispatch, state}, data) {
			// --- Get an OpenVidu object ---
			commit("CHANGE_JOININFO", data)
      commit("GET_OVOBJ")			
			// --- Init a session ---
      commit("INIT_SESSION")			
			// --- Specify the actions when events take place in the session ---      
			// On every new Stream received...
      // commit("STREAM_CREATED")
      state.session.on('streamCreated', ({ stream }) => {
        const subscriber = state.session.subscribe(stream);
				state.subscribers.push(subscriber);
			});

			// On every Stream destroyed...
			// commit("STREAM_DESTROYED")
      state.session.on('streamDestroyed', ({ stream }) => {
        const index = state.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
          state.subscribers.splice(index, 1);
				}
			});
    
			// On every asynchronous exception...
			// commit("WARN_EXCEPTION")
      state.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});
			// --- Connect to the session with a valid user token ---
			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend
      
      dispatch('getToken', state.mySessionId).then(token =>{
          state.session.connect(token, { clientData: state.myUserName })
            .then(() => {  
              // --- Get your own camera stream with the desired properties ---              
              let publisher = state.OV.initPublisher(undefined, data.publishInfo);  
              state.mainStreamManager = publisher;
							state.subscribers.push(publisher) // subscribers 에 publisher 추가
              state.publisher = publisher;  
              // --- Publish your stream ---
              state.session.publish(state.publisher);
            })
            .catch(error => {
              console.log('There was an error connecting to the session:', error.code, error.message);
            });
        });
			
      // 임시로 주석처리함
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
      
      console.log(this.publisher)
      console.log(stream)
			if (state.mainStreamManager === stream) return;
      commit('UPDATE_MAINVIDEO_STREAMMANAGER')
			
		},

		/**
		 * --------------------------
		 * SERVER-SIDE RESPONSIBILITY
		 * --------------------------
		 * These methods retrieve the mandatory user token from OpenVidu Server.
		 * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
		 * the API REST, openvidu-java-client or openvidu-node-client):
		 *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
		 *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
		 *   3) The Connection.token must be consumed in Session.connect() method
		 */

		getToken: function ({ dispatch }, data) {
      return dispatch('createSession', data).then(sessionId => dispatch('createToken', sessionId));
			// return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
		createSession: function ({ state }, sessionId) {
			const promise = new Promise((resolve, reject) => {
				axios
					.post(`${state.OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
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

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
		createToken: function ({ state }, sessionId ) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${state.OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
						auth: {
							username: 'OPENVIDUAPP',
							password: state.OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		}
  },
  modules: {
  }
})
