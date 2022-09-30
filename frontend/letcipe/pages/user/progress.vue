<template>
  <!-- 한줄에 하고 싶은데 방법 모름! -->
  <!-- 여기서의 레시피 리스트는 히스토리에서 가져오는건지,,!  -->
  <div id="app">
    <v-app id="inspire">
      <div class="progressrecipelist-page">
        <v-container class="progressrecipelist-container d-flex-row">
          <div class="progressrecipelist-head-wrap">
            <div class="d-flex justify-space-between pb-3">
              <v-icon>mdi-window-close</v-icon>
              <div style="font-size: x-large">진행중인 레시피리스트</div>
              <v-icon>mdi-blank</v-icon>
            </div>
          </div>
          <v-divider></v-divider>
          <!-- <div v-for="(recipeList, i) in letcipeList" :key="i"> -->
          <v-list-item three-line style="background-color: white">
            <v-list-item-avatar tile size="100">
              <v-img
                src="https://2bob.co.kr/data/recipe/20210810142007-EYPBD.jpg"
              ></v-img>
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title style="display: inline; float: left"
                >{{ historyInfo[0]['regTime'] }}
                <span style="font-size: 1rem; color: green; float: right"
                  >진행중</span
                >
              </v-list-item-title>

              <br />

              <!-- <v-list-item-subtitle
                >생성일 : {{ letcipeList.reg_time }}</v-list-item-subtitle
              > -->
              <v-list-item-subtitle style="text-align: right">
                총 17개의 재료를 포함&nbsp;
                <v-icon v-if="!isShow" @click="showRecipes"
                  >mdi-chevron-down</v-icon
                >
                <v-icon v-else @click="hideRecipes">mdi-chevron-up</v-icon>
              </v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
          <v-divider></v-divider>
          <!-- </div> -->
          <div v-if="isShow">
            <v-card-title
              class="justify-center"
              style="background-color: white; color: #7cb342"
              >포함된 레시피 목록</v-card-title
            >
            <v-divider></v-divider>
            <div v-for="(recipeInfo, i) in letcipeList.recipeListItem" :key="i">
              <v-list-item three-line style="background-color: white">
                <v-list-item-avatar tile size="100">
                  <v-img
                    src="https://2bob.co.kr/data/recipe/20210810142007-EYPBD.jpg"
                  ></v-img>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title>
                    {{ recipeInfo.recipe.title }}
                  </v-list-item-title>

                  <v-list-item-subtitle>
                    {{ recipeInfo.recipe.content }}
                  </v-list-item-subtitle>
                  <div class="d-flex justify-space-between">
                    <v-list-item-subtitle>
                      <v-icon small color="pink lighten-1"
                        >mdi-cards-heart</v-icon
                      >
                      {{ recipeInfo.recipe.recipeLike }}
                    </v-list-item-subtitle>
                    <v-list-item-subtitle style="text-align: right"
                      >선택한 재료 4개를 포함하고 있어요.</v-list-item-subtitle
                    >
                  </div>
                </v-list-item-content>
              </v-list-item>
              <v-divider></v-divider>
            </div>
            <v-divider></v-divider>
          </div>
          <div style="text-align: center; padding-top: 5%">
            <v-btn large color="lime darken-1" style="width: 50%">시작</v-btn>
          </div>
        </v-container>
      </div>
    </v-app>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
export default {
  name: 'ProgressPage',
  data() {
    return {
      status: '진행 예정',
      historyInfo: {},
      letcipeList: {
        id: 3,
        nickname: 'jgy',
        name: '정기연의 면요리',
        description: '가성비 버전',
        reg_time: '',
        is_shared: 'private',
        recipeListItem: [
          {
            recipe: {
              id: 1,
              nickname: '싸피10기',
              title: '고르곤졸라피자',
              content: `만만치 않은 가격에도 도우 반죽 때문에 집에서 만들기 영 꺼려졌던 피자
						토르티야로 간편하게 해결하세요!
						꼬릿하지만 묘한 매력이 있는 고르곤졸라치즈를 얹으면
						10분 만에 풍미 가득한 피자가 완성되죠. 꿀에 찍어 먹으면 더 맛있어요.`,
              cookingTime: 5,
              serving: 4,
              repImg: 'https://2bob.co.kr/data/recipe/20210810142007-EYPBD.jpg',
              recipeLike: 21222,
              recipeBookmark: 16,
            },
            amount: 3,
          },
          {
            recipe: {
              id: 4,
              nickname: '싸피7기',
              title: '시카고피자',
              content: '만만치 않은 가격!',
              cookingTime: 10,
              serving: 2,
              repImg: 'https://2bob.co.kr/data/recipe/20210810142007-EYPBD.jpg',
              recipeLike: 21,
              recipeBookmark: 16,
            },
            amount: 1,
          },
        ],
      },
      isShow: false,
    }
  },
  // async fetch() {
  //   await this.getHistoryList()
  //   console.log(this.historyList)
  // },
  computed: {
    ...mapState('history', ['historyList']),
  },

  created() {
    // this.getHistoryList()
    const promise = new Promise((resolve, reject) => {
      resolve()
    })
    promise.then(async () => {
      await this.getHistoryList()
      console.log(this.historyList)
      this.historyInfo = this.historyList

      console.log(this.historyList[0].regTime)
      this.letcipeList.reg_time = this.historyList[0].regTime.split('T')[0]
      for (let i = 0; i < this.historyList.length; i++) {
        if (this.historyList[i].process === 'READY') {
          this.status = '진행예정'
        } else if (this.historList[i].process === 'EATING') {
          this.status = '진행중'
        } else {
          this.status = '종료'
        }
      }
      this.historyInfo = this.historyList
      console.log(this.historyInfo)
    })
  },
  methods: {
    ...mapActions('history', ['getHistoryList']),
    showRecipes() {
      this.isShow = true
    },
    hideRecipes() {
      this.isShow = false
    },
  },
}
</script>

<style scoped>
.progressrecipelist-page {
  /* padding-top: 70px; */
  padding-bottom: 70px;
  padding: 4%;
}
.progressrecipelist-page-head {
  padding: 4%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  /* border: 1px solid gray; */
}
.progressrecipelist-container {
  position: sticky;
  height: 100%;
  color: black;

  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
}
</style>
