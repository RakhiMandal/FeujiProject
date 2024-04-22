package com.feuji.timesheetentryservice.bean;

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
public class ReferenceDetailsBean {
	
	
	private Integer referenceDetailId;
  private String referenceDetailValue;
}
