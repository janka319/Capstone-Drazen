import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
} from 'react-router-dom'
import Settings from './pages/Login'
import AdminRegistration from './pages/AdminRegistration'
import UserRegistration from './pages/UserRegistration'
import Login from './pages/Login'
import Results from './pages/Results'
import ProtectedRoute from './auth/ProtectedRoute'
import AuthProvider from './auth/AuthProvider'

export default function App() {
  return (
    <AuthProvider>
      <Router>
        <Switch>
          <Route path="/login" component={Login} />
          <ProtectedRoute path="/results" component={Results} />
          <ProtectedRoute path="/settings" component={Settings} />
          <ProtectedRoute
            path="/admin/registration"
            component={AdminRegistration}
          />
          <Route path="/user/registration" component={UserRegistration} />
          <Route path="/">
            <Redirect to="/login" />
          </Route>
        </Switch>
      </Router>
    </AuthProvider>
  )
}
