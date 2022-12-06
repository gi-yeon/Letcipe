import axios from 'axios'
import { API_BASE_URL, HADOOP_BASE_URL } from '../config'

function apiInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      'Content-type': 'application/json',
    },
    timeout: 5000,
  })
  return instance
}
function fileInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  })
  return instance
}
function hadoopInstance() {
  const instance = axios.create({
    baseURL: HADOOP_BASE_URL,
  })
  return instance
}
export { apiInstance, fileInstance, hadoopInstance }
