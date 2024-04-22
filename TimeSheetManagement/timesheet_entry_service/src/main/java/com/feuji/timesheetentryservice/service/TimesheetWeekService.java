package com.feuji.timesheetentryservice.service;

import java.util.List;


import com.feuji.timesheetentryservice.bean.TimesheetWeekBean;
import com.feuji.timesheetentryservice.dto.AccountProjectResourceMappingDto;
import com.feuji.timesheetentryservice.dto.ProjectNameDto;
import com.feuji.timesheetentryservice.dto.ProjectTaskDto;
import com.feuji.timesheetentryservice.dto.ProjectTaskTypeNameDto;
import com.feuji.timesheetentryservice.dto.TimeSheeApprovalDto;
import com.feuji.timesheetentryservice.dto.TimeSheetHistoryDto;
import com.feuji.timesheetentryservice.dto.TimesheetApprovalSecondDto;
import com.feuji.timesheetentryservice.entity.TimesheetWeekEntity;

public interface TimesheetWeekService {
	
	public TimesheetWeekEntity save(TimesheetWeekBean timesheetWeekBean);

	public TimesheetWeekEntity getById(Integer id);
	
	public List<ProjectNameDto>getProjectNameByEmpId(Integer employeeId,Integer accountId);
	
	public List<ProjectTaskTypeNameDto> getProjectTaskTypeName(Integer employeeId,Integer accountProjectId);
	
	public List<ProjectTaskDto> getProjectTask(Integer taskTypeId);
	List<AccountProjectResourceMappingDto> findAccountNameByUserEmpId(Integer userEmpId );
	//public List<Object> getProjectIdAndNameByEmpId(Long id);
	
	//public List<Object> getProjectNameTaskName(Long id);

	public List<TimeSheetHistoryDto> timeSheetHistoryDto(String month ,int year,String accountName,int employeeId);

	public List<TimeSheetHistoryDto> getTimeSheetHistoryByYear(int year,  String accountName , int employeeId);

	public List<TimeSheetHistoryDto> getAccountByMonthAndYear(String month, int year, int employeeId);
		
	public List<Integer> getYear(int employeeId);
	String updateTimesheetStatus(Integer employeeId,Integer accountId,String weekStartDate);

	public String rejectedTimesheet(Integer employeeId, Integer accountId, String weekStartDate);
	//public List<TimeSheeApprovalDto> timeSheetHistoryDto(String month ,int year,Integer accountId);
//	public List<TimeSheetHistoryDto> timeSheetHistoryDto1(String month, int year, String accountName, int employeeId);
	public List<TimesheetApprovalSecondDto> timeSheetHistoryDto(String month, int year, Integer accountId);

}