<template>
  <div id="app">
    <v-app id="inspire">
      <div id="recipedetail-modify-container">
        <v-container style="width: 100%">
          <div class="detail-head-wrap">
            <div class="d-flex justify-space-between pb-3">
              <div>
                <v-icon @click="moveBack">mdi-window-close</v-icon>
              </div>
              <div style="font-size: x-large">레시피리스트 수정</div>
              <div>
                <v-icon>mdi-blank</v-icon>
              </div>
            </div>
          </div>
          <v-card class="mx-auto my-12">
            <v-img :src="recipeListRepImg"></v-img>

            <v-card-title class="text-md-h3" style="text-overflow: ellipsis">
              <div class="d-flex align-center">
                <div>
                  {{ recipeListRes.name }}
                </div>

                <v-dialog v-model="dialogTitle" persistent max-width="290">
                  <template #activator="{ on, attrs }">
                    <v-icon
                      style="z-index: 2"
                      color="info"
                      v-bind="attrs"
                      v-on="on"
                      >mdi-pencil</v-icon
                    >
                  </template>

                  <v-card>
                    <v-card-title style="font-size: xx-large"
                      >제목 수정<v-icon color="letcipe"
                        >mdi-pencil</v-icon
                      ></v-card-title
                    >
                    <v-card-text class="text--primary">
                      <v-text-field
                        v-model="newName"
                        name="input-7-4"
                        placeholder="내용을 입력해주세요"
                        solo
                      ></v-text-field>
                    </v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn
                        color="letcipe"
                        text
                        @click="
                          ;[(dialogTitle = false), modifyItem(recipeListRes)]
                        "
                        >확인</v-btn
                      >
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </div>
            </v-card-title>

            <v-card-text>
              <div class="my-4 text-subtitle-1">
                <v-row class="mx-0 d-flex justify-space-between">
                  {{ recipeListWriter.job }} &nbsp;&nbsp;{{
                    recipeListWriter.nickname
                  }}
                  <v-dialog v-model="dialog2" persistent max-width="290">
                    <template #activator="{ on, attrs }">
                      <v-btn
                        style="border: 1px solid black"
                        v-bind="attrs"
                        @click="deleteList()"
                        v-on="on"
                        >삭제</v-btn
                      >
                    </template>
                    <v-card>
                      <v-card-title class="text-h5">Caution</v-card-title>
                      <v-card-text>레시피 리스트가 삭제됩니다.</v-card-text>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn
                          color="green darken-1"
                          text
                          @click="dialog2 = false"
                          >확인</v-btn
                        >
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                </v-row>
              </div>
              <v-row align="center" class="mx-0">
                {{ recipeListRes.description }}
              </v-row>
              <v-row align="center" class="mx-0">
                <v-icon
                  v-if="isBookmark"
                  small
                  color="letcipe"
                  @click="bookmark"
                  >mdi-bookmark</v-icon
                >
                <v-icon v-else small color="letcipe" @click="bookmark"
                  >mdi-bookmark-outline</v-icon
                >
                &nbsp;{{ Bookmarks }}&nbsp;&nbsp;
              </v-row>
              <v-row align="center" class="mx-0"
                >등록일자 : {{ regTime }}</v-row
              >
            </v-card-text>
            <br />
            <div align="center" class="d-flex justify-center">
              <!-- <v-btn style="width: 90%; border: 1px solid black" @click="partAdd()">선택 담기</v-btn> -->

              <v-dialog v-model="dialog" persistent max-width="290">
                <template #activator="{ on, attrs }">
                  <v-btn
                    height="48px"
                    style="width: 90%; border: 1px solid black"
                    v-bind="attrs"
                    @click="partDelete()"
                    v-on="on"
                    >선택 삭제</v-btn
                  >
                </template>
                <!-- <v-card v-if="isAll">
                      <v-card-title class="text-h5">Caution</v-card-title>
                      <v-card-text>레시피 전체 담기 성공!</v-card-text>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="green darken-1" text @click="dialog = false">확인</v-btn>
                      </v-card-actions>
                  </v-card>-->
                <v-card v-if="isNothing">
                  <v-card-title class="text-h5">Caution</v-card-title>
                  <v-card-text
                    >선택된 레시피가 없습니다. 레시피를
                    선택해주세요.</v-card-text
                  >
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="green darken-1" text @click="dialog = false"
                      >확인</v-btn
                    >
                  </v-card-actions>
                </v-card>
                <v-card v-if="!isNothing">
                  <v-card-title class="text-h5">Caution</v-card-title>
                  <v-card-text>레시피 장바구니에 담기 성공!</v-card-text>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="green darken-1" text @click="dialog = false"
                      >확인</v-btn
                    >
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </div>
            <v-card-subtitle
              class="mx-0 mr-3 ml-3 d-flex justify-space-between"
            >
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
                      class="d-flex align-center mx-auto mt-2 mb-2"
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
                            {{ item.recipe.title }}
                          </v-list-item-title>
                          <v-list-item-subtitle>{{
                            item.recipe.content
                          }}</v-list-item-subtitle>
                        </v-list-item-content>
                      </v-list-item>

                      <v-icon
                        v-if="checkedRecipe[i]"
                        class="mr-3"
                        @click="addCart(i)"
                        >mdi-check-circle</v-icon
                      >
                      <v-icon v-else class="mr-3" @click="addCart(i)"
                        >mdi-check-circle-outline</v-icon
                      >
                    </div>
                  </template>
                </v-hover>
              </v-col>
            </v-row>
            <div class="d-flex justify-center pt-2 pb-3">
              <v-btn
                class="mr-3"
                style="border: 1px solid black"
                @click="moveBack"
              >
                취소
              </v-btn>
              <!-- <v-btn style="border: 1px solid black" @click="modifyItem(recipeListRes)">
                저장
              </v-btn> -->
              <v-dialog v-model="modifyDialog" persistent max-width="290">
                <template #activator="{ on, attrs }">
                  <v-btn
                    class="ml-3"
                    style="border: 1px solid black"
                    v-bind="attrs"
                    v-on="on"
                    >저장</v-btn
                  >
                </template>

                <v-card>
                  <v-card-title class="text-h5">Caution</v-card-title>
                  <v-card-text>변경사항이 저장됩니다.</v-card-text>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                      color="green darken-1"
                      text
                      @click="
                        ;[(modifyDialog = false), modifyItem(recipeListRes)]
                      "
                      >확인</v-btn
                    >
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </div>
          </v-card>
        </v-container>
      </div>
    </v-app>
  </div>
