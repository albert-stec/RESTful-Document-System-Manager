package com.stecalbert.restfuldms.repository;

import com.stecalbert.restfuldms.model.entity.ApplicationUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUserEntity, Long> {
    Optional<ApplicationUserEntity> findByUsername(String username);
}
