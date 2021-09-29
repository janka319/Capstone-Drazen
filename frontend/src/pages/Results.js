import PageLayout from '../components/PageLayout'
import styled from 'styled-components'
import Header from '../components/Header'
import { useAuth } from '../auth/AuthProvider'
import ApartmentCard from '../components/ApartmentCard'
import { useEffect, useState } from 'react'
import { findAllFlats } from '../services/user-api-service'
import Navbar from '../components/Navbar'

export default function Results() {
  const { user, token } = useAuth()
  const [flats, setFlats] = useState([])
  useEffect(() => {
    findAllFlats(token).then(setFlats)
  }, [token])

  const reloadPage = () => {
    findAllFlats(token)
      .then(setFlats)
      .catch(error => console.error(error))
  }

  const allFlats = flats.map(flat => (
    <ApartmentCard
      key={flat.id}
      id={flat.id}
      image={flat.image}
      address={flat.address}
      size={flat.size}
      rent={flat.rent}
      email={flat.email}
      reloadPage={reloadPage}
    />
  ))

  return (
    <PageLayout>
      <Header>
        <Navbar user={user} />
      </Header>

      <Wrapper>{allFlats}</Wrapper>
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
