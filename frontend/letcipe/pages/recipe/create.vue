<template>
  <div id="app">
    <v-app id="inspire">
      <div class="create-container">
        <v-container style="padding: 0%">
          <v-card-title
            class="justify-center"
            style="background-color: white; font-size: 1.7rem"
            >레시피 등록</v-card-title
          >
          <v-divider></v-divider>
          <v-card>
            <v-card-title class="recipe-component">레시피 제목</v-card-title>
            <div class="recipe-input d-flex justify-center">
              <v-text-field
                v-model="title"
                class="text-md-h6"
                filled
                placeholder="ex) 소고기 미역국"
                hide-details="auto"
                :rules="rules"
                outlined
                color="#aac821"
              ></v-text-field>
            </div>
            <v-card-title class="recipe-component"
              >레시피 대표 사진</v-card-title
            >
            <div class="recipe-input d-flex justify-center">
              <v-file-input
                v-model="image"
                outlined
                accept="image/png, image/jpeg, image/bmp"
                placeholder="대표 사진 업로드"
                prepend-icon="mdi-camera"
                @change="Preview_image()"
              ></v-file-input>
            </div>
            <div class="d-flex justify-center">
              <v-img
                v-if="url != null"
                max-width="50%"
                :src="url"
                class="d-flex justify-center"
              ></v-img>
            </div>

            <v-card-title class="recipe-component">요리 소개</v-card-title>
            <div class="recipe-input d-flex justify-center">
              <v-textarea
                v-model="content"
                class="text-md-h6"
                auto-grow
                filled
                placeholder="요리와 레시피에 대한 간단한 설명을 적어주세요."
                :rules="rules"
                hide-details="auto"
                outlined
                color="#aac821"
              ></v-textarea>
            </div>
            <v-card-title class="recipe-component">카테고리</v-card-title>
            <div class="recipe-input d-flex justify-center">
              <v-select
                v-model="category"
                class="text-md-h6"
                :items="caregoryItems"
                outlined
                placeholder="카테고리 선택"
              ></v-select>
            </div>

            <v-card-title class="recipe-component">추가 정보</v-card-title>
            <div class="d-flex justify-center mr-5 ml-5">
              <v-card-subtitle class="d-flex justify-left align-center">
                조리 시간&nbsp;&nbsp;
                <v-text-field
                  v-model="cookingTime"
                  class="recipe-input-content text-md-h6"
                  filled
                  hide-details="auto"
                  outlined
                  color="#aac821"
                ></v-text-field
                >&nbsp;분
              </v-card-subtitle>
              <v-card-subtitle class="d-flex justify-left align-center">
                요리량&nbsp;&nbsp;
                <v-text-field
                  v-model="serving"
                  class="recipe-input-content text-md-h6"
                  filled
                  hide-details="auto"
                  outlined
                  color="#aac821"
                ></v-text-field
                >&nbsp;인분
              </v-card-subtitle>
            </div>
            <v-divider></v-divider>
            <v-divider></v-divider>
            <v-card-title class="recipe-component">재료</v-card-title>
            <v-simple-table style="width: 90%; margin: auto">
              <template #default>
                <thead>
                  <tr>
                    <th class="text-center">재료명</th>
                    <th class="text-center">재료양</th>
                    <th class="text-center">단위</th>
                    <th class="text-center"></th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(item, i) in ingredients" :key="i">
                    <td class="text-center">{{ item.name }}</td>
                    <td class="text-center">{{ item.amount }}</td>
                    <td class="text-center">{{ item.unit }}</td>

                    <th class="text-center">
                      <v-icon small @click="editItem(item)">mdi-pencil</v-icon>
                      <v-icon small @click="deleteItem(item)"
                        >mdi-delete</v-icon
                      >
                    </th>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
            <div class="d-flex justify-center">
              <v-dialog v-model="dialog" max-width="500px">
                <template #activator="{ on, attrs }">
                  <v-btn
                    dark
                    class="mb-5 mt-6"
                    v-bind="attrs"
                    v-on="on"
                    @click="clearItem"
                    >재료 추가</v-btn
                  >
                </template>

                <v-card>
                  <v-card-title>
                    <span>{{ formTitle }}</span>
                  </v-card-title>
                  <v-form ref="form">
                    <v-card-text>
                      <v-container>
                        <v-row>
                          <v-col cols="12" sm="6" md="4">
                            <!-- <v-text-field
                            v-model="editedItem.name"
                            label="재료명"
                            ></v-text-field>-->
                            <v-autocomplete
                              ref="keyword"
                              v-model="keyword"
                              :rules="keyword_rule"
                              :items="ingredientsList"
                              :loading="isLoading"
                              :search-input.sync="search"
                              cache-items
                              clearable
                              hide-details
                              hide-selected
                              hide-spin-buttons
                              item-text="name"
                              item-value="id"
                              label="재료검색"
                              outlined
                              :required="keyword"
                              class="pt-3 pb-3"
                              color="letcipe"
                              style="width: 90%"
                              append-inner-icon="mdi-magnify"
                              @keyup="ingre(search)"
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
                                  <v-chip
                                    :color="colors[item.category]"
                                    label
                                    >{{ item.category }}</v-chip
                                  >
                                </v-list-item-action>
                              </template>
                            </v-autocomplete>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field
                              ref="amount"
                              v-model="editedItem.amount"
                              :rules="ingre_rule"
                              required
                              placeholder="0"
                              color="letcipe"
                              label="재료량"
                            ></v-text-field>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field
                              v-model="editedItem.unit"
                              disabled
                              label="단위"
                            ></v-text-field>
                          </v-col>
                        </v-row>
                      </v-container>
                    </v-card-text>
                  </v-form>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="close"
                      >취소</v-btn
                    >
                    <v-btn
                      color="blue darken-1"
                      :disabled="
                        keyword === null ||
                        keyword === '' ||
                        editedItem.amount === null ||
                        editedItem.amount === ''
                      "
                      text
                      @click="saveIngre"
                      >재료 저장</v-btn
                    >
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </div>
            <v-divider></v-divider>
            <v-divider></v-divider>
            <v-card-title class="recipe-component">요리 순서</v-card-title>
            <div v-for="(step, i) in steps" :key="i">
              <div class="d-flex justify-space-between">
                <v-card-title class="text-subtitle-2 recipe-component"
                  >Step {{ step.no }}</v-card-title
                >
                <v-icon class="mr-5" color="red" @click="deleteStep(i)"
                  >mdi-minus-circle</v-icon
                >
              </div>
              <div class="recipe-input d-flex justify-center">
                <v-file-input
                  v-model="step.image"
                  outlined
                  accept="image/png, image/jpeg, image/bmp"
                  placeholder="단계 사진 업로드"
                  prepend-icon="mdi-camera"
                  @change="step_Preview_image(i)"
                ></v-file-input>
              </div>
              <div class="d-flex justify-center">
                <v-img
                  v-if="step.imageUrl != null"
                  max-width="50%"
                  :src="step.imageUrl"
                  class="d-flex justify-center"
                ></v-img>
              </div>
              <div class="recipe-input d-flex justify-center">
                <v-textarea
                  v-model="step.content"
                  class="text-md-h6"
                  auto-grow
                  filled
                  placeholder="예) 소고기는 기름기를 떼어내고 적당한 크기로 썰어주세요."
                  :rules="rules"
                  hide-details="auto"
                  outlined
                  color="#aac821"
                ></v-textarea>
              </div>
            </div>
            <div class="d-flex justify-center">
              <v-btn dark class="mt-4 mb-6" @click="addStep">스탭 추가</v-btn>
            </div>
            <v-divider></v-divider>
            <v-divider></v-divider>
            <v-card-title class="recipe-component">태그</v-card-title>
            <div class="d-flex justify-center">
              <v-input>
                <hash-tags @setTags="setTags"></hash-tags>
              </v-input>
            </div>
            <br />

            <div class="d-flex justify-center">
              <v-btn dark class="mr-6 ml-6 mb-5" @click="saveRecipe()"
                >저장</v-btn
              >
              <v-btn dark class="mr-6 ml-6 mb-5" @click="saveRecipe()"
                >저장 후 공개</v-btn
              >
              <v-btn dark class="mr-6 ml-6 mb-5" @click="moveBack">취소</v-btn>
            </div>
          </v-card>
        </v-container>
      </div>
    </v-app>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import HashTags from '@/components/Hashtags.vue'

