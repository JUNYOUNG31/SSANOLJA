<template>
  <div class="home">
    <img src="../assets/logo.png" alt="logo">
    <a href="#">로그인 하기</a>
    <br>
    <router-link :to="{ name: 'Room' }">대기실 가기</router-link>
  </div>
</template>

<script>
  // import HelloWorld from '../components/HelloWorld'

  export default {
    name: 'Home',

    // components: {
    //   HelloWorld,
    // },
  }
</script>

<style>
.home {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;  
}
.home img {
  width: 300px;
  margin-top: 100px;
  margin-bottom: 100px;
}
</style>
