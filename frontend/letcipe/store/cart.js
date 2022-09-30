import {
  readCart,
  deleteCart,
  updateCartRecipe,
  createCartIngredient,
  deleteCartIngredient,
  patchCartIngredient,
  getCartIngredient,
  createCart,
  startCart
} from '@/api/cart'

export const state = () => ({
  cart: [],     // 레시피 정보들
  ingreList : [],       // 전부 계산된 재료들
  // ingreAmountByRecipe : new Map(),
  amountByRecipe : {}
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
  SET_INGRELIST_AMOUNT(state,updateAmountObject){
    console.log("ingre" + updateAmountObject.index + "====" + updateAmountObject.updateAmount) 
    state.ingreList[updateAmountObject.index].amount = updateAmountObject.updateAmount
  } ,
  SET_BYRECIPE_AMOUNT(state,updateAmountObject){
    console.log("recipe" + updateAmountObject.ingreId + "====" + updateAmountObject.updateAmount)
    state.amountByRecipe[updateAmountObject.ingreId] = updateAmountObject.updateAmount
    
  },
  // CLEAR_INGRE_AMOUNT(state){
  //   state.ingreAmountByRecipe = new Map()
  // },
  SET_RECIPE_INGRE(state, amountByRecipe){
    state.amountByRecipe = amountByRecipe
  },
  // CALC_INGRE_AMOUNT(state){
  //     for (let i = 0; i < state.cart.length; i++) {
  //       const ingreList = state.cart[i].recipe.ingredients
  //       for (let j = 0; j < ingreList.length; j++) {
  //         if (state.ingreAmountByRecipe.has(ingreList[j].ingredient.name)) {
  //           state.ingreAmountByRecipe.get(ingreList[j].ingredient.name).amount +=
  //             ingreList[j].amount * state.cart[i].amount
  //         } else {
  //           state.ingreAmountByRecipe.set(ingreList[j].ingredient.name, {
  //             name: ingreList[j].ingredient.name,
  //             amount: ingreList[j].amount * state.cart[i].amount,
  //           })
  //         }
  //       }
  //     }
  //     console.log(state.ingreAmountByRecipe.get('달걀').amount + '---------------------------------')
  // }
  
  
}

export const getters = {}

export const actions = {

  async readCart({ commit }) {
    commit('CLEAR_CART')
    // commit('CLEAR_INGRE_AMOUNT')
    await readCart(
      ({ data }) => {
        commit('SET_CART', data.cartItems)
        // commit('CALC_INGRE_AMOUNT')
        console.log(data)
      
      },
      (error) => {
        console.log(error)
      }
    )
  },
  
    async createCart({ commit }, addrecipes) {
      await createCart(
        addrecipes,
        ({ data }) => {
          console.log(data)
           console.log('장바구니에 레시피 등록 성공!')
        },
        (error) => {
          console.log(error)
        }
      )
    },
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
        commit('SET_RECIPE_INGRE', data.amountByRecipe)
         console.log('장바구니 재료 읽어오기 성공!'  + data.amountByRecipe["80"])
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
  async deleteCartIngredient({ commit }, IngreId) {
    await deleteCartIngredient(
      IngreId,
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
        console.log(data)
        // console.log('장바구니 추가재료 수량 변경 성공!')
      },
      (error) => {
        console.log(error)
      }
    )
  },
    async startCart({ commit }) {
      await startCart(
          
        ({ data }) => {
          // console.log(data)
          // console.log('장바구니 실행 성공!')
        },
        (error) => {
          console.log(error)
        }
      )
    },
}
