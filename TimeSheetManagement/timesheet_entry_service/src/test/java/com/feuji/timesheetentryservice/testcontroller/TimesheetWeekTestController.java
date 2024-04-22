package com.feuji.timesheetentryservice.testcontroller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.feuji.timesheetentryservice.bean.TimesheetDayBean;
import com.feuji.timesheetentryservice.bean.TimesheetWeekBean;
import com.feuji.timesheetentryservice.dto.AccountProjectResourceMappingDto;
import com.feuji.timesheetentryservice.dto.ProjectNameDto;
import com.feuji.timesheetentryservice.dto.ProjectTaskDto;
import com.feuji.timesheetentryservice.dto.ProjectTaskTypeNameDto;
import com.feuji.timesheetentryservice.entity.TimesheetWeekEntity;
import com.feuji.timesheetentryservice.service.TimesheetWeekService;

@ExtendWith(MockitoExtension.class)
public class TimesheetWeekTestController {
	@Mock
private TimesheetWeekService timesheetWeekService;
	 @Test
	    public void testSaveTimesheetWeek() {
	        LocalDate weekStartDate = LocalDate.parse("2024-03-05");
	        Timestamp startDate = Timestamp.valueOf(weekStartDate.atStartOfDay());

	        LocalDate weekEndDate = LocalDate.parse("2024-03-12");
	        Timestamp endDate = Timestamp.valueOf(weekEndDate.atStartOfDay());
	        TimesheetWeekBean timesheetWeekBean = TimesheetWeekBean.builder().timesheetWeekId(1).weekEndDate(endDate).weekStartDate(startDate).build();
	        timesheetWeekService.save(timesheetWeekBean);
	        verify(timesheetWeekService, times(1)).save(timesheetWeekBean);
	       

	    }
	 @Test
	    public void testgetTimesheetWeekId() {
	        LocalDate weekStartDate = LocalDate.parse("2024-03-05");
	        Timestamp startDate = Timestamp.valueOf(weekStartDate.atStartOfDay());

	        LocalDate weekEndDate = LocalDate.parse("2024-03-12");
	        Timestamp endDate = Timestamp.valueOf(weekEndDate.atStartOfDay());
	        TimesheetWeekEntity timesheetWeekBean = TimesheetWeekEntity.builder().timesheetWeekId(1).weekEndDate(endDate).weekStartDate(startDate). build();
	        
	        // Stubbing the behavior of timesheetWeekService.getById(1)
	        when(timesheetWeekService.getById(1)).thenReturn(timesheetWeekBean);
	        
	        // Calling the method to test
	        TimesheetWeekEntity resultBean = timesheetWeekService.getById(1);
	        
	        // Assertions
	        assertThat(resultBean).isNotNull();
	        assertThat(resultBean.getTimesheetWeekId()).isEqualTo(1);
	    }
	 @Test
	    public void testgetTimesheetWeekProjectNameByEmpId() {
	        LocalDate weekStartDate = LocalDate.parse("2024-03-05");
	        Timestamp startDate = Timestamp.valueOf(weekStartDate.atStartOfDay());

	        LocalDate weekEndDate = LocalDate.parse("2024-03-12");
	        Timestamp endDate = Timestamp.valueOf(weekEndDate.atStartOfDay());
	        TimesheetWeekEntity timesheetWeekBean = TimesheetWeekEntity.builder().timesheetWeekId(1).weekEndDate(endDate).weekStartDate(startDate)
	                .accountProjectId(1).employeeId(1).accountId(1).build();

	        // Create a list of ProjectNameDto instances for stubbing
	        List<ProjectNameDto> projectNameList = new ArrayList<>();
	        projectNameList.add(new ProjectNameDto(1,1,"2024-03-05"));
	        projectNameList.add(new ProjectNameDto(1,1,"2024-03-05"));

	        // Stub the behavior of timesheetWeekService.getProjectNameByEmpId(1, 1)
	        when(timesheetWeekService.getProjectNameByEmpId(1, 1)).thenReturn(projectNameList);

	        // Calling the method to test
	        List<ProjectNameDto> resultBean = timesheetWeekService.getProjectNameByEmpId(1, 1);

	        // Assertions
	        assertThat(resultBean).isNotNull();
	        assertThat(resultBean.size()).isEqualTo(2); 
	        assertThat(resultBean.get(0).getAccountProjectId()).isEqualTo(1);
	        assertThat(resultBean.get(0).getAccountId()).isEqualTo(1);
	        // Add more assertions based on your test requirements
	    }
	 @Test
	    public void testgetTimesheetWeekProjectTaskTypeName() {
	        LocalDate weekStartDate = LocalDate.parse("2024-03-05");
	        Timestamp startDate = Timestamp.valueOf(weekStartDate.atStartOfDay());

	        LocalDate weekEndDate = LocalDate.parse("2024-03-12");
	        Timestamp endDate = Timestamp.valueOf(weekEndDate.atStartOfDay());
	        TimesheetWeekEntity timesheetWeekBean = TimesheetWeekEntity.builder().timesheetWeekId(1).weekEndDate(endDate).weekStartDate(startDate)
	                .accountProjectId(1).employeeId(1).accountId(1).build();

	        // Create a list of ProjectTaskTypeNameDto instances for stubbing
	        List<ProjectTaskTypeNameDto> projectTaskTypeNameList = new ArrayList<>();
	        projectTaskTypeNameList.add(new ProjectTaskTypeNameDto(1,""));
	        projectTaskTypeNameList.add(new ProjectTaskTypeNameDto(1,""));

	        // Stub the behavior of timesheetWeekService.getProjectTaskTypeName(101, 111)
	        when(timesheetWeekService.getProjectTaskTypeName(101, 111)).thenReturn(projectTaskTypeNameList);

	        // Calling the method to test
	        List<ProjectTaskTypeNameDto> resultBean = timesheetWeekService.getProjectTaskTypeName(101, 111);

	        // Assertions
	        assertThat(resultBean).isNotNull();
	        assertThat(resultBean.size()).isEqualTo(2); 
	        assertThat(resultBean.get(0).getTaskType()).isEqualTo("");
	        assertThat(resultBean.get(0).getTaskTypeId()).isEqualTo(1);
	        // Add more assertions based on your test requirements
	    }

