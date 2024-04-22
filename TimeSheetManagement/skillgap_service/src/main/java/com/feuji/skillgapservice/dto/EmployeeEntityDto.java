package com.feuji.skillgapservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeEntityDto {
	private Integer employeeId;
	private String employeeCode;
	private String firstName;
	private String middleName;
	private String lastName;
	private String designation;
	private String email;
}