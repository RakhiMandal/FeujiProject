package com.feuji.skillgapservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@ToString
public class EmployeeSkillDetailsDto {
	
	private String firstName;
	private String middleName;
	private String lastName;
	private Integer employeeId;
	private String employeeCode;
	private String designition;
	private String email;
	private Integer skillId;
	private String skillName;
	private String actualCompetency;
	private String expectedCompetency;
	private Long competencyLevelId;
	private String skillType;
	
}
