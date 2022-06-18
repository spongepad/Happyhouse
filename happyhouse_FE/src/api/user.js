import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  await api
    .post(`/user-api/login`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user-api/user?userid=${userid}`).then(success).catch(fail);
}

async function registUser(user, success, fail) {
  await api.post(`user-api/user`, user).then(success).catch(fail);
}

async function updateUser(user, success, fail) {
  await api.put(`user-api/user`, user).then(success).catch(fail);
}

async function deleteUser(userid, success, fail) {
  await api.delete(`user-api/user/${userid}`).then(success).catch(fail);
}

async function idCheck(ckid, success, fail) {
  await api.get(`user-api/idcheck?ckid=${ckid}`).then(success).catch(fail);
}

async function findPwd(user, success, fail) {
  await api
    .get(
      // eslint-disable-next-line
      `user-api/findpwd?userid=${user.userid}&username=${user.username}&tel=${user.tel}`
    )
    .then(success)
    .catch(fail);
}

export {
  login,
  findById,
  registUser,
  updateUser,
  deleteUser,
  idCheck,
  findPwd,
};
