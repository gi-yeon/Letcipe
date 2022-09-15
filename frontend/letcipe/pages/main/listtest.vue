<template>
  <div id="app">
    <v-app>
      <v-card
        v-for="(item, i) in letcipeList"
        :key="i"
        class="mx-auto elevation-20"
        color="amber darken-1"
        dark
        style="max-width: 400px"
      >
        <v-row justify="space-between">
          <v-col cols="8">
            <v-card-title>
              <div>
                <div class="text-h5">{{ item.title }}</div>
                <div>{{ item.sub_title }}</div>
                <div>({{ item.date }})</div>
              </div>
            </v-card-title>
          </v-col>
          <v-img
            class="shrink ma-2"
            contain
            :src="'https://2bob.co.kr/' + item.url"
            style="flex-basis: 125px"
          ></v-img>
        </v-row>
        <v-divider dark></v-divider>
        <v-card-actions class="pa-4">
          만든이: {{ item.nicknm }}
          <v-spacer></v-spacer>
          <span class="grey--text text--lighten-2 text-caption mr-2">
            ({{ item.rating }})
          </span>
          <v-rating
            v-model="item.rating"
            background-color="white"
            color="amber lighten-5"
            dense
            half-increments
            hover
            size="18"
          ></v-rating>
        </v-card-actions>
      </v-card>
    </v-app>
  </div>
</template>

<script>
export default {
  name: 'ListPage',
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
      ],
    }
  },
}
</script>

<style scoped></style>
