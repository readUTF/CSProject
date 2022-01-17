package com.readutf.csprojectapi.tokens;

import com.readutf.csprojectapi.authentication.AuthenticationRepository;
import com.readutf.csprojectapi.profile.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity @Getter @Setter @NoArgsConstructor
public class Token {

    @Id @GeneratedValue private Long id;
    long authId;
    UUID token;
    LocalDateTime generatedTime;
    String ipAddress;

    /**
     * This entity is used to store api authentication tokens
     * As outline in the project summery, an authentication token is used to grant a client
     * access to the database without an email and password being exchanged each time.
     *
     * Tokens are linked to the user ip-address to prevent tokens from being extracted from memory or local storage
     * and are time limited, expiring after 30 days.
     *
     * @param authId The id of the corresponding authentication entity
     * @param token The token itself, provided with each api request to authenticate
     * @param generatedTime The time at which the token was generated
     * @param ipAddress The ip of the client who generated the token
     */
    public Token(long authId, UUID token, LocalDateTime generatedTime, String ipAddress) {
        this.authId = authId;
        this.token = token;
        this.generatedTime = generatedTime;
        this.ipAddress = ipAddress;
    }

    public boolean isExpired() {
        return Duration.between(LocalDateTime.now(), generatedTime).toDays() > 30;
    }

    public Profile getProfile(AuthenticationRepository authRepo) {
        authRepo.getById(authId).get
    }

}
