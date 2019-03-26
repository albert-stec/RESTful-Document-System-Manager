package com.stecalbert.restfuldms.service;

import com.stecalbert.restfuldms.model.entity.ApplicationUserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<ApplicationUserEntity> findAll();

    Optional<ApplicationUserEntity> findById(Long id);

    Optional<ApplicationUserEntity> findByUsername(String username);

    ApplicationUserEntity save(ApplicationUserEntity applicationUserEntity);
}
