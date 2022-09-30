<template>
  <div id="header-container">
    <div class="header-icon-wrap">
      <div class="menu-wrap">
        <div class="hamburger-menu">
          <div class="bar-top"></div>
          <div class="bar-middle"></div>
          <div class="bar-bottom"></div>
        </div>
      </div>
      <div class="logo-wrap" @click="moveMain">
        <!-- <div>
          <v-icon size="1.2rem" color="black" style="position: fixed; top: 0"
            >mdi-bookmark</v-icon
          >
        </div>-->
        <img
          class="logo_word"
          src="/icon/Logo_word_icon_b.png"
          alt="Logo_word_icon_b.png"
          @click="moveMain"
        />
      </div>
      <div v-if="userId === 0 || userId === ''">
        <v-icon size="3rem" color="black" class="mr-2" @click="moveLogin"
          >mdi-login-variant</v-icon
        >
      </div>
      <div v-else>
        <v-icon size="3rem" color="black" class="mr-2" @click="logOut"
          >mdi-logout-variant</v-icon
        >
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
export default {
  name: 'HeaderComponent',
  data() {
    return {}
  },
  computed: {
    ...mapState('user', ['userId', 'nickname']),
  },
  methods: {
    ...mapActions('user', ['logout']),
    moveLogin() {
      this.$router.push('/user/login')
    },
    moveMain() {
      this.$router.push('/main')
    },
    moveMypage() {
      this.$router.push('/user/mypage')
    },
    logOut() {
      this.logout()
      this.moveMain()
    },
  },
}
</script>

<style scoped>
* {
  color: black;
}
#header-container {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  background-color: rgb(255, 255, 255);
  height: 70px;
  width: 100%;
  z-index: 999;
  box-shadow: 0 -4px 10px black;
}
/* hamburger menu css */
.hamburger-menu {
  margin: 1rem;
  display: flex;
  flex-flow: column wrap;
  justify-content: space-between;
  height: 2.5rem;
  width: 2.5rem;
  cursor: pointer;
}
.bar-top,
.bar-middle,
.bar-bottom {
  height: 5px;
  background: black;
  border-radius: 5px;
  margin: 3px 0;
  transform-origin: left;
  transition: all 0.5s;
}
.hamburger-menu:hover .bar-top {
  transform: rotate(45deg);
}
.hamburger-menu:hover .bar-middle {
  transform: translateX(1rem);
  opacity: 0;
}
.hamburger-menu:hover .bar-bottom {
  transform: rotate(-45deg);
}

/* 클릭 후 */

/* :deep(.hamburger-menu:checked ~) .bar-top {
  transform: rotate(45deg);
}
:deep(.hamburger-menu:checked ~) .bar-middle {
  transform: translateX(1rem);
  opacity: 0;
}
:deep(.hamburger-menu:checked ~) .bar-bottom {
  transform: rotate(-45deg);
} */

/*  */
.header-icon-wrap {
  width: 100%;
  height: 70px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo-wrap {
  display: flex;
  cursor: pointer;
}
.logo_word {
  height: 8vh;
}
</style>
 