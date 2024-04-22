package com.feuji.accountprojectservice.bean;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeBean {
	
	private Integer employeeId;
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
	private Timestamp exitDate;
	private String exitRemarks;
	private Boolean isDeleted;
	private String uuid;
	private String createdBy;
	private Timestamp createdOn;
	private String modifiedBy;
	private Timestamp modifiedOn;

}
