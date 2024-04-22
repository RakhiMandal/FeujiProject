package com.feuji.referenceservice.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.feuji.referenceservice.bean.CommonReferenceDetailsBean;
import com.feuji.referenceservice.bean.CommonReferenceTypeBean;
import com.feuji.referenceservice.bean.ReferenceDetailsBean;
import com.feuji.referenceservice.bean.TechnicalSkillsBean;
import com.feuji.referenceservice.constants.CommonConstants;
import com.feuji.referenceservice.entity.CommonReferenceDetailsEntity;
import com.feuji.referenceservice.entity.CommonReferenceTypeEntity;
import com.feuji.referenceservice.entity.SkillEntity;
import com.feuji.referenceservice.exception.CategoryNotFoundException;
import com.feuji.referenceservice.exception.InvalidInputException;
import com.feuji.referenceservice.exception.NoRecordFoundException;
import com.feuji.referenceservice.exception.RecordAlreadyExistsException;
import com.feuji.referenceservice.exception.RecordNotFoundException;
import com.feuji.referenceservice.exception.ReferenceNotFoundException;
import com.feuji.referenceservice.exception.TechnicalSkillsNotFoundException;
import com.feuji.referenceservice.repository.CommonReferenceDetailsRepo;
import com.feuji.referenceservice.repository.CommonReferenceTypeRepo;
import com.feuji.referenceservice.service.CommonReferenceDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommonReferenceDetailsImpl implements CommonReferenceDetails {

	@Autowired
	CommonReferenceDetailsRepo commonReferenceDetailsRepo;
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	CommonReferenceTypeRepo commonReferenceTypeRepo;

	@Autowired
	CommonReferenceTypeImpl commonReferenceTypeImpl;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<ReferenceDetailsBean> getDetailsByTypeId(String typeName) {
		log.info("result" + typeName);
		List<String> detailsByTypeName = commonReferenceDetailsRepo.getDetailsByTypeName(typeName);
		log.info("result" + detailsByTypeName);

		List<ReferenceDetailsBean> list = new ArrayList<ReferenceDetailsBean>();

		for (String item : detailsByTypeName) {
			ReferenceDetailsBean bean = new ReferenceDetailsBean();
			String[] split = item.split(",");
			bean.setReferenceDetailValue(split[0]);
			bean.setReferenceDetailId(Integer.parseInt(split[1]));
			list.add(bean);
		}
		return list;
	}

	@Override
	public CommonReferenceDetailsBean getReferenceById(Integer id) {
		CommonReferenceDetailsEntity commonReferenceDetailsEntity = commonReferenceDetailsRepo.findById(id)
				.orElseThrow();

		return modelMapper.map(commonReferenceDetailsEntity, CommonReferenceDetailsBean.class);
	}

	@Override
	public CommonReferenceDetailsBean addSubSkillcategory(CommonReferenceDetailsBean bean)
			throws ReferenceNotFoundException {
		log.info("Entered into addSubSkillCategory method in service implementation");
		CommonReferenceDetailsEntity save = null;
		CommonReferenceDetailsEntity beanToEntity = convertBeanToEntity(bean);
		CommonReferenceDetailsEntity existingEntities = commonReferenceDetailsRepo.findByReferenceDetailValue(
				beanToEntity.getReferenceDetailValue(), beanToEntity.getReferenceType().getReferenceTypeId());

		if (existingEntities != null && existingEntities.getIsDeleted() == CommonConstants.FALSE) {
			log.info("Record with referenceDetailValue " + beanToEntity.getReferenceDetailValue() + " already exists.");
			throw new RecordAlreadyExistsException(
					"Record with referenceDetailValue " + beanToEntity.getReferenceDetailValue() + " already exists.");
		} else if (existingEntities != null && existingEntities.getIsDeleted() == CommonConstants.TRUE) {
			existingEntities.setIsDeleted(CommonConstants.FALSE);
			save = commonReferenceDetailsRepo.save(existingEntities);
		} else if (existingEntities == null) {
			log.info("Saving the add subSkill into referenceDetails entity");
			beanToEntity.setIsDeleted(CommonConstants.FALSE);
			save = commonReferenceDetailsRepo.save(beanToEntity);
			log.info("Completed addSubSkillCategory method in service implementation");
		}
		return convertEntityToBean(save);
	}

	public CommonReferenceDetailsEntity convertBeanToEntity(CommonReferenceDetailsBean bean) {
		System.out.println(bean);
		CommonReferenceTypeEntity commonReferenceTypeEntity = new CommonReferenceTypeEntity();
		commonReferenceTypeEntity.setReferenceTypeId(bean.getReferenceTypeId().getReferenceTypeId());
		CommonReferenceDetailsEntity entity = new CommonReferenceDetailsEntity();
		entity.setReferenceDetailId(bean.getReferenceDetailId());
		entity.setReferenceDetailValue(bean.getReferenceDetailValue());
		entity.setReferenceType(commonReferenceTypeEntity);
		entity.setIsDeleted(bean.getIsDeleted());
		entity.setModifiedBy(bean.getModifiedBy());
		entity.setCreatedBy(bean.getCreatedBy());
		return entity;
	}

	public CommonReferenceDetailsBean convertEntityToBean(CommonReferenceDetailsEntity detailsEntity) {
		CommonReferenceTypeBean commonReferenceTypeBean = new CommonReferenceTypeBean();
		commonReferenceTypeBean.setReferenceTypeId(detailsEntity.getReferenceType().getReferenceTypeId());
		CommonReferenceDetailsBean bean = new CommonReferenceDetailsBean();
		bean.setReferenceDetailId(detailsEntity.getReferenceDetailId());
		bean.setReferenceDetailValue(detailsEntity.getReferenceDetailValue());
		bean.setReferenceTypeId(commonReferenceTypeBean);
		bean.setIsDeleted(detailsEntity.getIsDeleted());
		bean.setModifiedBy(detailsEntity.getModifiedBy());
		bean.setCreatedBy(detailsEntity.getCreatedBy());
		return bean;

	}

	@Override
	public CommonReferenceTypeBean saveReferenceDetails(CommonReferenceDetailsBean referenceDetailsBean) {
		CommonReferenceTypeBean bean = null;
		CommonReferenceTypeEntity save = null;
		CommonReferenceDetailsEntity convertBeanToEntity = convertBeanToEntity(referenceDetailsBean);
		CommonReferenceDetailsEntity existingDetailEntity = commonReferenceDetailsRepo
				.findByReferenceDetailValue(convertBeanToEntity.getReferenceDetailValue());

		CommonReferenceTypeEntity commonReferenceTypeEntity = new CommonReferenceTypeEntity();
		CommonReferenceTypeEntity findByReferenceTypeName = commonReferenceTypeRepo
				.findByReferenceTypeName(convertBeanToEntity.getReferenceDetailValue());

		commonReferenceTypeEntity.setReferenceTypeName(convertBeanToEntity.getReferenceDetailValue());
		commonReferenceTypeEntity.setCreatedBy(convertBeanToEntity.getCreatedBy());
		commonReferenceTypeEntity.setIsDeleted(convertBeanToEntity.getIsDeleted());
		commonReferenceTypeEntity.setModifiedBy(convertBeanToEntity.getModifiedBy());
		if (existingDetailEntity != null && existingDetailEntity.getIsDeleted() == CommonConstants.FALSE) {
			throw new RecordAlreadyExistsException("Record with referenceDetailValue "
					+ convertBeanToEntity.getReferenceDetailValue() + " already exists.");
		} else if (existingDetailEntity != null && findByReferenceTypeName != null
				&& findByReferenceTypeName.getIsDeleted() == CommonConstants.TRUE
				&& existingDetailEntity.getIsDeleted() == CommonConstants.TRUE) {
			existingDetailEntity.setIsDeleted(CommonConstants.FALSE);
			commonReferenceDetailsRepo.save(existingDetailEntity);
			findByReferenceTypeName.setIsDeleted(CommonConstants.FALSE);
			save = commonReferenceTypeRepo.save(findByReferenceTypeName);
			bean = commonReferenceTypeImpl.convertEntityToBean(save);
		} else {
			convertBeanToEntity = commonReferenceDetailsRepo.save(convertBeanToEntity);
			save = commonReferenceTypeRepo.save(commonReferenceTypeEntity);
			bean = commonReferenceTypeImpl.convertEntityToBean(save);
		}
		return bean;
	}

	@Override
	public CommonReferenceDetailsBean updateIsDeleted(CommonReferenceDetailsBean commonReferenceDetailsBean)
			throws InvalidInputException {
		log.info("updateIsDeleted started in CommonReferenceDetailsServiceImpl");
		if (commonReferenceDetailsBean != null) {
			CommonReferenceDetailsEntity convertBeanToEntity = convertBeanToEntity(commonReferenceDetailsBean);
			convertBeanToEntity = commonReferenceDetailsRepo.findById(commonReferenceDetailsBean.getReferenceDetailId())
					.get();
			convertBeanToEntity.setIsDeleted(commonReferenceDetailsBean.getIsDeleted());
			CommonReferenceDetailsEntity save = commonReferenceDetailsRepo.save(convertBeanToEntity);
			CommonReferenceTypeEntity byTypeName = commonReferenceTypeRepo
					.findByReferenceTypeName(commonReferenceDetailsBean.getReferenceDetailValue());
			byTypeName.setIsDeleted(convertBeanToEntity.getIsDeleted());
			commonReferenceTypeRepo.save(byTypeName);
			log.info("updateIsDeleted ende in CommonReferenceDetailsServiceImpl");
			return convertEntityToBean(save);
		} else {
			throw new InvalidInputException("input is null");
		}

	}

	@Override
	public CommonReferenceDetailsBean deleteSkillCategory(String skillCategory, Byte isDeleted)
			throws RecordNotFoundException, NoRecordFoundException, InvalidInputException {
		int skillCategoryId = getIdByName(skillCategory);
		Optional<CommonReferenceDetailsEntity> optionalEntity = commonReferenceDetailsRepo.findById(skillCategoryId);
		CommonReferenceTypeEntity typeEntity = commonReferenceTypeRepo.findByReferenceTypeName(skillCategory);
		if (optionalEntity.isPresent() && typeEntity != null) {
			CommonReferenceDetailsEntity entity = optionalEntity.get();
			entity.setIsDeleted(isDeleted);
			typeEntity.setIsDeleted(isDeleted);
			List<String> categories = commonReferenceDetailsRepo.getCategories(skillCategory);
			CommonReferenceDetailsEntity save = commonReferenceDetailsRepo.save(entity);
			CommonReferenceTypeEntity referenceTypeEntity = commonReferenceTypeRepo.save(typeEntity);

			for (String subCategory : categories) {
				CommonReferenceDetailsEntity value = commonReferenceDetailsRepo.findByReferenceDetailValue(subCategory,
						typeEntity.getReferenceTypeId());
				log.info(value.toString());
				if (value != null)
					deleteSubSkill(value.getReferenceDetailId(), isDeleted);

			}
			return convertEntityToBean(save);
		} else {
			throw new RecordNotFoundException("Record not found with this reference details id: " + skillCategoryId);
		}
	}

	@Override
	public int getIdByName(String name) {
		log.info("getByName() started");
		if (name != null) {
			int id = commonReferenceDetailsRepo.getByName(name);
			log.info("getByName() ended");
			return id;
		} else {
			throw new NullPointerException("name is null");
		}

	}

	@Override
	public CommonReferenceDetailsBean deleteSubSkill(Integer referenceDetailId, Byte isDeleted)
			throws RecordNotFoundException, NoRecordFoundException, InvalidInputException {
		Optional<CommonReferenceDetailsEntity> optionalEntity = commonReferenceDetailsRepo.findById(referenceDetailId);
		if (optionalEntity.isPresent()) {
			CommonReferenceDetailsEntity entity = optionalEntity.get();
			entity.setIsDeleted(isDeleted);
			CommonReferenceDetailsEntity save = commonReferenceDetailsRepo.save(entity);
			deleteSkill(referenceDetailId);
			return convertEntityToBean(save);
		} else {
			throw new RecordNotFoundException("Record not found with this reference details id: " + referenceDetailId);
		}
	}

	public SkillEntity deleteSkill(Integer referenceDetailId) throws NoRecordFoundException, InvalidInputException {
		if (referenceDetailId != null) {
			String url = "http://localhost:8087/api/skill/deleteSkill/" + referenceDetailId;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> httpEntity = new HttpEntity<>(headers);
			ResponseEntity<SkillEntity> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity,
					SkillEntity.class);
			if (responseEntity.getBody() != null) {
				return responseEntity.getBody();
			} else {
				throw new NoRecordFoundException("no records found with this id: " + referenceDetailId);
			}
		} else {
			throw new InvalidInputException("invalid input is entered:in EmployeeSkillServiceImpl");
		}

	}

	@Override
	public String getByid(int id) {
		log.info("getByid() started");
		String nameById = commonReferenceDetailsRepo.getNameById(id);
		if (nameById != null) {
			log.info("getByid() ended");
			return nameById;
		} else {
			throw new ReferenceNotFoundException("no suitable name found for this id: " + id);
		}
	}

	@Override
	public List<TechnicalSkillsBean> getRefDetailsByTypeId(String typeName) throws TechnicalSkillsNotFoundException {

		List<String> detailsByTypeName = commonReferenceDetailsRepo.getDetailsByTypeRefName(typeName);
		if (detailsByTypeName == null || detailsByTypeName.isEmpty()) {
			log.info("We are getting detailsByTypeName null");
			throw new TechnicalSkillsNotFoundException("No details found for type: " + typeName);
		}

		List<TechnicalSkillsBean> technicalSkillBeanList = new ArrayList<>();

		for (String item : detailsByTypeName) {
			TechnicalSkillsBean bean = new TechnicalSkillsBean();
			String[] split = item.split(",");
			bean.setReferenceDetailValue(split[0]);
			bean.setReferenceDetailId(Integer.parseInt(split[1]));
			bean.setReferenceTypeId(Integer.parseInt(split[2]));
			technicalSkillBeanList.add(bean);
		}
		return technicalSkillBeanList;
	}

	@Override
	public List<String> getCategories(String categoryName) {
		log.info("getCategories() start");
		List<String> categoryList = commonReferenceDetailsRepo.getCategories(categoryName);
		if (categoryList != null) {
			log.info("getCategories() end");
			return categoryList;
		} else {
			throw new CategoryNotFoundException("no categories found with this " + categoryName);
		}

	}

}
