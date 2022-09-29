import { apiInstance } from '.'

const api = apiInstance()

async function getRecipes(object, success, fail) {
    await api
      .get(`/api/recipe` , {params: object}, 
     )
      .then(success)
      .catch(fail)
}  
async function getRecipesIngre(object, success, fail){
    await api
    .get(`/api/recipe`, {params: object})
    .then(success)
    .catch(fail)
}
async function getRecipeList(keyword, size, page, success, fail){
    await api
    .get(`/api/recipelist?keyword=${keyword}&page=${page}&size=${size}`,)
    .then(success)
    .catch(fail)
}
export {
    getRecipes,
    getRecipesIngre,
    getRecipeList
}
  