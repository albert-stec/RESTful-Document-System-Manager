package com.stecalbert.restfuldms.service.impl;

import com.stecalbert.restfuldms.exception.ExistingUsernameException;
import com.stecalbert.restfuldms.model.dto.UserDto;
import com.stecalbert.restfuldms.model.entity.UserEntity;
import com.stecalbert.restfuldms.repository.UserRepository;
import com.stecalbert.restfuldms.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> findAll() {
        List<UserEntity> userEntityList = userRepository.findAll();

        var modelMapper = new ModelMapper();
        Type sourceListType =
                new TypeToken<List<UserEntity>>() {
                }.getType();

        return modelMapper.map(userEntityList, sourceListType);
    }

    @Transactional
    @Override
    public UserEntity save(UserDto userDto) {
        throwIfUsernameExists(userDto.getUsername());

        String encryptedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptedPassword);

        var userEntity = new ModelMapper().map(userDto, UserEntity.class);
        return userRepository.save(userEntity);
    }

    private void throwIfUsernameExists(String username) {
        Optional<UserEntity> userOptional =
                userRepository.findByUsername(username);
        userOptional.ifPresent(e -> {
            throw new ExistingUsernameException("User with that username already exists.");
        });
    }
}
