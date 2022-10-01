<template>
  <div id="app">
    <v-app class="check-page">
      <v-container class="check-container">
        <div class="check-head-wrap">
          <v-toolbar flat>
            <v-icon>mdi-window-close</v-icon>

            <v-toolbar-title @click="test()">장바구니</v-toolbar-title>

            <v-spacer></v-spacer>
            <template #extension>
              <v-tabs v-model="tabs" fixed-tabs>
                <v-tabs-slider color="black"></v-tabs-slider>
                <v-tab href="#mobile-tabs-5-1" class="letcipe--text">
                  <v-icon color="letcipe">mdi-cart-outline</v-icon>
                  <div>전체보기</div>
                </v-tab>

                <v-tab href="#mobile-tabs-5-2" class="letcipe--text">
                  <v-icon color="letcipe">mdi-sort</v-icon>
                  <div>분류별보기</div>
                </v-tab>
              </v-tabs>
            </template>
          </v-toolbar>
        </div>
        <v-tabs-items v-model="tabs" class="check-tabs-wrap">
          <v-tab-item v-for="i in 2" :key="i" :value="'mobile-tabs-5-' + i">
            <v-card flat>
              <v-card-text v-if="i === 1" class="fadeInUp">
                <div class="shopping-wrap">
                  <div class="before-shopping">
                    <div class="d-flex justify-space-between align-center">
                      <div>
                        <v-icon color="letcipe">mdi-cart-outline</v-icon>구매할식재료
                      </div>
                      <div class="d-flex align-center">
                        <div>전체선택</div>

                        <v-checkbox color="letcipe" @click="checkAll"></v-checkbox>
                      </div>
                    </div>
                    <v-divider></v-divider>
                    <div v-for="(c, index) in checklist" :key="index" class="pl-3 pr-3">
                      <div class="d-flex justify-space-between align-center mt-1">
                        <div class="ingre-name">
                          <v-checkbox
                            v-model="checklist[index]"
                            class="mt-0 pt-0"
                            :label="c.name"
                            color="letcipe"
                            :value="c.name"
                            hide-details
                            @click="bought(c, index)"
                          ></v-checkbox>
                        </div>
                        <div class="ingre-amount">{{c.amount}}{{c.measure}}</div>
                      </div>

                      <v-divider></v-divider>
                    </div>
                  </div>
                </div>
                <div class="shopping-wrap">
                  <div class="after-shopping">
                    <div class="d-flex justify-space-between align-center">
                      <div>
                        <v-icon color="letcipe">mdi-cart-plus</v-icon>담은식재료
                      </div>
                      <div class="d-flex align-center">
                        <div>전체선택</div>

                        <v-checkbox color="letcipe" @click="removeAll"></v-checkbox>
                      </div>
                    </div>
                    <v-divider></v-divider>
                    <div v-for="(c, index) in checkedList" :key="index" class="pl-3 pr-3">
                      <div class="d-flex justify-space-between align-center mt-1">
                        <div class="ingre-name">
                          <v-checkbox
                            v-model="checkedList[index]"
                            class="mt-0 pt-0"
                            :label="c.name"
                            color="letcipe"
                            true-value
                            hide-details
                            @click="needtobuy(c, index)"
                          ></v-checkbox>
                        </div>
                        <div class="ingre-amount">{{c.amount}}{{c.measure}}</div>
                      </div>
                      <v-divider></v-divider>
                    </div>
                  </div>
                </div>
              </v-card-text>
              <v-card-text v-else-if="i === 2" class="fadeInUp">
                <div class="shopping-wrap">
                  <div
                    v-for="(cg, index) in category"
                    :key="index"
                    class="before-shopping pl-3 pr-3"
                  >
                    <div class="d-flex justify-space-between align-center">
                      <div class="d-flex justify-center mt-2 mb-2">
                        <img class="category-images" :src="`/cart_icon/${cg}.png`" alt="flour" />

                        <div>{{cg}}</div>
                      </div>
                      <!-- <div class="d-flex align-center">
                        <div>전체선택</div>
                        <v-checkbox color="letcipe"></v-checkbox>
                      </div>-->
                    </div>
                    <v-divider></v-divider>
                    <div v-for="(c, idx) in checklist" :key="idx" class="pl-3 pr-3">
                      <div
                        v-if="checklist[idx].categoryName === cg"
                        class="d-flex justify-space-between align-center mt-1"
                      >
                        <div class="ingre-name">
                          <v-checkbox
                            v-model="checklist[idx]"
                            class="mt-0 pt-0"
                            :label="c.name"
                            color="letcipe"
                            :value="c.name"
                            hide-details
                            @click="bought(c, idx)"
                          ></v-checkbox>
                        </div>
                        <div class="ingre-amount">{{c.amount}}{{c.measure}}</div>
                      </div>
                      <v-divider v-if="checklist[idx].categoryName === cg"></v-divider>
                    </div>
                  </div>
                </div>
                <div class="shopping-wrap">
                  <div class="after-shopping">
                    <div class="d-flex justify-space-between align-center">
                      <div class="mt-3 mb-3">
                        <v-icon>mdi-cart-plus</v-icon>담은식재료
                      </div>
                      <!-- <div class="d-flex align-center">
                        <div>전체선택</div>
                        <v-checkbox color="letcipe"></v-checkbox>
                      </div>-->
                    </div>
                    <v-divider></v-divider>
                    <div v-for="(c, index) in checkedList" :key="index" class="pl-3 pr-3">
                      <div class="d-flex justify-space-between align-center mt-1">
                        <div class="ingre-name">
                          <v-checkbox
                            v-model="checkedList[index]"
                            class="mt-0 pt-0"
                            :label="c.name"
                            color="letcipe"
                            hide-details
                            true-value
                            @click="needtobuy(c, index)"
                          ></v-checkbox>
                        </div>
                        <div class="ingre-amount">{{c.amount}}{{c.measure}}</div>
                      </div>
                      <v-divider></v-divider>
                    </div>
                  </div>
                </div>
              </v-card-text>
            </v-card>
            <v-dialog v-model="dialog1" persistent max-width="290">
              <template #activator="{ on, attrs }">
                <v-btn
                  color="letcipe"
                  style="color: white; width: 100%"
                  v-bind="attrs"
                  v-on="on"
                >장보기 완료</v-btn>
              </template>
              <v-card v-if="checklist.length===0">
                <v-card-title style="font-size: x-large;">
                  장보기완료
                  <img class="category-images" src="/cart_icon/담은카트_1.png" alt="flour" />
                </v-card-title>
                <v-card-text>장보기를 완료하시겠습니까?</v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn
                    color="green darken-1"
                    text
                    @click="[dialog1 = false, completeShopping()]"
                  >확인</v-btn>
                </v-card-actions>
              </v-card>
              <v-card v-else>
                <v-card-title style="font-size: x-large">
                  잠깐
                  <img class="category-images" src="/cart_icon/빈카트_1.png" alt="flour" />
                </v-card-title>
                <v-card-text>아직 구매하지 않은 식재료가 있습니다. 장보기를 완료하시겠습니까?</v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="green darken-1" text @click="dialog1 = false">돌아가기</v-btn>
                  <v-btn
                    color="green darken-1"
                    text
                    @click="[dialog1 = false, completeShopping()]"
                  >확인</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-tab-item>
        </v-tabs-items>
      </v-container>
    </v-app>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
