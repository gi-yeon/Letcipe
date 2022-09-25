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

export const state = () => ({})

export const mutations = {}

export const getters = {}

export const actions = {
  async RecipeDetail({ commit }, recipeId) {
    await RecipeDetail(
      recipeId,
      ({ data }) => {
        commit('')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async selectRecipeDetail({ commit }) {
    await selectRecipeDetail(
      ({ data }) => {
        commit('')
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
