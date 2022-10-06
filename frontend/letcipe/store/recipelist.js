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
  isSucceededtoRecipeList: false,
  recipeListId: '',
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
  SET_ISSUCCESS_TRUE(state) {
    state.isSucceededtoRecipeList = true
  },
  SET_ISSUCCESS_FALSE(state) {
    state.isSucceededtoRecipeList = false
  },
  SET_RECIPE_ID(state, id) {
    state.recipeListId = id
  },
  CLEAR_RECIPE_ID(state) {
    state.recipeListId = null
  },
  SET_RECIPELIST_NAME(state, newName) {
    state.recipeListRes.name = newName
  },
  MODIFY_RECIPE_LIST_ITEM(state,index) {
    state.recipeListItems.splice(index,1)
  }
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
  async createRecipeListAll({ commit }, recipeList) {
    await createRecipeList(
      recipeList,
      ({ data }) => {
        console.log(data)
        commit('SET_ISSUCCESS_TRUE')
      },
      (error) => {
        console.log(error)
        commit('SET_ISSUCCESS_FALSE')
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
        console.log(data)
        console.log('레시피리스트 하나 검색 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async updateRecipeList({ commit }, object) {
    await updateRecipeList(
      object,
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
        console.log('레시피리스트 삭제 성공!')
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
        console.log('레시피리스트  북마크 설정 성공!')
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
        console.log('레시피리스트  북마크 해제 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
}
