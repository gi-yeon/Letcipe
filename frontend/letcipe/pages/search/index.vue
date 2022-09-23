<template>
  <div id="app">
    <v-app id="inspire">
      <div class="search-container">
        <v-container style="padding: 0%"
          ><v-tabs
            v-model="tab"
            centered
            icons-and-text
            color="red accent-3"
            hide-slider
          >
            <v-tabs-slider></v-tabs-slider>

            <v-tab href="#tab-1" style="width: 50%">
              이름으로 검색
              <v-icon>mdi-hamburger</v-icon>
            </v-tab>

            <v-tab href="#tab-2" style="width: 50%">
              재료로 검색
              <v-icon>mdi-carrot</v-icon>
            </v-tab>
          </v-tabs>

          <v-tabs-items v-model="tab">
            <v-tab-item :value="`tab-1`">
              <v-row align="center" class="pl-6 pr-6">
                <v-col class="d-flex pr-0 pl-0" cols="3">
                  <v-row>
                    <v-btn
                      style="width: 90%"
                      depressed
                      :color="byRecipe ? 'red accent-3' : 'black'"
                      text
                      small
                      class="pt-0 pb-0"
                      @click="byRecipe = true"
                      >레시피</v-btn
                    ><v-btn
                      style="width: 90%"
                      :color="byRecipe ? 'black' : 'red accent-3'"
                      text
                      small
                      class="pt-0 pb-0"
                      @click="byRecipe = false"
                      >레시피 리스트</v-btn
                    ></v-row
                  ></v-col
                >
                <v-col class="d-flex pr-0 pl-0" align="center">
                  <v-text-field
                    v-model="byname"
                    label="이름으로 검색"
                    outlined
                    class="pt-3 pb-3"
                    color="black"
                    @keyup.enter="searchByName"
                  >
                  </v-text-field></v-col></v-row
              ><v-divider></v-divider>
              <div v-if="recipebyName != null && recipebyName.length > 0">
                <v-card-subtitle>"{{ byname }}" 검색 결과</v-card-subtitle>
                <div v-for="(recipeInfo, i) in recipebyName" :key="i">
                  <v-list-item three-line style="background-color: white">
                    <v-list-item-avatar tile size="100"
                      ><v-img
                        src="https://2bob.co.kr/data/recipe/20210810142007-EYPBD.jpg"
                      ></v-img></v-list-item-avatar
                    ><v-list-item-content>
                      <v-list-item-title>{{
                        recipeInfo.recipe.title
                      }}</v-list-item-title>

                      <v-list-item-subtitle>{{
                        recipeInfo.recipe.content
                      }}</v-list-item-subtitle>
                      <div class="d-flex justify-space-between">
                        <v-list-item-subtitle>
                          <v-icon small color="pink lighten-1"
                            >mdi-cards-heart</v-icon
                          >{{ recipeInfo.recipe.recipeLike }}
                        </v-list-item-subtitle>
                        <v-list-item-subtitle style="text-align: right"
                          ><v-btn small dark>+담기</v-btn></v-list-item-subtitle
                        >
                      </div>
                    </v-list-item-content>
                  </v-list-item>
                  <v-divider></v-divider>
                </div></div
            ></v-tab-item>
            <v-tab-item :value="`tab-2`">
              <v-row>
                <v-col align="center">
                  <v-autocomplete
                    v-model="model"
                    :items="items"
                    :loading="isLoading"
                    :search-input.sync="search"
                    clearable
                    hide-details
                    hide-selected
                    hide-spin-buttons
                    item-text="name"
                    item-value="id"
                    label="재료로 검색"
                    outlined
                    class="pt-3 pb-3"
                    color="black"
                    style="width: 90%"
                  >
                    <template #no-data>
                      <v-list-item>
                        <v-list-item-title>
                          일치하는 재료가 없습니다.
                        </v-list-item-title>
                      </v-list-item>
                    </template>
                    <template #item="{ item }">
                      <v-list-item-content @click="selectIngre(item)">
                        <v-list-item-title
                          v-text="item.name"
                        ></v-list-item-title>
                      </v-list-item-content>
                      <v-list-item-action @click="selectIngre(item)">
                        <v-chip :color="colors[item.category]" label>{{
                          item.category
                        }}</v-chip>
                      </v-list-item-action>
                    </template>
                  </v-autocomplete></v-col
                ></v-row
              >

              <v-card-subtitle v-if="isSelected.length != 0"
                >선택한 재료 목록</v-card-subtitle
              >
              <div align="center">
                <v-row class="pb-5" style="width: 90%">
                  <div v-for="(item, i) in isSelected" :key="i">
                    <v-col
                      ><v-chip label :color="colors[item.category]">{{
                        item.name
                      }}</v-chip></v-col
                    >
                  </div></v-row
                >
              </div>
              <v-divider></v-divider>
              <div v-if="recipebyIngre != null && recipebyIngre.length > 0">
                <v-card-subtitle>재료 기반 추천 레시피</v-card-subtitle>
                <div v-for="(recipeInfo, i) in recipebyIngre" :key="i">
                  <v-list-item three-line style="background-color: white">
                    <v-list-item-avatar tile size="100"
                      ><v-img
                        src="https://2bob.co.kr/data/recipe/20210810142007-EYPBD.jpg"
                      ></v-img></v-list-item-avatar
                    ><v-list-item-content>
                      <v-list-item-title>{{
                        recipeInfo.recipe.title
                      }}</v-list-item-title>

                      <v-list-item-subtitle>{{
                        recipeInfo.recipe.content
                      }}</v-list-item-subtitle>
                      <div class="d-flex justify-space-between">
                        <v-list-item-subtitle>
                          <v-icon small color="pink lighten-1"
                            >mdi-cards-heart</v-icon
                          >{{ recipeInfo.recipe.recipeLike }}
                        </v-list-item-subtitle>
                        <v-list-item-subtitle style="text-align: right"
                          >선택한 재료 4개를 포함하고
                          있어요.</v-list-item-subtitle
                        >
                      </div>
                    </v-list-item-content>
                  </v-list-item>
                  <v-divider></v-divider>
                </div>
              </div>
              <div
                v-if="
                  isSelected.length == 0 ||
                  recipebyIngre == null ||
                  recipebyIngre.length == 0
                "
              >
                <v-img src="/img/noSearchIngre.png"></v-img>
              </div>
            </v-tab-item> </v-tabs-items
        ></v-container></div
    ></v-app>
  </div>
