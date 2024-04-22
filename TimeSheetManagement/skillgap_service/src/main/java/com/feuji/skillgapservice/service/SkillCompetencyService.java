package com.feuji.skillgapservice.service;

import java.util.List;

import com.feuji.skillgapservice.bean.SkillCompetencyBean;
import com.feuji.skillgapservice.dto.PaginationDto;
import com.feuji.skillgapservice.dto.TrainigRecommendedEmployeesDto;
import com.feuji.skillgapservice.exception.RecordNotFoundException;

public interface SkillCompetencyService {

	public void saveSkillCompetency(SkillCompetencyBean skillCompetencyBean) throws IllegalArgumentException;

	public SkillCompetencyBean updateAllSkillCompetencyRecords(SkillCompetencyBean skillCompetencyBean)
			throws RecordNotFoundException;

	public SkillCompetencyBean findSkillCompetencyByUuid(String uuid) throws RecordNotFoundException;

	public SkillCompetencyBean getSkillCompetencyBySkillId(int skillId) throws RecordNotFoundException;

	public List<SkillCompetencyBean> findSkillCompetencyByTechId(int technicalCatId) throws RecordNotFoundException;

	public PaginationDto getAllEmployeeSkillsBySkillIds(int[] skillId, int page, int size,String roleName) throws RecordNotFoundException;

	public List<TrainigRecommendedEmployeesDto> getAllTrainingRecommendedEmp(int[] skillIds);

}
