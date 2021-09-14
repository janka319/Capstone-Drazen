package de.janka.capstonedrazen.controller;


import de.janka.capstonedrazen.api.AccessToken;
import de.janka.capstonedrazen.api.Credentials;
import de.janka.capstonedrazen.config.JwtConfig;
import de.janka.capstonedrazen.model.UserEntity;
import de.janka.capstonedrazen.repo.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;


@SpringBootTest(
        properties = "spring.profiles.active:h2",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class AuthControllerTest {

    @LocalServerPort
    private int port;

    private String url() {
        return "http://localhost:" + port + "/auth";
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtConfig jwtConfig;

    @AfterEach
    public void clearDb() {
        userRepository.deleteAll();
    }

    @Test
    public void successfulLogin() {
        // Given
        String username = "bill";
        String password = "12345";
        String hashedPassword = passwordEncoder.encode(password);
        userRepository.saveAndFlush(
                UserEntity.builder()
                        .userName(username)
                        .password(hashedPassword).build()
        );
        Credentials credentials = Credentials.builder()
                .username(username)
                .password(password).build();

        // When
        String url = url() + "/access_token";
        ResponseEntity<AccessToken> response = restTemplate
                .postForEntity(url, credentials, AccessToken.class);

        //Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        String token = response.getBody().getToken();
        Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecret())
                .parseClaimsJws(token).getBody();
        assertThat(claims.getSubject(), is(username));
    }

    @Test
    public void badCredentials() {
        // Given
        Credentials credentials = Credentials.builder()
                .username("Lisa")
                .password("password").build();

        // When
        String url = url() + "/access_token";
        ResponseEntity<AccessToken> response = restTemplate
                .postForEntity(url, credentials, AccessToken.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
        assertThat(response.getBody(), nullValue());
    }

    @Test
    public void noCredentials() {
        // Given
        Credentials credentials = null;

        // When
        String url = url() + "/access_token";
        ResponseEntity<AccessToken> response = restTemplate
                .postForEntity(url, credentials, AccessToken.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(response.getBody(), nullValue());
    }


}