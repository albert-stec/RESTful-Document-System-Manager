package com.stecalbert.restfuldms.model.dto;

import com.googlecode.jmapper.annotations.JMap;
import com.googlecode.jmapper.annotations.JMapConversion;
import com.stecalbert.restfuldms.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DocumentDto {

    @JMap
    private Long id;

    @JMap
    private @NotNull String brief;

    @JMap
    private String description;

//    @JMap
//    private @NotNull MultipartFile file;

    @JMap("owner")
    private String ownerUsername;

    @JMapConversion(from = "owner", to = "ownerUsername")
    public String ownerConversion(UserEntity owner) {
        return owner.getUsername();
    }

    @JMapConversion(from = "owner", to = "ownerUsername")
    public String fileConversion(UserEntity owner) {
        return owner.getUsername();
    }

    // Some setters and getter are needed explicity for JMapper

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDescription(Optional<String> description) {
        description.ifPresent(e -> this.description = e);
    }
}
