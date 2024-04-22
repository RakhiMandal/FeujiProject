package com.feuji.employeeservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.feuji.employeeservice.dto.AddEmployee;
import com.feuji.employeeservice.dto.EmployeeDisplayDto;
import com.feuji.employeeservice.dto.EmployeeDto;
import com.feuji.employeeservice.dto.SaveEmployeeDto;
import com.feuji.employeeservice.dto.UpadteEmployeeDto;
import com.feuji.employeeservice.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer>{

	
	boolean existsByEmployeeCode(String empCode);

	@Query("SELECT e.reportingManagerId FROM EmployeeEntity e WHERE e.employeeId = :employeeId")
	public Integer getReportingMngIdByEmpId(@Param("employeeId") Integer employeeId);
	
//	@Query("SELECT new com.feuji.employeeservice.dto.EmployeeDto(e.firstName)FROM EmployeeEntity left joine WHERE e.designation LIKE '%manager%'")
//    List<EmployeeDto> findDesignationsContainingManager(@Param("designation") String designation);
	
	@Query("SELECT new com.feuji.employeeservice.dto.AddEmployee(e.firstName, e.designation,e.employeeId) FROM EmployeeEntity e WHERE e.designation LIKE '%manager%'")
	List<AddEmployee> findDesignationsContainingManager();


	@Query("SELECT e FROM EmployeeEntity e WHERE e.uuid = :uuid")
	EmployeeEntity findByUuid(@Param("uuid") String uuid);
	@Query("SELECT new com.feuji.employeeservice.dto.SaveEmployeeDto(crde.referenceDetailId, crde.referenceDetailValue,crte.referenceTypeId) "
			+ "FROM CommonReferenceDetailsEntity crde "
			+ "JOIN CommonReferenceTypeEntity crte ON crde.referenceType.referenceTypeId = crte.referenceTypeId "
			+ "WHERE crde.referenceType.referenceTypeId = :referenceTypeId")
    List<SaveEmployeeDto> getByReferenceTypeId(Integer referenceTypeId);
	@Query("SELECT new com.feuji.employeeservice.dto.EmployeeDto(ule.userEmpId, empe.employeeCode, empe.firstName, empe.middleName, empe.lastName, empe.image, empe.designation, empe.email, empe.gender, empe.dateOfJoining,empe.reportingManagerId, empe.employmentType, empe.status, empe.deliveryUnitId, empe.businessUnitId) "
	        + "FROM UserLoginEntity ule left join EmployeeEntity empe on( ule.userEmpId=empe.employeeId)"
	        + "WHERE ule.userEmpId = :userEmpId")
	List<EmployeeDto> getEmployeeDetailsByUserEmpId(@Param("userEmpId") Integer userEmpId);
	

	
	

	
	
	  List<EmployeeEntity> findByFirstNameContainingIgnoreCase(String firstName);
	  
	  @Query("SELECT new com.feuji.employeeservice.dto.EmployeeDisplayDto(" +
		        "emp.employeeId, " +
		        "emp.employeeCode, " +
		        "emp.firstName, " +
		        "emp.middleName, " +
		        "emp.lastName, " +
		        "emp.image, " +
		        "emp.designation, " +
		        "emp.email, " +
		        "crd_gender.referenceDetailValue, " +
		        "emp.dateOfJoining, " +
		        "e_manager.firstName, " +
		        "e_manager.lastName, "+
		        "e_manager.middleName , "+
		        "crd_status.referenceDetailValue, " +
		        "emp.uuid) " +
		        "FROM EmployeeEntity emp " +
		        "JOIN EmployeeEntity e_manager ON e_manager.employeeId = emp.reportingManagerId " +
		        "JOIN CommonReferenceDetailsEntity crd_gender ON crd_gender.referenceDetailId = emp.gender " +
		        "JOIN CommonReferenceDetailsEntity crd_status ON crd_status.referenceDetailId = emp.status " +
		        "WHERE emp.isDeleted=false ")
		List<EmployeeDisplayDto> getEmployeeDetails();
	  
		@Query("SELECT new com.feuji.employeeservice.dto.UpadteEmployeeDto(" +
		        "emp.employeeId, " +
		        "emp.employeeCode, " +
		        "emp.firstName, " +
		        "emp.middleName, " +
		        "emp.lastName, " +
		        "emp.image, " +
		        "emp.designation, " +
		        "emp.email, " +
		        "crd_gender.referenceDetailId, " +
		        "crd_gender.referenceDetailValue, " +
		        "emp.dateOfJoining, " +
		        "crd_emptype.referenceDetailId, " +
		        "crd_emptype.referenceDetailValue, " +
		        "e_manager.firstName, " +
		        "e_manager.lastName, "+
		        "e_manager.middleName , "+
		        "e_manager.employeeId, "+
		        "crd_du.referenceDetailId, " +
		        "crd_du.referenceDetailValue, " +
		        "crd_bu.referenceDetailId, " +
		        "crd_bu.referenceDetailValue, " +
		        "crd_status.referenceDetailId, " +
		        "crd_status.referenceDetailValue, " +
		        "emp.uuid,emp.isDeleted ) " +
		        "FROM EmployeeEntity emp " +
		        "JOIN EmployeeEntity e_manager ON e_manager.employeeId = emp.reportingManagerId " +
		        "JOIN CommonReferenceDetailsEntity crd_gender ON crd_gender.referenceDetailId = emp.gender " +
		        "JOIN CommonReferenceDetailsEntity crd_status ON crd_status.referenceDetailId = emp.status " +
		        "JOIN CommonReferenceDetailsEntity crd_emptype ON crd_emptype.referenceDetailId = emp.employmentType " +
		        "JOIN CommonReferenceDetailsEntity crd_bu ON crd_bu.referenceDetailId = emp.businessUnitId " +
		        "JOIN CommonReferenceDetailsEntity crd_du ON crd_du.referenceDetailId = emp.deliveryUnitId " +
		        "Where emp.uuid=:uuid")
		List<UpadteEmployeeDto> getEmployeeDetailByUUiD(@Param("uuid") String uuid);
		
		Optional<EmployeeEntity> findByEmail(String email);
}


	
	
