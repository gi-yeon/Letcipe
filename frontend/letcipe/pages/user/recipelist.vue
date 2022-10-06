<template>
  <div id="app">
    <v-app id="inspire">
      <div class="myrecipe-page">
        <v-container class="myrecipe-container d-flex-row">
          <div class="myrecipe-head-wrap">
            <div class="d-flex justify-space-between pb-3">
              <v-icon @click="moveMypage">mdi-window-close</v-icon>
              <div style="font-size: x-large">내가 만든 레시피리스트</div>
              <v-icon>mdi-blank</v-icon>
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
                      {{ mr.name }}
                    </div>
                    <div class="d-flex-column">
                      <div class="d-flex align-center">
                        <v-icon small color="letcipe"
                          >mdi-calendar-clock</v-icon
                        >
                        <div style="font-size: x-small">{{ mr.regTime }}</div>
                      </div>
                      <div class="d-flex justify-end">
                        <v-icon
                          v-if="mr.isShared === 'N'"
                          style="z-index: 1"
                          small
                          @click="modifyStatus(mr)"
                          >mdi-lock</v-icon
                        >
                        <v-icon
                          v-if="mr.isShared === 'Y'"
                          style="z-index: 1"
                          small
                          color="letcipe"
                          @click="modifyStatus(mr)"
                          >mdi-lock-open</v-icon
                        >
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
                    </div>
                  </v-list-item-title>

                  <v-list-item-subtitle
                    class="recipe-item"
                    @click="moveDetail(mr)"
                    >{{ mr.description }}</v-list-item-subtitle
                  >
                  <div class="d-flex justify-space-between">
                    <v-list-item-subtitle class="d-flex align-center">
                      <!-- <div class="d-flex align-center">
                          <v-icon small color="pink lighten-1">mdi-cards-heart</v-icon>
                          <div>{{mr.recipeLike }}</div>
                        </div>-->

                      <div>
                        <div class="d-flex align-center">
                          <v-icon
                            v-if="mr.bookmark"
                            small
                            :color="mr.bookmark ? `yellow lighten-1` : `gray`"
                            style="cursor: pointer"
                            @click="saveBookmark(mr)"
                            >mdi-bookmark</v-icon
                          >
                          <v-icon
                            v-else
                            small
                            :color="mr.bookmark ? `yellow lighten-1` : `gray`"
                            style="cursor: pointer"
                            @click="saveBookmark(mr)"
                            >mdi-bookmark-outline</v-icon
                          >
                          <div>{{ mr.bookmarkCnt }}</div>
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
                >레시피 리스트를 만들어주세요.</v-list-item
              >
              <v-divider></v-divider>
            </div>
          </div>

          <v-dialog v-model="dialog" persistent max-width="290">
            <v-card>
              <v-card-title class="text-h5">Caution</v-card-title>
              <v-card-text>해당 레시피리스트를 삭제하시겠습니까?</v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="green darken-1" text @click="dialog = false"
                  >취소</v-btn
                >
                <v-btn
                  color="green darken-1"
                  text
                  @click="deleteItem(selectedRecipeList.id)"
                  >삭제</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-dialog>

          <v-pagination
            v-model="currentPage"
            color="letcipe"
            :length="TotalPage"
            :per-page="perPage"
            :total-visivle="TotalPage"
            circle
          ></v-pagination>
        </v-container>

        <v-snackbar
          v-model="snackbar2"
          :timeout="timeout"
        >
          {{ isSucceededtoCart? "모두 담기에 성공했습니다":"모두 담기에 실패했습니다" }}
        </v-snackbar>

      </div>
    </v-app>
  </div>
</template>

<script>
import { mapActions, mapState, mapMutations } from 'vuex'
export default {
  name: 'MyrecipeListPage',
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
      dialog: false,
      selectedRecipe: {},
      timeout: 2000,
      snackbar2: false,
    }
  },
  computed: {
    ...mapState('user', ['myRecipe', 'myRecipeList']),
    ...mapState('cart', ['isSucceededtoCart']),
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
      await this.myrecipeList(pageable)
      this.myRecipeList?.forEach((mr) => {
        console.log(mr.recipeListItems)
        const repImg =  mr.recipeListItems[0]? mr.recipeListItems[0].recipe.repImg:''
        const content = mr.recipeListItems[0]? mr.recipeListItems[0].recipe.title:''
        const recipeListItem = {
          id: mr.id,
          bookmark: mr.bookmark,
          name: mr.name,
          isShared: mr.isShared,
          bookmarkCnt: mr.recipeListBookmark,
          repImg,
          description: mr.description,
          content,
          regTime: mr.regTime.split('T')[0],
          review: mr.review,
          cnt: 0,
          items: mr.recipeListItems,
        }
        mr.recipeListItems.forEach((m) => {
          recipeListItem.cnt += m.amount
        })
        this.recipeList.push(recipeListItem)
      })
      //   this.TotalPage = this.myRecipe.length / 5
      //   console.log(this.TotalPage)
      //   console.log(this.myRecipes)
    })
  },
  methods: {
    ...mapActions('recipe', ['patchRecipeDetail']),
    ...mapActions('user', ['myrecipe', 'myrecipeList']),
    ...mapActions('cart', ['createCart']),
    ...mapMutations('recipelist', ['SET_RECIPE_ID', 'CLEAR_RECIPE_ID']),
    ...mapActions('recipelist', [
      'deleteRecipeList',
      'deleteRecipeListBookmark',
      'createRecipeListBookmark',
      'updateRecipeList',
    ]),
    editItem(mr) {
      this.CLEAR_RECIPE_ID()
      this.SET_RECIPE_ID(mr.id)
      this.$router.push('/recipelist/modify')
    },
    async deleteItem(id) {
      await this.deleteRecipeList(id)
      await this.myrecipeList(this.pageable)
      this.dialog = false
    },
    moveDetail(mr) {
      this.CLEAR_RECIPE_ID()
      this.SET_RECIPE_ID(mr.id)
      this.$router.push('/recipelist/detail')
    },
    moveMypage() {
      this.$router.push('/user/mypage')
    },
    addCart(mr) {
      //  이부분 물어보기 지수한테.
      const recipeList = []
      mr.items.forEach((i) => {
        const id = i.recipe.id
        recipeList.push(id)
      })
      const addrecipes = {
        list: recipeList,
      }
      this.createCart(addrecipes)
      this.snackbar2 = true
    },
    openDialog(mr) {
      this.dialog = true
      this.selectedRecipeList = mr
    },
    saveBookmark(mr) {
      if (mr.bookmark) {
        mr.bookmark = false
        this.deleteRecipeListBookmark(mr.id)
        mr.bookmarkCnt--
      } else {
        mr.bookmark = true
        this.createRecipeListBookmark(mr.id)
        mr.bookmarkCnt++
      }
    },
    modifyStatus(mr) {
      console.log(mr)

      const object = {
        id: mr.id,
        ReqUpdateRecipeListDto: {
          name: mr.name,
          description: mr.description,
          isShared: mr.isShared,
        },
      }
      console.log(object)
      this.updateRecipeList(object)
      if (mr.isShared === 'Y') {
        mr.isShared = 'N'
      } else {
        mr.isShared = 'Y'
      }
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

@media (max-width: 415px) {
  .recipe-item {
    width: 85px;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}
</style>
