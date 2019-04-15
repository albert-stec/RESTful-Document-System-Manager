package com.stecalbert.restfuldms.model.entity;

import com.stecalbert.restfuldms.model.constants.DocumentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity(name = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentEntity {

    @Id
    @Generated
    private Long id;

    @ManyToOne
    private @NotNull UserEntity owner;

    @Column(nullable = false)
    private @NotNull String shortDescription;

    private String description;

    @Basic
    @Column(nullable = false)
    private @NotNull LocalDateTime creationDateTime;

    private int version;

    @Basic
    private LocalDateTime lastUpdatedDateTime;

    @Basic
    private LocalDateTime acceptanceTime;

    @ManyToOne
    private @NotNull UserEntity acceptor;

    @Enumerated(EnumType.STRING)
    @Column(length = 8, nullable = false)
    private @NotNull DocumentStatus status;

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<LocalDateTime> getLastUpdatedDateTime() {
        return Optional.ofNullable(lastUpdatedDateTime);
    }
}
