package com.feuji.employeeservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feuji.employeeservice.entity.UserLoginEntity;

public interface UserLoginRepo extends JpaRepository<UserLoginEntity, Integer>{
	
	Optional<UserLoginEntity> findByUserEmail(String userEmail);
//	Optional<UserLoginEntity> findByUserEmail(String userEmail);

}