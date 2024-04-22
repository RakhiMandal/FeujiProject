package com.feuji.employeeservice.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.feuji.employeeservice.entity.UserLoginEntity;

import com.feuji.employeeservice.repository.UserLoginRepo;

@Component
public class UserInfoUserDetailsService implements UserDetailsService{

	@Autowired
	private UserLoginRepo repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UserLoginEntity> userlogin = repository.findByUserEmail(email);
		return userlogin.map(UserInfoUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("user not found " + email));

	}
	
//	Optional<UserInfo> userInfo = repository.findByEmail(email);
//	return userInfo.map(UserInfoUserDetails::new)
//			.orElseThrow(() -> new UsernameNotFoundException("user not found " + email));

}
