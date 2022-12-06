<template>
  <div id="app">
    <v-app id="inspire">
      <div id="recipedetail-container">
        <v-container>
          <div class="detail-head-wrap">
            <div class="d-flex justify-space-between pb-3">
              <div>
                <v-icon @click="moveBack">mdi-window-close</v-icon>
              </div>
              <div style="font-size: x-large">레시피</div>
              <div>
                <v-icon>mdi-blank</v-icon>
              </div>
            </div>
          </div>

          <v-card class="mx-auto my-5">
            <div class="recipe-img-wrap" align="center">
              <v-img class="recipe-imgs" :src="recipeDetail.repImg">
                <div class="ref-wrap">
                  <v-card-title class="text-h6 ref-title">
                    {{ recipeDetail.title }}
                  </v-card-title>

                  <!-- 레시피도 서브타이틀 넣을지? 현재 erd에 작성 안되어있음 -->
                  <!-- <v-card-subtitle class="text-md-h3 ref-subtitle"
                    >맛있겠다!</v-card-subtitle
                  >-->
                </div>
              </v-img>
            </div>
            <v-card-title class="d-flex justify-space-between text-md-h3">
              <div>
                {{ recipeDetail.title }}
              </div>

              <v-btn
                :disabled="userId <= 0"
                color="letcipe"
                style="color: white"
                @click="addCart"
                >+ 담기</v-btn
              >
              <v-snackbar
                v-model="addSnackBar"
                centered
                style="z-index: 1"
                :timeout="1500"
              >
                {{ snackBarMsg }}
              </v-snackbar>
            </v-card-title>
            <v-card-subtitle class="text-md-h5">맛있겠다!</v-card-subtitle>

            <v-card-text>
              <v-row align="center" class="d-flex mx-0">
                <v-icon
                  v-if="isLike"
                  small
                  color="pink lighten-1"
                  @click="saveLike"
                  >mdi-heart</v-icon
                >
                <v-icon v-else small color="grey" @click="saveLike"
                  >mdi-heart-outline</v-icon
                >
                &nbsp;{{ Likes }}&nbsp;&nbsp;
                <v-icon
                  v-if="isBookmark"
                  small
                  color="yellow lighten-1"
                  @click="saveBookmark"
                  >mdi-bookmark</v-icon
                >
                <v-icon v-else small color="grey" @click="saveBookmark"
                  >mdi-bookmark-outline</v-icon
                >
                &nbsp;{{ Bookmarks }}
              </v-row>

              <v-row align="center" class="mx-0"
                >등록일자 : {{ regTime }}</v-row
              >
              <v-row class="d-flex justify-space-between">
                <div class="my-4 pl-4">
                  <v-avatar size="27px" color="letcipe">
                    <v-img
                      v-if="writer.profileImage"
                      :src="writer.profileImage"
                    ></v-img>
                    <v-icon v-else dark>mdi-account-circle</v-icon>
                  </v-avatar>

                  <span style="color: #ffa500">{{ writer.job }}</span>
                  &nbsp;&nbsp;{{ writer.nickname }}
                </div>
                <div
                  v-if="writer.nickname === nickname"
                  class="my-4 text-subtitle-1 pr-4"
                >
                  <v-btn small color="letcipe" dark @click="editMyRecipe"
                    >수정</v-btn
                  >
                  <v-btn small color="letcipe" dark @click="deleteMyRecipe"
                    >삭제</v-btn
                  >
                </div>
              </v-row></v-card-text
            >
            <v-divider class="mx-4"></v-divider>

            <div>
              <v-card-text>{{ recipeDetail.content }}</v-card-text>
              <v-card-title class="d-flex text-md-h4">
                <div>재료</div>
                <div
                  class="d-flex ml-3"
                  style="font-size: small; color: #ffa500"
                >
                  <div v-if="recipeDetail.cookingTime != -1">
                    <v-icon small color="letcipe">mdi-timer</v-icon>
                    {{ recipeDetail.cookingTime }}분
                  </div>
                  <div v-if="recipeDetail.serving != 0">
                    <v-icon small color="letcipe">mdi-account</v-icon>
                    {{ recipeDetail.serving }}인분
                  </div>
                </div>
              </v-card-title>
              <v-simple-table>
                <template #default>
                  <thead>
                    <tr>
                      <th class="text-left">재료명</th>
                      <th class="text-left">재료양</th>
                      <th class="text-left">단위</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(item, i) in recipeIngredient" :key="i">
                      <td>{{ item.ingredient.name }}</td>
                      <td>{{ item.amount }}</td>
                      <td>{{ item.ingredient.measure }}</td>
                    </tr>
                  </tbody>
                </template>
              </v-simple-table>
              <v-card-title class="text-md-h4">레시피</v-card-title>
              <div
                v-for="(stepInfo, i) in recipeSteps"
                :key="i"
                style="width: 80%; margin: auto"
              >
                <div class="stepDetail">
                  <v-img :src="stepInfo.img"></v-img>
                  <h2
                    style="display: inline; color: #ffa500; font-size: xx-large"
                  >
                    {{ stepInfo.step }}
                  </h2>
                  {{ stepInfo.content }}
                </div>
              </div>
              <v-divider class="mx-4"></v-divider>
              <v-divider class="mx-4"></v-divider>
              <v-divider class="mx-4"></v-divider>
            </div>

            <v-card-text>
              <v-chip-group column>
                <v-chip v-for="(tag, i) in recipeDetail.tags" :key="i">{{
                  tag.name
                }}</v-chip>
              </v-chip-group>
            </v-card-text>

            <div align="center">
              <v-row class="d-flex justify-center">
                <v-dialog v-model="dialog" persistent max-width="290">
                  <template #activator="{ on, attrs }">
                    <v-btn
                      :disabled="userId <= 0"
                      color="letcipe"
                      height="48px"
                      style="color: white"
                      v-bind="attrs"
                      @click="addCart"
                      v-on="on"
                    >
                      + 담기</v-btn
                    >
                  </template>
                  <v-card>
                    <v-card-title class="text-h5">Caution</v-card-title>
                    <v-card-text>성공적으로 레시피를 담았습니다!</v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn color="green darken-1" text @click="dialog = false"
                        >확인</v-btn
                      >
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-row>
              <v-row align="center">
                <v-col cols="1"></v-col>
                <v-col cols="10" class="pr-0 pl-0">
                  <v-text-field
                    v-model="enterComment"
                    color="letcipe"
                    solo
                    outlined
                    append-icon="mdi-arrow-left-bottom"
                    @click:append="addComment()"
                    @keyup.enter="addComment()"
                  ></v-text-field>
                </v-col>
                <v-col cols="1"></v-col>
              </v-row>
              <v-row>
                <v-col align="center">
                  <div
                    v-for="(comment, i) in comments"
                    :key="i"
                    style="width: 80%"
                  >
                    <div class="mx-auto pt-2 pb-2 d-flex align-center">
                      <v-list-item
                        three-line
                        style="border: 1px solid black !important"
                        align="left"
                      >
                        <v-list-item-content>
                          <v-row>
                            <v-col>
                              <v-list-item-subtitle class="recipe-comment">{{
                                comment.nickName
                              }}</v-list-item-subtitle>
                            </v-col>
                            <v-col align="right">
                              <v-list-item-subtitle class="recipe-comment">
                                {{ comment.regTime.split('T')[0] }}
                                <v-icon
                                  v-if="comment.nickName === nickname"
                                  size="small"
                                  @click="deleteComment(comment.id)"
                                  >mdi-close</v-icon
                                >
                              </v-list-item-subtitle>
                            </v-col>
                          </v-row>

                          <v-list-item-content class="recipe-comment">
                            {{ comment.content }}
                          </v-list-item-content>
                        </v-list-item-content>
                      </v-list-item>
                    </div>
                  </div>
                </v-col>
              </v-row>
              <div class="text-center">
                <v-pagination
                  v-model="currentPage"
                  color="letcipe"
                  :length="TotalPage"
                  prev-icon="mdi-menu-left"
                  next-icon="mdi-menu-right"
                  @input="handlePage"
                ></v-pagination>
              </div>
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
  name: 'RecipeDetail',
  data() {
    return {
      recipeInfo: {},
      TotalPage: 1,
      currentPage: 1,
      enterComment: null,
      addSnackBar: false,
      addCommentSnack: false,
      writer: {},
      content: '',
      regTime: '',
      dialog: false,
      Likes: 0,
      isLike: false,
      Bookmarks: 0,
      isBookmark: false,
      recipeSteps: [],
      recipeIngredient: [],
      ingredient: [],
      snackBarMsg: '',
      barCommentMsg: '',
    }
  },
  computed: {
    ...mapState('comment', ['comments', 'commentNum']),
    ...mapState('recipe', [
      'recipeDetail',
      'recipeID',
      'isSucceededtoRecipeDetail',
    ]),
    ...mapState('user', ['userId', 'nickname']),
  },

  created() {
    const promise = new Promise((resolve, reject) => {
      resolve()
    })
    this.recipeInfo = {
      boardType: 'RECIPE',
      boardId: this.recipeID,
    }
    promise.then(async () => {
      // 레시피 디테일 불러오는 부분
      await this.readUser()
      this.recipeSteps = []
      await this.RecipeDetail(this.recipeID)

      this.recipeSteps = this.recipeDetail.recipeSteps
      this.recipeIngredient = this.recipeDetail.ingredients
      this.writer = this.recipeDetail.user
      const temp = this.recipeDetail.regTime
      this.regTime = temp.split('T')[0]
      this.Likes = this.recipeDetail.recipeLike
      this.Bookmarks = this.recipeDetail.recipeBookmark
      if (this.userId !== 0) {
        this.isLike = this.recipeDetail.like
        this.isBookmark = this.recipeDetail.bookmark
      }

      // 레시피 코멘트 불러오는 부분
      await this.getCommentNum(this.recipeInfo)

      this.TotalPage = Math.ceil(this.commentNum / 5)

      this.recipeInfo = {
        boardType: 'RECIPE',
        boardId: this.recipeDetail.id,
        size: 5,
        page: this.currentPage,
      }
      await this.getComment(this.recipeInfo)
    })
  },
  methods: {
    ...mapMutations('recipe', ['SET_RECIPE_ID', 'CLEAR_RECIPE_ID']),
    ...mapActions('comment', [
      'getComment',
      'postComment',
      'getCommentNum',
      'patchComment',
    ]),
    ...mapActions('recipe', [
      'RecipeDetail',
      'createRecipeDetail',
      'selectBookmarks',
      'deleteBookmarks',
      'countRecipeLikes',
      'decountRecipeLikes',
      'patchRecipeDetail',
    ]),
    ...mapActions('cart', ['createCart']),
    ...mapActions('user', ['readUser']),
    handlePage(page) {
      this.recipeInfo = {
        boardType: 'RECIPE',
        boardId: this.recipeDetail.id,
        size: 5,
        page: this.currentPage,
      }
      const promise = new Promise((resolve, reject) => {
        resolve()
      })
      promise.then(async () => {
        await this.getComment(this.recipeInfo)
      })
    },
    moveBack() {
      this.$router.go(-1)
    },
    saveBookmark() {
      let msg = ''
      if (this.userId === 0) return
      if (this.isBookmark) {
        msg = '북마크 취소!'
        this.deleteBookmarks(this.recipeDetail.id)
        this.Bookmarks--
        this.snackBarMsg = msg
        this.addSnackBar = true
      } else {
        msg = '북마크 추가!'
        this.selectBookmarks(this.recipeDetail.id)
        this.Bookmarks++
        this.snackBarMsg = msg
        this.addSnackBar = true
      }
      this.isBookmark = !this.isBookmark
    },
    async saveLike() {
      let msg = ''
      if (this.userId === 0) return
      if (this.isLike) {
        msg = '좋아요 취소!'
        await this.decountRecipeLikes(this.recipeDetail.id)
        this.Likes--
        this.snackBarMsg = msg
        this.addSnackBar = true
      } else {
        msg = '이 레시피가 좋아요!'
        await this.countRecipeLikes(this.recipeDetail.id)
        this.Likes++
        this.snackBarMsg = msg
        this.addSnackBar = true
      }
      this.isLike = !this.isLike
    },

    async addComment() {
      if (this.userId === 0) {
        console.log('로그인이 필요합니다!')
        return
      }
      if (this.enterComment === null || this.enterComment.split('') === []) {
        console.log('내용을 입력하세요')
        return
      }

      const comment = {
        content: this.enterComment,
        boardId: this.recipeDetail.id,
        boardType: 'RECIPE',
      }
      const msg = '댓글을 작성했습니다.'
      await this.postComment(comment)
      this.enterComment = null
      this.reloadComment()
      this.snackBarMsg = msg
      this.addSnackBar = true
    },
    async deleteComment(id) {
      const comment = {
        commentId: id,
      }
      const msg = '댓글을 삭제했습니다.'
      await this.patchComment(comment)
      this.reloadComment()
      this.snackBarMsg = msg
      this.addSnackBar = true
    },
    async reloadComment() {
      await this.getCommentNum(this.recipeInfo)
      await this.getComment(this.recipeInfo)
    },
    async addCart() {
      const recipeList = []
      recipeList.push(this.recipeID)

      const addrecipes = {
        list: recipeList,
      }

      const msg = '카트에 담겼습니다.'
      await this.createCart(addrecipes)
      this.snackBarMsg = msg
      this.addSnackBar = true
    },
    editMyRecipe() {
      this.CLEAR_RECIPE_ID()
      this.SET_RECIPE_ID(this.recipeDetail.id)

      this.$router.push('/recipe/modify')
    },
    deleteMyRecipe() {
      this.patchRecipeDetail(this.recipeDetail.id)
    },
  },
}
</script>

