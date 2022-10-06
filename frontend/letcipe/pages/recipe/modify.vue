<template>
  <div id="app">
    <v-app id="inspire">
      <div class="makerecipe-container">
        <v-container style="padding: 0%">
          <v-card-title
            class="justify-center"
            style="background-color: white; font-size: 1.7rem"
            >레시피 수정</v-card-title
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
                ref="repImg"
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
                :items="categoryItems"
                outlined
                placeholder="카테고리 선택"
              >
                <template #selection="{ item }">
                  <img style="width: 30px" :src="item.img" />{{
                    item.name
                  }}</template
                >
                <template #item="{ item }">
                  <img style="width: 30px" :src="item.img" />&nbsp;{{
                    item.name
                  }}
                </template>
              </v-select>
            </div>

            <v-card-title class="recipe-component">추가 정보</v-card-title>
            <div class="d-flex justify-center mr-5 ml-5">
              <v-card-subtitle class="d-flex justify-left align-center">
                조리 시간&nbsp;&nbsp;&nbsp;
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
                요리량&nbsp;&nbsp;&nbsp;
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
                      <v-icon @click="editItem(item)">mdi-pencil</v-icon>
                      <v-icon @click="deleteItem(item)">mdi-delete</v-icon>
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
                    <span class="text-h5">{{ formTitle }}</span>
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
                <v-icon class="mr-5" color="red" @click="deleteStep(step, i)"
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
            <div class="d-flex ma-5">
              <div v-if="tags.length > 0" class="tags">
                <input ref="fake" type="text" class="fake" />
                <span
                  v-for="(t, index) in tags"
                  :key="index"
                  class="tag active"
                >
                  {{ t }}
                  <v-icon size="small" color="white" @click="deleteTag(index)"
                    >mdi-close</v-icon
                  >
                </span>
              </div>
            </div>
            <div class="d-flex justify-center">
              <v-input>
                <hash-tags @setTags="setTags"></hash-tags>
              </v-input>
            </div>
            <br />

            <div class="d-flex justify-center">
              <v-btn dark class="mr-6 ml-6 mb-5" @click="canSave()">저장</v-btn>
              <v-btn dark class="mr-6 ml-6 mb-5" @click="canSave()"
                >저장 후 공개</v-btn
              >
              <v-btn dark class="mr-6 ml-6 mb-5" @click="moveBack">취소</v-btn>
            </div>
            <v-snackbar
              v-model="saveSnackBar"
              style="z-index: 100; margin-bottom: 60px"
              :timeout="1500"
            >
              {{ snackBarMsg }}
            </v-snackbar>
          </v-card>
        </v-container>
      </div>
    </v-app>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'

import HashTags from '@/components/Hashtags.vue'
// const convertURIToImageData = (url) => {
//   return new Promise((resolve, reject) => {
//     if (!url) {
//       return reject
//     }
//     const canvas = document.createElement('canvas')
//     const context = canvas.getContext('2d')
//     const image = new Image()
//     image.onload = () => {
//       canvas.width = image.width
//       canvas.height = image.height
//       context.drawImage(image, 0, 0, canvas.width, canvas.height)
//       resolve(context.getImageData(0, 0, canvas.width, canvas.height))
//     }
//     image.crossOrigin = 'Anonymous'
//     image.src = url
//   })
// }

