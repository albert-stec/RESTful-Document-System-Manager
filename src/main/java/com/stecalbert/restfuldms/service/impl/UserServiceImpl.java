package com.stecalbert.restfuldms.service.impl;

import com.stecalbert.restfuldms.model.entity.UserEntity;
import com.stecalbert.restfuldms.repository.UserRepository;
import com.stecalbert.restfuldms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
}
