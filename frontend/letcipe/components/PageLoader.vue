<template>
  <div class="page-loader">
    <div class="spinner">
      <div class="back">
        <img class="load-spinner" src="/icon/렛시피_레코드.png" alt="loading-spinner-plane" />
      </div>
    </div>
    <!-- <div id="spinner"></div> -->
    <!-- <img class="spinner-logo" src="/logo.png" alt /> -->
  </div>
</template>

<script>
export default {
  name: 'PageLoader',
  props: {
    toggle: Boolean,
  },
  data: () => {
    return {
      //   isloaded: false,
    }
  },
  //   mounted() {
  //     document.onreadystatechange = () => {
  //       if (document.readyState === 'complete') {
  //         this.isloaded = true
  //       }
  //     }
  //   },
}
</script>

<style scoped>
.page-loader {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  z-index: 999;
}

.spinner {
  position: relative;
}
.load-spinner {
  width: 100%;
  height: 100%;
}

.back {
  animation: spin 2s linear infinite;
}
@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>