import styled from 'styled-components'
import Background from '../components/Background.jpg'

export default styled.div`
  background-image: url(${Background});
  background-size: cover;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;
  background-repeat: no-repeat;
  background-position: center;
  position: fixed;
  overflow-x: scroll;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: grid;
  grid-template-rows: min-content 1fr min-content;
  place-items: center;
  background-color: rgb(141, 197, 199);
`
