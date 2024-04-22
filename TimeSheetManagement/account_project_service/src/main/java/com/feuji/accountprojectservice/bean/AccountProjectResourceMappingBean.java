package com.feuji.accountprojectservice.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountProjectResourceMappingBean {
	
    private Integer mappingId;

    private Integer accountProjectId;

    private Integer accountId;

    private Integer employeeId;

    private Integer employeeStatus;

    private Timestamp allocationStartDate;

    private Timestamp allocationEndDate;

    private Integer allocationStatus;

    private BigDecimal allocationPercentage;

    private Integer billingTypeId;

    private Integer billingId;

    private Integer bufferType;

    private String alias;

    private Integer location;

    private String comments;

    private Boolean isDeleted;

    private String uuid;

    private String createdBy;

    private Timestamp createdOn;

    private String modifiedBy;

    private Timestamp modifiedOn;

}
