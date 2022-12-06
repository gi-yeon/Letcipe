<template>
  <div id="footer-container">
    <div class="footer-icon-wrap d-flex justify-space-around">
      <v-hover>
        <template #default="{ hover }">
          <div class="d-flex-column justify-center" style="text-align: center;">
            <v-icon
              size="30px"
              class="footer-icon"
              :style="hover? `color: #FFA500` : `color: black`"
              @click="moveMain"
            >mdi-home</v-icon>

            <div class="footer-text">홈</div>
          </div>
        </template>
      </v-hover>
      <v-hover>
        <template #default="{ hover }">
          <div class="d-flex-column justify-center" style="text-align: center;">
            <v-icon
              size="30px"
              class="footer-icon"
              :style="hover? `color: #FFA500` : `color: black`"
              @click="moveWrite"
            >mdi-clipboard-text</v-icon>

            <div class="footer-text">레시피</div>
          </div>
        </template>
      </v-hover>
      <div class="icons-main" @click="moveCart">
        <v-hover style="text-align: center;">
          <template #default="{ hover }">
            <v-btn fab x-large elevation="4">
              <div class="d-flex-column justify-center">
                <div style="text-align: center">
                  <img
                    class="footer-icon"
                    width="30px"
                    :src="hover ? `/icons/카트_h.gif`: `/icons/카트_o.png`"
                    alt
                    :style="hover ? `filter: brightness(0.9);` : `filter: brightness(1);`"
                  />
                </div>
                <div class="footer-text">장바구니</div>
              </div>
            </v-btn>
          </template>
        </v-hover>
      </div>
      <v-hover>
        <template #default="{ hover }">
          <div class="d-flex-column justify-center" style="text-align: center;">
            <v-icon
              size="30px"
              class="footer-icon"
              :style="hover? `color: #FFA500` : `color: black`"
              @click="moveSearch"
            >mdi-magnify</v-icon>

            <div class="footer-text">검색</div>
          </div>
        </template>
      </v-hover>
      <v-hover>
        <template #default="{ hover }">
          <div class="d-flex-column justify-center" style="text-align: center;">
            <v-icon
              size="30px"
              class="footer-icon"
              :style="hover? `color: #FFA500` : `color: black`"
              @click="moveMypage"
            >mdi-account</v-icon>
            <div class="footer-text">내렛시피</div>
          </div>
        </template>
      </v-hover>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FooterComponent',
  data() {
    return {}
  },
  methods: {
    moveMain() {
      this.$router.push('/main')
    },
    moveMypage() {
      this.$router.push('/user/mypage')
    },
    moveCart() {
      this.$router.push('/cart')
    },
    moveSearch() {
      this.$router.push('/search')
    },
    moveHadoop() {
      this.$router.push('/recipe/test')
    },
    moveWrite() {
      this.$router.push('/recipe/create')
    },
  },
}
</script>

<style scoped>
* {
  color: black;
}
#footer-container {
  background-color: rgb(255, 255, 255);
  height: 60px;
  width: 100%;
  position: fixed;
  bottom: 0;
  z-index: 800;
  box-shadow: 0 4px 7px black;
}
.footer-icon-wrap {
  display: flex;
  justify-content: space-around;
  align-items: center;
  height: 60px;
}
.footer-icon {
  color: black;
}
.footer-text {
  font-size: small;
  cursor: pointer;
}
</style>
