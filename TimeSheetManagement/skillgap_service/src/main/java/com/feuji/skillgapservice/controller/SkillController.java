package com.feuji.skillgapservice.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.skillgapservice.bean.SkillBean;
import com.feuji.skillgapservice.commonconstants.CommonConstants;
import com.feuji.skillgapservice.dto.SkillNamesDto;
import com.feuji.skillgapservice.entity.SkillEntity;
import com.feuji.skillgapservice.exception.RecordNotFoundException;
import com.feuji.skillgapservice.exception.SkillNotFoundException;
import com.feuji.skillgapservice.service.SkillService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/skill")
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")

public class SkillController {
	@Autowired
	private SkillService skillService;

	/**
	 * Saves a new SkillBean record.
	 * 
	 * @param bean The SkillBean object to save.
	 * @return A ResponseEntity containing the saved SkillBean object and HTTP
	 *         status CREATED.
	 * @return NullPointerException If the SkillBean is null .
	 * @return SkillNotFoundException if skill record is not found .
	 */
	@PostMapping("/insert")
	public ResponseEntity<SkillBean> saveSkill(@RequestBody SkillBean skillBean) {
		log.info("Save Start:in SkillController");
		SkillBean resultSkillBean = null;
		try {
			resultSkillBean = skillService.saveSkill(skillBean);
			log.info("Save End:in SkillController");
			return new ResponseEntity<SkillBean>(resultSkillBean, HttpStatus.CREATED);
		} catch (NullPointerException | SkillNotFoundException exception) {
			log.error(exception.getMessage());
			return new ResponseEntity<SkillBean>(resultSkillBean, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Updates the details of a SkillBean record.
	 *
	 * @param uuid      The UUID of the SkillBean record to update.
	 * @param skillBean The updated SkillBean object.
	 * @return A ResponseEntity containing the updated SkillBean object and HTTP
	 *         status OK.
	 * @return SkillNotFoundException If the SkillBean record with the provided UUID
	 *         is not found.
	 * @return NullPointerException if the skillbean is null.
	 */
	@PutMapping("/updateSkill")
	public ResponseEntity<SkillBean> updateSkillDetails(@RequestParam String uuid, @RequestBody SkillBean skillBean) {
		log.info("Update Start :in SkillController");
		SkillBean updatedSkill = null;
		try {
			skillBean.setUuid(uuid);
			updatedSkill = skillService.updateSkillDetails(skillBean);
			log.info("Update End:in SkillController");
			return ResponseEntity.ok(updatedSkill);
		} catch (NullPointerException | SkillNotFoundException exception) {
			log.error(exception.getMessage());
			return new ResponseEntity<SkillBean>(updatedSkill, HttpStatus.NOT_MODIFIED);
		}
	}

	/**
	 * Retrieves a list of SkillBean records from the database based on a technical
	 * category ID.
	 *
	 * @param categoryId The ID of the technical category to filter the SkillBean
	 *                   records.
	 * @return A ResponseEntity containing a list of SkillBean objects and HTTP
	 *         status OK.
	 * @return SkillNotFoundException If no SkillBean records are found for the
	 *         provided technical category ID.
	 * @return NullPointerException If Skill TechnicalCtegoryId is null.
	 */
	@GetMapping(path = "/getAll/{categoryId}")
	public ResponseEntity<List<SkillBean>> getSkillsByTechCategoryId(@PathVariable int categoryId) {
		log.info("GetAll Start:in SkillController");
		List<SkillBean> skillBeanList = null;
		try {
			skillBeanList = skillService.getSkillsByTechCategoryId(categoryId);
			log.info("GetAll End:in SkillController");
			return new ResponseEntity<>(skillBeanList, HttpStatus.OK);
		} catch (SkillNotFoundException | NullPointerException exception) {
			log.error(exception.getMessage());
			return new ResponseEntity<>(skillBeanList, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Retrieves a SkillBean record from the database based on its ID.
	 *
	 * @param skillId The ID of the SkillBean record to retrieve.
	 * @return A ResponseEntity containing the retrieved SkillBean object and HTTP
	 *         status FOUND.
	 * @return SkillNotFoundException If the SkillBean record with the provided ID
	 *         is not found.
	 * @return NullPointerException If SkillId is null.
	 */
	@GetMapping(path = "/getBySkillId/{skillId}")
	public ResponseEntity<SkillBean> getSkillBySkillId(@PathVariable int skillId) {
		log.info("GetBySkillId Start:in SkillController");
		SkillBean bean = null;
		try {
			bean = skillService.getSkillBySkillId(skillId);
			log.info("GetBySkillId End:in SkillController");
			return new ResponseEntity<>(bean, HttpStatus.FOUND);
		} catch (SkillNotFoundException | NullPointerException exception) {
			log.error(exception.getMessage());
			return new ResponseEntity<>(bean, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Retrieves a list of SkillNamesDto objects from the database based on an array
	 * of skill IDs.
	 *
	 * @param skillIds An array of skill IDs to retrieve SkillNamesDto objects.
	 * @return A ResponseEntity containing a list of SkillNamesDto objects and HTTP
	 *         status OK.
	 * @return RecordNotFoundException If no records are found for the provided
	 *         skill IDs.
	 * @return NullPointerException If SkillIds are null.
	 */
	@GetMapping(path = "/getSkillNames/{skillIds}")
	public ResponseEntity<List<SkillNamesDto>> getSkillNamesBySkillId(@PathVariable int[] skillIds) {
		log.info("GetSkillNames Start:in SkillController");
		List<SkillNamesDto> skills = new ArrayList<>();
		try {
			skills = skillService.getSkillNamesBySkillId(skillIds);
			log.info("GetSkillNames End:in SkillController");
			return new ResponseEntity<List<SkillNamesDto>>(skills, HttpStatus.OK);
		} catch (SkillNotFoundException | NullPointerException | RecordNotFoundException exception) {
			log.error(exception.getMessage());
			return new ResponseEntity<List<SkillNamesDto>>(skills, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Retrieves a SkillBean record from the database based on its skillUuid.
	 *
	 * @param skillUuid The UUID of the SkillBean record to retrieve.
	 * @return A ResponseEntity containing the retrieved SkillBean object and HTTP
	 *         status FOUND.
	 * @return SkillNotFoundException If the SkillBean record with the provided
	 *         skillUuid is not found.
	 * @return NullPointerException if skill uuid id null.
	 */
	@GetMapping(path = "/getByUuid/{skillUuid}")
	public ResponseEntity<SkillBean> getSkillBean(@RequestParam String skillUuid) {
		log.info("getByUuid Start:in SkillController");
		SkillBean skillBean = null;
		try {
			skillBean = skillService.getSkillByUuid(skillUuid);
			log.info("getByUuid End:in SkillController");
			return new ResponseEntity<>(skillBean, HttpStatus.FOUND);
		} catch (SkillNotFoundException | NullPointerException exception) {
			log.error(exception.getMessage());
			return new ResponseEntity<>(skillBean, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Updates the status of skills.
	 *
	 * @param skillIds The IDs of the skills to update.
	 * @param status   The new status for the skills.
	 * @return ResponseEntity indicating the success of the operation.
	 * @throws RecordNotFoundException If no records are found for the given skill
	 *                                        IDs.  
	 */
	@PutMapping("/updateStatus")
	public ResponseEntity<SkillEntity> updateSkillStatus(@RequestParam("skillIds") List<Integer> skillIds,
			@RequestParam("status") List<Byte> status) throws RecordNotFoundException {
		if (skillIds.isEmpty() || status.isEmpty()) {
			throw new IllegalArgumentException("Invalid request format");
		}

		try {
			List<SkillEntity> updateStatusBySkillId = skillService.updateStatusBySkillId(skillIds, status);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			throw new RecordNotFoundException("No record found with this skillId :" + skillIds);
		}

	}

	/**
	 * Retrieves skills by technical category ID for employees.
	 *
	 * @param categoryId The ID of the technical category for which skills are to be
	 *                   retrieved.
	 * @return ResponseEntity containing a list of SkillBean objects.  
	 */
	@GetMapping(path = "/getAllForEmployee/{categoryId}")
	public ResponseEntity<List<SkillBean>> getSkillsByTechCategoryIdForEmployee(@PathVariable int categoryId) {
		log.info("GetAll Start:in SkillController");
		List<SkillBean> skillBeanList = null;
		try {
			skillBeanList = skillService.getSkillsByTechCategoryIdForEmployee(categoryId);
			log.info("GetAll End:in SkillController");
			return new ResponseEntity<>(skillBeanList, HttpStatus.OK);
		} catch (SkillNotFoundException | NullPointerException exception) {
			log.error(exception.getMessage());
			return new ResponseEntity<>(skillBeanList, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Deletes a skill by its sub-skill category ID.
	 *
	 * @param subSkillCategoryId The ID of the sub-skill category associated with
	 *                           the skill to be deleted.
	 * @return ResponseEntity indicating the success of the operation.  
	 */
	@PutMapping("deleteSkill/{subSkillCategoryId}")
	public ResponseEntity<SkillEntity> deleteSkillBySubSkillCategoryId(@PathVariable Long subSkillCategoryId) {
		List<SkillEntity> deletedSkills = null;
		deletedSkills = skillService.deleteSkillBySubSkillCategoryId(subSkillCategoryId, (byte) CommonConstants.TRUE);
		if (deletedSkills.size() > 0) {
			return new ResponseEntity<SkillEntity>(deletedSkills.get(CommonConstants.FALSE), HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<SkillEntity>(HttpStatus.OK);
		}
	}

	public void setSkillService(SkillService skillService2) {
		
	}

}
