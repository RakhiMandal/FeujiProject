package com.feuji.timesheetentryservice.bean;

import java.sql.Timestamp;
import java.util.UUID;

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
public class AccountProjectTaskTypeBean {

	private long taskTypeId;

	private long accountId;

	private long accountProjectId;

	private String taskType;

	private String taskTypeDescription;

	private Boolean isActive;

	private Boolean isDeleted;

	private UUID uuid = UUID.randomUUID();

//	@Column(name = "uuid")
//	private String uuid;

	private String createdBy;

	private Timestamp createdOn;

	private String modifiedBy;

	private Timestamp modifiedOn;

}
