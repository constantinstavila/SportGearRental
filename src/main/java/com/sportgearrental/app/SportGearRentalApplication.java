package com.sportgearrental.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SportGearRentalApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportGearRentalApplication.class, args);
    }
}