import {
  RecipeDetail,
  selectRecipeDetail,
  updateRecipeDetail,
  patchRecipeDetail,
  countRecipeLikes,
  decountRecipeLikes,
  selectBookmarks,
  deleteBookmarks,
  selectComment,
  modifyComment,
  deleteComment,
} from '@/api/recipe'

export const state = () => ({
  recipeDetail: {},
})

export const mutations = {
  SET_RECIPE_DETAIL(state, recipeDetail) {
    state.recipeDetail = recipeDetail
  },

  CLEAR_RECIPE_DETAIL(state) {
    state.recipeDetail = {}
  },
}

export const getters = {}

export const actions = {
  async RecipeDetail({ commit }, recipeId) {
    await RecipeDetail(
      recipeId,
      ({ data }) => {
        commit('SET_RECIPE_DETAIL', data)
        // console.log(data)
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async selectRecipeDetail({ commit }, formData) {
    await selectRecipeDetail(
      console.log(formData),

      JSON.stringify(formData),
      ({ data }) => {
        commit('')
        console.log('등록 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async updateRecipeDetail({ commit }, recipeId) {
    await updateRecipeDetail(
      recipeId,
      ({ data }) => {
        commit('')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async patchRecipeDetail({ commit }, recipeId) {
    await patchRecipeDetail(
      recipeId,
      ({ data }) => {
        commit('')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async countRecipeLikes({ commit }) {
    await countRecipeLikes(
      ({ data }) => {
        commit('')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async decountRecipeLikes({ commit }) {
    await decountRecipeLikes(
      ({ data }) => {
        commit('')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async selectBookmarks({ commit }) {
    await selectBookmarks(
      ({ data }) => {
        commit('')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async deleteBookmarks({ commit }) {
    await deleteBookmarks(
      ({ data }) => {
        commit('')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  selectComment({ commit }) {
    selectComment(
      (data) => {
        commit('')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  modifyComment({ commit }) {
    modifyComment(
      (data) => {
        commit('')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  deleteComment({ commit }) {
    deleteComment(
      (data) => {
        commit('')
      },
      (error) => {
        console.log(error)
      }
    )
  },
}
