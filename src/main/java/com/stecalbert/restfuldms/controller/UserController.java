package com.stecalbert.restfuldms.controller;

import com.stecalbert.restfuldms.exception.ExistingUsernameException;
import com.stecalbert.restfuldms.model.dto.UserDto;
import com.stecalbert.restfuldms.model.entity.UserEntity;
import com.stecalbert.restfuldms.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserService userService;


    @Autowired
    public UserController(UserService userService,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping
    public ResponseEntity<UserEntity> registerUser(@RequestBody UserDto userDto) {
        Optional<UserEntity> userOptional =
                userService.findByUsername(userDto.getUsername());
        userOptional.ifPresent(e -> {
            throw new ExistingUsernameException("User with that username already exists.");
        });

        String encryptedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptedPassword);

        var modelMapper = new ModelMapper();
        var applicationUserEntity =
                modelMapper.map(userDto, UserEntity.class);

        return new ResponseEntity<>(
                userService.save(applicationUserEntity),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        var modelMapper = new ModelMapper();
        List<UserEntity> userEntityList = userService.findAll();
        Type sourceListType =
                new TypeToken<List<UserEntity>>() {
                }.getType();
        List<UserDto> userDtoList
                = modelMapper.map(userEntityList, sourceListType);

        return new ResponseEntity<>(
                userDtoList,
                HttpStatus.OK
        );
    }
}
