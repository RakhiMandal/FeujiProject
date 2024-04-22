package com.feuji.timesheetentryservice.testcontroller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.feuji.timesheetentryservice.controller.HolidayController;
import com.feuji.timesheetentryservice.entity.HolidayEntity;
import com.feuji.timesheetentryservice.service.HolidayService;

@ExtendWith(MockitoExtension.class)
public class HolidayTestController {
	@Mock
	private HolidayService holidayService;
	@InjectMocks
	private HolidayController holidayController;

	@Test
	public void testsaveHoliday() {
		HolidayEntity holidayEntity = HolidayEntity.builder().holidayDate(LocalDate.now()).holidayDay("Sunday")
				.holidayName("Diwali").build();
		holidayService.save(holidayEntity);
		verify(holidayService, times(1)).save(holidayEntity);
	}

	@Test
	public void testgetHoliday() {
		HolidayEntity holidayEntity = HolidayEntity.builder().holidayDate(LocalDate.now()).holidayDay("Sunday")
				.holidayName("Diwali").holidayId(54).build();
		when(holidayService.get(54)).thenReturn(holidayEntity);
		HolidayEntity holidayData = holidayService.get(54);
		assertThat(holidayData).isNotNull();
		assertThat(holidayData.getHolidayId()).isEqualTo(54);
	}

	@Test
	public void testgetAllHoliday() {
		List<HolidayEntity> holidayEntity =Arrays.asList(HolidayEntity.builder().holidayDate(LocalDate.now()).holidayDay("monday").holidayName("shivarthri")
				.build());
		when(holidayService.getAll()).thenReturn(holidayEntity);
		// assertThat(holidayEntity)
		List<HolidayEntity> holidayData = holidayService.getAll();
	}

	@Test
	public void testupdateHoliday() {
		HolidayEntity holidayEntity = HolidayEntity.builder().holidayDate(LocalDate.now()).holidayDay("Tuesday")
				.holidayName("Diwali").build();
		holidayService.update(holidayEntity);
		verify(holidayService, times(1)).update(holidayEntity);
	}

	public void testdeleteHoliday() {
		HolidayEntity holidayEntity = HolidayEntity.builder().holidayDate(LocalDate.now()).holidayDay("Sunday")
				.holidayName("Diwali").holidayId(54).build();
		when(holidayService.get(54)).thenReturn(holidayEntity);
		HolidayEntity DeleteholidayData = holidayService.delete(72);
		assertThat(DeleteholidayData).isNotNull();
		assertThat(DeleteholidayData.getHolidayId()).isEqualTo(72);
	}

}
