import { useAuth } from '../auth/AuthProvider'
import TextField from '@material-ui/core/TextField'
import React, { useState } from 'react'
import PageLayout from '../components/PageLayout'
import Header from '../components/Header'
import styled from 'styled-components'
import { createFlat } from '../services/user-api-service'
import Button from '@mui/material/Button'
import SendIcon from '@mui/icons-material/Send'
import { makeStyles } from '@material-ui/core/styles'
import Navbar from '../components/Navbar'
import DeleteIcon from '@mui/icons-material/Delete'
import ButtonGroup from '../components/ButtonGroup'
import Error from '../components/Error'

const useStyles = makeStyles(theme => ({
  button: {
    margin: theme.spacing(1),
  },
}))

const initialFlat = {
  address: '',
  rent: '',
  size: '',
  image: '',
  email: '',
}

export default function Publish() {
  const classes = useStyles

  const { token, user } = useAuth()
  const [flat, setFlat] = useState(initialFlat)

  const handleFlatsChange = event => {
    setFlat({ ...flat, [event.target.name]: event.target.value })
  }

  const handleSubmit = event => {
    event.preventDefault()
    createFlat(flat, token).finally(() => setFlat(initialFlat))
  }

  const clearFlat = () => setFlat(initialFlat)

  const flatCheck =
    flat.address.length &&
    flat.size.length &&
    flat.rent.length &&
    flat.email.length &&
    flat.image.length

  return (
    <PageLayout>
      <Header>
        <Navbar user={user} />
      </Header>
      <Wrapper onSubmit={handleSubmit}>
        <TextField
          required
          id="standard required"
          label="Adresse"
          name="address"
          value={flat.address}
          onChange={handleFlatsChange}
        />
        <TextField
          required
          id="standard required"
          label="Bild url"
          name="image"
          value={flat.image}
          onChange={handleFlatsChange}
        />
        <TextField
          required
          id="standard required"
          label="Größe"
          name="size"
          value={flat.size}
          onChange={handleFlatsChange}
        />
        <TextField
          required
          id="standard required"
          label="Miete(Kalt)"
          name="rent"
          value={flat.rent}
          onChange={handleFlatsChange}
        />
        <TextField
          required
          id="standard required"
          label="E-mail"
          name="email"
          value={flat.email}
          onChange={handleFlatsChange}
        />
        {!flatCheck && <Error>No field should be empty!</Error>}
        <ButtonGroup>
          <Button
            style={{
              margin: '8px',
            }}
            onClick={clearFlat}
            variant="contained"
            color="primary"
            className={classes.button}
            startIcon={<DeleteIcon />}
          >
            Cancel
          </Button>
          <Button
            disabled={!flatCheck}
            style={{
              margin: '8px',
            }}
            onClick={handleSubmit}
            variant="contained"
            color="primary"
            className={classes.button}
            endIcon={<SendIcon />}
          >
            Publish
          </Button>
        </ButtonGroup>
      </Wrapper>
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
