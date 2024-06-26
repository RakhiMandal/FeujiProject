package com.feuji.referenceservice.bean;

import java.sql.Timestamp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CommonReferenceTypeBean {
	
	private Integer referenceTypeId;

	private String referenceTypeName;

	private String uuid;

	private Byte isDeleted;

	private String createdBy;

	private Timestamp createdOn;

	private String modifiedBy;

	private Timestamp modifiedOn;
	
	public void setName(String referenceTypeName) {
		this.referenceTypeName=referenceTypeName;
	}
}
