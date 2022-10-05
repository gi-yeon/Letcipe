<template>
  <div id="app">
    <v-app id="inspire">
      <div class="myrecipe-page">
        <v-container class="myrecipe-container d-flex-row">
          <div class="myrecipe-head-wrap">
            <div class="d-flex justify-space-between pb-3">
              <div>
                <v-icon @click="moveMypage">mdi-window-close</v-icon>
              </div>

              <div style="font-size: x-large">내가 좋아하는 레시피</div>
              <div>
                <v-icon>mdi-blank</v-icon>
              </div>
            </div>
          </div>
          <v-divider></v-divider>

          <!-- <div :page="currentPage" :items="myRecipes" :items-per-page="perPage" class="text-center"> -->
          <div v-if="recipeList.length > 0">
            <div v-for="(mr, i) in recipeList" :key="i">
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
                        <v-icon
                          v-if="recipeLike[i]"
                          small
                          color="pink lighten-1"
                          @click="saveLike(i, mr)"
                          >mdi-heart</v-icon
                        >
                        <v-icon
                          v-else
                          small
                          color="grey"
                          @click="saveLike(i, mr)"
                          >mdi-heart-outline</v-icon
                        >
                      </div>
                    </v-list-item-subtitle>
                    <v-list-item-subtitle style="text-align: right">
                      <v-btn
                        style="z-index: 1"
                        small
                        color="letcipe"
                        @click="openDialog(mr)"
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
              <v-list-item three-line>좋아하는 레시피가 없습니다.</v-list-item>
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

        <div>
          <v-row class="justify-center pa-8">
            <v-dialog v-model="dialog" max-width="290">
              <v-card>
                <v-card-title class="text-h5"
                  >장바구니에 담을까요?</v-card-title
                >
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="green darken-1" text @click="addCart()"
                    >담기</v-btn
                  >

                  <v-btn color="red darken-1" text @click="dialog = false"
                    >닫기</v-btn
                  >
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-row>
        </div>
      </div>
    </v-app>
  </div>
</template>

<script>
import { mapActions, mapState, mapMutations } from 'vuex'
export default {
  name: 'MyLikePage',
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
      recipeLike: [],
      dialog: false,

      recipe: null,
    }
  },
  computed: {
    ...mapState('user', ['nickname', 'mylikeRecipe']),
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
      // 이부분만 고쳐서 쓰면됩니다.
      await this.myLikeRecipe(pageable)
      this.mylikeRecipe.forEach((lr) => {
        this.recipeLike.push(true)
        this.recipeList.push(lr)
      })
      this.TotalPage = this.recipeList.length / 5
    })
  },
  methods: {
    ...mapActions('recipe', [
      'patchRecipeDetail',
      'countRecipeLikes',
      'decountRecipeLikes',
    ]),

    ...mapActions('user', ['myLikeRecipe']),
    ...mapActions('cart', ['createCart']),
    ...mapMutations('recipe', ['SET_RECIPE_ID', 'CLEAR_RECIPE_ID']),
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
    addCart() {
      const cartItem = [this.recipe.id]
      const list = { list: cartItem }
      console.log(list)
      this.createCart(list)
      this.dialog = false
    },
    saveLike(i, mr) {
      if (this.recipeLike[i]) {
        this.decountRecipeLikes(mr.id)
      } else {
        this.countRecipeLikes(mr.id)
      }
      this.$set(this.recipeLike, i, !this.recipeLike[i])
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
