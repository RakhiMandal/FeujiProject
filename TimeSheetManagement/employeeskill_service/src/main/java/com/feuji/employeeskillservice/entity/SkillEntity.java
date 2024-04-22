package com.feuji.employeeskillservice.entity;

import java.sql.Timestamp;

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
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "skills")
public class SkillEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "skill_id")
	private int skillId;

	@Column(name = "skill_name")
	private String skillName;

	@Column(name = "techinical_category_id")
	private int techinicalCategoryId;

	@Column(name = "skill_category_id")
	private int skillCategoryId;

	@Column(name = "description")
	private String description;

	@Column(name = "is_deleted")
	private byte isDeleted;

	@Column(name = "uuid")
	private String uuid;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_on")
	private Timestamp modifiedOn;
}
