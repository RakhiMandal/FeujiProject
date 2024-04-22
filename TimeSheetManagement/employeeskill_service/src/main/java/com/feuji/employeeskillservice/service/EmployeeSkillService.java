package com.feuji.employeeskillservice.service;

import java.util.List;

import com.feuji.employeeskillservice.bean.EmployeeBean;
import com.feuji.employeeskillservice.bean.EmployeeSkillBean;
import com.feuji.employeeskillservice.bean.EmployeeSkillGet;
import com.feuji.employeeskillservice.bean.EmployeeUiBean;
import com.feuji.employeeskillservice.exception.InvalidInputException;
import com.feuji.employeeskillservice.exception.NoRecordFoundException;

public interface EmployeeSkillService {
	public EmployeeSkillBean saveEmployeeSkill(EmployeeSkillBean bean) throws Exception;

	public List<EmployeeSkillBean> getEmployeeSkillById(Integer employeeId) throws NoRecordFoundException;

	public List<EmployeeSkillBean> saveAll(List<EmployeeSkillBean> beanList);

	public List<EmployeeSkillBean> findBySkillId(int skillId) throws NoRecordFoundException, InvalidInputException;

	public EmployeeBean getEmployeeByEmail(String email) throws Exception;

	public List<EmployeeSkillGet> getAllEmployeeSkills(String email) throws Exception;

	public String updateDeleteStatus(Long employeeSkillId) throws InvalidInputException;

	public EmployeeSkillBean updateEmployeeSkill(EmployeeSkillGet set, Long id)
			throws InvalidInputException, NoRecordFoundException;

	public List<EmployeeSkillBean> convertUiBeanToSkillBean(List<EmployeeUiBean> employeeUiBeans)
			throws NoRecordFoundException;

}
