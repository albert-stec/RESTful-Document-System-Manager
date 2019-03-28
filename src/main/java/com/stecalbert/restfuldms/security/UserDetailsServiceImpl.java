package com.stecalbert.restfuldms.security;

import com.stecalbert.restfuldms.model.entity.AuthorityEntity;
import com.stecalbert.restfuldms.model.entity.UserEntity;
import com.stecalbert.restfuldms.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
                user.getPassword(), getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(UserEntity userEntity) {
        Set<AuthorityEntity> authorities = userEntity.getUserRoles();
        return authorities.stream()
                .map(e -> new SimpleGrantedAuthority(e.getName()))
                .collect(Collectors.toList());
    }
}
