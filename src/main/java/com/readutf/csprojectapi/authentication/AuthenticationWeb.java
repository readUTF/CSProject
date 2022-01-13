package com.readutf.csprojectapi.authentication;


import com.readutf.csprojectapi.errors.WebException;
import com.readutf.csprojectapi.tokens.Token;
import com.readutf.csprojectapi.tokens.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController @AllArgsConstructor
public class AuthenticationWeb {

    private TokenRepository tokenRepo;
    private AuthenticationRepository authRepo;

    private final String mailRegex = " \t\n" +
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";


    @PostMapping("/test")
    public String getTest() {
        return "test";
    }

    @GetMapping("/authentication")
    public Token getAuthid(String email, String password) {
        if(!email.matches(mailRegex)) {
            throw new WebException("invalid_email", email);
        }
        if(!isSecurePassword(password)) {
            throw new WebException("insecure_password", "N/A");
        }
        Authentication auth = authRepo.save(new Authentication(email, password));
        return tokenRepo.save(new Token(auth.getId(), UUID.randomUUID(), LocalDateTime.now(), "test"));
    }

    private static final String SECURE_PASSWORD_REGEX = "^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8}$";

    /**
     *
     * Checks if specific string matches a regex which checks for the following characteristics.
     *
     * ^                         Start anchor
     * (?=.*[A-Z].*[A-Z])        Ensure string has two uppercase letters.
     * (?=.*[!@#$&*])            Ensure string has one special case letter.
     * (?=.*[0-9].*[0-9])        Ensure string has two digits.
     * (?=.*[a-z].*[a-z].*[a-z]) Ensure string has three lowercase letters.
     * .{8}                      Ensure string is of length 8.
     * $
     *
     * @param password Password being checked
     * @return True if password is secure, false if password is insecure
     */
    public boolean isSecurePassword(String password) {
        return password.matches(SECURE_PASSWORD_REGEX);
    }

}
