import PageLayout from '../components/PageLayout'
import styled from 'styled-components'
import Footer from '../components/Footer'
import Header from '../components/Header'
import Button from '@mui/material/Button'
import { useAuth } from '../auth/AuthProvider'
import { NavLink } from 'react-router-dom'

export default function Results() {
  const { logout, user } = useAuth()
  return (
    <PageLayout>
      <Header>
        <Button variant="logout" onClick={logout}>
          Logout
        </Button>
        {user.role === 'admin' && (
          <NavLink to="/admin/registration">Admin</NavLink>
        )}
      </Header>
      <Wrapper>
        <Footer>Here are results</Footer>
      </Wrapper>
    </PageLayout>
  )
}

const Wrapper = styled.div`
  display: grid;
  place-items: center;
  text-align: center;
`
