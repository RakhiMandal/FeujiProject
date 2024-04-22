package com.feuji.accountprojectservice.entity;

import java.sql.Timestamp;
import java.util.UUID;

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
@Table(name="account_project_tasktype")
public class AccountProjectTaskType {

	@Id
	@Column(name = "tasktype_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   private int taskTypeId;

	@Column(name = "reference_type_id")
	private Long referenceTypeId;
	
	@Column(name = "reference_type_name")
	private String referenceTypeName;
		
	@Column(name = "account_id")
	private Integer accountId;

	@Column(name = "account_project_id")
	private Integer accountProjectId;

	@Column(name = "task_type")
	private String taskType;

	@Column(name = "task_type_description")
	private String taskTypeDescription;

	@Column(name = "is_active")
	private Boolean isActive;


	@Column(name = "is_deleted")
	private Boolean isDeleted;

//	@Column(name = "uuid")
//	private UUID uuid=UUID. randomUUID();

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
