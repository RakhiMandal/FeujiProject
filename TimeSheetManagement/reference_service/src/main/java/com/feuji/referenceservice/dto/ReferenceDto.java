package com.feuji.referenceservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ReferenceDto {
	private Integer referenceTypeId;
	private String referenceTypeName;

}
