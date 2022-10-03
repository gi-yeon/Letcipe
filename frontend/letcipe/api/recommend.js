import interceptor from '../config/interceptor'

const api = interceptor;

// 로그인한 정보로 추천 레시피 요청
async function getUserRecommend(success, fail) {
  await api
    .get(`/api/user/recipe/recommend`)
    .then(success)
    .catch(fail)
}



export {
   getUserRecommend
}
