package com.feuji.timesheetentryservice.entity;

import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "account_projects")
public class AccountProjectsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_project_id")	
	private Integer accountProjectId;

	@Column(name = "project_pid")
	private String projectPId;

	@Column(name = "project_name")
	private String projectName;

	@Column(name = "account_id")
	private Integer accountId;

	@Column(name = "priority")
	private Integer priority;

	@Column(name = "projectmanager_id")
	private Integer projectManagerId;

	@Column(name = "no_of_billing_hours")
	private Double noOfBillingHours;

	@CreationTimestamp
	@Column(name = "planned_start_date")
	private Timestamp plannedStartDate;

	@CreationTimestamp
	@Column(name = "planned_end_date")
	private Timestamp plannedEndDate;

	@CreationTimestamp
	@Column(name = "actual_start_date")
	private Timestamp actualStartDate;

	@CreationTimestamp
	@Column(name = "actual_end_date")
	private Timestamp actualEndDate;

	@Column(name = "project_status")
	private int projectStatus;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "is_red")
	private Boolean isRed;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Column(name = "uuid")
	private String uuid;

	@Column(name = "created_by")
	private String createdBy;

	@CreationTimestamp
	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "modified_by")
	private String modifiedBy;

	@CreationTimestamp
	@Column(name = "modified_on")
	private Timestamp modifiedOn;

}
