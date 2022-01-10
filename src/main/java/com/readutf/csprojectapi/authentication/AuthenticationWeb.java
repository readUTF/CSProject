package com.readutf.csprojectapi.authentication;


import com.readutf.csprojectapi.errors.WebException;
import com.readutf.csprojectapi.tokens.Token;
import com.readutf.csprojectapi.tokens.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController @AllArgsConstructor
public class AuthenticationWeb {

    private TokenRepository tokenRepo;
    private AuthenticationRepository authRepo;

    private final String mailRegex = " \t\n" +
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    @PutMapping("/authentication/")
    UUID getAuthid(@RequestParam("email") String email, @RequestParam("passwordHash") String passwordHash, HttpServletRequest request) {
        if(!email.matches(mailRegex)) {
            throw new WebException("invalid_email", passwordHash);
        }
        if(!passwordHash.matches("/^[a-f0-9]{64}$/gi")) {
            throw new WebException("invalid_hash", passwordHash);
        }
        Authentication authentication = authRepo.save(new Authentication(email, passwordHash));
        Token token = tokenRepo.save(new Token(authentication.getId(), UUID.randomUUID(), LocalDateTime.now(), request.getLocalAddr()));
        return token.getToken();
    }

}
