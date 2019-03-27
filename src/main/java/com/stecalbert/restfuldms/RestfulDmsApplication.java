package com.stecalbert.restfuldms;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class RestfulDmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestfulDmsApplication.class, args);
    }

    @Bean
    @Profile("dev")
    InitializingBean initDatabaseData() {

        return () -> {
        };
    }
}
