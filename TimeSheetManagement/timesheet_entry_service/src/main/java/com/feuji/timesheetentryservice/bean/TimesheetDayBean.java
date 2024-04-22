package com.feuji.timesheetentryservice.bean;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TimesheetDayBean {


    private Integer timesheetDayId;
    private Integer timesheetWeekId;
    private Integer attendanceType;
    private Integer taskId;
    private Timestamp date;
    private Double numberOfHours;
    private Boolean isActive;
    private Boolean isDeleted;
    private String uuid;
    private String createdBy;
    private Timestamp createdOn;
    private String modifiedBy;
    private Timestamp modifiedOn;

    
}