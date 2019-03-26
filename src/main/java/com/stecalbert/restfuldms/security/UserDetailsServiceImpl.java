package com.stecalbert.restfuldms.security;

import com.stecalbert.restfuldms.model.entity.UserEntity;
import com.stecalbert.restfuldms.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        UserEntity user = userOptional.orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(user.getUsername(),
                user.getPassword(), Collections.emptyList());
    }
}
