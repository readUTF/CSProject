package com.readutf.csprojectapi.tokens;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Token getByToken(UUID token);

}
