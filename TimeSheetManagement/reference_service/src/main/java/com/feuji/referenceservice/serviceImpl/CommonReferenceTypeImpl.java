package com.feuji.referenceservice.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.referenceservice.bean.CommonReferenceTypeBean;
import com.feuji.referenceservice.constants.CommonConstants;
import com.feuji.referenceservice.dto.ReferenceDto;
import com.feuji.referenceservice.entity.CommonReferenceTypeEntity;
import com.feuji.referenceservice.exception.ReferenceNotFoundException;
import com.feuji.referenceservice.repository.CommonReferenceTypeRepo;
import com.feuji.referenceservice.service.CommonReferenceType;

@Service
public class CommonReferenceTypeImpl implements CommonReferenceType {

	private static Logger log = LoggerFactory.getLogger(CommonReferenceTypeImpl.class);

	@Autowired
	CommonReferenceTypeRepo commonReferenceTypeRepo;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public CommonReferenceTypeEntity getByTypeName(String typeName) {
		log.info("getByTypeName() started in CommonReferenceTypeImpl");
		CommonReferenceTypeEntity byTypeName = commonReferenceTypeRepo.getByTypeName(typeName);
		log.info("getByTypeName() end in CommonReferenceTypeImpl");
		return byTypeName;
	}

	@Override
	public CommonReferenceTypeEntity save(CommonReferenceTypeBean commonReferenceTypeBean) {
		CommonReferenceTypeEntity commonReferenceTypeEntity = modelMapper.map(commonReferenceTypeBean,
				CommonReferenceTypeEntity.class);
		log.info("saving timesheet entity " + commonReferenceTypeEntity);
		commonReferenceTypeEntity = commonReferenceTypeRepo.save(commonReferenceTypeEntity);
		return commonReferenceTypeEntity;
	}

	@Override
	public List<ReferenceDto> getAllReferences() {
		return commonReferenceTypeRepo.findAllReferences();
	}

	public CommonReferenceTypeBean convertEntityToBean(CommonReferenceTypeEntity entity) {
		CommonReferenceTypeBean bean = new CommonReferenceTypeBean();
		bean.setReferenceTypeId(entity.getReferenceTypeId());
		bean.setReferenceTypeName(entity.getReferenceTypeName());
		bean.setCreatedBy(entity.getCreatedBy());
		bean.setCreatedOn(entity.getCreatedOn());
		bean.setModifiedBy(entity.getModifiedBy());
		bean.setModifiedOn(entity.getModifiedOn());
		bean.setIsDeleted(CommonConstants.FALSE);
		return bean;
	}

	@Override
	public String getNameById(int id) {
		log.info("getByid() started");
		String nameById = commonReferenceTypeRepo.getNameById(id);
		if (nameById != null) {
			log.info("getByid() ended");
			return nameById;
		} else {
			throw new ReferenceNotFoundException("no suitable name found for this id: " + id);
		}
	}

	public CommonReferenceTypeBean getRefByTypeName(String typeName) throws ReferenceNotFoundException {
		log.info("getByTypeName Start:Fetching reference type names");
		CommonReferenceTypeEntity byTypeName = commonReferenceTypeRepo.getRefByTypeName(typeName);
		if (byTypeName == null) {
			throw new ReferenceNotFoundException("Entity not found for type name: " + typeName);
		}
		log.info("getByTypeName End:Fetched reference type names");
		return convertEntityToBean(byTypeName);

	}

}
