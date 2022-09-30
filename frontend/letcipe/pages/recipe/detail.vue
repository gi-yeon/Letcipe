<template>
  <div id="app"> 
    <v-app id="inspire">
      <div id="recipedetail-container">
        <v-container>
          <div class="detail-head-wrap">
            <div class="d-flex justify-space-between pb-3">
              <v-icon>mdi-window-close</v-icon>
              <div style="font-size: x-large">레시피</div>
              <v-icon>mdi-blank</v-icon>
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
                  > -->
                </div>
              </v-img>
            </div>

            <v-card-title class="text-md-h3">{{
              recipeDetail.title
            }}</v-card-title>
            <v-card-subtitle class="text-md-h5">맛있겠다!</v-card-subtitle>

            <v-card-text>
              <v-row align="center" class="d-flex mx-0">
                <v-icon
                  v-if="recipeDetail.like === true"
                  small
                  color="pink lighten-1"
                  @click="deleteLikes()"
                  >mdi-heart</v-icon
                ><v-icon
                  v-else-if="recipeDetail.like === false"
                  small
                  color="grey"
                  @click="countLikes()"
                  >mdi-heart-outline</v-icon
                >
                &nbsp;{{ recipeDetail.recipeLike }}&nbsp;&nbsp;
                <v-icon
                  v-if="recipeDetail.bookmark === true"
                  small
                  color="yellow lighten-1"
                  @click="deleteBookmark()"
                  >mdi-bookmark</v-icon
                >
                <v-icon
                  v-if="recipeDetail.bookmark === false"
                  small
                  color="grey"
                  @click="saveBookmark()"
                  >mdi-bookmark-outline</v-icon
                >
                &nbsp;{{ recipeDetail.recipeBookmark }}
              </v-row>

              <v-row align="center" class="mx-0">등록일자 : 2022-09-18</v-row>
              <div class="my-4 text-subtitle-1">
                <!-- <v-avatar
                  v-if="profileImg !== null || profileImg !== ''"
                  size="36px"
                >
                  <img alt="Avatar" :src="profileImg" />
                </v-avatar> -->
                <v-avatar size="27px" color="letcipe">
                  <v-icon dark> mdi-account-circle </v-icon>
                </v-avatar>
                <span style="color: #ffa500">Chef</span>
                &nbsp;&nbsp;{{ writer }}
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
              <v-row class="d-flex justify-center"
                ><v-btn color="letcipe">+ 담기</v-btn></v-row
              >
              <v-row align="center"
                ><v-col cols="1"></v-col
                ><v-col cols="10" class="pr-0 pl-0"
                  ><v-text-field
                    v-model="enterComment"
                    color="letcipe"
                    solo
                    outlined
                    append-icon="mdi-arrow-left-bottom"
                    @click:append="addComment()"
                    @keyup.enter="addComment()"
                  ></v-text-field></v-col
                ><v-col cols="1"></v-col
              ></v-row>

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
import { mapActions, mapState } from 'vuex'
export default {
  name: 'RecipeDetail',
  data() {
    return {
      recipeInfo: {},
      TotalPage: 1,
      currentPage: 1,
      enterComment: null,
      writer: '',
      content: '',
      cookingTime: 10,
      serving: 4,
      repImg: 'adsasdasd',
      recipeLike: 21,
      recipeBookmark: 16,
      recipeSteps: [],
      recipeComment: [
        {
          nickname: '수리수리마수리',
          content: 'asd맛나는 레시피일까요아닐까요',
          reg_time: '2022-09-24',
          mod_time: '',
        },
        {
          nickname: '수리수리마수리',
          content: 'asd맛나는 레시피일까요아닐까요',
          reg_time: '2022-09-24',
          mod_time: '',
        },
      ],
      recipeIngredient: [],
      ingredient: [],
      profileImg: '',
    }
  },
  computed: {
    ...mapState('comment', ['comments', 'commentNum']),
    ...mapState('recipe', ['recipeDetail', 'recipeID']),
  },

  created() {
    const promise = new Promise((resolve, reject) => {
      resolve()
    })
    this.recipeInfo = {
      boardType: 'RECIPE',
      boadrId: this.recipeID,
    }
    promise.then(async () => {
      // 레시피 디테일 불러오는 부분
      this.recipeSteps = []
      await this.RecipeDetail(this.recipeID)
      console.log(this.recipeDetail)
      console.log(this.recipeDetail.user.nickname)
      this.recipeSteps = this.recipeDetail.recipeSteps
      this.recipeIngredient = this.recipeDetail.ingredients
      this.writer = this.recipeDetail.user.nickname
      console.log(this.writer)

      console.log('f레시피 인포' + JSON.stringify(this.recipeInfo))
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
      console.log('레시피 인포' + this.recipeInfo)
      await this.getComment(this.recipeInfo)
    })
  },
  methods: {
    ...mapActions('comment', ['getComment', 'postComment', 'getCommentNum']),
    ...mapActions('recipe', [
      'RecipeDetail',
      'createRecipeDetail',
      'selectBookmarks',
      'deleteBookmarks',
      'countRecipeLikes',
      'decountRecipeLikes',
    ]),
    handlePage(page) {
      this.recipeInfo = {
        boardType: 'RECIPE',
        boardId: '1',
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
    saveBookmark() {
      this.selectBookmarks(1798)
      this.RecipeDetail(1798)
    },
    deleteBookmark() {
      this.deleteBookmarks(1798)
      //   this.RecipeDetail(1)
    },
    deleteLikes() {
      this.decountRecipeLikes(1798)
    },
    countLikes() {
      this.countRecipeLikes(1798)
    },
    addComment() {
      console.log(this.enterComment)
      const comment = {
        content: this.enterComment,
        boardId: 1798,
        boardType: 'RECIPE',
      }
      this.postComment(comment)
      this.enterComment = null
      this.$router.go()
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
