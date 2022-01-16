package com.readutf.csprojectapi;

import com.github.javafaker.Faker;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;

@SpringBootApplication
public class ProjectAPI {

    @Getter private static Faker faker;

    public static void main(String[] args) {
        faker = Faker.instance();
        SpringApplication.run(ProjectAPI.class, args);
    }

}
