package com.feuji.accountservice.bean;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountBean {
	private Integer accountId;
	private String accountName;
	private Integer ownerId;
	private Integer relationshipManagerId;
	private Integer businessDevelopmentManagerId;
	private Integer parentAccountId;
	private Integer accountBuId;
	private Timestamp plannedStartDate;
	private Timestamp plannedEndDate;
	private Timestamp actualStartDate;
	private Timestamp actualEndDate;
	private String address;
	private String city;
	private String state;
	private String zipcode;
	private String country;
	private Boolean isRed;
	private Integer accountStatus;
	private String comments;
	private Boolean isDeleted;
//	private Character uuId;  
	private String uuId;  
	private String createdBy;
	private Timestamp createdOn;
	private String modifiedBy;
	private Timestamp modifiedOn;
}
