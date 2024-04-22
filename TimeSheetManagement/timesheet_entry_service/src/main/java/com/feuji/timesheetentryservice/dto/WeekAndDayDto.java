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
public class WeekAndDayDto {

	private Integer timesheetWeekId;
	private Integer accountId;
    private Integer employeeId;
    private Integer accountProjectId;
    private String projectName;
   
    private Integer taskTypeId;
    private String taskTypeName;
    
    private Integer taskId;
   private String taskName;
   private Integer attendanceType;
   private String attendanceTypeName;
   @JsonFormat(pattern = "yyyy-MM-dd")
   private Date weekStartDate;
   @JsonFormat(pattern = "yyyy-MM-dd")  // Specify the date format
   private Date dateMon;
    @JsonFormat(pattern = "yyyy-MM-dd")  // Specify the date format
    private Date dateTue;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateWed;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateThu;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateFri;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateSat;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateSun;
    @Builder.Default
    private Integer hoursMon=0;
    @Builder.Default
    private Integer hoursTue=0;
    @Builder.Default
    private Integer hoursWed=0;
    @Builder.Default
    private Integer hoursThu=0;
    @Builder.Default
    private Integer hoursFri=0;
    @Builder.Default
    private Integer hoursSat=0;
    @Builder.Default
    private Integer hoursSun=0;
   
    private String comments;
    private Integer timesheetStatus;
    private String timesheetStatusname;
	

   
}