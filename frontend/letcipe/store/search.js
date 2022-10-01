import {
  getRecipes,
  getRecipesIngre,
  getRecipeList,
  getHotRecipes,
} from '@/api/search'
export const state = () => ({
  recipes: [],
  recipesIngre: [],
  recipeLists: [],
  hotRecipes: [],
  hotTitle: '',
})

export const mutations = {
  SET_RECIPE(state, recipes) {
    state.recipes = recipes
  },
  SET_HOT_RECIPE(state, hotRecipes) {
    state.hotRecipes = hotRecipes
  },
  SET_HOT_TITLE(state, hotTitle) {
    state.hotTitle = hotTitle
  },
  CLEAR_RECIPE(state) {
    state.recipes = []
  },
  CLEAR_HOT_RECIPE(state) {
    state.hotRecipes = []
  },
  CLEAR_HOT_TITLE(state) {
    state.hotTitle = ''
  },
  SET_RECIPE_INGRE(state, recipesIngre) {
    state.recipesIngre = recipesIngre
  },
  CLEAR_RECIPE_INGRE(state) {
    state.recipesIngre = []
  },
  SET_RECIPE_LIST(state, recipeLists) {
    state.recipeLists = recipeLists
  },
  CLEAR_RECIPE_LIST(state) {
    state.recipeLists = ''
  },
}

export const getters = {}

export const actions = {
  async getRecipes({ commit }, object) {
    commit('CLEAR_RECIPE')
    commit('CLEAR_RECIPE_LIST')
    await getRecipes(
      {
        keyword: object.keyword,
        size: object.size,
        page: object.page,
      },
      ({ data }) => {
        commit('SET_RECIPE', data)
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async getHotRecipes({ commit }, object) {
    commit('CLEAR_HOT_RECIPE')
    commit('CLEAR_HOT_TITLE')
    await getHotRecipes(
      {
        size: object.size,
        page: object.page,
      },
      ({ data }) => {
        console.log(data)
        commit('SET_HOT_RECIPE', data.report)
        commit('SET_HOT_TITLE', data.title)
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async getRecipesIngre({ commit }, object) {
    commit('CLEAR_RECIPE_INGRE')
    await getRecipesIngre(
      {
        ingredients: object.ingredients,
        size: object.size,
        page: object.page,
      },
      ({ data }) => {
        commit('SET_RECIPE_INGRE', data)
        console.log(data)
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async getRecipeList({ commit }, object) {
    commit('CLEAR_RECIPE_LIST')
    commit('CLEAR_RECIPE')
    console.log(object)
    await getRecipeList(
      object.keyword,
      object.size,
      object.page,
      ({ data }) => {
        commit('SET_RECIPE_LIST', data)
        console.log(data)
      },
      (error) => {
        console.log(error)
      }
    )
  },
}
