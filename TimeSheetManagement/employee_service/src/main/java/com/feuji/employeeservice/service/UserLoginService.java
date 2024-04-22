package com.feuji.employeeservice.service;

import java.util.Optional;

import com.feuji.employeeservice.entity.UserLoginEntity;

public interface UserLoginService {
	//public boolean isEmailUnique(String email);
	public UserLoginEntity loginUser(String userEmail, String userPassword);


	public Optional<UserLoginEntity> getUserByEmail(String email);

	Optional<UserLoginEntity> updateUserPassword(String email, String password);

	boolean checkEmailExist(String email);

	boolean sendOtpEmail(String toEmail, String otp);

	boolean verifyOtp(String email, String enteredOtp);
	
	 void saveOtp(String email, String otp) ;

	public String generateOtp(String email);


}