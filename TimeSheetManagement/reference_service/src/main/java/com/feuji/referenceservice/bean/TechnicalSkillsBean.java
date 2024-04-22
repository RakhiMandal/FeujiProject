package com.feuji.referenceservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TechnicalSkillsBean {

	private Integer referenceDetailId;
	private String referenceDetailValue;
	private Integer referenceTypeId;
	public TechnicalSkillsBean(int referenceDetailId, String referenceDetailValue) {
		this.referenceDetailId=referenceDetailId;
		this.referenceDetailValue=referenceDetailValue;
	}
}
