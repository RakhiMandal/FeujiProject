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
public class EmployeeSkillGet
{
	 	public Long employeeSkillId;  
	    public String skillCategory;
		public String TechnicalCategory;
		public String skillName;
		public int skillId;
		public String skillTypeId;
		public String competencyLevelId;
		public int  yearsOfExp;
		public String certification;
		public String description;
		public String  comments;
		public int isDeleted;
}
