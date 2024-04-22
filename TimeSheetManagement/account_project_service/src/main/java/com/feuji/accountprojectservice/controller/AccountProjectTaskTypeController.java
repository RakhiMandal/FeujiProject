package com.feuji.accountprojectservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.accountprojectservice.entity.AccountProjectTaskType;
import com.feuji.accountprojectservice.service.AccountProjectTaskTypeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/accountProjectTaskType")
public class AccountProjectTaskTypeController {
	
	@Autowired
	private AccountProjectTaskTypeService accountProjectTaskTypeService;
	
	@GetMapping("/{id}")
	public ResponseEntity<AccountProjectTaskType> getByAccountProjectTaskTypeId(@PathVariable Integer id) {
	    try {
	        log.info("Fetching AccountProjectTaskType with ID: {}", id);
	        AccountProjectTaskType result = accountProjectTaskTypeService.getByAccountProjectTaskTypeId(id);

	        if (result != null) {
	            log.info("Found AccountProjectTaskType with ID {}: {}", id, result);
	            return ResponseEntity.ok(result);
	        } else {
	            log.warn("AccountProjectTaskType with ID {} not found", id);
	            return ResponseEntity.notFound().build();
	        }
	    } catch (Exception e) {
	        log.error("An error occurred while fetching AccountProjectTaskType with ID {}: {}", id, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

}
