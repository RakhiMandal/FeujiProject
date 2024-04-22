package com.feuji.employeeservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.feuji.employeeservice.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByToken(String token);

    @Query(value ="Select * from refresh_token where user_id=?1 ", nativeQuery = true )
	RefreshToken findByUserId(Integer id);
}