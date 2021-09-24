import axios from 'axios'

export const getToken = credentials =>
  axios
    .post('api/capstoneDrazen/auth/access_token', credentials)
    .then(response => response.data)
    .then(dto => dto.token)

export const createUser = (credentials, token) =>
  axios
    .post('/api/capstoneDrazen/user/create', credentials, {
      headers: {
        Authorization: 'Bearer ' + token,
      },
    })
    .then(response => response.data)

export const createUserAsUser = credentials =>
  axios
    .post('/api/capstoneDrazen/user/createAsUser', credentials)
    .then(response => response.data)

export const findAllFlats = token =>
  axios
    .get('/api/capstoneDrazen/flats', token, {
      headers: {
        Authorization: 'Bearer ' + token,
      },
    })
    .then(response => response.data)
