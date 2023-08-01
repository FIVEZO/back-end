package com.sparta.toogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ToogoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToogoApplication.class, args);
    }

}
