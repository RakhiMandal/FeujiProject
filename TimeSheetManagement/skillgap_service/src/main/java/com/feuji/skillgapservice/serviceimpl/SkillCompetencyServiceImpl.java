package com.feuji.skillgapservice.serviceimpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.feuji.skillgapservice.bean.SkillCompetencyBean;
import com.feuji.skillgapservice.commonconstants.CommonConstants;
import com.feuji.skillgapservice.dto.EmployeeEntityDto;
import com.feuji.skillgapservice.dto.EmployeesSkillsListDto;
import com.feuji.skillgapservice.dto.PaginationDto;
import com.feuji.skillgapservice.dto.SkillsBean;
import com.feuji.skillgapservice.dto.TrainigRecommendedEmployeesDto;
import com.feuji.skillgapservice.entity.SkillCompetencyEntity;
import com.feuji.skillgapservice.exception.RecordNotFoundException;
import com.feuji.skillgapservice.repository.EmployeeRepository;
import com.feuji.skillgapservice.repository.SkillCompetencyRepository;
import com.feuji.skillgapservice.service.SkillCompetencyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SkillCompetencyServiceImpl implements SkillCompetencyService {

	@Autowired
	SkillCompetencyRepository competencyRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	public SkillCompetencyEntity convertBeanToEntity(SkillCompetencyBean skillCompetencyBean) {
		try {
			log.info("convert bean to entity method started");
			SkillCompetencyEntity skillCompetencyEntity = new SkillCompetencyEntity();
			skillCompetencyEntity.setRoleId(skillCompetencyBean.getRoleId());
			skillCompetencyEntity.setRoleName(skillCompetencyBean.getRoleName());
			skillCompetencyEntity.setSkillId(skillCompetencyBean.getSkillId());
			skillCompetencyEntity.setSkillTypeId(skillCompetencyBean.getSkillTypeId());
			skillCompetencyEntity.setCompetencyLevelId(skillCompetencyBean.getCompetencyLevelId());
			skillCompetencyEntity.setYearsOfExperiance(skillCompetencyBean.getYearsOfExperiance());
			skillCompetencyEntity.setCertification(skillCompetencyBean.getCertification());
			skillCompetencyEntity.setDescription(skillCompetencyBean.getDescription());
			skillCompetencyEntity.setComments(skillCompetencyBean.getComments());
			skillCompetencyEntity.setIsDeleted(skillCompetencyBean.getIsDeleted());
			skillCompetencyEntity.setUuid(skillCompetencyBean.getUuid());
			skillCompetencyEntity.setCreatedBy(skillCompetencyBean.getCreatedBy());
			skillCompetencyEntity.setCreatedOn(skillCompetencyBean.getCreatedOn());
			skillCompetencyEntity.setModifiedBy(skillCompetencyBean.getModifiedBy());
			return skillCompetencyEntity;
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to convert bean to entity", e);
		}

	}

	public SkillCompetencyBean convertEntityToBean(SkillCompetencyEntity skillCompetencyEntity) {
		try {
			SkillCompetencyBean skillCompetencyBean = new SkillCompetencyBean();
			skillCompetencyBean.setRoleId(skillCompetencyEntity.getRoleId());
			skillCompetencyBean.setRoleName(skillCompetencyEntity.getRoleName());
			skillCompetencyBean.setSkillId(skillCompetencyEntity.getSkillId());
			skillCompetencyBean.setSkillTypeId(skillCompetencyEntity.getSkillTypeId());
			skillCompetencyBean.setCompetencyLevelId(skillCompetencyEntity.getCompetencyLevelId());
			skillCompetencyBean.setYearsOfExperiance(skillCompetencyEntity.getYearsOfExperiance());
			skillCompetencyBean.setCertification(skillCompetencyEntity.getCertification());
			skillCompetencyBean.setDescription(skillCompetencyEntity.getDescription());
			skillCompetencyBean.setComments(skillCompetencyEntity.getComments());
			skillCompetencyBean.setIsDeleted(skillCompetencyEntity.getIsDeleted());
			skillCompetencyBean.setUuid(skillCompetencyEntity.getUuid());
			skillCompetencyBean.setCreatedBy(skillCompetencyEntity.getCreatedBy());
			skillCompetencyBean.setCreatedOn(skillCompetencyEntity.getCreatedOn());
			skillCompetencyBean.setModifiedBy(skillCompetencyEntity.getModifiedBy());
			return skillCompetencyBean;
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to convert bean to entity", e);
		}

	}

	@Override
	public void saveSkillCompetency(SkillCompetencyBean skillCompetencyBean) {
		log.info("Service save Method Start");
		if (skillCompetencyBean != null) {
			SkillCompetencyEntity convertBeanToEntity = convertBeanToEntity(skillCompetencyBean);

			if (convertBeanToEntity.getModifiedOn() == null) {
				convertBeanToEntity.setModifiedOn(new Timestamp(System.currentTimeMillis()));
			}
			if (convertBeanToEntity.getCreatedOn() == null) {
				convertBeanToEntity.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			}
			competencyRepository.save(convertBeanToEntity);
			log.info("Service save Method End");
		} else {
			throw new NullPointerException("SkillCompetencyBean is null");
		}

	}

	@Override
	public SkillCompetencyBean updateAllSkillCompetencyRecords(SkillCompetencyBean skillCompetencyBean)
			throws RecordNotFoundException {
		log.info("Service updateAllRecords Method Start");
		if (skillCompetencyBean != null) {
			SkillCompetencyEntity beanToEntity = convertBeanToEntity(skillCompetencyBean);
			if (beanToEntity.getModifiedOn() == null) {
				beanToEntity.setModifiedOn(new Timestamp(System.currentTimeMillis()));
			}
			SkillCompetencyEntity save = competencyRepository.save(beanToEntity);
			SkillCompetencyBean entityToBean = convertEntityToBean(save);
			if (entityToBean != null) {
				log.info("Service updateAllRecords Method End");
				return entityToBean;
			} else {
				throw new RecordNotFoundException("skillCompetency record is not found");
			}
		} else {
			throw new NullPointerException("skillCompetencyBean is null");
		}

	}

	@Override
	public SkillCompetencyBean findSkillCompetencyByUuid(String uuid) throws RecordNotFoundException {
		log.info("Service findByUuid Method Start");
		if (uuid != null) {
			SkillCompetencyEntity skillEntity = competencyRepository.findByUuid(uuid)
					.orElseThrow(() -> new IllegalArgumentException("Skill not found with id-" + uuid));
			if (skillEntity != null) {
				log.info("Service findByUuid Method End");
				return convertEntityToBean(skillEntity);
			} else {
				throw new RecordNotFoundException("skillCompetency record is not found with this uuid");
			}
		} else {
			throw new NullPointerException("skillCompetencyBean uuid is null");
		}

	}

	@Override
	public SkillCompetencyBean getSkillCompetencyBySkillId(int skillId) throws RecordNotFoundException {
		log.info("Service getBySkillId Method Start");
		if (skillId != CommonConstants.FALSE) {
			SkillCompetencyEntity entity = competencyRepository.findBySkillId(skillId);
			if (entity != null) {
				log.info("Service getBySkillId Method End");
				return convertEntityToBean(entity);
			} else {
				throw new RecordNotFoundException("skillCompetency record is not found with this skillId");
			}
		} else {
			throw new NullPointerException("skillCompetency skillId is null");
		}

	}

	@Override
	public List<SkillCompetencyBean> findSkillCompetencyByTechId(int technicalCategoryId)
			throws RecordNotFoundException {
		log.info("Service findByTechId Method Start");
		if (technicalCategoryId != CommonConstants.FALSE) {
			List<SkillCompetencyEntity> entities = competencyRepository.getByTechId(technicalCategoryId);
			if (entities != null) {
				List<SkillCompetencyBean> list = new ArrayList<>();
				for (SkillCompetencyEntity entity : entities) {
					SkillCompetencyBean bean = convertEntityToBean(entity);
					list.add(bean);
				}
				log.info("Service findByTechId Method End");
				return list;
			} else {
				throw new RecordNotFoundException("skillCompetency record is not found with this technicalCatId");
			}
		} else {
			throw new NullPointerException("skillCompetency technicalCatId is null");
		}

	}
	@Override
	public PaginationDto getAllEmployeeSkillsBySkillIds(int[] skillId, int page, int size, String roleName)
			throws RecordNotFoundException {
		log.info("getAllEmployeeSkillsBySkillIds() started");
		Pageable pageable = PageRequest.of(page, size);

		List<EmployeesSkillsListDto> employeeSkillList = new ArrayList<EmployeesSkillsListDto>();

		Page<EmployeeEntityDto> findEmployeesBySkillId = employeeRepository.findEmployeesBySkillId(skillId, pageable);
		if (findEmployeesBySkillId != null) {
			for (EmployeeEntityDto employee : findEmployeesBySkillId.getContent()) {
				List<SkillsBean> findSkillsByEmployeeId = competencyRepository
						.findSkillsByEmployeeId(employee.getEmployeeId(), skillId,roleName);
				EmployeesSkillsListDto dto = new EmployeesSkillsListDto();
				dto.setEmployeeName(
						employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName());
				dto.setEmployeeId(employee.getEmployeeId());
				dto.setEmployeeCode(employee.getEmployeeCode());
				dto.setDesignition(employee.getDesignation());
				dto.setEmail(employee.getEmail());
				dto.setSkillLists(findSkillsByEmployeeId);

				employeeSkillList.add(dto);

			}
			PaginationDto paginationDto = PaginationDto.builder().pageNo(page).pageSize(size)
					.first(findEmployeesBySkillId.isFirst()).last(findEmployeesBySkillId.isLast())
					.totalRecords(findEmployeesBySkillId.getTotalElements())
					.totalPages(findEmployeesBySkillId.getTotalPages()).skillList(employeeSkillList).build();
			log.info("getAllEmployeeSkillsBySkillIds() ended");
			return paginationDto;
		} else {
			throw new RecordNotFoundException("skillCompetency record is not found");
		}

	}


	@Override
	public List<TrainigRecommendedEmployeesDto> getAllTrainingRecommendedEmp(int[] skillIds) {
		List<TrainigRecommendedEmployeesDto> trainigRecommendedEmployeesList = employeeRepository
				.getAllTrainingRecommendedEmp(skillIds);
		return trainigRecommendedEmployeesList;
	}

}