package com.feuji.timesheetentryservice.bean;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TimesheetHomeUiDayBean {
	
	private Integer timesheetDayId;
    private Integer timesheetWeekId;
    private Integer attendanceType;
    private Integer taskId;
    @JsonFormat(pattern = "dd-MMM-yyyy")
    private Date date;
    private Double numberOfHours;
    private Boolean isActive;
    private String uuid;
    private Boolean isDeleted;
  
    private String createdBy;
    
    @JsonFormat(pattern = "dd-MMM-yyyy")
    private Date createdOn;
    private String modifiedBy;
    @JsonFormat(pattern = "dd-MMM-yyyy")
    private Date modifiedOn;
}
