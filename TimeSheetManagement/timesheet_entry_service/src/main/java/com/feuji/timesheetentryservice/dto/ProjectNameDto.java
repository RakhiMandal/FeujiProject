package com.feuji.timesheetentryservice.dto;

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
public class ProjectNameDto {
	Integer accountId;
	Integer accountProjectId;
	String projectName;

}
