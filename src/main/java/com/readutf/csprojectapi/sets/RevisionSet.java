package com.readutf.csprojectapi.sets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RevisionSet {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    String name;
    long ownerId;
    boolean privateSet;

    public RevisionSet(String name, long ownerId) {
        this.name = name;
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "RevisionSet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ownerId=" + ownerId +
                ", privateSet=" + privateSet +
//                ", revisionCards=" + revisionCards +
                '}';
    }
}
