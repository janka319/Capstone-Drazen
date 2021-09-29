import { useAuth } from '../auth/AuthProvider'
import Header from '../components/Header'
import Main from '../components/Main'
import Username from '../components/Username'
import Navbar from '../components/Navbar'
import PageLayout from '../components/PageLayout'
import Button from '@mui/material/Button'

export default function Logout() {
  const { user, logout } = useAuth()
  return (
    <PageLayout>
      <Header>
        <Navbar user={user} />
      </Header>
      <Main>
        <p>
          You are logged in as <Username>{user.username}</Username>
        </p>
        <Button onClick={logout} variant="contained">
          Logout
        </Button>
      </Main>
    </PageLayout>
  )
}
