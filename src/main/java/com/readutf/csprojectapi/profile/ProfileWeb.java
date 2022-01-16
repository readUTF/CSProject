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
    public Profile getProfile(String token, long profile) {

        System.out.println(profile);

        Optional<Profile> byId = Optional.ofNullable(profileRepo.getById(profile));
        byId.orElseThrow(() -> new WebException("no_profile", token));

        if(!isAuthed(token, byId.get())) return null;

        return byId.get();
    }

    @PutMapping("/profile/username")
    public HashMap<String, String> updateUsername(String token, long profile, String username) {
        System.out.println(token);
        System.out.println(profile);
        System.out.println(username);

        if(profileRepo.existsProfileByUsername(username)) throw new WebException("name_taken", username);
        Optional<Profile> byId = Optional.ofNullable(profileRepo.getById(profile));
        byId.orElseThrow(() -> new WebException("no_profile", token));
        Profile entity = byId.get();
        if(!isAuthed(token, entity)) return null;

        entity.setUsername(username);
        profileRepo.save(entity);
        return new HashMapBuilder<String, String>().add(
                "response", "OK").build();
    }

    public boolean isAuthed(String token, Profile profile) {
        UUID uToken;

        try {
            uToken = UUID.fromString(token);
        } catch (Exception e) {throw new WebException("invalid_token", token);}
        Optional<Token> token1 = Optional.ofNullable(tokenRepo.getByToken(uToken));
        token1.orElseThrow(() -> new WebException("invalid_token", token));
        if(token1.get().getAuthId() != profile.getAuthId()) {
            throw new WebException("no_auth", token);
        }
        return true;
    }

}
