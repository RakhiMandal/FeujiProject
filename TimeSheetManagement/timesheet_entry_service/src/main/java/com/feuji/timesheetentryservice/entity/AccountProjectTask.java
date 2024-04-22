package com.feuji.timesheetentryservice.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="account_project_task")
public class AccountProjectTask {

	    @Id 
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "task_id")
	    private Integer taskId;

	    @Column(name = "tasktype_id")
	    private Integer taskTypeId;

	    @Column(name = "task")
	    private String task;

	    @Column(name = "task_description")
	    private String taskDescription;

	    @Column(name = "is_active")
	    private Boolean isActive;

	    @Column(name = "is_deleted")
	    private Boolean isDeleted;

	    @Column(name = "uuid")
	    private String uuid;

	    @Column(name = "created_by")
	    private String createdBy;

	    @Column(name = "created_on")
	    private Timestamp createdOn;

	    @Column(name = "modified_by")
	    private String modifiedBy;

	    @Column(name = "modified_on")
	    private Timestamp modifiedOn;

}
