import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
} from 'react-router-dom'

import AdminRegistration from './pages/AdminRegistration'
import UserRegistration from './pages/UserRegistration'
import Login from './pages/Login'
import Results from './pages/Results'
import ProtectedRoute from './auth/ProtectedRoute'
import AuthProvider from './auth/AuthProvider'
import Publish from './pages/Publish'
import Settings from './pages/Settings'
import Logout from './pages/Logout'

export default function App() {
  return (
    <AuthProvider>
      <Router>
        <Switch>
          <Route path="/login" component={Login} />
          <ProtectedRoute path="/results" component={Results} />
          <ProtectedRoute path="/publish" component={Publish} />
          <ProtectedRoute path="/settings" component={Settings} />
          <ProtectedRoute path="/logout" component={Logout} />
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
