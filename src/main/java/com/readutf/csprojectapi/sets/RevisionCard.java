package com.readutf.csprojectapi.sets;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class RevisionCard {

    @Id @GeneratedValue long id;
    long setId;

}
