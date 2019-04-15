package com.stecalbert.restfuldms.controller;

import com.stecalbert.restfuldms.model.dto.UserDto;
import com.stecalbert.restfuldms.model.entity.UserEntity;
import com.stecalbert.restfuldms.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
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
    private final HttpServletRequest request;

    @Autowired
    public UserController(UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }

    @PostMapping
    @ApiOperation("Register new user")
    public ResponseEntity<UserEntity> registerUser(@RequestBody @Valid UserDto userDto) {
        UserEntity createdUser = userService.save(userDto);

        return ResponseEntity
                .created(URI.create(request.getRequestURI() + '/' + createdUser.getId()))
                .body(createdUser);
    }

    @GetMapping
    @ApiOperation(value = "Get all users",
            authorizations = @Authorization(HttpHeaders.AUTHORIZATION))
    public ResponseEntity<List<UserDto>> getAll() {

        return ResponseEntity
                .ok()
                .body(userService.findAll());
    }
}
