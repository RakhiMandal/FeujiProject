package com.feuji.skillgapservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.skillgapservice.bean.SkillCompetencyBean;
import com.feuji.skillgapservice.dto.PaginationDto;
import com.feuji.skillgapservice.dto.TrainigRecommendedEmployeesDto;
import com.feuji.skillgapservice.exception.RecordNotFoundException;
import com.feuji.skillgapservice.service.SkillCompetencyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SkillCompetencyController {

	@Autowired
	private SkillCompetencyService skillCompetencyService;

	/**
	 * Inserts a new SkillCompetencyBean record into the database.
	 *
	 * @param skillCompetencyBean The SkillCompetencyBean object to insert.
	 * @return A ResponseEntity containing the inserted SkillCompetencyBean object
	 *         and HTTP status CREATED.
	 * @return NullPointerException If the insertion of the SkillCompetencyBean
	 *         record fails.
	 */
	@PostMapping(path = "/insert")
	public ResponseEntity<String> saveSkillCompetency(@RequestBody SkillCompetencyBean skillCompetencyBean) {
		log.info("InsertionSkillCompetency Start:Save SkillCompetency Details");
		try {
			skillCompetencyService.saveSkillCompetency(skillCompetencyBean);
			log.info("Insertion End:Saved SkillCompetency Details");
			return new ResponseEntity<String>("record saved successfully", HttpStatus.CREATED);
		} catch (NullPointerException exception) {
			log.info("error occured in saving skillCompetency");
			return new ResponseEntity<String>("error occured in saving record", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Updates all fields of a SkillCompetencyBean record identified by its UUID.
	 *
	 * @param competencyBean The SkillCompetencyBean object containing the updated
	 *                       fields.
	 * @param uuid           The UUID of the SkillCompetencyBean record to update.
	 * @return A ResponseEntity containing the updated SkillCompetencyBean object
	 *         and HTTP status OK.
	 * @return RecordNotFoundException If the SkillCompetencyBean record with the
	 *         provided UUID is not found.
	 * @return NullPointerException If the SkillCompetencyBean is null
	 * 
	 */
	@PostMapping(path = "/update/{uuid}")
	public ResponseEntity<SkillCompetencyBean> updateAllSkillCompetencyRecords(
			@RequestBody SkillCompetencyBean competencyBean, @PathVariable String uuid) {
		log.info("updateAllFeilds Start:Update AllFeilds Details");
		try {
			skillCompetencyService.updateAllSkillCompetencyRecords(competencyBean);
			log.info("updateAllFeilds End:Updated AllFeilds Details");
			return new ResponseEntity<SkillCompetencyBean>(competencyBean, HttpStatus.OK);
		} catch (RecordNotFoundException | NullPointerException exception) {
			log.info("error occured in updating skillCompetency");
			return new ResponseEntity<SkillCompetencyBean>(competencyBean, HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * Retrieves a SkillCompetencyBean record from the database based on its UUID.
	 *
	 * @param uuid The UUID of the SkillCompetencyBean record to retrieve.
	 * @return A ResponseEntity containing the retrieved SkillCompetencyBean object
	 *         and HTTP status FOUND. * @return RecordNotFoundException If the
	 *         SkillCompetencyBean record with the provided UUID is not found.
	 * @return NullPointerException If the SkillCompetencyBean uuid is null
	 */
	@GetMapping(path = "/getByUuid/{uuid}")
	public ResponseEntity<SkillCompetencyBean> findSkillCompetencyByUuid(@PathVariable String uuid) {
		log.info("getByUUID Start:Fetching Uuid Details");
		SkillCompetencyBean bean = null;
		try {
			bean = skillCompetencyService.findSkillCompetencyByUuid(uuid);
			log.info("getByUUID End:Fetched Uuid Details");
			return new ResponseEntity<SkillCompetencyBean>(bean, HttpStatus.FOUND);
		} catch (RecordNotFoundException | NullPointerException exception) {
			log.info("error occured in fetching skillCompetency");
			return new ResponseEntity<SkillCompetencyBean>(bean, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Retrieves a SkillCompetencyBean record from the database based on its skill
	 * ID.
	 *
	 * @param skillId The skill ID of the SkillCompetencyBean record to retrieve.
	 * @return A ResponseEntity containing the retrieved SkillCompetencyBean object
	 *         and HTTP status OK.
	 * @return SkillNotFoundException If the SkillCompetencyBean record with the
	 *         provided skill ID is not found.
	 * @return NullPointerException If the SkillCompetencyBean skillId is null
	 * 
	 */
	@GetMapping(path = "/getBySkillId/{skillId}")
	public ResponseEntity<SkillCompetencyBean> getSkillCompetencyBySkillId(@PathVariable int skillId) {
		log.info("getBySkillId Start:Fetching Skill Details");
		SkillCompetencyBean bean = null;
		try {
			bean = skillCompetencyService.getSkillCompetencyBySkillId(skillId);
			log.info("getBySkillId End:Fetched Skill Details");
			return new ResponseEntity<SkillCompetencyBean>(bean, HttpStatus.OK);
		} catch (RecordNotFoundException | NullPointerException exception) {
			log.info("error occured in fetching skillCompetency");
			return new ResponseEntity<SkillCompetencyBean>(bean, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Retrieves a list of SkillCompetencyBean records from the database based on a
	 * technical category ID.
	 *
	 * @param technicalCatId The technical category ID to filter the
	 *                       SkillCompetencyBean records.
	 * @return A ResponseEntity containing a list of SkillCompetencyBean objects and
	 *         HTTP status OK.
	 * @return RecordNotFoundException If no SkillCompetencyBean records are found
	 *         for the provided technical category ID.
	 * @return NullPointerException If technicalCatId is null
	 */
	@GetMapping("/getRoles/{technicalCatId}")
	public ResponseEntity<List<SkillCompetencyBean>> getRolesBySkillId(@PathVariable int technicalCatId) {
		log.info("getRolesBySkillId Start:Fetching Role Details");
		List<SkillCompetencyBean> beans = null;
		try {
			beans = skillCompetencyService.findSkillCompetencyByTechId(technicalCatId);
			log.info("getRolesBySkillId End:Fetched Role Details");
			return new ResponseEntity<List<SkillCompetencyBean>>(beans, HttpStatus.OK);
		} catch (RecordNotFoundException | NullPointerException exception) {
			log.info("error occured in fetching skillCompetency");
			return new ResponseEntity<List<SkillCompetencyBean>>(beans, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Retrieves a paginated list of employee skills based on an array of skill IDs.
	 *
	 * @param skillId The array of skill IDs to filter the employee skills.
	 * @param page    The page number for pagination.
	 * @param size    The size of each page for pagination.
	 * @return A ResponseEntity containing the paginated list of employee skills and
	 *         HTTP status OK.
	 * @return SkillNotFoundException If no employee skills are found for the
	 *         provided skill IDs.
	 * @return NullPointerException if skillId is null
	 */
	@GetMapping("/fetchBySkillId/{skillId}/{page}/{size}/{roleName}")
	public ResponseEntity<PaginationDto> fetcAllSkillIdWise(@PathVariable int[] skillId, @PathVariable int page,
			@PathVariable int size, @PathVariable String roleName) {
		log.info("fetcAllSkillIdWise Start:Fetching AllSkills Details");
		PaginationDto employeeSkillsBySkillIds = null;
		try {
			employeeSkillsBySkillIds = skillCompetencyService.getAllEmployeeSkillsBySkillIds(skillId, page, size,roleName);
			log.info("fetcAllSkillIdWise End:Fetched AllSkills Details");
			return new ResponseEntity<>(employeeSkillsBySkillIds, HttpStatus.OK);
		} catch (NullPointerException | RecordNotFoundException exception) {
			log.info("error occured in fetching skillCompetency records");
			return new ResponseEntity<>(employeeSkillsBySkillIds, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Retrieves all training recommended employees.
	 *
	 * @param skillIds Array of skill IDs for which training recommended employees
	 *                 are to be retrieved.
	 * @return ResponseEntity containing a list of TrainigRecommendedEmployeesDto
	 *         objects.
	 */
	@GetMapping("/fetchallemployees/{skillIds}")
	public ResponseEntity<List<TrainigRecommendedEmployeesDto>> getAllTrainingRecommendedEmp(
			@PathVariable int[] skillIds) {
		log.info("in controller and skillids: " + skillIds);
		List<TrainigRecommendedEmployeesDto> list = skillCompetencyService.getAllTrainingRecommendedEmp(skillIds);
		log.info(list.size() + " output size");
		return new ResponseEntity<List<TrainigRecommendedEmployeesDto>>(list, HttpStatus.OK);
	}

	public void setSkillCompetencyService(SkillCompetencyService skillCompetencyServiceMock) {
		
	}
}
