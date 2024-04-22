package com.feuji.accountprojectservice.serviceimpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.accountprojectservice.dto.AccountProjectResourceMappingDto;
import com.feuji.accountprojectservice.repository.AccountProjectResourceMappingRepository;
import com.feuji.accountprojectservice.service.AccountProjectResourceMappingService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class AccountProjectResourceMappingServiceImpl implements AccountProjectResourceMappingService {
	
	@Autowired
	private AccountProjectResourceMappingRepository accountProjectResourceMappingRepository;
	private static Logger log = LoggerFactory.getLogger(AccountProjectResourceMappingServiceImpl.class);
	
	@Override
	public List<AccountProjectResourceMappingDto> findAccountNameByUserEmpId(Integer userEmpId) {
		   
		log.info("Employee ID   : "+ userEmpId );
		
		List<AccountProjectResourceMappingDto> accountProjectResourceMappingDtos=accountProjectResourceMappingRepository.findAccountNameByUserEmpId(userEmpId);
		return accountProjectResourceMappingDtos;
	}

}