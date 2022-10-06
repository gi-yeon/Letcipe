<template>
  <div id="app">
    <v-app id="inspire">
      <div class="bookmark-page">
        <v-container class="bookmark-container d-flex-row">
          <div class="bookmark-head-wrap">
            <div class="d-flex justify-space-between pb-3">
              <div>
                <v-icon @click="moveMypage">mdi-window-close</v-icon>
              </div>
              <div style="font-size: x-large">즐겨찾는 레시피</div>
              <div>
                <v-icon>mdi-blank</v-icon>
              </div>
            </div>
          </div>
          <v-divider></v-divider>

          <v-card-subtitle>즐겨찾는 레시피</v-card-subtitle>

          <div v-if="recipeBookmarks.length > 0">
            <div v-for="(mr, i) in recipeBookmarks" :key="i">
              <v-list-item three-line>
                <v-list-item-avatar
                  class="recipe-item"
                  tile
                  size="100"
                  @click="moveDetail(mr)"
                >
                  <v-img :src="mr.repImg"></v-img>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title class="d-flex justify-space-between">
                    <div class="recipe-item" @click="moveDetail(mr)">
                      {{ mr.title }}
                    </div>
                  </v-list-item-title>

                  <v-list-item-subtitle
                    class="recipe-item"
                    @click="moveDetail(mr)"
                    >{{ mr.content }}</v-list-item-subtitle
                  >
                  <div class="d-flex justify-space-between">
                    <v-list-item-subtitle class="d-flex align-center">
                      <div class="d-flex align-center">
                        <v-icon small color="pink lighten-1"
                          >mdi-cards-heart</v-icon
                        >
                        <div>{{ mr.recipeLike }}</div>
                      </div>
                      <div>
                        <div class="d-flex align-center">
                          <v-icon small color="yellow lighten-1"
                            >mdi-bookmark</v-icon
                          >
                          <div>{{ mr.recipeBookmark }}</div>
                        </div>
                      </div>
                    </v-list-item-subtitle>
                    <v-list-item-subtitle style="text-align: right">
                      <v-btn
                        style="z-index: 1"
                        small
                        color="letcipe"
                        @click="addCart(mr)"
                        >+담기</v-btn
                      >
                    </v-list-item-subtitle>
                  </div>
                </v-list-item-content>
              </v-list-item>
              <v-divider></v-divider>
            </div>
          </div>
          <div v-else>
            <div>
              <v-list-item three-line
                >즐겨찾기에 추가된 레시피가 없습니다.</v-list-item
              >
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
      <v-snackbar
        v-model="snackbar"
        max-width="290"
        style="z-index: 100; margin-bottom: 60px"
        :timeout="timeout"
      >
        {{ text }}
      </v-snackbar>
    </v-app>
  </div>
</template>

<script>
import { mapActions, mapState, mapMutations } from 'vuex'
export default {
  name: 'BookmarkPage',
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
      recipeBookmarks: [],
      dialog: false,
      recipe: null,
      snackbar: false,
      timeout: 2000,
      text: '',
    }
  },
  computed: {
    ...mapState('user', ['nickname', 'myRecipe', 'myBookMarkRecipe']),
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
      await this.myBookmarkRecipe(pageable)
      this.myBookMarkRecipe.forEach((mr) => {
        this.recipeBookmarks.push(mr)
      })
      this.TotalPage = this.myBookMarkRecipe.length / 5
    })
  },
  methods: {
    ...mapActions('recipe', [
      'patchRecipeDetail',
      'countRecipeLikes',
      'decountRecipeLikes',
      'selectBookmarks',
      'deleteBookmarks',
    ]),
    ...mapActions('user', ['myBookmarkRecipe']),
    ...mapMutations('recipe', ['SET_RECIPE_ID', 'CLEAR_RECIPE_ID']),
    ...mapActions('cart', ['createCart']),
    moveDetail(mr) {
      this.CLEAR_RECIPE_ID()
      this.SET_RECIPE_ID(mr.id)
      this.$router.push('/recipe/detail')
    },
    moveMypage() {
      this.$router.push('/user/mypage')
    },
    openDialog(recipe) {
      this.dialog = true
      this.recipe = recipe
    },
    addCart(mr) {
      const cartItem = [mr.id]
      const list = { list: cartItem }
      this.createCart(list)
      this.text = '장바구니에 레시피가 담겼습니다!'
      this.snackbar = true
    },
  },
}
</script>

<style scoped>
.bookmark-page {
  /* padding-top: 70px; */
  padding-bottom: 70px;
  padding: 4%;
}
.bookmark-page-head {
  padding: 4%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  /* border: 1px solid gray; */
}
.bookmark-container {
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
