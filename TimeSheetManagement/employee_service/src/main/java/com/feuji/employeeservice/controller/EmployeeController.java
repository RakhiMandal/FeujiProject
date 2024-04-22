package com.feuji.employeeservice.controller;

import java.util.Collections;
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

import com.feuji.employeeservice.bean.EmployeeBean;
import com.feuji.employeeservice.dto.AddEmployee;
import com.feuji.employeeservice.dto.EmployeeDisplayDto;
import com.feuji.employeeservice.dto.EmployeeDto;
import com.feuji.employeeservice.dto.SaveEmployeeDto;
import com.feuji.employeeservice.dto.SaveEmployeeUserDto;
import com.feuji.employeeservice.dto.UpadteEmployeeDto;
import com.feuji.employeeservice.entity.EmployeeEntity;
import com.feuji.employeeservice.exception.RecordsNotFoundException;
import com.feuji.employeeservice.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/employee")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	/**
	 * Saves an employee along with user details.
	 * 
	 * @param employeeDTO The DTO containing the details of the employee to be
	 *                    saved.
	 * @return ResponseEntity containing the saved EmployeeEntity if successful, or
	 *         an error response if an exception occurs.
	 */
	@PostMapping("/employees")
	public ResponseEntity<EmployeeEntity> saveEmployee(@RequestBody SaveEmployeeUserDto employeeDTO) {
		log.info("Received request to save employee: {}", employeeDTO);
		EmployeeEntity savedEmployee = employeeService.saveEmployeeAndUser(employeeDTO);
		log.info("Saved employee: {}", savedEmployee);

		return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
	}

	/**
	 * Retrieves a list of employees based on the reference type ID.
	 * 
	 * @param referenceTypeId The ID of the reference type.
	 * @return ResponseEntity containing a list of SaveEmployeeDto objects if
	 *         successful, or an error response if an exception occurs.
	 */
	@GetMapping("/referenceTypeId/{referenceTypeId}")
	public ResponseEntity<List<SaveEmployeeDto>> getEmployeesByReferenceTypeId(@PathVariable Integer referenceTypeId) {
		try {
			log.info("Fetching employees by reference type ID: {}", referenceTypeId);
			List<SaveEmployeeDto> employees = employeeService.getByReferenceTypeId(referenceTypeId);
			log.info("Retrieved {} employee(s) by reference type ID: {}", employees.size(), referenceTypeId);
			return ResponseEntity.ok(employees);
		} catch (Exception e) {
			log.error("An error occurred while fetching employees by reference type ID {}: {}", referenceTypeId,
					e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Checks if the provided email is unique.
	 * 
	 * @param email The email to be checked for uniqueness.
	 * @return ResponseEntity containing a boolean value indicating if the email is
	 *         unique (true) or not (false). Returns HTTP status 200 (OK) if the
	 *         email is unique, or HTTP status 400 (BAD REQUEST) with a body of
	 *         "false" if the email already exists.
	 */
	@GetMapping("/checkEmail")
	public ResponseEntity<?> checkEmailUnique(@RequestParam String email) {
		log.info("Checking if email is unique: {}", email);

		boolean isUnique = employeeService.isEmailUnique(email);

		if (isUnique) {
			log.info("Email is unique: {}", email);
			return ResponseEntity.ok("true");
		} else {
			log.warn("Email already exists: {}", email);
			return ResponseEntity.badRequest().body("false");
		}
	}

	/**
	 * Retrieves the reporting manager ID for the given employee ID.
	 * 
	 * @param id The ID of the employee.
	 * @return ResponseEntity containing the EmployeeBean with the reporting manager
	 *         ID if found. Returns HTTP status 200 (OK) if the reporting manager ID
	 *         is found, or HTTP status 404 (NOT FOUND) if not found. If an error
	 *         occurs during the process, returns HTTP status 500 (INTERNAL SERVER
	 *         ERROR).
	 */
	@GetMapping("/getReportingMngIdByEmpId/{id}")
	public ResponseEntity<EmployeeBean> getReportingMngIdByEmpId(@PathVariable Integer id) {
		try {
			log.info("Fetching reporting manager id by employee ID: {}", id);
			EmployeeBean employeeBean = employeeService.getReportingMngIdByEmpId(id);

			if (employeeBean.getReportingManagerId() != null) {
				log.info("Reporting manager ID found: {}", employeeBean.getReportingManagerId());
				return ResponseEntity.ok(employeeBean);
			} else {
				log.warn("Reporting manager ID not found for employee ID: {}", id);
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			log.error("An error occurred while fetching reporting manager ID for employee ID {}: {}", id,
					e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	@GetMapping("/getAll")
	public ResponseEntity<List<EmployeeEntity>> getAllEmployees() {
		try {
			log.info("Fetching all employees");
			List<EmployeeEntity> accountEntities = employeeService.getAllEmployees();
			log.info("Retrieved {} employee(s)", accountEntities.size());
			return ResponseEntity.ok(accountEntities);
		} catch (Exception e) {
			log.error("An error occurred while fetching all employees: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Retrieves an employee by user employee ID.
	 * 
	 * @param userEmpId The user employee ID of the employee to retrieve.
	 * @return List of EmployeeDto objects containing the details of the employee if
	 *         found. Returns an empty list if no employee is found. If an error
	 *         occurs during the process, returns an empty list as a fallback.
	 */
	@GetMapping("/{userEmpId}")
	public List<EmployeeDto> getEmployeeByUserEmpId(@PathVariable("userEmpId") Integer userEmpId) {
		try {
			log.info("Fetching employee by user employee ID: {}", userEmpId);
			List<EmployeeDto> employee = employeeService.getByUserEmpId(userEmpId);
			log.info("Retrieved {} employee(s) by user employee ID: {}", employee.size(), userEmpId);
			return employee;
		} catch (Exception e) {
			log.error("An error occurred while fetching employee by user employee ID {}: {}", userEmpId,
					e.getMessage());
			return Collections.emptyList();
		}
	}

	/**
	 * Retrieves all reporting managers.
	 * 
	 * @return List of AddEmployee objects representing all reporting managers if
	 *         successful. Returns null if an error occurs during the process.
	 */
	@GetMapping("/reporting-managers")
	public List<AddEmployee> getAllReportingManagers() {
		try {
			log.info("Fetching all reporting managers...");
			List<AddEmployee> reportingManagers = employeeService.getAllReportingManager();
			log.info("Successfully fetched all reporting managers.");
			return reportingManagers;
		} catch (Exception e) {
			log.error("Error occurred while fetching reporting managers: {}", e.getMessage());
		}
		return null;
	}
	
	/**
	 * Retrieves details of all employees.
	 * 
	 * @return ResponseEntity containing a list of EmployeeDisplayDto objects
	 *         representing the details of all employees if successful. Returns HTTP
	 *         status 200 (OK) with the list of employee details if successful. If
	 *         an error occurs during the process, returns HTTP status 500 (INTERNAL
	 *         SERVER ERROR).
	 */
	@GetMapping(path = "/getEmployeeDetails")
	public ResponseEntity<List<EmployeeDisplayDto>> getEmployeeDetails() {
		try {
			log.info("Fetching employee details");
			List<EmployeeDisplayDto> updateDta = employeeService.getEmployeeDetails();
			log.info("Retrieved employee details: {}", updateDta);
			return ResponseEntity.ok(updateDta);
		} catch (Exception e) {
			log.error("An error occurred while fetching employee details: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	/**
	 * Searches for employees by first name.
	 * 
	 * @param firstName The first name to search for.
	 * @return ResponseEntity containing a list of EmployeeEntity objects matching
	 *         the search criteria if successful. Returns HTTP status 200 (OK) with
	 *         the list of matching employees if successful.
	 */
	@GetMapping("/search")
	public ResponseEntity<List<EmployeeEntity>> searchEmployees(@RequestParam("firstName") String firstName) {
		log.info("Searching employees by first name: {}", firstName);
		List<EmployeeEntity> employees = employeeService.searchEmployeesByFirstName(firstName);
		log.info("Found {} employees matching the search criteria", employees.size());
		return ResponseEntity.ok(employees);
	}

	/**
	 * Retrieves employee details by UUID.
	 * 
	 * @param uuid The UUID of the employee to retrieve details for.
	 * @return ResponseEntity containing a list of UpadteEmployeeDto objects
	 *         representing the details of the employee if successful. Returns HTTP
	 *         status 200 (OK) with the list of employee details if successful. If
	 *         an error occurs during the process, returns HTTP status 500 (INTERNAL
	 *         SERVER ERROR).
	 */
	@GetMapping(path = "/getEmployeeDetailByUUiD/{uuid}")
	public ResponseEntity<List<UpadteEmployeeDto>> getEmployeeDetailByUUiD(@PathVariable String uuid) {
		try {
			log.info("Fetching employee details for UUID: {}", uuid);
			List<UpadteEmployeeDto> updateDta = employeeService.getEmployeeDetailByUUiD(uuid);
			log.info("Retrieved employee details: {}", updateDta);
			return ResponseEntity.ok(updateDta);
		} catch (Exception e) {
			log.error("An error occurred while fetching employee details for UUID {}: {}", uuid, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Updates an employee.
	 * 
	 * @param employeeBean The EmployeeBean object containing the updated
	 *                     information of the employee.
	 * @return ResponseEntity containing the updated EmployeeEntity object if
	 *         successful. Returns HTTP status 200 (OK) with the updated employee if
	 *         successful. If the request contains invalid data, returns HTTP status
	 *         400 (BAD REQUEST). If the employee to be updated is not found,
	 *         returns HTTP status 404 (NOT FOUND).
	 */
	@PutMapping("/updateEmployee")
	public ResponseEntity<EmployeeEntity> updateEmployee(@RequestBody EmployeeBean employeeBean) {
		try {
			log.info("Updating employee in controller");
			log.info("EmployeeBean object: {}", employeeBean);

			EmployeeEntity updateEmployee = employeeService.updateEmployee(employeeBean);

			log.info("Employee updated successfully");
			return ResponseEntity.ok(updateEmployee);
		} catch (IllegalArgumentException e) {
			log.error("Bad request received while updating employee: {}", e.getMessage());
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			log.error("An error occurred while updating employee: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	/**
	 * Deletes an employee by ID.
	 * 
	 * @param employeeId The ID of the employee to delete.
	 * @return ResponseEntity containing the deleted EmployeeEntity object if
	 *         successful. Returns HTTP status 200 (OK) with the deleted employee if
	 *         successful. If an error occurs during the process, returns HTTP
	 *         status 500 (INTERNAL SERVER ERROR).
	 */
	@DeleteMapping("/deleteEmp/{employeeId}")
	public ResponseEntity<EmployeeEntity> delete(@PathVariable Integer employeeId) {
		try {
			log.info("Deleting employee with ID: {}", employeeId);
			EmployeeEntity employeeEntity = employeeService.delete(employeeId);
			log.info("Deleted employee with ID: {}", employeeId);
			return ResponseEntity.ok(employeeEntity);
		} catch (Exception e) {
			log.error("An error occurred while deleting employee with ID {}: {}", employeeId, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Retrieves an employee by email.
	 * 
	 * @param employeeEmail The email of the employee to retrieve.
	 * @return ResponseEntity containing the EmployeeBean object representing the
	 *         details of the employee if found. Returns HTTP status 200 (OK) with
	 *         the employee details if found. If no employee is found for the
	 *         provided email, returns HTTP status 404 (NOT FOUND).
	 */
	@GetMapping("/getByEmail/{employeeEmail}")
	public ResponseEntity<EmployeeBean> getByEmail(@PathVariable String employeeEmail) {
		log.info("GetByEmail Start:Fetching employee Details" + employeeEmail);
		EmployeeBean employeebean = null;
		try {
			employeebean = employeeService.findByEmail(employeeEmail);
			log.info("GetByEmail End:Fetching employee Details");
			return new ResponseEntity<EmployeeBean>(employeebean, HttpStatus.OK);
		} catch (RecordsNotFoundException e) {
			log.info("No records found for email: " + e.getMessage());
			return new ResponseEntity<EmployeeBean>(employeebean, HttpStatus.NOT_FOUND);
		}
	}

}