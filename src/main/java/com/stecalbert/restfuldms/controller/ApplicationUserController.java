package com.stecalbert.restfuldms.controller;

import com.stecalbert.restfuldms.model.ApplicationUser;
import com.stecalbert.restfuldms.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class ApplicationUserController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ApplicationUserRepository userRepository;


    @Autowired
    public ApplicationUserController(ApplicationUserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping
    public void registerUser(@RequestBody ApplicationUser applicationUser) {
        String encryptedPassword = bCryptPasswordEncoder.encode(applicationUser.getPassword());
        applicationUser.setPassword(encryptedPassword);
        userRepository.save(applicationUser);
    }
}
