<template>
  <div class="user-wrap">
    <header-component></header-component>
    <nuxt-child></nuxt-child>
    <footer-component></footer-component>
  </div>
</template>

<script>
import HeaderComponent from '../components/HeaderComponent.vue'
import FooterComponent from '../components/FooterComponent.vue'
export default {
  name: 'UserPage',
  components: {
    HeaderComponent,
    FooterComponent,
  },
}
</script>

<style scoped>
@font-face {
  font-family: 'LeeSeoyun';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2202-2@1.0/LeeSeoyun.woff')
    format('woff');
  font-weight: bold;
  font-style: normal;
}
* {
  font-family: 'LeeSeoyun';
}

.user-wrap {
  margin-top: 70px;
  margin-bottom: 70px;
}
</style>
 