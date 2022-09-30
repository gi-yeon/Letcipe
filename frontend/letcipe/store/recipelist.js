import {
  searchRecipeList,
  createRecipeList,
  getRecipeList,
  updateRecipeList,
  deleteRecipeList,
  addRecipeListItem,
  deleteRecipeListItem,
  createRecipeListBookmark,
  deleteRecipeListBookmark,
} from '@/api/recipelist'

export const state = () => ({
  recipeListRes: [],
  recipeListUser: {},
  recipeListItems: [],
  recipeListRepImg: '',
})

export const mutations = {
  SET_RECIPELIST(state, recipeListRes) {
    state.recipeListRes = recipeListRes
    state.recipeListUser = recipeListRes.user
    recipeListRes.recipeListItems.forEach((element) => {
      element.recipe.content = element.recipe.content.replaceAll('<br>', '  ')
    })
    state.recipeListItems = recipeListRes.recipeListItems
    state.recipeListRepImg = recipeListRes.recipeListItems[0].recipe.repImg
  },
}

export const getters = {}

export const actions = {
  async searchRecipeList({ commit }, query, page, size) {
    await searchRecipeList(
      query,
      page,
      size,
      ({ data }) => {
        commit('SET_RECIPELIST', data)
        // console.log(data)
        // console.log('레시피리스트 검색 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async createRecipeList({ commit }, recipelist) {
    await createRecipeList(
      recipelist,
      ({ data }) => {
        // console.log(data)
        // console.log('레시피리스트 등록 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async getRecipeList({ commit }, recipeListId) {
    await getRecipeList(
      recipeListId,
      ({ data }) => {
        commit('SET_RECIPELIST', data)
        // console.log(data)
        // console.log('레시피리스트 하나 검색 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async updateRecipeList({ commit }, recipeListId, updateRL) {
    await updateRecipeList(
      recipeListId,
      updateRL,
      ({ data }) => {
        // console.log(data)
        // console.log('레시피리스트 하나 수정 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async deleteRecipeList({ commit }, recipeListId) {
    await deleteRecipeList(
      recipeListId,
      ({ data }) => {
        // console.log(data)
        // console.log('레시피리스트 삭제 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async addRecipeListItem({ commit }, RLItem) {
    await addRecipeListItem(
      RLItem,
      ({ data }) => {
        // console.log(data)
        // console.log('레시피리스트에 레시피 추가 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async deleteRecipeListItem({ commit }, RLItem) {
    await deleteRecipeListItem(
      RLItem,
      ({ data }) => {
        // console.log(data)
        // console.log('레시피리스트에 레시피 삭제 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async createRecipeListBookmark({ commit }, recipeListId) {
    await createRecipeListBookmark(
      recipeListId,
      ({ data }) => {
        // console.log(data)
        // console.log('레시피리스트  북마크 설정 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async deleteRecipeListBookmark({ commit }, recipeListId) {
    await deleteRecipeListBookmark(
      recipeListId,
      ({ data }) => {
        // console.log(data)
        // console.log('레시피리스트  북마크 해제 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
}
