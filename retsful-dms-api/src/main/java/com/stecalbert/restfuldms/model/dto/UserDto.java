package com.stecalbert.restfuldms.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private @NotBlank String username;

    private @NotBlank String password;

    private @NotBlank String firstName;

    private @NotBlank String lastName;

    private @NotBlank String email;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<AuthorityDto> authorities;
}
