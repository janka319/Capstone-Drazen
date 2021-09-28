import styled from 'styled-components/macro'

export default function ApartmentCard(props) {
  return (
    <Wrapper>
      <img src={props.image} alt="Bild der Wohnung" />
      <p>Adresse der Wohnung: {props.address}</p>
      <p>Größe der Wohnung: {props.size}</p>
      <p>Miete der Wohnung: {props.rent}</p>
      <a href={props.email}>Kontakt des Anbieter</a>
    </Wrapper>
  )
}
const Wrapper = styled.div`
  width: min-content;
  padding: 24px;
  text-align: center;
  border: 1px solid #333;
  border-radius: 12px;
  box-shadow: 1px 2px 8px #666;
`
