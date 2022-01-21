package com.readutf.csprojectapi.profile;

import com.readutf.csprojectapi.errors.WebException;
import com.readutf.csprojectapi.sets.RevisionCard;
import com.readutf.csprojectapi.sets.RevisionCardRepository;
import com.readutf.csprojectapi.sets.RevisionSet;
import com.readutf.csprojectapi.sets.RevisionSetRepository;
import com.readutf.csprojectapi.tokens.Token;
import com.readutf.csprojectapi.tokens.TokenRepository;
import com.readutf.csprojectapi.utils.HashMapBuilder;
import com.readutf.csprojectapi.utils.UUIDParser;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
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
    RevisionCardRepository revisionCardRepo;

    @GetMapping("/profile")
    @SneakyThrows public Profile getProfile(String token, long profile) {

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

    public Profile handleTokenAuth(String stringToken, long profileId) throws WebException {
        Profile profile = Optional.ofNullable(profileRepo.getById(profileId))
                .orElseThrow(() -> new WebException("no_profile", stringToken));

        UUID uToken = UUIDParser.parseUUID(stringToken).orElseThrow(() -> new WebException("invalid_token", stringToken));
        Token token = Optional.ofNullable(tokenRepo.getByToken(uToken)).orElseThrow(() -> new WebException("invalid_token", stringToken));

        if (token.getAuthId() != profile.getAuthId()) {
            throw new WebException("no_auth", token);
        }
        if (token.isExpired()) {
            throw new WebException("token_expired", token);
        }
        return profile;
    }

    public RevisionSet handleSetAuth(Profile profile, long setId) throws WebException{
        RevisionSet revisionSet = revisionSetRepo.findById(setId)
                .orElseThrow(() -> new WebException("invalid_set", setId));
        if(revisionSet.isPrivateSet() && revisionSet.getOwnerId() != profile.getAuthId()) {
            throw new WebException("no_auth", "private_set");
        }
        return revisionSet;
    }

}
