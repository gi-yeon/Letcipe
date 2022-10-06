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
  myBookmarkRecipe,
  myBookmarkRecipeList,
  mycomment,
  mycommentNum,
  createCode,
  modifyPassword,
  myLikeRecipe,
} from '@/api/user'

export const state = () => ({
  idcheck: true,
  nickCheck: true,
  codeCheck: false,
  code: '',
  email: '',
  userJoinCheck: false,
  userid: '',
  birth: '',
  family: '',
  name: '',
  phone: '',
  userId: 0,
  nickname: '',
  profileImage: '',
  userGender: '',
  userJob: '',
  myRecipe: [],
  myRecipeList: [],
  myBookMarkRecipe: [],
  myBookMarkRecipeList: [],
  mylikeRecipe: [],
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

  SET_MY_RECIPE(state, recipes) {
    state.myRecipe = recipes
  },
  SET_MY_RECIPELIST(state, myRecipeList) {
    state.myRecipeList = myRecipeList
  },
  SET_MY_BOOKMARK_RECIPE(state, myBookMarkRecipe) {
    state.myBookMarkRecipe = myBookMarkRecipe
  },
  SET_MY_BOOKMARK_RECIPELIST(state, myBookMarkRecipeList) {
    state.myBookMarkRecipeList = myBookMarkRecipeList
  },
  SET_MY_LIKE_RECIPE(state, myLikeRecipe) {
    state.mylikeRecipe = myLikeRecipe
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
    state.myRecipeList = []
  },
  CLEAR_MY_RECIPE(state) {
    state.myRecipe = []
  },
  CLEAR_MY_RECIPELIST() {
    state.myRecipeList = []
  },
  CLEAR_MY_BOOKMARK_RECIPE() {
    state.myBookMarkRecipe = []
  },
  CLEAR_MY_BOOKMARK_RECIPELIST() {
    state.myBookMarkRecipeList = []
  },
  CLEAR_MY_LIKE_RECIPE() {
    state.mylikeRecipe = []
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
    await idCheck(
      userid,
      (res) => {
        console.log(res.status)
        commit('SET_IDCHECK_FALSE')
      },
      (error) => {
        console.log(error.status)
        commit('SET_IDCHECK_TRUE')
      }
    )
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
  async modifyPassword({ commit }, passwords) {
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
  async myrecipe({ commit }, pageable) {
    commit('CLEAR_MY_RECIPE')
    await myrecipe(
      pageable,
      ({ data }) => {
        // console.log(data)
        // console.log("내가만든 레시피 가져오기 성공!")
        commit('SET_MY_RECIPE', data.recipes)
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async myrecipeList({ commit }, pageable) {
    commit('CLEAR_MY_RECIPELIST')
    await myrecipeList(
      pageable,
      ({ data }) => {
        // console.log(data)
        console.log('내가만든 레시피리스트 가져오기 성공!')
        commit('SET_MY_RECIPELIST', data.recipeLists)
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async myBookmarkRecipe({ commit }, pageable) {
    commit('CLEAR_MY_BOOKMARK_RECIPE')
    await myBookmarkRecipe(
      pageable,
      ({ data }) => {
        console.log(data)
        // console.log("북마크 레시피 가져오기 성공!")
        commit('SET_MY_BOOKMARK_RECIPE', data.recipes)
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async myBookmarkRecipeList({ commit }, pageable) {
    commit('CLEAR_MY_BOOKMARK_RECIPELIST')
    await myBookmarkRecipeList(
      pageable,
      ({ data }) => {
        console.log(data)
        console.log('북마크 레시피목록 가져오기 성공!')
        commit('SET_MY_BOOKMARK_RECIPELIST', data.recipeLists)
      },
      (error) => {
        console.log(error)
      }
    )
  },
  async mycomment({ commit }, page) {
    let result
    await mycomment(
      page,
      ({ data }) => {
        result = data
      },
      (error) => {
        console.log(error)
      }
    )
    return result
  },
  async mycommentNum({ commit }) {
    let result
    await mycommentNum(
      ({ data }) => {
        result = data
      },
      (error) => {
        console.log(error)
      }
    )
    return result
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
    if (state.code === code) {
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
  changeNickCheck({ commit }) {
    commit('SET_NICKCHECK_TRUE')
  },
  async myLikeRecipe({ commit }, pageable) {
    await myLikeRecipe(
      pageable,
      ({ data }) => {
        console.log(data)
        commit('SET_MY_LIKE_RECIPE', data.recipes)
      },
      (error) => {
        console.log(error)
      }
    )
  },
}
