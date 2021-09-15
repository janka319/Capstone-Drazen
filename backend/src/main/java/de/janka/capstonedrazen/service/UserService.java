package de.janka.capstonedrazen.service;

import de.janka.capstonedrazen.model.UserEntity;
import de.janka.capstonedrazen.repo.UserRepository;
import lombok.Getter;
import lombok.Setter;
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
    private final PasswordService passwordService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordService passwordService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity create(UserEntity userEntity) {
        String name = userEntity.getUserName();
        if(!hasText(name)) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        checkUserNameExists(name);

        String password = passwordService.getNewPassword();
        String hashedPassword = passwordEncoder.encode(password);

        UserEntity savedUser = userRepository.save(userEntity.toBuilder()
                .password(hashedPassword)
                .build());
        return savedUser.toBuilder().password(password).build();
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
}
