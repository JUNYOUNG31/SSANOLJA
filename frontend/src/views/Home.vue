<template>
  <div class="home">
    <img src="../assets/logo.png" alt="logo">
    <span v-if="!isLogin">
      <section class="test">
      <div @click="login">      
        <button class="btn btn-lg btn-primary grey--text">
          <img src="../assets/googlelogo.png" alt="googlelogo" width="40">
          Sign in with Google
        </button>      
      </div>
      </section>
    </span>
    <span v-else>
      <div>
        <button class="btn btn-lg btn-danger"><router-link :to="{ name: 'Lobby' }">방 만들기</router-link></button>
      </div>
      <div>
        <button class="btn btn-lg btn-primary" @click="loginOk">로그아웃</button>
      </div>
    </span>
  </div>
</template>
<script>
import axios from 'axios'

  export default {
    name: 'Home',    
    data () {
      return {
        isLogin : sessionStorage.getItem('isLogin')
      }
    },
    methods: {
      login: function () {
        axios.get('/oauth2/authorization/google',{
          // headers: {
          //   'Access-Control-Allow-Origin': '*',
          // //   'Content-Type': 'application/json; charset = utf-8'
          // },      
          // data: this.userData
          // params: { redirect_url: "http://localhost:5500/lobby" }
        })
        .then(res => {
          location.href = `${res.request.responseURL}`
          // localStorage.setItem('jwt', res.data.token)
          sessionStorage.setItem('isLogin', true);
        })
        .catch(err=> {
          console.log(err)
        })
    },
    loginOk: function() {
      sessionStorage.clear()
      this.$router.go('Home')
    }
  },
}
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