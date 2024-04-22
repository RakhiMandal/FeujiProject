package com.feuji.accountprojectservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor


public class AccountProjectResourceMappingDto {
	private Integer mappingId;
	private Integer accountId;
	private String accountName;
	private Integer userEmpId;
	public AccountProjectResourceMappingDto(Integer mappingId, Integer accountId, String accountName,
			Integer userEmpId) {
		super();
		this.mappingId = mappingId;
		this.accountId = accountId;
		this.accountName = accountName;
		this.userEmpId = userEmpId;
	}
	
	
}
