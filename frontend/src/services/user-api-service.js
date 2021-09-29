import axios from 'axios'

export const getToken = credentials =>
  axios
    .post('/api/capstoneDrazen/auth/access_token', credentials)
    .then(response => response.data)
    .then(dto => dto.token)

const headers = token => ({
  headers: {
    Authorization: `Bearer ${token}`,
  },
})

export const createUser = (credentials, token) =>
  axios
    .post('/api/capstoneDrazen/user/create', credentials, headers(token))
    .then(response => response.data)

export const createUserAsUser = credentials =>
  axios
    .post('/api/capstoneDrazen/user/createAsUser', credentials)
    .then(response => response.data)

export const findAllFlats = token =>
  axios
    .get('/api/capstoneDrazen/flats/search', headers(token))
    .then(response => response.data)

export const createFlat = (flat, token) =>
  axios
    .post('/api/capstoneDrazen/flats/publish', flat, headers(token))
    .then(response => response.data)

export const deleteById = (id, token) =>
  axios.delete(`/api/capstoneDrazen/flats/${id}`, headers(token))

export const updatePassword = (token, password) =>
  axios.put('api/capstoneDrazen/user/password', { password }, headers(token))
