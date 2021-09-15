package de.janka.capstonedrazen.service;

import de.janka.capstonedrazen.model.UserEntity;
import de.janka.capstonedrazen.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserEntityDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserEntityDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity user = userRepository
                .findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("not found: " + userName));

        return User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .authorities("user")
                .build();
    }
}
