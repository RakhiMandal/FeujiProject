package com.feuji.accountservice.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
public class AccountDTO {
	private String uuId;
	private Integer accountId;
	private String accountName;
	private String ownerName;
	private String relationshipManageName;
	private String businessDevelopmentManagerId;
	private String parentAccountName;
	private Timestamp plannedStartDate;
	private Timestamp plannedEndDate;
	private Timestamp actualStartDate;
	private Timestamp actualEndDate;
	private String accountBuName;
	private String status;
	public AccountDTO(String uuId, Integer accountId, String accountName, String ownerName,
			String relationshipManageName, String businessDevelopmentManagerId, String parentAccountName,
			Timestamp plannedStartDate, Timestamp plannedEndDate, Timestamp actualStartDate, Timestamp actualEndDate,
			String accountBuName, String status) {
		super();
		this.uuId = uuId;
		this.accountId = accountId;
		this.accountName = accountName;
		this.ownerName = ownerName;
		this.relationshipManageName = relationshipManageName;
		this.businessDevelopmentManagerId = businessDevelopmentManagerId;
		this.parentAccountName = parentAccountName;
		this.plannedStartDate = plannedStartDate;
		this.plannedEndDate = plannedEndDate;
		this.actualStartDate = actualStartDate;
		this.actualEndDate = actualEndDate;
		this.accountBuName = accountBuName;
		this.status = status;
	}
	
	
	
}
