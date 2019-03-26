package com.stecalbert.restfuldms.service.impl;

import com.stecalbert.restfuldms.model.entity.ApplicationUserEntity;
import com.stecalbert.restfuldms.repository.ApplicationUserRepository;
import com.stecalbert.restfuldms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final ApplicationUserRepository userRepository;

    @Autowired
    public UserServiceImpl(ApplicationUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ApplicationUserEntity> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ApplicationUserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ApplicationUserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public ApplicationUserEntity save(ApplicationUserEntity applicationUserEntity) {
        return userRepository.save(applicationUserEntity);
    }
}
