<template>
  <v-carousel v-model="model">
    <v-carousel-item
      v-for="(num, i) in desc"
      :key="num"
    >
        <v-row
          class="fill-height"
          align="center"
          justify="center"
        >
          <div>
            <img :src="require(`@/assets/description_image/spyfall/` + `스파이폴설명`+(i+1)+`.png`)" width="100%"/>
          </div>
        </v-row>

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

</style>