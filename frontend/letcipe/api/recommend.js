import interceptor from '../config/interceptor'

const api = interceptor;

// 속성값으로 레시피 순위 요청
async function getCartReport(attr, success, fail) {
  await api
    .get(`/api/recipe/recommend?attributes=${attr}&size=5&page=0`)
    .then(success)
    .catch(fail)
}

// 로그인한 정보로 추천 레시피 요청
async function getUserRecommend(success, fail) {
  await api
    .get(`/api/user/recipe/recommend`)
    .then(success)
    .catch(fail)
}

// 북마크 수가 많은 레시피 리스트 5개 불러오기
async function getBestRecipeLists(size,success, fail) {
  await api
    .get(`/api/recipelist/best?size=${size}`)
    .then(success)
    .catch(fail)
}

export {
  getCartReport,
  getUserRecommend,
  getBestRecipeLists
}
