import { apiInstance, fileInstance } from '.'
// import { fileInstance } from './multipart'

const api = apiInstance()
const file = fileInstance()

// 레시피리스트 목록 검색
async function searchRecipeList(query, page, size, success, fail) {
  await api
    .get(`/api/recipelist?query=${query}&page=${page}&size=${size}`)
    .then(success)
    .catch(fail)
}

// 레시피리스트 등록
async function createRecipeList(recipelist, success, fail) {
  await file
    .post(`/api/recipelist`, JSON.stringify(recipelist))
    .then(success)
    .catch(fail)
}

// 레시피리스트 검색
async function getRecipeList(recipeListId, updateRL, success, fail) {
  await api
    .get(`/api/recipelist/${recipeListId}`, JSON.stringify(updateRL))
    .then(success)
    .catch(fail)
}

// 레시피리스트 수정
async function updateRecipeList(recipeListId, success, fail) {
  await api.put(`/api/recipe/${recipeListId}`).then(success).catch(fail)
}

// 레시피리스트 삭제
async function deleteRecipeList(recipeListId, success, fail) {
  await api.patch(`/api/recipe/${recipeListId}`).then(success).catch(fail)
}

// 레시피리스트에 레시피 추가
async function addRecipeListItem(RLItem, success, fail) {
  await api
    .post(`/api/recipelist/recipe`, JSON.stringify(RLItem))
    .then(success)
    .catch(fail)
}

// 레시피리스트에 레시피 삭제
async function deleteRecipeListItem(RLItem, success, fail) {
  await api
    .delete(`/api/recipelist/recipe`, JSON.stringify(RLItem))
    .then(success)
    .catch(fail)
}

// 레시피리스트  북마크 설정
async function createRecipeListBookmark(recipeListId, success, fail) {
  await api
    .post(`/api/recipelist/bookmark/${recipeListId}`)
    .then(success)
    .catch(fail)
}

// 레시피 북마크 해제
async function deleteRecipeListBookmark(recipeListId, success, fail) {
  await api
    .delete(`/api/recipelist/bookmark/${recipeListId}`)
    .then(success)
    .catch(fail)
}
export {
  searchRecipeList,
  createRecipeList,
  getRecipeList,
  updateRecipeList,
  deleteRecipeList,
  addRecipeListItem,
  deleteRecipeListItem,
  createRecipeListBookmark,
  deleteRecipeListBookmark,
}
