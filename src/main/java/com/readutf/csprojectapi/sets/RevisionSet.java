package com.readutf.csprojectapi.sets;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity @Getter @NoArgsConstructor
public class RevisionSet {

    @Id @GeneratedValue private long id;
    String name;
    long ownerId;

    public RevisionSet(String name, long ownerId) {
        this.name = name;
        this.ownerId = ownerId;
    }
}
