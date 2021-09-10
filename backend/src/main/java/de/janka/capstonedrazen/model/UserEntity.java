package de.janka.capstonedrazen.model;

import lombok.*;

import javax.persistence.*;

@Builder(toBuilder = true)
@Entity
@Table(name = "user_entity_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "role")
    private String role;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserEntity that = (UserEntity) o;
        return this.getUserName() != null && this.getUserName().equals(that.getUserName());
    }

    @Override
    public int hashCode() {
        return getUserName().hashCode();
    }
}
