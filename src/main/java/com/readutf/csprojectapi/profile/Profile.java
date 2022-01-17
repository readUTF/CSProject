package com.readutf.csprojectapi.profile;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.readutf.csprojectapi.authentication.Authentication;
import com.readutf.csprojectapi.authentication.AuthenticationRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonSerialize
public class Profile {

    @Id
    @GeneratedValue
    private Long id;

    private Long authId;
    private String username;
    LocalDateTime lastUsernameChange;
    int completedCards;
    long revisionTime;

    @ElementCollection
    List<Long> savedRevisionCards;

    public Profile(Long authId, String username, LocalDateTime lastUsernameChange, int completedCards, long revisionTime) {
        this.authId = authId;
        this.username = username;
        this.lastUsernameChange = lastUsernameChange;
        this.completedCards = completedCards;
        this.revisionTime = revisionTime;
    }

    public Authentication getLinkedAuthentication(AuthenticationRepository authenticationRepository) {
        return authenticationRepository.getById(authId);
    }

}