export default {
  name: 'ModifyRecipe',
  components: { HashTags },
  data() {
    return {
      title: '',
      image: null,
      url: null,
      content: '',
      category: '',
      categoryItems: [
        {
          name: '밥요리',
          img: 'https://img.icons8.com/emoji/344/cooked-rice-emoji.png',
          category: 'R0001',
        },
        {
          name: '국 탕',
          img: 'https://img.icons8.com/emoji/344/pot-of-food-emoji.png',
          category: 'R0002',
        },
        {
          name: '찌개 전골',
          img: 'https://img.icons8.com/emoji/344/shallow-pan-of-food-emoji.png',
          category: 'R0003',
        },
        {
          name: '밑반찬',
          img: 'https://img.icons8.com/color/344/petri-dish.png',
          category: 'R0004',
        },
        {
          name: '볶음요리',
          img: 'https://img.icons8.com/external-vitaliy-gorbachev-flat-vitaly-gorbachev/344/external-pan-camping-vitaliy-gorbachev-flat-vitaly-gorbachev.png',
          category: 'R0005',
        },
        {
          name: '구이(고기/생선)',
          img: 'https://img.icons8.com/flat-round/2x/fish.png',
          category: 'R0006',
        },
        {
          name: '찜 조림',
          img: 'https://img.icons8.com/external-basicons-color-edtgraphics/344/external-household-cookware-basicons-color-edtgraphics-3.png',
          category: 'R0007',
        },
        {
          name: '손님상',
          img: 'https://img.icons8.com/color/2x/food-bar.png',
          category: 'R0008',
        },
        {
          name: '아이반찬',
          img: 'https://img.icons8.com/emoji/344/baby-light-skin-tone.png',
          category: 'R0009',
        },
        {
          name: '김치 장아찌',
          img: 'https://img.icons8.com/external-justicon-flat-justicon/344/external-kimchi-korea-justicon-flat-justicon.png',
          category: 'R0010',
        },
        {
          name: '도시락',
          img: 'https://img.icons8.com/external-photo3ideastudio-flat-photo3ideastudio/2x/external-bento-japan-photo3ideastudio-flat-photo3ideastudio.png',
          category: 'R0011',
        },
        {
          name: '튀김',
          img: 'https://img.icons8.com/external-flat-gradient-andi-nur-abdillah/344/external-fried-food-flat-gradient-flat-gradient-andi-nur-abdillah-2.png',
          category: 'R0012',
        },
        {
          name: '면요리',
          img: 'https://img.icons8.com/external-nawicon-flat-nawicon/2x/external-noodle-fast-food-nawicon-flat-nawicon.png',
          category: 'R0013',
        },
        {
          name: '샐러드',
          img: 'https://img.icons8.com/emoji/344/green-salad-emoji.png',
          category: 'R0014',
        },
        {
          name: '김밥 초밥',
          img: 'https://img.icons8.com/external-sbts2018-flat-sbts2018/344/external-sushi-fast-food-sbts2018-flat-sbts2018.png',
          category: 'R0015',
        },
        {
          name: '야식 술안주',
          img: 'https://img.icons8.com/external-wanicon-flat-wanicon/344/external-chicken-takeaway-wanicon-flat-wanicon.png',
          category: 'R0016',
        },
        {
          name: '스파게티',
          img: 'https://img.icons8.com/emoji/344/spaghetti-emoji.png',
          category: 'R0017',
        },
        {
          name: '간식 분식',
          img: 'https://img.icons8.com/external-flaticons-flat-flat-icons/344/external-tteokbokki-world-cuisine-flaticons-flat-flat-icons.png',
          category: 'R0018',
        },
        {
          name: '토스트 샌드위치',
          img: 'https://img.icons8.com/emoji/344/sandwich-emoji.png',
          category: 'R0019',
        },
        {
          name: '베이킹',
          img: 'https://img.icons8.com/emoji/344/bread-emoji.png',
          category: 'R0020',
        },
        {
          name: '디저트',
          img: 'https://img.icons8.com/color/344/dessert.png',
          category: 'R0021',
        },
        {
          name: '주스 음료',
          img: 'https://img.icons8.com/color-glass/344/carton-of-orange-juice.png',
          category: 'R0022',
        },
        {
          name: '술 칵테일',
          img: 'https://img.icons8.com/fluency/344/beer.png',
          category: 'R0023',
        },
        {
          name: '명절요리',
          img: 'https://img.icons8.com/external-flat-wichaiwi/344/external-food-soft-power-flat-wichaiwi.png',
          category: 'R0024',
        },
        {
          name: '기타요리',
          img: 'https://img.icons8.com/fluency/344/bagel.png',
          category: 'R0025',
        },
      ],
      cookingTime: '',
      serving: '',
      rules: [(value) => !!value || 'Required.'],
      keyword_rule: [(v) => !!v || '재료는 필수 입력사항입니다.'],
      ingre_rule: [
        (v) => !!v || '재료량은 필수 입력사항입니다.',
        (v) => /^[0-9]*$/.test(v) || '재료량은 숫자만 입력 가능합니다.',
        (v) => !(v <= 0) || '재료량은 0 이상이어야 합니다.',
      ],
      stepUrl: [],
      stepImage: [],
      steps: [],
      tags: [],
      focusIndex: null,
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
      snackBarMsg: '',
      saveSnackBar: false,
    }
  },
  computed: {
    ...mapState('ingredients', ['ingredientsList']),
    ...mapState('recipe', ['recipeDetail', 'recipeID']),
    formTitle() {
      return this.editedIndex === -1 ? '재료 추가' : '재료 수정'
    },
  },

  watch: {
    dialog(val) {
      val || this.close()
    },
  },
  created() {
    this.initialize()
    const promise = new Promise((resolve, reject) => {
      resolve()
    })
    promise.then(async () => {
      // 1793

      await this.RecipeDetail(this.recipeID) // 1591 자리에 recipelist id 넘겨 받으면 돼.

      console.log(this.recipeDetail)
      if (this.recipeDetail !== null) {
        this.title = this.recipeDetail.title
        this.url = this.recipeDetail.repImg
        this.content = this.recipeDetail.content
        this.category = this.recipeDetail.category
        this.cookingTime = this.recipeDetail.cookingTime
        this.serving = this.recipeDetail.serving
        // this.steps = this.recipeDetail.recipeSteps
        // console.log(this.steps)
        console.log('이것')
        this.recipeDetail.recipeSteps.forEach((rs) => {
          const step = {
            no: rs.step,
            image: null,
            imageUrl: rs.img,
            content: rs.content,
          }
          this.steps.push(step)
        })
        // console.log(this.steps)
        console.log(this.recipeDetail)
        // console.log(this.recipeDetail.ingredients.length)
        // console.log(this.recipeDetail.ingredients[0])

        for (let i = 0; i < this.recipeDetail.ingredients.length; i++) {
          const ingre = {
            name: this.recipeDetail.ingredients[i].ingredient.name,
            id: this.recipeDetail.ingredients[i].ingredient.id,
            amount: this.recipeDetail.ingredients[i].amount,
            unit: this.recipeDetail.ingredients[i].ingredient.measure,
          }
          this.ingredients.push(ingre)
        }

        this.recipeDetail.tags?.forEach((t) => {
          this.tags.push(t.name)
        })
      }
    })
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
    deleteStep(step, index) {
      if (step.no > 1) {
        step.no = step.no - 1
        this.steps.splice(index, 1)

        for (let i = index; i < this.steps.length; i++) {
          this.steps[i].no = this.steps[i].no - 1
        }
      }
    },
    initialize() {
      this.ingredients = []
    },
    clearItem() {
      this.keyword = null
      this.search = null
    },
    editItem(item) {
      console.log(item)
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
      this.tags.push(tags[0].value)
      console.log('현재태그')
      console.log(tags)
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
    deleteTag(idx) {
      if (idx === null) {
        return
      }
      this.initSelectIndex()
      this.tags.splice(idx, 1)
    },
    initSelectIndex() {
      this.focusIndex = null
    },
    async canSave() {
      const sleep = (milliseconds) => {
        return new Promise((resolve) => setTimeout(resolve, milliseconds))
      }
      let msg = ''
      let flag = false
      if (this.title.trim() === '') {
        msg = '레시피 제목을 입력해주세요.'
      } else if (this.content.trim() === '') {
        msg = '레시피 내용을 입력해주세요.'
      } else if (this.image === null && this.imageUrl === '') {
        msg = '대표 사진을 입력해주세요.'
      } else if (this.serving === '') {
        msg = '몇 인분인지 입력해주세요.'
      } else if (this.category === '') {
        msg = '카테고리를 입력해주세요.'
      } else if (this.steps.length <= 0) {
        msg = ''
      } else if (
        this.steps[this.steps.length - 1].imageUrl == null ||
        this.steps[this.steps.length - 1].imageUrl === ''
      ) {
        msg = this.steps.length + '단계 이미지를 입력해주세요'
      } else if (this.steps[this.steps.length - 1].content.trim() === '') {
        msg = this.steps.length + '단계 내용을 입력해주세요'
      } else {
        flag = true
        msg = '성공적으로 레시피가 저장되었습니다!'
      }
      this.snackBarMsg = msg
      if (!flag) this.saveSnackBar = true
      if (flag) {
        await this.saveRecipe()
        this.saveSnackBar = true
        await sleep(1000)
        this.$router.push('/user/recipe')
      }
    },
    saveRecipe() {
      // console.log(this.ingredients)
      const formdata = new FormData()
      console.log(this.category.category)
      formdata.append('title', this.title)
      formdata.append('content', this.content)
      formdata.append('cookingTime', this.cookingTime)
      formdata.append('serving', this.serving)
      formdata.append('category', this.category.category)

      if (this.image === null) {
        // formdata.append('repImg', this.image)
        formdata.append('repImgUrl', this.url)
      } else {
        formdata.append('repImg', this.image)
      }
      console.log(this.steps.length)
      for (let i = 0; i < this.steps.length; i++) {
        formdata.append(`stepDtoList[${i}].step`, this.steps[i].no)
        if (this.steps[i].image === null) {
          // formdata.append(`stepDtoList[${i}].img`, this.steps[i].image);
          formdata.append(`stepDtoList[${i}].imgUrl`, this.steps[i].imageUrl)
        } else if (this.steps[i].image != null) {
          formdata.append(`stepDtoList[${i}].img`, this.steps[i].image)
          console.log(this.steps[i].image)
        }
        formdata.append(`stepDtoList[${i}].content`, this.steps[i].content)
      }
      for (let i = 0; i < this.ingredients.length; i++) {
        formdata.append(`ingredients[${i}].id`, this.ingredients[i].id)
        formdata.append(`ingredients[${i}].amount`, this.ingredients[i].amount)
      }
      // console.log(formdata)
      // const ingreVal = []
      // const keys = Object.keys(this.tags)
      // // console.log(keys)
      // for (let i = 0; i < keys.length; i++) {
      //   ingreVal.push(this.tags[keys[i]].value)
      // }
      // for (let i = 0; i < this.tags.length; i++) {
      //   formdata.append(`tagList[${i}]`, ingreVal[i])
      // }
      const ingreVal = []
      // console.log(keys)
      for (let i = 0; i < this.tags.length; i++) {
        ingreVal.push(this.tags[i])
      }
      if (this.tags.length > 0) {
        console.log('태그있어요')
        console.log(ingreVal)
        for (let i = 0; i < this.tags.length; i++) {
          formdata.append(`tagList[${i}]`, ingreVal[i])
        }
      } else {
        console.log('태그없음')
        formdata.append(`tagList`, [''])
      }

      for (const p of formdata.entries()) {
        console.log(p[0] + ',' + p[1])
      }
      console.log(formdata)
      const object = {
        recipeId: this.recipeID,
        formData: formdata,
      }
      this.updateRecipeDetail(object)
      // console.log(JSON.stringify(object.formData))
    },
    moveBack() {
      this.$router.go(-1)
    },
  },
}
</script>

<style lang="scss" scoped>
.modify-container {
  position: sticky;
  height: 100%;

  padding: 8% 0% 0% 0%;
  background-image: url('/bg/bg_img.png');
  background-repeat: repeat;

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

.tags {
  position: relative;
  overflow: hidden;
  display: inline-block;
  vertical-align: top;
  margin-bottom: -6px;

  .fake {
    position: absolute;
    width: 1px;
    height: 1px;
    left: -1px;
    right: -1px;
    padding: 0;
    border: 0;
    outline: none;
    -webkit-appearance: none;
    -webkit-text-size-adjust: none;
  }
  .tag {
    display: inline-block;
    position: relative;
    margin: 0 5px 6px 0;
    padding: 0 5px;
    line-height: 30px;
    border-radius: 5px;
    background-color: black;
    vertical-align: top;
    word-wrap: break-word;
    word-break: break-all;
    font-size: 13px;
    text-align: left;
    &:hover:after {
      display: block;
      position: absolute;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      box-sizing: border-box;
      border: 1px solid black;
      content: '';
      border-radius: 5px;
    }

    &:before {
      display: inline;
      content: '#';
    }

    &.active {
      background-color: #656565;
      color: #fff;
      &:hover:after {
        display: none;
      }
    }
  }
}
</style>
