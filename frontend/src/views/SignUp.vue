<template>
  <div class="home">
    <img src="../assets/logo.png" alt="logo">
    <div class="form-signup">
      <label>아이디</label>
      <input v-model="userId" class="form-control" type="text" required>
      <label>비밀번호</label>
      <input v-model="userPassword" class="form-control" type="text" required>
      <label>이메일</label>
      <input v-model="userEmail" class="form-control" type="text" required>
      <label>닉네임</label>
      <input v-model="userNickName" class="form-control" type="text" required>
    </div>  
    <br>
    <router-link :to="{ name: 'Home' }">가입하기</router-link> 
    <br>
  </div>
</template>

<script>
  // import HelloWorld from '../components/HelloWorld'

  export default {
    name: 'SignUp',    
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

