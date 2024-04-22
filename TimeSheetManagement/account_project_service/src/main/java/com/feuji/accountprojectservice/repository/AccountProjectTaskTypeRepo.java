package com.feuji.accountprojectservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feuji.accountprojectservice.entity.AccountProjectTaskType;

public interface AccountProjectTaskTypeRepo extends JpaRepository<AccountProjectTaskType, Integer>{
	
	public AccountProjectTaskType findByTaskTypeId(Integer id);	

}
