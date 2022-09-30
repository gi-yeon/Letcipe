import api from '../config/interceptor'

// import { apiInstance } from '.'

// const api = apiInstance()

// 장바구니 조회
async function readCart(success, fail) {
  await api.get(`/api/cart`).then(success).catch(fail)
}



// 장바구니에 레시피 등록 -- still ing
async function createCart(addrecipes, success, fail) {
  await api
    .post(`/api/cart`, addrecipes)
    .then(success)
    .catch(fail)
}

// 장바구니 레시피 삭제
async function deleteCart(id, success, fail) {

  await api.delete(`/api/cart`, {data : {recipeId : id}}).then(success).catch(fail)
}

// 장바구니 레시피 수량 변경
async function updateCartRecipe(updateObject, success, fail) {
  console.log(updateObject)
  await api
    .patch(`/api/cart-recipe`,JSON.stringify(updateObject))
    .then(success)
    .catch(fail)
}

// 장바구니 재료 조회
async function getCartIngredient(success, fail) {
  await api
    .get(`/api/cart-ingredient`)
    .then(success)
    .catch(fail)
}

// 장바구니 추가재료 추가
async function createCartIngredient(createObject, success, fail) {
  await api
    .post(`/api/cart-ingredient`, createObject)
    .then(success)
    .catch(fail)
}

// 장바구니 추가재료 삭제
async function deleteCartIngredient(IngreId, success, fail) {
  await api
    .delete(`/api/cart-ingredient`, {data : {ingredientId : IngreId}})
    .then(success)
    .catch(fail)
}

// 장바구니 추가재료 수량 변경
async function patchCartIngredient(updateObject, success, fail) {
  console.log(updateObject)
  await api
    .patch(`/api/cart-ingredient`, updateObject)
    .then(success)
    .catch(fail)
}

// 장바구니 실행 -- still ing
async function startCart( success, fail) {
  await api
    .post(`/api/cart-history`)
    .then(success)
    .catch(fail)
}

export {
  readCart,
  deleteCart,
  updateCartRecipe,
  createCartIngredient,
  deleteCartIngredient,
  patchCartIngredient,
  getCartIngredient,
  createCart,
  startCart,

}
