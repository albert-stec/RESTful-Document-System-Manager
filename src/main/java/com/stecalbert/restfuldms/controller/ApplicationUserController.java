package com.stecalbert.restfuldms.controller;

import com.stecalbert.restfuldms.model.ApplicationUser;
import com.stecalbert.restfuldms.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class ApplicationUserController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ApplicationUserRepository userRepository;


    @Autowired
    public ApplicationUserController(ApplicationUserRepository userRepository,
                                     BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping
    public void registerUser(@RequestBody ApplicationUser applicationUser) {
        Optional<ApplicationUser> userOptional =
                userRepository.findByUsername(applicationUser.getUsername());
        userOptional.ifPresent(e -> {
            throw new RuntimeException("User with that username already exists.");
        });

        String encryptedPassword = bCryptPasswordEncoder.encode(applicationUser.getPassword());
        applicationUser.setPassword(encryptedPassword);
        userRepository.save(applicationUser);
    }

    @GetMapping
    public ResponseEntity<List<ApplicationUser>> getAll() {
        return new ResponseEntity<>(
                userRepository.findAll(),
                HttpStatus.OK
        );
    }
}
