package com.feuji.timesheetentryservice.service;

import java.util.List;

import com.feuji.timesheetentryservice.bean.CommonReferenceDetailsBean;
import com.feuji.timesheetentryservice.bean.TimesheetDayBean;
import com.feuji.timesheetentryservice.dto.TimeSheetDayHistoryDto;
import com.feuji.timesheetentryservice.entity.TimesheetDayEntity;

public interface TimesheetDayService {

	public TimesheetDayEntity saveTimesheetDay(TimesheetDayBean timesheetDayBean);

	public TimesheetDayEntity getTimeSheetDayByuuid(Integer id);

	List<TimeSheetDayHistoryDto> getTimeSheetDayHistory(String uuId);

	List<CommonReferenceDetailsBean> getDetailsByTypeId(String typeName);

}