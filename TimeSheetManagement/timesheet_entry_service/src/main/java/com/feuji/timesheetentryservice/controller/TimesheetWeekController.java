package com.feuji.timesheetentryservice.controller;

import java.util.List;

import java.util.Map;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.timesheetentryservice.bean.TimesheetWeekBean;
import com.feuji.timesheetentryservice.dto.AccountProjectResourceMappingDto;
import com.feuji.timesheetentryservice.dto.ProjectNameDto;
import com.feuji.timesheetentryservice.dto.ProjectTaskDto;
import com.feuji.timesheetentryservice.dto.ProjectTaskTypeNameDto;
import com.feuji.timesheetentryservice.dto.TimeSheeApprovalDto;
import com.feuji.timesheetentryservice.dto.TimeSheetHistoryDto;
import com.feuji.timesheetentryservice.dto.TimesheetApprovalSecondDto;
import com.feuji.timesheetentryservice.entity.TimesheetWeekEntity;
import com.feuji.timesheetentryservice.repository.TimesheetWeekRepo;
import com.feuji.timesheetentryservice.service.TimesheetWeekService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/timesheet")
@CrossOrigin(origins = "http://localhost:4200")
public class TimesheetWeekController {
	private static Logger log = LoggerFactory.getLogger(TimesheetWeekController.class);

	@Autowired
	TimesheetWeekService timesheetWeekService;
	@Autowired
	TimesheetWeekRepo timesheetWeekRepo;


