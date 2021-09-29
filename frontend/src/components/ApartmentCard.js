import styled from 'styled-components/macro'
import { deleteById } from '../services/user-api-service'
import { useAuth } from '../auth/AuthProvider'
import DeleteIcon from '@mui/icons-material/Delete'
import React from 'react'
import { makeStyles } from '@material-ui/core/styles'
import Button from '@mui/material/Button'

const useStyles = makeStyles(theme => ({
  button: {
    margin: theme.spacing(1),
  },
}))

export default function ApartmentCard({
  id,
  image,
  address,
  size,
  rent,
  email,
  reloadPage,
}) {
  const classes = useStyles

  const { token, user } = useAuth()

  const handleDelete = () => {
    deleteById(id, token)
      .then(() => reloadPage())
      .catch(error => console.error(error))
  }

  return (
    <Wrapper>
      <img src={image} alt="Bild der Wohnung" />
      <address>Adresse der Wohnung: {address}</address>
      <p>Größe der Wohnung: {size}</p>
      <p>Miete der Wohnung: {rent}</p>
      <a href={'mailto:' + email}>Kontakt des Anbieter</a>
      {user && user.role === 'admin' && (
        <Button
          style={{
            margin: '8px',
          }}
          onClick={handleDelete}
          variant="contained"
          color="primary"
          className={classes.button}
          startIcon={<DeleteIcon />}
        >
          Delete
        </Button>
      )}
    </Wrapper>
  )
}
const Wrapper = styled.div`
  width: min-content;
  padding: 24px;
  text-align: center;
  border: 1px solid #333;
  border-radius: 12px;
  box-shadow: 1px 2px 8px #666;
  background-color: RGB(216, 145, 139);
  font-size: 16px;
`
