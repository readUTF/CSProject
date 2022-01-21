package com.readutf.csprojectapi.sets;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RevisionCardRepository extends JpaRepository<RevisionCard, Long> {

    Optional<List<RevisionCard>> findAllBySetId(long setId);
    boolean existsByIdAndKeyWord(long id, String keyword);

}
