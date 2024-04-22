package com.feuji.accountprojectservice.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.accountprojectservice.entity.AccountProjectTaskType;
import com.feuji.accountprojectservice.repository.AccountProjectTaskTypeRepo;
import com.feuji.accountprojectservice.service.AccountProjectTaskTypeService;

@Service
public class AccountProjectTaskTypeServiceImpl implements AccountProjectTaskTypeService{

	@Autowired
	private AccountProjectTaskTypeRepo accountProjectTaskTypeRepo;
	
	@Override
	public AccountProjectTaskType getByAccountProjectTaskTypeId(Integer id) {
		 return accountProjectTaskTypeRepo.findByTaskTypeId(id);
		
	}

}
