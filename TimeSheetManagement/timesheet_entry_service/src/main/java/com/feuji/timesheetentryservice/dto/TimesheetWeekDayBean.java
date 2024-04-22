package com.feuji.timesheetentryservice.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimesheetWeekDayBean {
    public int employeeId;
    public int accountProjectId;
    public int taskTypeId;
    public int taskId;

    public int attendanceType;
    // Assuming dates are strings in the form 'DD-Mon'
    @JsonFormat(pattern = "dd-MMM-yyyy")  // Specify the date format
    private Date dateMon;
     @JsonFormat(pattern =  "dd-MMM-yyyy")  // Specify the date format
     private Date dateTue;
     @JsonFormat(pattern =  "dd-MMM-yyyy")
     private Date dateWed;
     @JsonFormat(pattern =  "dd-MMM-yyyy")
     private Date dateThu;
     @JsonFormat(pattern =  "dd-MMM-yyyy")
     private Date dateFri;
     @JsonFormat(pattern =  "dd-MMM-yyyy")
     private Date dateSat;
     @JsonFormat(pattern =  "dd-MMM-yyyy")
     private Date dateSun; // e.g., '02-Mar'
    public int hoursMon;
    public int hoursTue;
    public int hoursWed;
    public int hoursThu;
    public int hoursFri;
    public int hoursSat;
    public int hoursSun;
    public String comments;
    public int timesheetStatus;
    public int accountId;
}
