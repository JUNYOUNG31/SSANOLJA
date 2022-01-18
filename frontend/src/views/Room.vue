<template>
  <div>
    <img src="../assets/logo.png" alt="logo" width="200">
    <v-container class="player-row">
      <v-row>

      <div v-for="player in players" :key="player.id">
      <v-card
        class="mx-auto"
        max-width="344"
        outlined
      >
        <v-list-item three-line>
          <v-list-item-content>          
            <v-list-item-title class="text-h5 mb-1">
              {{player.id}}
            </v-list-item-title>          
          </v-list-item-content>
        </v-list-item>
        <v-card-actions>
          <v-btn
            outlined
            rounded
            text
          >
            Button
          </v-btn>
        </v-card-actions>
      </v-card>
      </div>
      

    
      </v-row>
    </v-container>
    <div>


    <v-container class="player-col">
      <v-col>
      <div v-for="player in players" :key="player.id">
      <v-card
        class="mx-auto"
        max-width="344"
        outlined
      >
        <v-list-item three-line>
          <v-list-item-content>          
            <v-list-item-title class="text-h5 mb-1">
              {{player.id}}
            </v-list-item-title>          
          </v-list-item-content>
        </v-list-item>
        <v-card-actions>
          <v-btn
            outlined
            rounded
            text
          >
            Button
          </v-btn>
        </v-card-actions>
      </v-card>
      </div>
      </v-col>      
    </v-container>
    <div>
      <img src="../assets/logo.png" alt="logo" width="800">
      </div>
          </div>
    

  </div>

</template>

<script>


export default {
  name: "Room",
  
  data: function () {
    return { 
      players : [
        {id : "player1"},
        {id : "player2"},
        {id : "player3"},
        {id : "player4"},        
      ]
  }
  }
}
</script>


<style>
.player-col {
  display:flex;
  margin: 0;
}
</style>