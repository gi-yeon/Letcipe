<template>
  <div id="app">
    <v-app id="inspire"
      ><div id="recipedetail-container">
        <v-container style="width: 100%">
          <v-card class="mx-auto my-12">
            <v-img src="https://2bob.co.kr/data/recipe/20210810142007-EYPBD.jpg"
              ><div class="ref-wrap">
                <v-card-title class="text-md-h1 ref-title">{{
                  title
                }}</v-card-title>

                <!-- 레시피도 서브타이틀 넣을지? 현재 erd에 작성 안되어있음 -->
                <v-card-subtitle class="text-md-h3 ref-subtitle"
                  >맛있겠다!</v-card-subtitle
                >
              </div></v-img
            >

            <v-card-title class="text-md-h3">{{ title }}</v-card-title>
            <v-card-subtitle class="text-md-h5">맛있겠다!</v-card-subtitle>

            <v-card-text>
              <v-row align="center" class="mx-0">
                <v-icon small color="blue lighten-1">mdi-thumb-up</v-icon
                >&nbsp;{{ recipeLike }}&nbsp;&nbsp;
                <v-icon small color="pink lighten-1">mdi-cards-heart</v-icon
                >&nbsp;{{ recipeBookmark }}
              </v-row>
              <v-row align="center" class="mx-0">등록일자 : 2022-09-18 </v-row>
              <div class="my-4 text-subtitle-1">
                Chef&nbsp;&nbsp;{{ nickname }}
              </div>
            </v-card-text>

            <v-divider class="mx-4"></v-divider>
            <v-divider class="mx-4"></v-divider>
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
                  <v-img :src="stepInfo.img"></v-img>
                  <h2 style="display: inline">
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
                <v-chip v-for="(tag, i) in tags" :key="i">
                  {{ tag.name }}
                </v-chip>
              </v-chip-group>
            </v-card-text>
          </v-card>
          <!-- <v-img
            class="ref-imgs"
            src="https://2bob.co.kr/data/recipe/20210810142007-EYPBD.jpg"
            style="margin: auto"
          >
            <div class="ref-wrap">
              <v-card-title class="text-md-h1">{{ title }}</v-card-title>

              <v-card-subtitle class="text-md-h3">맛있겠다!</v-card-subtitle>
            </div>
          </v-img>
          <v-card-text style="margin: auto; padding: 16px 0px 16px 0px">
            <v-row style="text-align: center">
              <v-col
                cols="7"
                style="padding: 0% 0% 0% 10%"
                class="text-md-h5 text-left"
              >
                <v-icon small color="blue lighten-1">mdi-thumb-up</v-icon
                >&nbsp;{{ recipeLike }}&nbsp;&nbsp;
                <v-icon small color="pink lighten-1">mdi-cards-heart</v-icon
                >&nbsp;{{ recipeBookmark }} <br />등록일자 : 2022-09-18</v-col
              ><v-col cols="4"
                ><v-btn class="text-md-h5">+담기</v-btn></v-col
              ></v-row
            >

            <v-card-title class="text-md-h3">{{ title }}</v-card-title>
            <v-card-subtitle class="text-md-h5">맛있겠다!</v-card-subtitle>
            <v-card-subtitle>Chef&nbsp;{{ nickname }}</v-card-subtitle>
            <v-card-text>{{ content }}</v-card-text>
            <v-card-title>재료 </v-card-title>
          </v-card-text> -->
        </v-container>
      </div></v-app
    >
  </div>
</template>

<script>
export default {
  name: 'RecipeDetail',
  data() {
    return {
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
            '오이는 길게 2등분해서 씨를 제거한 뒤 한입 크기로 썰고, 감자와 당근, 양파, 닭다릿살도 한입 크기로',
        },
      ],
      recipeComment: [
        {
          nickname: 1,
          content: 'asd',
          reg_time: '',
          mod_time: '',
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
