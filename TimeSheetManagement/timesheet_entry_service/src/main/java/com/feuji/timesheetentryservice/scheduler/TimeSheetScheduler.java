package com.feuji.timesheetentryservice.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.timesheetentryservice.service.TimeSheetDataService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TimeSheetScheduler {

	@Autowired
	private TimeSheetDataService service;

	//@Scheduled(fixedDelay = 36000)
	//@Scheduled(cron = "0 0 1 * * *")  //  every day 1am
	public void processPendingTimesheets() throws Exception {
		log.info("Started processing the pending Timesheet");
		service.processPendingTimesheetsBySubmittedStatus();
		log.info("Completed processing the pending Timesheet");
	}

}


