package com.stecalbert.restfuldms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {

    private Long id;

    private @NotNull String title;

    private @NotNull String brief;

    private String description;

//    private @NotNull String base64File;

    private @NotNull byte[] file;


//    private @NotNull MultipartFile file;

    private String ownerUsername;
}
