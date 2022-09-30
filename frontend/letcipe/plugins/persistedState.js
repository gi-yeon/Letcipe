import createPersistedState from 'vuex-persistedstate'
import Cookies from 'js-cookie'

export default ({ store, req }) => {
  createPersistedState({
    paths: ['user', 'recipe'],
    storage: {
      getItem: (key) =>
        // See https://nuxtjs.org/guide/plugins/#using-process-flags
        Cookies.get(key),
      // Please see https://github.com/js-cookie/js-cookie#json, on how to handle JSON.
      setItem: (key, value) =>
        Cookies.set(key, value, { expires: 3, secure: false }),
      removeItem: (key) => Cookies.remove(key),
    },
  })(store)
}
 