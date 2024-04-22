package com.feuji.employeeskillservice.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeUiBean
{
	private String employeeMail;
	private String skillCategory;
	private String TechnicalCategory;
	private String skillId;
	private String skillTypeId;
	private String competencyLevelId;
	private String certification;
	private String comments;
	private int yearsOfExp;
	private String description;
	private String isDeleted;


}
