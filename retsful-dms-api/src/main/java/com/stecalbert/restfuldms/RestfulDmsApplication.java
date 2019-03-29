package com.stecalbert.restfuldms;

import com.stecalbert.restfuldms.model.entity.AuthorityEntity;
import com.stecalbert.restfuldms.model.entity.UserEntity;
import com.stecalbert.restfuldms.repository.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class RestfulDmsApplication {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    @Autowired
    public RestfulDmsApplication(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    public static void main(String... args) {
        SpringApplication.run(RestfulDmsApplication.class, args);
    }

    @Bean
    @Profile("dev")
    InitializingBean initDatabaseData() {

        AuthorityEntity adminAuthority = new AuthorityEntity();
        adminAuthority.setName("ADMIN");

        AuthorityEntity moderatorAuthority = new AuthorityEntity();
        moderatorAuthority.setName("MODERATOR");

        AuthorityEntity userAuthority = new AuthorityEntity();
        userAuthority.setName("DOMAIN");

        UserEntity superUser = new UserEntity();
        superUser.setUsername("super-user");
        superUser.setPassword(bCryptPasswordEncoder.encode("dmsadmin"));
        superUser.setUserRoles(new HashSet<>(Set.of(adminAuthority, moderatorAuthority, userAuthority)));

        return () -> userRepository.save(superUser);
    }
}
