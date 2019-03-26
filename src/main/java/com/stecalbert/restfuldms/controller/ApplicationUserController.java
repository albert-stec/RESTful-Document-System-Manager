package com.stecalbert.restfuldms.controller;

import com.stecalbert.restfuldms.exception.ExistingUsernameException;
import com.stecalbert.restfuldms.model.dto.ApplicationUserDto;
import com.stecalbert.restfuldms.model.entity.ApplicationUserEntity;
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
public class ApplicationUserController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserService userService;


    @Autowired
    public ApplicationUserController(UserService userService,
                                     BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping
    public ResponseEntity<ApplicationUserEntity> registerUser(@RequestBody ApplicationUserDto applicationUserDto) {
        Optional<ApplicationUserEntity> userOptional =
                userService.findByUsername(applicationUserDto.getUsername());
        userOptional.ifPresent(e -> {
            throw new ExistingUsernameException("User with that username already exists.");
        });

        String encryptedPassword = bCryptPasswordEncoder.encode(applicationUserDto.getPassword());
        applicationUserDto.setPassword(encryptedPassword);

        var modelMapper = new ModelMapper();
        var applicationUserEntity =
                modelMapper.map(applicationUserDto, ApplicationUserEntity.class);

        return new ResponseEntity<>(
                userService.save(applicationUserEntity),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<List<ApplicationUserDto>> getAll() {
        var modelMapper = new ModelMapper();
        List<ApplicationUserEntity> applicationUserEntityList = userService.findAll();
        Type sourceListType =
                new TypeToken<List<ApplicationUserEntity>>() {
                }.getType();
        List<ApplicationUserDto> applicationUserDtoList
                = modelMapper.map(applicationUserEntityList, sourceListType);

        return new ResponseEntity<>(
                applicationUserDtoList,
                HttpStatus.OK
        );
    }
}
