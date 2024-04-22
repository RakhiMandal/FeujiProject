package com.feuji.timesheetentryservice.dto;

import java.sql.Timestamp;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.feuji.timesheetentryservice.entity.TimesheetWeekEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimesheetWeekDayDetailDto {

	private Integer timesheetWeekId;
	
    private Integer employeeId;
    private Integer accountId;
    private Integer accountProjectId;
  
    private Timestamp weekStartDate;
  private Timestamp weekEndDate;
    private Integer weekNumber;
    private Integer timesheetDayId;
    
    private Integer taskId;
    private Integer taskTypeId;
    private Timestamp date;
    private Integer numberOfHours;
    private Integer attendanceType;
    private Integer timesheetStatus;
   
}


 
 
