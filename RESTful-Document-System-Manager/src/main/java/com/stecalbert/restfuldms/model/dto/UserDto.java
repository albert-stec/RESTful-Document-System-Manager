package com.stecalbert.restfuldms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDto {
    Long id;

    private String username;

    private String password;

    private Set<AuthorityDto> authorities;


}
