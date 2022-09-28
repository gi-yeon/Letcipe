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
  accessToken: '',
  refreshToken: '',
  userId: 0,
  nickname: '',
})

export const mutations = {
  SET_TOKEN(state, data) {
    state.accessToken = data.accessToken
    state.refreshToken = data.refreshToken
  },
  SET_USER(state, data) {
    state.userId = data.id
    state.nickname = data.nickname
  },
}

export const getters = {}

export const actions = {
  async login({ commit }, user) {
    await login(
      user,
      ({ data }) => {
        commit('SET_TOKEN', data)
        this.$cookies.set('access-token', data.accessToken)
        localStorage.setItem('access-token', data.accessToken)
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async readUser({ commit }) {
    await readUser(({ data }) => {
      console.log('-------------------------------')
      commit('SET_USER', data)
      console.log(data)
    })
    // await this.$interceptor.get(`/user`).then(({ data }) => {
    //   commit('SET_USER', data)
    // })
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
