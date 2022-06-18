import { apiInstance } from "./index.js";

const api = apiInstance();

async function listBoard(limit, success, fail) {
  await api.get(`/notice-api/notice?limit=${limit}`).then(success).catch(fail);
}

async function searchBoard(opt, success, fail) {
  await api
    .get(`/notice-api/notice/search?type=${opt.type}&word=${opt.word}`)
    .then(success)
    .catch(fail);
}

async function createBoard(article, success, fail) {
  await api.post(`/notice-api/notice`, article).then(success).catch(fail);
}

async function modifyBoard(article, success, fail) {
  await api.put(`/notice-api/notice`, article).then(success).catch(fail);
}

async function deleteBoard(articleNo, success, fail) {
  await api.delete(`/notice-api/notice/${articleNo}`).then(success).catch(fail);
}

async function viewBoard(articleNo, success, fail) {
  await api.get(`/notice-api/view/${articleNo}`).then(success).catch(fail);
}

async function createReply(reply, success, fail) {
  await api.post(`/notice-api/reply`, reply).then(success).catch(fail);
}

async function modifyReply(reply, success, fail) {
  await api.put(`/notice-api/reply`, reply).then(success).catch(fail);
}

async function deleteReply(replyNo, success, fail) {
  await api.delete(`/notice-api/reply?no=${replyNo}`).then(success).catch(fail);
}

export {
  listBoard,
  searchBoard,
  createBoard,
  modifyBoard,
  deleteBoard,
  viewBoard,
  createReply,
  modifyReply,
  deleteReply,
};
