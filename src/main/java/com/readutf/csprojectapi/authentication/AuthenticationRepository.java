package com.readutf.csprojectapi.authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {

    Optional<Authentication> getAuthenticationByEmail(String email);
    boolean existsAuthenticationByEmail(String email);

}
