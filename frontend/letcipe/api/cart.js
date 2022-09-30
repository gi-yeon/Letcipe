import { apiInstance } from '.'

const api = apiInstance()

// 장바구니 조회
async function readCart(success, fail) {
  await api.get(`/api/cart`).then(success).catch(fail)
}

// 장바구니에 레시피 등록 -- still ing
// async function createCart(recipeId, success, fail) {
//   await file
//     .post(`/api/cart`, recipeId)
//     .then(success)
//     .catch(fail)
// }

// 장바구니 레시피 삭제
async function deleteCart(recipeId, success, fail) {
  await api.delete(`/api/cart`, recipeId).then(success).catch(fail)
}

// 장바구니 레시피 수량 변경
async function updateCartRecipe(recipeAmount, success, fail) {
  await api
    .patch(`/api/cart-recipe`, JSON.stringify(recipeAmount))
    .then(success)
    .catch(fail)
}

// 장바구니 추가재료 추가
async function createCartIngredient(cartIngredientId, operator, success, fail) {
  await api
    .post(`/api/cart-ingredient`, cartIngredientId, operator)
    .then(success)
    .catch(fail)
}

// 장바구니 추가재료 삭제
async function deleteCartIngredient(cartIngredientId, success, fail) {
  await api
    .delete(`/api/cart-ingredient`, cartIngredientId)
    .then(success)
    .catch(fail)
}

// 장바구니 추가재료 수량 변경
async function patchCartIngredient(recipeAmount, success, fail) {
  await api
    .patch(`/api/cart-ingredient`, JSON.stringify(recipeAmount))
    .then(success)
    .catch(fail)
}

// 장바구니 실행 -- still ing
// async function startCart(calRecipeAmt, success, fail) {
//   await api
//     .patch(`/api/cart-history`, JSON.stringify(calRecipeAmt))
//     .then(success)
//     .catch(fail)
// }

export {
  readCart,
  deleteCart,
  updateCartRecipe,
  createCartIngredient,
  deleteCartIngredient,
  patchCartIngredient,
  // startCart,
  // createCart,
}
 