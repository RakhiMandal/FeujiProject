package com.feuji.timesheetentryservice.bean;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class TimesheetWeekBean {

	private Integer timesheetWeekId;
	private Integer accountId;
	private Integer accountProjectId;
	private Integer employeeId;
	private Integer weekNumber;
	private Timestamp weekStartDate;
	private Timestamp weekEndDate;
	private String comments;

	private Integer timesheetStatus;
	private Integer approvedBy;
	private Boolean isactive;
	private Boolean isDeleted;
	private String uuid;

	private String createdBy;
	private Timestamp createdOn;
	private String modifiedBy;

	private Timestamp modifiedOn;
}
