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
@AllArgsConstructor
@NoArgsConstructor

public class SaveEmployeeUserDto {
	private String employeeCode;
    private Integer userEmpId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private Integer gender;
    private String password;
    private String designation;
    private Timestamp dateOfJoining;
    private Integer reportingManagerId;
    private Integer employmentType;
    private Integer status;
    private Integer deliveryUnitId;
    private Integer businessUnitId;

}
