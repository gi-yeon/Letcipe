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
                :items="categories"
                outlined
                placeholder="카테고리 선택"
              ></v-select>
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
                            ></v-text-field> -->
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
                                  <v-list-item-title
                                    >일치하는 재료가
                                    없습니다.</v-list-item-title
                                  >
                                </v-list-item>
                              </template>
                              <template #item="{ item }">
                                <v-list-item-content @click="selectIngre(item)">
                                  <v-list-item-title
                                    v-text="item.name"
                                  ></v-list-item-title>
                                </v-list-item-content>
                                <v-list-item-action @click="selectIngre(item)">
                                  <v-chip :color="colors[item.category]" label>
                                    {{ item.category }}
                                  </v-chip>
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
            <div class="d-flex ma-5">
              <div v-if="tags.length > 0" class="tags">
                <input ref="fake" type="text" class="fake" />
                <span v-for="(t, index) in tags" :key="index" class="tag active"
                  >{{ t
                  }}<v-icon size="small" color="white" @click="deleteTag(index)"
                    >mdi-close</v-icon
                  ></span
                >
              </div>
            </div>
            <div class="d-flex justify-center">
              <v-input>
                <hash-tags @setTags="setTags"> </hash-tags>
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
const convertURIToImageData = (url) => {
  return new Promise((resolve, reject) => {
    if (!url) {
      return reject
    }
    const canvas = document.createElement('canvas')
    const context = canvas.getContext('2d')
    const image = new Image()
    image.onload = () => {
      canvas.width = image.width
      canvas.height = image.height
      context.drawImage(image, 0, 0, canvas.width, canvas.height)
      resolve(context.getImageData(0, 0, canvas.width, canvas.height))
    }
    image.crossOrigin = 'Anonymous'
    image.src = url
  })
}

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
      categories: ['R0001', 'R0002'],
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
      tags: ['바부'],
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
    }
  },
  computed: {
    ...mapState('ingredients', ['ingredientsList']),
    ...mapState('recipe', ['recipeDetail']),
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

      await this.RecipeDetail(1798) // 1591 자리에 recipelist id 넘겨 받으면 돼.

      console.log(this.recipeDetail)
      if (this.recipeDetail !== null) {
        this.title = this.recipeDetail.title
        this.url = convertURIToImageData(this.recipeDetail.repImg)
        this.content = this.recipeDetail.content
        this.category = this.recipeDetail.category
        this.cookingTime = this.recipeDetail.cookingTime
        this.serving = this.recipeDetail.serving
        // this.steps = this.recipeDetail.recipeSteps
        // console.log(this.steps)
        console.log('이것' + this.url)
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
      console.log(tags)
      this.tags.push(tags[0].value)
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
    saveRecipe() {
      // console.log(this.ingredients)
      const formdata = new FormData()
      formdata.append('title', this.title)
      formdata.append('content', this.content)
      formdata.append('cookingTime', this.cookingTime)
      formdata.append('serving', this.serving)

      formdata.append('repImg', this.image)

      console.log(this.steps.length)
      for (let i = 0; i < this.steps.length; i++) {
        if (this.steps[i].image === null) {
          formdata.append(
            `stepDtoList[${i}].step[${i}].img`,
            this.steps[i].image
          )
        } else if (this.steps[i].image != null) {
          formdata.append(`stepDtoList[${i}].step[${i}].img`, this.steps[i].img)
        }
        formdata.append(
          `stepDtoList[${i}].step[${i}].content`,
          this.steps[i].content
        )
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
      for (let i = 0; i < this.tags.length; i++) {
        formdata.append(`tagList[${i}]`, this.tags[i])
      }

      for (const p of formdata.entries()) {
        console.log(p[0] + ',' + p[1])
      }
      console.log(formdata)

      this.updateRecipeDetail(1798, formdata)
    },
    moveBack() {
      this.$router.push('/main')
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
