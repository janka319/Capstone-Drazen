package de.janka.capstonedrazen.controller;



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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static de.janka.capstonedrazen.controller.UserController.USER_CONTROLLER_TAG;
import static javax.servlet.http.HttpServletResponse.*;
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
    public ResponseEntity<User> create(@RequestBody User user) {

            UserEntity createdUserEntity = userService.create(user);
            User createdUser = map(createdUserEntity);
            return ok(createdUser);

    }


    @GetMapping(value = "{userName}", produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = SC_NOT_FOUND, message = "User not found")
    })
    public ResponseEntity<User> find(@PathVariable String userName) {
            Optional<UserEntity> userEntityOptional = userService.find(userName);
            if(userEntityOptional.isPresent()) {
                UserEntity userEntity = userEntityOptional.get();
                User user = map(userEntity);
                return ok(user);
            }
            return ResponseEntity.notFound().build();
    }



    private User map(UserEntity userEntity) {
        return User.builder()
                .userName(userEntity.getUserName())
                .password(userEntity.getPassword())
                .build();
    }


}
