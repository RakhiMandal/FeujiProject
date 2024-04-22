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
import lombok.ToString;
@Entity
@Table(name = "skill_competency")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@DynamicInsert
@DynamicUpdate
public class SkillCompetencyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Long roleId;
	@Column(name = "role_name")
	private String roleName;
	@Column(name = "skill_id")
	private int skillId;
	@Column(name = "skill_type_id")
	private int skillTypeId;
	@Column(name = "competency_level_id")
	private int competencyLevelId;
	@Column(name = "years_of_experiance")
	private int yearsOfExperiance;
	@Column(name = "certification")
	private Byte certification;
	@Column(name = "description")
	private String description;
	@Column(name = "comments")
	private String comments;
	@Column(name = "is_deleted")
	private Byte isDeleted;
	@Column(name = "uuid")
	private String uuid;
	@Column(name = "created_by")
	private String createdBy;
    @CreationTimestamp
	@Column(name = "created_on",nullable=false,updatable=false)
	private Timestamp createdOn;
	@Column(name = "modified_by")
	private String modifiedBy;
    @CreationTimestamp
	@Column(name = "modified_on", nullable = false)
	private Timestamp modifiedOn;
}
