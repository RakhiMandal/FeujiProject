package com.feuji.skillgapservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.feuji.skillgapservice.dto.EmployeeEntityDto;
import com.feuji.skillgapservice.dto.TrainigRecommendedEmployeesDto;
import com.feuji.skillgapservice.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer>{

	Optional<EmployeeEntity> findByEmail(String email);
	@Query("select DISTINCT new com.feuji.skillgapservice.dto.EmployeeEntityDto(e.employeeId,e.employeeCode,e.firstName, e.middleName, e.lastName, " +
	        "  e.designation, e.email ) "	    
	        + " FROM EmployeeEntity e "
	        
	        +"INNER JOIN EmployeeSkillEntity es ON e.employeeId = es.employeeId " +
	        
	        "INNER JOIN SkillEntity s ON s.skillId = es.skillId " +
	        "INNER JOIN SkillCompetencyEntity sc ON s.skillId = sc.skillId " +
			"WHERE s.skillId IN :skillId "
			 )
	Page<EmployeeEntityDto> findEmployeesBySkillId(int[] skillId,Pageable pageable);
	
	@Query("select new com.feuji.skillgapservice.dto.TrainigRecommendedEmployeesDto(e.employeeId,e.employeeCode,e.firstName, e.middleName, e.lastName, " +
	        "  e.designation, e.email,s.skillName,cra.referenceDetailValue as actualCompetency, "
	        + "cre.referenceDetailValue as expectedCompetency,"
	        + "es.competencyLevelId  as actualcompetencyLevelId,"
	        + "sc.competencyLevelId as expectedcompetencyLevelId) "
	        + " FROM EmployeeEntity e "
	        +"INNER JOIN EmployeeSkillEntity es ON e.employeeId = es.employeeId " 
	        +"INNER JOIN SkillEntity s ON s.skillId = es.skillId " 
	        +"INNER JOIN SkillCompetencyEntity sc ON s.skillId = sc.skillId " 
	        + "INNER JOIN CommonReferenceDetailsEntity cre ON sc.competencyLevelId = cre.referenceDetailId "
			+ "INNER JOIN CommonReferenceDetailsEntity cra ON es.competencyLevelId = cra.referenceDetailId "
			+"WHERE s.skillId IN :skillIds and es.isDeleted=0"
			 )
	List<TrainigRecommendedEmployeesDto> getAllTrainingRecommendedEmp(int[] skillIds);

	
}
