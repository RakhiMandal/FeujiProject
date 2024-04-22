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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="project_day_timesheet")
public class TimesheetDayEntity {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "timesheet_day_id")
	    private Integer timesheetDayId;
	  	@OneToOne
	  	@JoinColumn(name="timesheet_week_id",referencedColumnName = "timesheet_week_id")
	  	private TimesheetWeekEntity timesheetWeekEntity;

	    @Column(name = "attendance_type")
	    private Integer attendanceType;

	    @Column(name = "task_id")
	    private Integer taskId;

	    @Column(name = "date")
	    private Date date;

	    @Column(name = "no_of_hours")
	    private Integer numberOfHours;

	    @Column(name = "is_active")
	    private byte isActive;

	    @Column(name = "is_deleted")
	    private byte isDeleted;

	    @Column(name = "uuid")
	    private String uuid;

	    @Column(name = "created_by")
	    private String createdBy;

		@CreationTimestamp
		@Column(name="created_on",nullable=false,updatable=false)
	    private Date createdOn;

	    @Column(name = "modified_by")
	    private String modifiedBy;

		@CreationTimestamp
		@Column(name="modified_on",nullable=false,updatable=false)
	    private Date modifiedOn;

}
