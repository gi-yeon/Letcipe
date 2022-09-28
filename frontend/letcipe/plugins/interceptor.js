export default function ({ $axios, $cookies }, inject) {
  // Create a custom axios instance
  const interceptor = $axios.create({
    headers: {
      common: {
        Accept: 'text/plain, */*',
      },
    },
  })

  // Set baseURL to something different
  interceptor.setHeader('Content-Type', 'application/json')
  interceptor.setHeader('access-token', $cookies.get('access-token'))

  // Inject to context as $api
  inject('interceptor', interceptor)
}
