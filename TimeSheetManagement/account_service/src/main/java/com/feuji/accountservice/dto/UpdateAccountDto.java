package com.feuji.accountservice.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
public class UpdateAccountDto {
	private Integer accountId;
	private String accountName;
	private Integer ownerId;
	private String ownerName;
	private Integer relationshipManagerId;
	private String relationshipManageName;
	private Integer businessDevelopmentManagerId;
	private String businessDevelopmentManagerName;
	private Integer parentAccountId;
	private String parentAccountName;
	private Integer accountBuId;
	private String accountBuName;
	private Timestamp formattedPlannedStartDate;


	private Timestamp formattedPlannedEndDate;


	private Timestamp formattedActualStartDate;


	private Timestamp formattedActualEndDate;
	private String address;
	private String city;
	private String state;
	private String zipcode;
	private String country;
	private Integer accountStatus;
	private String statusValue;
	private String comments;
	private String uuId;
	private Boolean isDeleted;
	
	public String getPlannedStartDate() {
        return formatDate(formattedPlannedStartDate);
    }

    public String getPlannedEndDate() {
        return formatDate(formattedPlannedEndDate);
    }

    public String getActualStartDate() {
        return formatDate(formattedActualStartDate);
    }

    public String getActualEndDate() {
        return formatDate(formattedActualEndDate);
    }

    private String formatDate(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(timestamp);
    }

	public UpdateAccountDto(Integer accountId, String accountName, Integer ownerId, String ownerName,
			Integer relationshipManagerId, String relationshipManageName, Integer businessDevelopmentManagerId,
			String businessDevelopmentManagerName, Integer parentAccountId, String parentAccountName,
			Integer accountBuId, String accountBuName, Timestamp formattedPlannedStartDate,
			Timestamp formattedPlannedEndDate, Timestamp formattedActualStartDate, Timestamp formattedActualEndDate,
			String address, String city, String state, String zipcode, String country, Integer accountStatus,
			String statusValue, String comments, String uuId, Boolean isDeleted) {
		super();
		this.accountId = accountId;
		this.accountName = accountName;
		this.ownerId = ownerId;
		this.ownerName = ownerName;
		this.relationshipManagerId = relationshipManagerId;
		this.relationshipManageName = relationshipManageName;
		this.businessDevelopmentManagerId = businessDevelopmentManagerId;
		this.businessDevelopmentManagerName = businessDevelopmentManagerName;
		this.parentAccountId = parentAccountId;
		this.parentAccountName = parentAccountName;
		this.accountBuId = accountBuId;
		this.accountBuName = accountBuName;
		this.formattedPlannedStartDate = formattedPlannedStartDate;
		this.formattedPlannedEndDate = formattedPlannedEndDate;
		this.formattedActualStartDate = formattedActualStartDate;
		this.formattedActualEndDate = formattedActualEndDate;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.country = country;
		this.accountStatus = accountStatus;
		this.statusValue = statusValue;
		this.comments = comments;
		this.uuId = uuId;
		this.isDeleted = isDeleted;
	}

	
	
	
}
