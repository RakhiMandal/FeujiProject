package com.feuji.timesheetentryservice.bean;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class WeekAndDayDataBean {

    public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public Integer getAccountProjectId() {
		return accountProjectId;
	}
	public void setAccountProjectId(Integer accountProjectId) {
		this.accountProjectId = accountProjectId;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public Integer getTaskTypeId() {
		return taskTypeId;
	}
	public void setTaskTypeId(Integer taskTypeId) {
		this.taskTypeId = taskTypeId;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public Integer getAttendanceType() {
		return attendanceType;
	}
	public void setAttendanceType(Integer attendanceType) {
		this.attendanceType = attendanceType;
	}
	public Date getDateMon() {
		return dateMon;
	}
	public void setDateMon(Date dateMon) {
		this.dateMon = dateMon;
	}
	public Date getDateTue() {
		return dateTue;
	}
	public void setDateTue(Date dateTue) {
		this.dateTue = dateTue;
	}
	public Date getDateWed() {
		return dateWed;
	}
	public void setDateWed(Date dateWed) {
		this.dateWed = dateWed;
	}
	@Override
	public String toString() {
		return "WeekAndDayDataBean [employeeId=" + employeeId + ", accountProjectId=" + accountProjectId
				+ ", accountId=" + accountId + ", taskTypeId=" + taskTypeId + ", taskId=" + taskId + ", attendanceType="
				+ attendanceType + ", dateMon=" + dateMon + ", dateTue=" + dateTue + ", dateWed=" + dateWed
				+ ", dateThu=" + dateThu + ", dateFri=" + dateFri + ", dateSat=" + dateSat + ", dateSun=" + dateSun
				+ ", hoursMon=" + hoursMon + ", hoursTue=" + hoursTue + ", hoursWed=" + hoursWed + ", hoursThu="
				+ hoursThu + ", hoursFri=" + hoursFri + ", hoursSat=" + hoursSat + ", hoursSun=" + hoursSun
				+ ", comments=" + comments + ", timesheetStatus=" + timesheetStatus + "]";
	}
	public Date getDateThu() {
		return dateThu;
	}
	public void setDateThu(Date dateThu) {
		this.dateThu = dateThu;
	}
	public Date getDateFri() {
		return dateFri;
	}
	public void setDateFri(Date dateFri) {
		this.dateFri = dateFri;
	}
	public Date getDateSat() {
		return dateSat;
	}
	public void setDateSat(Date dateSat) {
		this.dateSat = dateSat;
	}
	public Date getDateSun() {
		return dateSun;
	}
	public void setDateSun(Date dateSun) {
		this.dateSun = dateSun;
	}
	public Integer getHoursMon() {
		return hoursMon;
	}
	public void setHoursMon(Integer hoursMon) {
		this.hoursMon = hoursMon;
	}
	public Integer getHoursTue() {
		return hoursTue;
	}
	public void setHoursTue(Integer hoursTue) {
		this.hoursTue = hoursTue;
	}
	public Integer getHoursWed() {
		return hoursWed;
	}
	public void setHoursWed(Integer hoursWed) {
		this.hoursWed = hoursWed;
	}
	public Integer getHoursThu() {
		return hoursThu;
	}
	public void setHoursThu(Integer hoursThu) {
		this.hoursThu = hoursThu;
	}
	public Integer getHoursFri() {
		return hoursFri;
	}
	public void setHoursFri(Integer hoursFri) {
		this.hoursFri = hoursFri;
	}
	public Integer getHoursSat() {
		return hoursSat;
	}
	public void setHoursSat(Integer hoursSat) {
		this.hoursSat = hoursSat;
	}
	public Integer getHoursSun() {
		return hoursSun;
	}
	public void setHoursSun(Integer hoursSun) {
		this.hoursSun = hoursSun;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Integer getTimesheetStatus() {
		return timesheetStatus;
	}
	public void setTimesheetStatus(Integer timesheetStatus) {
		this.timesheetStatus = timesheetStatus;
	}
	private Integer employeeId;
    private Integer accountProjectId;
    private Integer accountId;
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
    public WeekAndDayDataBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WeekAndDayDataBean(Integer employeeId, Integer accountProjectId, Integer accountId, Integer taskTypeId,
			Integer taskId, Integer attendanceType, Date dateMon, Date dateTue, Date dateWed, Date dateThu,
			Date dateFri, Date dateSat, Date dateSun, Integer hoursMon, Integer hoursTue, Integer hoursWed,
			Integer hoursThu, Integer hoursFri, Integer hoursSat, Integer hoursSun, String comments,
			Integer timesheetStatus) {
		super();
		this.employeeId = employeeId;
		this.accountProjectId = accountProjectId;
		this.accountId = accountId;
		this.taskTypeId = taskTypeId;
		this.taskId = taskId;
		this.attendanceType = attendanceType;
		this.dateMon = dateMon;
		this.dateTue = dateTue;
		this.dateWed = dateWed;
		this.dateThu = dateThu;
		this.dateFri = dateFri;
		this.dateSat = dateSat;
		this.dateSun = dateSun;
		this.hoursMon = hoursMon;
		this.hoursTue = hoursTue;
		this.hoursWed = hoursWed;
		this.hoursThu = hoursThu;
		this.hoursFri = hoursFri;
		this.hoursSat = hoursSat;
		this.hoursSun = hoursSun;
		this.comments = comments;
		this.timesheetStatus = timesheetStatus;
	}
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