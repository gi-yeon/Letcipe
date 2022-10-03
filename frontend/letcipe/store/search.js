
import{
  getRecipes,
  getRecipesIngre,
  getRecipeList,
  getTotalNumRecipe,
  getTotalNumRecipeList
} from '@/api/search'
export const state = () => ({
    recipes : [],
    recipesIngre : [],
    recipeLists : [],
    totalPage : 1,
})

export const mutations = {
    SET_RECIPE(state, recipes){
        state.recipes = recipes
    },
    CLEAR_RECIPE(state){
        state.recipes = []
    },
    SET_RECIPE_INGRE(state, recipesIngre){
      state.recipesIngre = recipesIngre
    },
    CLEAR_RECIPE_INGRE(state){
      state.recipesIngre = []
    },
    SET_RECIPE_LIST(state, recipeLists){
      state.recipeLists = recipeLists
    },
    CLEAR_RECIPE_LIST(state){
      state.recipeLists = ""
    },
    SET_TOTAL_PAGE(state, page){
      state.totalPage = page
    }
    
}

export const getters = {}

export const actions = {
  async getRecipes({ commit }, object) {
    commit('CLEAR_RECIPE');
    commit('CLEAR_RECIPE_LIST');
    await getRecipes(
      {
        keyword: object.keyword,
        size: object.size,
        page : object.page
      },
      ({data}) => {

        commit('SET_RECIPE', data)
      },
      (error) => {
        console.log(error)
      }
    )
    await getTotalNumRecipe(
      {
        keyword: object.keyword,

      },
      ({data}) => {
        console.log(data)
        commit('SET_TOTAL_PAGE', data)
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async getRecipesIngre({ commit }, object) {
    commit('CLEAR_RECIPE_INGRE');
    await getRecipesIngre(
      { 
        ingredients : object.ingredients,
        size: object.size,
        page : object.page
       },
      ({ data }) => {
        commit('SET_RECIPE_INGRE', data)
        console.log(data)
      },
      (error) => {
        console.log(error)
      }
    )
    await getTotalNumRecipe(
      {
        ingredients : object.ingredients,
      },
      ({data}) => {
        console.log(data)
        commit('SET_TOTAL_PAGE', data)
      },
      (error) => {
        console.log(error)
      }
    )
  }, 
  async getRecipeList({ commit }, object) {
    commit('CLEAR_RECIPE_LIST');
    commit('CLEAR_RECIPE');
    console.log(object)
    await getRecipeList(
         object.keyword,
        object.size,
        object.page
       ,
      ({ data }) => {
        commit('SET_RECIPE_LIST', data)
        console.log(data)
      },
      (error) => {
        console.log(error)
      }
    )
    await getTotalNumRecipeList(
      object.keyword,
  
      ({data}) => {
    console.log(data)
        commit('SET_TOTAL_PAGE', data)
      },
      (error) => {
        console.log(error)
      }
    )
  },
 
}