package com.feuji.timesheetentryservice.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TimeSheeApprovalDto {
	private Integer employeeId;
    private Timestamp weekStartDate;
    private String email;
    private Timestamp plannedStartDate;
    private Timestamp plannedEndDate;
    private Timestamp weekEndDate;
    private String projectName;
    private String accountName;
    private String empCode;
    private String designation;
    private int managerId;
    private String timesheetStatus;
    private String fullName;
    private Long billingHours;
    private Long nonBillinghours;
    private Long leaveDays;

    public TimeSheeApprovalDto(Integer empId,  Timestamp weekStartDate, String email, Timestamp plannedStartDate,
            Timestamp plannedEndDate, Timestamp weekEndDate, String projectName, String accountName, String empCode,
            String designation, int managerId, String timesheetStatus, String fullName, Long billingHours,
            Long nonBillinghours, Long leaveDays) {
        super();
       this.employeeId=empId;
        this.weekStartDate = weekStartDate;
        this.email = email;
        this.plannedStartDate = plannedStartDate;
        this.plannedEndDate = plannedEndDate;
        this.weekEndDate = weekEndDate;
        this.projectName = projectName;
        this.accountName = accountName;
        this.empCode = empCode;
        this.designation = designation;
        this.managerId = managerId;
        this.timesheetStatus = timesheetStatus;
        this.fullName = fullName;
        this.billingHours = billingHours;
        this.nonBillinghours = nonBillinghours;
        this.leaveDays = leaveDays;
    }
}