<template>
  <v-app > 
    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: 'App',

  data: () => ({
  }),
};
</script>

<style>
.v-application {
  border: hidden;
}
.v-application--wrap {
  border: hidden;
  background-image: url(assets/paint/checkback.png);
  background-size: cover;
  background-position: center;
}
.v-main__wrap {
  border: hidden;
}
.main > div {
  border: hidden;
}
</style>