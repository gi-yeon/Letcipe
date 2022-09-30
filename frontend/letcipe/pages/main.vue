<template>
  <div class="main-wrap">
    <header-component></header-component>
    <!-- <HeaderCompTest></HeaderCompTest> -->
    <nuxt-child></nuxt-child>
    <footer-component></footer-component>
    <!-- <btn-component></btn-component> -->
  </div>
</template>

<script>
// import HeaderCompTest from '../components/HeaderCompTest.vue'
import HeaderComponent from '../components/HeaderComponent.vue'
import FooterComponent from '../components/FooterComponent.vue'
// import BtnComponent from '../components/BtnComponent.vue'
export default {
  name: 'MainPage',
  components: {
    HeaderComponent,
    // HeaderCompTest,
    FooterComponent,
    // BtnComponent,
  },
}
</script>

<style scoped></style>
 