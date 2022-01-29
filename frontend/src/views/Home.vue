<template>
  <div class="home">
    <img src="../assets/logo.png" alt="logo">
      <div class="form-group">
        <div>
          <label>아이디</label>
          <input v-model="userId" class="form-control" type="text" required>
          <br>
          <label>비밀번호</label>
          <input v-model="userPassword" class="form-control" type="text" required>
        </div>
        <div class="login-button">
          <button class="btn btn-lg btn-success">
            <router-link :to="{ name: 'Lobby' }">Login</router-link> 
          </button>
        </div>
      </div>
    <section class="test">
    <div v-on:click="GoogleLoginBtn" class="googlelogin">
      <img src="../assets/googlelogo.png" alt="googlelogo" width="40">
      <a class="grey--text">Sign in with Google</a>      
    </div>
    <div id="my-signin2" style="display: none"></div>
    </section>
    <br>
    <div class="signup-button">
      <button class="btn btn-lg btn-primary">
        <router-link :to="{ name: 'SignUp' }">회원가입</router-link> 
      </button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'


  export default {
    name: 'Home',    
    data () {
      return {
        userData : {
        userId : null,
        userPassword : null,
        } 
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
        console.log(googleUser)
        this.$router.push('lobby')
      }
    },
    
    //구글 로그인 콜백함수 (실패)
    GoogleLoginFailure(error) {
      console.log(error);
    },
    // axios
    login: function () {
      axios({
        method: 'post',
        url:'#',
        data: this.userData
      })
        .then(res=>{
          console.log(res)
          // localStorage.setItem('jwt', res.data.token)
          // this.$emit('login') 
          this.$router.push({name:'Lobby'})
        })
        .catch(err=> {
          console.log(err)
          alert("로그인에 실패했습니다. 아이디/비밀번호를 확인하세요")
        })
      this.$emit('submit-login-data', this.loginData)
    }


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
  margin-top: 50px;
  margin-bottom: 50px;
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
.form-group {
  display: flex;
}

.login-button {
  margin-left: 30px;
  align-self: center;
}

.login-button button {
  width: 100px;
  height: 100px;
}
.login-button button a {
  color: white;
}

.signup-button button {
  width: 300px;
}

.signup-button button a {
  color: white;
}




</style>
