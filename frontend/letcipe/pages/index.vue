<template>
  <v-container class="init-page" justify="center" align="center" @click="moveMain">
    <v-row>
      <v-col class="logo-wrap">
        <v-col class="logo-title">
          <h4>
            스마트한 데일리
            <br />장보기 도우미
            <br />EAT, PLAY, SHARE
          </h4>
        </v-col>

        <img class="load-spinner" src="/icon/렛시피_레코드.png" alt="loading-spinner-plane" />
        <h1 class="name">LET'CIPE</h1>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'IndexPage',
  data() {
    return {}
  },
  methods: {
    moveMain() {
      this.$router.push('/main')
    },
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

.init-page {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  /* background-image: url('/bg/bg_img.png'); */
  /* background-color: #ffbc35; */
}
.logo-wrap {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.col {
  max-width: 100%;
}
.row {
  justify-content: center;
}
.logo-horizontal {
  cursor: pointer;
  width: 60%;
}
.logo-title {
  /* color: #364067; */
  color: black;
  text-align: center;
}
.load-spinner {
  animation: spin 2s linear infinite;
  width: 30%;
}
.name {
  font-weight: bolder;
}

@media (min-width: 800px) {
  .container {
    max-width: 100%;
  }
  .logo-horizontal {
    width: 20%;
  }
  .load-spinner {
    width: 10%;
  }
  .img {
    width: 10%;
  }
}

.logo-horizontal {
  animation: slide-in-right 1s cubic-bezier(0.25, 0.46, 0.45, 0.94) forwards,
    logo-jello 0.9s linear 1.2s;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

/* .logo-horizontal {
  -webkit-animation: logo-jello 0.9s both;
  animation: logo-jello 0.9s both;
  animation-delay: 1.2s;
} */

@-webkit-keyframes slide-in-right {
  0% {
    -webkit-transform: translateX(1000px);
    transform: translateX(1000px);
    opacity: 0;
  }
  100% {
    -webkit-transform: translateX(0);
    transform: translateX(0);
    opacity: 1;
  }
}
@keyframes slide-in-right {
  0% {
    -webkit-transform: translateX(1000px);
    transform: translateX(1000px);
    opacity: 0;
  }
  100% {
    -webkit-transform: translateX(0);
    transform: translateX(0);
    opacity: 1;
  }
}

@keyframes logo-jello {
  0% {
    -webkit-transform: scale3d(1, 1, 1);
    transform: scale3d(1, 1, 1);
  }
  30% {
    -webkit-transform: scale3d(1.25, 0.75, 1);
    transform: scale3d(1.25, 0.75, 1);
  }
  40% {
    -webkit-transform: scale3d(0.75, 1.25, 1);
    transform: scale3d(0.75, 1.25, 1);
  }
  50% {
    -webkit-transform: scale3d(1.15, 0.85, 1);
    transform: scale3d(1.15, 0.85, 1);
  }
  65% {
    -webkit-transform: scale3d(0.95, 1.05, 1);
    transform: scale3d(0.95, 1.05, 1);
  }
  75% {
    -webkit-transform: scale3d(1.05, 0.95, 1);
    transform: scale3d(1.05, 0.95, 1);
  }
  100% {
    -webkit-transform: scale3d(1, 1, 1);
    transform: scale3d(1, 1, 1);
  }
}
</style>
