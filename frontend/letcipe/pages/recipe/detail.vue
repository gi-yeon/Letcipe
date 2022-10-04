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
                    {{
                    recipeDetail.title
                    }}
                  </v-card-title>

                  <!-- 레시피도 서브타이틀 넣을지? 현재 erd에 작성 안되어있음 -->
                  <!-- <v-card-subtitle class="text-md-h3 ref-subtitle"
                    >맛있겠다!</v-card-subtitle
                  >-->
                </div>
              </v-img>
            </div>

            <v-card-title class="text-md-h3">{{ recipeDetail.title }}</v-card-title>
            <v-card-subtitle class="text-md-h5">맛있겠다!</v-card-subtitle>

            <v-card-text>
              <v-row align="center" class="d-flex mx-0">
                <v-icon v-if="isLike" small color="pink lighten-1" @click="saveLike">mdi-heart</v-icon>
                <v-icon v-else small color="grey" @click="saveLike">mdi-heart-outline</v-icon>
                &nbsp;{{ Likes }}&nbsp;&nbsp;
                <v-icon
                  v-if="isBookmark"
                  small
                  color="yellow lighten-1"
                  @click="saveBookmark"
                >mdi-bookmark</v-icon>
                <v-icon v-else small color="grey" @click="saveBookmark">mdi-bookmark-outline</v-icon>
                &nbsp;{{ Bookmarks }}
              </v-row>

              <v-row align="center" class="mx-0">등록일자 : {{ regTime }}</v-row>
              <div class="my-4 text-subtitle-1">
                <!-- <v-avatar
                  v-if="profileImg !== null || profileImg !== ''"
                  size="36px"
                >
                  <img alt="Avatar" :src="profileImg" />
                </v-avatar>-->
                <v-avatar size="27px" color="letcipe">
                  <v-img v-if="writer.profileImage" :src="writer.profileImage"></v-img>
                  <v-icon v-else dark>mdi-account-circle</v-icon>
                </v-avatar>
                <span style="color: #ffa500">{{ writer.job }}</span>
                &nbsp;&nbsp;{{ writer.nickname }}
              </div>
            </v-card-text>

            <v-divider class="mx-4"></v-divider>

            <div>
              <v-card-text>{{ recipeDetail.content }}</v-card-text>
              <v-card-title class="d-flex text-md-h4">
                <div>재료</div>
                <div class="ml-3" style="font-size: small; color: #ffa500">
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
              <div v-for="(stepInfo, i) in recipeSteps" :key="i" style="width: 80%; margin: auto">
                <div class="stepDetail">
                  <v-img :src="stepInfo.img"></v-img>
                  <h2
                    style="display: inline; color: #ffa500; font-size: xx-large"
                  >{{ stepInfo.step }}</h2>
                  {{ stepInfo.content }}
                </div>
              </div>
              <v-divider class="mx-4"></v-divider>
              <v-divider class="mx-4"></v-divider>
              <v-divider class="mx-4"></v-divider>
            </div>

            <v-card-text>
              <v-chip-group column>
                <v-chip v-for="(tag, i) in recipeDetail.tags" :key="i">{{ tag.name }}</v-chip>
              </v-chip-group>
            </v-card-text>

            <div align="center">
              <v-row class="d-flex justify-center">
                <v-btn color="letcipe" @click="addCart">+ 담기</v-btn>
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
                  <div v-for="(comment, i) in comments" :key="i" style="width: 80%">
                    <div class="mx-auto pt-2 pb-2 d-flex align-center">
                      <v-list-item
                        three-line
                        style="border: 1px solid black !important"
                        align="left"
                      >
                        <v-list-item-content>
                          <v-row>
                            <v-col>
                              <v-list-item-subtitle class="recipe-comment">{{ comment.nickName }}</v-list-item-subtitle>
                            </v-col>
                            <v-col align="right">
                              <v-list-item-subtitle class="recipe-comment">
                                {{ comment.regTime.split('T')[0]
                                }}
                                <v-icon
                                  v-if="comment.nickName === nickname"
                                  size="small"
                                  @click="deleteComment(comment.id)"
                                >mdi-close</v-icon>
                              </v-list-item-subtitle>
                            </v-col>
                          </v-row>

                          <v-list-item-content class="recipe-comment">
                            {{
                            comment.content
                            }}
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
import { mapActions, mapState } from 'vuex'
export default {
  name: 'RecipeDetail',
  data() {
    return {
      recipeInfo: {},
      TotalPage: 1,
      currentPage: 1,
      enterComment: null,
      writer: {},
      content: '',
      regTime: '',
      Likes: 0,
      isLike: false,
      Bookmarks: 0,
      isBookmark: false,
      recipeSteps: [],
      recipeIngredient: [],
      ingredient: [],
    }
  },
  computed: {
    ...mapState('comment', ['comments', 'commentNum']),
    ...mapState('recipe', ['recipeDetail', 'recipeID']),
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
      this.recipeSteps = []
      await this.RecipeDetail(this.recipeID)
      console.log(this.recipeDetail.user.nickname)
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
      console.log('코멘트 넘버요' + this.commentNum)
      this.TotalPage = Math.ceil(this.commentNum / 5)
      console.log('토탈' + this.TotalPage)
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
    ]),
    ...mapActions('cart', ['createCart']),
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
      if (this.userId === 0) return
      if (this.isBookmark) {
        this.deleteBookmarks(this.recipeDetail.id)
        this.Bookmarks--
      } else {
        this.selectBookmarks(this.recipeDetail.id)
        this.Bookmarks++
      }
      this.isBookmark = !this.isBookmark
    },
    saveLike() {
      if (this.userId === 0) return
      if (this.isLike) {
        this.decountRecipeLikes(this.recipeDetail.id)
        this.Likes--
      } else {
        this.countRecipeLikes(this.recipeDetail.id)
        this.Likes++
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
      console.log(this.enterComment)
      const comment = {
        content: this.enterComment,
        boardId: this.recipeDetail.id,
        boardType: 'RECIPE',
      }
      await this.postComment(comment)
      this.enterComment = null
      this.reloadComment()
    },
    async deleteComment(id) {
      const comment = {
        commentId: id,
      }
      await this.patchComment(comment)
      this.reloadComment()
    },
    async reloadComment() {
      await this.getCommentNum(this.recipeInfo)
      await this.getComment(this.recipeInfo)
    },
    addCart() {
      const recipeList = []
      recipeList.push(this.recipeID)
      console.log(this.recipeID)
      const addrecipes = {
        list: recipeList,
      }
      this.createCart(addrecipes)
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
