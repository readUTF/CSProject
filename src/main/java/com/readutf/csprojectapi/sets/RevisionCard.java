package com.readutf.csprojectapi.sets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor @Getter @Setter @Entity
public class RevisionCard {

    @Id @GeneratedValue long id;
    long setId;

    String keyWord;
    String description;
    int fails;
    int successes;

    public RevisionCard(long setId, String keyWord, String description) {
        this.setId = setId;
        this.keyWord = keyWord;
        this.description = description;
        this.fails = 0;
        this.successes = 0;

    }

    public RevisionCard copy() {
        return new RevisionCard(setId, keyWord, description);
    }

}
