package com.skillset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.skillset.dto.GapDto;
import com.skillset.dto.SkillGapDto;
import com.skillset.entity.EmployeeEntity;

import jakarta.transaction.Transactional;

@Transactional
@EnableJpaRepositories
public interface SkillSetRepository extends JpaRepository<EmployeeEntity, Integer> {

	@Query("SELECT new com.skillset.dto.GapDto(e.firstName, e.middleName, e.lastName, e.employeeId, " +
	        "e.email, e.designation, s.skillName, s.description, sc.competencyLevelId as exCompetencyLevelId, " +
	        "es.competencyLevelId as acCompetencyLevelId, "
	        + "sr.referenceDetailValue as exReferenceDetailsValues, "
	        + " er.referenceDetailValue as acReferenceDetailsValues) " +
	        "FROM SkillEntity s " +
	        "JOIN SkillCompetencyEntity sc ON s.skillId = sc.skillId " +
	        "JOIN EmployeeSkillEntity es ON sc.skillId = es.skillId " +
	        "JOIN EmployeeEntity e ON e.employeeId = es.employeeId " +
	        "JOIN CommonReferenceDetailsEntity sr ON sc.competencyLevelId = sr.referenceDetailId " +
	        "JOIN CommonReferenceDetailsEntity er ON es.competencyLevelId = er.referenceDetailId " +
	        "WHERE e.email = :email AND es.isDeleted = 0 AND s.isDeleted=0 AND s.status=1 AND s.skillCategoryId = :skillCategoryId")
	List<GapDto> findEmployeeDetailsByEmail(@Param("email") String email, @Param("skillCategoryId") int skillCategoryId);

	
	@Query("SELECT new com.skillset.dto.SkillGapDto(e.firstName, e.middleName, e.lastName, e.employeeId, " +
	        "e.email, e.designation, s.skillName, s.description, sc.competencyLevelId as exCompetencyLevelId, " +
	        "es.competencyLevelId as acCompetencyLevelId, "+
	        "sr.referenceDetailValue as exReferenceDetailsValues, "+
	        "er.referenceDetailValue as acReferenceDetailsValues, " +
	        "cre.referenceDetailValue as skillCategoryName, "+
	        "crd.referenceDetailValue as subSkillCategoryName) "+
	        "FROM SkillEntity s " +
	        "JOIN SkillCompetencyEntity sc ON s.skillId = sc.skillId " +
	        "JOIN EmployeeSkillEntity es ON sc.skillId = es.skillId " +
	        "JOIN EmployeeEntity e ON e.employeeId = es.employeeId " +
	        "JOIN CommonReferenceDetailsEntity sr ON sc.competencyLevelId = sr.referenceDetailId " +
	        "JOIN CommonReferenceDetailsEntity er ON es.competencyLevelId = er.referenceDetailId " +
	        "JOIN CommonReferenceDetailsEntity cre ON s.skillCategoryId = cre.referenceDetailId "+
	        "JOIN CommonReferenceDetailsEntity crd ON s.techinicalCategoryId=crd.referenceDetailId "+
	        "WHERE e.email = :email AND es.isDeleted = 0")
	public List<SkillGapDto> findEmployeeSkills(@Param("email") String email);

	@Query(value="select referenceTypeId from CommonReferenceTypeEntity cr where "
			+ " cr.referenceTypeName =:referenceTypeName ")
	public Integer getTypeIdByDetailsId(String referenceTypeName);

	@Query(value="select referenceDetailValue from "
			+ " CommonReferenceDetailsEntity crd where "
			+ "referenceDetailId=:referenceDetailId")
	public String repogetTypeNameByCategoryId(Integer referenceDetailId);
	
	

}
