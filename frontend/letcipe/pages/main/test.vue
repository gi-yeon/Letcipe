<template>
  <v-app class="main-wrap">
    <v-container class="main-container">
      <v-row class="title-wrap">
        <v-row class="title"> {{ user }}님이 좋아할 레시피 </v-row>
        <v-row class="title imgs">
          <v-card v-for="ref in refImg" :key="ref">
            <v-img class="ref-imgs" :src="'https://2bob.co.kr/' + ref"> </v-img>
          </v-card>
        </v-row>
      </v-row>
    </v-container>
  </v-app>
</template>

<script>
export default {
  name: 'TestTest',
  data() {
    return {
      user: '렛싸피',
      refImg: [
        'data/recipe/20210810152825-4Y20E.jpg',
        'data/recipe/20210810142007-EYPBD.jpg',
        'data/recipe/20210713113307-VT9JZ.jpg',
        'data/recipe/20210708104052-PX6S9.jpg',
        'data/recipe/20220825153420-BD8U3.png',
      ],
    }
  },
}
</script>

<style scoped>
* {
  border: 3px solid orange;
}
.main-wrap {
  height: 100vh;
  width: 100vw;
  background-image: url('/bg/bg_img.png');
}
.main-container {
  height: 100vh;
  width: 90%;
  display: flex;
  justify-content: center;
}
.title-wrap {
  width: 90%;
}
.title {
  font-size: xx-large;
}
.imgs {
  justify-content: space-between;
  overflow-x: scroll;
}
.ref-imgs {
  width: 100px;
  height: 100px;
}
@media (min-width: 800px) {
  .container {
    max-width: 100%;
    display: flex;
    justify-content: center;
  }
  .imgs {
    justify-content: space-between;
    overflow-x: scroll;
  }
  .ref-imgs {
    width: 900px;
    height: 900px;
  }
  .title-wrap {
  }
}
</style>
