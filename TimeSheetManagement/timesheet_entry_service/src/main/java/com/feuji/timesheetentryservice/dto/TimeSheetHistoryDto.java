package com.feuji.timesheetentryservice.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TimeSheetHistoryDto {
	private Integer employeeId;
	private String uuId;
	private Timestamp weekStartDate;
	private Timestamp weekEndDate;
	private String projectName;
	private String accountName;
	private String status;
	private String empFirstName;
	private String empLastName;
	private double billingHours;
	private double nonBillinghours;
	private double leaveDays;
	public TimeSheetHistoryDto(Integer empId,String uuId, Timestamp weekStartDate, Timestamp weekEndDate, String projectName,
			String accountName, String status, String empFirstName, String empLastName, double billingHours,
			double nonBillinghours, double leaveDays) {
		super();
		this.employeeId=empId;
		this.uuId = uuId;
		this.weekStartDate = weekStartDate;
		this.weekEndDate = weekEndDate;
		this.projectName = projectName;
		this.accountName = accountName;
		this.status = status;
		this.empFirstName = empFirstName;
		this.empLastName = empLastName;
		this.billingHours = billingHours;
		this.nonBillinghours = nonBillinghours;
		this.leaveDays = leaveDays;
	}

}