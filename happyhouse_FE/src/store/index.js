import Vue from "vue";
import Vuex from "vuex";
import http from "@/api/http";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

import userStore from "@/store/modules/userStore.js";

export default new Vuex.Store({
  state: {
    sidos: [{ value: null, text: "선택하세요" }],
    guguns: [{ value: null, text: "선택하세요" }],
    dongs: [{ value: null, text: "선택하세요" }],
    houses: [],
    houses_distinct: [],
    house: null,
    selected_houses: [],
    checkedCategorys: [],
    houses_count: {},
    houses_like: [],
    houses_like_distinct: [],
    todos: [
      // { title: '할 일1', completed: false },
      // { title: '할 일2', completed: false },
    ],
  },
  getters: {
    allTodosCount(state) {
      return state.todos.length;
    },
    completedTodosCount(state) {
      return state.todos.filter((todo) => {
        return todo.completed === true;
      }).length;
    },
    unCompletedTodosCount(state) {
      return state.todos.filter((todo) => {
        return todo.completed === false;
      }).length;
    },
  },
  mutations: {
    /////////////////////////////// House start /////////////////////////////////////
    SET_SIDO_LIST(state, sidos) {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST(state, guguns) {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_DONG_LIST(state, dongs) {
      dongs.forEach((dong) => {
        state.dongs.push({ value: dong.dongCode, text: dong.dongName });
      });
    },
    CLEAR_SIDO_LIST(state) {
      state.sidos = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_GUGUN_LIST(state) {
      state.guguns = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_DONG_LIST(state) {
      state.dongs = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_HOUSES_COUNT(state) {
      state.houses_count = {};
    },
    CLEAR_CHECKED_CATEGORYS(state) {
      state.checkedCategorys = [];
    },

    SET_HOUSE_LIST(state, houses) {
      state.houses = houses;
    },
    SET_HOUSE_LIST_DISTINCT(state, houses_distinct) {
      state.houses_distinct = houses_distinct;
    },
    SET_SELECTED_HOUSE_LIST(state, selected_houses) {
      state.selected_houses = selected_houses;
    },
    SET_DETAIL_HOUSE(state, house) {
      //alert("set" + house.aptname);
      console.log("Mutations", house);
      state.house = house;
    },
    SET_CHECKED_CATEGORYS(state, checkedCategorys) {
      state.checkedCategorys = checkedCategorys;
    },
    SET_LIKE_LIST(state, houses) {
      state.houses_like = houses;
    },
    SET_LIKE_DISTINCT(state, houses_like_distinct) {
      state.houses_like_distinct = houses_like_distinct;
    },
    ADD_HOUSES_COUNT(state, aptname) {
      if (state.houses_count[aptname]) state.houses_count[aptname] += 1;
      else state.houses_count[aptname] = 1;
    },
    /////////////////////////////// House end /////////////////////////////////////

    //////////////////////////// Todo List start //////////////////////////////////
    CREATE_TODO(state, todoItem) {
      state.todos.push(todoItem);
    },
    DELETE_TODO(state, todoItem) {
      const index = state.todos.indexOf(todoItem);
      state.todos.splice(index, 1);
    },
    UPDATE_TODO_STATUS(state, todoItem) {
      state.todos = state.todos.map((todo) => {
        if (todo === todoItem) {
          return {
            ...todo,
            completed: !todoItem.completed,
          };
        }
        return todo;
      });
    },
    //////////////////////////// Todo List end //////////////////////////////////
  },
  actions: {
    /////////////////////////////// House start /////////////////////////////////////
    getSido({ commit }) {
      http
        .get(`/apt-api/sido`)
        .then(({ data }) => {
          // console.log(data);
          commit("SET_SIDO_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getGugun({ commit }, sidoCode) {
      const params = { sido: sidoCode };
      http
        .get(`/apt-api/gugun`, { params })
        .then(({ data }) => {
          // console.log(commit, response);
          commit("SET_GUGUN_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getDong({ commit }, gugunCode) {
      const params = { gugun: gugunCode };
      http
        .get(`/apt-api/dong`, { params })
        .then(({ data }) => {
          //alert(JSON.stringify(data));
          // console.log(commit, response);
          commit("SET_DONG_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getHouseList({ commit }, { dongCode, aptName }) {
      var dong = dongCode == null ? "dong=" : `dong=${dongCode}`;
      var apt = aptName == null ? "aptname=" : `aptname=${aptName}`;
      //alert(`/apt-api/search?` + apt + "&" + dong);
      http
        .post(`/apt-api/search?` + apt + "&" + dong)
        .then(({ data }) => {
          // console.log(commit, response);
          //alert(JSON.stringify(data));
          var housesnames = [];
          var aptnames = [];
          this.state.houses_count = {};

          for (var i in data) {
            //commit("ADD_HOUSES_COUNT", data[i].aptname);
            if (this.state.houses_count[data[i].aptname])
              this.state.houses_count[data[i].aptname] += 1;
            else this.state.houses_count[data[i].aptname] = 1;
            if (!aptnames.includes(data[i].aptname)) {
              aptnames.push(data[i].aptname);
              housesnames.push(data[i]);
            }
          }

          commit("SET_HOUSE_LIST_DISTINCT", housesnames);
          commit("SET_HOUSE_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getLikeHouseList({ commit }, userid) {
      const params = { userid: userid };
      http
        .get(`/like-api/like`, { params })
        .then(({ data }) => {
          var aptnames = [];
          var likehousedistinct = [];
          for (var i in data) {
            if (!aptnames.includes(data[i].aptname)) {
              aptnames.push(data[i].aptname);
              likehousedistinct.push(data[i]);
            }
          }
          commit("SET_LIKE_DISTINCT", likehousedistinct);
          commit("SET_LIKE_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    setLikeHouseList({ commit }, { userid, aptcode }) {
      const params = { userid: userid, aptcode: aptcode };
      //alert(JSON.stringify(params));
      http
        .post(`/like-api/like`, JSON.stringify(params))
        .then(({ data }) => {
          data;
          // console.log(commit, response);
          //alert(JSON.stringify(data));
          commit;
          http
            .get(`/like-api/like`, { params })
            .then(({ data }) => {
              var aptnames = [];
              var likehousedistinct = [];
              for (var i in data) {
                if (!aptnames.includes(data[i].aptname)) {
                  aptnames.push(data[i].aptname);
                  likehousedistinct.push(data[i]);
                }
              }
              commit("SET_LIKE_DISTINCT", likehousedistinct);
              commit("SET_LIKE_LIST", data);
            })
            .catch((error) => {
              console.log(error);
            });
        })
        .catch((error) => {
          console.log(error);
        });
    },
    deleteLikeHouseList({ commit }, { userid, likeno }) {
      const params = { userid: userid };
      http
        .delete(`/like-api/like?no=` + likeno)
        .then(({ data }) => {
          data;
          console.log(commit);
          //alert(JSON.stringify(data));
          http
            .get(`/like-api/like`, { params })
            .then(({ data }) => {
              var aptnames = [];
              var likehousedistinct = [];
              for (var i in data) {
                if (!aptnames.includes(data[i].aptname)) {
                  aptnames.push(data[i].aptname);
                  likehousedistinct.push(data[i]);
                }
              }
              commit("SET_LIKE_DISTINCT", likehousedistinct);
              commit("SET_LIKE_LIST", data);
            })
            .catch((error) => {
              console.log(error);
            });
        })
        .catch((error) => {
          console.log(error);
        });
    },
    detailHouse({ commit }, house) {
      // 나중에 house.일련번호를 이용하여 API 호출
      // console.log(commit, house);
      var selected_houses = [];

      for (var i in this.state.houses) {
        if (house.aptname == this.state.houses[i].aptname) {
          selected_houses.push(this.state.houses[i]);
        }
      }
      for (var j in this.state.houses_like) {
        if (house.aptname == this.state.houses_like[j].aptname) {
          house = this.state.houses_like[j];
          break;
        }
      }
      commit("SET_SELECTED_HOUSE_LIST", selected_houses);
      commit("SET_DETAIL_HOUSE", house);
    },
    detailHouseSet({ commit }, house) {
      commit("SET_DETAIL_HOUSE", house);
    },
    detailHouse_like({ commit }, house) {
      // 나중에 house.일련번호를 이용하여 API 호출
      // console.log(commit, house);
      var selected_houses = [];
      for (var i in this.state.houses_like) {
        if (house.aptname == this.state.houses_like[i].aptname) {
          selected_houses.push(this.state.houses_like[i]);
        }
      }
      for (var j in this.state.houses_like) {
        if (house.aptname == this.state.houses_like[j].aptname) {
          house = this.state.houses_like[j];
          break;
        }
      }
      commit("SET_SELECTED_HOUSE_LIST", selected_houses);
      commit("SET_DETAIL_HOUSE", house);
    },
    categoryCheck({ commit }, checkedCategorys) {
      commit("SET_CHECKED_CATEGORYS", checkedCategorys);
    },
    /////////////////////////////// House end /////////////////////////////////////

    //////////////////////////// Todo List start //////////////////////////////////

    // destructuring 활용
    createTodo({ commit }, todoItem) {
      commit("CREATE_TODO", todoItem);
    },
    deleteTodo({ commit }, todoItem) {
      commit("DELETE_TODO", todoItem);
    },
    updateTodoStatus({ commit }, todoItem) {
      commit("UPDATE_TODO_STATUS", todoItem);
    },
    //////////////////////////// Todo List end //////////////////////////////////
  },
  modules: { userStore },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});
