import axios from 'axios'

export const getToken = credentials =>
  axios
    .post('api/capstoneDrazen/auth/access_token', credentials)
    .then(response => response.data)

export const createUser = credentials =>
  axios
    .post('/api/capstoneDrazen/user/create', credentials)
    .then(response => response.data)

export const createUserAsUser = credentials =>
  axios
    .post('/api/capstoneDrazen/user/createAsUser', credentials)
    .then(response => response.data)
