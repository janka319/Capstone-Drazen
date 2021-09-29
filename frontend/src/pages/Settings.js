import { useAuth } from '../auth/AuthProvider'
import { useState } from 'react'
import PageLayout from '../components/PageLayout'
import Header from '../components/Header'
import TextField from '@material-ui/core/TextField'
import Error from '../components/Error'
import ButtonGroup from '../components/ButtonGroup'
import Button from '@material-ui/core/Button'
import Navbar from '../components/Navbar'
import { updatePassword } from '../services/user-api-service'
import DeleteIcon from '@mui/icons-material/Delete'
import SaveIcon from '@material-ui/icons/Save'
import { makeStyles } from '@material-ui/core/styles'
import styled from 'styled-components'

const useStyles = makeStyles(theme => ({
  button: {
    margin: theme.spacing(1),
  },
}))

const initialPasswords = {
  newPassword: '',
  retypePassword: '',
}

export default function Settings() {
  const classes = useStyles()

  const { token, logout, user } = useAuth()
  const [passwords, setPasswords] = useState(initialPasswords)
  const [error, setError] = useState()

  const handlePasswordsChange = event =>
    setPasswords({ ...passwords, [event.target.name]: event.target.value })

  const clearPasswords = () => setPasswords(initialPasswords)

  const handleSubmit = event => {
    event.preventDefault()
    setError()
    updatePassword(token, passwords.newPassword)
      .then(logout)
      .catch(error => {
        setError(error)
      })
  }

  const passwordMatch =
    passwords.newPassword.length &&
    passwords.newPassword === passwords.retypePassword

  return (
    <PageLayout>
      <Header>
        <Navbar user={user} />
      </Header>
      <Wrapper onSubmit={handleSubmit}>
        <TextField
          label="New Password"
          name="newPassword"
          type="password"
          value={passwords.newPassword}
          onChange={handlePasswordsChange}
        />
        <TextField
          label="Retype Password"
          name="retypePassword"
          type="password"
          value={passwords.retypePassword}
          onChange={handlePasswordsChange}
        />
        {!passwordMatch && <Error>Enter matching passwords</Error>}
        <ButtonGroup>
          <Button
            onClick={clearPasswords}
            variant="contained"
            color="primary"
            className={classes.button}
            startIcon={<DeleteIcon />}
          >
            Cancel
          </Button>
          <Button
            disabled={!passwordMatch}
            onClick={handleSubmit}
            variant="contained"
            color="primary"
            className={classes.button}
            endIcon={<SaveIcon />}
          >
            Save
          </Button>
        </ButtonGroup>
      </Wrapper>
      {error && <Error>{error.message}</Error>}
    </PageLayout>
  )
}

const Wrapper = styled.form`
  display: grid;
  justify-content: center;
  align-items: center;
  text-align: center;
  grid-template-rows: 1fr 1fr 1fr;
`
