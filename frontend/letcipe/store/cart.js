import {
  readCart,
  deleteCart,
  updateCartRecipe,
  createCartIngredient,
  deleteCartIngredient,
  patchCartIngredient,
  getCartIngredient
  // startCart,
  // createCart,
} from '@/api/cart'

export const state = () => ({
  cart: [],
  ingreList : []

})
 
export const mutations = {
  SET_CART(state, cart) {
    state.cart = cart
  },
  CLEAR_CART(state) {
    state.cart = []
  },
  SET_INGRE(state, ingreList){
    state.ingreList = ingreList
  },
  CLEAR_INGRE(state){
    state.ingreList = []
  },
  CALC_PLUS_INGRE(state, index){
    state.ingreList[index].amount += 1
  },
  CALC_SUB_INGRE(state, index){
    state.ingreList[index].amount -= 1
  },
  
  
}

export const getters = {}

export const actions = {
  async readCart({ commit }) {
    await readCart(
      ({ data }) => {
        commit('SET_CART', data.cartItems)
        console.log(data)
      
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
  async updateCartRecipe({ commit }, updateObject) {
    await updateCartRecipe(
      updateObject,
      ({ data }) => {
        // console.log(data)
        // console.log('장바구니 레시피 수량 변경 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },

  async getCartIngredient({ commit }) {
    commit('CLEAR_INGRE')
    await getCartIngredient(
      
      ({ data }) => {
        console.log(data)
        commit('SET_INGRE',data.list)
         console.log('장바구니 재료 읽어오기 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },

  async createCartIngredient({ commit }, IngreId) {
    await createCartIngredient(
      IngreId,
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
  async patchCartIngredient({ commit }, updateObject) {
    await patchCartIngredient(
      updateObject,
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
