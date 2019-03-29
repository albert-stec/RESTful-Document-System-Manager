package com.stecalbert.restfuldms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class AuthorityDto {

    private Long id;

    private String name;

}
