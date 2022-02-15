<template>
  <div class="home">
    <div class="bouncing-text">
      <div class="S1"><h1>S</h1></div>
      <div class="S2"><h1>S</h1></div>
      <div class="A1"><h1>A</h1></div>
      <div class="N"><h1>N</h1></div>
      <div class="O"><h1>O</h1></div>
      <div class="L"><h1>L</h1></div>
      <div class="J"><h1>J</h1></div>
      <div class="A2"><h1>A</h1></div>
      <div class="shadow"></div>
      <div class="shadow-two"></div>
      <div class="shadow-three"></div>
      <div class="shadow-four"></div>
    </div>
    <section class="test">
    <div v-on:click="GoogleLoginBtn">
      <button class="paper-btn btn-lg black--text d-flex" style="width: 500px; height: 220px" >
          <img src="../assets/googlelogo.png" alt="googlelogo" width="130px" style="margin-top: 30px">
          <p 
          style="margin: 60px 0 8px; font-family: Patrick Hand SC, sans-serif; font-weight: normal; font-size: 50px" witdh="370px">
          Sign in with Google
          </p> 
        </button> 
    </div>
    <div id="my-signin2" style="display: none"></div>
    </section>
  </div>
</template>

<script>
  // import HelloWorld from '../components/HelloWorld'
import axios from 'axios'
export default {
  name: 'Home',
  data() {
    return {
      userData : {
        userEmail : null,
        userNickname : null,
      },
    }
  },
  methods: {
    GoogleLoginBtn:function(){
      var self = this;
      window.gapi.signin2.render('my-signin2', {
        scope: 'profile email',
        width: 240,
        height: 50,
        longtitle: true,
        theme: "dark",
        onsuccess: this.GoogleLoginSuccess,
        onfailure: this.GoogleLoginFailure,
      });
      setTimeout(function () {
        if (!self.googleLoginCheck) {
          const auth = window.gapi.auth2.getAuthInstance();
          auth.isSignedIn.get();
          document.querySelector(".abcRioButton").click();
        }
      }, 1500)
    },
    async GoogleLoginSuccess(googleUser) {
      const googleEmail = googleUser.getBasicProfile().getEmail();
      if (googleEmail !== "undefined") {
        this.userData.userEmail = googleUser.getBasicProfile().getEmail();
        this.userData.userNickname = googleUser.getBasicProfile().getName();
      }
      this.insertUser();
    },
      //구글 로그인 콜백함수 (실패)
    GoogleLoginFailure(error) {
      console.log(error);
    },
    insertUser: function () {
      axios({
                method:'POST',
                url: '/api/login/userLogin',
         data: { 
           userEmail : this.userData.userEmail,
           userNickname : this.userData.userNickname
         }
            }).then(res => {
        console.log(res.data)
                if(res.data == "ok"){
           this.$router.push( { name: 'Lobby', params: { sendUserEmail: this.userData.userEmail}})
         }
            })
            .catch(err => console.log(err))

    },
  },
};
</script>

<style>
.home {
  margin-top: 50px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.home > img {
  width: 300px;
  margin-top: 100px;
  margin-bottom: 100px;
}
/* 로고 */
.bouncing-text {
  position: relative;
  display: flex;  
  font-family: "Patrick Hand SC", sans-serif;
  margin-bottom: 120px;
  margin-top: 120px;
}

.S1, .S2, .A1, .N, .O, .A2 {
  color: rgb(0, 0, 0);
  margin-right: 20px;
  text-shadow: 0 3px #a1a1a1, 0 5px #a1a1a1, 0 7px #a1a1a1;
}

.L {
  color: rgb(0, 0, 0);
  margin-right: 25px;
  text-shadow: 0 3px #a1a1a1, 0 5px #a1a1a1, 0 7px #a1a1a1;
}

.J {
  color: rgb(0, 0, 0);
  margin-right: 25px;
  text-shadow: 0 3px #a1a1a1, 0 5px #a1a1a1, 0 7px #a1a1a1;
}

.S1 h1, .S2 h1, .A1 h1, .N h1, .O h1, .L h1, .J h1, .A2 h1 {
  margin: 0;
  font-size: 250px;
}

@keyframes bounce {
        0%   { transform: scale(1,1) translateY(0); }
        10%  { transform: scale(1.1,.9) translateY(0); }
        30%  { transform: scale(.9,1.1)   translateY(-55px);}
        50%  { transform: scale(1.05,.95) translateY(0); }
        58%  { transform: scale(1,1) translateY(-7px); }
        65%  { transform: scale(1,1) translateY(0);}
        100% { transform: scale(1,1) translateY(0);}
    }

.S1 {
  animation: bounce 2s ease infinite;
}

.S2 {
  animation: bounce 2s ease infinite .2s;
}

.A1 {
  animation: bounce 2s ease infinite .4s;
}

.N {
  animation: bounce 2s ease infinite .6s;
}

.O {
  animation: bounce 2s ease infinite .8s;
}

.L {
  animation: bounce 2s ease infinite 1.0s;
}

.J {
  animation: bounce 2s ease infinite 1.2s;
}

.A2 {
  animation: bounce 2s ease infinite 1.4s;
}


.shadow, .shadow-two, .shadow-three, .shadow-four {
  position: absolute;
  bottom: 0;
}

.shadow:before, .shadow:after, .shadow-two:before, .shadow-two:after, .shadow-three:before, .shadow-three:after, .shadow-four:before, .shadow-four:after {
  content:"";
  position: absolute;
  background-color: rgba(0,0,0,0.4);
  width: 114px;
  height:5px;
  border-radius:50%;
  top: -20px;  
}

.bouncing-text:before {
  bottom: 0;
  animation: scale 2s linear infinite;
}

@keyframes scale {
  0% {transform: scaleX(1);}
  25% {transform: scaleX(0.4);}
  50% {transform: scaleX(1);}
  75% {transform: scaleX(0.9);}
  100% {transform: scaleX(1);}
}

.shadow:before {
  left: 0;
  animation: scale 2s linear infinite .4s;
}

.shadow:after {
  left:135px;
  animation: scale 2s linear infinite .6s;
}

.shadow-two:before {
  left:276px;
  animation: scale 2s linear infinite .8s;
}

.shadow-two:after {
  left: 420px;
  animation: scale 2s linear infinite 1.0s;
}

.shadow-three:before {
  left: 580px;
  animation: scale 2s linear infinite 1.2s;
}

.shadow-three:after {
  left: 710px;
  animation: scale 2s linear infinite 1.4s;
}

.shadow-four:before {
  left:830px;
  animation: scale 2s linear infinite 1.2s;
}

.shadow-four:after {
  left: 960px;
  animation: scale 2s linear infinite 1.4s;
}
</style>