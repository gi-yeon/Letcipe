import {
  getHistoryList,
  getHistory,
  deleteHistory,
  updateHistory,
  checkHistoryIngredient,
  createReview,
  modifyReview,
  deleteReview,
} from '@/api/history'

export const state = () => ({
  historyList: [],
  history: {},
  reviewModifySuccess: false,
})

export const mutations = {
  SET_HISTORY_LIST(state, historyList) {
    state.historyList = historyList
    // console.log(state.historyList)
  },
  SET_HISTORY(state, history) {
    state.history = history
  },
  SET_REVIEWMODIFY_TRUE(state) {
    state.reviewModifySuccess = true
  },
  SET_REVIEWMODIFY_FALSE(state) {
    state.reviewModifySuccess = false
  },
  CLEAR_HISTORY_LIST(state) {
    state.historyList = []
  },
  CLEAR_HISTORY(state) {
    state.history = {}
  },
}

export const getters = {}

export const actions = {
  async getHistoryList({ commit }) {
    await getHistoryList(
      ({ data }) => {
        commit('SET_HISTORY_LIST', data)
        console.log('히스토리목록가져오기 성공!')
        // console.log(data)
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async getHistory({ commit }, historyId) {
    await getHistory(
      historyId,
      ({ data }) => {
        commit('SET_HISTORY', data)
        // console.log(data)
        console.log('히스토리한개가져오기 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async deleteHistory({ commit }, historyId) {
    await deleteHistory(
      historyId,
      ({ data }) => {
        commit('CLEAR_HISTORY')
        // console.log(data)
        // console.log('히스토리 레시피 삭제 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async updateHistory({ commit }, history) {
    await updateHistory(
      history,
      ({ data }) => {
        // console.log(data)
        console.log('히스토리 프로세스 변경 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async checkHistoryIngredient({ commit }, historyIngredientId) {
    await checkHistoryIngredient(
      historyIngredientId,
      ({ data }) => {
        // console.log(data)
        console.log('히스토리 장보기목록 checked 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async createReview({ commit }, hitoryReview) {
    await createReview(
      hitoryReview,
      ({ data }) => {
        // console.log(data)
        console.log('히스토리 리뷰 등록 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async modifyReview({ commit }, hitoryReview) {
    await modifyReview(
      hitoryReview,
      ({ data }) => {
        // console.log(data)
        commit('SET_REVIEWMODIFY_TRUE')
        console.log('히스토리 리뷰 수정 성공!')
      },
      (error) => {
        commit('SET_REVIEWMODIFY_FALSE')
        console.log(error)
      }
    )
  },
  async deleteReview({ commit }, hitoryReview) {
    await deleteReview(
      hitoryReview,
      ({ data }) => {
        // console.log(data)
        console.log('히스토리 리뷰 삭제 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
}
