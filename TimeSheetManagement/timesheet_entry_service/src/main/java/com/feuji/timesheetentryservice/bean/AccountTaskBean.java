package com.feuji.timesheetentryservice.bean;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountTaskBean {
	private Integer taskId;
	private Integer taskTypeId;
	private String task;
	private String taskDescription;
	private byte isActive;
	private byte isDeleted;
	private String uuid;
	private String createdBy;
	private Timestamp createdOn;
	private String modifiedBy;
	private Timestamp modifiedOn;
}