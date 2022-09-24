<template>
  <div id="app">
    <v-app class="cart-page">
      <v-container class="cart-container d-flex-row">
        <div class="cart-head-wrap">
          <div class="d-flex justify-space-between pb-3">
            <v-icon>mdi-window-close</v-icon>
            <div style="font-size: x-large;">장바구니</div>
            <v-icon>mdi-blank</v-icon>
          </div>
          <div class="d-flex justify-space-between" @click="moveWirte">
            <div>레시피리스트 제목을 입력해주세요</div>
            <v-icon>mdi-chevron-right</v-icon>
          </div>
          <div class="d-flex justify-space-between">
            <div class="d-flex align-items-center">
              <v-icon>mdi-check-circle</v-icon>
              <div>전체선택</div>
            </div>
            <div>선택삭제</div>
          </div>
        </div>
        <v-spacer></v-spacer>
        <div class="cart-recipe-wrap">
          <div class="d-flex pa-2">
            <v-icon>mdi-cart</v-icon>
            <div>내가 담은 레시피</div>
          </div>
          <v-divider></v-divider>
          <div v-for="(recipeInfo, i) in letcipeList.recipeListItem" :key="i">
            <v-list-item three-line style="background-color: white">
              <v-icon>mdi-check-circle</v-icon>
              <v-list-item-avatar tile size="100">
                <v-img src="https://2bob.co.kr/data/recipe/20210810142007-EYPBD.jpg"></v-img>
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title class="d-flex justify-space-between">
                  <div>
                    {{
                    recipeInfo.recipe.title
                    }}
                  </div>
                  <v-icon>mdi-window-close</v-icon>
                </v-list-item-title>

                <v-list-item-subtitle
                  style="text-overflow: ellipsis; over-flow: hidden; white-space: nowrap; display: inline-block;"
                >
                  {{
                  recipeInfo.recipe.content
                  }}
                </v-list-item-subtitle>
                <div class="d-flex justify-space-between">
                  <v-list-item-subtitle>
                    <v-icon small color="pink lighten-1">mdi-cards-heart</v-icon>
                    {{ recipeInfo.recipe.recipeLike }}
                  </v-list-item-subtitle>
                  <v-list-item-subtitle style="text-align: right">선택한 재료 4개를 포함하고 있어요.</v-list-item-subtitle>
                </div>
              </v-list-item-content>
            </v-list-item>
            <div class="d-flex">
              <v-icon>mdi-minus</v-icon>
              <div>숫자</div>
              <v-icon>mdi-plus</v-icon>
            </div>
            <v-divider></v-divider>
          </div>
        </div>
        <div class="cart-ingredient-wrap">
          <div class="d-flex pa-2">
            <v-icon>mdi-cart</v-icon>
            <div>장보기 목록</div>
          </div>
          <v-divider></v-divider>
          <div v-for="(item, i) in cartList" :key="i">
            <div class="d-flex justify-space-between pa-2">
              <div style="font-size: x-large">{{item.ingredient}}</div>
              <div>
                <div style="font-size: x-large;">
                  <v-btn class="mx-2" fab dark x-small color="#aac821" @click="minusNum">
                    <v-icon dark>mdi-minus</v-icon>
                  </v-btn>
                  {{ item.num }}
                  <v-btn class="mx-2" fab dark x-small color="#aac821" @click="addNum">
                    <v-icon dark>mdi-plus</v-icon>
                  </v-btn>
                </div>
                <div style="float: right;">{{item.num}}g</div>
              </div>
            </div>
            <v-divider></v-divider>
          </div>
          <div class="d-flex justify-center align-center pa-2">
            <v-btn class="mx-2" fab dark x-small color="#aac821" @click="plusCartList">
              <v-icon dark>mdi-plus</v-icon>
            </v-btn>
          </div>
        </div>
        <div class="cart-count-wrap">
          <div class="ma-2">총 합계</div>
          <v-divider></v-divider>
          <div class="d-flex justify-space-between ma-2">
            <div>총 요리 수</div>
            <div>2</div>
          </div>
          <v-divider></v-divider>
          <div class="d-flex justify-space-between ma-2">
            <div>총 재료 수</div>
            <div>17</div>
          </div>
        </div>
        <div class="cart-btn-wrap pt-3">
          <v-btn elevation="5" width="100%" large color="#aac821" class="white--text">장보기</v-btn>
        </div>
      </v-container>
    </v-app>
  </div>
</template>

<script>
export default {
  name: 'CartIndex',
  data() {
    return {
      letcipeList: {
        id: 3,
        nickname: 'jgy',
        name: '정기연의 면요리',
        description: '가성비 버전',
        reg_time: '2022-09-13',
        is_shared: 'private',
        recipeListItem: [
          {
            recipe: {
              id: 1,
              nickname: '싸피10기',
              title: '고르곤졸라피자',
              content: `만만치 않은 가격에도 도우 반죽 때문에 집에서 만들기 영 꺼려졌던 피자
						토르티야로 간편하게 해결하세요!
						꼬릿하지만 묘한 매력이 있는 고르곤졸라치즈를 얹으면
						10분 만에 풍미 가득한 피자가 완성되죠. 꿀에 찍어 먹으면 더 맛있어요.`,
              cookingTime: 5,
              serving: 4,
              repImg: 'https://2bob.co.kr/data/recipe/20210810142007-EYPBD.jpg',
              recipeLike: 21222,
              recipeBookmark: 16,
            },
            amount: 3,
          },
          {
            recipe: {
              id: 4,
              nickname: '싸피7기',
              title: '시카고피자',
              content: '만만치 않은 가격!',
              cookingTime: 10,
              serving: 2,
              repImg: 'https://2bob.co.kr/data/recipe/20210810142007-EYPBD.jpg',
              recipeLike: 21,
              recipeBookmark: 16,
            },
            amount: 1,
          },
        ],
      },
      cartList: [
        { ingredient: '당근', num: 3 },
        { ingredient: '오이', num: 3 },
        { ingredient: '돼지고기', num: 3 },
        { ingredient: '고춧가루', num: 3 },
      ],
    }
  },
  methods: {
    minusNum() {},
    addNum() {},
    plusCartList() {},
    moveWirte() {
      this.$router.push('/cart/write')
    },
  },
}
</script>

<style scoped>
.cart-page {
  padding-top: 70px;
  padding-bottom: 70px;
}
.cart-page-head {
  padding: 4%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  /* border: 1px solid gray; */
}
.cart-head-wrap {
  padding: 4%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  /* border: 1px solid gray; */
}
.cart-recipe-wrap {
  padding: 2%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
}
.cart-ingredient-wrap {
  padding: 2%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
}
.cart-count-wrap {
  padding: 2%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
}

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