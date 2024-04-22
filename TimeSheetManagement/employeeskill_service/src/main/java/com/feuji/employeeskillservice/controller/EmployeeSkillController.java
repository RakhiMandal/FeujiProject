package com.feuji.employeeskillservice.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.feuji.employeeskillservice.bean.EmployeeSkillBean;
import com.feuji.employeeskillservice.bean.EmployeeSkillGet;
import com.feuji.employeeskillservice.bean.EmployeeUiBean;
import com.feuji.employeeskillservice.exception.InvalidInputException;
import com.feuji.employeeskillservice.exception.NoRecordFoundException;
import com.feuji.employeeskillservice.service.EmployeeSkillService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/employeeskill")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeSkillController {
	@Autowired
	private EmployeeSkillService employeeSkillservice;

	/**
	 * Inserts a list of employee UI beans into the database as employee skill
	 * beans.
	 * 
	 * @param employeeUiBeans The list of employee UI beans to be saved.
	 * @return ResponseEntity<List<EmployeeSkillBean>> A ResponseEntity containing
	 *         the saved employee skill beans and HTTP status OK if successful. and
	 *         HTTP status BAD_REQUEST if failure
	 */
	@PostMapping("/saverecord")
	public ResponseEntity<List<EmployeeSkillBean>> saveEmployeeSkillBean(
			@RequestBody List<EmployeeUiBean> employeeUiBeans) {
		List<EmployeeSkillBean> employeeSkillBeansList = null;
		List<EmployeeSkillBean> savedEmployeeSkillBeansList = new ArrayList<>();
		try {
			log.info("insertEmployeeSkillBean() Start: in EmployeeSkillController");
			try {
				employeeSkillBeansList = employeeSkillservice.convertUiBeanToSkillBean(employeeUiBeans);
			} catch (NullPointerException | NoRecordFoundException e) {
				log.error(e.getMessage());
			}
			savedEmployeeSkillBeansList = employeeSkillservice.saveAll(employeeSkillBeansList);
			log.info("insertEmployeeSkillBean() End: in EmployeeSkillController");
			return new ResponseEntity<>(savedEmployeeSkillBeansList, HttpStatus.OK);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(savedEmployeeSkillBeansList, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Retrieves the list of employee skill beans associated with the given employee
	 * ID.
	 * 
	 * @param employeeId The ID of the employee whose skills are to be retrieved.
	 * @return ResponseEntity<List<EmployeeSkillBean>> A ResponseEntity containing
	 *         the employee skill beans associated with the given employee ID and
	 *         HTTP status OK if successful.
	 */
	@GetMapping("/getEmployeeSkillById/{employeeId}")
	public ResponseEntity<List<EmployeeSkillBean>> getEmployeeSkillById(@PathVariable Integer employeeId) {
		log.info("getEmployeeSkillById Start: in EmployeeSkillController");
		List<EmployeeSkillBean> list = null;
		try {
			list = employeeSkillservice.getEmployeeSkillById(employeeId);
			log.info("getEmployeeSkillById End: in EmployeeSkillController");
			return new ResponseEntity<List<EmployeeSkillBean>>(list, HttpStatus.OK);
		} catch (NoRecordFoundException | NullPointerException e) {
			log.error(e.getMessage());
			return new ResponseEntity<List<EmployeeSkillBean>>(list, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Retrieves the list of employee skill beans associated with the given skill
	 * ID.
	 * 
	 * @param skillId The ID of the skill for which employee skills are to be
	 *                retrieved.
	 * @return ResponseEntity<List<EmployeeSkillBean>> A ResponseEntity containing
	 *         the employee skill beans associated with the given skill ID and HTTP
	 *         status FOUND if successful and NOT_FOUND is failure.
	 */
	@GetMapping("/getBySkillId/{skillId}")
	public ResponseEntity<List<EmployeeSkillBean>> getBySkillId(@PathVariable int skillId) {
		log.info("getBySkillId Start: in EmployeeSkillController");
		List<EmployeeSkillBean> employeeSkillBeanList = null;
		try {
			employeeSkillBeanList = employeeSkillservice.findBySkillId(skillId);
			log.info("getBySkillId End: in EmployeeSkillController");
			return new ResponseEntity<List<EmployeeSkillBean>>(employeeSkillBeanList, HttpStatus.FOUND);
		} catch (IllegalArgumentException | NoRecordFoundException | InvalidInputException e) {
			log.error(e.getMessage());
			return new ResponseEntity<List<EmployeeSkillBean>>(employeeSkillBeanList, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Retrieves all employee skill records associated with the given email.
	 * 
	 * @param email The email of the employee for which skill records are to be
	 *              retrieved.
	 * @return ResponseEntity<List<EmployeeSkillGet>> A ResponseEntity containing
	 *         all employee skill records associated with the given email and HTTP
	 *         status OK if successful and NOT_FOUND is failure.
	 */
	@GetMapping("/getAll/{email}")
	public ResponseEntity<List<EmployeeSkillGet>> getAllEmployeeSkills(@PathVariable String email) {
		log.info("getAll Start: in EmployeeSkillController");
		List<EmployeeSkillGet> allEmployeeSkills = null;
		try {
			allEmployeeSkills = employeeSkillservice.getAllEmployeeSkills(email);
			log.info("getAll End: in EmployeeSkillController");
			return new ResponseEntity<List<EmployeeSkillGet>>(allEmployeeSkills, HttpStatus.OK);

		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<List<EmployeeSkillGet>>(allEmployeeSkills, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Updates the delete status of an employee skill record identified by the given
	 * employee skill ID.
	 * 
	 * @param employeeSkillId The ID of the employee skill record to be deleted.
	 * @return ResponseEntity<String> A ResponseEntity containing a message
	 *         indicating the result of the deletion operation and HTTP status OK if
	 *         successful.
	 * @throws Exception If there is an error during the deletion process, this
	 *                   exception is thrown.
	 */
	@PutMapping("/delete/{employeeSkillId}")
	public ResponseEntity<String> deleteEmployeeSkill(@PathVariable Long employeeSkillId) {
		log.info("delete Start: in EmployeeSkillController");
		String result = null;
		try {
			result = employeeSkillservice.updateDeleteStatus(employeeSkillId);
			log.info("delete End: in EmployeeSkillController");
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (InvalidInputException e) {
			log.error(e.getMessage());
			return new ResponseEntity<String>(result, HttpStatus.BAD_REQUEST);

		}
	}

	/**
	 * Updates the details of an employee skill record identified by the given
	 * employee skill ID.
	 * 
	 * @param set             The updated details of the employee skill record.
	 * @param employeeSkillId The ID of the employee skill record to be updated.
	 * @return ResponseEntity<EmployeeSkillBean> A ResponseEntity containing the
	 *         updated employee skill bean and HTTP status OK if successful.
	 * @throws Exception If there is an error during the update process, this
	 *                   exception is thrown.
	 */
	@PutMapping("/update/{employeeSkillId}")
	public ResponseEntity<EmployeeSkillBean> updateEmployeeSkill(@RequestBody EmployeeSkillGet set,
			@PathVariable Long employeeSkillId) throws Exception {
		log.info("update Start: in EmployeeSkillController");
		EmployeeSkillBean bean = null;
		try {
			bean = employeeSkillservice.updateEmployeeSkill(set, employeeSkillId);
			log.info("update End: in EmployeeSkillController");
			return new ResponseEntity<EmployeeSkillBean>(bean, HttpStatus.OK);
		} catch (InvalidInputException | NoRecordFoundException e) {
			log.error(e.getMessage());
			return new ResponseEntity<EmployeeSkillBean>(bean, HttpStatus.BAD_GATEWAY);
		}
	}

	public void setEmployeeSkillService(EmployeeSkillService employeeSkillService2) {
	}

}