	@PostMapping("/save")
	public ResponseEntity<TimesheetWeekEntity> saveTimesheetWeek(@RequestBody TimesheetWeekBean timesheetWeekBean) {
	    try {
	        log.info("Saving timesheet week: {}", timesheetWeekBean);
	        TimesheetWeekEntity timesheetWeekEntity = timesheetWeekService.save(timesheetWeekBean);
	        log.info("Timesheet week saved successfully");
	        return new ResponseEntity<>(timesheetWeekEntity, HttpStatus.CREATED);
	    } catch (Exception e) {
	        log.error("An error occurred while saving timesheet week: {}", e.getMessage(), e);
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}



	@GetMapping("/gettimesheetweek/{id}")
	public ResponseEntity<TimesheetWeekEntity> getTimesheetById(@PathVariable Integer id) {
	    try {
	        log.info("Fetching timesheet week with ID: {}", id);
	        TimesheetWeekEntity timesheetWeekEntity = timesheetWeekService.getById(id);
	        log.info("Retrieved timesheet week with ID: {}", id);
	        return new ResponseEntity<>(timesheetWeekEntity, HttpStatus.OK);
	    } catch (Exception e) {
	        log.error("An error occurred while fetching timesheet week with ID {}: {}", id, e.getMessage(), e);
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	@GetMapping("/getproject")
	public ResponseEntity<List<ProjectNameDto>> getProjectBYEMpId(@RequestParam Integer employeeId,
	        @RequestParam Integer accountId) {
	    try {
	        log.info("Fetching projects for employee ID: {}", employeeId);
	        List<ProjectNameDto> projectNameByEmpId = timesheetWeekService.getProjectNameByEmpId(employeeId, accountId);
	        log.info("Retrieved projects for employee ID: {}", employeeId);
	        return new ResponseEntity<>(projectNameByEmpId, HttpStatus.OK);
	    } catch (Exception e) {
	        log.error("An error occurred while fetching projects for employee ID {}: {}", employeeId, e.getMessage(), e);
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	@GetMapping("/getprojecttasktype")
	public ResponseEntity<List<ProjectTaskTypeNameDto>> getProjectTaskType(@RequestParam Integer employeeId,
	        @RequestParam Integer accountProjectId) {
	    try {
	        log.info("Fetching project task types for employee ID: {}", employeeId);
	        List<ProjectTaskTypeNameDto> projectTaskType = timesheetWeekService.getProjectTaskTypeName(employeeId,
	                accountProjectId);
	        log.info("Retrieved project task types for employee ID: {}", employeeId);
	        return new ResponseEntity<>(projectTaskType, HttpStatus.OK);
	    } catch (Exception e) {
	        log.error("An error occurred while fetching project task types for employee ID {}: {}", employeeId, e.getMessage(), e);
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	
	@GetMapping("/getprojecttask")
	public ResponseEntity<List<ProjectTaskDto>> getProjectTask(@RequestParam Integer taskTypeId) {
	    try {
	        log.info("Fetching project tasks for task type ID: {}", taskTypeId);
	        List<ProjectTaskDto> projectTask = timesheetWeekService.getProjectTask(taskTypeId);
	        log.info("Retrieved project tasks for task type ID: {}", taskTypeId);
	        return new ResponseEntity<>(projectTask, HttpStatus.OK);
	    } catch (Exception e) {
	        log.error("An error occurred while fetching project tasks for task type ID {}: {}", taskTypeId, e.getMessage(), e);
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	/**
	 * Handles the HTTP GET request to retrieve account details based on the user's
	 * employee ID.
	 *
	 * @param userEmpId The employee ID of the user for whom account details are
	 *                  being retrieved.
	 * @return ResponseEntity containing a list of AccountProjectResourceMappingDto
	 *         objects representing account details and HTTP status code.
	 */
	@GetMapping(path = "/getaccountdetails")
	public ResponseEntity<List<AccountProjectResourceMappingDto>> findAccountNameByUserEmpId(
	        @RequestParam Integer userEmpId) {
	    try {
	        List<AccountProjectResourceMappingDto> accountProjectResourceMappingDtos = timesheetWeekService
	                .findAccountNameByUserEmpId(userEmpId);

	        log.info("Fetching accountProjectResourceMappingDtos for userEmpId {}: {}", userEmpId,
	                accountProjectResourceMappingDtos);

	        return ResponseEntity.status(HttpStatus.OK).body(accountProjectResourceMappingDtos);
	    } catch (Exception e) {
	        log.error("Error fetching account details for userEmpId {}: {}", userEmpId, e.getMessage(), e);
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	/**
	 * Handles the HTTP GET request to retrieve time sheet history for a specific
	 * month, year, account, and employee.
	 *
	 * @param month       The month for which time sheet history is being retrieved
	 *                    (e.g., "January", "February").
	 * @param year        The year for which time sheet history is being retrieved.
	 * @param accountName The name of the account for which time sheet history is
	 *                    being retrieved.
	 * @param employeeId  The ID of the employee for whom time sheet history is
	 *                    being retrieved.
	 * @return ResponseEntity containing a list of TimeSheetHistoryDto objects
	 *         representing time sheet history and HTTP status code.
	 */
	@GetMapping(path = "/gettimeSheetHistory/{month}/{year}/{accountName}/{employeeId}")
	public ResponseEntity<List<TimeSheetHistoryDto>> timeSheetHistoryDto(@PathVariable String month,
			@PathVariable int year, @PathVariable String accountName, @PathVariable int employeeId) {
		try {

			List<TimeSheetHistoryDto> timeSheetHistory = timesheetWeekService.timeSheetHistoryDto(month, year,
					accountName, employeeId);

			log.info("Fetching timeSheetHistory for {} {} accountName: {} employeeId: {}", month, year, accountName,
					employeeId);

			return ResponseEntity.status(HttpStatus.OK).body(timeSheetHistory);
		} catch (Exception e) {

			log.error("Error fetching time sheet history for {} {} accountName: {} employeeId: {}: {}", month, year,
					accountName, employeeId, e.getMessage());

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Handles the HTTP GET request to retrieve time sheet history for a specific
	 * year, account, and employee.
	 *
	 * @param year        The year for which time sheet history is being retrieved.
	 * @param accountName The name of the account for which time sheet history is
	 *                    being retrieved.
	 * @param employeeId  The ID of the employee for whom time sheet history is
	 *                    being retrieved.
	 * @return ResponseEntity containing a list of TimeSheetHistoryDto objects
	 *         representing time sheet history and HTTP status code.
	 */

	@GetMapping(path = "/gettimeSheetHistory/{year}/{accountName}/{employeeId}")
	public ResponseEntity<List<TimeSheetHistoryDto>> getTimeSheetHistoryByYear(@PathVariable int year,
			@PathVariable String accountName, @PathVariable int employeeId) {
		try {
			List<TimeSheetHistoryDto> timeSheetHistory = timesheetWeekService.getTimeSheetHistoryByYear(year,
					accountName, employeeId);

			log.info("Fetching timeSheetHistory for year: {} accountName: {} employeeId: {}", year, accountName,
					employeeId);

			return ResponseEntity.status(HttpStatus.OK).body(timeSheetHistory);
		} catch (Exception e) {

			log.error("Error fetching time sheet history for year: {} accountName: {} employeeId: {}: {}", year,
					accountName, employeeId, e.getMessage()); // Example: Logging the error

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Handles the HTTP GET request to retrieve time sheet history for a specific
	 * month, year, and employee, grouped by account.
	 *
	 * @param month      The month for which time sheet history is being retrieved
	 *                   (e.g., "January", "February").
	 * @param year       The year for which time sheet history is being retrieved.
	 * @param employeeId The ID of the employee for whom time sheet history is being
	 *                   retrieved.
	 * @return ResponseEntity containing a list of TimeSheetHistoryDto objects
	 *         representing time sheet history grouped by account and HTTP status
	 *         code.
	 */
	@GetMapping(path = "/gettimeSheetHistory/getAccountByMonthAndYear/{month}/{year}/{employeeId}")
	public ResponseEntity<List<TimeSheetHistoryDto>> getAccountByMonthAndYear(@PathVariable String month,
			@PathVariable int year, @PathVariable int employeeId) {
		try {

			List<TimeSheetHistoryDto> timeSheetHistory = timesheetWeekService.getAccountByMonthAndYear(month, year,
					employeeId);

			log.info("Fetching timeSheetHistory for {} {} employeeId: {}", month, year, employeeId);

			return ResponseEntity.status(HttpStatus.OK).body(timeSheetHistory);
		} catch (Exception e) {

			log.error("Error fetching time sheet history for {} {} employeeId: {}: {}", month, year, employeeId,
					e.getMessage());

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Handles the HTTP GET request to retrieve the years for which time sheet
	 * history is available for a specific employee.
	 *
	 * @param employeeId The ID of the employee for whom years are being retrieved.
	 * @return ResponseEntity containing a list of years for which time sheet
	 *         history is available and HTTP status code.
	 */
	@GetMapping(path = "/gettimeSheetHistory/getYears/{employeeId}")
	public ResponseEntity<List<Integer>> getYear(@PathVariable int employeeId) {
		try {

			List<Integer> years = timesheetWeekService.getYear(employeeId);

			log.info("Fetching Years for employeeId: {}", employeeId);

			return ResponseEntity.status(HttpStatus.OK).body(years);
		} catch (Exception e) {

			log.error("Error fetching years for employeeId {}: {}", employeeId, e.getMessage());

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	

	
	@PutMapping("update/{employeeId}/{accountId}/{weekStartDate}")
	public ResponseEntity<String> updateTimesheetStatus(@PathVariable Integer employeeId,
	        @PathVariable Integer accountId, @PathVariable String weekStartDate) {
	    try {
	        log.info("Updating timesheet status for employeeId: {}, accountId: {}, weekStartDate: {}", 
	            employeeId, accountId, weekStartDate);
	        
	        String result = timesheetWeekService.updateTimesheetStatus(employeeId, accountId, weekStartDate);
	        
	        return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	        log.error("Error updating timesheet status for employeeId: {}, accountId: {}, weekStartDate: {}: {}", 
	            employeeId, accountId, weekStartDate, e.getMessage(), e);
	        
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	@PutMapping("reject/{employeeId}/{accountId}/{weekStartDate}")
	public ResponseEntity<String> rejectedTimesheet(@PathVariable Integer employeeId,
	        @PathVariable Integer accountId, @PathVariable String weekStartDate) {
	    try {
	        log.info("Rejecting timesheet for employeeId: {}, accountId: {}, weekStartDate: {}", 
	            employeeId, accountId, weekStartDate);
	        
	        String result = timesheetWeekService.rejectedTimesheet(employeeId, accountId, weekStartDate);
	        
	        return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	        log.error("Error rejecting timesheet for employeeId: {}, accountId: {}, weekStartDate: {}: {}", 
	            employeeId, accountId, weekStartDate, e.getMessage(), e);
	        
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	@GetMapping(path = "/gettimeSheetHistory/bymonth/{month}/{year}/{accountId}")
	public ResponseEntity<List<TimesheetApprovalSecondDto>> timeSheetHistoryDto(@PathVariable String month,
			@PathVariable int year, @PathVariable Integer accountId) {
		try {

			List<TimesheetApprovalSecondDto> timeSheetHistory = timesheetWeekService.timeSheetHistoryDto(month, year,
					accountId);

			log.info("Fetching timeSheetHistory for month: {} year: {} account id: {}", month, year, accountId
					);

			return ResponseEntity.status(HttpStatus.OK).body(timeSheetHistory);
		} catch (Exception e) {

			log.error("Error Fetching timeSheetHistory for month: {} year: {} account id: {}", month, year,
					accountId,  e.getMessage());

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
