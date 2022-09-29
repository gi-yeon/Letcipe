import {
  login,
  readUser,
  signup,
  //   check,
  mypage,
  modifyMember,
  deleteMember,
  myrecipe,
  myrecipeList,
} from '@/api/user'

export const state = () => ({
  userId: 0,
  nickname: '',
})

export const mutations = {
  SET_USER(state, data) {
    state.userId = data.id
    state.nickname = data.nickname
  },
  CLEAR_USER(state) {
    state.userId = 0
    state.nickname = ''
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
  logout({ commit }) {
    commit('CLEAR_USER')
    this.$cookies.remove('access-token')
    this.$cookies.remove('refresh-token')
  },
  async signup({ commit }, user) {
    await signup(
      user,
      ({ data }) => {
        commit('')
      },
      (error) => {
        console.log(error)
      }
    )
  },
  //   async check({ commit }, userid) {
  //     await check(
  //       userid,
  //       ({ data }) => {
  //         commit('')
  //       },
  //       (error) => {
  //         console.log(error)
  //       }
  //     )
  //   },
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
