package de.janka.capstonedrazen.controller;

import de.janka.capstonedrazen.api.NewPassword;
import de.janka.capstonedrazen.api.User;
import de.janka.capstonedrazen.config.JwtConfig;
import de.janka.capstonedrazen.model.UserEntity;
import de.janka.capstonedrazen.repo.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;


import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


@SpringBootTest(
        properties = "spring.profiles.active:h2",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    private String url() {
        return "http://localhost:" + port + "/user";
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void clearUserRepo() {
        userRepository.deleteAll();
    }

    @Test
    public void createNewUserAsAdmin() {
        // Given
        User userToAdd = User.builder()
                .userName("Lisa")
                .role("user")
                .build();

        // When
        HttpEntity<User> httpEntity = new HttpEntity<>(userToAdd, authorizedHeader("Frank", "admin"));
        ResponseEntity<User> response = restTemplate.exchange(url() + "/create", HttpMethod.POST, httpEntity, User.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        User actualUser = response.getBody();
        assertThat(actualUser.getUserName(), is("Lisa"));
        assertThat(actualUser.getRole(), is("user"));
        assertThat(actualUser.getPassword(), is(notNullValue()));
    }

    @Test
    public void userCanNotCreateANewUser() {
        // Given
        User userToAdd = User.builder()
                .userName("Lisa")
                .role("user")
                .build();

        // When
        HttpEntity<User> httpEntity = new HttpEntity<>(userToAdd, authorizedHeader("Frank", "user"));
        ResponseEntity<User> response = restTemplate.exchange(url() + "/create", HttpMethod.POST, httpEntity, User.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void userCanChangeHerPassword() {
        // Given
        userRepository.save(UserEntity.builder()
                .userName("Gwen")
                .password("old-password")
                .role("user")
                .build());

        NewPassword newPassword = new NewPassword("new-password");


        // When
        HttpEntity<NewPassword> httpEntity = new HttpEntity<>(newPassword, authorizedHeader("Gwen", "user"));
        ResponseEntity<User> response = restTemplate
                .exchange(url() + "/password", HttpMethod.PUT, httpEntity, User.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        User actualUser = response.getBody();
        assertThat(actualUser.getUserName(), is("Gwen"));
        assertThat(actualUser.getRole(), is("user"));
        assertThat(actualUser.getPassword(), is("new-password"));

        UserEntity foundUserEntity = userRepository.findByUserName("Gwen").orElseThrow();
        assertThat(foundUserEntity.getPassword(), is(not("old-password")));

    }

    @Test
    public void adminCanResetUsersPassword() {
        // Given
        userRepository.save(UserEntity.builder()
                .userName("Gwen")
                .password("old-password")
                .role("user")
                .build());

        // When
        HttpEntity<Void> httpEntity = new HttpEntity<>(authorizedHeader("Mark", "admin"));
        ResponseEntity<User> response = restTemplate
                .exchange(url() + "/Gwen/reset-password", HttpMethod.PUT, httpEntity, User.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        User actualUser = response.getBody();
        assertThat(actualUser.getUserName(), is("Gwen"));
        assertThat(actualUser.getRole(), is("user"));
        assertThat(actualUser.getPassword(), is(not("old-password")));

        UserEntity foundUserEntity = userRepository.findByUserName("Gwen").orElseThrow();
        assertThat(foundUserEntity.getPassword(), is(not("old-password")));

    }

    @Test
    public void testOnlyAdminCanResetUserPassword() {
        // Given
        userRepository.save(UserEntity.builder()
                .userName("Gwen")
                .password("old-password")
                .role("user")
                .build());

        // When
        HttpEntity<Void> httpEntity = new HttpEntity<>(authorizedHeader("Mark", "user"));
        ResponseEntity<User> response = restTemplate
                .exchange(url() + "/Gwen/reset-password", HttpMethod.PUT, httpEntity, User.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    private HttpHeaders authorizedHeader(String username, String role) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        Instant now = Instant.now();
        Date iat = Date.from(now);
        Date exp = Date.from(now.plus(Duration.ofMinutes(jwtConfig.getExpiresAfterMinutes())));
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(iat)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret())
                .compact();


        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        return headers;
    }
}
