<template>
  <div id="app">
    <v-app id="inspire">
      <v-container class="main-container">
        <div class="my-middle-wrap">
          <div class="my-middle">
            <div class="my-container">
              <div>{{ nickname }}님의 현재 진행중인 레시피리스트</div>
              <v-carousel
                hide-delimiters
                class="my-list-carousel"
                height="100%"
                style="border-radius: 10px"
              >
                <v-carousel-item
                  v-for="(item, i) in userPlayList"
                  :key="i"
                  @click="moveProgress"
                >
                  <v-container>
                    <v-row style="margin: -25px">
                      <v-col cols="12">
                        <v-hover>
                          <template #default="{ hover }">
                            <v-card
                              :elevation="hover ? 24 : 6"
                              :class="hover ? 'letcipe lighten-2' : 'white'"
                              class="my-card mx-auto"
                            >
                              <div
                                class="d-flex flex-no-wrap justify-center pa-3"
                              >
                                <div>
                                  <div class="my-lecipe">
                                    <v-avatar class="ma-3" size="100">
                                      <v-img :src="item.url">
                                        <v-icon x-large>mdi-play</v-icon>
                                      </v-img>
                                    </v-avatar>
                                  </div>
                                  <v-card-title
                                    class="my-title text-h5"
                                    v-text="item.title"
                                  ></v-card-title>
                                  <v-card-subtitle
                                    class="my-sub-title"
                                    v-text="item.sub_title"
                                  ></v-card-subtitle>
                                </div>
                              </div>
                            </v-card>
                          </template>
                        </v-hover>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-carousel-item>
              </v-carousel>
            </div>
          </div>
          <div class="my-middle">
            <div class="check-wrap">
              <div>{{ nickname }}님의 장보기목록</div>
              <v-container elevation="3" class="check-container">
                <div class="check-head-wrap">
                  <v-tabs v-model="tabs" fixed-tabs>
                    <v-tabs-slider color="black"></v-tabs-slider>
                    <v-tab href="#mobile-tabs-5-1" class="letcipe--text">
                      <div>
                        <v-icon color="letcipe">mdi-cart-outline</v-icon
                        >구매할식재료
                      </div>
                    </v-tab>

                    <v-tab href="#mobile-tabs-5-2" class="letcipe--text">
                      <div>
                        <v-icon color="letcipe">mdi-cart-plus</v-icon>담은식재료
                      </div>
                    </v-tab>
                  </v-tabs>
                </div>
                <v-tabs-items v-model="tabs" class="check-tabs-wrap">
                  <v-tab-item
                    v-for="i in 2"
                    :key="i"
                    :value="'mobile-tabs-5-' + i"
                  >
                    <v-card flat>
                      <v-card-text
                        v-if="i === 1"
                        class="check-item-wrap fadeInUp"
                      >
                        <div class="shopping-wrap">
                          <div
                            v-if="checklist.length > 0"
                            class="before-shopping"
                          >
                            <div
                              v-for="(c, index) in checklist"
                              :key="index"
                              class="pl-3 pr-3"
                            >
                              <div
                                class="d-flex justify-space-between align-center mt-1"
                              >
                                <div class="ingre-name">
                                  <v-checkbox
                                    v-model="checklist[index]"
                                    class="mt-0 pt-0"
                                    :label="c.name"
                                    color="letcipe"
                                    :value="c.name"
                                    hide-details
                                    @click="bought(c, index)"
                                  ></v-checkbox>
                                </div>
                                <div class="ingre-amount">
                                  {{ c.amount }}{{ c.measure }}
                                </div>
                              </div>

                              <v-divider></v-divider>
                            </div>
                          </div>
                          <div v-else class="shopping-wrap">
                            <div
                              class="before-shopping-none"
                              style="
                                align-self: center !important;
                                text-align: center !important;
                              "
                            >
                              <div>진행중인 장보기목록이 없습니다.</div>
                            </div>
                          </div>
                        </div>
                      </v-card-text>
                      <v-card-text
                        v-if="i === 2"
                        class="check-item-wrapfadeInUp"
                      >
                        <div
                          v-if="checkedList.length > 0"
                          class="shopping-wrap"
                        >
                          <div class="after-shopping">
                            <div
                              v-for="(c, index) in checkedList"
                              :key="index"
                              class="pl-3 pr-3"
                            >
                              <div
                                class="d-flex justify-space-between align-center mt-1"
                              >
                                <div class="ingre-name">
                                  <v-checkbox
                                    v-model="checkedList[index]"
                                    class="mt-0 pt-0"
                                    :label="c.name"
                                    color="letcipe"
                                    true-value
                                    hide-details
                                    @click="needtobuy(c, index)"
                                  ></v-checkbox>
                                </div>
                                <div class="ingre-amount">
                                  {{ c.amount }}{{ c.measure }}
                                </div>
                              </div>
                              <v-divider></v-divider>
                            </div>
                          </div>
                        </div>
                        <div v-else class="shopping-wrap">
                          <div
                            class="after-shopping-none"
                            style="
                              align-self: center !important;
                              text-align: center !important;
                            "
                          >
                            <div>담은 식재료가 없습니다.</div>
                          </div>
                        </div>
                      </v-card-text>
                    </v-card>
                  </v-tab-item>
                </v-tabs-items>
              </v-container>
            </div>
            <div class="btn-group pt-4">
              <v-hover style="text-align: center">
                <template #default="{ hover }">
                  <v-btn class="my-btn" large width="49%" @click="moveWrite">
                    <img
                      class="footer-icon"
                      width="30px"
                      :src="hover ? `/icons/연필_h.gif` : `/icons/연필_o.png`"
                      alt
                      :style="
                        hover
                          ? `filter: brightness(0.9);`
                          : `filter: brightness(1);`
                      "
                    />
                    <span style="font-size: medium">레시피 만들기</span>
                  </v-btn>
                </template>
              </v-hover>
              <v-hover style="text-align: center">
                <template #default="{ hover }">
                  <v-btn
                    class="my-btn"
                    large
                    width="49%"
                    @click="moveCheckList"
                  >
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

                    <span style="font-size: medium">장보기목록</span>
                  </v-btn>
                </template>
              </v-hover>
            </div>
          </div>
        </div>

        <div class="chart-group mt-7 mb-7">
          <div>오늘 {{ time }} 기준</div>
          <div class="chart-header">
            <div>Let'cipe차트</div>
            <div>전체보기</div>
          </div>
          <div class="chart-chips-group">
            <v-sheet class="chip-sheet mx-auto">
              <v-slide-group v-model="selectTag" mandatory overflow >
                <v-slide-item v-for="(tag, i) in tag_set" :key="i" v-slot="{ active, toggle }">
                  <v-chip
                    class="mx-2"
                    :input-value="active"
                    active-class="letcipe white--text"
                    rounded
                    @click="toggle"
                  >{{tag}}</v-chip>
                </v-slide-item>
              </v-slide-group>
            </v-sheet>
          </div>

          <v-row>
            <v-col>
              <v-hover v-for="(data, i) in recipeChart[selectTag]" :key="i">
                <template #default="{ hover }">
                  <v-card
                    :elevation="hover ? 24 : 6"
                    :class="
                        hover ? 'letcipe lighten-2' : 'white'
                      "
                    class="lecipe-list-group mx-auto mt-2 mb-2 d-flex align-center"
                    @click="moveDetail2(data.recipe.id)"
                  >
                    <div class="ml-4" style="color: #FFA500;">{{ i+1 }}</div>
                    <v-list-item three-line>
                      <v-list-item-avatar tile size="57">
                        <v-img elevation="10" :src="data.recipe.repImg" style="border-radius: 5px"></v-img>
                      </v-list-item-avatar>
                      <v-list-item-content>
                        <v-list-item-title class="mb-1">
                          {{
                          data.recipe.title
                          }}
                        </v-list-item-title>
                        <v-list-item-subtitle>{{ data.recipe.content }}</v-list-item-subtitle>
                      </v-list-item-content>
                    </v-list-item>
                    <v-btn class="mr-3" fab dark x-small color="letcipe" outlined>
                      <v-icon color="letcipe">mdi-heart</v-icon>
                    </v-btn>
                  </v-card>
                </template>
              </v-hover>
            </v-col>
          </v-row>
        </div>
        <div class="ingrediant-base-group mt-2 mb-3">
          <div v-if="nickname!=''">{{nickname}} 님을 위한 레시피 추천</div>
          <div v-else>이런 레시피 어때요?</div>
          <div class="chart-header">
            <div>{{recommendTitle}}</div>
          </div>
          <div class="rec-imgs-group d-flex justify-space-between">
            <v-avatar
              v-for="(ref, i) in recommendRecipes"
              :key="i"
              size="130"
              tile
              class="mr-2"
              @click="moveDetail2(ref.recipe.id)"
            >
              <v-img class="ref-imgs" :src="ref.recipe.repImg">
                <div class="ref-wrap">
                  <v-card-title class="ref-title">{{ ref.recipe.title }}</v-card-title>
                  <v-card-subtitle class="ref-subtitle">{{
                    ref.recipe.content
                  }}</v-card-subtitle>
                </div>
              </v-img>
            </v-avatar>
          </div>
        </div>
        <div class="lecipe-base-group mt-3 mb-2">
          <div class="chart-header">
            <div>인기있는 레시피 리스트!</div>
          </div>
          <div v-for="(recipeList,i) in recipeLists" :key="i">
            <div class="chart-header">
              {{recipeList.name}}
            </div>
            <div class="chart-header">
              {{recipeList.description}}
            </div>
            <div class="rec-imgs-group d-flex justify-space-between">
              <v-avatar
                v-for="(item, j) in recipeList.recipeListItems"
                :key="j"
                size="130"
                tile
                class="mr-2"
                @click="moveListDetail"
              >
                <!-- <v-img class="ref-imgs" :src="item.recipe.repImg"></v-img> -->
                <v-img class="ref-imgs" :src="item.recipe.repImg">
              </v-img>
              </v-avatar>
            </div>
          </div>
          <!-- <div class="rec-imgs-group d-flex justify-space-between">
            <v-avatar
              v-for="(ref, i) in refImg"
              :key="i"
              size="130"
              tile
              class="mr-2"
              @click="moveListDetail(ref)"
            >
              <v-img class="ref-imgs" :src="'https://2bob.co.kr/' + ref.url">
                <div class="ref-wrap">
                  <v-card-title class="ref-title">{{ ref.title }}</v-card-title>
                  <v-card-subtitle class="ref-subtitle">{{ ref.sub_title }}</v-card-subtitle>
                </div>
              </v-img>
            </v-avatar>
          </div> -->
        </div>
      </v-container>
    </v-app>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from 'vuex'
