<template>
  <div class="home">
    <img src="../assets/logo.png" alt="logo">
    <div class="form-signup" style="form-signup">
      <label>아이디</label>
      <input v-model="userId" type="text" name="userId" id="userId" placeholder="Id" :style="input">
        <br>
      <label>비밀번호</label>
        <input  v-model="userPassword" type="text" name="userPassword" id="userPassword" placeholder="Password" :style="input">
        <br>
        <label>이메일</label>
      <input v-model="userEmail" type="text" name="userPasuserEmailsword" id="userEmail" placeholder="Email" :style="input">
            <br>
      <label>닉네임</label>
      <input v-model="userNickName" type="text" name="userNickName" id="userNickName" placeholder="NickName" :style="input">
      <br>
      <v-btn>
      <router-link :to="{ name: 'Home' }">가입하기</router-link>
      </v-btn>

    </div>  
    <br>
  </div>
</template>

<script>
  export default {
    name: 'Signup',    
    data () {
      return {
        userId : null,
        userPassword : null,
        userEmail : null,
        userNickName : null,
      }
    },
  }
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Poppins&display=swap');
    .form-signup {
        background: rgba(255, 255, 255, 0.4);
        position: absolute;
        top: 20%;
        left: 30%;
        width: 40%;
        text-align: center;
        padding: 5px;
        border-radius: 3rem;
        box-shadow: 0px 0px 10px 0px;
        padding-top: 3%;
        padding-bottom: 5%;
        font-family: 'Poppins', sans-serif;
    }
    h1 {
        cursor: default;
        user-select: none;
    }
    input {
        border-radius: 3rem;
        border: none;
        padding: 10px;
        text-align: center;
        outline: none;
        margin: 10px;
        width: 30%;
        box-sizing: border-box;
        font-family: 'Poppins', sans-serif;
        font-weight: 400;
    }
    input:hover {
        box-shadow: 0px 0px 5px 0px;
    }
    input:active {
        box-shadow: 0px 0px 5px 0px;
    }
    .button {
        cursor: pointer;
        user-select: none;
    }
    img:hover {
        box-shadow: 0px 0px 5px 0px;
        cursor: pointer;
    }
</style>