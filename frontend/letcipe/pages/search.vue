<template>
  <div class="search-wrap">
    <nuxt-child></nuxt-child>
    <footer-component></footer-component>
  </div>
</template>

<script>
import FooterComponent from '../components/FooterComponent.vue'
export default {
  name: 'SearchPage',
  components: {
    FooterComponent,
  },
}
</script>

<style scope>
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
.search-wrap {
  /* margin-top: 70px; */
  margin-bottom: 70px;
}
</style>
