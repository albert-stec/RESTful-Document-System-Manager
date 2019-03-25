package com.stecalbert.restfuldms.security;

import com.stecalbert.restfuldms.model.ApplicationUser;
import com.stecalbert.restfuldms.repository.ApplicationUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private ApplicationUserRepository applicationUserRepository;

    public UserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<ApplicationUser> userOptional = applicationUserRepository.findByUsername(username);
        ApplicationUser user = userOptional.orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(user.getUsername(),
                user.getPassword(), Collections.emptyList());
    }
}
