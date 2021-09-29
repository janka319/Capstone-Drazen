import React, { useState } from 'react'
import Button from '@mui/material/Button'
import PageLayout from '../components/PageLayout'
import TextField from '@material-ui/core/TextField'
import { makeStyles } from '@material-ui/core/styles'
import styled from 'styled-components'
import SendIcon from '@mui/icons-material/Send'
import { Redirect } from 'react-router-dom'
import Error from '../components/Error'
import { useAuth } from '../auth/AuthProvider'

const useStyles = makeStyles(theme => ({
  button: {
    margin: theme.spacing(1),
  },
}))

const initialState = {
  username: '',
  password: '',
}

export default function Login() {
  const classes = useStyles

  const { login, user } = useAuth()
  const [credentials, setCredentials] = useState(initialState)
  const [error, setError] = useState()

  const handleCredentialsChange = event =>
    setCredentials({ ...credentials, [event.target.name]: event.target.value })

  const handleSubmit = event => {
    event.preventDefault()
    setError()
    login(credentials).catch(error => {
      setError(error)
    })
  }

  if (user) {
    return <Redirect to="/results" />
  }

  return (
    <PageLayout>
      <Wrapper>
        <TextField
          required
          id="standard required"
          label="Username"
          name="username"
          value={credentials.username}
          onChange={handleCredentialsChange}
        />
        <TextField
          required
          id="standard required"
          type="password"
          label="Password"
          name="password"
          value={credentials.password}
          onChange={handleCredentialsChange}
        />
        <Button
          onClick={handleSubmit}
          variant="contained"
          color="primary"
          className={classes.button}
          endIcon={<SendIcon />}
        >
          Login
        </Button>
      </Wrapper>
      {error && <Error>{error.message}</Error>}
    </PageLayout>
  )
}

const Wrapper = styled.div`
  display: grid;
  justify-content: center;
  align-items: center;
  text-align: center;
  grid-template-rows: 1fr 1fr 1fr;
`
