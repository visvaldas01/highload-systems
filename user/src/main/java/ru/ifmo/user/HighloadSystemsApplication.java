package ru.ifmo.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HighloadSystemsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HighloadSystemsApplication.class, args);
    }

}
