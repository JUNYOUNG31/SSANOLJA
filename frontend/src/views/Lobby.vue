<template>
  <div class = "lobby">
    <div class="myface">
      <img src="../assets/logo.png" alt="logo">
    </div>
      <v-btn>
        <router-link :to="{ name: 'Room' }">방만들기</router-link> 
      </v-btn>
      <br><br>     
      <v-btn>게임참가</v-btn>
      <br>
      <div>
        <input type="text" style="border: 1px solid white">
        <br>
        <br>
      </div>
      <v-btn>닉네임</v-btn>
      <br>
      <div>
        <input type="text" style="border: 1px solid white">
      </div>
  </div>
</template>

<script>
export default {
  name : "Lobby"
}
</script>

<style>
.lobby {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;  
  border: hidden;
}
.lobby div input{
  color: white;
}
.lobby .myface {
  width: 300px;
}
.lobby .myface img {
  width: 100%;
}

.go-or-make {
  display: flex;
  justify-content: center;
  align-items: center;  
}
.go-or-make button{
  margin: 10px;
  width: 100px;
}

.lobby > img {
  width: 300px;
  margin-top: 100px;
  margin-bottom: 100px;
}
</style>