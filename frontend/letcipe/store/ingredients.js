import { searchIngredient } from '@/api/ingredients'

export const state = () => ({
  ingredientsList: [],
})

export const mutations = {
  SET_INGREDIENTS_LIST(state, ingredient) {
    state.ingredientsList = ingredient
    // console.log(ingredient)
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
        // console.log('재료가져오기 성공!')
        const ingreVal = []
        const keys = Object.keys(data)
        // console.log(keys)
        for (let i = 0; i < keys.length; i++) {
          ingreVal.push(data[keys[i]])
        }
        console.log(ingreVal)
        commit('SET_INGREDIENTS_LIST', ingreVal)
      },
      (error) => {
        console.log(error)
      }
    )
  },
}
