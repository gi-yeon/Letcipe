import {
  getComment,
  putComment,
  postComment,
  getCommentNum,
  patchComment,
} from '@/api/comment'

export const state = () => ({
  comments: [],
  commentNum: 0,
})

export const mutations = {
  SET_COMMENT(state, comments) {
    state.comments = comments
  },
  CLEAR_COMMENT(state) {
    state.comments = []
  },
  SET_COMMENT_NUM(state, commentNum) {
    state.commentNum = commentNum
  },
  CLEAR_COMMENT_NUM(state) {
    state.commentNum = 0
  },
}

export const getters = {}

export const actions = {
  async getComment({ commit }, object) {
    commit('CLEAR_COMMENT')
    await getComment(
      {
        boardType: object.boardType,
        boardId: object.boardId,
        size: object.size,
        page: object.page,
      },
      ({ data }) => {
        console.log(JSON.stringify(data))
        commit('SET_COMMENT', data)
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async postComment({ commit }, object) {
    await postComment(
      {
        content: object.content,
        boardId: object.boardId,
        boardType: object.boardType,
      },
      ({ data }) => {},
      (error) => {
        console.log(error)
      }
    )
  },
  async putComment({ commit }, object) {
    await putComment(
      {
        id: object.commentId,
        content: object.content,
      },
      ({ data }) => {},
      (error) => {
        console.log(error)
      }
    )
  },
  async patchComment({ commit }, object) {
    await patchComment(
      {
        id: object.commentId,
      },
      ({ data }) => {},
      (error) => {
        console.log(error)
      }
    )
  },
  async getCommentNum({ commit }, object) {
    commit('CLEAR_COMMENT_NUM')
    await getCommentNum(
      {
        boardType: object.boardType,
        boardId: object.boardId,
      },
      ({ data }) => {
        console.log(data)
        commit('SET_COMMENT_NUM', data)
      },
      (error) => {
        console.log(error)
      }
    )
  },
}
