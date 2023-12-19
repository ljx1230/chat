import { createStore } from 'vuex';


export default createStore({
  state: {
    login: localStorage.getItem("isLogin"),
    userId: localStorage.getItem("userId"),
    user: {
      username: "",
      nickname: ""
    }
  },
  getters: {
  },
  mutations: {
    updateLogin(state, login) {
      state.login = login;
    },
    updateUser(state, user) {
      state.user.username = user.username;
      state.user.nickname = user.nickname;
    }
  },
  actions: {
  },
  modules: {
  }
})
