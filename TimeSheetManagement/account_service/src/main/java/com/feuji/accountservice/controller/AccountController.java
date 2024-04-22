package com.feuji.accountservice.controller;

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

import com.feuji.accountservice.bean.AccountBean;
import com.feuji.accountservice.bean.EmployeeBean;
import com.feuji.accountservice.dto.AccountDTO;
import com.feuji.accountservice.dto.UpdateAccountDto;
import com.feuji.accountservice.entity.AccountEntity;
import com.feuji.accountservice.exception.SaveUniqueAccountException;
import com.feuji.accountservice.exception.UUIDNotFoundException;
import com.feuji.accountservice.service.AccountService;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/accountSave")
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {
	private static Logger log = LoggerFactory.getLogger(AccountController.class);
	@Autowired
	private AccountService accountService;

	@PostMapping("/save")
	public ResponseEntity<Object> saveAccount(@RequestBody AccountBean accountBean) {
	    try {
	        log.info("Start saving account: {}", accountBean);
	        AccountEntity savedAccount = accountService.saveAccount(accountBean);
	        log.info("Account saved successfully: {}", savedAccount);
	        return ResponseEntity.ok(savedAccount);
	    } catch (SaveUniqueAccountException e) {
	        log.error("Failed to save account due to unique constraint violation: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	    } catch (Exception e) {
	        log.error("An error occurred while saving account: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	
	@GetMapping(path = "/getbyuuid/{uuId}")
	public ResponseEntity<AccountEntity> findByUUId(@PathVariable String uuId) {
	    try {
	        log.info("Fetching account details for UUID: {}", uuId);
	        AccountEntity accountEntity = accountService.findByUUId(uuId);
	        return ResponseEntity.ok(accountEntity);
	    } catch (Exception e) {
	        log.error("An error occurred while fetching account details for UUID {}: {}", uuId, e.getMessage());
	        return ResponseEntity.notFound().build();
	    }
	}

	
	
	@GetMapping(path = "/getAll")
    public ResponseEntity<List<AccountEntity>> getAllAccounts() {
        List<AccountEntity> accounts = accountService.getAllAcount();
        if (!accounts.isEmpty()) {
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
	
	@GetMapping(path="/getEmployee")
	public ResponseEntity<List<EmployeeBean>> getAllEmployees() {
	    try {
	        log.info("Fetching all employees");
	        List<EmployeeBean> beans = accountService.getEmployeeBean();
	        log.info("Retrieved {} employees", beans.size());
	        return ResponseEntity.ok(beans);
	    } catch (Exception e) {
	        log.error("An error occurred while fetching all employees: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	
	@GetMapping(path="/getAccountDto")
	public ResponseEntity<List<AccountDTO>> accountProjectDto() {
	    try {
	        log.info("Fetching account DTOs");
	        List<AccountDTO> accountDto = accountService.accountDto();
	        log.info("Retrieved {} account DTOs", accountDto.size());
	        return ResponseEntity.ok(accountDto);
	    } catch (Exception e) {
	        log.error("An error occurred while fetching account DTOs: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	@GetMapping(path = "/fetchByuuId/{uuId}")
	public ResponseEntity<List<UpdateAccountDto>> fetchByUuID(@PathVariable String uuId) {
	    try {
	        log.info("Fetching UpdateAccountDto with UUID: {}", uuId);
	        List<UpdateAccountDto> updateDta = accountService.fetchByUuID(uuId);
	        log.info("Retrieved UpdateAccountDto: {}", updateDta);
	        return ResponseEntity.ok(updateDta);
	    } catch (Exception e) {
	        log.error("An error occurred while fetching UpdateAccountDto with UUID {}: {}", uuId, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	@PutMapping("/updateAccount")
	public ResponseEntity<AccountEntity> updateAccountProject(@RequestBody AccountBean accountBean) {
	    try {
	        log.info("Updating account in controller");
	        log.info("AccountBean object: {}", accountBean);
	        
	        AccountEntity updateAccount = accountService.updateAccount(accountBean);
	        
	        log.info("Account updated successfully");
	        return ResponseEntity.ok(updateAccount);
	    } catch (IllegalArgumentException e) {
	        log.error("Bad request received while updating account: {}", e.getMessage());
	        return ResponseEntity.badRequest().build();
	    } catch (UUIDNotFoundException e) {
	        log.warn("Account not found while updating account: {}", e.getMessage());
	        return ResponseEntity.notFound().build();
	    } catch (Exception e) {
	        log.error("An error occurred while updating account: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	 
	@DeleteMapping("/deleteAcc/{accountId}")
	public ResponseEntity<AccountEntity> delete(@PathVariable Integer accountId) {
	    try {
	        log.info("Deleting account with ID: {}", accountId);
	        AccountEntity accountEntity = accountService.delete(accountId);
	        log.info("Deleted account with ID: {}", accountId);
	        return ResponseEntity.ok(accountEntity);
	    } catch (Exception e) {
	        log.error("An error occurred while deleting account with ID {}: {}", accountId, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}


}

