import PageLayout from '../components/PageLayout'
import Header from '../components/Header'
import Button from '@material-ui/core/Button'
import Footer from '../components/Footer'
import styled from 'styled-components'
import TextField from '@material-ui/core/TextField'
import SaveIcon from '@material-ui/icons/Save'
import { makeStyles } from '@material-ui/core/styles'
import React, { useState } from 'react'

import { createUserAsUser } from '../services/user-api-service'

const useStyles = makeStyles(theme => ({
  button: {
    margin: theme.spacing(1),
  },
}))

const initialState = { username: '', password: '', role: 'user' }

export default function AdminRegistration() {
  const classes = useStyles()

  const [credentials, setCredentials] = useState(initialState)

  const handleCredentialsChange = event => {
    setCredentials({ ...credentials, [event.target.name]: event.target.value })
  }

  const handleSubmit = event => {
    createUserAsUser(credentials).then(dto => dto.password)
  }

  return (
    <PageLayout>
      <Header>Welcome</Header>
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
          startIcon={<SaveIcon />}
        >
          Create user
        </Button>
      </Wrapper>
      <Footer></Footer>
    </PageLayout>
  )
}

const Wrapper = styled.div`
  display: grid;
  place-items: center;
  text-align: center;
`
