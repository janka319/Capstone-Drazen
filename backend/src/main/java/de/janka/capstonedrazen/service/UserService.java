package de.janka.capstonedrazen.service;

import de.janka.capstonedrazen.model.UserEntity;
import de.janka.capstonedrazen.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;


@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity create(String userName) {

        if(!hasText(userName)) {
            throw new IllegalArgumentException("Name must not be blank");
        }

        Optional<UserEntity> existingUser = find(userName);
        if (existingUser.isPresent()) {
            throw new EntityExistsException(String.format(
                    "User with name=%s already exists", userName));
        }


        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);

        return userRepository.save(userEntity);
    }

    public Optional<UserEntity> find(String userName) {
        return userRepository.findByUserName(userName);
    }
}
