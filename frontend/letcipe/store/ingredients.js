import { searchIngredient } from '@/api/ingredients'

export const state = () => ({
  ingredientsList: [],
})

export const mutations = {
  SET_INGREDIENTS_LIST(state, ingredient) {
    state.ingredientsList = ingredient
  },
  CLEAR_INGREDIENTS_LIST(state) {
    state.ingredientsList = []
  },
}
 
export const getters = {}

export const actions = {
  async searchIngredient({ commit }, keyword) {
    await searchIngredient(
      keyword,
      ({ data }) => {
        commit('SET_INGREDIENTS_LIST', data)
        // console.log('재료가져오기 성공!')
        // console.log(data)
      },
      (error) => {
        console.log(error)
      }
    )
  },
}
