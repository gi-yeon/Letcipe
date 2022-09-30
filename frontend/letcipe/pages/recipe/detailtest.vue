<template>
  <div id="app"> 
    <v-app id="inspire">
      <div id="recipedetail-container">
        <v-container style="width: 100%">
          <v-row>
            <v-col style="text-align: center; font-size: xx-large"
              >레시피</v-col
            >
          </v-row>
          <v-card class="mx-auto my-5">
            <v-img :src="recipeDetail.repImg">
              <div class="ref-wrap">
                <v-card-title class="text-md-h1 ref-title">
                  {{ recipeDetail.title }}
                </v-card-title>

                <!-- 레시피도 서브타이틀 넣을지? 현재 erd에 작성 안되어있음 -->
                <v-card-subtitle class="text-md-h3 ref-subtitle"
                  >맛있겠다!</v-card-subtitle
                >
              </div>
            </v-img>

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
                  @click="deleteLikes"
                  >mdi-heart</v-icon
                ><v-icon
                  v-else-if="recipeDetail.like === false"
                  small
                  color="grey"
                  @click="countLikes"
                  >mdi-heart-outline</v-icon
                >
                &nbsp;{{ recipeDetail.recipeLike }}&nbsp;&nbsp;
                <v-icon
                  v-if="recipeDetail.bookmark === true"
                  small
                  color="yellow lighten-1"
                  @click="deleteBookmark"
                  >mdi-bookmark</v-icon
                >
                <v-icon
                  v-if="recipeDetail.bookmark === false"
                  small
                  color="grey"
                  @click="saveBookmark"
                  >mdi-bookmark-outline</v-icon
                >
                &nbsp;{{ recipeDetail.recipeBookmark }}
              </v-row>

              <v-row align="center" class="mx-0">등록일자 : 2022-09-18</v-row>
              <div class="my-4 text-subtitle-1">
                Chef&nbsp;&nbsp;{{ writer.nickname }}
              </div>
            </v-card-text>

            <v-divider class="mx-4"></v-divider>
            <v-divider class="mx-4"></v-divider>
            <v-divider class="mx-4"></v-divider>
            <div>
              <v-card-text>{{ recipeDetail.content }}</v-card-text>
              <v-card-title class="d-flex text-md-h4">
                <div>재료</div>
                <div class="ml-3" style="font-size: small; color: #ffa500">
                  <v-icon
                    v-if="recipeDetail.cookingTime != -1"
                    small
                    color="letcipe"
                    >mdi-timer</v-icon
                  >
                  {{ recipeDetail.cookingTime }}분

                  <v-icon
                    v-if="recipeDetail.serving != -1"
                    small
                    color="letcipe"
                    >mdi-account</v-icon
                  >
                  {{ recipeDetail.serving }}인분
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
            <v-row>
              <v-col align="center">
                <div
                  v-for="(comment, i) in recipeComment"
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
                            <v-list-item-subtitle class="recipe-comment">
                              {{ comment.nickname }}
                            </v-list-item-subtitle>
                          </v-col>
                          <v-col align="right">
                            <v-list-item-subtitle class="recipe-comment">
                              {{ comment.reg_time }}
                            </v-list-item-subtitle>
                          </v-col>
                        </v-row>

                        <v-list-item-subtitle class="recipe-comment">
                          {{ comment.content }}</v-list-item-subtitle
                        >
                      </v-list-item-content>
                    </v-list-item>
                  </div>
                </div>
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
  name: 'RecipeDetailTest',
  data() {
    return {
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
    }
  },
  computed: {
    ...mapState('recipe', ['recipeDetail']),
  },
  created() {
    const promise = new Promise((resolve, reject) => {
      resolve()
    })
    promise.then(async () => {
      this.recipeSteps = []
      // recipeId 받아와서 넣어야함
      await this.RecipeDetail(1798)
      console.log(this.recipeDetail)
      //   console.log(this.recipeDetail.ingredients)
      this.recipeSteps = this.recipeDetail.recipeSteps
      this.recipeIngredient = this.recipeDetail.ingredients
      this.writer = this.recipeDetail.user
    })
  },
  methods: {
    // 필요한거 레시피 좋아요, 레시피 좋아요 해제
    // 레시피 북마크 설정, 북마크 해제
    ...mapActions('recipe', [
      'RecipeDetail',
      'createRecipeDetail',
      'selectBookmarks',
      'deleteBookmarks',
      'countRecipeLikes',
      'decountRecipeLikes',
    ]),
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
  },
}
</script>

<style scoped>
#recipedetail-container {
  position: sticky;
  height: 100%;
  padding: 8% 0% 0% 0%;
  background-image: url('/bg/bg_img.png');
  background-repeat: repeat;

  color: black;
}
.stepDetail {
  margin-top: 10%;
  margin-bottom: 10%;
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
  /* color: rgb(0, 0, 0); */
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
