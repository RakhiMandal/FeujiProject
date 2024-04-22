package com.feuji.timesheetentryservice.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="account")
@DynamicInsert
@DynamicUpdate
public class AccountEntity {
	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="account_id")
	private Integer accountId;
	@Column(name="account_name")
	private String accountName;

	@Column(name="owner_id")
	private Integer ownerId;
	@Column(name="relationship_manager_id")
	private Integer relationshipManagerId;
	@Column(name="business_development_manager_id")
	private Integer businessDevelopmentManagerId;
	@Column(name="parentAccount_id")
	private Integer parentAccountId;
	@Column(name="account_bu_id")
	private Integer accountBuId;
	@Column(name="planned_start_date")
	private Timestamp plannedStartDate;
	@Column(name="planned_end_date")
	private Timestamp plannedEndDate;
	@Column(name="actual_start_date")
	private Timestamp actualStartDate;
	@Column(name="actual_end_date")
	private Timestamp actualEndDate;
	@Column(name="address")
	private String address;
	@Column(name="city")
	private String city;
	@Column(name="state")
	private String state;
	@Column(name="zipcode")
	private String zipcode;
	@Column(name="country")
	private String country;
	@Column(name="is_red")
	private Boolean isRed;
	@Column(name="account_status")
	private Integer accountStatus;
	@Column(name="comments")
	private String comments;
	@Column(name="is_deleted")
	private Boolean isDeleted;
	@Column(name="uuid")
	private String uuId;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Timestamp createdOn;
	@Column(name="modified_by")
	private String modifiedBy;
	@Column(name="modified_on")
	private Timestamp modifiedOn;
}
