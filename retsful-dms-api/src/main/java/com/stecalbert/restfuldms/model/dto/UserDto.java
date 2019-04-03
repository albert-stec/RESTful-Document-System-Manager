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
    private Long id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private Set<AuthorityDto> authorities;


}
