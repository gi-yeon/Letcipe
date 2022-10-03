<template>
  <div id="app">
    <v-app id="inspire">
      <div class="myrecipe-page">
        <v-container class="myrecipe-container d-flex-row">
          <div class="myrecipe-head-wrap">
            <div class="d-flex justify-space-between pb-3">
              <v-icon @click="moveMypage">mdi-window-close</v-icon>
              <div style="font-size: x-large">히스토리</div>
              <v-icon>mdi-blank</v-icon>
            </div>
          </div>
          <v-divider></v-divider>

          <v-card-subtitle>히스토리</v-card-subtitle>
          <!-- <div :page="currentPage" :items="myRecipes" :items-per-page="perPage" class="text-center"> -->
          <div v-if="recipeList.length > 0">
            <div v-for="(mr, i) in recipeList" :key="i">
              <v-list-item three-line>
                <v-list-item-avatar class="recipe-item" tile size="100" @click="moveDetail(mr)">
                  <v-img :src="mr.repImg"></v-img>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title class="d-flex justify-space-between">
                    <div class="recipe-item" @click="moveDetail(mr)">{{ mr.title }}</div>
                    <div>
                      <v-icon
                        style="z-index: 1"
                        small
                        color="info"
                        @click="writeReview(mr)"
                      >mdi-pencil</v-icon>
                    </div>
                  </v-list-item-title>
                  <v-list-item-subtitle class="recipe-item" @click="moveDetail(mr)">{{ mr.review }}</v-list-item-subtitle>
                  <div class="d-flex justify-space-between">
                    <v-list-item-subtitle class="d-flex align-center">
                      <div class="d-flex align-center">
                        <v-icon small color="letcipe">mdi-calendar-clock</v-icon>
                        <div style="font-size: x-small">{{mr.regTime }}</div>
                      </div>
                    </v-list-item-subtitle>
                    <v-list-item-subtitle style="text-align: right">
                      <v-btn style="z-index: 1" small color="letcipe" @click="addCart(mr)">+담기</v-btn>
                    </v-list-item-subtitle>
                  </div>
                </v-list-item-content>
              </v-list-item>
              <v-divider></v-divider>
            </div>
          </div>
          <div v-else>
            <div>
              <v-list-item three-line>완료된 목록이 없습니다.</v-list-item>
              <v-divider></v-divider>
            </div>
          </div>
          <v-pagination
            v-model="currentPage"
            color="letcipe"
            :length="TotalPage"
            :per-page="perPage"
            :total-visivle="TotalPage"
            circle
          ></v-pagination>
        </v-container>
      </div>
    </v-app>
  </div>
</template>
      
      <script>
import { mapActions, mapState, mapMutations } from 'vuex'
export default {
  name: 'MyHistoryPage',
  data() {
    return {
      TotalPage: 0,
      perPage: 5,
      currentPage: 1,
      byname: '',
      searchedName: '',
      dialogSameIngre: false,
      byRecipe: true,
      scroll: 0,
      scrollTarget: '',
      isLoading: false,
      items: [],
      keyword: '',
      search: null,
      tab: null,
      isSelected: [],
      selectedIngre: '',
      recipeList: [],
    }
  },
  computed: {
    ...mapState('user', ['myRecipe']),
    ...mapState('history', ['historyList']),
  },

  watch: {},

  created() {
    const pageable = {
      page: 0,
    }
    const promise = new Promise((resolve, reject) => {
      resolve()
    })
    promise.then(async () => {
      await this.getHistoryList(pageable)
      this.historyList?.forEach((mr) => {
        if (mr.process === 'END') {
          const myHistory = {
            regTime: mr.regTime.split('T')[0],
            review: mr.review,
            repImg: mr.historyItems[0].recipe.repImg,
            title:
              mr.historyItems[0].recipe.title + ' 외 ' + mr.historyItems.length,
          }
          this.recipeList.push(myHistory)
        }
      })
      //   this.TotalPage = this.myRecipe.length / 5
      //   console.log(this.TotalPage)
      //   console.log(this.myRecipes)
      console.log(this.historyList)
    })
  },
  methods: {
    ...mapActions('recipe', ['patchRecipeDetail']),
    ...mapActions('history', ['getHistoryList']),
    ...mapActions('user', ['myrecipe']),
    ...mapActions('cartr', ['createCart']),
    ...mapMutations('recipe', ['SET_RECIPE_ID', 'CLEAR_RECIPE_ID']),
    writeReview(mr) {
      this.CLEAR_RECIPE_ID()
      this.SET_RECIPE_ID(mr.id)
      this.$router.push('/recipe/modify')
    },
    moveDetail(mr) {
      this.CLEAR_RECIPE_ID()
      this.SET_RECIPE_ID(mr.id)
      this.$router.push('/recipe/detail')
    },
    moveMypage() {
      this.$router.push('/user/mypage')
    },
    addCart(mr) {
      const cartItem = [mr.id]
      const list = { cartItem }
      this.createCart(list)
    },
  },
}
</script>
      
      <style scoped>
.myrecipe-page {
  /* padding-top: 70px; */
  padding-bottom: 70px;
  padding: 4%;
}
.myrecipe-page-head {
  padding: 4%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  /* border: 1px solid gray; */
}
.myrecipe-container {
  position: sticky;
  height: 100%;
  color: black;

  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
}
.recipe-item {
  cursor: pointer;
}
:deep(.v-input__icon.v-input__icon--append) {
  display: none;
}
:deep(.v-text-field__details) {
  display: none;
}
</style>
      