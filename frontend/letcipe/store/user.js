import {
  login,
  idCheck,
  nicknameCheck,
  signup,
  mypage,
  modifyMember,
  deleteMember,
  myrecipe,
  myrecipeList,
  createCode
} from '@/api/user'

export const state = () => ({
  idcheck: true,
  nickCheck: true,
  codeCheck: false,
  code: '',
  userJoinCheck: false,
})

export const mutations = {
  SET_IDCHECK_TRUE(state) {
    state.idcheck = true
  },

  SET_IDCHECK_FALSE(state) {
    state.idcheck = false
  },

  SET_NICKCHECK_TRUE(state) {
    state.nickCheck = true
  },

  SET_NICKCHECK_FALSE(state) {
    state.nickCheck = false
  },

  SET_CODECHECK_TRUE(state) {
    state.codeCheck = true
  },

  SET_CODECHECK_FALSE(state) {
    state.codeCheck = false
  },

  SET_USERJOINCHECK_FALSE(state) {
    state.userJoinCheck = false
  },

  SET_USERJOINCHECK_TRUE(state) {
    state.userJoinCheck = true
  },

  SET_CODE(state, code) {
    state.code = code
  },
}

export const getters = {}

export const actions = {
  async login({ commit }, object) {
    await login(
      {
        username: object.id,
        password: object.password,
      },
      ({ data }) => {
        commit('')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async idCheck({ commit }, userid) {
    await idCheck(userid, 
      (res) => {
        console.log(res.status)
        commit('SET_IDCHECK_FALSE')
      },
      (error) => {
        console.log(error.status)
        commit('SET_IDCHECK_TRUE')
      })
  },
  idCheckReset({ commit }) {
    commit('SET_IDCHECK_TRUE')
  },
  async nicknameCheck({ commit }, nickname) {
    await nicknameCheck(
      nickname,
      (res) => {
        console.log(res.status)
        commit('SET_NICKCHECK_FALSE')
      },
      (error) => {
        console.log(error)
        commit('SET_NICKCHECK_TRUE')
      }
    )
  },
  async signup({ commit }, user) {
    await signup(
      user,
      ({ data }) => {
        console.log(data)
        commit('SET_USERJOINCHECK_TRUE')
      },
      (error) => {
        console.log(error)
        commit('SET_USERJOINCHECK_FALSE')
      }
    )
  },
  async mypage({ commit }, userid) {
    await mypage(
      userid,
      ({ data }) => {
        commit('')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async deleteMember({ commit }, userid) {
    await deleteMember(
      userid,
      ({ data }) => {
        commit('')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async myrecipe({ commit }, userid) {
    await myrecipe(
      userid,
      ({ data }) => {
        commit('')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async myrecipeList({ commit }, userid) {
    await myrecipeList(
      userid,
      ({ data }) => {
        commit('')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async createCode({ commit }, phone) {
    await createCode(
      phone,
      (res) => {
        console.log(res.data.code)
        commit('SET_CODE', res.data.code)
      },
      (error) => {
        console.log(error)
      }
    )
  },
  checkCodeEq({ commit, state }, code) {
    if(state.code === code) {
      commit('SET_CODECHECK_TRUE')
    } else {
      commit('SET_CODECHECK_FALSE')
    }
  },
  modifyMember({ commit }, userObject) {
    modifyMember(
      userObject,
      (response) => {
        commit('')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async resetSatus({ commit }) {
    await commit('SET_IDCHECK_TRUE')
    await commit('SET_NICKCHECK_TRUE')
    await commit('SET_CODECHECK_FALSE')
    await commit('SET_USERJOINCHECK_FALSE')
    await commit('SET_CODE', '')
  }
}
