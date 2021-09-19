import PageLayout from '../components/PageLayout'
import styled from 'styled-components'
import Footer from '../components/Footer'

export default function Results() {
  return (
    <PageLayout>
      <Footer>Here are results</Footer>
    </PageLayout>
  )
}

const Wrapper = styled.div`
  display: grid;
  place-items: center;
  text-align: center;
`
