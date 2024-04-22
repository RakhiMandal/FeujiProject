package com.feuji.timesheetentryservice.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "project_timesheet_summary_view")
public class TimesheetWeekSummaryViewEntity {
	@Id
	private Integer TimesheetWeekSummaryId;
	private Integer employeeId;
	private String designation;
	private String employeeCode;
	private String fullName;
	private String email;
	private Integer approvedBy;
	private Integer weekNumber;
	private String projectName;
	private Integer accountProjectId;
	private Long totalBillingHours;
	private Long totalNonBillingHours;
	private Long totalLeaveHours;
	private String timesheetStatus;
	private Timestamp weekStartDate;
	private Timestamp weekEndDate;
	private Timestamp plannedStartDate;
	private Timestamp plannedEndDate;
	private Integer accountId;
	

}
