<template>
  <div id="app">
    <v-app id="inspire">
      <div class="search-page">
        <v-container class="search-container d-flex-row">
          <div class="search-head-wrap">
            <div class="d-flex justify-space-between pb-3">
              <div>
                <v-icon @click="moveBack">mdi-window-close</v-icon>
              </div>
              <div style="font-size: x-large">검색하기</div>
              <div>
                <v-icon>mdi-blank</v-icon>
              </div>
            </div>
          </div>
          <v-tabs
            v-model="tab"
            centered
            icons-and-text
            color="letcipe"
            hide-slider
          >
            <v-tabs-slider></v-tabs-slider>

            <v-tab href="#tab-1" style="width: 50%">
              이름 또는 태그로 검색
              <v-icon>mdi-hamburger</v-icon>
            </v-tab>

            <v-tab href="#tab-2" style="width: 50%">
              재료로 검색
              <v-icon>mdi-carrot</v-icon>
            </v-tab>
          </v-tabs>

          <v-tabs-items v-model="tab">
            <v-tab-item :value="`tab-1`">
              <v-row align="center" class="pl-6 pr-6">
                <v-col class="d-flex pr-0 pl-0" cols="3">
                  <v-row>
                    <v-btn
                      style="width: 90%"
                      depressed
                      :color="byRecipe ? 'letcipe' : 'black'"
                      text
                      small
                      class="pt-0 pb-0"
                      @click="byRecipe = true"
                      >레시피</v-btn
                    >
                    <v-btn
                      style="width: 90%"
                      :color="byRecipe ? 'black' : 'letcipe'"
                      text
                      small
                      class="pt-0 pb-0"
                      @click="byRecipe = false"
                      >레시피 리스트</v-btn
                    >
                  </v-row>
                </v-col>
                <v-col class="d-flex pr-0 pl-0" align="center">
                  <v-text-field
                    v-model="byname"
                    label="이름 또는 태그로 검색"
                    outlined
                    class="pt-3 pb-3"
                    color="letcipe"
                    append-outer-icon="mdi-magnify"
                    @click:append-outer="searchByName()"
                    @keyup.enter="searchByName"
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-divider></v-divider>
              <div v-if="recipes != null && recipes.length > 0">
                <v-card-subtitle
                  >"{{ searchedName }}" 검색 결과</v-card-subtitle
                >
                <div v-for="(recipeInfo, i) in recipes" :key="i">
                  <v-list-item three-line>
                    <v-list-item-avatar
                      tile
                      size="100"
                      @click="moveDetail(recipeInfo)"
                    >
                      <v-img :src="recipeInfo['repImg']"></v-img>
                    </v-list-item-avatar>
                    <v-list-item-content>
                      <v-list-item-title @click="moveDetail(recipeInfo)">{{
                        recipeInfo['title']
                      }}</v-list-item-title>

                      <v-list-item-subtitle @click="moveDetail(recipeInfo)">{{
                        recipeInfo['content']
                      }}</v-list-item-subtitle>
                      <div class="d-flex justify-space-between">
                        <v-list-item-subtitle>
                          <v-icon small color="pink lighten-1"
                            >mdi-cards-heart</v-icon
                          >
                          {{ recipeInfo['recipeLike'] }}
                        </v-list-item-subtitle>

                        <v-list-item-subtitle style="text-align: right">
                          <v-btn
                            small
                            color="letcipe"
                            @click="addRecipe(recipeInfo['id'])"
                            >+담기</v-btn
                          >
                        </v-list-item-subtitle>
                      </div>
                    </v-list-item-content>
                  </v-list-item>
                  <v-divider></v-divider>
                </div>
                <div class="text-center">
                  <v-pagination
                    v-model="currentPage"
                    color="letcipe"
                    :length="Math.ceil(totalPage / 5)"
                    prev-icon="mdi-menu-left"
                    next-icon="mdi-menu-right"
                    @input="handlePage"
                  ></v-pagination>
                </div>
              </div>
              <div v-if="recipeLists != null && recipeLists.length > 0">
                <v-card-subtitle
                  >"{{ searchedName }}" 검색 결과</v-card-subtitle
                >
                <div v-for="(recipeListInfo, i) in recipeLists" :key="i">
                  <v-list-item three-line>
                    <v-list-item-avatar
                      tile
                      size="100"
                      @click="moveDetail(recipeListInfo)"
                    >
                      <v-img
                        :src="recipeListInfo.recipeListItems[0].repImg"
                      ></v-img>
                    </v-list-item-avatar>
                    <v-list-item-content>
                      <v-list-item-title @click="moveDetail(recipeListInfo)">{{
                        recipeListInfo['name']
                      }}</v-list-item-title>

                      <v-list-item-subtitle
                        @click="moveDetail(recipeListInfo)"
                        >{{
                          recipeListInfo['description']
                        }}</v-list-item-subtitle
                      >
                      <div class="d-flex justify-space-between">
                        <v-list-item-subtitle style="margin: auto">
                          by
                          {{ recipeListInfo['nickname'] }}
                        </v-list-item-subtitle>
                        <v-list-item-subtitle style="text-align: right">
                          <v-btn
                            small
                            color="letcipe"
                            @click="addRecipeList(recipeListInfo)"
                            >+전체담기</v-btn
                          >
                        </v-list-item-subtitle>
                      </div>
                    </v-list-item-content>
                  </v-list-item>
                  <v-divider></v-divider>
                </div>
                <!-- <div class="text-center">
                  <v-pagination
                    v-model="commentPage"
                    color="letcipe"
                    :length="commentTotalPage"
                    prev-icon="mdi-menu-left"
                    next-icon="mdi-menu-right"
                    @input="handlePage"
                  ></v-pagination>
                </div>-->
              </div>
            </v-tab-item>
            <v-tab-item :value="`tab-2`">
              <v-row>
                <v-col align="center">
                  <v-autocomplete
                    v-model="keyword"
                    :items="ingredientsList"
                    :loading="isLoading"
                    :search-input.sync="search"
                    clearable
                    hide-details
                    hide-selected
                    hide-spin-buttons
                    item-text="name"
                    item-value="id"
                    label="재료로 검색"
                    outlined
                    class="pt-3 pb-3"
                    color="letcipe"
                    style="width: 90%"
                    append-outer-icon="mdi-magnify"
                    @keyup="ingre(search)"
                  >
                    <template #no-data>
                      <v-list-item>
                        <v-list-item-title
                          >일치하는 재료가 없습니다.</v-list-item-title
                        >
                      </v-list-item>
                    </template>
                    <template #item="{ item }">
                      <v-list-item-content @click="selectIngre(item)">
                        <v-list-item-title
                          v-text="item.name"
                        ></v-list-item-title>
                      </v-list-item-content>
                      <v-list-item-action @click="selectIngre(item)">
                        <v-chip :color="colors[item.category]" label>{{
                          item.category
                        }}</v-chip>
                      </v-list-item-action>
                    </template>
                  </v-autocomplete>
                </v-col>
              </v-row>

              <v-card-subtitle v-if="isSelected.length < 2" align="center"
                >두 개 이상의 재료를 선택해 주세요</v-card-subtitle
              >
              <v-card-subtitle v-if="isSelected.length >= 2"
                >선택한 재료 목록</v-card-subtitle
              >
              <div align="center">
                <v-row class="pb-5" style="width: 90%">
                  <div v-for="(item, i) in isSelected" :key="i">
                    <v-col>
                      <v-chip
                        label
                        :color="colors[item.category]"
                        @click="deleteIngre(i)"
                      >
                        {{ item.name }}
                        <v-icon small>mdi-window-close</v-icon>
                      </v-chip>
                    </v-col>
                  </div>
                </v-row>
              </div>
              <v-divider></v-divider>
              <div
                v-if="
                  isSelected.length > 0 &&
                  recipesIngre != null &&
                  recipesIngre.length > 0
                "
              >
                <v-card-subtitle>재료 기반 추천 레시피</v-card-subtitle>
                <div v-for="(recipeInfo, i) in recipesIngre" :key="i">
                  <v-list-item three-line style="background-color: white">
                    <v-list-item-avatar
                      tile
                      size="100"
                      @click="moveDetail(recipeInfo)"
                    >
                      <v-img :src="recipeInfo['repImg']"></v-img>
                    </v-list-item-avatar>
                    <v-list-item-content>
                      <v-list-item-title @click="moveDetail(recipeInfo)">{{
                        recipeInfo['title']
                      }}</v-list-item-title>

                      <v-list-item-subtitle @click="moveDetail(recipeInfo)">{{
                        recipeInfo['content']
                      }}</v-list-item-subtitle>
                      <div class="d-flex justify-space-between">
                        <v-list-item-subtitle>
                          <v-icon small color="pink lighten-1"
                            >mdi-cards-heart</v-icon
                          >
                          {{ recipeInfo['recipeLike'] }}
                        </v-list-item-subtitle>

                        <v-list-item-subtitle style="text-align: right">
                          <v-btn
                            small
                            color="letcipe"
                            @click="addRecipe(recipeInfo['id'])"
                            >+담기</v-btn
                          >
                        </v-list-item-subtitle>
                      </div>
                    </v-list-item-content>
                  </v-list-item>
                  <v-divider></v-divider>
                </div>
                <div class="text-center">
                  <v-pagination
                    v-model="currentPage"
                    color="letcipe"
                    :length="Math.ceil(totalPage / 5)"
                    prev-icon="mdi-menu-left"
                    next-icon="mdi-menu-right"
                    @input="handlePage"
                  ></v-pagination>
                </div>
              </div>
              <div
                v-if="
                  isSelected.length >= 2 &&
                  (isSelected.length == 0 ||
                    recipesIngre == null ||
                    recipesIngre.length == 0)
                "
              >
                <v-card-subtitle align="center"
                  >검색 결과가 없습니다.</v-card-subtitle
                >
              </div>
            </v-tab-item>
          </v-tabs-items>

          <v-dialog v-model="dialogSameIngre" persistent max-width="290">
            <v-card>
              <v-card-title>Caution</v-card-title>
              <v-card-text>이미 추가된 레시피입니다.</v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                  color="green darken-1"
                  text
                  @click="dialogSameIngre = false"
                  >확인</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-container>
      </div>
    </v-app>
  </div>
