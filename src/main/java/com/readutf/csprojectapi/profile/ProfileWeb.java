package com.readutf.csprojectapi.profile;

import com.readutf.csprojectapi.errors.WebException;
import com.readutf.csprojectapi.sets.RevisionSet;
import com.readutf.csprojectapi.sets.RevisionSetRepository;
import com.readutf.csprojectapi.utils.HashMapBuilder;
import com.readutf.csprojectapi.tokens.Token;
import com.readutf.csprojectapi.tokens.TokenRepository;
import com.readutf.csprojectapi.utils.UUIDParser;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class ProfileWeb {

    TokenRepository tokenRepo;
    ProfileRepository profileRepo;
    RevisionSetRepository revisionSetRepo;

    @GetMapping("/profile") @SneakyThrows
    public Profile getProfile(String token, long profile) {

        return handleTokenAuth(token, profile);
    }

    @PutMapping("/profile/username")
    public HashMap<String, String> updateUsername(String token, long profileId, String username) {
        Profile profile = handleTokenAuth(token, profileId);

        profile.setUsername(username);
        profileRepo.save(profile);

        return new HashMapBuilder<String, String>().add(
                "response", "OK").build();
    }

    @PutMapping("profile/createSet")
    public List<Long> createSet(String token, long profile, String name) {
        Profile profile1 = handleTokenAuth(token, profile);
        if(revisionSetRepo.existsByNameAndOwnerId(name, profile)) throw new WebException("set_exists", name);
        RevisionSet save = revisionSetRepo.save(new RevisionSet(name, profile));
        profile1.getSavedRevisionCards().add(save.getId());
        return profile1.getSavedRevisionCards();
    }

    public Profile handleTokenAuth(String stringToken, long profileId) throws WebException {
        Profile profile = Optional.ofNullable(profileRepo.getById(profileId))
                .orElseThrow(() -> new WebException("no_profile", stringToken));

        UUID uToken = UUIDParser.parseUUID(stringToken).orElseThrow(() -> new WebException("invalid_token", stringToken));
        Token token = Optional.ofNullable(tokenRepo.getByToken(uToken)).orElseThrow(() -> new WebException("invalid_token", stringToken));

        if(token.getAuthId() != profile.getAuthId()) {
            throw new WebException("no_auth", token);
        }
        if(token.isExpired()) {
            throw new WebException("token_expired", token);
        }
        return profile;
    }

}
