package de.janka.capstonedrazen.service;

import de.janka.capstonedrazen.model.UserEntity;
import de.janka.capstonedrazen.repo.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;


@Service
@Getter
@Setter
public class UserService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity create(UserEntity userEntity) {
        String name = userEntity.getUserName();
        if(!hasText(name)) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        checkUserNameExists(name);

        String randomPassword = RandomStringUtils.randomAlphanumeric(12);
        String hashedPassword = passwordEncoder.encode(randomPassword);
        userEntity.setPassword(hashedPassword);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        savedUserEntity.setPassword(randomPassword);
        return savedUserEntity;
    }

    public UserEntity createAsUser(UserEntity userEntity) {
        String name = userEntity.getUserName();
        if(!hasText(name)) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        checkUserNameExists(name);


        String hashedPassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(hashedPassword);
        return userRepository.save(userEntity);
    }

    private void checkUserNameExists(String name) {
        Optional<UserEntity> existingUser = find(name);
        if(existingUser.isPresent()) {
            throw new EntityExistsException(String.format("User with name=%s already exists", name));
        }
    }

    public Optional<UserEntity> find(String userName) {
        return userRepository.findByUserName(userName);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public UserEntity updatePassword(String userName, String password) {
        UserEntity userEntity = find(userName).orElseThrow(() -> new IllegalArgumentException("Username not found: " + userName));
        String hashedPassword = passwordEncoder.encode(password);
        userEntity.setPassword(hashedPassword);
        return userRepository.save(userEntity);
    }

    public Optional<UserEntity> delete(String name) {
        Optional<UserEntity> userEntityOptional = find(name);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            userRepository.delete(userEntity);
        }
        return userEntityOptional;
    }
}