</template>

<script>
export default {
  name: 'SearchMain',
  data() {
    return {
      byname: null,

      byRecipe: true,
      scroll: 0,
      scrollTarget: null,
      colors: { 채소: 'light-green', '콩 견과류': 'yellow' },
      isLoading: false,
      items: [],
      model: null,
      search: null,
      tab: null,
      isSelected: [],
      recipebyName: null,
      recipebyIngre: null,
    }
  },
  computed: {},
  watch: {
    search(val) {
      if (this.items.length > 0) return

      this.isLoading = true
      // 데이터 받아와서 넣는 작업 필요
      this.items = [
        {
          id: 943,
          name: '양파',
          category: '채소',
          measure: '개',
          gml: 300.0,
        },
        {
          id: 567,
          name: '양파즙',
          category: '콩 견과류',
          measure: 'ml',
          gml: 1.0,
        },
      ]
      this.isLoading = false
    },
  },
  methods: {
    selectIngre(item) {
      if (!this.isSelected.includes(item)) this.isSelected.push(item)
      else {
        alert('이미 추가된 재료입니다.')
      }
      // 재로를 추가할 때마다 axio요청 넣는 함수 작성
      this.recipebyIngre = [
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
      ]

      console.log(item)
    },
    searchByName() {
      // this.byname을 보내서 검색 결과 가져오는 함수 작성
      this.recipebyName = [
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
      ]
    },
  },
}
</script>

<style scoped>
.search-container {
  position: sticky;
  height: 100%;

  background-image: url('/bg/bg_img.png');
  background-repeat: repeat;

  color: black;
}
:deep(.v-input__icon.v-input__icon--append) {
  display: none;
}
:deep(.v-text-field__details) {
  display: none;
}
</style>
