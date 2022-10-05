<template>
  <div id="app">
    <v-app id="inspire">
      <div class="mycomment-page">
        <v-container class="mycomment-container d-flex-row">
          <div class="mycomment-head-wrap">
            <div class="d-flex justify-space-between pb-3">
              <div>
                <v-icon @click="moveMypage">mdi-window-close</v-icon>
              </div>
              <div style="font-size: x-large">댓글 관리</div>
              <v-icon>mdi-blank</v-icon>
            </div>
          </div>

          <!-- <div>
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
          </div> -->
          <v-row>
            <v-col align="center">
              <div v-for="(comment, i) in comments" :key="i" style="width: 90%">
                <div class="mx-auto mt-2 mb-2 d-flex align-center">
                  <!-- <v-icon
                    v-if="!isChecked[i]"
                    class="mr-3"
                    @click="addChecked(i)"
                    >mdi-check-circle-outline</v-icon
                  >
                  <v-icon v-else class="mr-3" @click="addChecked(i)"
                    >mdi-check-circle</v-icon
                  > -->
                  <v-list-item
                    three-line
                    style="border: 1px solid black !important"
                    align="left"
                  >
                    <v-list-item-content>
                      <v-row>
                        <v-col>
                          <v-list-item-subtitle>{{
                            nickname
                          }}</v-list-item-subtitle></v-col
                        ><v-col align="right">
                          <v-list-item-subtitle>{{
                            comment.regTime.split('T')[0]
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

                      <v-list-item-content
                        style="
                          display: inline-block;
                          text-overflow: ellipsis;
                          white-space: nowrap;
                          overflow: hidden;
                        "
                        @click="commentDetail(i)"
                      >
                        {{ comment.content }}
                      </v-list-item-content>
                      <v-row
                        ><v-col align="right">
                          <v-icon @click="deleteComment(comment.id)"
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
                          nickname
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
                      >{{ selectedComment.content }}
                    </v-list-item-title>
                    <v-row>
                      <v-col cols="12" align="right">
                        <v-list-item-subtitle
                          @click="moveBoard(selectedComment.id)"
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
                        <v-btn
                          dark
                          style="width: 45%"
                          @click="
                            {
                              deleteComment(selectedComment.id)
                              commentDialog = false
                            }
                          "
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
import { mapActions, mapState, mapMutations } from 'vuex'

export default {
  name: 'MyCommentPage',
  data() {
    return {
      commentNum: 0,
      checkedNum: 0,
      isChecked: [],
      checkedComment: [],
      isAllCheck: false,
      comments: [],
      commentDialog: false,
      selectedComment: {},
    }
  },
  async fetch() {
    this.commentNum = await this.mycommentNum()
    this.comments = await this.mycomment()
    console.log(this.comments)
    console.log(this.commentNum)
  },
  fetchOnServer: false,
  computed: {
    ...mapState('user', ['userId', 'nickname']),
  },
  created() {
    this.initChecked()
  },
  methods: {
    ...mapActions('comment', ['patchComment']),
    ...mapActions('user', ['mycomment', 'mycommentNum']),
    ...mapMutations('recipe', ['SET_RECIPE_ID', 'CLEAR_RECIPE_ID']),
    initChecked() {
      for (let i = 0; i < this.comments.length; i++) {
        this.isChecked.push(false)
      }
    },
    moveMypage() {
      this.$router.push('/user/mypage')
    },
    addChecked(index) {
      this.isAllCheck = false
      if (!this.isChecked[index]) {
        this.checkedComment.push(this.comments[index])
      } else {
        this.checkedComment.splice(index, 1)
      }
      this.isChecked[index] = !this.isChecked[index]
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
    async deleteComment(id) {
      console.log(id)
      this.allChecked()
      console.log('-----------------------------')
      const comment = {
        commentId: id,
      }
      await this.patchComment(comment)
      this.comments = await this.mycomment()
    },
    commentDetail(index) {
      this.selectedComment = this.comments[index]
      this.selectedComment.regTime = this.selectedComment.regTime.split('T')[0]
      this.commentDialog = true
    },
    moveBoard() {
      this.CLEAR_RECIPE_ID()
      this.SET_RECIPE_ID(this.selectedComment.boardId)
      this.$router.push('/recipe/detail')
    },
  },
}
</script>

<style scoped>
.mycomment-page {
  /* padding-top: 70px; */
  padding-bottom: 70px;
  padding: 4%;
}
.mycomment-page-head {
  padding: 4%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  /* border: 1px solid gray; */
}
.mycomment-container {
  position: sticky;
  height: 100%;
  color: black;

  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
}
</style>
