import axios from 'axios'
import { API_BASE_URL } from '.';

const instance = axios.create({
    baseURL: API_BASE_URL,
    timeout: 1000
  });

instance.interceptors.request.use(
    function (config) {
        config.headers["Content-Type"] = "application/json";
        config.headers["accessToken"] = null;
        return config;
    }, 
    function (error) {
        return Promise.reject(error);
    }
);

instance.interceptors.response.use(
    function (response) {
        return response;
    },

    function (error) {
        return Promise.reject(error);
    }
);

// 생성한 인스턴스를 익스포트 합니다.
export default instance;
