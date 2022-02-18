<template>
  <v-carousel hide-delimiters v-model="model" :show-arrows="true" class="TelestationInfo border" height="567px">
    <v-carousel-item v-for="(num, i) in desc" :key="num">
      <v-row class="fill-height" align="center" justify="center">
        <div>
          <img :src="require(`@/assets/description_image/telestation/` + `텔레스테이션설명`+(i+1)+`.jpg`)" class="telestationinfoimg"/>
        </div>
      </v-row>
    </v-carousel-item>
  </v-carousel>
</template>

<script>
export default {
  name: "TelestationDescription",
  data () {
      return {
        model: 0,
        desc: [
          "p1","p2","p3", "p4", "p5"
        ],
      }
    },

  props: {
    gameSelected: String,
  }
}
</script>

<style scoped>
.TelestationInfo {
  border-bottom-left-radius: 15px 255px;
    border-bottom-right-radius: 225px 15px;
    border-top-left-radius: 23px 15px;
    border-top-right-radius: 15px 225px;
}
.telestationinfoimg {
  height: 567px;
  margin-left: 10px;
  margin-top: 5px;
  border: 0;
  border-radius: 0;
  border-top-left-radius: 22px 15px;      
}
</style>