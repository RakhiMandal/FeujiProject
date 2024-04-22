package com.feuji.skillgapservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.feuji.skillgapservice.dto.SkillNamesDto;
import com.feuji.skillgapservice.entity.SkillEntity;

import jakarta.transaction.Transactional;

@Transactional
public interface SkillRepository extends JpaRepository<SkillEntity, Integer> {

	Optional<SkillEntity> findByUuid(String uuid);

	@Query(value = "select * from skills where techinical_category_id =:categoryId and is_deleted=0", nativeQuery = true)
	List<SkillEntity> findByTechinicalCategoryId(Long categoryId);

	@Query(value = "select * from skills where techinical_category_id =:categoryId", nativeQuery = true)
	List<SkillEntity> findDeletedSkillsByTechinicalCategoryId(Long categoryId);
	
	@Query("SELECT new com.feuji.skillgapservice.dto.SkillNamesDto(s.skillName, sc.skillTypeId, "
			+ "crd.referenceDetailValue as skillTypeId " + ")" + "FROM SkillEntity s "
			+ "inner join SkillCompetencyEntity sc on s.skillId=sc.skillId "
			+ "inner join CommonReferenceDetailsEntity crd on sc.skillTypeId = crd.referenceDetailId "
			+ "where s.skillId in :skillIds")
	List<SkillNamesDto> getSkills(int[] skillIds);

	 List<Optional<SkillEntity>> findBySkillName(String skillName);

	@Query(value = "select * from skills where techinical_category_id =:categoryId and is_deleted=0 and status=1", nativeQuery = true)
	List<SkillEntity> findByTechinicalCategoryIdForEmployee(int categoryId);

	
	@Modifying
	@Query(value = "update skills set skills.is_deleted = :is_deleted where skills.techinical_category_id = :technical_category_id", nativeQuery = true)
	void deleteSkill(@Param("technical_category_id") Long technicalCategoryId, @Param("is_deleted") Byte isDeleted);


}
