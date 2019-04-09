package com.stecalbert.restfuldms.service.impl;

import com.stecalbert.restfuldms.exception.ExistingUserException;
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
        throwIfEmailExists(userDto.getEmail());

        String encryptedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptedPassword);

        UserEntity userEntity = new ModelMapper().map(userDto, UserEntity.class);
        return userRepository.save(userEntity);
    }

    private void throwIfUsernameExists(String username) {
        if (userRepository.countByUsername(username) > 0) {
            throw new ExistingUserException("User with that username already exists.");
        }
    }

    private void throwIfEmailExists(String email) {
        if (userRepository.countByEmail(email) > 0) {
            throw new ExistingUserException("User with that email already exists.");
        }
    }
}
