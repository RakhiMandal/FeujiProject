package com.feuji.skillgapservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SkillNamesDto {


	private String skillName;
	private Integer skillTypeId;
	private String skillType;
	
	public SkillNamesDto(int skillTypeId, String skillName) 
	{
		this.skillTypeId = skillTypeId;
		this.skillName = skillName;
	}

}
