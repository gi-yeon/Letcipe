import {
  login,
  signup,
  //   check,
  mypage,
  modifyMember,
  deleteMember,
  myrecipe,
  myrecipeList,
} from '@/api/user'

export const state = () => ({
  loginMember: null,
  flightNum: '',
  seatInfo: null,
  memberDetail: null,
  terms: {
    termService: 'N',
    privacyPolicy: 'N',
    locationBased: 'N',
    promotionalInfo: 'N',
  },
})

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
