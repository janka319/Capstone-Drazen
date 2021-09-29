import { NavLink } from 'react-router-dom'
import styled from 'styled-components/macro'
import React from 'react'

export default function Navbar({ user, ...props }) {
  return (
    <Wrapper {...props}>
      {user && <NavLink to="/logout">Logout</NavLink>}
      {!user && <NavLink to="/login">Login</NavLink>}
      {user && <NavLink to="/results">Results</NavLink>}
      {user && <NavLink to="/settings">Settings</NavLink>}
      {user && <NavLink to="/publish">Publish</NavLink>}
      {user && user.role === 'admin' && (
        <NavLink to="/admin/registration">Admin</NavLink>
      )}
    </Wrapper>
  )
}

const Wrapper = styled.nav`
  width: 100%;
  padding: var(--size-m);
  display: flex;
  overflow-y: scroll;
  flex-grow: 1;
  margin: 0;
  text-align: center;
  text-decoration: none;

  a {
    flex-grow: 1;
    margin: 0 var(--size-s);
    text-align: center;
    text-decoration: none;
  }
`