<style scoped>
.detail-page-head {
  padding: 4%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  /* border: 1px solid gray; */
}
#recipedetail-container {
  position: sticky;
  height: 100%;
  padding: 4%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  color: letcipe;
}
.recipe-imgs {
  width: 50%;
}
.recipe-img-wrap {
  padding-top: 10px;
  padding-bottom: 10px;
}
.stepDetail {
  margin-bottom: 5%;
}
.ref-imgs {
  width: 100%;

  /*  width: 150px;
  height: 150px !important;  */
  cursor: pointer;
}

.ref-wrap {
  width: 100%;
  height: 35%;
  /* width: 150px;
  height: 80px; */
  background-color: rgba(49, 49, 49, 0.422);
}
.ref-title {
  font-family: 'LeeSeoyun' !important;
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
.recipe-comment {
  font-size: 0.8rem;
  margin: 0px 0px;
  padding: 0%;
}
:deep(.v-text-field__details) {
  display: none;
}
/* 글자 크기 조절 방법좀 알려주세요~~ */
@media (min-width: 960px) {
  .v-application .text-md-h1 {
    font-family: 'LeeSeoyun' !important;
    padding: 15px;
  }
  .v-application .text-md-h3 {
    font-family: 'LeeSeoyun' !important;
    padding: 15px;
  }
  .v-application .text-md-h5 {
    font-family: 'LeeSeoyun' !important;
  }
}
@media (min-width: 650px) {
  .v-application .text-md-h1 {
    font-family: 'LeeSeoyun' !important;
    font-size: 280%;
    padding: 15px;
  }
  .v-application .text-md-h3 {
    font-family: 'LeeSeoyun' !important;
    font-size: 180%;
    padding: 15px;
  }
  .v-application .text-md-h5 {
    font-family: 'LeeSeoyun' !important;
    font-size: 180%;
  }
} ;
</style>
