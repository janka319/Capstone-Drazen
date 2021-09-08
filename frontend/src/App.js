import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import Welcome from './pages/Welcome'
import Settings from './pages/Settings'

export default function App() {
  return (
    <Router>
      <Switch>
        <Route path="/">
          <Welcome />
        </Route>
        <Route path="/settings" component={Settings} />
      </Switch>
    </Router>
  )
}
