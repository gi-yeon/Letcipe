import {
  readCart,
  deleteCart,
  updateCartRecipe,
  createCartIngredient,
  deleteCartIngredient,
  patchCartIngredient,
  // startCart,
  // createCart,
} from '@/api/cart'

export const state = () => ({
  cart: [],
})
 
export const mutations = {
  SET_CART(state, cart) {
    state.cart = cart
  },
  CLEART_CART(state) {
    state.cart = []
  },
}

export const getters = {}

export const actions = {
  async readCart({ commit }) {
    await readCart(
      ({ data }) => {
        commit('SET_CART', data.cart)
        // console.log(data)
        // console.log('장바구니 조회 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  //   async createCart({ commit }, recipeId) {
  //     await createCart(
  //         recipeId,
  //       ({ data }) => {
  //         // console.log(data)
  //         // console.log('장바구니에 레시피 등록 성공!')
  //       },
  //       (error) => {
  //         console.log(error)
  //       }
  //     )
  //   },
  async deleteCart({ commit }, recipeId) {
    await deleteCart(
      recipeId,
      ({ data }) => {
        // console.log(data)
        // console.log('장바구니 레시피 삭제 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async updateCartRecipe({ commit }, recipeAmount) {
    await updateCartRecipe(
      recipeAmount,
      ({ data }) => {
        // console.log(data)
        // console.log('장바구니 레시피 수량 변경 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async createCartIngredient({ commit }, cartIngredientId, operator) {
    await createCartIngredient(
      cartIngredientId,
      operator,
      ({ data }) => {
        // console.log(data)
        // console.log('장바구니 추가재료 추가 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async deleteCartIngredient({ commit }, cartIngredientId, operator) {
    await deleteCartIngredient(
      cartIngredientId,
      operator,
      ({ data }) => {
        // console.log(data)
        // console.log('장바구니 추가재료 삭제 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async patchCartIngredient({ commit }, recipeAmount) {
    await patchCartIngredient(
      recipeAmount,

      ({ data }) => {
        // console.log(data)
        // console.log('장바구니 추가재료 수량 변경 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  //   async startCart({ commit }, calRecipeAmt) {
  //     await startCart(
  //         calRecipeAmt,

  //       ({ data }) => {
  //         // console.log(data)
  //         // console.log('장바구니 실행 성공!')
  //       },
  //       (error) => {
  //         console.log(error)
  //       }
  //     )
  //   },
}
