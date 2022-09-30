<template>
  <div id="app">
    <v-app id="inspire">
      <div class="bookmark-page">
        <v-container class="bookmark-container d-flex-row">
          <div class="bookmark-head-wrap">
            <div class="d-flex justify-space-between pb-3">
              <v-icon>mdi-window-close</v-icon>
              <div style="font-size: x-large">검색하기</div>
              <v-icon>mdi-blank</v-icon>
            </div>
          </div>
          <v-divider></v-divider>
          <div v-if="recipes != null && recipes.length > 0">
            <v-card-subtitle>"{{ searchedName }}" 검색 결과</v-card-subtitle>
            <div v-for="(recipeInfo, i) in recipes" :key="i">
              <v-list-item three-line>
                <v-list-item-avatar tile size="100">
                  <v-img :src="recipeInfo['repImg']"></v-img>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title>
                    {{ recipeInfo['title'] }}
                  </v-list-item-title>

                  <v-list-item-subtitle>
                    {{ recipeInfo['content'] }}
                  </v-list-item-subtitle>
                  <div class="d-flex justify-space-between">
                    <v-list-item-subtitle>
                      <v-icon small color="pink lighten-1"
                        >mdi-cards-heart</v-icon
                      >
                      {{ recipeInfo['recipeLike'] }}
                    </v-list-item-subtitle>
                    <v-list-item-subtitle style="text-align: right">
                      <v-btn small color="letcipe">+담기</v-btn>
                    </v-list-item-subtitle>
                  </div>
                </v-list-item-content>
              </v-list-item>
              <v-divider></v-divider>
            </div>
            <div class="text-center">
              <v-pagination
                v-model="Page"
                color="letcipe"
                :length="TotalPage"
                prev-icon="mdi-menu-left"
                next-icon="mdi-menu-right"
                @input="handlePage"
              ></v-pagination>
            </div>
          </div>
        </v-container>
      </div>
    </v-app>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
export default {
  name: 'BookmarkPage',
  data() {
    return {
      TotalPage: 1,
      currentPage: 1,
      byname: '',
      searchedName: '',
      dialogSameIngre: false,
      byRecipe: true,
      scroll: 0,
      scrollTarget: '',
      colors: {
        '가루 분말': 'purple',
        '감자 고구마': 'pink',
        고기: 'pink darken-3',
        곡류: 'purple lighten-1',
        과일: 'red lighten-5',
        과자: 'green lighten-3',
        '국물 육수': 'green',
        기름: 'yellow darken-4',
        기타: 'blue lighten-3',
        달걀: 'blue darken-4',
        '떡 면': 'red',
        '묵 두부': 'purple darken-3',
        빵: 'red lighten-2',
        어패류: 'yellow lighten-1',
        '유제품 치즈': 'blue darken-2',
        '음료 주류': 'green lighten-1',
        '음식 식품': 'pink lighten-3',
        절임류: 'light-green darken-4',
        '조미료 향신료 소스': 'blue',
        채소: 'light-green',
        '초콜릿 사탕': 'light-green lighten-2',
        '콩 견과류': 'yellow',
        해조류: 'grey',
        '햄 소시지': 'black',
      },
      isLoading: false,
      items: [],
      keyword: '',
      search: null,
      tab: null,
      isSelected: [],
      selectedIngre: '',
    }
  },
  computed: {
    ...mapState('ingredients', ['ingredientsList']),
    ...mapState('recipe', []),
    ...mapState('recipelist', ['getRecipeList']),
    ...mapState('recipe', []),
  },
  watch: {},
  methods: {
    ...mapActions(),
  },
}
</script>

<style scoped>
.bookmark-page {
  /* padding-top: 70px; */
  padding-bottom: 70px;
  padding: 4%;
}
.bookmark-page-head {
  padding: 4%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  /* border: 1px solid gray; */
}
.bookmark-container {
  position: sticky;
  height: 100%;
  color: black;

  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
}
:deep(.v-input__icon.v-input__icon--append) {
  display: none;
}
:deep(.v-text-field__details) {
  display: none;
}
</style>
