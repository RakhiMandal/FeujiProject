package com.feuji.employeeservice.bean;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder 
@ToString
public class UserLoginBean {
	
	private Integer userId;
    private Integer userEmpId;
    private String userName;
    private String userPassword;
    private String designation;
    private String userEmail;
    private String employeeStatus;
    private boolean isInternal;
    private boolean isDeleted;
    private String uuid;
    private String createdBy;
    private String createdOn;
    private String modifiedBy;
    private Timestamp modifiedOn;
    private Boolean flag;

}