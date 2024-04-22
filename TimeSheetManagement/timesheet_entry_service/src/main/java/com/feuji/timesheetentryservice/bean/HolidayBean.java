package com.feuji.timesheetentryservice.bean;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HolidayBean {
	private Integer holidayId;

	private LocalDateTime holidayDate;

	private String holidayDay;

	private String holidayName;

	private String description;

	private String location;

	private boolean isDeleted;

	private String uuid;

	private String createdBy;

	private LocalDateTime createdOn;

	private String modifiedBy;

	private LocalDateTime modifiedOn;


}
