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
  createCode,
  modifyPassword
} from '@/api/user'

export const state = () => ({
  idcheck: true,
  nickCheck: true,
  codeCheck: false,
  code: '',
  email: '',
  userJoinCheck: false,
  userid:'',
  birth:'',
  family:'',
  name:'',
  phone:'',
  userId: 0,
  nickname: '',
  profileImage: '',
  userGender: '',
  userJob: '',
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
    state.userid = data.userId
    state.birth = data.birth
    state.family = data.family
    state.name = data.name
    state.phone = data.phone
    state.email = data.email
    state.userId = data.id
    state.nickname = data.nickname
    state.profileImage = data.profileImage
    state.userGender = data.gender
    state.userJob = data.job
  },
  
  CLEAR_USER(state) {
    state.userid = ''
    state.birth = ''
    state.family = ''
    state.name = ''
    state.phone = ''
    state.email = ''
    state.userId = 0
    state.nickname = ''
    state.profileImage = ''
    state.userGender = ''
    state.userJob = ''
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
        console.log(data)
        commit('SET_USER', data)
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async modifyPassword({commit}, passwords) {
    await modifyPassword(
      passwords,
      (res) => {
        console.log(res)
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
  async modifyMember({ commit }, userObject) {
    await modifyMember(
      userObject,
      (res) => {
        console.log(res)
        this.readUser()
      },
      (error) => {
        console.log(error)
      }
    )
  },
  changeNickCheck({commit}){
    commit('SET_NICKCHECK_TRUE')
  }
}
