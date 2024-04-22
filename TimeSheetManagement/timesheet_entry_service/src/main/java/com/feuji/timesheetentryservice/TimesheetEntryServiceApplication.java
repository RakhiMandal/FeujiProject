 package com.feuji.timesheetentryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TimesheetEntryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimesheetEntryServiceApplication.class, args);
		
	}

}
 