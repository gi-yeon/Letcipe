import {
  RecipeDetail,
  createRecipeDetail,
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
  recipeID: null,
})

export const mutations = {
  SET_RECIPE_DETAIL(state, recipeDetail) {
    state.recipeDetail = recipeDetail
  },
  SET_RECIPE_ID(state, id) {
    state.recipeID = id
  },

  CLEAR_RECIPE_DETAIL(state) {
    state.recipeDetail = {}
  },
  CLEAR_RECIPE_ID(state, id) {
    state.recipeID = null
  },
}

export const getters = {}

export const actions = {
  async RecipeDetail({ commit }, recipeId) {
    console.log(recipeId)
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
  async createRecipeDetail({ commit }, formData) {
    await createRecipeDetail(
      formData,
      ({ data }) => {
        commit('')
        console.log('등록 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async updateRecipeDetail({ commit }, recipeId, formData) {
    await updateRecipeDetail(
      recipeId,
      formData,
      ({ data }) => {
        commit('')
        console.log('수정 성공!')
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
  async countRecipeLikes({ commit }, recipeId) {
    await countRecipeLikes(
      recipeId,
      ({ data }) => {
        commit('')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async decountRecipeLikes({ commit }, id) {
    await decountRecipeLikes(
      id,
      ({ data }) => {
        commit('')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async selectBookmarks({ commit }, recipeId) {
    await selectBookmarks(
      recipeId,
      commit,
      ({ data }) => {
        console.log('북마크 등록 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async deleteBookmarks({ commit }, id) {
    await deleteBookmarks(
      id,
      ({ data }) => {
        commit('')
        console.log('북마크 해제 성공!')
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
