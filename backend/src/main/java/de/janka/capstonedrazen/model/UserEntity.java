package de.janka.capstonedrazen.model;

import lombok.*;

import javax.persistence.*;

@Builder(toBuilder = true)
@Entity
@Table(name = "user_entity_table")
@Data
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

}
