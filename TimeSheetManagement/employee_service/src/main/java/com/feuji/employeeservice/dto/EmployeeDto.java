package com.feuji.employeeservice.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class EmployeeDto {
	private Integer userEmpId;
	private String employeeCode;
	private String firstName;
	private String middleName;
	private String lastName;
	private String image;
	private String designation;
	private String email;
	private Integer gender;
	private Timestamp dateOfJoining;
	private Integer reportingManagerId;
	private Integer employmentType;
	private Integer status;
	private Integer deliveryUnitId;
	private Integer businessUnitId;
	
	public EmployeeDto(Integer userEmpId, String employeeCode, String firstName, String middleName, String lastName,
			String image, String designation, String email, Integer gender, Timestamp dateOfJoining,
			Integer reportingManagerId, Integer employmentType, Integer status, Integer deliveryUnitId,
			Integer businessUnitId) {
		super();
		this.userEmpId = userEmpId;
		this.employeeCode = employeeCode;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.image = image;
		this.designation = designation;
		this.email = email;
		this.gender = gender;
		this.dateOfJoining = dateOfJoining;
		this.reportingManagerId = reportingManagerId;
		this.employmentType = employmentType;
		this.status = status;
		this.deliveryUnitId = deliveryUnitId;
		this.businessUnitId = businessUnitId;
	}
	public EmployeeDto() {
		super();
	}

	

}