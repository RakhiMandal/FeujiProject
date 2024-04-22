package com.feuji.timesheetentryservice.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProjectTaskDto {
	private Integer taskId;
	private String task;

}
