package com.stecalbert.restfuldms.service.impl;

import com.stecalbert.restfuldms.configuration.i18n.Translator;
import com.stecalbert.restfuldms.exception.ExistingUserException;
import com.stecalbert.restfuldms.exception.UserNotFoundException;
import com.stecalbert.restfuldms.model.dto.UserDto;
import com.stecalbert.restfuldms.model.entity.UserEntity;
import com.stecalbert.restfuldms.repository.UserRepository;
import com.stecalbert.restfuldms.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.security.Principal;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    Translator translator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> findAll() {
        List<UserEntity> userEntityList = userRepository.findAll();
        Type userDtoListType =
                new TypeToken<List<UserDto>>() {
                }.getType();

        return modelMapper.map(userEntityList, userDtoListType);
    }

    @Transactional
    @Override
    public UserEntity save(UserDto userDto) {
        throwIfUsernameExists(userDto.getUsername());
        throwIfEmailExists(userDto.getEmail());

        String encryptedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptedPassword);

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity getAuthenticatedUser() {
        Principal principal =
                SecurityContextHolder.getContext().getAuthentication();

        return userRepository
                .findByUsername(principal.getName())
                .orElseThrow(() -> new UserNotFoundException("There was a problem with your authentication. " +
                        "Couldn't find user with given username."));
    }

    private void throwIfUsernameExists(String username) {
        if (userRepository.countByUsername(username) > 0) {
            throw new ExistingUserException("hello");
        }
    }

    private void throwIfEmailExists(String email) {
        if (userRepository.countByEmail(email) > 0) {
            throw new ExistingUserException("User with that email already exists.");
        }
    }
}
