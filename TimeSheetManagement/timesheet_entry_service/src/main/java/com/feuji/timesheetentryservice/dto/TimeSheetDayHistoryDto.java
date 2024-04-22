package com.feuji.timesheetentryservice.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TimeSheetDayHistoryDto {
	 private Integer timesheetDayId;
	 private Timestamp date;
	 private String taskTypeName;
	 private String taskName;
	 private double billingHours;
		private double nonBillinghours;
		private double leaveDays;
		public TimeSheetDayHistoryDto(Integer timesheetDayId, Timestamp date, String taskTypeName, String taskName,
				double billingHours, double nonBillinghours, double leaveDays) {
			super();
			this.timesheetDayId = timesheetDayId;
			this.date = date;
			this.taskTypeName = taskTypeName;
			this.taskName = taskName;
			this.billingHours = billingHours;
			this.nonBillinghours = nonBillinghours;
			this.leaveDays = leaveDays;
		}
		
		
	 
}
