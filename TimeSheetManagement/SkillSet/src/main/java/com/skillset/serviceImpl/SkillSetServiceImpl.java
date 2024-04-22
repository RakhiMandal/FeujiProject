package com.skillset.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillset.dto.GapDto;
import com.skillset.dto.SkillGapDto;
import com.skillset.exception.RecordNotFoundException;
import com.skillset.repository.SkillSetRepository;
import com.skillset.service.SkillSetService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class SkillSetServiceImpl implements SkillSetService {

	@Autowired
	private SkillSetRepository repository;
	
	/**
	 * Retrieves a list of GapDto objects containing skill gap details for a specific employee and skill category.
	 * @param email The email address of the employee.
	 * @param skillCategoryId The ID of the skill category.
	 * @return A list of GapDto objects representing the skill gap details.
	 * @throws RecordNotFoundException 
	 */

	public List<GapDto> fetchSkillDto(String email, Integer skillCategoryId) throws RecordNotFoundException {
		log.info("fetchSkillDto start");
		String categoryName = repository.repogetTypeNameByCategoryId(skillCategoryId);
		int typeId = repository.getTypeIdByDetailsId(categoryName);
		List<GapDto> queryResult = repository.findEmployeeDetailsByEmail(email, typeId);
		if(queryResult!= null)
		{
			log.info("fetchSkillDto end");
			return queryResult;
		}else {
			throw new RecordNotFoundException("no records found");
		}
	}
	
	
	

	/**
	Retrieves the skills of an employee identified by the provided email address.
	@param email The email address of the employee whose skills are to be retrieved.
	@return A list of {@link SkillGapDto} objects representing the skills of the employee.
	@throws RecordNotFoundException If no skills are found for the provided email address.
	*/
	@Override
	public List<SkillGapDto> findEmployeeSkills(String email) throws RecordNotFoundException 
	{
		log.info("findEmployeeSkills start");
		List<SkillGapDto> queryResult = repository.findEmployeeSkills(email);
		if(queryResult!= null)
		{
			log.info("findEmployeeSkills end");
			return queryResult;
		}else {
			throw new RecordNotFoundException("no records found");
		}
	}

}
