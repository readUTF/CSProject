package com.readutf.csprojectapi.sets;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RevisionSetRepository extends JpaRepository<RevisionSet, Long> {

    boolean existsByNameAndOwnerId(String name, long ownerId);
    Optional<RevisionSet> getByIdAndOwnerId(long id, long ownerId);

}