</template>

<script>
import { mapActions, mapMutations, mapState } from 'vuex'

export default {
  name: 'RecipeListModify',
  data() {
    return {
      dialog: false,
      dialog2: false,
      recipeListWriter: {},
      regTime: '',
      Bookmarks: 0,
      isBookmark: false,
      checkedRecipe: [],
      cart: [],
      isAllCheck: false,
      id: '',
      isNothing: false,
      dialogTitle: false,
      newName: null,
      modifyDialog: false,
    }
  },

  async fetch() {
    await this.getRecipeList(this.recipeListId)
    console.log(this.recipeListId)
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
    ...mapState('user', ['userId', 'myRecipe', 'myRecipeList']),
  },

  methods: {
    ...mapActions('recipelist', [
      'getRecipeList',
      'createRecipeListBookmark',
      'deleteRecipeListBookmark',
      'deleteRecipeList',
      'updateRecipeList',
    ]),
    ...mapActions('cart', ['createCart']),
    ...mapMutations('recipelist', [
      'SET_RECIPELIST_NAME',
      'SET_RECIPE_ID',
      'CLEAR_RECIPE_ID',
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
    async deleteList() {
      await this.deleteRecipeList(this.recipeListId).then(
        this.$router.push('/user/mypage')
      )
    },
    partDelete() {
      this.deleteRecipeListItem()
    },
    modifyItem(recipeListRes) {
      if (this.newName !== null) {
        this.SET_RECIPELIST_NAME(this.newName)
      }

      const object = {
        recipeListId: this.recipeListId,
        ReqUpdateRecipeListDto: {
          name: recipeListRes.name,
          description: recipeListRes.description,
          isShared: recipeListRes.isShared,
        },
      }
      this.updateRecipeList(object)
      this.CLEAR_RECIPE_ID()
      this.SET_RECIPE_ID(this.recipeListId)
      this.$router.push('/recipelist/modify')
    },
  },
}
</script>

<style scoped>
.fadeInUp {
  animation: fadeInUp 1s ease backwards;
}
@keyframes fadeInUp {
  0% {
    transform: translate(0px, 100px);
    opacity: 0;
  }
  100% {
    transform: translate(0px, 0);
    opacity: 1;
  }
}
</style>
