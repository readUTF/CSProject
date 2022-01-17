package com.readutf.csprojectapi.sets;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RevisionSet {

    @Id @GeneratedValue private long id;
    String name;
    long ownerId;



}
