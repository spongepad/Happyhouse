/* eslint-disable */
import jwt_decode from "jwt-decode";
import {
  login,
  findById,
  registUser,
  updateUser,
  deleteUser,
  idCheck,
  findPwd,
} from "@/api/user.js";

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    isAvailableId: false,
    userInfo: null,
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_LOGOUT: (state) => {
      state.isLogin = false;
      state.userInfo = null;
      sessionStorage.removeItem("access-token");
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_IS_AVAILABLE_ID: (state, isAvailableId) => {
      state.isAvailableId = isAvailableId;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        (response) => {
          if (response.data.message === "success") {
            let token = response.data["access-token"];
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            sessionStorage.setItem("access-token", token);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
          }
        },
        () => {}
      );
    },
    async getUserInfo({ commit }, token) {
      let decode_token = jwt_decode(token);
      await findById(
        decode_token.userid,
        (response) => {
          if (response.data.message === "success") {
            commit("SET_USER_INFO", response.data.userInfo);
          } else {
            console.log("해당하는 유저가 없음.");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async userRegist({ commit }, user) {
      await registUser(
        user,
        (response) => {
          if (response.data > 0) {
            commit("SET_IS_LOGIN", false);
            alert("회원 가입이 완료 되었습니다.");
          } else {
            alert("회원 가입중 문제가 발생하였습니다 다시 시도해주세요.");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async userUpdate({ commit }, user) {
      await updateUser(
        user,
        (response) => {
          if (response.data > 0) {
            commit("SET_USER_INFO", user);
            alert("회원 정보 수정이 완료 되었습니다.");
          } else {
            alert("회원 정보 수정중 문제가 발생하였습니다 다시 시도해주세요.");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async userDelete({ commit }, user) {
      await deleteUser(
        user.userid,
        (response) => {
          if (response.data > 0) {
            commit("SET_LOGOUT");
            alert("회원 탈퇴가 완료 되었습니다.");
          } else {
            alert("회원 탈퇴중 문제가 발생하였습니다 다시 시도해주세요.");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    checkid({ commit }, userid) {
      idCheck(
        userid,
        (response) => {
          console.log(response.data);
          if (response.data == 0) {
            commit("SET_IS_AVAILABLE_ID", true);
          } else {
            commit("SET_IS_AVAILABLE_ID", false);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async findPassword({ commit }, user) {
      await findPwd(
        user,
        ({ data }) => {
          if (data) {
            alert("비밀번호: " + data);
          } else {
            alert("해당하는 사용자가 없습니다.");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default userStore;
