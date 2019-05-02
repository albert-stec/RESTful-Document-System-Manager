package com.stecalbert.restfuldms.model.entity;

import com.stecalbert.restfuldms.model.constants.DocumentStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity(name = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentEntity {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue
    private Long id;

    @ManyToOne
    private UserEntity owner;

    @Column(nullable = false)
    private String brief;

    private String description;

    @Basic
    @Column(nullable = false)
    private LocalDateTime creationDateTime;

    @Column(nullable = false)
    private Integer version;

    @Basic
    @Column
    private LocalDateTime lastUpdatedDateTime;

    @Basic
    @Column
    private LocalDateTime acceptanceDateTime;

    @ManyToOne
    private UserEntity acceptor;

    @Enumerated(EnumType.STRING)
    @Column(length = 8, nullable = false)
    private DocumentStatus status;

    @Lob
    @Column
    private byte[] file;

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<LocalDateTime> getLastUpdatedDateTime() {
        return Optional.ofNullable(lastUpdatedDateTime);
    }

    public Optional<LocalDateTime> getAcceptanceDateTime() {
        return Optional.ofNullable(acceptanceDateTime);
    }

}
