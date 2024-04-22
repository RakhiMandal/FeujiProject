package com.feuji.employeeskillservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.feuji.employeeskillservice.entity.EmployeeSkillEntity;

import jakarta.transaction.Transactional;

@Transactional
public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkillEntity, Long> {

	Optional<EmployeeSkillEntity> findByUuid(String uuid);

	List<EmployeeSkillEntity> findBySkillId(int skillId);

	@Query(value = "SELECT es.* FROM employee_skills es " + "INNER JOIN skills s on s.skill_id = es.skill_id  "
			+ " WHERE es.employee_id = :employee_id " + " AND es.is_deleted = 0 " + " AND s.is_deleted = 0 "
			+ " AND s.status = 1", nativeQuery = true)
	public List<EmployeeSkillEntity> findByEmployeeId(@Param("employee_id") Integer employee_id);

	@Modifying
	@Query(value = "update employee_skills set is_deleted=1 where employee_skill_id=:employeeSkillId", nativeQuery = true)
	void updateIsDeleted(Long employeeSkillId);

	@Query(value = "select * from employee_skills where employee_skill_id=:id", nativeQuery = true)
	EmployeeSkillEntity findByEmployeeSkillId(Long id);

}
