package com.feuji.referenceservice.service;

import java.util.List;

import com.feuji.referenceservice.bean.CommonReferenceDetailsBean;
import com.feuji.referenceservice.bean.CommonReferenceTypeBean;
import com.feuji.referenceservice.bean.ReferenceDetailsBean;
import com.feuji.referenceservice.bean.TechnicalSkillsBean;
import com.feuji.referenceservice.exception.CategoryNotFoundException;
import com.feuji.referenceservice.exception.InvalidInputException;
import com.feuji.referenceservice.exception.NameNotFoundException;
import com.feuji.referenceservice.exception.NoRecordFoundException;
import com.feuji.referenceservice.exception.RecordNotFoundException;
import com.feuji.referenceservice.exception.ReferenceNotFoundException;
import com.feuji.referenceservice.exception.TechnicalSkillsNotFoundException;

public interface CommonReferenceDetails {
	public List<ReferenceDetailsBean> getDetailsByTypeId(String typeName);

	public CommonReferenceDetailsBean getReferenceById(Integer id);

	public CommonReferenceDetailsBean addSubSkillcategory(CommonReferenceDetailsBean bean)
			throws ReferenceNotFoundException;

	public CommonReferenceTypeBean saveReferenceDetails(CommonReferenceDetailsBean referenceDetailsBean);

	public CommonReferenceDetailsBean updateIsDeleted(CommonReferenceDetailsBean commonReferenceDetailsBean)
			throws InvalidInputException;

	public CommonReferenceDetailsBean deleteSkillCategory(String skillCategory, Byte true1)
			throws RecordNotFoundException, NoRecordFoundException, InvalidInputException;

	public int getIdByName(String name) throws NameNotFoundException;

	public String getByid(int id);

	public CommonReferenceDetailsBean deleteSubSkill(Integer referenceDetailId, Byte isDeleted)
			throws RecordNotFoundException, NoRecordFoundException, InvalidInputException;

	public List<TechnicalSkillsBean> getRefDetailsByTypeId(String typeName) throws TechnicalSkillsNotFoundException;

	public List<String> getCategories(String category) throws CategoryNotFoundException;
}
