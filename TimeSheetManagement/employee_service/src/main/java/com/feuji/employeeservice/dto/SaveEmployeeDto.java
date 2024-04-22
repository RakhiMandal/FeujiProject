package com.feuji.employeeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class SaveEmployeeDto {
	private Integer referenceDetailId;
	private String referenceDetailValue;
	private Integer referenceTypeId;
	

}
