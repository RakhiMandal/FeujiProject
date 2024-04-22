package com.feuji.accountprojectservice.service;

import java.util.List;

import com.feuji.accountprojectservice.dto.AccountProjectResourceMappingDto;

public interface AccountProjectResourceMappingService {
	List<AccountProjectResourceMappingDto> findAccountNameByUserEmpId(Integer userEmpId );
}