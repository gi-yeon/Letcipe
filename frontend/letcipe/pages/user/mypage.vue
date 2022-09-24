<template>
  <div id="app">
    <v-app id="inspire">
      <div class="mypage-container">
        <div class="user-section">
          <v-container>
            <v-row style="padding: 0%">
              <v-col cols="2">
                <v-avatar size="45px">
                  <img
                    v-if="avatar"
                    alt="Avatar"
                    src="https://avatars0.githubusercontent.com/u/9064066?v=4&s=460"
                  />
                  <v-icon v-else :color="message.color" v-text="message.icon"></v-icon>
                </v-avatar>
              </v-col>

              <v-col cols="6" style="margin: auto">
                <v-row style="font-size: 18px">&nbsp;{{ nickname }}ㅡㅡㅡㅡ</v-row>
                <v-row style="font-size: 13px">&nbsp;{{ job }}</v-row>
              </v-col>
              <v-col cols="3" style="margin: auto">
                <v-btn
                  outlined
                  small
                  rounded
                  style="margin: 0px; color: black"
                  @click="moveModify"
                >내 정보</v-btn>
              </v-col>
            </v-row>
          </v-container>
          <v-container style="padding: 0%">
            <div class="list-btns">
              <v-row style="margin: auto">
                <v-col cols="6">
                  <v-icon class="menu-icon">mdi-book-open-page-variant-outline</v-icon>진행중인 리스트
                </v-col>
                <span style="margin: auto">|</span>
                <v-col style="margin: auto" @click="moveCheckList">
                  <v-icon class="menu-icon">mdi-cart</v-icon>장보기목록
                </v-col>
              </v-row>
            </div>
          </v-container>

          <v-container style="padding: 0rem; margin: auto">
            <v-row style="margin: auto; text-align: center">
              <v-col cols="3">
                <v-icon x-large class="menu-icon">mdi-book-outline</v-icon>
              </v-col>
              <v-col cols="3">
                <v-icon x-large class="menu-icon">mdi-book-edit-outline</v-icon>
              </v-col>
              <v-col cols="3">
                <v-icon x-large class="menu-icon">mdi-noodles</v-icon>
              </v-col>
              <v-col cols="3">
                <v-icon x-large class="menu-icon">mdi-history</v-icon>
              </v-col>
            </v-row>
            <v-row style="margin: auto; text-align: center; display: flex">
              <v-col cols="3" class="menu-title">
                즐겨찾는
                <br />레시피리스트
              </v-col>
              <v-col cols="3" class="menu-title">
                내가 만든
                <br />레시피리스트
              </v-col>
              <v-col cols="3" class="menu-title">
                내가 만든
                <br />레시피
              </v-col>
              <v-col cols="3" class="menu-title">히스토리</v-col>
            </v-row>
            <br />
            <v-row style="margin: auto; text-align: center">
              <v-col cols="3">
                <v-icon x-large class="menu-icon">mdi-bookmark-check-outline</v-icon>
              </v-col>
              <v-col cols="3">
                <v-icon x-large class="menu-icon">mdi-cards-heart</v-icon>
              </v-col>
              <v-col cols="3">
                <v-icon x-large class="menu-icon">mdi-comment-edit-outline</v-icon>
              </v-col>
              <v-col cols="3">
                <v-icon x-large class="menu-icon">mdi-account-supervisor</v-icon>
              </v-col>
            </v-row>
            <v-row style="margin: auto; text-align: center; display: flex">
              <v-col cols="3" class="menu-title">
                즐겨찾는
                <br />레시피
              </v-col>
              <v-col cols="3" class="menu-title">
                좋아요한
                <br />레시피
              </v-col>
              <v-col cols="3" class="menu-title">댓글관리</v-col>
              <v-col cols="3" class="menu-title">내 글 관리</v-col>
            </v-row>
          </v-container>
        </div>
      </div>
    </v-app>
  </div>
</template>

<script>
export default {
  name: 'MyPage',
  data: () => ({
    avatar: 'https://avatars0.githubusercontent.com/u/9064066?v=4&s=460',
    nickname: '레시피 마법사',
    job: 'Chef',
    title: 'Welcome to Vuetify!',
    excerpt: 'Thank you for joining our community...',
  }),
  methods: {
    moveModify() {
      this.$router.push('/user/modify')
    },
    moveCheckList() {
      this.$router.push('/check')
    },
  },
}
</script>

<style scoped>
.mypage-container {
  height: 100%;
  padding: 8%;
  /* background-image: url('/bg/bg_img.png'); */
  background-repeat: repeat;
  /* background-color: rgba(255, 255, 221, 0.771); */
  color: black;
}
.user-section {
  /* background-color: white !important; */
  /* background-image: url('/bg/bg_img.png'); */
  /* background-repeat: repeat; */
}
.list-btns {
  margin: 1.5rem 0 1.5rem 0;
  border: 1px solid black;
  border-radius: 10px;
  height: 3rem;
  text-align: center;
}
.menu-title {
  font-size: 0.8rem;
  padding: 0rem;
}
.menu-icon {
  color: black;
}
</style>
