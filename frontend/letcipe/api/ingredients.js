import { apiInstance } from '.'

const api = apiInstance()

// 재료 조회
async function searchIngredient(keyword, success, fail) {
  await api.get(`/api/ingredient?keyword=${keyword}`).then(success).catch(fail)
}

// async function createIngredient(params, success, fail) {
//     await api.get(`/api/ingredient`, params).then(success).catch(fail)
//   }

export { searchIngredient }
 