export default {
  name: 'CreateRecipe',
  components: {
    HashTags,
  },
  data() {
    return {
      title: '',
      image: null,
      url: null,
      content: '',
      category: '',
      caregoryItems: [
        '밥요리',
        '국 탕',
        '찌개 전골',
        '밑반찬',
        '볶음요리',
        '구이(고기/생선)',
        '찜 조림',
        '손님상',
        '아이반찬',
        '김치 장아찌',
        '도시락',
        '튀김',
        '면요리',
        '샐러드',
        '김밥 초밥',
        '야식 술안주',
        '스파게티',
        '간식 분식',
        '토스트 샌드위치',
        '베이킹',
        '디저트',
        '주스 음료',
        '술 칵테일',
        '명절요리',
        '기타요리',
      ],
      categories: {
        밥요리: 'R0001',
        '국 탕': 'R0002',
        '찌개 전골': 'R0003',
        밑반찬: 'R0004',
        볶음요리: 'R0005',
        '구이(고기/생선)': 'R0006',
        '찜 조림': 'R0007',
        손님상: 'R0008',
        아이반찬: 'R0009',
        '김치 장아찌': 'R0010',
        도시락: 'R0011',
        튀김: 'R0012',
        면요리: 'R0013',
        샐러드: 'R0014',
        '김밥 초밥': 'R0015',
        '야식 술안주': 'R0016',
        스파게티: 'R0017',
        '간식 분식': 'R0018',
        '토스트 샌드위치': 'R0019',
        베이킹: 'R0020',
        디저트: 'R0021',
        '주스 음료': 'R0022',
        '술 칵테일': 'R0023',
        명절요리: 'R0024',
        기타요리: 'R0025',
      },

      cookingTime: '',
      serving: '',
      rules: [(value) => !!value || '필수 입력값입니다.'],
      keyword_rule: [(v) => !!v || '재료는 필수 입력사항입니다.'],
      ingre_rule: [
        (v) => !!v || '재료량은 필수 입력사항입니다.',
        (v) => /^[0-9]*$/.test(v) || '재료량은 숫자만 입력 가능합니다.',
        (v) => !(v <= 0) || '재료량은 0 이상이어야 합니다.',
      ],
      stepUrl: [],
      stepImage: [],
      steps: [{ no: 1, image: null, imageUrl: null, content: '' }],
      tags: ['바부'],
      dialog: false,
      isLoading: false,
      keyword: null,
      search: null,
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

      amount: '',
      headers: [
        {
          text: '재료명',
          align: 'start',
          sortable: false,
          value: 'name',
        },
        { text: '재료양', value: 'amount' },
        { text: '단위', value: 'unit' },
      ],
      ingredients: [],
      editedIndex: -1,
      editedItem: {
        name: '',
        id: '',
        amount: null,
        unit: 'g',
      },
      defaultItem: {
        name: '',
        id: '',
        amount: null,
        unit: 'g',
      },
      stepNum: 1,
      IngreValid: false,
    }
  },
  computed: {
    ...mapState('ingredients', ['ingredientsList']),
    ...mapState('recipe', ['recipeDetail']),
    ...mapState('user', ['userid']),
    formTitle() {
      return this.editedIndex === -1 ? '재료 추가' : '재료 수정'
    },
    form() {
      return {
        keyword: this.keyword,
        amount: this.editedItem.amount,
      }
    },
  },

  watch: {
    dialog(val) {
      val || this.close()
    },
  },
  created() {
    this.initialize()
  },
  methods: {
    ...mapActions('ingredients', ['searchIngredient']),
    ...mapActions('recipe', [
      'RecipeDetail',
      'createRecipeDetail',
      'updateRecipeDetail',
      'patchRecipeDetail',
    ]),
    Preview_image() {
      if (this.image != null) {
        this.url = URL.createObjectURL(this.image)
        console.log(this.url)
      } else {
        this.url = null
      }
    },
    step_Preview_image(index) {
      if (this.steps[index].image != null) {
        this.steps[index].imageUrl = URL.createObjectURL(
          this.steps[index].image
        )
      } else {
        this.steps[index].imageUrl = null
      }
    },
    addStep() {
      this.stepNum = this.stepNum + 1
      const newStep = {
        no: this.stepNum,
        image: null,
        imageUrl: null,
        content: '',
      }
      this.steps.push(newStep)
      console.log(this.steps)
    },
    deleteStep(index) {
      if (this.stepNum > 1) {
        this.stepNum = this.stepNum - 1
        this.steps.splice(index, 1)

        for (let i = index; i < this.steps.length; i++) {
          this.steps[i].no = this.steps[i].no - 1
        }
      }
    },
    // Preview_image(file) {
    //   if (file) {
    //     const fileData = (data) => {
    //       this.image = data
    //     }
    //     this.fileInfo = file
    //     const reader = new FileReader()
    //     reader.readAsDataURL(file)
    //     reader.addEventListener(
    //       'load',
    //       function () {
    //         fileData(reader.result)
    //       },
    //       false
    //     )
    //     this.stepImage.push(this.image)
    //   } else if (file === null) {
    //     this.fileInfo = null
    //     this.image = '/banner/no-image.png'
    //     this.stepImage.push(this.image)
    //   } else {
    //     file = this.fileInfo
    //     const fileData = (data) => {
    //       this.image = data
    //     }
    //     const reader = new FileReader()
    //     reader.readAsDataURL(file)
    //     reader.addEventListener(
    //       'load',
    //       function () {
    //         fileData(reader.result)
    //       },
    //       false
    //     )
    //     this.stepImage.push(this.image)
    //   }
    // },
    initialize() {
      this.ingredients = []
    },
    clearItem() {
      this.keyword = null
    },
    editItem(item) {
      this.search = item.name
      this.editedIndex = this.ingredients.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialog = true
    },
    deleteItem(item) {
      this.editedIndex = this.ingredients.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.ingredients.splice(this.editedIndex, 1)
      this.closeDelete()
    },
    close() {
      this.dialog = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
    },

    closeDelete() {
      this.dialogDelete = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
    },
    saveIngre() {
      if (this.$refs.form.validate()) {
        console.log(this.$refs.form.validate())

        this.IngreValid = false
        this.ingredients?.forEach((ingre) => {
          if (this.editedItem.name === ingre.name) {
            ingre.amount =
              parseInt(ingre.amount) + parseInt(this.editedItem.amount)
            this.IngreValid = true
          }
        })
        if (this.IngreValid === false) {
          if (this.editedIndex > -1) {
            Object.assign(this.ingredients[this.editedIndex], this.editedItem)
          } else {
            this.ingredients.push(this.editedItem)
          }
        }

        if (this.editedIndex > -1) {
          Object.assign(this.ingredients[this.editedIndex], this.editedItem)
        }
        // if (this.editedIndex > -1) {
        //   Object.assign(this.ingredients[this.editedIndex], this.editedItem)
        // } else {
        //   this.ingredients.push(this.editedItem)
        // }

        this.close()
      }
    },
    setTags(tags) {
      this.tags = tags
      console.log(this.tags)
    },
    ingre(keyword) {
      if (keyword != null && keyword.length > 0) {
        keyword = keyword.trim()
        if (keyword.length > 0 && keyword != null) {
          this.searchIngredient(keyword)
          console.log(keyword)
        }
      }
    },
    selectIngre(item) {
      this.editedItem.name = item.name
      this.editedItem.id = item.id
      this.editedItem.unit = item.measure
    },
    saveRecipe() {
      // console.log(this.ingredients)
      const formdata = new FormData()
      formdata.append('title', this.title)
      formdata.append('content', this.content)
      formdata.append('category', this.category)
      formdata.append('cookingTime', this.cookingTime)
      formdata.append('serving', this.serving)
      formdata.append('repImg', this.image)
      for (let i = 0; i < this.steps.length; i++) {
        formdata.append(`stepDtoList[${i}].step`, this.steps[i].no)
        formdata.append(`stepDtoList[${i}].content`, this.steps[i].content)
        formdata.append(`stepDtoList[${i}].img`, this.steps[i].image)
      }
      for (let i = 0; i < this.ingredients.length; i++) {
        formdata.append(`ingredients[${i}].id`, this.ingredients[i].id)
        formdata.append(`ingredients[${i}].amount`, this.ingredients[i].amount)
      }
      // console.log('이거슨' + this.ingredients.length)
      // console.log('이거슨' + this.ingredients)
      const ingreVal = []
      const keys = Object.keys(this.tags)
      // console.log(keys)
      for (let i = 0; i < keys.length; i++) {
        ingreVal.push(this.tags[keys[i]].value)
      }
      for (let i = 0; i < this.tags.length; i++) {
        formdata.append(`tagList[${i}]`, ingreVal[i])
      }
      for (const p of formdata.entries()) {
        console.log(p[0] + ',' + p[1])
      }
      console.log(formdata)
      this.createRecipeDetail(formdata)
    },
    moveBack() {
      this.$router.go(-1)
    },
  },
}
</script>

<style scoped>
.create-container {
  position: sticky;
  height: 100%;

  padding: 4%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);

  color: black;
}
.recipe-input {
  background-color: white;
  /* border: 0.5px solid #aac821; */
  margin-bottom: 3%;
  margin-top: 3%;
  border-radius: 10px;
}
.v-input {
  max-width: 90%;
}
.recipe-component {
  padding-bottom: 0px;
}
.recipe-input-content {
  max-width: 40% !important;
}
</style>
