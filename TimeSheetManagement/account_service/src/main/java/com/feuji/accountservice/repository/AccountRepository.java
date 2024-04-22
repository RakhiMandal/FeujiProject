package com.feuji.accountservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.feuji.accountservice.dto.AccountDTO;
import com.feuji.accountservice.dto.UpdateAccountDto;
import com.feuji.accountservice.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
	AccountEntity findByAccountName( String accountName);
	AccountEntity findByuuId( String uuId);
	

	@Query("SELECT NEW com.feuji.accountservice.dto.AccountDTO(a.uuId,a.accountId,a.accountName,e_owener.firstName AS owenername,e_relationship.firstName AS relationshipManagername ,e_businessDevelopment.firstName AS businessDevelopmentManagername ,a_parentname.accountName,a.plannedStartDate ,a.plannedEndDate,a.actualStartDate,a.actualEndDate, "
			+"crd_accountBuId.referenceDetailValue,crd_status.referenceDetailValue )"
			+ "FROM AccountEntity a " 
			+"JOIN AccountEntity a_parentname ON a_parentname.accountId=a.parentAccountId "
			+ "JOIN EmployeeEntity e_owener ON a.ownerId = e_owener.employeeId "
			+ " JOIN EmployeeEntity e_relationship ON a.relationshipManagerId=e_relationship.employeeId "
			+ " JOIN EmployeeEntity e_businessDevelopment ON a.businessDevelopmentManagerId=e_businessDevelopment.employeeId "
	        + " JOIN CommonReferenceDetailsEntity crd_accountBuId ON crd_accountBuId.referenceDetailId=a.accountBuId "
			+" JOIN CommonReferenceDetailsEntity crd_status ON crd_status.referenceDetailId=a.accountStatus "
	         +"WHERE a.isDeleted=false ")
	List<AccountDTO> accountDto();

	@Query("SELECT NEW com.feuji.accountservice.dto.UpdateAccountDto(a.accountId,a.accountName,e_owener.employeeId,e_owener.firstName, "
			+" e_relationship.employeeId  ,e_relationship.firstName,e_businessDevelopment.employeeId,e_businessDevelopment.firstName ,"
			+" a_parentname.parentAccountId, a_parentname.accountName,crd_accountBuId.referenceDetailId,crd_accountBuId.referenceDetailValue, "
			+" a.plannedStartDate ,a.plannedEndDate,a.actualStartDate,a.actualEndDate,a.address,a.city,a.state,a.zipcode,a.country, "
			+" crd_status.referenceDetailId,crd_status.referenceDetailValue ,a.comments,a.uuId,a.isDeleted)"
			+ "FROM AccountEntity a " 
					+"JOIN AccountEntity a_parentname ON a_parentname.accountId=a.parentAccountId "
					+ "JOIN EmployeeEntity e_owener ON a.ownerId = e_owener.employeeId "
					+ " JOIN EmployeeEntity e_relationship ON a.relationshipManagerId=e_relationship.employeeId "
					+ " JOIN EmployeeEntity e_businessDevelopment ON a.businessDevelopmentManagerId=e_businessDevelopment.employeeId "
			        + " JOIN CommonReferenceDetailsEntity crd_accountBuId ON crd_accountBuId.referenceDetailId=a.accountBuId "
					+" JOIN CommonReferenceDetailsEntity crd_status ON crd_status.referenceDetailId=a.accountStatus "
					+"WHERE a.uuId=:uuId")
			List< UpdateAccountDto> fetchByUuID(@Param("uuId") String uuId);

}
