package com.feuji.timesheetentryservice.dto;

import org.hibernate.internal.build.AllowNonPortable;

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
public class ProjectTaskTypeNameDto {
Integer	taskTypeId;
String taskType;
}
