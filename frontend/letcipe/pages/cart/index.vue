<template>
  <div id="app">
    <v-app class="cart-page">
      <v-container class="cart-container d-flex-row" style="position: absoulte">
        <div class="cart-head-wrap">
          <div class="d-flex justify-space-between pb-3">
            <div>
              <v-icon style="z-index: 2" @click="moveBack"
                >mdi-window-close</v-icon
              >
            </div>
            <div style="font-size: x-large">장바구니</div>
            <div>
              <v-icon style="color: white">mdi-window-close</v-icon>
            </div>
          </div>
          <div
            class="recipelist-title-btn d-flex justify-space-between pb-7"
            @click="moveWirte"
          >
            <div>레시피리스트 제목을 입력해주세요</div>
            <v-icon>mdi-chevron-right</v-icon>
          </div>
          <div class="d-flex justify-space-between">
            <div class="d-flex align-items-center">
              <div v-if="!isAllCheck" style="z-index: 2" @click="allCheck">
                <v-icon>mdi-check-circle-outline</v-icon>전체 선택
              </div>
              <div v-else style="z-index: 2" @click="allCheck">
                <v-icon>mdi-check-circle</v-icon>전체 선택
              </div>
            </div>
            <div>
              <v-btn
                style="z-index: 2"
                small
                color="letcipe"
                class="white--text"
                @click="checkedDeleteAlert"
                >선택 삭제</v-btn
              >
            </div>
          </div>
        </div>
        <v-spacer></v-spacer>
        <div class="cart-recipe-wrap fadeInUp">
          <div class="d-flex pa-2">
            <v-icon>mdi-cart</v-icon>
            <div>내가 담은 레시피</div>
          </div>
          <v-divider></v-divider>
          <div v-if="cart == null || cart.length === 0">
            <!-- <div class="d-flex justify-center">
              <v-img src="/cart_icon/담은카트.png" size="10"></v-img>
            </div> -->
            <div class="d-flex justify-center pt-3 pb-3">
              담긴 레시피가 없습니다.
            </div>
          </div>
          <div v-for="(recipeInfo, i) in cart" v-else :key="i">
            <v-list-item
              three-line
              style="background-color: white"
              class="pl-3 pr-3"
              @click="moveDetail(recipeInfo)"
            >
              <v-icon
                v-if="!checkedRecipe[i]"
                style="z-index: 2"
                class="mr-3"
                @click="addRecipe(i)"
                >mdi-check-circle-outline</v-icon
              >
              <v-icon
                v-else
                class="mr-3"
                style="z-index: 2"
                @click="addRecipe(i)"
                >mdi-check-circle</v-icon
              >
              <v-list-item-avatar tile size="100">
                <v-img :src="recipeInfo.recipe.repImg"></v-img>
              </v-list-item-avatar>
              <v-list-item-content class="pl-4">
                <v-list-item-title class="d-flex justify-space-between">
                  <div>{{ recipeInfo.recipe.title }}</div>
                  <v-icon
                    style="z-index: 2"
                    @click="deleteRecipe(recipeInfo.recipe.id)"
                    >mdi-window-close</v-icon
                  >
                </v-list-item-title>

                <v-list-item-subtitle
                  style="
                    text-overflow: ellipsis;
                    over-flow: hidden;
                    white-space: nowrap;
                    display: inline-block;
                  "
                  >{{ recipeInfo.recipe.content }}</v-list-item-subtitle
                >
                <div class="d-flex justify-space-between">
                  <div style="margin: auto 0">
                    <v-icon small color="pink lighten-1"
                      >mdi-cards-heart</v-icon
                    >
                    {{ recipeInfo.recipe.recipeLike }}
                  </div>
                  <!-- <v-list-item-subtitle style="text-align: right"
                    >선택한 재료 4개를 포함하고 있어요.</v-list-item-subtitle
                  >-->
                  <div>
                    <v-btn
                      elevation="3"
                      class="mx-2"
                      fab
                      dark
                      x-small
                      color="letcipe"
                      @click="subRecipeAmount(recipeInfo.recipe.id, i)"
                    >
                      <v-icon dark>mdi-minus</v-icon>
                    </v-btn>
                    {{ recipeInfo.amount }}
                    <v-btn
                      elevation="3"
                      class="mx-2"
                      fab
                      dark
                      x-small
                      color="letcipe"
                      @click="plusRecipeAmount(recipeInfo.recipe.id, i)"
                    >
                      <v-icon dark>mdi-plus</v-icon>
                    </v-btn>
                    <!-- <div class="d-flex justify-center">
                      <v-icon class="mx-2" color="letcipe"
                        >mdi-minus-circle</v-icon
                      >
                      <div style="margin: auto">{{ recipeInfo.amount }}</div>
                      <v-icon class="mx-2" color="letcipe"
                        >mdi-plus-circle</v-icon
                      >
                    </div>-->
                  </div>
                </div>
              </v-list-item-content>
            </v-list-item>

            <v-divider></v-divider>
          </div>
        </div>
        <div class="cart-ingredient-wrap fadeInUp">
          <div class="d-flex pa-2">
            <v-icon>mdi-cart</v-icon>
            <div>장보기 목록</div>
          </div>
          <v-divider></v-divider>
          <div v-if="ingreList == null || ingreList.length === 0">
            <!-- <div class="d-flex justify-center">
              <v-img src="/cart_icon/담은카트.png" size="10"></v-img>
            </div> -->
            <div class="d-flex justify-center pt-3 pb-3">
              담긴 재료가 없습니다.
            </div>
          </div>
          <div v-else class="ingre-wrap">
            <div
              v-for="(cg, index) in cartCategory"
              :key="index"
              class="ingre-category pl-3 pr-3"
            >
              <div class="d-flex justify-space-between align-center">
                <div class="d-flex justify-center mt-2 mb-2">
                  <img
                    class="category-images"
                    :src="`/cart_icon/${cg}.png`"
                    alt="flour"
                  />

                  <div>{{ cg }}</div>
                </div>
                <!-- <div class="d-flex align-center">
                        <div>전체선택</div>
                        <v-checkbox color="letcipe"></v-checkbox>
                      </div>-->
              </div>
              <v-divider></v-divider>
              <div v-for="(item, i) in ingreList" :key="i" class="pl-3 pr-3">
                <!-- <div
              v-if="item.amount > 0 && ingreList[i].ingredient.category === cg"
              class="d-flex justify-space-between pa-2"
            > -->
                <div
                  v-if="
                    item.amount > 0 && ingreList[i].ingredient.category === cg
                  "
                  class="check-wrap d-flex justify-space-between align-center mt-1"
                >
                  <!-- <div style="font-size: x-large"> -->
                  <div>{{ item.ingredient.name }}</div>
                  <div>
                    <!-- <div style="font-size: x-large"> -->
                    <div>
                      <v-btn
                        class="mx-2"
                        elevation="3"
                        fab
                        dark
                        x-small
                        color="letcipe"
                        @click="subIngreAmount(i)"
                      >
                        <v-icon dark>mdi-minus</v-icon>
                      </v-btn>
                      {{ Math.ceil(item.amount) }}{{ item.ingredient.measure }}
                      <v-btn
                        class="mx-2"
                        elevation="3"
                        fab
                        dark
                        x-small
                        color="letcipe"
                        @click="plusIngreAmount(i)"
                      >
                        <v-icon dark>mdi-plus</v-icon>
                      </v-btn>
                    </div>
                    <div style="float: right">
                      {{ Math.ceil(item.amount * item.ingredient.gml) }}
                      <span v-if="item.ingredient.measure == 'ml'">ml</span>
                      <span v-else>g</span>
                      <!-- {{ cartIngre.get(key).amount
                  }}{{ cartIngre.get(key).measure }}-->
                      <!-- <v-icon
                    v-if="!(item.ingredient.id in amountByRecipe)"
                    @click="deleteIngre(item)"
                    >mdi-close</v-icon
                  ><v-icon v-else color="white">mdi-close</v-icon>-->
                    </div>
                  </div>
                </div>
                <v-divider
                  v-if="
                    item.amount > 0 && ingreList[i].ingredient.category === cg
                  "
                ></v-divider>
              </div>
            </div>
          </div>
          <div class="d-flex justify-center">
            <v-dialog v-model="dialog" max-width="500px">
              <template #activator="{ on, attrs }">
                <v-btn
                  elevation="3"
                  small
                  color="letcipe"
                  class="mb-5 mt-6 white--text"
                  v-bind="attrs"
                  v-on="on"
                  @click="clearItem"
                  >재료 추가</v-btn
                >
              </template>

              <v-card>
                <v-card-title>
                  <span>재료 추가</span>
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
                                <v-list-item-title
                                  >일치하는 재료가 없습니다.</v-list-item-title
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
                            required
                            placeholder="0"
                            color="letcipe"
                            label="재료량"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field
                            v-model="editedItem.ingredient.measure"
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
                  <v-btn color="blue darken-1" text @click="close">취소</v-btn>
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
        </div>
        <div class="cart-count-wrap fadeInUp">
          <div class="ma-2">총 합계</div>
          <v-divider></v-divider>
          <div class="d-flex justify-space-between ma-2">
            <div>총 요리 수</div>
            <div>{{ checked.length }}</div>
          </div>
          <v-divider></v-divider>
          <div class="d-flex justify-space-between ma-2">
            <div>총 재료 수</div>
            <div>{{ totalAmount }}</div>
          </div>
        </div>
        <div class="cart-btn-wrap pt-3 fadeInUp">
          <v-btn
            elevation="5"
            width="100%"
            large
            color="letcipe"
            class="white--text"
            @click="clickStartCart"
            >장보기</v-btn
          >
        </div>

        <v-dialog v-model="dialogStartCartError" persistent max-width="290">
          <v-card>
            <v-card-title>{{ dialogTitle }}</v-card-title>
            <v-card-text>{{ errorMsg }}</v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                color="green darken-1"
                text
                @click="dialogStartCartError = false"
                >확인</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogAlert" persistent max-width="290">
          <v-card>
            <v-card-title>{{ dialogTitle }}</v-card-title>
            <v-card-text>{{ errorMsg }}</v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="green darken-1" text @click="dialogAlert = false"
                >취소</v-btn
              >
              <v-btn color="green darken-1" text @click="checkedDelete"
                >확인</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-dialog>

        <v-snackbar v-model="snackbar" :timeout="timeout">
          {{ text }}
        </v-snackbar>
      </v-container>
    </v-app>
  </div>
