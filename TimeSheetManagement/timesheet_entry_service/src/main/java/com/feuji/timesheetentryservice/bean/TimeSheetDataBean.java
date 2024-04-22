package com.feuji.timesheetentryservice.bean;

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
public class TimeSheetDataBean {
	private Integer employeeId;
	
	private Integer accountProjectId;
	private Integer taskTypeId;
	private Integer taskId;
	private Integer weekNumber;
	private Integer timesheetStatus;
	private Timestamp weekStartDate;
	private Timestamp weekEndDate;
	
	

}
