<template>
  <div id="app"> 
    <v-app id="inspire">
      <div id="recipedetail-container">
        <v-container style="width: 100%">
          <v-card class="mx-auto my-12">
            <v-img src="https://2bob.co.kr/data/recipe/20210810142007-EYPBD.jpg"></v-img>

            <v-card-title class="text-md-h3">{{ name }}</v-card-title>

            <v-card-text>
              <div class="my-4 text-subtitle-1">
                <v-row class="mx-0 d-flex justify-space-between">
                  Chef&nbsp;&nbsp;{{ nickname
                  }}
                  <v-btn style="border: 1px solid black">+ 전체담기</v-btn>
                </v-row>
              </div>
              <v-row align="center" class="mx-0">{{ description }}</v-row>
              <v-row align="center" class="mx-0">
                <v-icon small color="blue lighten-1">mdi-thumb-up</v-icon>&nbsp;2200&nbsp;&nbsp;
                <v-icon small color="pink lighten-1">mdi-cards-heart</v-icon>&nbsp;123&nbsp;&nbsp;
                <v-icon small color="blue lighten-3">mdi-comment</v-icon>&nbsp;23
              </v-row>
              <v-row align="center" class="mx-0">등록일자 : 2022-09-18</v-row>
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
                <v-hover v-for="(item, i) in recipeListItems" :key="i" style="width: 90%">
                  <template #default="{ hover }">
                    <div
                      style="
                        border: 1px solid black !important;
                        border-radius: 4px;
                      "
                      :elevation="hover ? 24 : 6"
                      :class="hover ? 'grey lighten-5' : 'white'"
                      class="mx-auto mt-2 mb-2 d-flex align-center"
                    >
                      <div class="ml-4">{{ i }}</div>
                      <v-list-item three-line>
                        <v-list-item-avatar tile size="57">
                          <v-img
                            elevation="10"
                            src="https://2bob.co.kr/data/recipe/20191212142613-HV8JG.jpg"
                            style="border-radius: 5px"
                          ></v-img>
                        </v-list-item-avatar>
                        <v-list-item-content>
                          <v-list-item-title class="mb-1">{{ item.recipe.title }}</v-list-item-title>
                          <v-list-item-subtitle>
                            {{
                            item.recipe.content
                            }}
                          </v-list-item-subtitle>
                        </v-list-item-content>
                      </v-list-item>

                      <v-icon
                        v-if="!checkedRecipe[i]"
                        class="mr-3"
                        @click="addCart(i)"
                      >mdi-check-circle-outline</v-icon>
                      <v-icon v-else class="mr-3" @click="addCart(i)">mdi-check-circle</v-icon>
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
export default {
  name: 'RecipeListDetail',
  data() {
    return {
      id: '3',
      nickname: 'jgy',
      name: '정기연의 면요리',
      description: '가성비 버전',
      reg_time: '2022-09-13',
      is_shared: 'private',
      recipeListItems: [
        {
          recipe: {
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
          },
          amount: 3,
        },
        {
          recipe: {
            id: 4,
            nickname: '싸피7기',
            title: '시카고피자',
            content: '만만치 않은 가격!',
            cookingTime: 2,
            serving: 2,
            repImg: 'fsdfadasd',
            recipeLike: 21,
            recipeBookmark: 16,
          },
          amount: 1,
        },
      ],
      checkedRecipe: [],
      cart: [],
      isAllCheck: false,
    }
  },
  created() {
    this.initCart()
  },
  methods: {
    initCart() {
      for (let i = 0; i < this.recipeListItems.length; i++) {
        this.checkedRecipe.push(false)
      }
    },
    addCart(index) {
      if (this.isAllCheck) {
        this.isAllCheck = false
        this.cart = []
      }
      if (!this.checkedRecipe[index]) {
        this.checkedRecipe[index] = true
        this.cart.push(this.recipeListItems[index])
      } else {
        this.checkedRecipe[index] = false
        this.cart.splice(index, 1)
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
