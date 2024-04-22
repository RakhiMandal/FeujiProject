package com.feuji.accountprojectservice.bean;

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


	private Integer taskTypeId;

	private Integer accountId;

	private Integer accountProjectId;

	private String taskType;

	private String taskTypeDescription;

	private Boolean isActive;

	private Boolean isDeleted;

//	private UUID uuid = UUID.randomUUID();


	private String uuid;

	private String createdBy;

	private Timestamp createdOn;

	private String modifiedBy;

	private Timestamp modifiedOn;

}