</template>

<script>
import { mapActions, mapState, mapMutations } from 'vuex'
export default {
  name: 'CartIndex',
  data() {
    return {
      dialogAlert: false,
      errorMsg: '',
      timeout: 2000,
      snackbar: false,
      text: '',
      ingredients: [],
      dialogTitle: 'Caution',
      totalAmount: 0,
      dialog: false,
      dialogStartCartError: false,
      isAllCheck: false,
      checked: [],
      checkedRecipe: [], // 체크된 재료를 구분 false 체크 x
      cartCategory: new Set(),
      cartKeyList: [],
      category: [],
      ingreIndexList: {}, // ingreList에 저장된 재료들의 인덱스를 저장하는 객체
      ingre_rule: [
        (v) => !!v || '재료량은 필수 입력사항입니다.',
        (v) => /^[0-9]*$/.test(v) || '재료량은 숫자만 입력 가능합니다.',
        (v) => !(v <= 0) || '재료량은 0 이상이어야 합니다.',
      ],
      keyword: null,
      keyword_rule: [(v) => !!v || '재료는 필수 입력사항입니다.'],
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
      editedItem: {
        amount: 0,
        ingredient: {
          name: '',
          id: '',
          category: '',
          gml: 1,
          measure: 'g',
        },
      },
      defaultItem: {
        amount: 0,
        ingredient: {
          name: '',
          id: '',
          category: '',
          gml: 1,
          measure: 'g',
        },
      },
      search: null,
      historyListInfo: [],
      isProgressHistory: false,
    }
  },
  computed: {
    ...mapState('cart', [
      'cart',
      'ingreList',
      'amountByRecipe',
      'isSucceededtoHistory',
    ]),
    ...mapState('ingredients', ['ingredientsList']),
    ...mapState('history', ['historyList']),
  },
  watch: {
    dialog(val) {
      val || this.close()
    },
  },
  created() {
    const promise = new Promise((resolve, reject) => {
      resolve()
    })
    promise.then(async () => {
      await this.readCart()
      this.initCart()
      await this.getCartIngredient()
      this.allCheck()
      this.isAllCheck = true
      console.log(this.ingreList)
      for (let i = 0; i < this.ingreList.length; i++) {
        // 각 재료의 인덱스 저장
        console.log(this.ingreList[i])
        this.ingreIndexList[this.ingreList[i].ingredient.id] = i
        this.cartCategory.add(this.ingreList[i].ingredient.category)
      }
      await this.getHistoryList()
      this.historyListInfo = this.historyList
      console.log(this.historyList)
      for (let i = 0; i < this.historyListInfo.length; i++) {
        if (this.historyListInfo[i].process === 'READY') {
          this.isProgressHistory = true
          break
        } else if (this.historyListInfo[i].process === 'EATING') {
          this.isProgressHistory = true
          break
        }
      }
    })
  },
  methods: {
    ...mapMutations('cart', [
      'CALC_PLUS_INGRE',
      'CALC_SUB_INGRE',
      'SET_INGRELIST_AMOUNT',
      'SET_BYRECIPE_AMOUNT',
      'ADD_INGRELIST',
      'ADD_INGRE_AMOUNT',
    ]),
    ...mapMutations('recipe', ['CLEAR_RECIPE_ID', 'SET_RECIPE_ID']),
    ...mapActions('ingredients', ['searchIngredient']),
    ...mapActions('cart', [
      'readCart',
      'updateCartRecipe',
      'deleteCart',
      'patchCartIngredient',
      'createCartIngredient',
      'deleteCartIngredient',
      'getCartIngredient',
      'deleteCartIngredient',
      'startCart',
    ]),
    ...mapActions('history', ['getHistoryList']),

    initialize() {
      this.ingredients = []
    },
    moveBack() {
      this.$router.go(-1)
    },
    moveDetail(data) {
      this.CLEAR_RECIPE_ID()
      this.SET_RECIPE_ID(data.recipe.id)
      this.$router.push('/recipe/detail')
      console.log(data)
    },
    close() {
      this.dialog = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
    },
    initCart() {
      for (let i = 0; i < this.cart.length; i++) {
        this.checkedRecipe.push(true)
      }
    },

    minusNum() {},
    addNum() {},
    plusCartList() {},
    moveWirte() {
      this.$router.push('/cart/write')
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
      console.log(item)
      this.editedItem.ingredient.name = item.name
      this.editedItem.ingredient.id = item.id
      this.editedItem.ingredient.measure = item.measure
      this.editedItem.ingredient.gml = item.gml
      this.editedItem.ingredient.category = item.category
    },
    async saveIngre() {
      if (this.$refs.form.validate()) {
        console.log(this.$refs.form.validate())
        // const tempEditedItem = {
        //   ingredientId : this.editedItem.id,
        //   operator : '+'
        // }
        this.IngreValid = false
        // ingreList를 돌면서 안에 있으면 amount를 더한 것으로 설정, 없으면 patch어짜구
        for (let i = 0; i < this.ingreList.length; i++) {
          // console.log(this.editedItem.ingredient.name)
          // console.log(this.ingreList[i].ingredient.name)
          if (
            this.editedItem.ingredient.name ===
            this.ingreList[i].ingredient.name
          ) {
            const ingreInfo = {
              index: i,
              amount: parseFloat(this.editedItem.amount),
            }
            this.ADD_INGRE_AMOUNT(ingreInfo)
            const createObject = {
              ingredientId: this.editedItem.ingredient.id,
              operator: '+',
            }
            for (let i = 0; i < this.editedItem.amount; i++) {
              await this.patchCartIngredient(createObject)
            }

            this.IngreValid = true
          }
        }
        // this.ingreList?.forEach((ingre) => {
        //   if (this.editedItem.name === ingre.ingredient.name) {
        //     ingre.amount =
        //       parseInt(ingre.amount) + parseInt(this.editedItem.amount)
        //     this.IngreValid = true
        //   }
        // })
        if (this.IngreValid === false) {
          console.log(this.editedItem)
          const ingreItem = {
            amount: this.editedItem.amount,
            ingredient: {
              name: this.editedItem.ingredient.name,
              id: this.editedItem.ingredient.id,
              category: this.editedItem.ingredient.category,
              gml: this.editedItem.ingredient.gml,
              measure: this.editedItem.ingredient.measure,
            },
          }
          this.ADD_INGRELIST(ingreItem)
          const createObject = {
            ingredientId: this.editedItem.ingredient.id,
            amount: this.editedItem.amount,
          }
          this.createCartIngredient(createObject)
          this.cartCategory.add(this.editedItem.ingredient.category)
          console.log(this.editedItem.ingredient.category)
          this.ingreIndexList[this.editedItem.ingredient.id] =
            this.ingreList.length - 1

          // if (this.editedIndex > -1) {
          //   Object.assign(this.ingreList[this.editedIndex], this.editedItem)
          // } else {
          //   this.ADD_INGRELIST(this.editedItem)
          //   // this.ingreList.push(this.editedItem)
          // }
        }

        // if (this.editedIndex > -1) {
        //   Object.assign(this.ingreList[this.editedIndex], this.editedItem)
        // }
        // if (this.editedIndex > -1) {
        //   Object.assign(this.ingredients[this.editedIndex], this.editedItem)
        // } else {
        //   this.ingredients.push(this.editedItem)
        // }

        this.close()
      }
      this.text = '재료가 추가되었습니다.'
      this.snackbar = true
      // this.dialogTitle = '재료 추가 성공'
      // this.errorMsg = '재료가 추가되었습니다.'
      // this.dialogStartCartError = true
    },
    initSelectIndex() {
      this.focusIndex = null
    },
    totalIngreAmount() {
      this.totalAmount = 0
      for (let i = 0; i < this.ingreList.length; i++) {
        if (this.ingreList[i].amount > 0) this.totalAmount++
      }
    },
    allCheck() {
      console.log(this.isAllCheck)

      if (!this.isAllCheck) {
        this.isAllCheck = true
        this.checked = []
        for (let i = 0; i < this.cart.length; i++) {
          this.checked.push(this.cart[i])
        }

        for (let i = 0; i < this.checked.length; i++) {
          if (!this.checkedRecipe[i]) {
            this.checkedRecipe[i] = true
            this.pressIngreAmount(i)
          }
        }
      } else {
        this.isAllCheck = false
        this.checked = []
        for (let i = 0; i < this.checkedRecipe.length; i++) {
          if (this.checkedRecipe[i]) {
            this.checkedRecipe[i] = false
            this.noPressIngreAmount(i)
          }
        }
      }
      this.totalIngreAmount()
    },
    pressIngreAmount(index) {
      for (let i = 0; i < this.cart[index].recipe.ingredients.length; i++) {
        const ingreIndex =
          this.ingreIndexList[
            this.cart[index].recipe.ingredients[i].ingredient.id
          ]
        const addAmount =
          this.cart[index].recipe.ingredients[i].amount *
          this.cart[index].amount
        console.log(this.ingreList[ingreIndex].amount + addAmount)
        let updateAmountObject = {
          index: ingreIndex,
          updateAmount: this.ingreList[ingreIndex].amount + addAmount,
        }
        this.SET_INGRELIST_AMOUNT(updateAmountObject)
        updateAmountObject = {
          ingreId: this.cart[index].recipe.ingredients[i].ingredient.id,
          updateAmount:
            this.cart[index].recipe.ingredients[i].ingredient.id - addAmount,
        }
        this.SET_BYRECIPE_AMOUNT(updateAmountObject)
      }
    },
    noPressIngreAmount(index) {
      for (let i = 0; i < this.cart[index].recipe.ingredients.length; i++) {
        const ingreIndex =
          this.ingreIndexList[
            this.cart[index].recipe.ingredients[i].ingredient.id
          ]
        const subAmount =
          this.cart[index].recipe.ingredients[i].amount *
          this.cart[index].amount
        console.log(this.ingreList[ingreIndex].amount - subAmount)
        let updateAmountObject = {
          index: ingreIndex,
          updateAmount: this.ingreList[ingreIndex].amount - subAmount,
        }
        this.SET_INGRELIST_AMOUNT(updateAmountObject)
        updateAmountObject = {
          ingreId: this.cart[index].recipe.ingredients[i].ingredient.id,
          updateAmount:
            this.cart[index].recipe.ingredients[i].ingredient.id - subAmount,
        }
        this.SET_BYRECIPE_AMOUNT(updateAmountObject)
      }
    },
    addRecipe(index) {
      this.checked = []
      if (this.isAllCheck) {
        this.isAllCheck = false
      }
      if (!this.checkedRecipe[index]) {
        this.checkedRecipe[index] = true
        this.pressIngreAmount(index)
      } else {
        this.checkedRecipe[index] = false
        this.noPressIngreAmount(index)
      }
      for (let i = 0; i < this.checkedRecipe.length; i++) {
        if (this.checkedRecipe[i]) {
          this.checked.push(this.cart[i])
        }
      }
      if (this.checked.length === this.cart.length) {
        this.isAllCheck = true
      }
      this.totalIngreAmount()
    },
    subRecipeAmount(id, i) {
      if (this.cart[i].amount > 1) {
        const updateObject = {
          recipeId: id,
          operator: '-',
        }
        this.updateCartRecipe(updateObject)
        this.text = '레시피의 수량을 수정하였습니다.'
        this.snackbar = true
        this.$router.go()
      }
    },
    plusRecipeAmount(id, i) {
      const updateObject = {
        recipeId: id,
        operator: '+',
      }
      this.updateCartRecipe(updateObject)
      this.text = '레시피의 수량을 수정하였습니다.'
      this.snackbar = true
      this.$router.go()
    },
    async deleteRecipe(recipeId) {
      await this.deleteCart(recipeId)
    },
    checkedDeleteAlert() {
      this.dialogTitle = 'Caution'
      this.errorMsg = '정말로 삭제하시겠습니까?'
      this.dialogAlert = true
    },
   checkedDelete() {
      if (this.checked.length > 0) {
        for (let i = 0; i < this.checked.length; i++) {
         this.deleteRecipe(this.checked[i].recipe.id)
        }

      }
       this.$router.go()
      this.dialogAlert = false
    },
    subIngreAmount(index) {
      const ingredientInfo = this.ingreList[index]
      console.log(ingredientInfo)
      // 원래있던 재료에서더한 경우
      console.log(this.amountByRecipe)
      if (ingredientInfo.ingredient.id in this.amountByRecipe) {
        // amountByRecipe에 재료가 있는 지 확인 -> 있으면 원래 있던 재료에서 추가한 재료
        console.log(parseInt(ingredientInfo.amount))
        console.log(parseInt(this.amountByRecipe[ingredientInfo.ingredient.id]))
        if (
          Number(
            parseInt(ingredientInfo.amount) -
              parseInt(this.amountByRecipe[ingredientInfo.ingredient.id])
          ) === 0
        )
          return
        console.log(
          Number(
            parseInt(ingredientInfo.amount) -
              parseInt(this.amountByRecipe[ingredientInfo.ingredient.id]) -
              1
          )
        )
        console.log(
          Number(
            parseInt(ingredientInfo.amount) -
              parseInt(this.amountByRecipe[ingredientInfo.ingredient.id]) -
              1
          ) === 0
        )
        if (
          // delete하는 경우
          Number(
            parseInt(ingredientInfo.amount) -
              parseInt(this.amountByRecipe[ingredientInfo.ingredient.id]) -
              1
          ) === 0
        ) {
          this.deleteCartIngredient(ingredientInfo.ingredient.id)

          console.log('11111111111111111111111111111111')
        } else {
          // 추가 재료의 수를 -1해줘야 하는 경우
          console.log('222222222222222222')
          const updateObject = {
            ingredientId: this.ingreList[index].ingredient.id,
            operator: '-',
          }
          this.patchCartIngredient(updateObject)
        }
      } else if (ingredientInfo.amount - 1 === 0) {
        // 원래 없는 재료를 더해준 것

        // 추가 재료의 수가 0이되어 cart_ingredient 테이블에서 정보를 지워야 할때
        console.log('3333333333333333333333333')

        this.deleteCartIngredient(ingredientInfo.ingredient.id)
      } else {
        const updateObject = {
          ingredientId: this.ingreList[index].ingredient.id,
          operator: '-',
        }
        this.patchCartIngredient(updateObject)
      }
      this.CALC_SUB_INGRE(index)
      this.text = '재료의 수량을 수정하였습니다.'
      this.snackbar = true
    },

    plusIngreAmount(index) {
      this.CALC_PLUS_INGRE(index)
      console.log(this.ingreList[index])
      const createObject = {
        ingredientId: this.ingreList[index].ingredient.id,
        operator: '+',
      }
      this.patchCartIngredient(createObject)
      this.text = '재료의 수량을 수정하였습니다.'
      this.snackbar = true
    },
    deleteIngre(item) {
      this.deleteCartIngredient(item.id)
    },
    clickStartCart() {
      if (this.checked.length > 0) {
        if (this.isProgressHistory) {
          this.dialogTitle = 'Caution'
          this.errorMsg =
            '이미 진행 예정이거나 진행 중인 레시피 리스트가 있습니다.'
          this.dialogStartCartError = true
        } else {
          this.startCart()
          this.$router.go()
        }
      } else {
        this.dialogTitle = 'Caution'
        this.errorMsg = '하나 이상의 레시피를 담아주세요.'
        this.dialogStartCartError = true
      }
    },
    clearItem() {
      this.keyword = null
    },
    editItem(item) {
      this.search = item.name
      this.editedIndex = this.ingreList.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialog = true
    },
    deleteItem(item) {
      this.editedIndex = this.ingreList.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.ingreList.splice(this.editedIndex, 1)
      this.closeDelete()
    },
    closeDelete() {
      this.dialogDelete = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
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
.recipelist-title-btn {
  cursor: pointer;
}
.category-images {
  width: 20px;
}
.fadeInUp {
  animation: fadeInUp 1s ease backwards;
}
.ingre-wrap {
  padding: 4%;
}
.ingre-category {
  padding: 4%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
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

@media (max-width: 415px) {
  .recipe-title {
    width: 130px;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}
</style>
