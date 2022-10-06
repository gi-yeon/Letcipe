<template>
  <div id="app">
    <v-app id="inspire">
      <div class="myrecipe-page">
        <v-container class="myrecipe-container d-flex-row">
          <div class="myrecipe-head-wrap">
            <div class="d-flex justify-space-between pb-3">
              <v-icon @click="moveMypage">mdi-window-close</v-icon>
              <div style="font-size: x-large">즐겨찾는 레시피리스트</div>
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
                          style="z-index: 2"
                          small
                          @click="modifyStatus(mr)"
                          >mdi-lock</v-icon
                        >
                        <v-icon
                          v-if="mr.isShared === 'Y'"
                          style="z-index: 2"
                          small
                          color="letcipe"
                          @click="modifyStatus(mr)"
                          >mdi-lock-open</v-icon
                        >
                        <v-icon
                          style="z-index: 2"
                          small
                          color="info"
                          @click="editItem(mr)"
                          >mdi-pencil</v-icon
                        >
                        <v-icon style="z-index: 2" small @click="deleteItem(mr)"
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
                            small
                            :color="mr.bookmark ? `yellow lighten-1` : `gray`"
                            style="cursor: pointer"
                            @click="createBookmark(mr)"
                            >mdi-bookmark</v-icon
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
                >즐겨찾기에 추가된 레시피 리스트가 없습니다.</v-list-item
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
    </v-app>
  </div>
</template>

<script>
import { mapActions, mapState, mapMutations } from 'vuex'
export default {
  name: 'MyrecipeListBookmark',
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
    ...mapState('user', ['userId', 'nickname', 'myBookMarkRecipeList']),
  },

  watch: {},

  created() {
    const pageable = {
      page: 0,
      size: 5,
    }
    const promise = new Promise((resolve, reject) => {
      resolve()
    })
    promise.then(async () => {
      await this.myBookmarkRecipeList(pageable)
      this.myBookMarkRecipeList?.forEach((mr) => {
        const recipeListItem = {
          id: mr.id,
          bookmark: mr.bookmark,
          name: mr.name,
          isShared: mr.isShared,
          bookmarkCnt: mr.recipeListBookmark,
          repImg: mr.recipeListItems[0].recipe.repImg,
          description: mr.description,
          content: mr.recipeListItems[0].recipe.title + ' 외',
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

      console.log(this.recipeList)
      console.log(this.recipeList.length)
    })
  },
  methods: {
    ...mapActions('user', ['myBookmarkRecipeList']),
    ...mapActions('cart', ['createCart']),
    ...mapActions('recipelist', ['updateRecipeList', 'deleteRecipeList']),
    ...mapMutations('recipelist', ['SET_RECIPE_ID', 'CLEAR_RECIPE_ID']),
    editItem(mr) {
      this.CLEAR_RECIPE_ID()
      this.SET_RECIPE_ID(mr.id)
      this.$router.push('/recipelist/modify')
    },
    deleteItem(mr) {
      //     this.checkedList.splice(index, 1)
      //   this.checklist.push(c)
      this.deleteRecipeList(mr.id)
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
