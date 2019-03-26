package com.stecalbert.restfuldms.service;

import com.stecalbert.restfuldms.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserEntity> findAll();

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByUsername(String username);

    UserEntity save(UserEntity userEntity);
}
