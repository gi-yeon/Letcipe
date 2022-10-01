import interceptor from '../config/interceptor'
import fileInterceptor from '../config/fileInterceptor'
import { apiInstance, fileInstance } from '.'

const api = apiInstance()
const fileApi = fileInstance()

// 사용자 로그인
async function login(user, success, fail) {
  await api
    .post(`/api/user/login`, JSON.stringify(user))
    .then(success)
    .catch(fail)
}

// 사용자 id, nickname 얻어오기
async function readUser(success, fail) {
  await interceptor.get(`/api/user`).then(success).catch(fail)
}

// 사용자 등록
async function signup(user, success, fail) {
  await fileApi.post(`/api/user`, user).then(success).catch(fail)
}

// 사용자 정보 조회
async function idCheck(userid, success, fail) {
  await api.get(`/api/user/id/${userid}/exists`).then(success).catch(fail)
}

async function nicknameCheck(nickname, success, fail) {
  await api
    .get(`/api/user/nickname/${nickname}/exists`)
    .then(success)
    .catch(fail)
}

// 사용자 정보 조회??
async function mypage(userid, success, fail) {
  // eslint-disable-next-line dot-notation
  api.defaults.headers['Authorization'] =
    sessionStorage.getItem('Authorization')
  await api.get(`/api/user/${userid}`).then(success).catch(fail)
}

// 사용자 수정
async function modifyMember(userObject, success, fail) {
  // eslint-disable-next-line dot-notation
  await fileInterceptor.put('/api/user', userObject).then(success).catch(fail)
}

async function modifyPassword(passwordObject, success, fail) {
  await interceptor
    .patch('/api/user/password', passwordObject)
    .then(success)
    .catch(fail)
}

// 사용자 삭제
async function deleteMember(userid, success, fail) {
  await api.patch(`/api/user/${userid}`).then(success).catch(fail)
}

// 내 레시피 조회
async function myrecipe(pageable, success, fail) {
  await interceptor.get(`/api/user/recipe`, pageable).then(success).catch(fail)
}

// 내 레시피리스트 조회
async function myrecipeList(pageable, success, fail) {
  await interceptor
    .get(`/api/user/recipelist`, pageable)
    .then(success)
    .catch(fail)
}

// 내 레시피북마크 목록 조회
async function myBookmarkRecipe(pageable, success, fail) {
  await interceptor
    .get(`/api/user/mark/recipe`, pageable)
    .then(success)
    .catch(fail)
}

// 내 레시피북마크 목록 조회
async function myBookmarkRecipeList(pageable, success, fail) {
  await interceptor
    .get(`/api/user/mark/recipelist`, pageable)
    .then(success)
    .catch(fail)
}

// 핸드폰 인증
async function createCode(phoneNo, success, fail) {
  await api.post(`/api/sms`, phoneNo).then(success).catch(fail)
}

export {
  login,
  idCheck,
  nicknameCheck,
  signup,
  readUser,
  mypage,
  modifyMember,
  deleteMember,
  myrecipe,
  myrecipeList,
  myBookmarkRecipe,
  myBookmarkRecipeList,
  createCode,
  modifyPassword,
}
