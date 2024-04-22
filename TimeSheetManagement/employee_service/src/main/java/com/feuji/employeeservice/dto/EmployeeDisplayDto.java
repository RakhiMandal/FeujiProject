package com.feuji.employeeservice.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
@NoArgsConstructor
public class EmployeeDisplayDto {
	private Integer employeeId;
	private String employeeCode;
	private String firstName;
	private String middleName;
	private String lastName;
	private String image;
	private String designation;
	private String email;
	private String gender;
	private Timestamp dateOfJoining;
	private String managerFirstName;
	private String managerLastNamee;
	private String managerMiddleName;
	private String status;
	private String uuid;
	public EmployeeDisplayDto(Integer employeeId, String employeeCode, String firstName, String middleName,
			String lastName, String image, String designation, String email, String gender, Timestamp dateOfJoining,
			String managerFirstName, String managerLastNamee, String managerMiddleName, String status, String uuid) {
		super();
		this.employeeId = employeeId;
		this.employeeCode = employeeCode;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.image = image;
		this.designation = designation;
		this.email = email;
		this.gender = gender;
		this.dateOfJoining = dateOfJoining;
		this.managerFirstName = managerFirstName;
		this.managerLastNamee = managerLastNamee;
		this.managerMiddleName = managerMiddleName;
		this.status = status;
		this.uuid = uuid;
	}
	
}

