package com.skillset.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.skillset.dto.GapDto;
import com.skillset.dto.SkillGapDto;
import com.skillset.exception.RecordNotFoundException;
import com.skillset.service.SkillSetService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SkillSetController {

	@Autowired
	private SkillSetService skillSetservice;

	/**
	 * Handles the HTTP GET request to fetch skill details based on email and skill category ID.
	 * @param email The email of the user whose skill details are to be fetched.
	 * @param skillCategoryId The ID of the skill category for which details are to be fetched.
	 * @return An HTTP response containing the list of GapDto objects representing skill details and the corresponding HTTP status code.
	 */

	@GetMapping("/fetch/{email}/{skillCategoryId}")
	public ResponseEntity<List<GapDto>> fetchSkillDetails(@PathVariable String email,
			@PathVariable Integer skillCategoryId) {
		List<GapDto> list = null;
		try {
			list = skillSetservice.fetchSkillDto(email, skillCategoryId);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (RecordNotFoundException e) {
			log.info(e.getMessage());
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
	}

	/**
	* Retrieves the skills of an employee identified by the provided email address.
	* @param email The email address of the employee whose skills are to be retrieved.
	* @return A {@link ResponseEntity} containing a list of {@link SkillGapDto} objects representing the skills of the employee.
	*/
	@GetMapping("/fetch/{email}")
	public ResponseEntity<List<SkillGapDto>> findEmployeeSkills(@PathVariable String email)
	{
		List<SkillGapDto> list = null;
		try {
			list = skillSetservice.findEmployeeSkills(email);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (RecordNotFoundException e) {
			log.info(e.getMessage());
			return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
		}
	}
}