export default {
  name: 'CheckIndex',
  data() {
    return {
      historyID: null,
      tabs: null,
      check: false,
      checklist: [],
      checkedList: [],
      dialog1: false,
      dialog2: false,
      category: [],
      ingredients: [],
      isSelected: false,
      isRemoved: false,
      isComplete: false,
      selectedIngre: [],
    }
  },
  computed: {
    ...mapState('history', ['history', 'historyList']),
  },
  created() {
    this.category = []
    const promise = new Promise((resolve, reject) => {
      resolve()
    })
    promise.then(async () => {
      await this.getHistoryList()
      this.historyList.forEach((h) => {
        if (h.process === 'READY') {
          this.historyID = h.id
        }
      })
      await this.getHistory(this.historyID)
      this.history.historyIngredients.forEach((jaeryo) => {
        if (jaeryo.isPurchased === 'N') {
          this.checklist.push(jaeryo)
        } else {
          this.checkedList.push(jaeryo)
        }
        if (this.category.length === 0) {
          this.category.push(jaeryo.categoryName)
        } else {
          let cnt = 0
          this.category?.forEach((cg) => {
            if (cg === jaeryo.categoryName) {
              cnt++
            }
          })
          if (cnt === 0) {
            this.category.push(jaeryo.categoryName)
          }
        }
      })
    })
  },
  methods: {
    ...mapActions('history', [
      'getHistory',
      'getHistoryList',
      'checkHistoryIngredient',
      'updateHistory',
    ]),
    completeShopping() {
      console.log('장보기')
      const h = {
        id: this.history.id,
        process: this.history.process,
      }
      this.updateHistory(h)
    },
    bought(c, index) {
      this.checklist.splice(index, 1)
      this.checkedList.push(c)
      this.checkHistoryIngredient(c.id)
    },
    needtobuy(c, index) {
      this.checkedList.splice(index, 1)
      this.checklist.push(c)
      this.checkHistoryIngredient(c.id)
    },
    checkAll() {
      this.checklist.forEach((c) => {
        this.checkHistoryIngredient(c.id)
        this.checkedList.push(c)
      })
      this.checklist = []
    },
    removeAll() {
      this.checkedList.forEach((c) => {
        this.checkHistoryIngredient(c.id)
        this.checklist.push(c)
      })
      this.checkedList = []
    },
    test() {
      console.log(this.category.length)
      console.log(this.category)
    },
  },
}
</script>



<style scoped>
.check-page {
  padding-top: 70px;
  padding-bottom: 70px;
}
.check-page-head {
  padding: 4%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  /* border: 1px solid gray; */
}
.check-head-wrap {
  padding: 4%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  /* border: 1px solid gray; */
}
.check-tabs-wrap {
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
}
.shopping-wrap {
  padding: 4%;
}
.before-shopping {
  padding: 4%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
}
.after-shopping {
  padding: 4%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
}
.category-images {
  width: 20px;
}
/* .v-input--selection-controls {
  margin-top: 0 !important;
  padding-top: 0 !important;
} */
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