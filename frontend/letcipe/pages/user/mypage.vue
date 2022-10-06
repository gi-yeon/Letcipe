<template>
  <div id="app">
    <v-app id="inspire">
      <div class="mypage-container">
        <div class="user-section">
          <v-container class="user-head">
            <div class="user-title" style="padding: 0%">
              <div class="user-title-head">
                <div class="user-avatar">
                  <v-avatar class="user-profile" size="90px">
                    <img
                      v-if="profileImage !== '' && profileImage !== null"
                      alt="Avatar"
                      :src="profileImage"
                    />
                    <img v-else alt="Avatar" src="/icons/유저_mo.png" />
                  </v-avatar>
                </div>
                <div
                  v-if="nickname !== null && nickname !== ''"
                  class="user-info"
                >
                  <div class="user-name" style="font-size: 18px">
                    {{ nickname }}
                  </div>
                  <div class="user-name" style="font-size: 13px">
                    {{ userJob }}
                  </div>
                </div>
                <div v-else class="user-info">
                  <div class="user-name" style="font-size: 18px">
                    로그인을 해주세요.
                  </div>
                </div>
              </div>
              <div class="user-chip">
                <v-chip
                  class="user-chip-info ma-2 text--center"
                  color="letcipe lighten-2"
                  @click="moveModify"
                  >내 정보</v-chip
                >
                <!-- 
                <v-btn outlined small rounded style="margin: 0px; color: black" @click="logout">로그아웃</v-btn>-->
              </div>
            </div>
          </v-container>
          <v-container class="my-btn-container">
            <div class="user-head-btn d-flex justify-space-between">
              <v-btn class="my-btn" large @click="moveProgress">
                <v-hover style="text-align: center">
                  <template #default="{ hover }">
                    <img
                      class="footer-icon"
                      width="30px"
                      :src="
                        hover
                          ? `/icons/레시피북_h.gif`
                          : `/icons/레시피북_o.png`
                      "
                      alt
                      :style="
                        hover
                          ? `filter: brightness(0.9);`
                          : `filter: brightness(1);`
                      "
                    />
                  </template>
                </v-hover>
                <span style="font-size: medium">진행중인 리스트</span>
              </v-btn>
              <v-btn class="my-btn" large @click="moveCheckList">
                <v-hover style="text-align: center">
                  <template #default="{ hover }">
                    <img
                      class="footer-icon"
                      width="30px"
                      :src="hover ? `/icons/종이_h.gif` : `/icons/종이_o.png`"
                      alt
                      :style="
                        hover
                          ? `filter: brightness(0.9);`
                          : `filter: brightness(1);`
                      "
                    />
                  </template>
                </v-hover>
                <span style="font-size: medium">장보기목록</span>
              </v-btn>
            </div>
          </v-container>
          <v-card class="my-menu-card mx-auto" tile>
            <v-list flat>
              <v-subheader style="font-size: large; color: #ffa500"
                >마이메뉴</v-subheader
              >
              <v-divider></v-divider>
              <v-list-item-group color="letcipe">
                <v-hover>
                  <template #default="{ hover }">
                    <v-list-item @click="moveCart">
                      <v-list-item-icon>
                        <img
                          class="footer-icon"
                          width="30px"
                          :src="
                            hover ? `/icons/카트_h.gif` : `/icons/카트_o.png`
                          "
                          alt
                        />
                      </v-list-item-icon>
                      <v-list-item-content>
                        <v-list-item-title>장바구니</v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </template>
                </v-hover>
                <v-hover>
                  <template #default="{ hover }">
                    <v-list-item @click="moveSearch">
                      <v-list-item-icon>
                        <img
                          class="footer-icon"
                          width="30px"
                          :src="
                            hover ? `/icons/검색_h.gif` : `/icons/검색_o.png`
                          "
                          alt
                        />
                      </v-list-item-icon>
                      <v-list-item-content>
                        <v-list-item-title>검색</v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </template>
                </v-hover>
                <v-hover>
                  <template #default="{ hover }">
                    <v-list-item @click="moveModify">
                      <v-list-item-icon>
                        <img
                          class="footer-icon"
                          width="30px"
                          :src="
                            hover ? `/icons/유저_mh.gif` : `/icons/유저_mo.png`
                          "
                          alt
                        />
                      </v-list-item-icon>
                      <v-list-item-content>
                        <v-list-item-title>내정보</v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </template>
                </v-hover>
                <v-hover>
                  <template #default="{ hover }">
                    <v-list-item @click="moveRecipeListBookmark">
                      <v-list-item-icon>
                        <img
                          class="footer-icon"
                          width="30px"
                          :src="
                            hover
                              ? `/icons/북마크_h.gif`
                              : `/icons/북마크_o.png`
                          "
                          alt
                        />
                      </v-list-item-icon>
                      <v-list-item-content>
                        <v-list-item-title
                          >즐겨찾는 레시피 리스트</v-list-item-title
                        >
                      </v-list-item-content>
                    </v-list-item>
                  </template>
                </v-hover>
                <v-hover>
                  <template #default="{ hover }">
                    <v-list-item @click="moveMyRecipeList">
                      <v-list-item-icon>
                        <img
                          class="footer-icon"
                          width="30px"
                          src="/icons/북마크_o.png"
                          :style="
                            hover
                              ? `filter: brightness(1.3)`
                              : `filter: brightness(1)`
                          "
                          alt
                        />
                      </v-list-item-icon>
                      <v-list-item-content>
                        <v-list-item-title
                          >내가만든 레시피 리스트</v-list-item-title
                        >
                      </v-list-item-content>
                    </v-list-item>
                  </template>
                </v-hover>
                <v-hover>
                  <template #default="{ hover }">
                    <v-list-item @click="moveMyRecipe">
                      <v-list-item-icon>
                        <img
                          class="footer-icon"
                          width="30px"
                          :src="
                            hover
                              ? `/icons/스크롤_h.gif`
                              : `/icons/스크롤_o.png`
                          "
                          alt
                        />
                      </v-list-item-icon>
                      <v-list-item-content>
                        <v-list-item-title>내가만든 레시피</v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </template>
                </v-hover>
                <v-hover>
                  <template #default="{ hover }">
                    <v-list-item @click="moveHistory">
                      <v-list-item-icon>
                        <img
                          class="footer-icon"
                          width="30px"
                          src="/icons/히스토리_1.png"
                          :style="
                            hover
                              ? `filter: brightness(1.3)`
                              : `filter: brightness(1)`
                          "
                          alt
                        />
                      </v-list-item-icon>
                      <v-list-item-content>
                        <v-list-item-title>히스토리</v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </template>
                </v-hover>
                <v-hover>
                  <template #default="{ hover }">
                    <v-list-item @click="moveRecipeBookmark">
                      <v-list-item-icon>
                        <img
                          class="footer-icon"
                          width="30px"
                          :src="
                            hover
                              ? `/icons/즐겨찾기_h.gif`
                              : `/icons/즐겨찾기_o.png`
                          "
                          alt
                        />
                      </v-list-item-icon>
                      <v-list-item-content>
                        <v-list-item-title>즐겨찾는 레시피</v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </template>
                </v-hover>
                <v-hover>
                  <template #default="{ hover }">
                    <v-list-item @click="moveLike">
                      <v-list-item-icon>
                        <img
                          class="footer-icon"
                          width="30px"
                          :src="
                            hover
                              ? `/icons/좋아요_h.gif`
                              : `/icons/좋아요_o.png`
                          "
                          alt
                        />
                      </v-list-item-icon>
                      <v-list-item-content>
                        <v-list-item-title>좋아요한 레시피</v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </template>
                </v-hover>
                <v-hover>
                  <template #default="{ hover }">
                    <v-list-item @click="moveComment">
                      <v-list-item-icon>
                        <img
                          class="footer-icon"
                          width="30px"
                          :src="
                            hover
                              ? `/icons/댓글관리_h.gif`
                              : `/icons/댓글관리_o.png`
                          "
                          alt
                        />
                      </v-list-item-icon>
                      <v-list-item-content>
                        <v-list-item-title>댓글관리</v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </template>
                </v-hover>
                <v-hover>
                  <template #default="{ hover }">
                    <v-list-item @click="moveComment">
                      <v-list-item-icon>
                        <img
                          class="footer-icon"
                          width="30px"
                          src="/icons/보드.png"
                          :style="
                            hover
                              ? `filter: brightness(1.3)`
                              : `filter: brightness(1)`
                          "
                          alt
                        />
                      </v-list-item-icon>
                      <v-list-item-content>
                        <v-list-item-title>내글관리</v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </template>
                </v-hover>
              </v-list-item-group>
            </v-list>
          </v-card>
        </div>
      </div>
    </v-app>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'MyPage',
  data: () => ({
    avatar: 'https://avatars0.githubusercontent.com/u/9064066?v=4&s=460',
    title: 'Welcome to Vuetify!',
    excerpt: 'Thank you for joining our community...',
  }),
  computed: {
    ...mapState('user', ['userId', 'nickname', 'userJob', 'profileImage']),
  },
  methods: {
    moveModify() {
      this.$router.push('/user/modify')
    },
    moveCheckList() {
      this.$router.push('/check')
    },
    moveComment() {
      this.$router.push('/user/comment')
    },
    moveProgress() {
      this.$router.push('/user/progress')
    },
    moveRecipeBookmark() {
      this.$router.push('/user/bookmark')
    },
    moveMyRecipe() {
      this.$router.push('/user/recipe')
    },
    moveRecipeListBookmark() {
      this.$router.push('/user/listbookmark')
    },
    moveMyRecipeList() {
      this.$router.push('/user/recipelist')
    },
    moveCart() {
      this.$router.push('/cart')
    },
    moveHistory() {
      this.$router.push('/user/history')
    },
    moveSearch() {
      this.$router.push('/search')
    },
    moveLike() {
      this.$router.push('/user/like')
    },
    logout() {},
  },
}
</script>