</template>

<script>
import { mapActions, mapState, mapMutations } from 'vuex'
export default {
  name: 'SearchMain',
  data() {
    return {
      currentPage: 1,
      byname: '',
      searchedName: '',
      dialogSameIngre: false,
      byRecipe: true,
      scroll: 0,
      scrollTarget: '',
      colors: {
        '가루 분말': 'purple',
        '감자 고구마': 'pink',
        고기: 'pink darken-3',
        곡류: 'purple lighten-1',
        과일: 'red lighten-5',
        과자: 'green lighten-3',
        '국물 육수': 'green',
        기름: 'yellow darken-4',
        기타: 'blue lighten-3',
        달걀: 'blue darken-4',
        '떡 면': 'red',
        '묵 두부': 'purple darken-3',
        빵: 'red lighten-2',
        어패류: 'yellow lighten-1',
        '유제품 치즈': 'blue darken-2',
        '음료 주류': 'green lighten-1',
        '음식 식품': 'pink lighten-3',
        절임류: 'light-green darken-4',
        '조미료 향신료 소스': 'blue',
        채소: 'light-green',
        '초콜릿 사탕': 'light-green lighten-2',
        '콩 견과류': 'yellow',
        해조류: 'grey',
        '햄 소시지': 'black',
      },
      isLoading: false,
      items: [],
      keyword: '',
      search: null,
      tab: null,
      isSelected: [],
      selectedIngre: '',
    }
  },
  computed: {
    ...mapState('ingredients', ['ingredientsList']),
    ...mapState('search', [
      'recipes',
      'recipesIngre',
      'recipeLists',
      'totalPage',
    ]),
    ...mapState('cart', ['cart', 'ingreList', 'amountByRecipe']),
  },

  watch: {
    tab() {
      console.log('====')
      this.CLEAR_RECIPE()
      this.CLEAR_RECIPE_INGRE()
      this.CLEAR_RECIPE_LIST()
      this.byname = ''
      this.isSelecte = []
      this.currentPage = 1
    },
  },
  created() {
    const promise = new Promise((resolve, reject) => {
      resolve()
    })
    promise.then(async () => {
      await this.readCart()
    })
    this.CLEAR_RECIPE()
    this.CLEAR_RECIPE_INGRE()
    this.CLEAR_RECIPE_LIST()
  },
  methods: {
    ...mapMutations('search', [
      'CLEAR_RECIPE',
      'CLEAR_RECIPE_INGRE',
      'CLEAR_RECIPE_LIST',
    ]),
    ...mapMutations('recipe', ['SET_RECIPE_ID', 'CLEAR_RECIPE_ID']),
    ...mapActions('ingredients', ['searchIngredient']),
    ...mapActions('search', ['getRecipes', 'getRecipesIngre', 'getRecipeList']),
    ...mapActions('cart', ['createCart', 'readCart']),
    moveBack() {
      this.$router.go(-1)
    },
    moveDetail(data) {
      this.CLEAR_RECIPE_ID()
      this.SET_RECIPE_ID(data.id)
      this.$router.push('/recipe/detail')
    },
    ingre(keyword) {
      if (keyword != null && keyword.length > 0) {
        keyword = keyword.trim()
        if (keyword.length > 0 && keyword != null) {
          this.searchIngredient(keyword)
        }
      }
    },
    // nextPage() {
    //   this.TotalPage++
    //   this.currentPage = this.currentPage + 1
    //   console.log('----------------')
    // },
    handlePage(page) {
      if (this.tab === 'tab-1') {
        if (this.byRecipe) {
          const searchObject = {
            keyword: this.byname,
            size: 5,
            page: this.currentPage - 1,
          }
          const promise = new Promise((resolve, reject) => {
            resolve()
          })
          promise.then(async () => {
            await this.getRecipes(searchObject)
          })
        } else {
          const searchObject = {
            keyword: this.byname,
            size: 5,
            page: this.currentPage - 1,
          }
          const promise = new Promise((resolve, reject) => {
            resolve()
          })
          promise.then(async () => {
            await this.getRecipeList(searchObject)
          })
        }
        this.searchedName = this.byname
      } else {
        const searchObject = {
          ingredients: this.selectedIngre,
          size: 5,
          page: this.currentPage - 1,
        }
        const promise = new Promise((resolve, reject) => {
          resolve()
        })
        promise.then(async () => {
          await this.getRecipesIngre(searchObject)
        })
      }
    },
    selectIngre(item) {
      this.currentPage = 1
      if (!String(this.selectedIngre).includes(String(item.id))) {
        this.isSelected.push(item)
        if (this.selectedIngre.length === 0) {
          this.selectedIngre = item.id
        } else {
          this.selectedIngre = this.selectedIngre + ',' + item.id
        }
      } else {
        this.dialogSameIngre = true
      }

      if (this.selectedIngre !== '') {
        const searchObject = {
          ingredients: this.selectedIngre,
          size: 5,
          page: this.currentPage - 1,
        }
        this.getRecipesIngre(searchObject)
      }
    },
    deleteIngre(index) {
      if (this.isSelected.length === 1) {
        this.selectedIngre = ''
      } else {
        this.selectedIngre = this.selectedIngre
          .toString()
          .replace(`,${this.isSelected[index].id}`, '')
      }
      this.isSelected.splice(index, 1)
      if (this.selectedIngre !== '') {
        const searchObject = {
          ingredients: this.selectedIngre,
          size: 5,
          page: this.currentPage - 1,
        }
        this.getRecipesIngre(searchObject)
      }
    },
    searchByName() {
      this.currentPage = 1
      if (this.byRecipe) {
        const searchObject = {
          keyword: this.byname,
          size: 5,
          page: this.currentPage - 1,
        }
        this.getRecipes(searchObject)
      } else {
        const searchObject = {
          keyword: this.byname,
          size: 5,
          page: this.currentPage - 1,
        }
        this.getRecipeList(searchObject)
      }
      this.searchedName = this.byname
    },
    addRecipe(recipeId) {
      const recipeList = []
      recipeList.push(recipeId)
      const addrecipes = {
        list: recipeList,
      }
      this.createCart(addrecipes)
    },
    addRecipeList(recipeListInfo) {
      const recipeList = []
      for (let i = 0; i < recipeListInfo.recipeListItems.length; i++) {
        for (let j = 0; j < recipeListInfo.recipeListItems[i].amount; j++)
          recipeList.push(recipeListInfo.recipeListItems[i].recipeId)
      }
      const addrecipes = {
        list: recipeList,
      }
      this.createCart(addrecipes)
    },
  },
}
</script>

<style scoped>
.search-page {
  /* padding-top: 70px; */
  padding-bottom: 70px;
  padding: 4%;
}
.search-page-head {
  padding: 4%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  /* border: 1px solid gray; */
}
.search-container {
  position: sticky;
  height: 100%;
  color: black;

  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
}
:deep(.v-input__icon.v-input__icon--append) {
  display: none;
}
:deep(.v-text-field__details) {
  display: none;
}
</style>
