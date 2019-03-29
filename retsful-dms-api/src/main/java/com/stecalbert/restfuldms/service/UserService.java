package com.stecalbert.restfuldms.service;

import com.stecalbert.restfuldms.model.dto.UserDto;
import com.stecalbert.restfuldms.model.entity.UserEntity;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    UserEntity save(UserDto userDto);
}
