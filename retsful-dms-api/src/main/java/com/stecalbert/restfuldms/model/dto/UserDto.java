package com.stecalbert.restfuldms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDto {
    private Long id;

    private @NotBlank String username;

    private @NotBlank String password;

    private @NotBlank String firstName;

    private @NotBlank String lastName;

    private @NotBlank String email;

    private Set<AuthorityDto> authorities;
}
