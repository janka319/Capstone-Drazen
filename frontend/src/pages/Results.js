import PageLayout from '../components/PageLayout'
import styled from 'styled-components'
import Header from '../components/Header'
import Button from '@mui/material/Button'
import { useAuth } from '../auth/AuthProvider'
import { Link, NavLink } from 'react-router-dom'
import ApartmentCard from '../components/ApartmentCard'
import { useEffect, useState } from 'react'
import { findAllFlats } from '../services/user-api-service'

export default function Results() {
  const { logout, user, token } = useAuth()
  const [flats, setFlats] = useState([])
  useEffect(() => {
    findAllFlats(token).then(setFlats)
  }, [token])

  return (
    <PageLayout>
      <Header>
        <Link to="/publish">Inserieren</Link>
        <Button variant="logout" onClick={logout}>
          Logout
        </Button>
        {user.role === 'admin' && (
          <NavLink to="/admin/registration">Admin</NavLink>
        )}
      </Header>

      <Wrapper>
        {flats.map(flat => (
          <ApartmentCard
            key={flat.id}
            image={flat.image}
            address={flat.address}
            size={flat.size}
            rent={flat.rent}
            email={flat.email}
          />
        ))}
      </Wrapper>
    </PageLayout>
  )
}

const Wrapper = styled.div`
  display: grid;
  grid-gap: 10px;
  place-items: center;
  width: 100%;
  height: 100%;
  overflow-y: scroll;
`
