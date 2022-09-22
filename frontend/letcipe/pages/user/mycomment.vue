<template>
  <div id="app">
    <v-app id="inspire">
      <div class="mycomment-container">
        <v-container style="padding: 0%"
          ><v-card-title
            class="justify-center"
            style="background-color: white; font-size: 1.7rem"
            >댓글 관리</v-card-title
          >
          <div>
            <v-card-subtitle
              >전체선택({{ checkedNum }}/{{ comments.length }})</v-card-subtitle
            >
          </div>
          <v-row>
            <v-col align="center">
              <div v-for="(comment, i) in comments" :key="i" style="width: 90%">
                <div class="mx-auto mt-2 mb-2 d-flex align-center">
                  <v-icon class="mr-3">mdi-check-circle-outline</v-icon>
                  <!-- /<v-icon class="mr-3">mdi-check-circle</v-icon> -->
                  <v-list-item
                    three-line
                    style="border: 1px solid black !important"
                    align="left"
                  >
                    <v-list-item-content>
                      <v-list-item-title class="mb-1">{{
                        comment.content
                      }}</v-list-item-title>
                      <v-list-item-subtitle>{{
                        comment.user
                      }}</v-list-item-subtitle>
                      <v-list-item-subtitle>{{
                        comment.regTime
                      }}</v-list-item-subtitle>
                    </v-list-item-content>
                  </v-list-item>
                </div>
              </div>
            </v-col>
          </v-row>
        </v-container>
      </div></v-app
    >
  </div>
</template>

<script>
export default {
  name: 'MyCommentPage',
  data() {
    return {
      checkedNum: 0,
      checkedComment: [],

      comments: [
        {
          user: '나야나',
          board: '1',
          content: '밥도둑레시피입니다.',
          regTime: '2022-09-08',
        },
        {
          user: '나야나',
          board: '1',
          content: '밥도둑레시피입니다.',
          regTime: '2022-09-08',
        },
        {
          user: '나야나',
          board: '1',
          content: '밥도둑레시피입니다.',
          regTime: '2022-09-08',
        },
      ],
    }
  },
}
</script>

<style scoped>
.mycomment-container {
  position: sticky;
  height: 100%;

  padding: 8% 0% 0% 0%;
  background-image: url('/bg/bg_img.png');
  background-repeat: repeat;

  color: black;
}
</style>
