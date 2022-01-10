package com.readutf.csprojectapi.authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {




}
