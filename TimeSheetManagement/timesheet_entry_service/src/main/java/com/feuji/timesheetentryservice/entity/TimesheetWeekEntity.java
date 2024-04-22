package com.feuji.timesheetentryservice.entity;


import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DynamicInsert
@DynamicUpdate
@Table(name="project_week_timesheet")
public class TimesheetWeekEntity {
@Id
@Column(name="timesheet_week_id")
@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer timesheetWeekId;
@Column(name="account_id")
private Integer accountId;
@Column(name="account_project_id")
	private Integer	accountProjectId;
	@Column(name="employee_id")
	private Integer	employeeId;
	@Column(name="week_number")
		private Integer weekNumber;
	@Column(name="week_start_date")
		private Date weekStartDate;
	@Column(name="week_end_date")
		private Date weekEndDate;
	@Column(name="comments")
		private String comments;
		@Column(name="timesheet_status")
	private Integer	timesheetStatus;
		@Column(name="approved_by")
	private Integer 	approvedBy;
		@Column(name="is_active")
	private byte isactive;
		@Column(name="is_deleted")
	private byte isDeleted;
		@Column(name="uuid")
		private String uuid;
		@Column(name="created_by")
		private String createdBy;
		@CreationTimestamp
		@Column(name="created_on",nullable=false,updatable=false)
		
		private Date createdOn;
		@Column(name="modified_by")
		private String modifiedBy;
		@CreationTimestamp
		@Column(name="modified_on",nullable=false,updatable=false)
	
		private Timestamp modifiedOn;
}
