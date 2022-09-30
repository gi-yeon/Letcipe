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
                  <v-icon>mdi-cart-outline</v-icon>
                  <div>전체보기</div>
                </v-tab>

                <v-tab href="#mobile-tabs-5-2" class="letcipe--text">
                  <v-icon>mdi-sort</v-icon>
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
                        <v-icon>mdi-carrot</v-icon>구매할식재료
                      </div>
                      <div class="d-flex align-center">
                        <div>전체선택</div>

                        <v-checkbox color="lime darken-1"></v-checkbox>
                      </div>
                    </div>
                    <v-divider></v-divider>
                    <div v-for="(c, index) in checklist" :key="index" class="pl-3 pr-3">
                      <v-checkbox
                        v-model="checklist[index]"
                        :label="c"
                        color="lime darken-1"
                        :value="c"
                        hide-details
                        @click="bought(c, index)"
                      ></v-checkbox>
                    </div>
                  </div>
                </div>
                <div class="shopping-wrap">
                  <div class="after-shopping">
                    <div class="d-flex justify-space-between align-center">
                      <div>
                        <v-icon>mdi-cart-plus</v-icon>담은식재료
                      </div>
                      <div class="d-flex align-center">
                        <div>전체선택</div>

                        <v-checkbox color="lime darken-1"></v-checkbox>
                      </div>
                    </div>
                    <v-divider></v-divider>
                    <div v-for="(c, index) in checkedList" :key="index" class="pl-3 pr-3">
                      <v-checkbox
                        v-model="checkedList[index]"
                        :label="c"
                        color="lime darken-1"
                        :value="c"
                        hide-details
                        @click="needtobuy(c, index)"
                      ></v-checkbox>
                    </div>
                  </div>
                </div>
                <v-dialog v-model="dialog" persistent max-width="290">
                  <template #activator="{ on, attrs }">
                    <v-btn
                      color="letcipe"
                      style="color: white; width: 100%"
                      v-bind="attrs"
                      @click="completeShopping()"
                      v-on="on"
                    >장보기 완료</v-btn>
                  </template>
                  <v-card v-if="check===false">
                    <v-card-title class="text-h5">Complete</v-card-title>
                    <v-card-text>장보기를 완료하시겠습니까?</v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn color="green darken-1" text @click="dialog = false">확인</v-btn>
                    </v-card-actions>
                  </v-card>
                  <v-card v-if="check===true">
                    <v-card-title class="text-h5">Caution</v-card-title>
                    <v-card-text>아직 구매하지 않은 식재료가 있습니다. 장보기를 완료하시겠습니까?</v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn color="green darken-1" text @click="dialog = false">돌아가기</v-btn>
                      <v-btn color="green darken-1" text @click="dialog = false">확인</v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-card-text>
              <v-card-text v-else-if="i === 2" class="fadeInUp">
                <div class="shopping-wrap">
                  <div
                    v-for="(cg, index) in category"
                    :key="index"
                    class="before-shopping pl-3 pr-3"
                  >
                    <div class="d-flex justify-space-between align-center">
                      <div>
                        <v-icon>mdi-carrot</v-icon>
                        {{cg}}
                      </div>
                      <div class="d-flex align-center">
                        <div>전체선택</div>
                        <v-checkbox color="lime darken-1"></v-checkbox>
                      </div>
                    </div>
                    <v-divider></v-divider>
                    <div v-for="(c) in checklist" :key="c" class="pl-3 pr-3">
                      <v-checkbox
                        v-model="checklist[index]"
                        :label="c"
                        color="lime darken-1"
                        :value="c"
                        hide-details
                        @click="bought(c, index)"
                      ></v-checkbox>
                    </div>
                  </div>
                </div>
                <div class="shopping-wrap">
                  <div class="after-shopping">
                    <div class="d-flex justify-space-between align-center">
                      <div>
                        <v-icon>mdi-cart-plus</v-icon>담은식재료
                      </div>
                      <div class="d-flex align-center">
                        <div>전체선택</div>

                        <v-checkbox color="lime darken-1"></v-checkbox>
                      </div>
                    </div>
                    <v-divider></v-divider>
                    <div v-for="(c, index) in checkedList" :key="index" class="pl-3 pr-3">
                      <v-checkbox
                        v-model="checkedList[index]"
                        :label="c"
                        color="lime darken-1"
                        :value="c"
                        hide-details
                        @click="needtobuy(c, index)"
                      ></v-checkbox>
                    </div>
                  </div>
                </div>
                <v-dialog v-model="dialog" persistent max-width="290">
                  <template #activator="{ on, attrs }">
                    <v-btn
                      color="letcipe"
                      style="color: white; width: 100%"
                      v-bind="attrs"
                      @click="completeShopping()"
                      v-on="on"
                    >장보기 완료</v-btn>
                  </template>
                  <v-card v-if="check===false">
                    <v-card-title class="text-h5">Complete</v-card-title>
                    <v-card-text>장보기를 완료하시겠습니까?</v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn color="green darken-1" text @click="dialog = false">확인</v-btn>
                    </v-card-actions>
                  </v-card>
                  <v-card v-if="check===true">
                    <v-card-title class="text-h5">Caution</v-card-title>
                    <v-card-text>아직 체크되지 않은 항목이 있습니다. 장보기를 완료하시겠습니까?</v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn color="green darken-1" text @click="dialog = false">돌아가기</v-btn>
                      <v-btn color="green darken-1" text @click="dialog = false">확인</v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-card-text>
            </v-card>
          </v-tab-item>
        </v-tabs-items>
      </v-container>
    </v-app>
  </div>
</template>

<script>
export default {
  name: 'CheckIndex',
  data() {
    return {
      tabs: null,
      check: false,
      checklist: ['양파', '오이', '토마토', '대파', '쪽마늘'],
      checkedList: [],
      dialog: false,
      category: [],
      ingredients: [
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
        {
          id: 604,
          name: '양장피',
          category: '음식 식품',
          measure: '개',
          gml: 30.0,
        },
        {
          id: 824,
          name: '양배추',
          category: '채소',
          measure: '통',
          gml: 200.0,
        },
      ],
    }
  },
  created() {
    this.category = []
    this.ingredients?.forEach((item) => {
      if (this.category.length === 0) {
        this.category.push(item.category)
      } else {
        let cnt = 0
        this.category?.forEach((cg) => {
          if (cg === item.category) {
            cnt++
          }
        })
        if (cnt === 0) {
          this.category.push(item.category)
        }
      }
    })
  },
  methods: {
    completeShopping() {
      console.log('장보기')
    },
    bought(c, index) {
      this.checklist.splice(index, 1)
      this.checkedList.push(c)
    },
    needtobuy(c, index) {
      this.checkedList.splice(index, 1)
      this.checklist.push(c)
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