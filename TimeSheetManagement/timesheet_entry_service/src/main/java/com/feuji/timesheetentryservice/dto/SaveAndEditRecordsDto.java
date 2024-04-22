package com.feuji.timesheetentryservice.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.feuji.timesheetentryservice.bean.WeekAndDayDataBean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveAndEditRecordsDto {
	private List<WeekAndDayDataBean> timesheetWeekDayDetailDto;
	private List<WeekAndDayDto> weekAndDayDto;
	
}
