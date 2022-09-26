// import VueCookies from 'vue-cookies'
import axios from 'axios'
// import Vue from 'vue'
import { API_BASE_URL } from '../config'

// Vue.use(VueCookies)
function apiInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      'Content-type': 'application/json',
    },
  })
  return instance
}
function interceptorInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      'Content-type': 'application/json',
      'access-token': getCookie('access-token='),
    },
  })
  return instance
}
function getCookie(cookieName) {
  // let cookieValue = null
  // if (document.cookie) {
  //   const array = document.cookie.split(escape(cookieName) + '=')
  //   if (array.length >= 2) {
  //     const arraySub = array[1].split(';')
  //     cookieValue = unescape(arraySub[0])
  //   }
  // }
  // return cookieValue
  // console.log(document.cookie.split('access-token=')[1])
  // return document.cookie.split(cookieName)[1]
}

export { apiInstance, interceptorInstance }
