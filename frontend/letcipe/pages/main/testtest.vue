<template>
  <div id="app">
    <v-app>
      <div v-for="(item, index) in letcipeList" :key="index" class="center">
        <div class="card">
          <div class="additional">
            <div class="user-card">
              <div class="level center">{{ item.sub_title }}</div>
              <img
                width="100"
                src="https://2bob.co.kr/data/recipe/20191212142613-HV8JG.jpg"
                role="img"
                aria-labelledby="title desc"
                class="center"
                style="border-radius: 100px; padding: 2%; height: 100px"
              />

              <div class="points center">5,312 Points</div>
            </div>
            <div class="more-info">
              <h1>{{ item.nicknm }}</h1>
              <div class="coords">
                <span>필수재료:</span>
                <span class="ingrediants">{{ item.ingrediants.must }}</span>
              </div>
              <div class="coords">
                <span>추가재료:</span>
                <span class="ingrediants">{{ item.ingrediants.option }}</span>
              </div>
              <div class="stats">
                <div>
                  <v-btn icon small>
                    <v-icon size="20">mdi-thumb-up</v-icon>
                  </v-btn>
                  <div class="value">2</div>
                </div>
                <div>
                  <v-btn icon small>
                    <v-icon size="20">mdi-bookmark</v-icon>
                  </v-btn>
                  <div class="value">27</div>
                </div>
                <div>
                  <v-btn icon small>
                    <v-icon size="20"> mdi-cart </v-icon>
                  </v-btn>
                  <div class="value">123</div>
                </div>
                <div>
                  <v-btn icon small>
                    <v-icon size="20"> mdi-eye </v-icon>
                  </v-btn>
                  <div class="value infinity">∞</div>
                </div>
              </div>
            </div>
          </div>
          <div class="general">
            <h1>{{ item.nicknm }}</h1>
            <p>
              {{ item.content }}
            </p>
            <span class="more">{{ item.date }}</span>
          </div>
        </div>
      </div>
    </v-app>
  </div>
</template>

