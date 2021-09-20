import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import Settings from './pages/Login'
import AdminRegistration from './pages/AdminRegistration'
import UserRegistration from './pages/UserRegistration'
import Login from './pages/Login'
import { useEffect, useState } from 'react'
import { getToken } from './services/user-api-service'
import Results from './pages/Results'
import ProtectedRoute from './auth/ProtectedRoute'

export default function App() {
  const [token, setToken] = useState()

  const login = credentials => getToken(credentials).then(setToken)

  useEffect(() => {
    console.log('token changed to', token)
  }, [token])

  return (
    <Router>
      <Switch>
        <Route exact path="/" component={Login} />
        <ProtectedRoute exact path="/results" component={Results} />
        <Route path="/login">
          <Login onLogin={login} token={token} />
        </Route>
        <ProtectedRoute path="/settings" component={Settings} />
        <ProtectedRoute
          path="/admin/registration"
          component={AdminRegistration}
        />
        <Route path="/user/registration" component={UserRegistration} />
      </Switch>
    </Router>
  )
}
