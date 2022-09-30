<template>
  <div id="app">
    <Nuxt />
  </div>
</template>

<script>
export default {
  name: 'DefaultLayout',
}
</script>
 
<style>
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
#app {
  /* background-color: white; */
  color: black;
  /* background-image: url('/bg/bg_img.png'); */
  background-color: white;
  background-repeat: repeat;
}
</style>
