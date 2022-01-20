<template>
  <div class = "lobby">
    <img src="../assets/logo.png" alt="logo">
    <v-container>
      <v-col class="go-or-make">
        <v-btn>
          <router-link :to="{ name: 'Room' }">방만들기</router-link> 
        </v-btn>
        <div>
          <v-btn>게임참가</v-btn>
          <br>
          <input type="text">
        </div>
      </v-col>
    </v-container>
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