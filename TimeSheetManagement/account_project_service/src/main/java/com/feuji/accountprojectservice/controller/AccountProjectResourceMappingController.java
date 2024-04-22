package com.feuji.accountprojectservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.accountprojectservice.dto.AccountProjectResourceMappingDto;
import com.feuji.accountprojectservice.service.AccountProjectResourceMappingService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/AccountProjectResourceMapping")
@CrossOrigin(origins = "http://localhost:4200")
public class AccountProjectResourceMappingController {
	@Autowired
	private AccountProjectResourceMappingService accountProjectResourceMappingService;
	@GetMapping(path = "/accountdetails/{userEmpId}")
	public ResponseEntity<List<AccountProjectResourceMappingDto>> findAccountNameByUserEmpId(@PathVariable Integer userEmpId) {
	    try {
	        List<AccountProjectResourceMappingDto> accountProjectResourceMappingDtos = accountProjectResourceMappingService.findAccountNameByUserEmpId(userEmpId);
	        
	        log.info("Fetching accountProjectResourceMappingDtos: {}", accountProjectResourceMappingDtos);
	        return ResponseEntity.ok().body(accountProjectResourceMappingDtos);
	    } catch (Exception e) {
	        log.error("An error occurred while fetching account details by userEmpId {}: {}", userEmpId, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

}