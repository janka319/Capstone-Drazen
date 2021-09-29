import styled from 'styled-components/macro'
import { deleteById } from '../services/user-api-service'
import { useAuth } from '../auth/AuthProvider'

export default function ApartmentCard(props, reloadPage) {
  const { token } = useAuth()

  const handleDelete = () => {
    deleteById(props.id, token).then(reloadPage)
  }

  return (
    <Wrapper>
      <img src={props.image} alt="Bild der Wohnung" />
      <address>Adresse der Wohnung: {props.address}</address>
      <p>Größe der Wohnung: {props.size}</p>
      <p>Miete der Wohnung: {props.rent}</p>
      <a href={'mailto:' + props.email}>Kontakt des Anbieter</a>
      <button onClick={handleDelete}>Delete</button>
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
  background-color: RGB(216, 145, 139);
  font-size: 16px;
`
