<template>
  <v-dialog
      v-model="dialog"
      max-width="900"
    >
      <v-card>
        <v-card-title class="text-h5">
          대기화면
        </v-card-title>
        <div class="d-flex">
          <video :srcObject.prop="videoSrc" autoplay></video>
          <div class="videocontrol">
            <div>
              <v-select 
                @change="changeDevice"
                append-icon="mdi-camera-outline"
                v-model="publishInfo.videoSource"
                :items="videoDevices"
                item-text="label"
                item-value="deviceId"
                solo 
                placehoder="video"></v-select>
            </div>
            <div>
              <v-select
                @click="changeDevice"
                append-icon="mdi-volume-high"
                v-model="publishInfo.audioSource" 
                :items="audioDevices"
                item-text="label"
                item-value="deviceId"
                solo 
                ></v-select>
            </div>
            <div class="d-flex justify-content-around">
              <button  class="paper-btn" fab @click="publishInfo.publishAudio = !publishInfo.publishAudio">
                <v-icon>{{ publishInfo.publishAudio ? 'mdi-volume-high' : 'mdi-volume-off' }}</v-icon>
              </button>
              <button class="paper-btn" fab @click="publishInfo.publishVideo = !publishInfo.publishVideo">
                <v-icon>{{ publishInfo.publishVideo ? 'mdi-camera-outline' : 'mdi-camera-off-outline' }}</v-icon>
              </button>
            </div>
          </div>
        </div>

        <v-divider></v-divider>
        <v-card-actions>
          <button
            class="paper-btn"      
            @click="enterRoom"
          >
            입장
          </button>
        </v-card-actions>
      </v-card>
    </v-dialog>
</template>

<script>
import { OpenVidu } from 'openvidu-browser'
import { mapActions } from 'vuex'
export default {
  name: "Preview",
  props: {
    joinCode: String,
    myUserName: String
  },
  data: function () {
    return {
        publishInfo: {
          audioSource: undefined, // The source of audio. If undefined default microphone
          videoSource: undefined, // The source of video. If undefined default webcam
          publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
          publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
          resolution: '640x480',  // The resolution of your video
          frameRate: 30,			// The frame rate of your video
          insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
          mirror: false       	// Whether to mirror your local video or not
        },
      dialog: false,
      OV: undefined,
      videoDevices: [],
      audioDevices: [],
      videoSrc: undefined,
    }
  },
  mounted: function () {
  this.OV = new OpenVidu()//
  },
  methods: {
    ...mapActions([
      'joinSession'
    ]),
    getUserDevices: function() {
      this.OV.getDevices().then( devices => {
        devices.forEach( device => {
          if(device.kind === 'videoinput') {
            this.videoDevices.push(device)
          }
          else {
            this.audioDevices.push(device)
          }
        })
      })
    },
    changeDevice: function () {
      this.OV.getUserMedia({
        audioSource: false,
        videoSource: this.publishInfo.videoSource,
        resolution: '640x480',
        frameRate: 30
      })
      .then(mediaStream => {
        let videoTrack = mediaStream.getVideoTracks()[0];
        this.videoSrc = new MediaStream([videoTrack])
      });
    },
    enterRoom: function () {
      let roomInfo = {
        sessionId: String(this.joinCode),
        userName: this.myUserName,
        publishInfo: this.publishInfo
      }
      console.log(roomInfo, 'roomInfo')
      this.joinSession(roomInfo)
      try {
        this.videoSrc.getTracks()[0].stop()
      }
      catch {
        roomInfo
      }
      this.$router.push({ name: 'Room', params: { joinCode: this.joinCode}})
    }
  },
  computed: {
    // ...mapState('accounts', [
    //   'user'
    // ])
  },
  
  watch: {
    dialog: function () {
      if (this.dialog) {
        this.getUserDevices()
        this.changeDevice()
      }
      else {
        this.videoSrc.getTracks()[0].stop()
      }
    }
  }
};
</script>

<style scoped>
  video {
    width: 450px;
    margin-left: 20px;
    border-bottom-left-radius: 15px 255px;
    border-bottom-right-radius: 225px 15px;
    border-top-left-radius: 255px 15px;
    border-top-right-radius: 15px 225px;
    border-color: #41403e;
    border-color: var(--primary);
    border-style: solid;
    border-width: 2px;
  }
  .videocontrol {
    display: flex;
    flex-direction: column;
    margin-left: 20px;
  }
</style>