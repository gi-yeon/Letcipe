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

export const state = () => ({})

export const mutations = {}

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
    await idCheck(
      userid,
      ({ data }) => {
        console.log(data)
      },
      (error) => {
        console.log(error.response.status)
      }
    )
  },
  async nicknameCheck({ commit }, nickname) {
    await nicknameCheck(
      nickname,
      ({ data }) => {
        commit('')
      },
      (error) => {
        console.log(error)
      }
    )
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
