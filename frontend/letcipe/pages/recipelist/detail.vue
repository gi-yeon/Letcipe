<template>
  <div id="app">
    <v-app id="inspire">
      <div id="recipedetail-container">
        <v-container style="width: 100%">
          <div class="detail-head-wrap">
            <div class="d-flex justify-space-between pb-3">
              <div>
                <v-icon @click="moveBack">mdi-window-close</v-icon>
              </div>
              <div style="font-size: x-large">레시피리스트</div>
              <div>
                <v-icon>mdi-blank</v-icon>
              </div>
            </div>
          </div>
          <v-card class="mx-auto my-12">
            <v-img :src="recipeListRepImg"></v-img>

            <v-card-title class="text-md-h3" style="text-overflow: ellipsis">
              {{
              recipeListRes.name
              }}
            </v-card-title>

            <v-card-text>
              <div class="my-4 text-subtitle-1">
                <v-row class="mx-0 d-flex justify-space-between">
                  {{ recipeListWriter.job }} &nbsp;&nbsp;{{
                  recipeListWriter.nickname
                  }}
                  <v-btn style="border: 1px solid black">+ 전체담기</v-btn>
                </v-row>
              </div>
              <v-row align="center" class="mx-0">
                {{
                recipeListRes.description
                }}
              </v-row>
              <v-row align="center" class="mx-0">
                <v-icon v-if="isBookmark" small color="letcipe" @click="bookmark">mdi-bookmark</v-icon>
                <v-icon v-else small color="letcipe" @click="bookmark">mdi-bookmark-outline</v-icon>
                &nbsp;{{ Bookmarks }}&nbsp;&nbsp;
              </v-row>
              <v-row align="center" class="mx-0">등록일자 : {{ regTime }}</v-row>
            </v-card-text>
            <br />
            <div align="center">
              <v-btn style="width: 90%; border: 1px solid black">선택 담기</v-btn>
            </div>
            <v-card-subtitle class="mx-0 mr-3 ml-3 d-flex justify-space-between">
              <div v-if="!isAllCheck" @click="allAddCart">
                <v-icon>mdi-check</v-icon>전체선택
              </div>
              <div v-else @click="allAddCart">
                <v-icon color="black">mdi-check-bold</v-icon>전체선택
              </div>
              <div>{{ recipeListItems.length }}가지 요리</div>
            </v-card-subtitle>

            <v-row>
              <v-col>
                <v-hover
                  v-for="(item, i) in recipeListRes.recipeListItems"
                  :key="i"
                  style="width: 90%"
                >
                  <template #default="{ hover }">
                    <div
                      style="
                        border: 1px solid black !important;
                        border-radius: 4px;
                        cursor: pointer;
                      "
                      :elevation="hover ? 24 : 6"
                      :class="hover ? 'grey lighten-5' : 'white'"
                      class="mx-auto mt-2 mb-2 d-flex align-center"
                    >
                      <div class="ml-4">{{ i + 1 }}</div>
                      <v-list-item three-line>
                        <v-list-item-avatar tile size="57">
                          <v-img
                            elevation="10"
                            :src="item.recipe.repImg"
                            style="border-radius: 5px"
                          ></v-img>
                        </v-list-item-avatar>
                        <v-list-item-content>
                          <v-list-item-title class="mb-1">
                            {{
                            item.recipe.title
                            }}
                          </v-list-item-title>
                          <v-list-item-subtitle>{{ item.recipe.content }}</v-list-item-subtitle>
                        </v-list-item-content>
                      </v-list-item>

                      <v-icon
                        v-if="checkedRecipe[i]"
                        class="mr-3"
                        @click="addCart(i)"
                      >mdi-check-circle</v-icon>
                      <v-icon v-else class="mr-3" @click="addCart(i)">mdi-check-circle-outline</v-icon>
                    </div>
                  </template>
                </v-hover>
              </v-col>
            </v-row>
          </v-card>
        </v-container>
      </div>
    </v-app>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'

export default {
  name: 'RecipeListDetail',
  data() {
    return {
      recipeListWriter: {},
      regTime: '',
      Bookmarks: 0,
      isBookmark: false,
      checkedRecipe: [],
      cart: [],
      isAllCheck: false,
      id: '',
    }
  },

  async fetch() {
    await this.getRecipeList(1)
    this.recipeListWriter = this.recipeListUser
    const temp = this.recipeListRes.regTime
    this.regTime = temp.replace('T', '  ')
    this.Bookmarks = this.recipeListRes.recipeListBookmark
    this.isBookmark = this.recipeListRes.bookmark
    this.initCart()
  },

  fetchOnServer: false,
  computed: {
    ...mapState('recipelist', [
      'recipeListRes',
      'recipeListUser',
      'recipeListItems',
      'recipeListRepImg',
      'recipeListId',
    ]),
    ...mapState('user', ['userId']),
  },
  created() {},
  methods: {
    ...mapActions('recipelist', [
      'getRecipeList',
      'createRecipeListBookmark',
      'deleteRecipeListBookmark',
    ]),
    moveBack() {
      this.$router.go(-1)
    },
    bookmark() {
      if (this.userId === 0) return
      if (this.isBookmark) {
        this.isBookmark = !this.isBookmark
        this.deleteRecipeListBookmark(this.recipeListRes.id)
        this.Bookmarks--
      } else {
        this.isBookmark = !this.isBookmark
        this.createRecipeListBookmark(this.recipeListRes.id)
        this.Bookmarks++
      }
    },
    initCart() {
      for (let i = 0; i < this.recipeListItems.length; i++) {
        this.checkedRecipe.push(false)
      }
    },
    addCart(index) {
      this.cart = []
      if (this.isAllCheck) {
        this.isAllCheck = false
      }
      this.checkedRecipe[index] = !this.checkedRecipe[index]
      // this.cart.push(this.recipeListItems[index])
      // this.cart.splice(index, 1)
      for (let i = 0; i < this.checkedRecipe.length; i++) {
        if (this.checkedRecipe[i]) {
          this.cart.push(this.recipeListItems[i])
        }
      }
      console.log(this.checkedRecipe)
      console.log(this.cart)
    },
    allAddCart() {
      if (!this.isAllCheck) {
        this.isAllCheck = true
        this.cart = []
        for (let i = 0; i < this.recipeListItems.length; i++) {
          this.cart.push(this.recipeListItems[i])
        }
        for (let i = 0; i < this.checkedRecipe.length; i++) {
          this.checkedRecipe[i] = true
        }
      } else {
        this.isAllCheck = false
        this.cart = []
        for (let i = 0; i < this.checkedRecipe.length; i++) {
          this.checkedRecipe[i] = false
        }
      }
      console.log(this.cart)
    },
  },
}
</script>

<style></style>
