package com.stecalbert.restfuldms.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private String username;

    private String password;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Set<AuthorityEntity> userRoles;

    private String firstName;


    private String lastName;


    private String email;

    @PrePersist
    void setUserDefaultAuthorityIfNone() {
        if (CollectionUtils.isEmpty(userRoles)) {
            AuthorityEntity userAuthority = AuthorityEntity.builder()
                    .name("USER")
                    .user(this)
                    .build();
            setUserRoles(new HashSet<>(Set.of(userAuthority)));
        }
    }
}
