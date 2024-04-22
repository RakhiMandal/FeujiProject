package com.feuji.employeeservice.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.feuji.employeeservice.entity.OTPEntity;

import jakarta.transaction.Transactional;

public interface OtpRepository extends JpaRepository<OTPEntity, Integer> {
	
	@Modifying
	@Transactional
	@Query("DELETE FROM OTPEntity o WHERE TIMESTAMPDIFF(MINUTE, o.expirationTime, CURRENT_TIMESTAMP()) <> 2 AND o.id > 0")
	void deleteExpiredOtps();

	Optional<OTPEntity> findByEmail(String email);
	

}