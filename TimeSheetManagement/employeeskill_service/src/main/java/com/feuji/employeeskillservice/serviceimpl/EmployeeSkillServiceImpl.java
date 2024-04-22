package com.feuji.employeeskillservice.serviceimpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.feuji.employeeskillservice.bean.EmployeeBean;
import com.feuji.employeeskillservice.bean.EmployeeSkillBean;
import com.feuji.employeeskillservice.bean.EmployeeSkillGet;
import com.feuji.employeeskillservice.bean.EmployeeUiBean;
import com.feuji.employeeskillservice.bean.SkillBean;
import com.feuji.employeeskillservice.constants.CommonConstants;
import com.feuji.employeeskillservice.entity.EmployeeSkillEntity;
import com.feuji.employeeskillservice.exception.InvalidInputException;
import com.feuji.employeeskillservice.exception.NoRecordFoundException;
import com.feuji.employeeskillservice.repository.EmployeeSkillRepository;
import com.feuji.employeeskillservice.service.EmployeeSkillService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeSkillServiceImpl implements EmployeeSkillService {
	@Autowired
	private EmployeeSkillRepository repository;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Saves a list of employee skill beans.
	 * 
	 * @param beanList The list of employee skill beans to be saved.
	 * @return List<EmployeeSkillBean> A list containing the saved employee skill
	 *         beans.
	 */
	@Override
	public List<EmployeeSkillBean> saveAll(List<EmployeeSkillBean> beanList) {
		log.info("saveAll() started:in EmployeeSkillServiceImpl ");
		if (beanList != null) {
			List<EmployeeSkillEntity> entityList = beanList.stream().map(this::beanToEntityConvertion)
					.collect(Collectors.toList());
			List<EmployeeSkillBean> list = repository.saveAll(entityList).stream().map(this::entityToBeanCovertion)
					.collect(Collectors.toList());
			log.info("saveAll() ended:in EmployeeSkillServiceImpl ");
			return list;
		} else {
			throw new NullPointerException("beanlist is null:in EmployeeSkillServiceImpl");
		}
	}

	/**
	 * 
	 * Updates the delete status of an employee skill record identified by the given
	 * employee skill ID.
	 * 
	 * @param employeeSkillId The ID of the employee skill record to be updated.
	 * @return String A message indicating the result of the update operation.
	 * @throws InvalidInputException
	 */
	@Override
	public String updateDeleteStatus(Long employeeSkillId) throws InvalidInputException {
		log.info("updateDeleteStatus() started: in EmployeeSkillServiceImpl");
		if (employeeSkillId != null) {
			repository.updateIsDeleted(employeeSkillId);
			log.info("updateDeleteStatus() ended: in EmployeeSkillServiceImpl");
			return "updated Successfully";
		} else {
			throw new InvalidInputException("invalid input is given:in EmployeeSkillServiceImpl");
		}
	}

	/**
	 * 
	 * Updates an employee skill record with the details provided in the employee
	 * skill get object.
	 * 
	 * @param set The employee skill get object containing the updated details.
	 * 
	 * @param id  The ID of the employee skill record to be updated.
	 * 
	 * @return EmployeeSkillBean The updated employee skill bean.
	 * @throws InvalidInputException
	 * @throws NoRecordFoundException
	 */
	@Override
	public EmployeeSkillBean updateEmployeeSkill(EmployeeSkillGet set, Long id)
			throws InvalidInputException, NoRecordFoundException {
		log.info("update() started: in EmployeeSkillServiceImpl");
		if (set != null && id != null) {
			EmployeeSkillEntity entity = repository.findByEmployeeSkillId(id);
			entity.setCompetencyLevelId(GetIdByName(set.getCompetencyLevelId()));
			entity.setComments(set.getComments());
			entity.setYearsOfExp(set.getYearsOfExp());
			entity.setDescription(set.getDescription());
			entity.setSkillTypeId(GetIdByName(set.getSkillTypeId()));
			if (set.getCertification().equals(CommonConstants.YES)) {
				entity.setCertification((byte) CommonConstants.TRUE);
			} else {
				entity.setCertification((byte) CommonConstants.FALSE);
			}
			entity.setDescription(set.getDescription());
			entity.setComments(set.getComments());

			long currentTimeMillis = System.currentTimeMillis();
			Timestamp timestamp = new Timestamp(currentTimeMillis);
			entity.setModifiedOn(timestamp);
			EmployeeSkillEntity save = repository.save(entity);
			log.info("update() ended: in EmployeeSkillServiceImpl");
			return entityToBeanCovertion(save);
		} else {
			throw new InvalidInputException("invalid input entered:in EmployeeSkillServiceImpl");
		}

	}

	/**
	 * Saves an employee skill bean.
	 * 
	 * @param bean The employee skill bean to be saved.
	 * @return EmployeeSkillBean The saved employee skill bean.
	 * @throws Exception
	 */
	@Override
	public EmployeeSkillBean saveEmployeeSkill(EmployeeSkillBean bean) throws Exception {
		log.info("saveEmployeeSkill() start:Saves an employee skill bean");
		EmployeeSkillEntity skillEntity = null;
		EmployeeSkillBean skillBean = null;
		try {
			EmployeeSkillEntity entity = beanToEntityConvertion(bean);
			skillEntity = repository.save(entity);
			skillBean = entityToBeanCovertion(skillEntity);
			log.info("saveEmployeeSkill() ended:Saves an employee skill bean");
			return skillBean;
		} catch (Exception e) {
			throw new Exception("failed to Save an employee skill bean:in EmployeeSkillServiceImpl");
		}
	}

	/**
	 * Converts a list of employee UI beans to employee skill beans.
	 * 
	 * @param employeeUiBeans The list of employee UI beans to be converted.
	 * @return List<EmployeeSkillBean> The list of employee skill beans converted
	 *         from the provided UI beans.
	 * @throws NoRecordFoundException
	 */
	@Override
	public List<EmployeeSkillBean> convertUiBeanToSkillBean(List<EmployeeUiBean> employeeUiBeans)
			throws NoRecordFoundException {
		if (employeeUiBeans != null) {
			log.info("convertUiBeanToSkillBean() start:Converts a list of employee UI beans to employee skill beans");
			List<EmployeeSkillBean> listBeans = new ArrayList<>();
			for (EmployeeUiBean bean : employeeUiBeans) {
				EmployeeBean employee = null;
				try {
					employee = getEmployeeByEmail(bean.getEmployeeMail());
				} catch (NoRecordFoundException e) {
					throw e;
				}
				EmployeeSkillBean empSkillBean = new EmployeeSkillBean();
				empSkillBean.setEmployeeId(employee.getEmployeeId());
				empSkillBean.setEmployeeCode(employee.getEmployeeCode());
				empSkillBean.setSkillId(Integer.parseInt(bean.getSkillId()));
				empSkillBean.setComments(bean.getComments());
				empSkillBean.setDescription(bean.getDescription());
				empSkillBean.setCreatedBy(
						employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName());
				empSkillBean.setModifiedBy(
						employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName());
				empSkillBean.setCompetencyLevelId(Integer.parseInt(bean.getCompetencyLevelId()));
				empSkillBean.setSkillTypeId(Integer.parseInt(bean.getSkillTypeId()));
				empSkillBean.setYearsOfExp(bean.getYearsOfExp());
				empSkillBean.setIsDeleted(Byte.parseByte(bean.getIsDeleted()));
				if (bean.getCertification().equals(CommonConstants.YES)) {
					empSkillBean.setCertification((byte) CommonConstants.TRUE);
				} else {
					empSkillBean.setCertification((byte) CommonConstants.FALSE);
				}
				listBeans.add(empSkillBean);
			}
			log.info("convertUiBeanToSkillBean() end:Converts a list of employee UI beans to employee skill beans");
			return listBeans;
		} else {
			throw new NullPointerException("employeeUiBeans are null:in EmployeeSkillServiceImpl");
		}
	}

	/**
	 * 
	 * Retrieves employee details by email from another server.
	 * 
	 * @param email The email of the employee to be retrieved.
	 * 
	 * @return EmployeeBean The employee details retrieved by email.
	 * @throws NoRecordFoundException
	 */
	@Override
	public EmployeeBean getEmployeeByEmail(String email) throws NoRecordFoundException {
		if (email != null) {
			log.info("getEmployeeByEmail() start: in EmployeeSkillServiceImpl");
			String url = "http://localhost:8082/api/employee/getByEmail/" + email;

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> httpEntity = new HttpEntity<>(headers);

			ResponseEntity<EmployeeBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
					EmployeeBean.class);
			EmployeeBean employeeBean = responseEntity.getBody();
			if (employeeBean != null) {
				log.info("getEmployeeByEmail() ended:in EmployeeSkillServiceImpl");
				return employeeBean;
			} else {
				throw new NoRecordFoundException("no record found with this email: " + email);
			}
		} else {
			throw new NullPointerException("email is null:in EmployeeSkillServiceImpl");
		}
	}

	/**
	 * Retrieves a list of employee skill beans associated with the given employee
	 * ID.
	 * 
	 * @param employeeId The ID of the employee whose skills are to be retrieved.
	 * @return List<EmployeeSkillBean> A list containing the employee skill beans
	 *         associated with the given employee ID.
	 * @throws NoRecordFoundException If no records are found for the specified
	 *                                employee ID, this exception is thrown.
	 */
	@Override
	public List<EmployeeSkillBean> getEmployeeSkillById(Integer employeeId) throws NoRecordFoundException {
		if (employeeId != null) {
			log.info("getEmployeeSkillById() start: in EmployeeSkillServiceImpl");
			List<EmployeeSkillEntity> list = repository.findByEmployeeId(employeeId);
			if (list != null) {
				List<EmployeeSkillBean> beans = list.stream().map(this::entityToBeanCovertion)
						.collect(Collectors.toList());
				log.info("getEmployeeSkillById() ended: in EmployeeSkillServiceImpl");
				return beans;
			} else {
				throw new NoRecordFoundException("no record found with this id: " + employeeId);
			}
		} else {
			throw new NullPointerException("employee id is null:in EmployeeSkillServiceImpl");
		}
	}

	/**
	 * Retrieves a list of employee skill beans associated with the given skill ID.
	 * 
	 * @param skillId The ID of the skill for which employee skills are to be
	 *                retrieved.
	 * @return List<EmployeeSkillBean> A list containing the employee skill beans
	 *         associated with the given skill ID.
	 * @throws NoRecordFoundException
	 * @throws InvalidInputException
	 */
	@Override
	public List<EmployeeSkillBean> findBySkillId(int skillId) throws NoRecordFoundException, InvalidInputException {
		if (skillId != CommonConstants.FALSE) {
			log.info("findBySkillId() started:  in EmployeeSkillServiceImpl");
			List<EmployeeSkillEntity> entities = repository.findBySkillId(skillId);
			if (entities != null) {
				List<EmployeeSkillBean> beans = entities.stream().map(this::entityToBeanCovertion)
						.collect(Collectors.toList());
				log.info("findBySkillId() started:  in EmployeeSkillServiceImpl");
				return beans;
			} else {
				throw new NoRecordFoundException("no record found with this skillId: " + skillId);
			}
		} else {
			throw new InvalidInputException("skillId is not found:in EmployeeSkillServiceImpl");
		}
	}

	/**
	 * 
	 * Retrieves all employee skills associated with the given email.
	 * 
	 * @param email The email of the employee for which skill records are to be
	 *              retrieved.
	 * @return List<EmployeeSkillGet> A list containing all employee skills
	 *         associated with the given email.
	 * @throws NoRecordFoundException
	 * @throws InvalidInputException
	 */
	@Override
	public List<EmployeeSkillGet> getAllEmployeeSkills(String email)
			throws NoRecordFoundException, InvalidInputException {

		log.info(" getAllEmployeeSkills() started: in EmployeeSkillServiceImpl");
		if (email != null) {
			EmployeeBean employee = getEmployeeByEmail(email);
			log.info(employee.toString());
			List<EmployeeSkillBean> findAll = getEmployeeSkillById(employee.getEmployeeId());
			List<EmployeeSkillGet> list = new ArrayList<>();
			for (EmployeeSkillBean bean : findAll) {
				if (bean.getIsDeleted() == CommonConstants.FALSE) {
					EmployeeSkillGet skillget = entityToSkill(bean);
					list.add(skillget);
				}
			}
			log.info(" getAllEmployeeSkills() ended: in EmployeeSkillServiceImpl");
			return list;
		} else {
			throw new NullPointerException("email is null:in EmployeeSkillServiceImpl");
		}
	}

	/**
	 * 
	 * Retrieves a skill bean by its ID from another server.
	 * 
	 * @param skillId The ID of the skill to be retrieved.
	 * 
	 * @return SkillBean The skill bean retrieved by ID.
	 * @throws NoRecordFoundException
	 * @throws InvalidInputException
	 */
	public SkillBean getSkillbeanById(int skillId) throws InvalidInputException, NoRecordFoundException {
		log.info("getSkillbeanById() started: in EmployeeSkillServiceImpl");
		if (skillId != CommonConstants.FALSE) {
			String url = "http://localhost:8087/api/skill/getBySkillId/" + skillId;

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> httpEntity = new HttpEntity<>(headers);

			ResponseEntity<SkillBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
					SkillBean.class);
			log.info("getSkillbeanById() ended:in EmployeeSkillServiceImpl");
			if (responseEntity.getBody() != null) {
				return responseEntity.getBody();
			} else {
				throw new NoRecordFoundException("no records found with this id: " + skillId);
			}
		} else {
			throw new InvalidInputException("invalid input is entered:in EmployeeSkillServiceImpl");
		}
	}

	/**
	 * 
	 * Retrieves the type name by its ID from another server.
	 * 
	 * @param id The ID of the type for which the name is to be retrieved.
	 * 
	 * @return String The name of the type retrieved by ID.
	 * @throws NoRecordFoundException
	 * @throws InvalidInputException
	 */
	public String getTypeName(int id) throws NoRecordFoundException, InvalidInputException {
		if (id != CommonConstants.FALSE) {
			log.info("getTypeName() started: in EmployeeSkillServiceImpl");
			String url = "http://localhost:8089/api/referencedetails/getById/" + id;

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> httpEntity = new HttpEntity<>(headers);

			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
					String.class);
			log.info("getTypeName() ended:in EmployeeSkillServiceImpl");
			return responseEntity.getBody();
		} else {
			throw new InvalidInputException("invalid input:in EmployeeSkillServiceImpl");
		}

	}

	/**
	 * 
	 * Retrieves the ID by its name from another server.
	 * 
	 * @param name The name of the item for which the ID is to be retrieved.
	 * 
	 * @return int The ID of the item retrieved by name.
	 * @throws InvalidInputException
	 * @throws NoRecordFoundException
	 */
	public int GetIdByName(String name) throws InvalidInputException, NoRecordFoundException {
		if (name != null) {
			log.info("GetIdByName() started: in EmployeeSkillServiceImpl");
			String url = "http://localhost:8089/api/referencedetails/getByName/" + name;

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> httpEntity = new HttpEntity<>(headers);

			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
					String.class);
			if (responseEntity != null) {

				log.info("GetIdByName() ended:in EmployeeSkillServiceImpl");
				return Integer.parseInt(responseEntity.getBody());
			} else {
				throw new NoRecordFoundException("no record found with this name: " + name);
			}
		} else {
			throw new InvalidInputException("invalid input::in EmployeeSkillServiceImpl");
		}
	}

	public String getTypeNameFromTypeService(int id) throws NoRecordFoundException, InvalidInputException {
		if (id != CommonConstants.FALSE) {
			log.info("getTypeName() started: in EmployeeSkillServiceImpl");
			String url = "http://localhost:8089/api/referencetype/getById/" + id;

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> httpEntity = new HttpEntity<>(headers);

			ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
			log.info("getTypeName() ended:in EmployeeSkillServiceImpl");
			return exchange.getBody();
		} else {
			throw new InvalidInputException("invalid input:in EmployeeSkillServiceImpl");
		}

	}

	/**
	 * Converts an employee skill bean to an employee skill get bean.
	 * 
	 * @param entity The employee skill bean to be converted.
	 * @return EmployeeSkillGet The employee skill get bean converted from the
	 *         provided employee skill bean.
	 * @throws NoRecordFoundException
	 * @throws InvalidInputException
	 */
	public EmployeeSkillGet entityToSkill(EmployeeSkillBean employeeSkillBean)
			throws InvalidInputException, NoRecordFoundException {
		if (employeeSkillBean != null) {
			log.info("entityToSkill() started: in EmployeeSkillServiceImpl");
			SkillBean skillBean = getSkillbeanById(employeeSkillBean.getSkillId());
			EmployeeSkillGet skill = new EmployeeSkillGet();
			skill.setEmployeeSkillId(employeeSkillBean.getEmployeeSkillId());
			skill.setSkillCategory(getTypeNameFromTypeService(skillBean.getSkillCategoryId()));
			skill.setTechnicalCategory(getTypeName(skillBean.getTechinicalCategoryId()));
			skill.setSkillTypeId(getTypeName(employeeSkillBean.getSkillTypeId()));
			skill.setSkillName(skillBean.getSkillName());
			skill.setSkillId(employeeSkillBean.getSkillId());
			skill.setCompetencyLevelId(getTypeName(employeeSkillBean.getCompetencyLevelId()));
			skill.setYearsOfExp(employeeSkillBean.getYearsOfExp());
			String getCertification = (employeeSkillBean.getCertification() == (byte) CommonConstants.TRUE)
					? CommonConstants.YES
					: CommonConstants.NO;
			skill.setCertification(getCertification);
			skill.setDescription(employeeSkillBean.getDescription());
			skill.setComments(employeeSkillBean.getComments());
			skill.setIsDeleted(employeeSkillBean.getIsDeleted());
			log.info("entityToSkill() ended: in EmployeeSkillServiceImpl");
			return skill;
		} else {
			throw new NullPointerException("entered employeeSkillBean is null");
		}
	}

	/**
	 * Converts an EmployeeSkillEntity to an EmployeeSkillBean.
	 */
	public EmployeeSkillBean entityToBeanCovertion(EmployeeSkillEntity entity) {
		log.info("entityToBeanCovertion() started:in EmployeeSkillServiceImpl");
		EmployeeSkillBean bean = new EmployeeSkillBean();
		bean.setEmployeeSkillId(entity.getEmployeeSkillId());
		bean.setEmployeeId(entity.getEmployeeId());
		bean.setEmployeeCode(entity.getEmployeeCode());
		bean.setSkillId(entity.getSkillId());
		bean.setCompetencyLevelId(entity.getCompetencyLevelId());
		bean.setSkillTypeId(entity.getSkillTypeId());
		bean.setYearsOfExp(entity.getYearsOfExp());
		bean.setCertification(entity.getCertification());
		bean.setDescription(entity.getDescription());
		bean.setComments(entity.getComments());
		bean.setIsDeleted(entity.getIsDeleted());
		bean.setUuid(entity.getUuid());
		bean.setCreatedBy(entity.getCreatedBy());
		bean.setCreatedOn(entity.getCreatedOn());
		bean.setModifiedBy(entity.getModifiedBy());
		bean.setModifiedOn(entity.getModifiedOn());
		log.info("entityToBeanCovertion() ended :in EmployeeSkillServiceImpl");
		return bean;
	}

	/**
	 * Converts an employee skill bean to an EmployeeSkillEntity.
	 */
	public EmployeeSkillEntity beanToEntityConvertion(EmployeeSkillBean bean) {
		log.info("beanToEntityConvertion() started:in EmployeeSkillServiceImpl");
		EmployeeSkillEntity entity = new EmployeeSkillEntity();
		entity.setEmployeeId(bean.getEmployeeId());
		entity.setEmployeeCode(bean.getEmployeeCode());
		entity.setSkillId(bean.getSkillId());
		entity.setSkillTypeId(bean.getSkillTypeId());
		entity.setCompetencyLevelId(bean.getCompetencyLevelId());
		entity.setYearsOfExp(bean.getYearsOfExp());
		entity.setIsDeleted(bean.getIsDeleted());
		entity.setCertification(bean.getCertification());
		entity.setDescription(bean.getDescription());
		entity.setComments(bean.getComments());
		entity.setCreatedBy(bean.getCreatedBy());
		entity.setModifiedBy(bean.getModifiedBy());
		log.info("beanToEntityConvertion() ended:in EmployeeSkillServiceImpl");
		return entity;
	}
}
