package com.skillset.entity;

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
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@DynamicInsert
@DynamicUpdate
@Table(name = "skills")
public class SkillEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "skill_id")
	private int skillId;

	@Column(name = "skill_name")
	private String skillName;

	@Column(name = "techinical_category_id")
	private Integer techinicalCategoryId;

	@Column(name = "skill_category_id")
	private int skillCategoryId;

	@Column(name = "description")
	private String description;

	@Column(name = "is_deleted")
	private Byte isDeleted;

	@Column(name = "uuid")
	private String uuid;

	@Column(name = "created_by")
	private String createdBy;

	@CreationTimestamp
	@Column(name = "created_on", nullable = false, updatable = false)
	private Timestamp createdOn;

	@Column(name = "modified_by")
	private String modifiedBy;

	@CreationTimestamp
	@Column(name = "modified_on", nullable = false)
	private Timestamp modifiedOn;

	@Column(name = "status")
	private Byte status;
}
