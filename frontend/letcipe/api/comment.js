import interceptor from '../config/interceptor'

const api = interceptor

async function getComment(recipeObject, success, fail) {
  await api
    .get(
      `/api/comment/${recipeObject.boardType}/${recipeObject.boardId}?size=${recipeObject.size}&page=${recipeObject.page}`
    )
    .then(success)
    .catch(fail)
}
async function postComment(commentObject, success, fail) {
  console.log(commentObject)
  await api
    .post(`/api/comment`, JSON.stringify(commentObject))
    .then(success)
    .catch(fail)
}

async function putComment(commentObject, success, fail) {
  console.log(commentObject)
  await api
    .put(`/api/comment`, JSON.stringify(commentObject))
    .then(success)
    .catch(fail)
}

async function patchComment(commentObject, success, fail) {
  console.log(commentObject)
  await api
    .patch(`/api/comment`, JSON.stringify(commentObject))
    .then(success)
    .catch(fail)
}

async function getCommentNum(recipeObject, success, fail) {
  await api
    .get(
      `/api/comment/${recipeObject.boardType}/${recipeObject.boardId}/commentNum`
    )
    .then(success)
    .catch(fail)
}
export { getComment, getCommentNum, postComment, putComment, patchComment }
