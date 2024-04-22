package com.feuji.employeeskillservice.bean;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SkillBean {

	private int skillId;
	private String skillName;
	private int techinicalCategoryId;
	private int skillCategoryId;
	private String description;
	private Byte  isDeleted;
	private String uuid;
	private String createdBy;
	private Timestamp createdOn;
	private String modifiedBy;
	private Timestamp modifiedOn;
	
}
