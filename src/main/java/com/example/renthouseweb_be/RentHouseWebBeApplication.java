package com.example.renthouseweb_be;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class RentHouseWebBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentHouseWebBeApplication.class, args);
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"));
        System.out.println("Application is running in time zone: " + TimeZone.getDefault().getID());
    }

}
