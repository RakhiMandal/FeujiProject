package com.feuji.accountprojectservice.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.feuji.accountprojectservice.dto.AccountProjectResourceMappingDto;
import com.feuji.accountprojectservice.entity.AccountProjectResourceMappingEntity;



public interface AccountProjectResourceMappingRepository extends JpaRepository<AccountProjectResourceMappingEntity,Integer>{

       @Query("SELECT NEW com.feuji.accountprojectservice.dto.AccountProjectResourceMappingDto(arm.mappingId,acc.accountId,acc.accountName,ud.userEmpId)"+
       " FROM AccountProjectResourceMappingEntity arm"+
    	 " JOIN AccountEntity acc ON acc.accountId=arm.accountId"+
       " JOIN UserLoginEntity ud ON ud.userEmpId=arm.employeeId"+
    	 " WHERE ud.userEmpId=:userEmpId"
    		   )
	List<AccountProjectResourceMappingDto> findAccountNameByUserEmpId(@Param("userEmpId") Integer userEmpId );
}