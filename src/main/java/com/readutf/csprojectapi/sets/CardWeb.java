package com.readutf.csprojectapi.sets;

import com.readutf.csprojectapi.errors.WebException;
import com.readutf.csprojectapi.profile.Profile;
import com.readutf.csprojectapi.profile.ProfileRepository;
import com.readutf.csprojectapi.profile.ProfileWeb;
import com.readutf.csprojectapi.tokens.Token;
import com.readutf.csprojectapi.tokens.TokenRepository;
import com.readutf.csprojectapi.utils.UUIDParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CardWeb {

    TokenRepository tokenRepo;
    ProfileRepository profileRepo;
    RevisionSetRepository revisionSetRepo;
    RevisionCardRepository revisionCardRepo;
    
    @PutMapping("/profile/set/create")
    public HashMap<String, Long> createSet(String token, long profile, String name) {
        Profile profile1 = handleTokenAuth(token, profile);
        if (revisionSetRepo.existsByNameAndOwnerId(name, profile)) throw new WebException("set_exists", name);
        RevisionSet save = revisionSetRepo.save(new RevisionSet(name, profile));
        profile1.getSavedRevisionCards().add(save.getId());
        profileRepo.save(profile1);
        return new HashMap<>() {{
            put("set_id", save.getId());
        }};
    }

    @GetMapping("/profile/sets")
    public List<Long> getSets(String token, long profile) {
        Profile profile1 = handleTokenAuth(token, profile);
        return profile1.getSavedRevisionCards();
    }

    @GetMapping("/profile/set")
    public RevisionSet revisionSet(String token, long profile, long setId) {
        Optional<RevisionSet> revisionSet = Optional.ofNullable(revisionSetRepo.getById(setId));
        RevisionSet revisionSet1 = revisionSet.orElseThrow(() -> new WebException("set_not_found", setId));
        if (revisionSet1.isPrivateSet()) {
            Profile profile1 = handleTokenAuth(token, profile);
            if (profile1.getId() != revisionSet1.getOwnerId()) {
                throw new WebException("private_set", setId);
            }
        }
        return revisionSet1;
    }

    @GetMapping("/profile/set/add")
    public RevisionCard addRevisionCard(String token, long profile, long setId,
                                        String keyWord, String definition) {
        Profile profile1 = handleTokenAuth(token, profile);
        RevisionSet revisionSet = handleSetAuth(profile1, setId);

        if (revisionCardRepo.existsByIdAndKeyWord(setId, keyWord)) {
            throw new WebException("card_exists", keyWord);
        }

        return revisionCardRepo.save(new RevisionCard(revisionSet.getId(), keyWord, definition));
    }

    @GetMapping("/profile/set/cards")
    public List<RevisionCard> getCards(String token, long profile, long setId) {
        Profile profile1 = handleTokenAuth(token, profile);
        RevisionSet revisionSet = handleSetAuth(profile1, setId);

        return revisionCardRepo.findAllBySetId(setId)
                .orElseThrow(() -> new WebException("no_cards", setId));
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
