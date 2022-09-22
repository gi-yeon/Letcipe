<template>
  <div id="app">
    <v-app id="inspire">
      <div class="makerecipe-container">
        <v-container style="padding: 0%"
          ><v-card-title
            class="justify-center"
            style="background-color: white; font-size: 1.7rem"
            >레시피 등록</v-card-title
          ><v-divider></v-divider
          ><v-card
            ><v-card-title class="recipe-component">레시피 제목</v-card-title>
            <div class="recipe-input d-flex justify-center">
              <v-text-field
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
                class="text-md-h6"
                :items="items"
                outlined
                placeholder="카테고리 선택"
              ></v-select>
            </div>

            <v-card-title class="recipe-component">추가 정보</v-card-title>
            <div class="d-flex justify-center mr-5 ml-5">
              <v-card-subtitle class="d-flex justify-left align-center"
                >조리 시간&nbsp;&nbsp;&nbsp;<v-text-field
                  class="recipe-input-content text-md-h6"
                  filled
                  hide-details="auto"
                  outlined
                  color="#aac821"
                ></v-text-field
                >&nbsp;분 </v-card-subtitle
              ><v-card-subtitle class="d-flex justify-left align-center"
                >요리량&nbsp;&nbsp;&nbsp;<v-text-field
                  class="recipe-input-content text-md-h6"
                  filled
                  hide-details="auto"
                  outlined
                  color="#aac821"
                ></v-text-field
                >&nbsp;인분</v-card-subtitle
              >
            </div>
            <v-divider></v-divider><v-divider></v-divider
            ><v-card-title class="recipe-component">재료</v-card-title>
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
                      <v-icon @click="editItem(item)">mdi-pencil</v-icon
                      ><v-icon @click="deleteItem(item)"> mdi-delete</v-icon>
                    </th>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
            <div class="d-flex justify-center">
              <v-dialog v-model="dialog" max-width="500px">
                <template #activator="{ on, attrs }">
                  <v-btn dark class="mb-5 mt-6" v-bind="attrs" v-on="on">
                    재료 추가
                  </v-btn>
                </template>

                <v-card>
                  <v-card-title>
                    <span class="text-h5">{{ formTitle }}</span>
                  </v-card-title>

                  <v-card-text>
                    <v-container>
                      <v-row>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field
                            v-model="editedItem.name"
                            label="재료명"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field
                            v-model="editedItem.amount"
                            label="재료량"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field
                            v-model="editedItem.unit"
                            label="단위"
                          ></v-text-field>
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-card-text>

                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="close">
                      취소
                    </v-btn>
                    <v-btn color="blue darken-1" text @click="save">
                      재료 저장
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </div>
            <v-divider></v-divider><v-divider></v-divider>
            <v-card-title class="recipe-component">요리 순서</v-card-title>
            <div v-for="(step, i) in steps" :key="i">
              <div class="d-flex justify-space-between">
                <v-card-title class="text-subtitle-2 recipe-component"
                  >Step {{ step.no }}</v-card-title
                ><v-icon class="mr-5" color="red" @click="deleteStep(i)"
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
                  @change="Preview_image(i)"
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
            <v-divider></v-divider><v-divider></v-divider>
            <v-card-title class="recipe-component">태그 </v-card-title>
            <div class="d-flex justify-center">
              <v-input><hash-tags @setTags="setTags"></hash-tags></v-input>
            </div>
            <br />

            <div class="d-flex justify-center">
              <v-btn dark class="mr-6 ml-6 mb-5">저장</v-btn
              ><v-btn dark class="mr-6 ml-6 mb-5">저장 후 공개</v-btn
              ><v-btn dark class="mr-6 ml-6 mb-5">취소</v-btn>
            </div></v-card
          ></v-container
        >
      </div></v-app
    >
  </div>
</template>

<script>
import HashTags from '@/components/Hashtags.vue'

export default {
  name: 'MakeRecipe',
  components: {
    HashTags,
  },
  data() {
    return {
      rules: [(value) => !!value || 'Required.'],
      items: ['카테고리1', '카테고리2', '카테고리3', '카테고리4'],
      stepUrl: [],
      stepImage: [],
      steps: [{ no: 1, image: null, imageUrl: null, content: '' }],
      url: null,
      tags: ['바부'],
      image: null,
      dialog: false,

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
        amount: 0,
        unit: 'g',
      },
      defaultItem: {
        name: '',
        amount: 0,
        unit: 'g',
      },
      stepNum: 1,
    }
  },
  computed: {
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
  },
  methods: {
    Preview_image(index) {
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

    editItem(item) {
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

    save() {
      if (this.editedIndex > -1) {
        Object.assign(this.ingredients[this.editedIndex], this.editedItem)
      } else {
        this.ingredients.push(this.editedItem)
      }
      this.close()
    },
    setTags(tags) {
      this.tags = tags
      console.log(this.tags)
    },
  },
}
</script>

<style scoped>
.makerecipe-container {
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
</style>
