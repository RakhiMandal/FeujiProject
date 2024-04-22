package com.feuji.employeeservice.service;

import java.time.Instant;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.employeeservice.entity.RefreshToken;
import com.feuji.employeeservice.entity.UserLoginEntity;
import com.feuji.employeeservice.repository.RefreshTokenRepository;
import com.feuji.employeeservice.repository.UserLoginRepo;

@Service
public class RefreshTokenService {

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	@Autowired
	private UserLoginRepo userLoginRepo;

	public RefreshToken createRefreshToken(String email) {
		Optional<UserLoginEntity> user = userLoginRepo.findByUserEmail(email);
		Integer id = user.get().getUserId();
		System.out.println(id+".......");
		RefreshToken refreshToken = refreshTokenRepository.findByUserId(id);
		System.out.println(refreshToken);
		if (refreshToken != null) {
			return refreshToken;
		} else {
			RefreshToken refreshToken1 = RefreshToken.builder()
					.userLoginEntity(userLoginRepo.findByUserEmail(email).get()).token(UUID.randomUUID().toString())
					.expiryDate(Instant.now().plusSeconds(604800)) // 7 days (7 * 24 * 60 * 60 seconds)
					.build();
			return refreshTokenRepository.save(refreshToken1);
		}
	}

	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new RuntimeException(
					token.getToken() + " Refresh token was expired. Please make a new signin request");
		}
		return token;
	}

}
