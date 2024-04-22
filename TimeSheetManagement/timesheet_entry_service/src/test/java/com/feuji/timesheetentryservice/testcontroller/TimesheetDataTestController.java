package com.feuji.timesheetentryservice.testcontroller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.feuji.timesheetentryservice.bean.TimeSheetDataBean;
import com.feuji.timesheetentryservice.bean.WeekAndDayDataBean;
import com.feuji.timesheetentryservice.controller.TimesheetDataController;
import com.feuji.timesheetentryservice.dto.EmployeeDataDto;
import com.feuji.timesheetentryservice.dto.SaveAndEditRecordsDto;
import com.feuji.timesheetentryservice.dto.WeekAndDayDto;
import com.feuji.timesheetentryservice.entity.TimesheetDayEntity;
import com.feuji.timesheetentryservice.entity.TimesheetWeekEntity;
import com.feuji.timesheetentryservice.repository.TimesheetDayRepo;
import com.feuji.timesheetentryservice.repository.TimesheetWeekRepo;
import com.feuji.timesheetentryservice.service.TimeSheetDataService;
import com.feuji.timesheetentryservice.util.Constants;
import com.feuji.timesheetentryservice.util.EmailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class TimesheetDataTestController {
	@Mock
	private TimeSheetDataService timeSheetDataService;
	@Mock
	private TimesheetWeekRepo timesheetWeekRepo;
	@Mock
	private TimesheetDayRepo timesheetDayRepo;
	
	@InjectMocks
	private TimesheetDataController timesheetDataController;

//	@Test
//	public void testsaveTimesheetData() {
//	    LocalDate weekStartDate = LocalDate.parse("2024-03-05");
//	    WeekAndDayDto weekAndDayDto = WeekAndDayDto.builder()
//	                                        .timesheetWeekId(1)
//	                                        .timesheetStatus(1)
//	                                        .weekStartDate(Date.valueOf(weekStartDate))
//	                                        .build();
//	    WeekAndDayDataBean dataBean = new WeekAndDayDataBean(); // Assuming there's a constructor or builder that accepts WeekAndDayDto
//	    List<WeekAndDayDataBean> dataList = Collections.singletonList(dataBean);
//	    timeSheetDataService.saveAll(dataList);
//	    verify(timeSheetDataService, times(1)).saveAll(dataList);
//	}
	
	@Test
    public void testSaveOrUpdateRecords_Success() {
        // Mock input data
        SaveAndEditRecordsDto weekAndDayDataBeans = new SaveAndEditRecordsDto();
        String weekStartDate = "2022-04-18"; // Example week start date

        // Mock behavior of service to return saved entities
        List<TimesheetWeekEntity> expectedEntities = new ArrayList<>();
        // Add some expected TimesheetWeekEntity objects

        // Stub the behavior of the service
        when(timeSheetDataService.saveOrUpdate(weekAndDayDataBeans, weekStartDate)).thenReturn(expectedEntities);

        // Call the method under test
        ResponseEntity<List<TimesheetWeekEntity>> responseEntity = timesheetDataController.saveOrUpdateRecords(weekAndDayDataBeans, weekStartDate);

        // Verify behavior
        verify(timeSheetDataService).saveOrUpdate(weekAndDayDataBeans, weekStartDate);

        // Assert the response status code
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Assert the returned entities
        assertEquals(expectedEntities, responseEntity.getBody());
    }
	

		
	@Test
	public void testFetchAllWeekDayRecordsById() {
		// Prepare test data
		LocalDate weekStartDate = LocalDate.parse("2024-03-05");
		LocalDate weekEndDate = LocalDate.parse("2024-04-05");
		List<WeekAndDayDto> expectedResultList = Collections.singletonList(WeekAndDayDto.builder().timesheetWeekId(1)
				.timesheetStatus(1).weekStartDate(Date.valueOf(weekStartDate)).accountId(1).employeeId(1).build());

		// Mock the service method to return the test data
		when(timeSheetDataService.fetchAllWeekDayRecordsById(anyInt(), anyInt(), anyString(), anyString()))
				.thenReturn(expectedResultList);

		// Call the service method
		List<WeekAndDayDto> actualResultList = timeSheetDataService.fetchAllWeekDayRecordsById(1, 1, "2024-03-05",
				"2024-04-05");

		// Assertions
		assertThat(actualResultList).isNotNull();
		assertThat(actualResultList).hasSize(1);
		assertThat(actualResultList.get(0).getAccountId()).isEqualTo(1);
		// Add more assertions as needed
	}

	@Test
	public void testdelteDayRecor() {
		LocalDate weekStartDate = LocalDate.parse("2024-03-05");
		WeekAndDayDto weekAndDayDto = WeekAndDayDto.builder().timesheetWeekId(1).timesheetStatus(1)
				.weekStartDate(Date.valueOf(weekStartDate)).build();
		WeekAndDayDataBean dataBean = new WeekAndDayDataBean(); // Assuming there's a constructor or builder that
																// accepts WeekAndDayDto
		List<WeekAndDayDataBean> dataList = Collections.singletonList(dataBean);
		timeSheetDataService.deleteDayRecord(weekAndDayDto);
		verify(timeSheetDataService, times(1)).deleteDayRecord(weekAndDayDto);
	}

	@Test
	public void testSubmittingTimesheet() {
		// Input data
		String weekStartDate = "2024-03-05";
		Integer timesheetStatus = 1;
		Integer accountId = 1;
		Integer employeeId = 123; // Sample employeeId

		// Mocking the behavior of the submittingTimesheet method
		when(timeSheetDataService.submittingTimesheet(weekStartDate, timesheetStatus, accountId, employeeId))
				.thenReturn(Collections.singletonList(TimesheetWeekEntity.builder().accountId(accountId).build()));

		// Calling the method to test
		List<TimesheetWeekEntity> actualResultList = timeSheetDataService.submittingTimesheet(weekStartDate,
				timesheetStatus, accountId, employeeId);

		// Assertions
		assertThat(actualResultList).isNotNull();
		assertThat(actualResultList).hasSize(1);
		assertThat(actualResultList.get(0).getAccountId()).isEqualTo(accountId);
		// Add more assertions as needed
	} 
}
	
	