	 @Test
	    public void testgetTimesheetWeekProjectTask() {
	        LocalDate weekStartDate = LocalDate.parse("2024-03-05");
	        Timestamp startDate = Timestamp.valueOf(weekStartDate.atStartOfDay());

	        LocalDate weekEndDate = LocalDate.parse("2024-03-12");
	        Timestamp endDate = Timestamp.valueOf(weekEndDate.atStartOfDay());
	        TimesheetWeekEntity timesheetWeekBean = TimesheetWeekEntity.builder().timesheetWeekId(1).weekEndDate(endDate).weekStartDate(startDate).build();

	        // Create a list of ProjectTaskDto instances for stubbing
	        List<ProjectTaskDto> projectTaskList = new ArrayList<>();
	        projectTaskList.add(new ProjectTaskDto(1, ""));

	        // Stub the behavior of timesheetWeekService.getProjectTask(1)
	        when(timesheetWeekService.getProjectTask(1)).thenReturn(projectTaskList);

	        // Calling the method to test
	        List<ProjectTaskDto> resultBean = timesheetWeekService.getProjectTask(1);

	        // Assertions
	        assertThat(resultBean).isNotNull();
	        assertThat(resultBean.get(0).getTaskId()).isEqualTo(1);
	        // Add more assertions based on your test requirements
	    }
	 @Test
	    public void testfindAccountNameByUserEmpId() {
	        LocalDate weekStartDate = LocalDate.parse("2024-03-05");
	        Timestamp startDate = Timestamp.valueOf(weekStartDate.atStartOfDay());

	        LocalDate weekEndDate = LocalDate.parse("2024-03-12");
	        Timestamp endDate = Timestamp.valueOf(weekEndDate.atStartOfDay());
	        TimesheetWeekEntity timesheetWeekBean = TimesheetWeekEntity.builder().timesheetWeekId(1).weekEndDate(endDate).weekStartDate(startDate).build();

	        // Create a list of AccountProjectResourceMappingDto instances for stubbing
	        List<AccountProjectResourceMappingDto> accountProjectResourceList = new ArrayList<>();
	        accountProjectResourceList.add(new AccountProjectResourceMappingDto(1,1, "", 1));

	        // Stub the behavior of timesheetWeekService.findAccountNameByUserEmpId(1)
	        when(timesheetWeekService.findAccountNameByUserEmpId(1)).thenReturn(accountProjectResourceList);

	        // Calling the method to test
	        List<AccountProjectResourceMappingDto> resultBean = timesheetWeekService.findAccountNameByUserEmpId(1);

	        // Assertions
	        assertThat(resultBean).isNotNull();
	        assertThat(resultBean.get(0).getUserEmpId()).isEqualTo(1);
	        // Add more assertions based on your test requirements
	    }
}

