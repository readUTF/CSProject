package com.readutf.csprojectapi.profile;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity @Getter @Setter @NoArgsConstructor @JsonSerialize
public class Profile {

    @Id @GeneratedValue
    private Long id;
    private Long authId;
    private String username;
    LocalDateTime localDateTime;
    int completedCards;
    long revisionTime;

    public Profile(Long authId, String username, LocalDateTime localDateTime, int completedCards, long revisionTime) {
        this.authId = authId;
        this.username = username;
        this.localDateTime = localDateTime;
        this.completedCards = completedCards;
        this.revisionTime = revisionTime;
    }
}
