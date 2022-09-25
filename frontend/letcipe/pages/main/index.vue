<template>
  <div id="app">
    <v-app id="inspire">
      <v-container class="main-container">
        <div class="title-wrap">
          <div class="title">{{ user }}님이 좋아할 레시피</div>
          <div class="title-imgs">
            <div v-for="(ref, i) in refImg" :key="i" class="card">
              <div>{{ user }}맞춤 추천</div>
              <v-card>
                <v-img class="ref-imgs" :src="'https://2bob.co.kr/' + ref.url">
                  <div class="ref-wrap">
                    <v-card-title class="ref-title">
                      {{
                      ref.title
                      }}
                    </v-card-title>
                    <v-card-subtitle class="ref-subtitle">
                      {{
                      ref.sub_title
                      }}
                    </v-card-subtitle>
                  </div>
                </v-img>
              </v-card>
            </div>
          </div>
        </div>
        <div class="my-container">
          <div>{{ user }}님의 현재 진행중인 레시피리스트</div>
          <v-carousel height="100%" style="border-radius: 30px">
            <v-carousel-item v-for="(item, i) in userPlayList" :key="i">
              <v-container>
                <v-row style="margin: -25px">
                  <v-col cols="12">
                    <v-hover>
                      <template #default="{ hover }">
                        <v-card
                          :elevation="hover ? 24 : 6"
                          :class="hover ? 'lime lighten-3' : 'lime lighten-2'"
                          class="my-card mx-auto"
                        >
                          <div class="d-flex flex-no-wrap justify-center pa-3">
                            <div>
                              <div class="my-lecipe">
                                <v-avatar class="ma-3" size="100">
                                  <v-img :src="'https://2bob.co.kr/' + item.url">
                                    <v-icon x-large>mdi-play</v-icon>
                                  </v-img>
                                </v-avatar>
                              </div>
                              <v-card-title class="my-title text-h5" v-text="item.title"></v-card-title>
                              <v-card-subtitle v-text="item.sub_title"></v-card-subtitle>
                            </div>
                          </div>
                        </v-card>
                      </template>
                    </v-hover>
                  </v-col>
                </v-row>
              </v-container>
            </v-carousel-item>
          </v-carousel>
        </div>
        <div class="cart-group">
          <div>{{ user }}님의 장보기 리스트</div>
          <v-card
            flat
            color="green lighten-2"
            height="250px"
            style="overflow: scroll; border-radius: 25px"
          >
            <v-card-text>
              <v-container fluid>
                <div style="color: white">
                  <v-icon>mdi-cart</v-icon>장보기 목록
                </div>
                <v-row v-for="(c, index) in checklist" :key="index">
                  <v-col>
                    <v-checkbox
                      v-model="checklist[index]"
                      :label="c"
                      color="indigo darken-3"
                      :value="c"
                      hide-details
                    ></v-checkbox>
                  </v-col>
                  <!-- <v-row align="center">
                    <v-checkbox
                      v-model="enabled"
                      hide-details
                      class="shrink mr-2 mt-0"
                    ></v-checkbox>
                    <v-text-field
                      :disabled="!enabled"
                      label="I only work if you check the box"
                    ></v-text-field>
                  </v-row>-->
                </v-row>
              </v-container>
            </v-card-text>
          </v-card>
        </div>
        <div class="btn-group">
          <v-hover>
            <template #default="{ hover }">
              <v-btn :elevation="hover ? 24 : 6" class="mx-2" fab dark x-large color="amber">
                <v-icon dark>mdi-android</v-icon>
              </v-btn>
            </template>
          </v-hover>
          <v-hover>
            <template #default="{ hover }">
              <v-btn :elevation="hover ? 24 : 6" class="mx-2" fab dark x-large color="amber">
                <v-icon dark>mdi-android</v-icon>
              </v-btn>
            </template>
          </v-hover>
          <v-hover>
            <template #default="{ hover }">
              <v-btn :elevation="hover ? 24 : 6" class="mx-2" fab dark x-large color="amber">
                <v-icon dark>mdi-android</v-icon>
              </v-btn>
            </template>
          </v-hover>
        </div>
        <div class="chart-group mt-7 mb-7">
          <div>오늘 {{ time }} 기준</div>
          <div class="chart-header">
            <div>Let'cipe차트</div>
            <div>전체보기</div>
          </div>
          <div class="chart-chips-group">
            <v-chip
              v-for="(tag, i) in tag_set"
              :key="i"
              class="tag-set ma-1"
              close
              color="green"
              outlined
            >{{ tag }}</v-chip>
          </div>
          <v-row>
            <v-col>
              <v-hover v-for="(data, i) in lecipeData" :key="i">
                <template #default="{ hover }">
                  <v-card
                    :elevation="hover ? 24 : 6"
                    :class="
                      hover ? 'light-green lighten-3' : 'light-green lighten-2'
                    "
                    class="lecipe-list-group mx-auto mt-2 mb-2 d-flex align-center"
                    @click="moveDetail"
                  >
                    <div class="ml-4">{{ i }}</div>
                    <v-list-item three-line>
                      <v-list-item-avatar tile size="57">
                        <v-img
                          elevation="10"
                          src="https://2bob.co.kr/data/recipe/20191212142613-HV8JG.jpg"
                          style="border-radius: 5px"
                        ></v-img>
                      </v-list-item-avatar>
                      <v-list-item-content>
                        <v-list-item-title class="mb-1">{{ data.title }}</v-list-item-title>
                        <v-list-item-subtitle>
                          {{
                          data.sub_title
                          }}
                        </v-list-item-subtitle>
                      </v-list-item-content>
                    </v-list-item>
                    <v-btn class="mr-3" fab dark x-small color="pink" outlined>
                      <v-icon dark>mdi-heart</v-icon>
                    </v-btn>
                  </v-card>
                </template>
              </v-hover>
            </v-col>
          </v-row>
        </div>
        <div class="ingrediant-base-group mt-2 mb-3">
          <div>최근 먹은 두부 토마토 파스타</div>
          <div class="chart-header">
            <div>같은 재료가 들어간 레시피 더보기</div>
            <div>전체보기</div>
          </div>
          <div class="rec-imgs-group d-flex justify-space-between">
            <v-avatar
              v-for="(ref, i) in refImg"
              :key="i"
              size="130"
              tile
              class="mr-2"
              @click="moveDetail"
            >
              <v-img class="ref-imgs" :src="'https://2bob.co.kr/' + ref.url">
                <div class="ref-wrap">
                  <v-card-title class="ref-title">{{ ref.title }}</v-card-title>
                  <v-card-subtitle class="ref-subtitle">
                    {{
                    ref.sub_title
                    }}
                  </v-card-subtitle>
                </div>
              </v-img>
            </v-avatar>
          </div>
        </div>
        <div class="lecipe-base-group mt-3 mb-2">
          <div>이런 레시피리스트 어때요?</div>
          <div class="chart-header">
            <div>{{ user }}님 맞춤 추천</div>
            <div>전체보기</div>
          </div>
          <div class="rec-imgs-group d-flex justify-space-between">
            <v-avatar
              v-for="(ref, i) in refImg"
              :key="i"
              size="130"
              tile
              class="mr-2"
              @click="moveListDetail"
            >
              <v-img class="ref-imgs" :src="'https://2bob.co.kr/' + ref.url">
                <div class="ref-wrap">
                  <v-card-title class="ref-title">{{ ref.title }}</v-card-title>
                  <v-card-subtitle class="ref-subtitle">
                    {{
                    ref.sub_title
                    }}
                  </v-card-subtitle>
                </div>
              </v-img>
            </v-avatar>
          </div>
        </div>
      </v-container>
    </v-app>
  </div>
