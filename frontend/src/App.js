import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import Welcome from './pages/Welcome'
import Settings from './pages/Login'
import Registration from './pages/Registration'
import Login from './pages/Login'

export default function App() {
  return (
    <Router>
      <Switch>
        <Route exact path="/" component={Login} />
        <Route path="/settings" component={Settings} />
        <Route path="/registration" component={Registration} />
      </Switch>
    </Router>
  )
}
