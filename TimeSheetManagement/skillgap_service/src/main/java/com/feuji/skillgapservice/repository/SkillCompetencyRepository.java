package com.feuji.skillgapservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.feuji.skillgapservice.dto.EmployeeSkillDetailsDto;
import com.feuji.skillgapservice.dto.SkillsBean;
import com.feuji.skillgapservice.entity.SkillCompetencyEntity;

import jakarta.transaction.Transactional;

@Transactional
public interface SkillCompetencyRepository extends JpaRepository<SkillCompetencyEntity, Long> {

	Optional<SkillCompetencyEntity> findByUuid(String uuid);

	SkillCompetencyEntity findBySkillId(int skillId);

	@Query(value = "select * from skill_competency where skill_id in (select skill_id "
			+ "from skills where  techinical_category_id=:technicalCatId)", nativeQuery = true)
	List<SkillCompetencyEntity> getByTechId(int technicalCatId);

	@Query("select new com.feuji.skillgapservice.dto.EmployeeSkillDetailsDto(e.firstName, e.middleName, e.lastName, "
			+ " e.employeeId,e.employeeCode, e.designation, e.email,s.skillId, s.skillName , cre.referenceDetailValue,"
			+ " cra.referenceDetailValue,COALESCE(cre.referenceDetailId-cra.referenceDetailId,0) as competencyLevelId , crs.referenceDetailValue )"

			+ " FROM EmployeeEntity e "

			+ "INNER JOIN EmployeeSkillEntity es ON e.employeeId = es.employeeId " +

			"INNER JOIN SkillEntity s ON s.skillId = es.skillId "
			+ "INNER JOIN SkillCompetencyEntity sc ON s.skillId = sc.skillId "
			+ "INNER JOIN CommonReferenceDetailsEntity cre ON sc.competencyLevelId = cre.referenceDetailId "
			+ "INNER JOIN CommonReferenceDetailsEntity cra ON es.competencyLevelId = cra.referenceDetailId "
			+ "INNER JOIN CommonReferenceDetailsEntity crs ON sc.skillTypeId = crs.referenceDetailId "
			+ "WHERE s.skillId IN :skillId " + " ORDER BY ?#{#pageble}")
	Page<EmployeeSkillDetailsDto> findAllEmployeeSkillsBySkillIds(@Param("skillId") int[] skillId, Pageable pageble);

	@Query("""
				select new com.feuji.skillgapservice.dto.SkillsBean(
				s.skillId, s.skillName,
				cra.referenceDetailValue,
				cre.referenceDetailValue,

				es.competencyLevelId  as actualcompetencyLevelId,
				sc.competencyLevelId as expectedcompetencyLevelId,
				crs.referenceDetailValue
				)
				from SkillEntity s
				inner join SkillCompetencyEntity sc  on s.skillId=sc.skillId
			inner join EmployeeSkillEntity es on sc.skillId=es.skillId
			inner join EmployeeEntity e on es.employeeId=e.employeeId
			INNER JOIN CommonReferenceDetailsEntity cre ON sc.competencyLevelId = cre.referenceDetailId
			INNER JOIN CommonReferenceDetailsEntity cra ON es.competencyLevelId = cra.referenceDetailId
			INNER JOIN CommonReferenceDetailsEntity crs ON sc.skillTypeId = crs.referenceDetailId
			where e.employeeId = :employeeId and s.skillId in :skillId
			and es.isDeleted=0 and sc.isDeleted=0 and s.isDeleted=0 and sc.roleName =:roleName
			""")
	List<SkillsBean> findSkillsByEmployeeId(Integer employeeId, int[] skillId, String roleName);

}
