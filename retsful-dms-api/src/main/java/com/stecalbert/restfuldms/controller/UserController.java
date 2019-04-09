package com.stecalbert.restfuldms.controller;

import com.stecalbert.restfuldms.model.dto.UserDto;
import com.stecalbert.restfuldms.model.entity.UserEntity;
import com.stecalbert.restfuldms.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(value = "US", description = "<div>\n" +
        "<p>The User Service (US) offers the following services</p><ul>\n" +
        "<li>Register new user;</li>\n" +
        "<li>Get all users or get all users for given role (parametrized);</li>\n" +
        "<li>Get single user by id;</li>\n" +
        "<li>Delete user;</li>\n" +
        "<li>Disable/Enable user;</li>\n" +
        "<li>Update user;</li>\n" +
        "</ul></div>", tags = "User Service (US)")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserEntity> registerUser(@RequestBody @Valid UserDto userDto) {

        return new ResponseEntity<>(
                userService.save(userDto),
                HttpStatus.OK
        );
    }

    @GetMapping
    @ApiOperation(value = "Get all users",
            authorizations = @Authorization(HttpHeaders.AUTHORIZATION))
    public ResponseEntity<List<UserDto>> getAll() {

        return new ResponseEntity<>(
                userService.findAll(),
                HttpStatus.OK
        );
    }
}
