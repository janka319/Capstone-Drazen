package de.janka.capstonedrazen.repo;

import de.janka.capstonedrazen.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findByUserNameContains(String name);

}
