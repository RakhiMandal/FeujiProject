package com.feuji.accountprojectservice.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
@Builder
@Entity
@Table(name = "account_resource_mapping")
public class AccountProjectResourceMappingEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mapping_id")
    private Integer mappingId;

    @Column(name = "account_project_id")
    private Integer accountProjectId;

    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "employee_status")
    private Integer employeeStatus;

    @Column(name = "allocation_start_date")
    private Timestamp allocationStartDate;

    @Column(name = "allocation_end_date")
    private Timestamp allocationEndDate;

    @Column(name = "allocation_status")
    private Integer allocationStatus;

    @Column(name = "allocation_percentage")
    private BigDecimal allocationPercentage;

    @Column(name = "billing_type_id")
    private Integer billingTypeId;

    @Column(name = "billing_id")
    private Integer billingId;

    @Column(name = "buffer_type")
    private Integer bufferType;

    @Column(name = "alias")
    private String alias;

    @Column(name = "location")
    private Integer location;

    @Column(name = "comments")
    private String comments;

    @Column(name = "isdeleted")
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
