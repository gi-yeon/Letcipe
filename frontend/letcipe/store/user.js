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

<<<<<<< HEAD
export const state = () => ({})

export const mutations = {}
=======
export const state = () => ({
  idcheck: true,
  nickCheck: true,
  codeCheck: false,
  code: '',
  userJoinCheck: false,
  userId: 0,
  nickname: '',
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
  },
  CLEAR_USER(state) {
    state.userId = 0
    state.nickname = ''

  },
}
>>>>>>> frontend

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
