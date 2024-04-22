package com.feuji.timesheetentryservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.timesheetentryservice.dto.AccountNameDto;
import com.feuji.timesheetentryservice.dto.ProjectNameDto;
import com.feuji.timesheetentryservice.dto.TimeSheeApprovalDto;
import com.feuji.timesheetentryservice.dto.TimesheetApprovalSecondDto;
import com.feuji.timesheetentryservice.dto.ProjectTaskDto;
import com.feuji.timesheetentryservice.dto.ProjectTaskTypeNameDto;
import com.feuji.timesheetentryservice.entity.TimesheetWeekEntity;
import com.feuji.timesheetentryservice.entity.TimesheetWeekSummaryViewEntity;
import com.feuji.timesheetentryservice.repository.TimesheetWeekSummaryRepo;
import com.feuji.timesheetentryservice.service.TimesheetWeekSummaryService;

@RestController

@RequestMapping("/TimesheetWeekSummaryView")
@CrossOrigin(origins = "http://localhost:4200")
public class TimesheetWeekSummaryViewController {
	private static Logger log = LoggerFactory.getLogger(TimesheetWeekSummaryViewController.class);

	@Autowired
	private TimesheetWeekSummaryRepo timesheetWeekSummaryRepo;

	@Autowired
	private TimesheetWeekSummaryService timesheetWeekSummaryService;


	@GetMapping("/timesheets/manager/{approvedBy}/{accountId}/{weekNumber}")
	public ResponseEntity<List<TimesheetWeekSummaryViewEntity>> getTimesheetsForManager(
			@PathVariable Integer approvedBy, @PathVariable Integer accountId, @PathVariable Integer weekNumber) {
		try {
			log.info("Fetching timesheets for manager: approvedBy={}, accountId={}, weekNumber={}", approvedBy,
					accountId, weekNumber);

			List<TimesheetWeekSummaryViewEntity> timesheets = timesheetWeekSummaryService
					.getTimesheetsForManager(approvedBy, accountId, weekNumber);

			if (timesheets.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(timesheets);
			}
		} catch (Exception e) {
			log.error("Error fetching timesheets for manager: approvedBy={}, accountId={}, weekNumber={}: {}",
					approvedBy, accountId, weekNumber, e.getMessage(), e);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}


	@GetMapping("/projects/{accountId}/{approvedBy}")
	public ResponseEntity<List<TimesheetApprovalSecondDto>> getAccountProjects(@PathVariable Integer accountId,
			@PathVariable Integer approvedBy) {
		try {
			log.info("Fetching account projects for accountId: {}", accountId);
			List<TimesheetApprovalSecondDto> projects = timesheetWeekSummaryService.getAccountProjects(accountId, approvedBy);

			if (projects != null && !projects.isEmpty()) {
				return ResponseEntity.ok(projects);
			} else {
				return ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			log.error("Error fetching account projects for accountId: {}: {}", accountId, e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}



	@GetMapping("/accounts/{approvedBy}")
	public ResponseEntity<List<AccountNameDto>> getAccounts(@PathVariable Integer approvedBy) {
		try {
			log.info("Fetching account for approvedBy: {}", approvedBy);
			List<AccountNameDto> accounts = timesheetWeekSummaryService.getAccounts(approvedBy);

			if (accounts != null && !accounts.isEmpty()) {
				return ResponseEntity.ok(accounts);
			} else {
				return ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			log.error("Error fetching accounts for approvedBy: {}: {}", approvedBy, e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}



	@GetMapping("/total/{employeeId}/{accountProjectId}/{weekNumber}")
	public ResponseEntity<Integer> getTotalHours(@PathVariable Integer employeeId,
			@PathVariable Integer accountProjectId, @PathVariable Integer weekNumber) {
		 try {
		        Integer totalHours = timesheetWeekSummaryService.getTotalHours(employeeId, accountProjectId, weekNumber);
		        log.info("empl id :{} accproId: {} weekno: {}",employeeId,accountProjectId,weekNumber);
		        if (totalHours != null) {
		            return ResponseEntity.ok(totalHours);
		        } else {
		        	return ResponseEntity.noContent().build();
		        }
		    } catch (Exception e) {
		        log.error("Error fetching total hours for employeeId {}, accountProjectId {}, weekNumber {}: {}",
		                employeeId, accountProjectId, weekNumber, e.getMessage(), e);
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		    }
	}

	
	@GetMapping(path = "/getTimeSheeApproval/{projectManagerId}/{year}/{accountId}")
	public ResponseEntity<List<TimeSheeApprovalDto>> getTimeSheetApproval(@PathVariable Integer projectManagerId,
			@PathVariable Integer year, @PathVariable Integer accountId) {
		Object employeeId = null;
		try {
			List<TimeSheeApprovalDto> timeSheetHistory = timesheetWeekSummaryService
					.getTimeSheetApproval(projectManagerId, year, accountId);
			log.info("Fetching timeSheetHistory for year: {} accountId: {} ", year, accountId);

			return ResponseEntity.status(HttpStatus.OK).body(timeSheetHistory);
		} catch (Exception e) {

			log.error("Error fetching time sheet history for year: {} accountId: {}", year, accountId, e.getMessage()); // Example:Logging the error
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/getTimeSheeApproval/{projectManagerId}/{month}/{year}/{accountId}/{employeeId}")
	public ResponseEntity<List<TimesheetApprovalSecondDto>> getTimeSheetApprovalByEmployeeId(
			@PathVariable Integer projectManagerId, @PathVariable String month, @PathVariable Integer year,
			@PathVariable Integer accountId, @PathVariable Integer employeeId) {

		try {
			List<TimesheetApprovalSecondDto> timeSheetHistory = timesheetWeekSummaryService
					.getTimeSheetApprovalByEmployeeId(projectManagerId, month, year, accountId, employeeId);
			log.info("Fetching timeSheetHistory for year: {} accountId: {} employeeId: {} month: {}", year, accountId,employeeId,month);
			log.info("details: {}",timeSheetHistory);
			return ResponseEntity.status(HttpStatus.OK).body(timeSheetHistory);
		} catch (Exception e) {

			log.error("Error fetching time sheet history for year: {} accountId: {}  employeeId:{}", year, accountId,
					employeeId, e.getMessage()); // Example: Logging the error

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/all")
	public List<TimesheetApprovalSecondDto> getAllTimesheets(@RequestParam Integer approvedBy) {
	    log.info("Fetching timesheets for employeeId: {}", approvedBy);
	    List<TimesheetApprovalSecondDto> timesheets = timesheetWeekSummaryService.getAllTimesheetsByApprovedBy(approvedBy);
	    log.info("Retrieved {} timesheets.", timesheets.size());
	    return timesheets;
	}

}