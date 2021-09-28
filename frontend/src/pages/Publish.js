import { useAuth } from '../auth/AuthProvider'
import TextField from '@material-ui/core/TextField'
import React, { useState } from 'react'
import PageLayout from '../components/PageLayout'
import Header from '../components/Header'
import styled from 'styled-components'
import { createFlat } from '../services/user-api-service'
import Button from '../components/Button'

const initialState = {
  address: '',
  rent: '',
  size: '',
  image: '',
  email: '',
}

export default function Publish() {
  const { token } = useAuth()
  const [flat, setFlat] = useState(initialState)

  const handleFlatsChange = event => {
    setFlat({ ...flat, [event.target.name]: event.target.value })
  }

  const handleSubmit = () => {
    createFlat(flat, token)
      .then(setFlat)
      .finally(() => setFlat(initialState))
  }

  return (
    <PageLayout>
      <Header></Header>
      <Wrapper>
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
        <Button onClick={handleSubmit}>Hochladen</Button>
      </Wrapper>
    </PageLayout>
  )
}

const Wrapper = styled.div`
  display: grid;
  place-items: center;
  text-align: center;
  grid-template-rows: 1fr 1fr 1fr;
`
