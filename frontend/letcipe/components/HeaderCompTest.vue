<template>
  <v-app>
    <v-card class="overflow-hidden">
      <v-app-bar
        absolute
        color="white"
        elevate-on-scroll
        scroll-target="#scrolling-techniques-7"
      >
        <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>

        <v-toolbar-title
          ><img
            class="logo_word"
            src="/icon/Logo_word_icon_b.png"
            alt="Logo_word_icon_b.png"
            @click="moveMain"
        /></v-toolbar-title>

        <v-spacer></v-spacer>

        <v-btn v-if="user === null || user === ''" icon>
          <v-icon size="3rem" color="black" class="mr-2" @click="moveLogin"
            >mdi-login-variant</v-icon
          >
        </v-btn>

        <v-btn icon>
          <v-icon size="3rem" color="black" class="mr-2"
            >mdi-account-circle</v-icon
          >
        </v-btn>

        <v-btn icon>
          <v-icon>mdi-dots-vertical</v-icon>
        </v-btn>
      </v-app-bar>
      <v-sheet
        id="scrolling-techniques-7"
        class="overflow-y-auto"
        max-height="600"
      >
        <v-container style="height: 1500px"> </v-container>
      </v-sheet>
    </v-card>
    <v-navigation-drawer
      v-model="drawer"
      class="nav-drawer"
      absolute
      bottom
      temporary
      style="width: 100%"
    >
      <v-col class="menu-wrap">
        <div class="hamburger-menu" @click.stop="drawer = !drawer">
          <div class="bar-top-after"></div>
          <div class="bar-bottom-after"></div>
        </div>
      </v-col>
      <v-list nav dense>
        <v-list-item-group
          v-model="group"
          active-class="deep-purple--text text--accent-4"
        >
          <v-list-item>
            <v-list-item-title>Foo</v-list-item-title>
          </v-list-item>

          <v-list-item>
            <v-list-item-title>Bar</v-list-item-title>
          </v-list-item>

          <v-list-item>
            <v-list-item-title>Fizz</v-list-item-title>
          </v-list-item>

          <v-list-item>
            <v-list-item-title>Buzz</v-list-item-title>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
  </v-app>
  <!-- <v-app-bar dense fixed class="app-bar">
        <v-row class="header-icon-wrap">
          <v-col class="menu-wrap">
            <div class="hamburger-menu" @click.stop="drawer = !drawer">
              <div class="bar-top"></div>
              <div class="bar-middle"></div>
              <div class="bar-bottom"></div>
            </div>
          </v-col>
          <v-col class="logo-wrap">

            <img
              class="logo_word"
              src="/icon/Logo_word_icon_b.png"
              alt="Logo_word_icon_b.png"
              @click="moveMain"
            />
          </v-col>
          <v-col v-if="user === null || user === ''">
            <v-icon size="3rem" color="black" class="mr-2" @click="moveLogin"
              >mdi-login-variant</v-icon
            >
          </v-col>
          <v-col v-else>
            <v-icon size="3rem" color="black" class="mr-2"
              >mdi-account-circle</v-icon
            >
          </v-col>
        </v-row>
      </v-app-bar>
    </v-app>
    <v-navigation-drawer
      v-model="drawer"
      class="nav-drawer"
      absolute
      left
      temporary
      style="width: 100%"
    >
      <v-col class="menu-wrap">
        <div class="hamburger-menu" @click.stop="drawer = !drawer">
          <div class="bar-top-after"></div>
          <div class="bar-bottom-after"></div>
        </div>
      </v-col>
      <v-list nav dense>
        <v-list-item-group
          v-model="group"
          active-class="deep-purple--text text--accent-4"
        >
          <v-list-item>
            <v-list-item-title>Foo</v-list-item-title>
          </v-list-item>

          <v-list-item>
            <v-list-item-title>Bar</v-list-item-title>
          </v-list-item>

          <v-list-item>
            <v-list-item-title>Fizz</v-list-item-title>
          </v-list-item>

          <v-list-item>
            <v-list-item-title>Buzz</v-list-item-title>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer> -->
</template>

<script>
export default {
  name: 'HeaderTest',
  data() {
    return {
      user: '',
      drawer: false,
      group: null,
    }
  },
  watch: {
    group() {
      this.drawer = false
    },
  },
  methods: {
    moveLogin() {
      this.$router.push('/user/login')
    },
    moveMain() {
      this.$router.push('/main')
    },
  },
}
</script>

<style scoped>
* {
  color: black;
}
:deep(.v-application--wrap) {
  min-height: fit-content;
}

.app-bar {
  /* height: 70px !important; */
}

#header-container {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  background-color: rgb(255, 255, 255);
  height: 70px;
  width: 100%;
  /* z-index: 999; */
  box-shadow: 0 -4px 10px black;
}
/* hamburger menu css */
.hamburger-menu {
  margin: 0.5rem;
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

.bar-bottom-after,
.bar-top-after,
.bar-middle-after {
  height: 5px;
  background: black;
  border-radius: 5px;
  margin: 3px 0;
  transform-origin: left;
  transition: all 0.5s;
}
.bar-top-after {
  transform: rotate(45deg);
}
.bar-bottom-after {
  transform: rotate(-45deg);
}
.row {
  margin: -9px !important;
}
.col {
  flex-grow: 0 !important;
}
.header-icon-wrap {
  width: 100%;
  height: 70px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo-wrap {
  display: flex;
}
.logo_word {
  height: 8vh;
}
</style>
