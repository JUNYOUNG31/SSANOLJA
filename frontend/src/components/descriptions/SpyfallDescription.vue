<template>
  <v-carousel v-model="model">
    <v-carousel-item
      v-for="(num, i) in desc"
      :key="num"
    >
      <v-sheet
        :color="colors[i]"
        height="100%"
        tile
      >
        <v-row
          class="fill-height"
          align="center"
          justify="center"
        >
          <div class="text-h2">
            <img :src="require(`@/assets/description_image/spyfall/` + `spy_`+(i+1)+`.png`)" width="100%"/>
          </div>
        </v-row>
      </v-sheet>
    </v-carousel-item>
  </v-carousel>
</template>

<script>
export default {
  name: "SpyfallDescription",
  data () {
      return {
        colors: [
          'white',
          'white',
          'white',
          'white',
          'white',
          'white',
        ],
        model: 0,
        desc: [
          "p1","p2","p3"
        ],
      }
    },

  props: {
    gameSelected: String,
  }
}
</script>

<style>
.text-h2 {
  /* text-align: center; */
  padding:2cm
}
</style>