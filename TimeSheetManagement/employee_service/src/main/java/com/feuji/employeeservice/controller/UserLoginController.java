package com.feuji.employeeservice.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.employeeservice.dto.AuthRequest;
import com.feuji.employeeservice.dto.JwtResponse;
import com.feuji.employeeservice.dto.RefreshTokenRequest;
import com.feuji.employeeservice.entity.RefreshToken;
import com.feuji.employeeservice.entity.UserLoginEntity;
import com.feuji.employeeservice.repository.UserLoginRepo;
import com.feuji.employeeservice.service.JwtService;
import com.feuji.employeeservice.service.RefreshTokenService;
import com.feuji.employeeservice.service.UserLoginService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserLoginController {

	@Autowired
	private UserLoginService userLoginService;

	@Autowired
	private UserLoginRepo loginRepo;
	@Autowired
	private JwtService jwtService;

	@Autowired
	private RefreshTokenService refreshTokenService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public JwtResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {

			UserLoginEntity user = loginRepo.findByUserEmail(authRequest.getEmail()).get();

			RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getEmail());
			return JwtResponse.builder().accessToken(jwtService.generateToken(authRequest.getEmail()))
					.token(refreshToken.getToken()).userEntity(user).build();
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}
	}

	@PostMapping("/refreshToken")
	public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		return refreshTokenService.findByToken(refreshTokenRequest.getToken())
				.map(refreshTokenService::verifyExpiration).map(RefreshToken::getUserLoginEntity).map(userEntity -> {
					String accessToken = jwtService.generateToken(userEntity.getUserName());
					return JwtResponse.builder().accessToken(accessToken).token(refreshTokenRequest.getToken()).build();
				}).orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
	}

	@PutMapping("/forgot-password")
	public ResponseEntity<Map<String, Object>> forgotPassword(@RequestBody Map<String, String> request) {
		try {
			String userEmail = request.get("userEmail");
			String userPassword = request.get("userPassword");

			if (userEmail == null || userPassword == null) {
				return ResponseEntity.badRequest()
						.body(createResponse(false, "Email and newPassword parameters are required"));
			}

			Optional<UserLoginEntity> result = userLoginService.updateUserPassword(userEmail, userPassword);
			if (result != null) {
				return ResponseEntity.ok().body(createResponse(true, "Password updated successfully!"));
			} else {
				return ((BodyBuilder) ResponseEntity.notFound()).body(createResponse(false, "Email not found!"));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(createResponse(false, "An error occurred while updating the password!"));
		}
	}

	private Map<String, Object> createResponse(boolean success, String message) {
		Map<String, Object> response = new HashMap<>();
		response.put("success", success);
		response.put("message", message);
		return response;
	}

	@PostMapping("/check-email")
	public ResponseEntity<?> checkEmailExist(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		boolean exists = userLoginService.checkEmailExist(email);
		return ResponseEntity.ok(exists);
	}

	@PostMapping("/generate-otp")
	public ResponseEntity<?> generateOTP(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		if (email != null && !email.isEmpty()) { // Check if email is present and not empty
			String generatedOtp = userLoginService.generateOtp(email); // Generate OTP for the provided email

			if (generatedOtp != null && !generatedOtp.isEmpty()) {
				boolean success = userLoginService.sendOtpEmail(email, generatedOtp); // Send OTP email

				if (success) {
					return ResponseEntity.ok(generatedOtp); // Return generated OTP in response
				} else {
					return ResponseEntity.badRequest().body("Failed to send OTP email");
				}
			} else {
				return ResponseEntity.badRequest().body("Failed to generate OTP");
			}
		} else {
			return ResponseEntity.badRequest().body("Email is required");
		}
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<?> verifyOTP(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		String otp = request.get("otp");
		boolean success = userLoginService.verifyOtp(email, otp);
		return ResponseEntity.ok(success);
	}

	@PostMapping("/update-password")
	public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		String newPassword = request.get("newPassword");
		userLoginService.updateUserPassword(email, newPassword);
		return ResponseEntity.ok().build();
	}

}