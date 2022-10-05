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
  isSucceededtoRecipe: false,
  isSucceededtoRecipeDetail: false,
})

export const mutations = {
  SET_RECIPE_DETAIL(state, recipeDetail) {
    state.recipeDetail = recipeDetail
  },
  SET_RECIPE_ID(state, id) {
    state.recipeID = id
  },
  SET_SUCCESS_RECIPE(state) {
    state.isSucceededtoRecipe = true
  },
  SET_FAIL_RECIPE(state) {
    state.isSucceededtoRecipe = false
  },
  SET_SUCCESS_DETAIL(state) {
    state.isSucceededtoRecipeDetail = true
  },
  SET_FAIL_DETAIL(state) {
    state.isSucceededtoRecipeDetail = false
  },
  CLEAR_RECIPE_DETAIL(state) {
    state.recipeDetail = {}
  },
  CLEAR_RECIPE_ID(state) {
    state.recipeID = null
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
  async createRecipeDetail({ commit }, formData) {
    commit('SET_FAIL_RECIPE')
    await createRecipeDetail(
      formData,
      ({ data }) => {
        commit('SET_SUCCESS_RECIPE')
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
      ({ data }) => {},
      (error) => {
        console.log(error)
      }
    )
  },
  async decountRecipeLikes({ commit }, id) {
    await decountRecipeLikes(
      id,
      ({ data }) => {},
      (error) => {
        console.log(error)
      }
    )
  },
  async selectBookmarks({ commit }, recipeId) {
    await selectBookmarks(
      recipeId,
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