<script>
export default {
  name: 'TestTest',
  data() {
    return {
      letcipeList: [
        {
          nicknm: '꼬치마법사',
          url: 'data/recipe/20191212142613-HV8JG.jpg',
          sub_title: '만소~만소',
          title: '만두소시지꼬치',
          date: '2022-09-10',
          content:
            '소떡소떡을 응용한 만두요리예요. 떡 대신 만두가 실하게 자리잡았답니다. 추억의 케첩 양념에 고소함을 더할 마요네즈도 꼭 곁들여주세요.',
          ingrediants: {
            must: '냉동 납작만두(15개), 소시지(12개), 마요네즈(2)',
            option: '고추장(1)+케첩(4)+올리고당(2)',
            souce: '',
          },
          rating: 4.3,
        },
        {
          nicknm: '꼬치마법사',
          url: 'data/recipe/20191212142613-HV8JG.jpg',
          sub_title: '만소~만소',
          title: '만두소시지꼬치',
          date: '2022-09-10',
          content:
            '소떡소떡을 응용한 만두요리예요. 떡 대신 만두가 실하게 자리잡았답니다. 추억의 케첩 양념에 고소함을 더할 마요네즈도 꼭 곁들여주세요.',
          ingrediants: {
            must: '냉동 납작만두(15개), 소시지(12개), 마요네즈(2)',
            option: '고추장(1)+케첩(4)+올리고당(2)',
            souce: '',
          },
          rating: 4.3,
        },
        {
          nicknm: '꼬치마법사',
          url: 'data/recipe/20191212142613-HV8JG.jpg',
          sub_title: '만소~만소',
          title: '만두소시지꼬치',
          date: '2022-09-10',
          content:
            '소떡소떡을 응용한 만두요리예요. 떡 대신 만두가 실하게 자리잡았답니다. 추억의 케첩 양념에 고소함을 더할 마요네즈도 꼭 곁들여주세요.',
          ingrediants: {
            must: '냉동 납작만두(15개), 소시지(12개), 마요네즈(2)',
            option: '고추장(1)+케첩(4)+올리고당(2)',
            souce: '',
          },
          rating: 4.3,
        },
      ],
    }
  },
  methods: {},
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Abel');

html,
body {
  background: #fceeb5;
  font-family: Abel, Arial, Verdana, sans-serif;
}

.center {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  padding: 2%;
}
.skin {
  fill: #eab38f;
}
.eyes {
  fill: #1f1f1f;
}
.hair {
  fill: #2f1b0d;
}
.line {
  fill: none;
  stroke: #2f1b0d;
  stroke-width: 2px;
}
.card {
  width: 60vh;
  height: 100%;
  background-color: #fff;
  background: linear-gradient(#f8f8f8, #fff);
  box-shadow: 0 8px 16px -8px rgba(0, 0, 0, 0.4);
  border-radius: 6px;
  overflow: hidden;
  position: relative;
  margin: 1.5rem;
}

.card h1 {
  text-align: center;
}

.card .additional {
  position: absolute;
  width: 33%;
  height: 100%;
  background: linear-gradient(#de685e, #ee786e);
  transition: width 0.4s;
  overflow: hidden;
  z-index: 2;
}

.card.green .additional {
  background: linear-gradient(#92bca6, #a2ccb6);
}

.card:hover .additional {
  width: 100%;
  border-radius: 0 5px 5px 0;
}

.card .additional .user-card {
  width: 140px;
  height: 100%;
  padding: 10%;
  position: relative;
  float: left;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}

.card .additional .user-card::after {
  content: '';
  display: block;
  position: absolute;
  top: 10%;
  right: -2px;
  height: 80%;
  border-left: 2px solid rgba(0, 0, 0, 0.025);
}

.card .additional .user-card .level,
.card .additional .user-card .points {
  color: #fff;
  text-transform: uppercase;
  font-size: 0.75em;
  font-weight: bold;
  background: rgba(0, 0, 0, 0.15);
  padding: 0.125rem 0.75rem;
  border-radius: 100px;
  white-space: nowrap;
  height: 12%;
}

.card .additional .more-info {
  width: 300px;
  float: left;
  position: absolute;
  left: 140px;
  height: 100%;
}

.card .additional .more-info h1 {
  color: #fff;
  margin-bottom: 0;
}

.card.green .additional .more-info h1 {
  color: #224c36;
}

.card .additional .coords {
  margin: 0 2rem;
  color: #fff;
  font-size: 0.9rem;
  padding: 1%;
}

.card.green .additional .coords {
  color: #325c46;
}

.card .additional .coords span + span {
  float: right;
}

.card .additional .stats {
  font-size: 0.5rem;
  display: flex;
  position: absolute;
  bottom: 0.3rem;
  left: 1rem;
  right: 1rem;
  top: auto;
  color: #fff;
}

.card.green .additional .stats {
  color: #325c46;
}

.card .additional .stats > div {
  flex: 1;
  text-align: center;
}

.card .additional .stats i {
  display: block;
}

.card .additional .stats div.title {
  font-size: 0.75rem;
  font-weight: bold;
  text-transform: uppercase;
}

.card .additional .stats div.value {
  font-size: 1rem;
  font-weight: bold;
  line-height: 1.5rem;
}

.card .additional .stats div.value.infinity {
  font-size: 1.5rem;
}

.card .general {
  width: 67%;
  height: 100%;
  position: absolute;
  color: black;
  top: 0;
  right: 0;
  z-index: 1;
  box-sizing: border-box;
  padding: 1rem;
  padding-top: 0;
}

.card .general .more {
  position: absolute;
  bottom: 1rem;
  right: 1rem;
  font-size: 0.9em;
}

.ingrediants {
  font-size: small;
}
</style>
