package com.feuji.skillgapservice.entity;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@DynamicInsert
@DynamicUpdate
@Table(name = "employee_skills")
public class EmployeeSkillEntity 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_skill_id")
	private Long employeeSkillId;
	
	@Column(name="employee_id")
	private int employeeId;
	
	@Column(name="employee_code")
	private String employeeCode;

	@Column(name="skill_id")
	private int skillId;
	
	@Column(name="competency_level_id")
	private int competencyLevelId;
	
	@Column(name="skill_type_id")
	private int skillTypeId;
	
	@Column(name="years_of_experiance")
	private int yearsOfExp;
	
	@Column(name="certification")
	private byte certification;
	
	@Column(name="description")
	private String description;
	
	@Column(name="comments")
	private String comments;
	
	@Column(name="is_deleted")
	private byte isDeleted;
	
	@Column(name="uuid")
	private String uuid;
	
	@Column(name="created_by")
	private String createdBy;
	
	@CreationTimestamp
	@Column(name="created_on",nullable=false,updatable=false)
	private Timestamp createdOn;
	
	@Column(name="modified_by")
	private String modifiedBy;
	
	@CreationTimestamp
	@Column(name="modified_on",nullable=false)
	private Timestamp modifiedOn;
}
