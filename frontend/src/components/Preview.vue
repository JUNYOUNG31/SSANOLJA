<template>
  <v-dialog v-model="dialog" max-width="900">
    <v-card>
      <div>
        <v-card-title>
        <h3 style="font-family: 'GowunDodum-Regular'">대기화면</h3>
        </v-card-title>
      </div>
      <div class="d-flex">
        <video :srcObject.prop="videoSrc" autoplay></video>
        <div class="videoselect">
          <div>
            <label> 비디오 선택</label>
            <select @change="changeDevice" v-model="publishInfo.videoSource" placehoder="video" style="border-style: solid; width: 350px">
              <option v-for="(item,index) in videoDevices" :key="index" :value="item.deviceId">{{item.label}}</option>
            </select>
            <label> 오디오 선택</label>
            <select @click="changeDevice" v-model="publishInfo.audioSource" style="border-style: solid">
              <option v-for="(item, index) in audioDevices" :key="index" :value="item.deviceId">{{item.label}}</option>
            </select>
          </div>
          <div class="videocontrol">
            <div>
              <button class="paper-btn btn-secondary" fab @click="publishInfo.publishAudio = !publishInfo.publishAudio">
                <v-icon>{{ publishInfo.publishAudio ? 'mdi-volume-high' : 'mdi-volume-off' }}</v-icon>
              </button>
              <button class="paper-btn btn-secondary" fab @click="publishInfo.publishVideo = !publishInfo.publishVideo">
                <v-icon>{{ publishInfo.publishVideo ? 'mdi-camera-outline' : 'mdi-camera-off-outline' }}</v-icon>
              </button>
              </div>
            <button class="paper-btn enter btn-success" @click="enterRoom">입장</button>
          </div>
        </div>
      </div>
      <v-divider></v-divider>
      <v-card-actions>          
      </v-card-actions>
      </v-card>
    </v-dialog>
</template>

<script>
import { OpenVidu } from 'openvidu-browser'
import { mapActions } from 'vuex'
export default {
  name: "Preview",  
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

  props: {
    joinCode: String,
    myUserName: String,
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
  },

  mounted: function () {
    this.OV = new OpenVidu()
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
  .videoselect {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    margin-left: 20px;
  }
  .videoselect div select{
    margin-bottom: 20px;
  }

  .videocontrol  {
    display: flex;
    justify-content: space-between;
  }

  .videocontrol button {
    margin-right: 20px;
  }
  .videocontrol .enter {
    margin: 0;
  }
  .v-card {
    background-color: rgb(255, 255, 255);
    color: black;
  }

  .v-btn {
    margin:0 20px;
  }
</style>