</template>

<script>
export default {
  name: 'MainPage',
  data() {
    return {
      user: '렛싸피',
      userPlayList: [
        {
          url: 'data/recipe/20191212142613-HV8JG.jpg',
          sub_title: '만소~만소',
          title: '만두소시지꼬치',
          content:
            '소떡소떡을 응용한 만두요리예요. 떡 대신 만두가 실하게 자리잡았답니다. 추억의 케첩 양념에 고소함을 더할 마요네즈도 꼭 곁들여주세요.',
          ingrediants: {
            must: '냉동 납작만두(15개), 소시지(12개), 마요네즈(2)',
            option: '고추장(1)+케첩(4)+올리고당(2)',
            souce: '',
          },
        },
        {
          url: 'data/recipe/20170419170950-RSFQM.jpg',
          sub_title: '알싸한 파로 승부 걸었다',
          title: '매운파골뱅이무침',
          content:
            '고춧가루와 고추만 맵다고 생각했다면 큰 오산. 골뱅이무침에 소면 대신 파채를 듬뿍 곁들여보세요. 아삭하고 알싸한 파의 매운맛,이번에 제대로 경험할 거예요.',
          ingrediants: {
            must: '콩나물(2줌=150g),통조림 골뱅이(1캔=400g), 대파(4대×20cm=대파채 4컵 분량=180g)',
            option: '풋고추(1개)',
            souce: '소금(0.2)',
          },
        },
      ],
      refImg: [
        {
          url: 'data/recipe/20210810152825-4Y20E.jpg',
          sub_title: '끝맛이 개운한',
          title: '오이찜닭',
        },
        {
          url: 'data/recipe/20210810142007-EYPBD.jpg',
          sub_title: '색다른 꿀조합',
          title: '명란 오이무침',
        },
        {
          url: 'data/recipe/20210713113307-VT9JZ.jpg',
          sub_title: '식감이 재밌는',
          title: '소고기 오이볶음밥',
        },
        {
          url: 'data/recipe/20210708104052-PX6S9.jpg',
          sub_title: '오래도록 아삭한',
          title: '오이 물김치',
        },
        {
          url: 'data/recipe/20220825153420-BD8U3.png',
          sub_title: '다이어터를 위한 후식냉면',
          title: '오이냉면',
        },
      ],
      tag_set: ['Now', '최신', '추석', '쉐프의리스트', '더보기'],
      lecipeData: [
        {
          url: '',
          sub_title: '다이어터를 위한 후식냉면',
          title: '오이냉면',
        },
        {
          url: '',
          sub_title: '다이어터를 위한 후식냉면',
          title: '오이냉면',
        },
        {
          url: '',
          sub_title: '다이어터를 위한 후식냉면',
          title: '오이냉면',
        },
      ],
      checklist: ['양파', '오이', '토마토', '대파', '쪽마늘'],
      time: '',
    }
  },
  created() {
    setInterval(this.findnow.bind(this), 1000)
  },
  methods: {
    findnow() {
      const today = new Date()
      const hours = ('0' + today.getHours()).slice(-2)
      const minutes = ('0' + today.getMinutes()).slice(-2)
      const seconds = ('0' + today.getSeconds()).slice(-2)
      this.time = hours + ':' + minutes + ':' + seconds
      // console.log(this.time)
    },
    moveListDetail() {
      this.$router.push('/recipelist/detail')
    },
    moveDetail() {
      this.$router.push('/recipe/detail')
    },
  },
}
</script>

