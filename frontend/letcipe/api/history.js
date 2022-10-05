import api from '../config/interceptor'
// import { apiInstance } from '.'

// const api = apiInstance()

// 히스토리 목록 조회
async function getHistoryList(success, fail) {
  await api.get(`/api/history`).then(success).catch(fail)
}

// 히스토리 상세 조회 = temporary complete
async function getHistory(historyId, success, fail) {
  await api.get(`/api/history/${historyId}`).then(success).catch(fail)
}

// 히스토리 레시피 삭제
async function deleteHistory(historyId, success, fail) {
  await api.patch(`/api/history/${historyId}`).then(success).catch(fail)
}

// 히스토리 프로세스 변경
async function updateHistory(history, success, fail) {
  await api
    .put(`/api/history`, JSON.stringify(history))
    .then(success)
    .catch(fail)
}

// 히스토리 장보기목록 checked
async function checkHistoryIngredient(historyIngredientId, success, fail) {
  await api
    .patch(`/api/history/ingredient/${historyIngredientId}`)
    .then(success)
    .catch(fail)
}

// 히스토리 리뷰
async function createReview(hitoryReview, success, fail) {
  await api.post(`/api/history/review`, hitoryReview).then(success).catch(fail)
}
async function modifyReview(hitoryReview, success, fail) {
  await api.put(`/api/history/review`, hitoryReview).then(success).catch(fail)
}
async function deleteReview(hitoryId, success, fail) {
  await api.delete(`/api/history/review`, hitoryId).then(success).catch(fail)
}

export {
  getHistoryList,
  getHistory,
  deleteHistory,
  updateHistory,
  checkHistoryIngredient,
  createReview,
  modifyReview,
  deleteReview,
}
