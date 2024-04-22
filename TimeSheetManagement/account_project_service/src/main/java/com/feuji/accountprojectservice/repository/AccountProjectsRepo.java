package com.feuji.accountprojectservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.feuji.accountprojectservice.dto.AccountDto;
import com.feuji.accountprojectservice.dto.UpdateAccountProjectDto;
import com.feuji.accountprojectservice.entity.AccountProjectsEntity;

public interface AccountProjectsRepo extends JpaRepository<AccountProjectsEntity,Integer>{
	
	Optional<AccountProjectsEntity> findByUuid(String uuid);
	
	@Query("SELECT NEW com.feuji.accountprojectservice.dto.AccountDto(" +
	         " ap.accountProjectId ,ap.projectAId, " +
	           "ap.projectName, " +
	           "a.accountName, " +
	           "e.firstName, " +
	           "rd.referenceDetailValue,ap.noOfBillingHours,ap.uuid,ap.plannedStartDate,ap.plannedEndDate,ap.actualStartDate,ap.actualEndDate) " +
	           "FROM AccountProjectsEntity ap " +
	           "JOIN AccountEntity a ON ap.accountId = a.accountId " +
	           "JOIN EmployeeEntity e ON ap.projectManagerId = e.employeeId " +
	           "JOIN CommonReferenceDetailsEntity rd ON ap.priority = rd.referenceDetailId  " + 
	           "JOIN CommonReferenceTypeEntity rt ON rd.referenceTypeId = rt.referenceTypeId "+
	           "WHERE ap.isDeleted=false "
	           )
	List<AccountDto> accountProjectDto();
	
	
	
	@Query("SELECT NEW com.feuji.accountprojectservice.dto.UpdateAccountProjectDto(" +
	         " ap.accountProjectId ,ap.projectAId, " +
	           "ap.projectName, " +
	           "a.accountId, " +
	           "a.accountName, " +
	           "e.employeeId, " +
	           "e.firstName, " +
	           "rd_priority.referenceDetailId, "+
	           "rd_priority.referenceDetailValue,"+
	           " rd_status.referenceDetailId, "+
	           " rd_status.referenceDetailValue,"
	           + "ap.noOfBillingHours,ap.plannedStartDate,ap.plannedEndDate,ap.actualStartDate,ap.actualEndDate,ap.uuid,ap.isDeleted) " +
	           "FROM AccountProjectsEntity ap " +
	           "JOIN AccountEntity a ON ap.accountId = a.accountId " +
	           "JOIN EmployeeEntity e ON ap.projectManagerId = e.employeeId " +
	           "JOIN CommonReferenceDetailsEntity rd_priority ON ap.priority = rd_priority.referenceDetailId  " + 
	           "JOIN CommonReferenceDetailsEntity  rd_status ON ap.projectStatus = rd_status.referenceDetailId  "+
	           "WHERE ap.uuid=:uuid"
			)
	List<UpdateAccountProjectDto> accountProjectUpdate(@Param("uuid") String uuid);
	
//	@Query(value="select account_project_id from account_projects ap join account a  on ap.account_id=a.account_id	where a.account_id=2",nativeQuery=true)
//	List<AccountProjectsEntity> getAccountProjectIdByAccountId( Integer accountId);
	
//	@Query(value="select account_project_id from account_projects ap where account_id=(select account_id from account where account_id=?",nativeQuery=true)
//	List<Integer> getAccountProjectIdByAccountId(Integer referenceTypeId);
	
	@Query(value="select account_project_id from account_projects ap where account_id=(select account_id from account where account_id=:accountId)", nativeQuery=true)
	List<Integer> getAccountProjectIdByAccountId(@Param("accountId") Integer accountId);

	
	@Modifying
	@Query(value="update account_projects set is_deleted=1 where account_project_id=:accountProjectId", nativeQuery=true)
	void updateIsDeleted(Integer accountProjectId);


    

}
