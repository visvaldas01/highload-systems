package ru.ifmo.highloadsystems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableFeignClients
@EnableR2dbcRepositories
public class HighloadSystemsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HighloadSystemsApplication.class, args);
    }

}
