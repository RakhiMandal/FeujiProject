package com.feuji.timesheetentryservice.controller;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RestController;

import com.feuji.timesheetentryservice.entity.HolidayEntity;
import com.feuji.timesheetentryservice.exception.HolidayDateExistsException;
import com.feuji.timesheetentryservice.exception.HolidayNameAndHolidayDateExistException;
import com.feuji.timesheetentryservice.exception.HolidayNameExistException;
import com.feuji.timesheetentryservice.exception.HolidayNotFoundException;
import com.feuji.timesheetentryservice.service.HolidayService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/holiday")
@CrossOrigin(origins = "http://localhost:4200")
public class HolidayController {
	private static Logger log = LoggerFactory.getLogger(HolidayController.class);
	@Autowired
	private HolidayService holidayService;

	@PostMapping(path="/save")
	public ResponseEntity<HolidayEntity> saveHoliday(@RequestBody HolidayEntity holidayEntity) {
	    try {
	        log.info("Saving holiday details: {}", holidayEntity);
	        holidayService.save(holidayEntity);
	        log.info("Holiday details saved successfully");
	        return ResponseEntity.status(HttpStatus.CREATED).body(holidayEntity);
	    } catch (HolidayNameAndHolidayDateExistException e) {
	        log.error("Holiday name and date already exist: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.CONFLICT).build();
	    } catch (HolidayDateExistsException|HolidayNameExistException e) {
	        log.error("Holiday date or name already exist: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	    } catch (Exception e) {
	        log.error("An error occurred while saving holiday details: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}


	@GetMapping(path = "/{holidayId}")
	public ResponseEntity<HolidayEntity> getHolidayById(@PathVariable Integer holidayId) {
	    try {
	        log.info("Fetching holiday details for ID: {}", holidayId);
	        HolidayEntity holidayEntity = holidayService.get(holidayId);
	        log.info("Retrieved holiday details: {}", holidayEntity);
	        return ResponseEntity.ok(holidayEntity);
	    } catch (HolidayNotFoundException e) {
	        log.error("Holiday not found for ID {}: {}", holidayId, e.getMessage());
	        return ResponseEntity.notFound().build();
	    } catch (Exception e) {
	        log.error("An error occurred while fetching holiday details for ID {}: {}", holidayId, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}


	@PutMapping(path="/update")
	public ResponseEntity<String> updateHoliday(@RequestBody HolidayEntity holidayEntity) {
	    try {
	        log.info("Starting holiday update: {}", holidayEntity);
	        holidayService.update(holidayEntity);
	        log.info("Updated holiday details: {}", holidayEntity);
	        return ResponseEntity.ok("Updated");
	    } catch (HolidayNotFoundException e) {
	        log.error("Holiday not found: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } catch (Exception e) {
	        log.error("An error occurred while updating holiday: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while updating holiday");
	    }
	}

	@DeleteMapping("delete/{holidayId}")
	public ResponseEntity<HolidayEntity> deleteHoliday(@PathVariable Integer holidayId) {
	    try {
	        log.info("Deleting holiday with ID: {}", holidayId);
	        HolidayEntity holidayEntity = holidayService.delete(holidayId);
	        log.info("Deleted holiday with ID: {}", holidayId);
	        return ResponseEntity.ok(holidayEntity);
	    } catch (Exception e) {
	        log.error("An error occurred while deleting holiday with ID {}: {}", holidayId, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	
	@GetMapping("/getWeekHolidaysDayIds/{startweekofDate}")
	public ResponseEntity<List<Integer>> getWeekHolidaysDayIds(@PathVariable String startweekofDate) {
	    try {
	        log.info("Fetching holiday day IDs for week starting from: {}", startweekofDate);
	        List<Integer> holidayList = holidayService.getWeekHolidaysDayIds(startweekofDate);
	        log.info("Retrieved holiday day IDs for week starting from {}: {}", startweekofDate, holidayList);
	        return ResponseEntity.ok(holidayList);
	    } catch (Exception e) {
	        log.error("An error occurred while fetching holiday day IDs for week starting from {}: {}", startweekofDate, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
	    }
	}


	@GetMapping("/getHolidayByYear/{year}")
	public ResponseEntity<List<HolidayEntity>> getHolidayByYear(@PathVariable int year) {
	    try {
	        log.info("Fetching holidays for year: {}", year);
	        List<HolidayEntity> holidayList = holidayService.getHolidayByYear(year);
	        log.info("Retrieved {} holidays for year {}", holidayList.size(), year);
	        return ResponseEntity.ok(holidayList);
	    } catch (Exception e) {
	        log.error("An error occurred while fetching holidays for year {}: {}", year, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
	    }
	}

	

}