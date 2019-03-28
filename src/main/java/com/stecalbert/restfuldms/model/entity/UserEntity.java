package com.stecalbert.restfuldms.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @NotNull
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    Set<AuthorityEntity> userRoles;

    @PrePersist
    void setUserAuthority() {
        AuthorityEntity userAuthority = AuthorityEntity.builder()
                .name("USER")
                .user(this)
                .build();
        this.setUserRoles(new HashSet<>(Set.of(userAuthority)));
    }

}
