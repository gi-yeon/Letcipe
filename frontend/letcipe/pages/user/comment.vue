<template>
  <div id="app">
    <v-app id="inspire">
      <div class="mycomment-container">
        <v-container style="background-color: white; padding: 0%"
          ><v-card-title class="justify-center" style="font-size: 1.7rem"
            >댓글 관리</v-card-title
          ><v-divider></v-divider>
          <div>
            <v-card-subtitle>
              <v-row
                ><v-col>
                  <v-icon v-if="!isAllCheck" class="mr-3" @click="allChecked()"
                    >mdi-check-circle-outline</v-icon
                  ><v-icon v-else class="mr-3" @click="allChecked()"
                    >mdi-check-circle</v-icon
                  >전체선택({{ checkedComment.length }}/{{
                    comments.length
                  }})</v-col
                ><v-col align="right" class="mr-4"
                  ><v-btn small dark @click="deleteComment()"
                    >선택 삭제</v-btn
                  ></v-col
                ></v-row
              ></v-card-subtitle
            >
          </div>
          <v-row>
            <v-col align="center">
              <div v-for="(comment, i) in comments" :key="i" style="width: 90%">
                <div class="mx-auto mt-2 mb-2 d-flex align-center">
                  <v-icon
                    v-if="!isChecked[i]"
                    class="mr-3"
                    @click="addChecked(i)"
                    >mdi-check-circle-outline</v-icon
                  >
                  <v-icon v-else class="mr-3" @click="addChecked(i)"
                    >mdi-check-circle</v-icon
                  >
                  <v-list-item
                    three-line
                    style="border: 1px solid black !important"
                    align="left"
                  >
                    <v-list-item-content @click="commentDetail(i)">
                      <v-row>
                        <v-col>
                          <v-list-item-subtitle>{{
                            comment.user
                          }}</v-list-item-subtitle></v-col
                        ><v-col align="right">
                          <v-list-item-subtitle>{{
                            comment.regTime
                          }}</v-list-item-subtitle></v-col
                        ></v-row
                      >

                      <!-- <v-list-item-title
                        style="
                          text-overflow: ellipsis;
                          overflow: hidden;
                          white-space: nowrap;
                          display: inline-block;
                          font-size: 1.2rem;
                        "
                      > -->

                      <v-list-item-title style="font-size: 1.2rem">
                        {{ comment.content }}
                      </v-list-item-title>
                      <v-row
                        ><v-col align="right">
                          <v-icon @click="deleteComment()"
                            >mdi-delete</v-icon
                          ></v-col
                        ></v-row
                      >
                    </v-list-item-content>
                  </v-list-item>
                </div>
              </div>
            </v-col>
          </v-row>
          <div class="text-center">
            <v-dialog v-model="commentDialog" width="500">
              <v-card>
                <v-list-item
                  three-line
                  style="border: 1px solid black !important"
                  align="left"
                >
                  <v-list-item-content>
                    <v-row class="mb-5">
                      <v-col>
                        <v-list-item-subtitle>{{
                          selectedComment.user
                        }}</v-list-item-subtitle></v-col
                      ><v-col align="right">
                        <v-list-item-subtitle>{{
                          selectedComment.regTime
                        }}</v-list-item-subtitle></v-col
                      ></v-row
                    >

                    <!-- <v-list-item-title
                        style="
                          text-overflow: ellipsis;
                          overflow: hidden;
                          white-space: nowrap;
                          display: inline-block;
                          font-size: 1.2rem;
                        "
                      > -->

                    <v-list-item-title
                      style="font-size: 1.2rem"
                      class="text-wrap pb-4"
                    >
                      {{ selectedComment.content }}
                    </v-list-item-title>
                    <v-row>
                      <v-col cols="12" align="right">
                        <v-list-item-subtitle
                          ><v-btn text
                            >원문 보기 >>
                          </v-btn></v-list-item-subtitle
                        ></v-col
                      ><v-col class="d-flex justify-space-between ml-8 mr-8">
                        <v-btn
                          dark
                          style="width: 45%"
                          @click="commentDialog = false"
                          >닫기</v-btn
                        >
                        <v-btn dark style="width: 45%" @click="deleteComment()"
                          >삭제</v-btn
                        ></v-col
                      ></v-row
                    >

                    <v-row></v-row>
                  </v-list-item-content>
                </v-list-item>
              </v-card>
            </v-dialog>
          </div>
        </v-container></div
    ></v-app>
  </div>
</template>

<script>
export default {
  name: 'MyCommentPage',
  data() {
    return {
      checkedNum: 0,
      isChecked: [],
      checkedComment: [],
      isAllCheck: false,
      comments: [
        {
          user: '나야나',
          board: '1',
          content:
            '밥도둑레시피입니다.밥도둑레시피입니다.밥도둑레시피입니다.밥도둑레시피입니다.밥도둑레시피입니다.밥도둑레시피입니다.밥도둑레시피입니다.',
          regTime: '2022-09-08',
        },
        {
          user: '나야나',
          board: '1',
          content: `밥도둑레시피입니다.
          `,
          regTime: '2022-09-08',
        },
        {
          user: '나야나',
          board: '1',
          content: '밥도둑레시피입니다.',
          regTime: '2022-09-08',
        },
      ],
      commentDialog: false,
      selectedComment: {
        user: null,
        board: null,
        content: null,
        regTime: null,
      },
    }
  },
  created() {
    this.initChecked()
  },
  methods: {
    initChecked() {
      for (let i = 0; i < this.comments.length; i++) {
        this.isChecked.push(false)
      }
    },
    addChecked(index) {
      if (this.isAllCheck) {
        this.isAllCheck = false
        this.checkedComment = []
      }
      if (!this.isChecked[index]) {
        this.isChecked[index] = true
        this.checkedComment.push(this.comments[index])
      } else {
        this.isChecked[index] = false
        this.checkedComment.splice(index, 1)
      }
      console.log(this.isChecked)
      console.log(this.checkedComment)
    },
    allChecked() {
      if (!this.isAllCheck) {
        this.isAllCheck = true
        this.checkedComment = []
        for (let i = 0; i < this.comments.length; i++) {
          this.checkedComment.push(this.comments[i])
        }
        for (let i = 0; i < this.isChecked.length; i++) {
          this.isChecked[i] = true
        }
      } else {
        this.isAllCheck = false
        this.checkedComment = []
        for (let i = 0; i < this.isChecked.length; i++) {
          this.isChecked[i] = false
        }
      }
      console.log(this.checkedComment)
    },
    deleteComment() {},
    commentDetail(index) {
      this.selectedComment.user = this.comments[index].user
      this.selectedComment.board = this.comments[index].board
      this.selectedComment.content = this.comments[index].content
      this.selectedComment.regTime = this.comments[index].regTime
      this.commentDialog = true
    },
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
