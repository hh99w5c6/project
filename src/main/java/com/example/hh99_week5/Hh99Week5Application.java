package com.example.hh99_week5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Hh99Week5Application {

    public static void main(String[] args) {
        SpringApplication.run(Hh99Week5Application.class, args);
    }
}
