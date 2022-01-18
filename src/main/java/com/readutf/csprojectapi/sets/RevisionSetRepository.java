package com.readutf.csprojectapi.sets;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RevisionSetRepository extends JpaRepository<RevisionSet, Long> {

    boolean existsByNameAndOwnerId(String name, long ownerId);

}
