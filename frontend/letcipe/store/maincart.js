export const state = () => ({
  remainIngredient: [],
})

export const mutations = {
  SET_REMAIN_INGREDIENT(state, remains) {
    state.remainIngredient = remains
  },
  CLEAR_REMAIN_INGREDIENT(state, remains) {
    state.remainIngredient = []
  },
}

export const getters = {}

export const actions = {
  putRemainIngredient({ commit }, remains) {
    commit('CLEAR_REMAIN_INGREDIENT')
    commit('SET_REMAIN_INGREDIENT', remains)
  },
}
