import axios from 'axios'

export const createUser = credentials => {
  axios
    .post('/api/capstoneDrazen/user/create', credentials)
    .then(response => response.data)
}
