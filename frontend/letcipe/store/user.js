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
} from '@/api/user'

export const state = () => ({
  idcheck: true,
  nickCheck: true,
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
        commit('')
      },
      (error) => {
        console.log(error)
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
