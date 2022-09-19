<template>
  <div id="footer-container">
    <div class="footer-icon-wrap">
      <div>
        <v-icon x-large class="footer-icon">mdi-home</v-icon>
      </div>
      <div>
        <v-icon x-large class="footer-icon">mdi-account</v-icon>
      </div>
      <div>
        <v-icon x-large class="footer-icon">mdi-cart-plus</v-icon>
      </div>
      <div>
        <v-icon x-large class="footer-icon">mdi-magnify</v-icon>
      </div>
      <div>
        <v-icon x-large class="footer-icon">mdi-clipboard-text</v-icon>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FooterComponent',
}
</script>

<style scoped>
* {
  color: black;
}
#footer-container {
  background-color: rgb(255, 255, 255);
  height: 70px;
  width: 100%;
  position: fixed;
  bottom: 0;
  z-index: 999;
  box-shadow: 0 4px 7px black;
}
.footer-icon-wrap {
  display: flex;
  justify-content: space-around;
  align-items: center;
  height: 70px;
}
.footer-icon {
  color: black;
}
</style>