import { getCartReport, getUserRecommend, getBestRecipeLists} from "@/api/recommend";

export default {
  name: 'MainPage',
  data() {
    return {
      userPlayList: [
        {
          url: 'https://img.icons8.com/emoji/344/orange-circle-emoji.png',
          sub_title: '아직 진행중인 레시피리스트가 없습니다.',
          title: '레시피를 진행해주세요',
          serving: 0,
        },
      ],
      refImg: [
        {
          url: 'data/recipe/20210810152825-4Y20E.jpg',
          sub_title: '끝맛이 개운한',
          title: '오이찜닭',
        },
        {
          url: 'data/recipe/20210810142007-EYPBD.jpg',
          sub_title: '색다른 꿀조합',
          title: '명란 오이무침',
        },
        {
          url: 'data/recipe/20210713113307-VT9JZ.jpg',
          sub_title: '식감이 재밌는',
          title: '소고기 오이볶음밥',
        },
        {
          url: 'data/recipe/20210708104052-PX6S9.jpg',
          sub_title: '오래도록 아삭한',
          title: '오이 물김치',
        },
        {
          url: 'data/recipe/20220825153420-BD8U3.png',
          sub_title: '다이어터를 위한 후식냉면',
          title: '오이냉면',
        },
      ],
      tag_set: [],
      lecipeData: [],
      recipeChart:[],
      selectTag:"",
      time: '',
      historyID: null,
      eating: null,
      tabs: null,
      check: false,
      checklist: [],
      checkedList: [],
      dialog1: false,
      dialog2: false,
      category: [],
      ingredients: [],
      isSelected: false,
      isRemoved: false,
      isComplete: false,
      selectedIngre: [],
      recommendTitle:"",
      recommendRecipes:[],
      recipeLists:[],
    }
  },
  computed: {
    ...mapState('user', ['userId', 'nickname']),
    ...mapState('search', ['recipes', 'recipeLists', 'hotRecipes', 'hotTitle']),
    ...mapState('history', ['history', 'historyList']),
  },
  created() {
    setInterval(this.findnow.bind(this), 1000)
    this.category = []
    const promise = new Promise((resolve, reject) => {
      resolve()
    })
    promise.then(async () => {
      console.log('이거슨감자' + this.recipeLists)

      await this.getHistoryList()
      this.historyList?.forEach((h) => {
        if (h.process === 'READY') {
          this.historyID = h.id
        } else if (h.process === 'EATING') {
          this.eating = h.id
        }
      })
      if (this.historyID !== null || this.eating !== null) {
        this.userPlayList = []
        if (this.historyID !== null) {
          await this.getHistory(this.historyID)
        } else {
          await this.getHistory(this.eating)
        }
        this.history.historyItems?.forEach((h) => {
          const playRecipe = {
            url: h.recipe.repImg,
            sub_title: h.recipe.content,
            title: h.recipe.title,
            serving: h.recipe.serving,
          }
          this.userPlayList.push(playRecipe)
        })
      }
      if (this.historyID !== null) {
        await this.getHistory(this.historyID)
        this.history.historyIngredients.forEach((jaeryo) => {
          if (jaeryo.isPurchased === 'N') {
            this.checklist.push(jaeryo)
          } else {
            this.checkedList.push(jaeryo)
          }
          if (this.category.length === 0) {
            this.category.push(jaeryo.categoryName)
          } else {
            let cnt = 0
            this.category?.forEach((cg) => {
              if (cg === jaeryo.categoryName) {
                cnt++
              }
            })
            if (cnt === 0) {
              this.category.push(jaeryo.categoryName)
            }
          }
        })
      }
      getCartReport("-,-,1,-",(response) => {
      this.tag_set.push(response.data.title);
      this.recipeChart.push(response.data.report);
      });
      getCartReport("-,-,-,JUBU",(response) => {
        this.tag_set.push(response.data.title);
        this.recipeChart.push(response.data.report);
      });
      getCartReport("-,-,-,COOK",(response) => {
        this.tag_set.push(response.data.title);
        this.recipeChart.push(response.data.report);
      });
      getUserRecommend(
        (response) => {
          this.recommendTitle = response.data.title;
          this.recommendRecipes = response.data.report;
      });
      getBestRecipeLists(1,
        (response) => {
          this.recipeLists = response.data;
        }
      );
    })
  },
  methods: {
    ...mapActions('history', [
      'getHistory',
      'getHistoryList',
      'checkHistoryIngredient',
      'updateHistory',
    ]),
    ...mapActions('search', ['getRecipes', 'getRecipeList', 'getHotRecipes']),
    ...mapMutations('recipe', ['SET_RECIPE_ID', 'CLEAR_RECIPE_ID']),
    findnow() {
      const today = new Date()
      const hours = ('0' + today.getHours()).slice(-2)
      const minutes = ('0' + today.getMinutes()).slice(-2)
      const seconds = ('0' + today.getSeconds()).slice(-2)
      this.time = hours + ':' + minutes + ':' + seconds
      // console.log(this.time)
    },
    moveListDetail(ref) {
      this.$router.push('/recipelist/detail')
    },
    moveDetail(data) {
      this.CLEAR_RECIPE_ID()
      this.SET_RECIPE_ID(data.recipeId)
      this.$router.push('/recipe/detail')
    },
    moveDetail2(recipeId) {
      this.CLEAR_RECIPE_ID()
      this.SET_RECIPE_ID(recipeId)
      this.$router.push('/recipe/detail')
    },
    moveProgress() {
      this.$router.push('/user/progress')
    },
    moveWrite() {
      this.$router.push('/recipe/create')
    },
    moveCheckList() {
      this.$router.push('/check')
    },
    bought(c, index) {
      this.checklist.splice(index, 1)
      this.checkedList.push(c)
      this.checkHistoryIngredient(c.id)
    },
    needtobuy(c, index) {
      this.checkedList.splice(index, 1)
      this.checklist.push(c)
      this.checkHistoryIngredient(c.id)
    },
    checkAll() {
      this.checklist.forEach((c) => {
        this.checkHistoryIngredient(c.id)
        this.checkedList.push(c)
      })
      this.checklist = []
    },
    removeAll() {
      this.checkedList.forEach((c) => {
        this.checkHistoryIngredient(c.id)
        this.checklist.push(c)
      })
      this.checkedList = []
    },
    createRecipe() {
      this.$router.push('/recipe/create')
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
  /* border: 1px solid rgb(42, 55, 246); */
}

.main-container {
  height: 100%;
  padding: 10%;
  /* background-image: url('/bg/bg_img.png'); */
  background-repeat: repeat;
  background-color: white;
  /* background-color: rgba(255, 255, 221, 0.771); */
  color: black;
  margin-top: 70px;
  margin-bottom: 70px;
}
.title {
  font-size: x-large;
  font-family: 'LeeSeoyun';
}
.v-application .title {
  font-family: 'LeeSeoyun' !important;
  font-size: x-large !important;
}
.title-wrap {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.title-imgs {
  display: flex;
  overflow: auto;
  cursor: pointer;
  transform: translate3d(0, 0, 0);
}

/* 체크리스트 */
.check-container {
  height: 280px;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
}

.check-container {
}
.check-item-wrap {
  height: 200px;
  overflow: scroll;
}
.before-shopping {
  padding: 4%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
}
.after-shopping {
  padding: 4%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
}
.before-shopping-none,
.after-shopping-none {
  height: 165px;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
}

.card {
  margin-right: 10%;
  width: 150px;
}
.ref-imgs {
  width: 150px;
  height: 150px !important;
  cursor: pointer;
}

.ref-wrap {
  width: 150px;
  height: 80px;
  background-color: rgba(49, 49, 49, 0.422);
}
.ref-title {
  /* color: rgb(0, 0, 0); */
  color: aliceblue;
  text-align: right;
  font-size: large;
  font-weight: bolder;
  display: block;
  overflow: auto;
  text-overflow: ellipsis;
}
.ref-subtitle {
  /* color: rgb(0, 0, 0); */
  color: aliceblue;
  text-align: right;
}
.my-container {
  padding-top: 10%;
  padding-bottom: 10%;
}
.my-title {
  font-family: 'LeeSeoyun' !important;
}
.my-sub-title {
  text-overflow: ellipsis;
  height: 2.3rem;
  word-wrap: break-word;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
.my-card {
  border-radius: 10px;
  cursor: pointer;
}

.my-lecipe {
  display: flex;
  justify-content: center;
}

/* 캐러셀 css */
.v-carousel__controls {
  display: none !important;
}
.my-list-carousel {
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
}
/* btn-group css */

/* cart-group css */
.cart-group {
  margin-top: 3%;
  margin-bottom: 3%;
}
/* btn-group css */
.btn-group {
  display: flex;
  justify-content: space-between;
}

/* chart-group css */

.chart-header {
  display: flex;
  justify-content: space-between;
}
.chart-chips-group {
  display: flex;
  overflow-x: auto;
}
.tag-set {
  padding: 0 12px;
  display: flex;
  justify-content: center;
  text-align: center;
  cursor: pointer;
}
.lecipe-list-group {
  cursor: pointer;
}

/* rec-imgs-group css */
.rec-imgs-group {
  overflow: auto;
}
.container {
  max-width: 100%;
}

@media (min-width: 900px) {
  .my-middle-wrap {
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .my-middle {
    width: 48%;
  }

  .chip-sheet {
    max-width: 100%;
  }
  .my-list-carousel {
    height: 300px;
  }
  .my-card {
    height: 340px;
    align-items: center;
    display: flex;
    justify-content: center;
  }
  /* .check-container {
    height: 190px;
    box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  }
  .check-item-wrap {
    height: 110px;
    overflow: scroll;
  }
  .after-shopping-none {
    height: 165px;
    box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  } */
}
/* 모바일 screen */
@media (max-width: 400px) {
  .ref-imgs {
    width: 200px;
    height: 200px;
  }

  .imgs {
    justify-content: space-between;
    overflow-x: auto;
  }

  .main-container {
    height: 100%;
    padding: 10%;
    /* background-image: url('/bg/bg_img.png'); */
    background-repeat: repeat;
    background-color: white;
    /* background-color: rgba(255, 255, 221, 0.771); */
    color: black;
    margin-top: 70px;
    margin-bottom: 70px;
  }
  .title {
    font-size: x-large;
    font-family: 'LeeSeoyun';
  }
  .v-application .title {
    font-family: 'LeeSeoyun' !important;
    font-size: x-large !important;
  }
  .title-wrap {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
  .title-imgs {
    display: flex;
    overflow: auto;
    cursor: pointer;
    transform: translate3d(0, 0, 0);
  }

  /* 체크리스트 */
  .check-container {
    height: 280px;
    box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  }

  .check-container {
  }
  .check-item-wrap {
    height: 200px;
    overflow: scroll;
  }
  .before-shopping {
    padding: 4%;
    box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  }
  .after-shopping {
    padding: 4%;
    box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  }
  .after-shopping-none {
    height: 165px;
    box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  }

  .card {
    margin-right: 10%;
    width: 150px;
  }

  .ref-wrap {
    width: 150px;
    height: 80px;
    background-color: rgba(49, 49, 49, 0.422);
  }
  .ref-title {
    /* color: rgb(0, 0, 0); */
    color: aliceblue;
    text-align: right;
    font-size: large;
    font-weight: bolder;
    display: block;
    overflow: auto;
    text-overflow: ellipsis;
  }
  .ref-subtitle {
    /* color: rgb(0, 0, 0); */
    color: aliceblue;
    text-align: right;
  }
  .my-container {
    padding-top: 10%;
    padding-bottom: 10%;
  }
  .my-title {
    font-family: 'LeeSeoyun' !important;
  }
  .my-card {
    border-radius: 10px;
    cursor: pointer;
  }

  .my-lecipe {
    display: flex;
    justify-content: center;
  }

  /* 캐러셀 css */
  .v-carousel__controls {
    display: none !important;
  }
  .my-list-carousel {
    box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  }
  /* btn-group css */

  /* cart-group css */
  .cart-group {
    margin-top: 3%;
    margin-bottom: 3%;
  }
  /* btn-group css */
  .btn-group {
    display: flex;
    justify-content: space-between;
  }

  /* chart-group css */

  .chart-header {
    display: flex;
    justify-content: space-between;
  }
  .chart-chips-group {
    display: flex;
    overflow-x: auto;
  }
  .tag-set {
    padding: 0 12px;
    display: flex;
    justify-content: center;
    text-align: center;
    cursor: pointer;
  }
  .lecipe-list-group {
    cursor: pointer;
  }

  /* rec-imgs-group css */
  .rec-imgs-group {
    overflow: auto;
  }
  .container {
    max-width: 100%;
  }
  .chip-sheet {
    max-width: 700px;
  }
  .recipe-item {
    width: 85px;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}
</style>
