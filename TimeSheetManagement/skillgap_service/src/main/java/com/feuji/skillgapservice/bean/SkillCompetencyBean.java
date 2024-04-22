package com.feuji.skillgapservice.bean;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SkillCompetencyBean {
	private Long roleId;
	private String roleName;
	private int skillId;
	private int skillTypeId;
	private int competencyLevelId;
	private int yearsOfExperiance;
	private Byte certification;
	private String description;
	private String comments;
	private Byte isDeleted;
	private String uuid;
	private String createdBy;
	private Timestamp createdOn;
	private String modifiedBy;
	private Timestamp modifiedOn;
}
