package com.feuji.skillgapservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EmployeesSkillsListDto {
	private String employeeName;
	private Integer employeeId;
	private String employeeCode;
	private String designition;
	private String email;
	private List<SkillsBean> skillLists;

}