<style scoped>
.mypage-container {
  height: 100%;
  width: 100%;
  padding: 8%;
  /* background-image: url('/bg/bg_img.png'); */
  background-repeat: repeat;
  /* background-color: rgba(255, 255, 221, 0.771); */
  color: black;
  display: flex;
  justify-content: center;
  align-items: center;
}

.user-section {
  /* background-color: white !important; */
  /* background-image: url('/bg/bg_img.png'); */
  /* background-repeat: repeat; */
  width: 80%;
}
.user-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.user-title-head {
  display: flex;
  align-items: center;
}
.user-info {
  margin-left: 10%;
}

.user-name {
  width: 200px;
}

/* .list-btns {
  margin: 1.5rem 0 1.5rem 0;
  border: 1px solid black;
  border-radius: 10px;
  height: 3rem;
  text-align: center;
} */
.user-profile {
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
}
.my-btn-container {
  padding: 2%;
}
.my-btn {
  width: 48%;
}
.menu-title {
  font-size: 0.8rem;
  padding: 0rem;
  cursor: pointer;
}
.menu-icon {
  color: black;
  cursor: pointer;
}
.my-menu-card {
  padding-top: 2%;
  padding-bottom: 2%;
  width: 96%;
}
@media (max-width: 900px) {
  .user-name {
    width: 150px;
  }
}

