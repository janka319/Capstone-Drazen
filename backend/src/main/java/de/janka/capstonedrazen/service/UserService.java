package de.janka.capstonedrazen.service;

import de.janka.capstonedrazen.api.User;
import de.janka.capstonedrazen.model.UserEntity;
import de.janka.capstonedrazen.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;


@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity create(User user) {

        if(!hasText(user.getUserName())) {
            throw new IllegalArgumentException("Name must not be blank");
        }

        if(!hasText(user.getPassword())) {
            throw new IllegalArgumentException("Password must not be blank");
        }

        Optional<UserEntity> existingUser = find(user.getUserName());
        if (existingUser.isPresent()) {
            throw new EntityExistsException(String.format(
                    "User with name=%s already exists", user.getUserName()));
        }


        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(user.getUserName());
        userEntity.setPassword(user.getPassword());

        return userRepository.save(userEntity);
    }

    public Optional<UserEntity> find(String userName) {
        return userRepository.findByUserName(userName);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }
}
