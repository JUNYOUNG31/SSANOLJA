<template>
  <div class="home">
    <img src="../assets/logo.png" alt="logo">
    <section class="test">
    <div v-on:click="GoogleLoginBtn" class="googlelogin">
      <img src="../assets/googlelogo.png" alt="googlelogo" width="40">
      <a class="grey--text">Sign in with Google</a>      
    </div>
    <div id="my-signin2" style="display: none"></div>
    </section>
    <br>
    <router-link :to="{ name: 'Lobby' }">로비로 가기</router-link> 
    <br>
    <router-link :to="{ name: 'Room' }">대기실 가기</router-link>
  </div>
</template>

<script>
  // import HelloWorld from '../components/HelloWorld'

  export default {
    name: 'Home',
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
        console.log(googleUser)
        this.$router.push('Lobby')
      }
    },
    
    //구글 로그인 콜백함수 (실패)
    GoogleLoginFailure(error) {
      console.log(error);
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
.googlelogin {
  display: flex;
  text-decoration: none;
  background: whitesmoke;
  padding: 5px 5px;
}
.googlelogin:hover{
  background: lightgray;
}

.googlelogin  a {
  margin-top: 8px; 
}


</style>
