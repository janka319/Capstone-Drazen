import Button from '../components/Button'
import Main from '../components/Main'
import PageLayout from '../components/PageLayout'
import Header from '../components/Header'
import Footer from '../components/Footer'
import styled from 'styled-components'

export default function Welcome() {
  return (
    <PageLayout>
      <Header></Header>
      <Wrapper>
        <Main as="form">
          <input type="text" title="Username" name="username" />
          <input title="Password" name="password" type="password" />
          <Button>login</Button>
        </Main>
      </Wrapper>
      <Footer></Footer>
    </PageLayout>
  )
}

const Wrapper = styled.div`
  display: grid;
  place-items: center;
  text-align: center;
`
