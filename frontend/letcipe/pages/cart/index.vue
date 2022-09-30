<template>
  <div id="app">
    <v-app class="cart-page">
      <v-container class="cart-container d-flex-row">
        <div class="cart-head-wrap">
          <div class="d-flex justify-space-between pb-3">
            <v-icon>mdi-window-close</v-icon>
            <div style="font-size: x-large">장바구니</div>
            <v-icon>mdi-blank</v-icon>
          </div>
          <div class="d-flex justify-space-between pb-7" @click="moveWirte">
            <div>레시피리스트 제목을 입력해주세요</div>
            <v-icon>mdi-chevron-right</v-icon>
          </div>
          <div class="d-flex justify-space-between">
            <div class="d-flex align-items-center">
              <div v-if="!isAllCheck" @click="allCheck">
                <v-icon>mdi-check-circle-outline</v-icon>전체 선택
              </div>
              <div v-else @click="allCheck">
                <v-icon>mdi-check-circle</v-icon>전체 선택
              </div>
            </div>
            <div>
              <v-btn small color="letcipe" @click="checkedDelete"
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
          <div v-for="(recipeInfo, i) in cart" :key="i">
            <v-list-item
              three-line
              style="background-color: white"
              class="pl-3 pr-3"
            >
              <v-icon
                v-if="!checkedRecipe[i]"
                class="mr-3"
                @click="addRecipe(i)"
                >mdi-check-circle-outline</v-icon
              >
              <v-icon v-else class="mr-3" @click="addRecipe(i)"
                >mdi-check-circle</v-icon
              >
              <v-list-item-avatar tile size="100">
                <v-img :src="recipeInfo.recipe.repImg"></v-img>
              </v-list-item-avatar>
              <v-list-item-content class="pl-4">
                <v-list-item-title class="d-flex justify-space-between">
                  <div>
                    {{ recipeInfo.recipe.title }}
                  </div>
                  <v-icon @click="deleteRecipe(recipeInfo.recipe.id)"
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
                >
                  {{ recipeInfo.recipe.content }}
                </v-list-item-subtitle>
                <div class="d-flex justify-space-between">
                  <div style="margin: auto 0">
                    <v-icon small color="pink lighten-1"
                      >mdi-cards-heart</v-icon
                    >
                    {{ recipeInfo.recipe.recipeLike }}
                  </div>
                  <!-- <v-list-item-subtitle style="text-align: right"
                    >선택한 재료 4개를 포함하고 있어요.</v-list-item-subtitle
                  > -->
                  <div>
                    <v-btn
                      class="mx-2"
                      fab
                      dark
                      x-small
                      color="#aac821"
                      @click="subRecipeAmount(recipeInfo.recipe.id)"
                    >
                      <v-icon dark>mdi-minus</v-icon>
                    </v-btn>
                    {{ recipeInfo.amount }}
                    <v-btn
                      class="mx-2"
                      fab
                      dark
                      x-small
                      color="#aac821"
                      @click="plusRecipeAmount(recipeInfo.recipe.id)"
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
                    </div> -->
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

          <div v-for="(item, index) in ingreList" :key="index">
            <div class="d-flex justify-space-between pa-2">
              <!-- <div style="font-size: x-large"> -->
              <div>
                {{ item.ingredient.name }}
              </div>
              <div>
                <!-- <div style="font-size: x-large"> -->
                <div>
                  <v-btn
                    class="mx-2"
                    fab
                    dark
                    x-small
                    color="#aac821"
                    @click="subIngreAmount(index)"
                  >
                    <v-icon dark>mdi-minus</v-icon>
                  </v-btn>
                  {{ Math.ceil(item.amount) }}{{ item.ingredient.measure }}
                  <v-btn
                    class="mx-2"
                    fab
                    dark
                    x-small
                    color="#aac821"
                    @click="plusIngreAmount(index)"
                  >
                    <v-icon dark>mdi-plus</v-icon>
                  </v-btn>
                  <v-icon @click="deleteIngre(item)">mdi-close</v-icon>
                </div>
                <div style="float: right">
                  {{ Math.ceil(item.amount * item.ingredient.gml)
                  }}<span v-if="item.ingredient.measure == 'ml'">ml</span>
                  <span v-else>g</span>
                  <!-- {{ cartIngre.get(key).amount
                  }}{{ cartIngre.get(key).measure }} -->
                </div>
              </div>
            </div>
            <v-divider></v-divider>
          </div>
          <div class="d-flex justify-center align-center pa-2">
            <v-btn
              class="mx-2"
              small
              dark
              color="#aac821"
              @click="addIngreDialog = true"
            >
              재료 추가
            </v-btn>
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
            <div>{{ requiredList.size }}</div>
          </div>
        </div>
        <div class="cart-btn-wrap pt-3 fadeInUp">
          <v-btn
            elevation="5"
            width="100%"
            large
            color="#aac821"
            class="white--text"
            >장보기</v-btn
          >
        </div>
        <div class="d-flex justify-center">
          <v-dialog v-model="addIngreDialog" max-width="500px">
            <v-card>
              <v-card-title>
                <span class="text-h5">{{ formTitle }}</span>
              </v-card-title>

              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="12" sm="6" md="4">
                      <!-- <v-text-field
                              v-model="editedItem.name"
                              label="재료명"
                            ></v-text-field> -->
                      <v-autocomplete
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
                        :rules="ingre_rule"
                        placeholder="0"
                        color="letcipe"
                        label="재료량"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="4">
                      <v-text-field disabled label="단위"></v-text-field>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                  color="blue darken-1"
                  text
                  @click="addIngreDialog = false"
                  >취소</v-btn
                >
                <v-btn color="blue darken-1" text @click="saveIngre"
                  >재료 저장</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-dialog>
        </div>
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
      isAllCheck: false,
      checked: [],
      checkedRecipe: [], // 체크된 재료를 구분 false 체크 x
      cartCategory: [],
      cartKeyList: [],
      requiredList: new Map(),
      subIngreCheck: [],
      plusIngreCheck: [],
      addIngreDialog: false,
      ingre_rule: [
        (v) => !!v || '재료량은 필수 입력사항입니다.',
        (v) => /^[0-9]*$/.test(v) || '재료량은 숫자만 입력 가능합니다.',
        (v) => !(v <= 0) || '재료량은 0 이상이어야 합니다.',
      ],
    }
  },
  computed: {
    ...mapState('cart', ['cart', 'ingreList']),
  },
  created() {
    const promise = new Promise((resolve, reject) => {
      resolve()
    })
    promise.then(async () => {
      await this.readCart()
      this.initCart()
      this.getCartIngredient()
      this.allCheck()
      this.calRequiredList()
      console.log(this.ingreList)
      // for (let i = 0; i < this.requiredList.size; i++) {
      //   this.subIngreCheck.push(0)
      //   this.plusIngreCheck.push(0)
      // }
      // console.log(this.subIngreCheck)
    })
  },
  methods: {
    initCart() {
      for (let i = 0; i < this.cart.length; i++) {
        this.checkedRecipe.push(false)
      }
    },
    ...mapMutations('cart', ['CALC_PLUS_INGRE', 'CALC_SUB_INGRE']),
    ...mapActions('cart', [
      'readCart',
      'updateCartRecipe',
      'deleteCart',
      'patchCartIngredient',
      'createCartIngredient',
      'deleteCartIngredient',
      'getCartIngredient',
      'deleteCartIngredient',
    ]),
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
      this.editedItem.name = item.name
      this.editedItem.id = item.id
      this.editedItem.unit = item.measure
    },
    saveIngre() {
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
    },
    initSelectIndex() {
      this.focusIndex = null
    },
    allCheck() {
      if (!this.isAllCheck) {
        this.isAllCheck = true
        this.checked = []
        for (let i = 0; i < this.cart.length; i++) {
          this.checked.push(this.cart[i])
        }

        for (let i = 0; i < this.checked.length; i++) {
          this.checkedRecipe[i] = true
        }
      } else {
        this.isAllCheck = false
        this.checked = []
        for (let i = 0; i < this.checkedRecipe.length; i++) {
          this.checkedRecipe[i] = false
        }
      }
      this.calRequiredList()
    },
    addRecipe(index) {
      this.checked = []
      if (this.isAllCheck) {
        this.isAllCheck = false
      }
      if (!this.checkedRecipe[index]) {
        this.checkedRecipe[index] = true
      } else {
        this.checkedRecipe[index] = false
      }
      for (let i = 0; i < this.checkedRecipe.length; i++) {
        if (this.checkedRecipe[i]) {
          this.checked.push(this.cart[i])
        }
      }
      if (this.checked.length === this.cart.length) {
        this.isAllCheck = true
      }
      this.calRequiredList()
    },
    calRequiredList() {
      this.requiredList = new Map()

      for (let i = 0; i < this.checked.length; i++) {
        const ingreList = this.checked[i].recipe.ingredients
        for (let j = 0; j < ingreList.length; j++) {
          if (this.requiredList.has(ingreList[j].ingredient.name)) {
            this.requiredList.get(ingreList[j].ingredient.name).amount +=
              ingreList[j].amount * this.checked[i].amount
          } else {
            this.requiredList.set(ingreList[j].ingredient.name, {
              name: ingreList[j].ingredient.name,
              category: ingreList[j].ingredient.category,
              measure: ingreList[j].ingredient.measure,
              toGram: ingreList[j].ingredient.gml,
              id: ingreList[j].ingredient.id,
              amount: ingreList[j].amount * this.checked[i].amount,
            })
          }
        }
      }
    },
    subRecipeAmount(id) {
      const updateObject = {
        recipeId: id,
        operator: '-',
      }
      this.updateCartRecipe(updateObject)
      this.$router.go()
    },
    plusRecipeAmount(id) {
      const updateObject = {
        recipeId: id,
        operator: '+',
      }
      this.updateCartRecipe(updateObject)
      this.$router.go()
    },
    deleteRecipe(recipeId) {
      this.deleteCart(recipeId)
      this.$router.go()
    },
    checkedDelete() {
      if (this.checked.length > 0) {
        for (let i = 0; i < this.checked.length; i++) {
          this.deleteRecipe(this.checked[i].recipe.id)
        }
        this.$router.go()
      }
    },
    subIngreAmount(index) {
      this.CALC_SUB_INGRE(index)
      console.log(this.ingreList[index])

      const createObject = {
        ingredientId: this.ingreList[index].ingredient.id,
        operator: '-',
      }
      this.patchCartIngredient(createObject)
      // console.log(id + '=======' + index)
      // console.log(this.subIngreCheck[index])
      // if (this.subIngreCheck[index] - 1 === 0) {
      //   this.deleteCartIngredient(id)
      //   this.subIngreCheck[index]--
      // } else if (
      //   this.subIngreCheck[index] > 0 ||
      //   this.subIngreCheck[index] < 0
      // ) {
      //   const updateObject = {
      //     ingredientId: id,
      //     operator: '-',
      //   }
      //   this.patchCartIngredient(updateObject)
      // } else if (this.subIngreCheck[index] === 0) {
      //   const createObject = {
      //     ingredientId: id,
      //     operator: '-',
      //   }
      //   this.createCartIngredient(createObject)
      // }

      // console.log(id)
    },
    plusIngreAmount(index) {
      this.CALC_PLUS_INGRE(index)
      // this.$store.commit('SET_PLUS_INGRE', index)
      console.log(this.ingreList[index])
      const createObject = {
        ingredientId: this.ingreList[index].ingredient.id,
        operator: '+',
      }
      this.patchCartIngredient(createObject)
    },
    deleteIngre(item) {
      this.deleteCartIngredient(item.id)
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
