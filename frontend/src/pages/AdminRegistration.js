import PageLayout from '../components/PageLayout'
import Header from '../components/Header'
import Button from '@material-ui/core/Button'
import styled from 'styled-components'
import TextField from '@material-ui/core/TextField'
import SaveIcon from '@material-ui/icons/Save'
import { makeStyles } from '@material-ui/core/styles'
import { useState } from 'react'

import { createUser } from '../services/user-api-service'
import { useAuth } from '../auth/AuthProvider'
import Navbar from '../components/Navbar'

const useStyles = makeStyles(theme => ({
  button: {
    margin: theme.spacing(1),
  },
}))

const initialState = { username: '', password: '', role: 'user' }

export default function AdminRegistration() {
  const classes = useStyles()

  const [credentials, setCredentials] = useState(initialState)
  const { token, user: admin } = useAuth()
  const [newPassword, setNewPassword] = useState('')

  const handleCredentialsChange = event => {
    setCredentials({ ...credentials, [event.target.name]: event.target.value })
  }

  const handleSubmit = event => {
    createUser(credentials, token).then(dto => setNewPassword(dto.password))
  }

  return (
    <PageLayout>
      <Header>
        <Navbar user={admin} />
      </Header>
      <Wrapper onSubmit={handleSubmit}>
        <TextField
          required
          id="standard required"
          label="Username"
          name="username"
          value={credentials.username}
          onChange={handleCredentialsChange}
        />
        <Button
          onClick={handleSubmit}
          variant="contained"
          color="primary"
          className={classes.button}
          startIcon={<SaveIcon />}
        >
          Create user
        </Button>
        <p>New Password:{newPassword}</p>
      </Wrapper>
    </PageLayout>
  )
}

const Wrapper = styled.form`
  display: grid;
  place-items: center;
  text-align: center;
`
