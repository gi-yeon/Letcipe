import { apiInstance } from '.'

const api = apiInstance()

// 게시글 목록 조회
async function getBoardList(page, size, success, fail) {
  await api.get(`/board`, page, size).then(success).catch(fail)
}

// 게시글 상세 조회
// async function getBoard(boardId, success, fail) {
//     await api.get(`/boardId`).then(success).catch(fail)
//   }

// 게시글 등록
async function postBoard(board, success, fail) {
  await api.post(`/board`, JSON.stringify(board)).then(success).catch(fail)
}

// 게시글 수정
async function updateBoard(boardId, board, success, fail) {
  await api
    .put(`/board/${boardId}`, JSON.stringify(board))
    .then(success)
    .catch(fail)
}

// 게시글 삭제
async function deleteBoard(boardId, success, fail) {
  await api.patch(`/board/${boardId}`).then(success).catch(fail)
}

export {
  getBoardList,
  // getBoard,
  postBoard,
  updateBoard,
  deleteBoard,
}
