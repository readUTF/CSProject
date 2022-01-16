package com.readutf.csprojectapi.profile;

import com.readutf.csprojectapi.authentication.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;


public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Override @Nullable
    Profile getById(Long aLong);
    boolean existsProfileByUsername(String username);
}
