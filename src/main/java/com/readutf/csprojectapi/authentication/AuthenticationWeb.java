package com.readutf.csprojectapi.authentication;


import com.github.javafaker.Faker;
import com.readutf.csprojectapi.ProjectAPI;
import com.readutf.csprojectapi.errors.WebException;
import com.readutf.csprojectapi.profile.Profile;
import com.readutf.csprojectapi.profile.ProfileRepository;
import com.readutf.csprojectapi.tokens.QuickMap;
import com.readutf.csprojectapi.tokens.Token;
import com.readutf.csprojectapi.tokens.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class AuthenticationWeb {

    private TokenRepository tokenRepo;
    private AuthenticationRepository authRepo;
    private ProfileRepository profileRepo;

    @PostMapping("/test")
    public String getTest() {
        return "test";
    }

    @GetMapping("/authentication")
    public HashMap<String, Object> getAuthid(String email, String password) {
        String mailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        if (!email.matches(mailRegex)) {
            throw new WebException("invalid_email", email);
        }

        if (!isSecurePassword(password)) {
            throw new WebException("insecure_password", "N/A");
        }

        Faker faker = ProjectAPI.getFaker();

        Authentication auth = authRepo.save(new Authentication(email, password));
        Profile profile = profileRepo.save(new Profile(auth.getId(), faker.superhero().prefix() + faker.name().firstName() + faker.address().buildingNumber(), LocalDateTime.now(), 0, 0));
        Token token = tokenRepo.save(new Token(auth.getId(), UUID.randomUUID(), LocalDateTime.now(), ""));
        return new QuickMap<String, Object>(
                "token", token.getToken(),
                "profile_id", profile.getId()
        ).getMap();
    }


    private static final String SECURE_PASSWORD_REGEX = "(?=^.{8,}$)(?=.*\\d)(?=.*[!@#$%^&*]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";

    /**
     * Checks if specific string matches a regex which checks for the following characteristics.
     * <p>
     * (?=^.{8,}$)               Ensure string is of length 8.
     * (?=.*\d)                  Ensure that there is at least 1 digit
     * (?=.*[!@#$%^&*]+)         Ensure that there is a special character
     * (?![.\n])                 Ensure there is no line breaks or .
     * (?=.*[A-Z])               Ensure that there is a capital letter
     * (?=.*[a-z])               Ensure that there is a lowercase letter
     *
     * @param password Password being checked
     * @return True if password is secure, false if password is insecure
     */
    public boolean isSecurePassword(String password) {
        return password.matches(SECURE_PASSWORD_REGEX);
    }

}
