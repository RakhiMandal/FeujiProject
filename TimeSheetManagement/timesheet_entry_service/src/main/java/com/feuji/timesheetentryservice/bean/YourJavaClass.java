package com.feuji.timesheetentryservice.bean;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class YourJavaClass {

    private Integer employeeId;
    private Integer projectId;
    private Integer taskTypeId;
   private Integer taskId;
  
   private Integer attendanceType;
   @JsonFormat(pattern = "dd-MMM-yyyy")  // Specify the date format
   private Date dateMon;
    @JsonFormat(pattern = "dd-MMM-yyyy")  // Specify the date format
    private Date dateTue;
    @JsonFormat(pattern = "dd-MMM-yyyy")
    private Date dateWed;
    @JsonFormat(pattern = "dd-MMM-yyyy")
    private Date dateThu;
    @JsonFormat(pattern = "dd-MMM-yyyy")
    private Date dateFri;
    @JsonFormat(pattern = "dd-MMM-yyyy")
    private Date dateSat;
    @JsonFormat(pattern = "dd-MMM-yyyy")
    private Date dateSun;

    private Integer hoursMon;
    private Integer hoursTue;
    private Integer hoursWed;
    private Integer hoursThu;
    private Integer hoursFri;
    private Integer hoursSat;
    private Integer hoursSun;
    private String comments;
    private Integer timesheetStatus;
	

   
}