<style scoped>
@font-face {
  font-family: 'LeeSeoyun';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2202-2@1.0/LeeSeoyun.woff')
    format('woff');
  font-weight: bold;
  font-style: normal;
}

* {
  font-family: 'LeeSeoyun';
  /* border: 1px solid rgb(42, 55, 246); */
}

.main-container {
  height: 100%;
  padding: 10%;
  /* background-image: url('/bg/bg_img.png'); */
  background-repeat: repeat;
  background-color: white;
  /* background-color: rgba(255, 255, 221, 0.771); */
  color: black;
  margin-top: 70px;
  margin-bottom: 70px;
}
.title {
  font-size: x-large;
  font-family: 'LeeSeoyun';
}
.v-application .title {
  font-family: 'LeeSeoyun' !important;
  font-size: x-large !important;
}
.title-wrap {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.title-imgs {
  display: flex;
  overflow: auto;
  cursor: pointer;
  transform: translate3d(0, 0, 0);
}
.card {
  margin-right: 10%;
  width: 150px;
}
.ref-imgs {
  width: 150px;
  height: 150px !important;
  cursor: pointer;
}

.ref-wrap {
  width: 150px;
  height: 80px;
  background-color: rgba(49, 49, 49, 0.422);
}
.ref-title {
  /* color: rgb(0, 0, 0); */
  color: aliceblue;
  text-align: right;
  font-size: large;
  font-weight: bolder;
  display: block;
  overflow: auto;
  text-overflow: ellipsis;
}
.ref-subtitle {
  /* color: rgb(0, 0, 0); */
  color: aliceblue;
  text-align: right;
}
.my-container {
  padding-top: 10%;
  padding-bottom: 10%;
}
.my-title {
  font-family: 'LeeSeoyun' !important;
}
.my-card {
  border-radius: 30px;
  cursor: pointer;
}

.my-lecipe {
  display: flex;
  justify-content: center;
}

/* 캐러셀 css */
/* .v-carousel__controls {
      display: none;
    } */
/* btn-group css */

/* cart-group css */
.cart-group {
  margin-top: 3%;
  margin-bottom: 3%;
}
/* btn-group css */
.btn-group {
  display: flex;
  justify-content: space-between;
}

/* chart-group css */

.chart-header {
  display: flex;
  justify-content: space-between;
}
.chart-chips-group {
  display: flex;
  overflow-x: auto;
}
.tag-set {
  padding: 0 12px;
  display: flex;
  justify-content: center;
  text-align: center;
  cursor: pointer;
}
.lecipe-list-group {
  cursor: pointer;
}

/* rec-imgs-group css */
.rec-imgs-group {
  overflow: auto;
}
.container {
  max-width: 100%;
}
/* 모바일 screen */
@media (max-width: 400px) {
  .ref-imgs {
    width: 200px;
    height: 200px;
  }

  .imgs {
    justify-content: space-between;
    overflow-x: auto;
  }
}
</style>
