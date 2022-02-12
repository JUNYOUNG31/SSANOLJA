<template>
  <div class="home">
    <img src="../assets/logo.png" alt="logo">
    <section class="test">
    <div v-on:click="GoogleLoginBtn">
      <button class="paper-btn btn-lg btn-warning black--text d-flex" >
          <img src="../assets/googlelogo.png" alt="googlelogo" width="40">
          <p 
          style="margin: 8px 0 8px; font-family: Patrick Hand SC, sans-serif; font-weight: normal;" witdh="40">
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
        theme: 'dark',
        onsuccess: this.GoogleLoginSuccess,
        onfailure: this.GoogleLoginFailure,
      });

      setTimeout(function () {
        if (!self.googleLoginCheck) {
          const auth = window.gapi.auth2.getAuthInstance();
          auth.isSignedIn.get();
          document.querySelector('.abcRioButton').click();
        }
      }, 1500)

    },
    async GoogleLoginSuccess(googleUser) {
      const googleEmail = googleUser.getBasicProfile().getEmail();
      if (googleEmail !== 'undefined') {
        console.log(googleEmail);
        console.log(googleUser);
        console.log(googleUser.getBasicProfile().getEmail());
        this.userData.userEmail = googleUser.getBasicProfile().getEmail();
        this.userData.userNickname = googleUser.getBasicProfile().getName();
        console.log("==============");
        console.log(this.userData);
        console.log("==============");
      }
      this.insertUser()
    },
    //구글 로그인 콜백함수 (실패)
    GoogleLoginFailure(error) {
      console.log(error);
    },
    insertUser: function() {
      axios({
                method:'POST',
                url: '/api/login/postTest',
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
      // .then(res => {
      //   console.log(res)
      //   this.$router.push( { name: 'Lobby', params: { sendUserEmail: this.userData.userEmail}})
      // })
            .catch(err => console.log(err))

    },
  }
}    // components: {
    //   HelloWorld,
    // },

</script>

<style>
.home {
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
</style>