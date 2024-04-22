package com.feuji.referenceservice.service;

import java.util.List;

import com.feuji.referenceservice.bean.CommonReferenceTypeBean;
import com.feuji.referenceservice.dto.ReferenceDto;
import com.feuji.referenceservice.entity.CommonReferenceTypeEntity;

public interface CommonReferenceType {

	public CommonReferenceTypeEntity getByTypeName(String typeName);

	public CommonReferenceTypeEntity save(CommonReferenceTypeBean commonReferenceTypeBean);

	List<ReferenceDto> getAllReferences();

	public String getNameById(int id);

	public CommonReferenceTypeBean getRefByTypeName(String typeName);
}
