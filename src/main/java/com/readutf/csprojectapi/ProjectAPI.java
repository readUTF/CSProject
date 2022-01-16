package com.readutf.csprojectapi;

import com.github.javafaker.Faker;
import com.readutf.csprojectapi.hashing.PasswordHashing;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class ProjectAPI {

    @Getter private static Faker faker;
    @Getter private static PasswordHashing passwordHashing;

    public static void main(String[] args) throws NoSuchAlgorithmException {
        faker = Faker.instance();
        passwordHashing = new PasswordHashing();

        SpringApplication.run(ProjectAPI.class, args);
    }



}
