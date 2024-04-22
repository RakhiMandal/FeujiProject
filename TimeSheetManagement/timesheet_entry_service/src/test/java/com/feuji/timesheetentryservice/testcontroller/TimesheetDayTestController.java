package com.feuji.timesheetentryservice.testcontroller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.feuji.timesheetentryservice.bean.TimesheetDayBean;
import com.feuji.timesheetentryservice.controller.TimesheetDayController;
import com.feuji.timesheetentryservice.entity.HolidayEntity;
import com.feuji.timesheetentryservice.entity.TimesheetDayEntity;
import com.feuji.timesheetentryservice.service.TimesheetDayService;

@ExtendWith(MockitoExtension.class)
public class TimesheetDayTestController {
	@Mock
private TimesheetDayService timesheetDayService;
	@InjectMocks
	private TimesheetDayController timesheetDayController;
	
	@Test
    public void testsaveTimesheetDay() {
        LocalDate timesheetDayId = LocalDate.parse("2024-03-05");
        Timestamp date = Timestamp.valueOf(timesheetDayId.atStartOfDay()); // Convert LocalDate to Timestamp

        TimesheetDayBean timesheetDayBean = TimesheetDayBean.builder()
                .timesheetDayId(1)
                .date(date) // Set the Timestamp date
                .taskId(1)
                .build();

        timesheetDayService.saveTimesheetDay(timesheetDayBean);
        verify(timesheetDayService, times(1)).saveTimesheetDay(timesheetDayBean);
    }
	
	@Test
    public void testgettimesheetDay() {
        LocalDate timesheetDayId = LocalDate.parse("2024-03-05");
        Timestamp date = Timestamp.valueOf(timesheetDayId.atStartOfDay()); // Convert LocalDate to Timestamp

        TimesheetDayEntity timesheetDayBean = TimesheetDayEntity.builder()
                .timesheetDayId(1)
                .date(date) // Set the Timestamp date
                .taskId(1)
                .uuid("112")
                .build();

        // Mocking the service method to return the test data
        when(timesheetDayService.getTimeSheetDayByuuid(112)).thenReturn(timesheetDayBean);

        // Call the service method
        TimesheetDayEntity resultBean = timesheetDayService.getTimeSheetDayByuuid(112);

        // Assertions
        assertThat(resultBean).isNotNull();
        assertThat(resultBean.getUuid()).isEqualTo("112");
    }
	
}
	
	
