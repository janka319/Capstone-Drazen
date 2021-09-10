package de.janka.capstonedrazen.model;

import de.janka.capstonedrazen.SpringBootTests;
import de.janka.capstonedrazen.repo.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import java.awt.print.PrinterJob;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserEntityTest extends SpringBootTests {

    @AfterEach
    public void clearDb() {
        userRepository.deleteAll();
    }

    @Resource
    private UserRepository userRepository;

    @Test
    public void testCreateUserWithoutNameShouldThrow() {
        try {
            UserEntity userEntity = new UserEntity();
            userRepository.save(userEntity);
            fail("user without name must not be persisted");
        } catch (DataIntegrityViolationException ignore) {
            // expected
        }
    }

    @Test
    public void testCreateUserName() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName("foo");
        userEntity.setPassword("password");
        assertNull(userEntity.getId());

        UserEntity createdEntity = userRepository.save(userEntity);
        assertNotNull(createdEntity.getId());
        assertEquals(createdEntity, userEntity);

        // additional equals hash code test by adding same instance to set twice
        Set<UserEntity> users = new HashSet<>();
        users.add(userEntity);
        users.add(createdEntity);
        assertEquals(1, users.size());
    }

    @Test
    @Transactional
    public void testUserContainsName() {
        // GIVEN
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName("foo");
        userEntity.setPassword("password");
        UserEntity expectedUser = userRepository.save(userEntity);

        // WHEN
        Optional<UserEntity> actualUserOpt = userRepository.findByUserNameContains("o");
        assertTrue(actualUserOpt.isPresent());

        // THEN
        assertEquals(expectedUser, actualUserOpt.get());
    }

}


