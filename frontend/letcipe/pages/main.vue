<template>
  <div class="main-wrap">
    <header-component></header-component>
    <nuxt-child></nuxt-child>
    <footer-component></footer-component>
    <!-- <btn-component></btn-component> -->
  </div>
</template>

<script>
import HeaderComponent from '../components/HeaderComponent.vue'
import FooterComponent from '../components/FooterComponent.vue'
// import BtnComponent from '../components/BtnComponent.vue'
export default {
  name: 'MainPage',
  components: {
    HeaderComponent,
    FooterComponent,
    // BtnComponent,
  },
}
</script>

<style scoped></style>
 