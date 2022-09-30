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
              <v-img
                class="recipe-imgs"
                src="https://2bob.co.kr/data/recipe/20210810142007-EYPBD.jpg"
              >
                <div class="ref-wrap">
                  <v-card-title class="text-h6 ref-title">
                    {{ title }}
                  </v-card-title>

                  <!-- 레시피도 서브타이틀 넣을지? 현재 erd에 작성 안되어있음 -->
                  <!-- <v-card-subtitle class="text-md-h3 ref-subtitle"
                  >맛있겠다!</v-card-subtitle
                > -->
                </div>
              </v-img>
            </div>

            <v-card-title class="text-md-h3">{{ title }}</v-card-title>
            <!-- <v-card-subtitle class="text-md-h5">맛있겠다!</v-card-subtitle> -->

            <v-card-text>
              <v-row align="center" class="mx-0">
                <v-icon small color="blue lighten-1">mdi-thumb-up</v-icon>
                &nbsp;{{ recipeLike }}&nbsp;&nbsp;
                <v-icon small color="pink lighten-1">mdi-cards-heart</v-icon>
                &nbsp;{{ recipeBookmark }}
              </v-row>
              <v-row align="center" class="mx-0">등록일자 : 2022-09-18</v-row>
              <v-card-text style="padding: 4% 0% 0% 0%">
                Chef&nbsp;&nbsp;{{ nickname }}
              </v-card-text>
            </v-card-text>

            <v-divider class="mx-4"></v-divider>

            <div>
              <v-card-text>{{ content }}</v-card-text>
              <v-card-title class="text-md-h4">재료</v-card-title>
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
                      <td>{{ item.name }}</td>
                      <td>{{ item.amount }}</td>
                      <td>{{ item.unit }}</td>
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
                  <div class="recipe-img-wrap" align="center">
                    <v-img :src="stepInfo.img" class="recipe-imgs"></v-img>
                  </div>
                  <h2 style="display: inline">{{ stepInfo.step }}</h2>
                  {{ stepInfo.content }}
                </div>
              </div>
              <v-divider class="mx-4"></v-divider>
              <v-divider class="mx-4"></v-divider>
              <v-divider class="mx-4"></v-divider>
            </div>

            <v-card-text>
              <v-chip-group column>
                <v-chip v-for="(tag, i) in tags" :key="i">{{
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
      id: 1,
      nickname: '싸피10기',
      title: '고르곤졸라피자',
      content: `만만치 않은 가격에도 도우 반죽 때문에 집에서 만들기 영 꺼려졌던 피자.
토르티야로 간편하게 해결하세요!
꼬릿하지만 묘한 매력이 있는 고르곤졸라치즈를 얹으면
10분 만에 풍미 가득한 피자가 완성되죠. 꿀에 찍어 먹으면 더 맛있어요.`,
      cookingTime: 10,
      serving: 4,
      repImg: 'adsasdasd',
      recipeLike: 21,
      recipeBookmark: 16,
      recipeSteps: [
        {
          step: 1,
          img: 'https://2bob.co.kr/data/recipe/20210810142007-EYPBD.jpg',
          content:
            '오이는 길게 2등분해서 씨를 제거한 뒤 한입 크기로 썰고, 감자와 당근, 양파, 닭다릿살도 한입 크기로',
        },
        {
          step: 2,
          img: 'https://2bob.co.kr/data/recipe/20210810142007-EYPBD.jpg',
          content:
            '오이는 길게 2등분해서 씨를 제거한 뒤 한입 크기로 썰고, 감자와 당근, 양파, 닭다릿살도 한입 크기로오이는 길게 2등분해서 씨를 제거한 뒤 한입 크기로 썰고, 감자와 당근, 양파, 닭다릿살도 한입 크기로오이는 길게 2등분해서 씨를 제거한 뒤 한입 크기로 썰고, 감자와 당근, 양파, 닭다릿살도 한입 크기로',
        },
      ],

      recipeIngredient: [
        {
          name: '치즈',
          unit: 'g',
          category: 1,
          amount: 500,
        },
        {
          name: '치즈',
          unit: 'g',
          category: 1,
          amount: 500,
        },
      ],
      tags: [
        {
          name: '피자',
        },
        {
          name: '꿀',
        },
      ],
    }
  },
  computed: {
    ...mapState('comment', ['comments', 'commentNum']),
  },
  created() {
    this.recipeInfo = {
      boardType: 'RECIPE',
      boardId: '1',
    }
    const promise = new Promise((resolve, reject) => {
      resolve()
    })
    promise.then(async () => {
      await this.getCommentNum(this.recipeInfo)
      this.TotalPage = Math.ceil(this.commentNum / 5)
      this.recipeInfo = {
        boardType: 'RECIPE',
        boardId: '1',
        size: 5,
        page: this.currentPage,
      }
      await this.getComment(this.recipeInfo)
    })
  },
  methods: {
    ...mapActions('comment', ['getComment', 'postComment', 'getCommentNum']),
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
  },
  addComment() {
    console.log(this.enterComment)
    const comment = {
      content: this.enterComment,
      boardId: 1,
      boardType: 'RECIPE',
    }
    this.postComment(comment)
    this.enterComment = null
    this.$router.go()
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
  height: 30%;
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
