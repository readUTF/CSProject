package com.readutf.csprojectapi.profile;

import com.readutf.csprojectapi.errors.WebException;
import com.readutf.csprojectapi.tokens.HashMapBuilder;
import com.readutf.csprojectapi.tokens.Token;
import com.readutf.csprojectapi.tokens.TokenRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class ProfileWeb {

    TokenRepository tokenRepo;
    ProfileRepository profileRepo;

    @GetMapping("/profile") @SneakyThrows
    public Profile getProfile(String token) {


        Optional<Profile> byId = Optional.ofNullable(profileRepo.getById(profile));
        byId.orElseThrow(() -> new WebException("no_profile", token));

        handleTokenAuth(token, byId.get());
        return byId.get();
    }

    @PutMapping("/profile/username")
    public HashMap<String, String> updateUsername(String token, String username) {
        System.out.println(token);
        System.out.println(profile);
        System.out.println(username);

        if(profileRepo.existsProfileByUsername(username)) throw new WebException("name_taken", username);
        Optional<Profile> byId = Optional.ofNullable(profileRepo.getById(profile));
        byId.orElseThrow(() -> new WebException("no_profile", token));
        Profile entity = byId.get();
        handleTokenAuth(token, entity);

        entity.setUsername(username);
        profileRepo.save(entity);
        return new HashMapBuilder<String, String>().add(
                "response", "OK").build();
    }

    @PutMapping("/profile/createSet")
    public String

    public void handleTokenAuth(String token, Profile profile) throws WebException {
        UUID uToken;

        try {
            uToken = UUID.fromString(token);
        } catch (Exception e) {throw new WebException("invalid_token", token);}
        Optional<Token> token1 = Optional.ofNullable(tokenRepo.getByToken(uToken));
        token1.orElseThrow(() -> new WebException("invalid_token", token));
        if(token1.get().getAuthId() != profile.getAuthId()) {
            throw new WebException("no_auth", token);
        }
        if(token1.get().isExpired()) {
            throw new WebException("token_expired", token);
        }
    }

}
