package com.feuji.accountservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feuji.accountservice.entity.UserLoginEntity;


public interface UserLoginRepo extends JpaRepository<UserLoginEntity, Integer>{
	
	Optional<UserLoginEntity> findByUserEmail(String userEmail);

}