package com.ECommerce.Repository;

import com.ECommerce.Entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepo extends JpaRepository<Token, Integer> {

    boolean existsByToken(String token);

    Token findByToken(String token);
}
