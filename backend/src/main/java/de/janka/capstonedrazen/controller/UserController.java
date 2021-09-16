package de.janka.capstonedrazen.controller;


import de.janka.capstonedrazen.api.NewPassword;
import de.janka.capstonedrazen.api.User;
import de.janka.capstonedrazen.model.UserEntity;
import de.janka.capstonedrazen.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static de.janka.capstonedrazen.controller.UserController.USER_CONTROLLER_TAG;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@Tag(name = USER_CONTROLLER_TAG, description = "Provides CRUD operations for an User")
@Api(
        tags = USER_CONTROLLER_TAG
)


@CrossOrigin
@RestController
@RequestMapping("/user")
@Getter
@Setter
public class UserController {

    public static final String USER_CONTROLLER_TAG = "User";

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/create", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = SC_BAD_REQUEST, message = "Unable to create User with blank name"),
            @ApiResponse(code = SC_CONFLICT, message = "Unable to create User, user already exists")
    })
    public ResponseEntity<User> create(@AuthenticationPrincipal UserEntity authUser, @RequestBody User user) {
        if (!authUser.getRole().equals("admin")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            UserEntity userEntity = map(user);

            UserEntity createdUserEntity = userService.create(userEntity);
            User createdUser = map(createdUserEntity);
            createdUser.setPassword(createdUserEntity.getPassword());
            return ok(createdUser);

        } catch (IllegalArgumentException e) {
            return badRequest().build();
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }

    @PutMapping("/password")
    public ResponseEntity<User> updatePassword(@AuthenticationPrincipal UserEntity authUser, @RequestBody NewPassword newPassword){
        try {
            UserEntity updatedUserEntity = userService.updatePassword(authUser.getUserName(), newPassword.getPassword());
            User updatedUser = map(updatedUserEntity);
            updatedUser.setPassword(newPassword.getPassword());
            return ok(updatedUser);
        }catch (IllegalArgumentException e) {
            return badRequest().build();
        }

    }


    @GetMapping(value = "{userName}", produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = SC_NOT_FOUND, message = "User not found")
    })
    public ResponseEntity<User> find(@PathVariable String userName) {
        Optional<UserEntity> userEntityOptional = userService.find(userName);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            User user = map(userEntity);
            return ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = SC_NO_CONTENT, message = "No users found")
    })
    public ResponseEntity<List<User>> findAll() {
        List<UserEntity> userEntities = userService.findAll();
        if (userEntities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<User> users = map(userEntities);
        return ok(users);
    }

    private User map(UserEntity userEntity) {
        return User.builder()
                .userName(userEntity.getUserName())
                .role(userEntity.getRole())
                .build();
    }

    private UserEntity map(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(user.getUserName());
        userEntity.setRole(user.getRole());
        return userEntity;
    }

    private List<User> map(List<UserEntity> userEntities) {
        List<User> users = new LinkedList<>();
        for (UserEntity userEntity : userEntities) {
            User user = map(userEntity);
            users.add(user);
        }
        return users;
    }


}
