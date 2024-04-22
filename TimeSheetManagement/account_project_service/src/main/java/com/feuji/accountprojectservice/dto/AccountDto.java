package com.feuji.accountprojectservice.dto;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor

public class AccountDto {
	
	private Integer accountProjectId;

	private String projectPId;

	private String projectName;

	private String accountName;
	
	private String firstName;
	
	private String referenceDetailValue;
	
	private float noOfBillingHours;


private String uuid;
//	
	
	private Timestamp plannedStartDate;
	private Timestamp  plannedEndDate;
	private Timestamp  actualStartDate;
	private Timestamp  actualEndDate;
	public AccountDto(Integer accountProjectId, String projectPId, String projectName, String accountName,
			String firstName, String referenceDetailValue, float noOfBillingHours, String uuid,
			Timestamp plannedStartDate, Timestamp plannedEndDate, Timestamp actualStartDate, Timestamp actualEndDate) {
		super();
		this.accountProjectId = accountProjectId;
		this.projectPId = projectPId;
		this.projectName = projectName;
		this.accountName = accountName;
		this.firstName = firstName;
		this.referenceDetailValue = referenceDetailValue;
		this.noOfBillingHours = noOfBillingHours;
		this.uuid = uuid;
		this.plannedStartDate = plannedStartDate;
		this.plannedEndDate = plannedEndDate;
		this.actualStartDate = actualStartDate;
		this.actualEndDate = actualEndDate;
	}
	

}
