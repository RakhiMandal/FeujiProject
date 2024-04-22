package com.feuji.timesheetentryservice.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.timesheetentryservice.dto.EmployeeDataDto;
import com.feuji.timesheetentryservice.dto.SaveAndEditRecordsDto;
import com.feuji.timesheetentryservice.dto.WeekAndDayDto;
import com.feuji.timesheetentryservice.entity.TimesheetDayEntity;
import com.feuji.timesheetentryservice.entity.TimesheetWeekEntity;
import com.feuji.timesheetentryservice.service.TimeSheetDataService;
import com.feuji.timesheetentryservice.service.TimesheetWeekService;
import com.feuji.timesheetentryservice.util.Constants;
import com.feuji.timesheetentryservice.util.EmailSender;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/timesheetdata")
@CrossOrigin(origins = "http://localhost:4200")
public class TimesheetDataController {
	@Autowired
	TimeSheetDataService timeSheetDataService;

	@Autowired
	TimesheetWeekService timesheetWeekService;

	@Autowired
	EmailSender emailsender;

	/**
	 * Handles the HTTP POST request to save a list of timesheet data for multiple
	 * weeks.
	 *
	 * @param timesheetData A list of WeekAndDayDataBean objects containing the
	 *                      timesheet data for each week.
	 * @return ResponseEntity containing the saved TimesheetWeekEntity objects and
	 *         HTTP status code.
	 */
	@PostMapping("/saveedit/{weekStartDate}")
	public ResponseEntity<List<TimesheetWeekEntity>> saveOrUpdateRecords(
			@RequestBody SaveAndEditRecordsDto weekAndDayDataBeans, @PathVariable String weekStartDate) {
		System.out.println(weekAndDayDataBeans);
		System.out.println(weekStartDate);
		try {
			log.info("Saving or updating records for week starting from: {}", weekStartDate);
			List<TimesheetWeekEntity> saveOrUpdate = timeSheetDataService.saveOrUpdate(weekAndDayDataBeans,
					weekStartDate);
			log.info("Records saved or updated successfully");
			return ResponseEntity.ok(saveOrUpdate);
		} catch (Exception e) {
			log.error("An error occurred while saving or updating records for week starting from {}: {}", weekStartDate,
					e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Handles the HTTP GET request to retrieve the timesheet data for a specific
	 * week and employee.
	 *
	 * @param accountId     The ID of the account associated with the timesheet
	 *                      data.
	 * @param employeeId    The ID of the employee for whom the timesheet data is
	 *                      being retrieved.
	 * @param weekStartDate The start date of the week for which timesheet data is
	 *                      requested (in the format "YYYY-MM-DD").
	 * @param weekEndDate   The end date of the week for which timesheet data is
	 *                      requested (in the format "YYYY-MM-DD").
	 * @return ResponseEntity containing a list of WeekAndDayDto objects
	 *         representing the timesheet data and HTTP status code.
	 */
	@GetMapping("/getallweekdayData/{accountId}/{employeeId}/{weekStartDate}/{weekEndDate}")
	public ResponseEntity<List<WeekAndDayDto>> getAllWeekdayData(@PathVariable Integer accountId,
			@PathVariable Integer employeeId, @PathVariable String weekStartDate, @PathVariable String weekEndDate) {
		try {
			log.info("Fetching all weekday data for account {}, employee {} from {} to {}", accountId, employeeId,
					weekStartDate, weekEndDate);

			List<WeekAndDayDto> weekdayData = timeSheetDataService.fetchAllWeekDayRecordsById(accountId, employeeId,
					weekStartDate, weekEndDate);

			log.info("Retrieved {} weekday data records", weekdayData.size());
			return ResponseEntity.ok(weekdayData);
		} catch (Exception e) {
			log.error("An error occurred while fetching weekday data: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Handles the HTTP POST request to delete a timesheet day record.
	 *
	 * @param weekAndDayDto A WeekAndDayDto object containing the data for the
	 *                      timesheet day record to be deleted.
	 * @return ResponseEntity containing a list of TimesheetDayEntity objects
	 *         representing the deleted record and HTTP status code.
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<List<TimesheetDayEntity>> deleteRecord(@RequestBody WeekAndDayDto weekAndDayDto) {
		try {
			log.info("Deleting record: {}", weekAndDayDto);
			List<TimesheetDayEntity> deletedRecords = timeSheetDataService.deleteDayRecord(weekAndDayDto);

			if (deletedRecords == null || deletedRecords.isEmpty()) {
				log.warn("No records deleted");
				return ResponseEntity.notFound().build();
			}

			log.info("Deleted {} records", deletedRecords.size());
			return ResponseEntity.ok(deletedRecords);
		} catch (Exception e) {
			log.error("An error occurred while deleting record: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Handles the HTTP POST request to submit a timesheet for a specific week.
	 *
	 * @param weekStartDate   The start date of the week for which the timesheet is
	 *                        being submitted (in the format "YYYY-MM-DD").
	 * @param timesheetStatus The status of the submitted timesheet.
	 * @return ResponseEntity containing a list of TimesheetWeekEntity objects
	 *         representing the submitted timesheet and HTTP status code.
	 */
	@PostMapping("submitAction")
	public ResponseEntity<List<TimesheetWeekEntity>> submitTimesheet(@RequestParam Integer employeeId,
			@RequestParam Integer accountId, @RequestParam String weekStartDate) {
		try {
			log.info("Submitting timesheet for week starting on {} with status: {}", weekStartDate,
					Constants.TIME_SHEET_STATUS_SAVED);

			List<TimesheetWeekEntity> submittingTimesheet = timeSheetDataService.submittingTimesheet(weekStartDate,
					Constants.TIME_SHEET_STATUS_SUBMITTED, accountId, employeeId);

			if (submittingTimesheet != null && !submittingTimesheet.isEmpty()) {
				this.sendEmails(employeeId, accountId, weekStartDate);
				return new ResponseEntity<>(submittingTimesheet, HttpStatus.OK);
			} else {
				log.error("Failed to submit timesheet for week starting on {}", weekStartDate);
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error("An error occurred while submitting timesheet for week starting on {}: {}", weekStartDate,
					e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<List<TimesheetWeekEntity>> sendEmails(Integer employeeId, Integer accountId,
			String weekStartDate) {
		try {
			log.info("Submitting timesheet for employeeId : {} and account Id: {} for week starting on: {}", employeeId,
					accountId, weekStartDate);

			List<EmployeeDataDto> list = timeSheetDataService.getEmployeeDetailsByIdAndAccountId(accountId, employeeId);

			log.info("List size: {}", list.size());
			for (EmployeeDataDto emp : list) {
				log.info("Sending email to: {}", emp.getEmail());
				emailsender.sendSimpleEmail(emp.getEmail(), "Request for Timesheet Approval",
						this.composeBody(emp, weekStartDate));
				log.info("Email sent to: {}", emp.getEmail());
			}

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			log.error("An error occurred while sending emails for timesheet approval: {}", e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private String composeBody(EmployeeDataDto emp, String weekStartDate) {
		try {
			log.info("composeBody for employee: {}", emp.getEmail());

			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDate startDate = LocalDate.parse(weekStartDate, dateTimeFormatter);
			LocalDate endDate = startDate.plusDays(6);

			String emailBody = "Dear " + emp.getFirstName() + " " + emp.getLastName() + ",\n\n"
					+ "The employee has submitted the timesheet for the period from: " + startDate.toString() + " to: "
					+ endDate.toString() + " Please review and approve it accordingly.";

			return emailBody;
		} catch (Exception e) {
			log.error("An error occurred while composing email body for employee: {}", emp.getEmail(), e);
			return null;
		}
	}

}