/* 모바일 screen */
@media (max-width: 500px) {
  .mypage-container {
    height: 100%;
    /* background-image: url('/bg/bg_img.png'); */
    background-repeat: repeat;
    /* background-color: rgba(255, 255, 221, 0.771); */
    color: black;
  }

  .user-section {
    width: 100%;
  }
  .user-profile {
    /* min-width: 45px !important;
    width: 45px !important;
    height: 45px !important; */
    box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  }
  .menu-title {
    font-size: 0.8rem;
    padding: 0rem;
    cursor: pointer;
  }
  .menu-icon {
    color: black;
    cursor: pointer;
  }
  .user-title {
    display: flex;
    flex-direction: column;
  }
  .user-title-head {
    display: flex;
    flex-direction: column;
  }
  .user-info {
    margin-left: 0;
    padding-top: 4%;
  }
  .user-name {
    width: 100%;
  }
  .user-chip {
    justify-content: center;
  }
  .user-head-btn {
    flex-direction: column;
  }
  .user-chip-info {
    justify-content: center;
    box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  }
  .my-btn-container {
    padding: none;
  }
  .my-btn {
    margin-top: 3px;
    width: 100%;
  }
  .my-menu-card {
    margin-top: 7%;
    max-width: 300;
  }
}

.fadeInUp {
  animation: fadeInUp 1s ease backwards;
}
@keyframes fadeInUp {
  0% {
    transform: translate(0px, 100px);
    opacity: 0;
  }
  100% {
    transform: translate(0px, 0);
    opacity: 1;
  }
}
</style>
