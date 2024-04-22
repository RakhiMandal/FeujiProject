package com.feuji.employeeservice.serviceimpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.feuji.employeeservice.entity.OTPEntity;
import com.feuji.employeeservice.entity.UserLoginEntity;
import com.feuji.employeeservice.exception.InvalidOtpException;
import com.feuji.employeeservice.exception.OTPNotFoundException;
import com.feuji.employeeservice.repository.OtpRepository;
import com.feuji.employeeservice.repository.UserLoginRepo;
import com.feuji.employeeservice.service.UserLoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserLoginRepo userLoginRepo;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public UserLoginEntity loginUser(String userEmail, String userPassword) {
        Optional<UserLoginEntity> optionalUser = userLoginRepo.findByUserEmail(userEmail);
        if (optionalUser.isPresent() && optionalUser.get().getUserPassword().equals(userPassword)) {
            return optionalUser.get(); 
        }
        return null;
    }

    @Override
    public Optional<UserLoginEntity> getUserByEmail(String email) {
        return userLoginRepo.findByUserEmail(email);
    }

    @Override
    public Optional<UserLoginEntity> updateUserPassword(String email, String newPassword) {
        Optional<UserLoginEntity> optionalUser = userLoginRepo.findByUserEmail(email);
        if (optionalUser.isPresent()) {
            UserLoginEntity user = optionalUser.get();
            user.setUserPassword(newPassword);
            userLoginRepo.save(user);
            return Optional.of(user); // Return the updated UserLoginEntity
        }
        return Optional.empty();
    }

    @Override
    public boolean checkEmailExist(String email) {
        return userLoginRepo.findByUserEmail(email).isPresent();
    }

    
    @Override
    public String generateOtp(String email) {
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000);
        String generatedOtp = String.valueOf(otpValue);
        log.info("Generated OTP for user: {}", generatedOtp);
        
        saveOtp(email, generatedOtp); 
        
        return generatedOtp;
    }


    @Override
	public void saveOtp(String email, String otp) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = now.plusMinutes(30); 
        
        OTPEntity otpEntity = new OTPEntity();
        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        otpEntity.setExpirationTime(Timestamp.valueOf(expirationTime));
        
        otpRepository.save(otpEntity);
    }

    
    @Override
    public boolean sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your OTP");
        message.setText("Your OTP is: " + otp);

        javaMailSender.send(message);
		return true;
    }

    @Override
    public boolean verifyOtp(String email, String enteredOtp) {
        Optional<OTPEntity> otpEntityOptional = otpRepository.findByEmail(email);
        
        if (otpEntityOptional.isPresent()) {
            OTPEntity otpEntity = otpEntityOptional.get();
            
            if (!enteredOtp.equals(otpEntity.getOtp())) {
                throw new InvalidOtpException("Entered incorrect OTP");
            }
            
            Timestamp expirationTime = otpEntity.getExpirationTime();
            LocalDateTime expirationLocalDateTime = expirationTime.toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            
            if (expirationLocalDateTime.isBefore(LocalDateTime.now())) {
                throw new InvalidOtpException("OTP has expired");
            }
            
            return true;
        } else {
            throw new OTPNotFoundException("OTP not found for email: " + email);
        }
    }

	

    

}