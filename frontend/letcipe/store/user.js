import {
  login,
  idCheck,
  nicknameCheck,
  readUser,
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
  userId: 0,
  nickname: '',
  profileImage: '',
  gender: '',
  job: '',
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

  SET_USER(state, data) {
    state.userId = data.id
    state.nickname = data.nickname
    state.profileImage = data.profileImage
    state.gender = data.gender
    state.job = data.job
  },
  
  CLEAR_USER(state) {
    state.userId = 0
    state.nickname = ''
    state.profileImage = ''
    state.gender = ''
    state.job = ''
  },
}

export const getters = {}

export const actions = {
  async login({ commit }, user) {
    await login(
      user,
      ({ data }) => {
        this.$cookies.set('access-token', data.accessToken)
        this.$cookies.set('refresh-token', data.refreshToken)
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
  async readUser({ commit }) {
    await readUser(
      ({ data }) => {
        commit('SET_USER', data)
      },
      (error) => {
        console.log(error)
      }
    )
  },
  logout({ commit }) {
    commit('CLEAR_USER')
    this.$cookies.remove('access-token')
    this.$cookies.remove('refresh-token')
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
}
