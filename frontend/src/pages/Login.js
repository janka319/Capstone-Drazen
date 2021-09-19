import React, { useState } from 'react'
import Header from '../components/Header'
import Button from '@mui/material/Button'
import PageLayout from '../components/PageLayout'
import TextField from '@material-ui/core/TextField'
import { makeStyles } from '@material-ui/core/styles'
import styled from 'styled-components'
import SendIcon from '@mui/icons-material/Send'
import Footer from '../components/Footer'
import { Redirect } from 'react-router-dom'
import Error from '../components/Error'

const useStyles = makeStyles(theme => ({
  button: {
    margin: theme.spacing(1),
  },
}))

const initialState = {
  username: '',
  password: '',
}

export default function Login({ token, onLogin }) {
  const classes = useStyles()

  const [credentials, setCredentials] = useState(initialState)
  const [error, setError] = useState()

  const handleCredentialsChange = event =>
    setCredentials({ ...credentials, [event.target.name]: event.target.value })

  const handleSubmit = event => {
    event.preventDefault()
    onLogin(credentials).catch(setError)
  }

  if (token) {
    return <Redirect to="/results" />
  }

  return (
    <PageLayout>
      <Header></Header>
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
      <Footer></Footer>
    </PageLayout>
  )
}

const Wrapper = styled.div`
  display: grid;
  place-items: center;
  text-align: center;
`
