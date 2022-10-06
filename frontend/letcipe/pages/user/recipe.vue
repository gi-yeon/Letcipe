<template>
  <div id="app">
    <v-app id="inspire">
      <div class="myrecipe-page">
        <v-container class="myrecipe-container d-flex-row">
          <div class="myrecipe-head-wrap">
            <div class="d-flex justify-space-between pb-3">
              <v-icon @click="moveMypage">mdi-window-close</v-icon>
              <div style="font-size: x-large">내가 만든 레시피</div>
              <v-icon>mdi-blank</v-icon>
            </div>
          </div>
          <v-divider></v-divider>

          <v-card-subtitle>내가 만든 레시피</v-card-subtitle>
          <!-- <div :page="currentPage" :items="myRecipes" :items-per-page="perPage" class="text-center"> -->
          <div>
            <div v-for="(mr, i) in myRecipe" :key="i">
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
                    <div>
                      <v-icon
                        style="z-index: 1"
                        small
                        color="info"
                        @click="editItem(mr)"
                        >mdi-pencil</v-icon
                      >
                      <v-icon style="z-index: 1" small @click="openDialog(mr)"
                        >mdi-delete</v-icon
                      >
                    </div>
                  </v-list-item-title>

                  <v-list-item-subtitle
                    class="recipe-item"
                    @click="moveDetail(mr)"
                    >{{ mr.content }}</v-list-item-subtitle
                  >
                  <div class="d-flex justify-space-between">
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

          <v-dialog v-model="dialog" persistent max-width="290">
            <v-card>
              <v-card-title class="text-h5">Caution</v-card-title>
              <v-card-text>{{ selectedRecipe.title }}</v-card-text>
              <v-card-text>해당 레시피를 삭제하시겠습니까?</v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="green darken-1" text @click="dialog = false"
                  >취소</v-btn
                >
                <v-btn
                  color="green darken-1"
                  text
                  @click="deleteItem(selectedRecipe.id)"
                  >삭제</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-dialog>
          <v-pagination
            v-model="currentPage"
            color="letcipe"
            :length="myRecipes.length"
            :per-page="perPage"
            :total-visivle="5"
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
  name: 'MyrecipePage',
  data() {
    return {
      pageable: {
        page: 0,
      },
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
      myRecipes: [],
      dialog: false,
      selectedRecipe: {},
    }
  },
  computed: {
    ...mapState('user', ['myRecipe']),
  },

  watch: {},

  created() {
    const promise = new Promise((resolve, reject) => {
      resolve()
    })
    promise.then(async () => {
      await this.myrecipe(this.pageable)
      this.myrecipe = this.myRecipe
      //   this.myRecipe.forEach((mr) => {
      //     this.myRecipes.push(mr)
      //   })
      //   this.TotalPage = this.myRecipe.length / 5
      //   console.log(this.TotalPage)
      //   console.log(this.myRecipes)
    })
  },
  methods: {
    ...mapActions('recipe', ['patchRecipeDetail']),
    ...mapActions('user', ['myrecipe']),
    ...mapActions('cart', ['createCart']),
    ...mapMutations('recipe', ['SET_RECIPE_ID', 'CLEAR_RECIPE_ID']),
    editItem(mr) {
      this.CLEAR_RECIPE_ID()
      this.SET_RECIPE_ID(mr.id)
      this.$router.push('/recipe/modify')
    },
    async deleteItem(id) {
      await this.patchRecipeDetail(id)
      await this.myrecipe(this.pageable)
      this.dialog = false
    },
    moveDetail(mr) {
      this.CLEAR_RECIPE_ID()
      this.SET_RECIPE_ID(mr.id)
      this.$router.push('/recipe/detail')
    },
    moveMypage() {
      this.$router.push('/user/mypage')
    },
    // addCart(mr) {
    //   const cartItem = [mr.id]
    //   const list = { cartItem }
    //   this.createCart(list)
    // },
    openDialog(mr) {
      this.dialog = true
      this.selectedRecipe = mr
    },
    addCart(mr) {
      const recipeList = []
      recipeList.push(mr.id)
      console.log(mr.id)
      const addrecipes = {
        list: recipeList,
      }
      this.createCart(addrecipes)
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
