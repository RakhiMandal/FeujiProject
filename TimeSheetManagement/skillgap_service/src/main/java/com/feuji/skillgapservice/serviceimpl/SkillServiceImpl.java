package com.feuji.skillgapservice.serviceimpl;

import java.util.ArrayList;
import java.util.IllformedLocaleException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.skillgapservice.bean.SkillBean;
import com.feuji.skillgapservice.commonconstants.CommonConstants;
import com.feuji.skillgapservice.dto.SkillNamesDto;
import com.feuji.skillgapservice.entity.SkillEntity;
import com.feuji.skillgapservice.exception.InputNotFoundException;
import com.feuji.skillgapservice.exception.RecordNotFoundException;
import com.feuji.skillgapservice.exception.SkillNotFoundException;
import com.feuji.skillgapservice.repository.SkillRepository;
import com.feuji.skillgapservice.service.SkillService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SkillServiceImpl implements SkillService {

	@Autowired
	public SkillRepository skillRepository;

	/**
	 * Saves a SkillBean object by converting it to its corresponding entity
	 * representation and persists it into the database through the SkillRepository.
	 * 
	 * @param skillBean The SkillBean object to be saved.
	 * @return The saved SkillBean object after conversion from its entity
	 *         representation.
	 * @throws SkillNotFoundException If the saved SkillEntity is null, indicating
	 *                                that the skill is not found.
	 * @throws NullPointerException   If the input SkillBean object is null.
	 */
	@Override
	public SkillBean saveSkill(SkillBean skillBean) {
	    log.info("Service saveSkill Method Start");
	    try {
	    	SkillEntity entity = null;
	        List<Optional<SkillEntity>> existingSkills = skillRepository.findBySkillName(skillBean.getSkillName());
	        log.info(existingSkills.toString());
	        for(Optional<SkillEntity> existingSkill:existingSkills)
	        {
	        	if (existingSkill.isPresent()&& existingSkill.get().getIsDeleted()==CommonConstants.FALSE 
		        		&& existingSkill.get().getTechinicalCategoryId()==skillBean.getTechinicalCategoryId()
		        		&& existingSkill.get().getSkillCategoryId() == skillBean.getSkillCategoryId() )
	        	{
		            throw new IllegalArgumentException("Skill with name '" + skillBean.getSkillName() + "' already exists");
		        }
		        else if (existingSkill.isPresent() && existingSkill.get().getIsDeleted()==CommonConstants.TRUE
		        		&& existingSkill.get().getTechinicalCategoryId()==skillBean.getTechinicalCategoryId()
		        		&& existingSkill.get().getSkillCategoryId() == skillBean.getSkillCategoryId())
		        { 
		        	existingSkill.get().setIsDeleted((byte)CommonConstants.FALSE); 
		        	existingSkill.get().setStatus((byte)CommonConstants.TRUE); 
		        	existingSkill.get().setDescription(skillBean.getDescription());
		        	existingSkill.get().setCreatedBy(skillBean.getCreatedBy());
		        	existingSkill.get().setModifiedBy(skillBean.getModifiedBy());
		        	existingSkill.get().setTechinicalCategoryId(skillBean.getTechinicalCategoryId());
		        	existingSkill.get().setSkillCategoryId(skillBean.getSkillCategoryId());
		        	entity = skillRepository.save(existingSkill.get()); 
		        	return entityToBean(entity);
		        	
		        }
	        }
	        
	        SkillEntity beanToEntity  = beanToEntity(skillBean);
	        entity = skillRepository.save(beanToEntity);
	        log.info("Service saveSkill Method End");
	        return entityToBean(entity); 
	    }catch(NullPointerException e)
	    {
	    	throw new IllformedLocaleException("failed to save");
	    }
	}

	/**
	 * Updates the details of a SkillBean object by modifying its corresponding
	 * entity representation and persisting the changes into the database through
	 * the SkillRepository.
	 * 
	 * @param skillBean The SkillBean object containing the updated details.
	 * @return The updated SkillBean object after persistence of changes.
	 * @throws SkillNotFoundException If no skill is found in the database with the
	 *                                provided UUID.
	 * @throws NullPointerException   If the input SkillBean object is null.
	 */
	@Override
	public SkillBean updateSkillDetails(SkillBean skillBean) {
		log.info("updateDetails Method Start:in SkillServiceImpl");
		if (skillBean != null) {
			Optional<SkillEntity> optionalEntity = skillRepository.findByUuid(skillBean.getUuid());
			if (optionalEntity.isPresent()) {
				SkillEntity entity = optionalEntity.get();
				entity.setDescription(skillBean.getDescription());
				entity.setModifiedBy(skillBean.getModifiedBy());
				entity.setIsDeleted(skillBean.getIsDeleted());
				SkillEntity savedEntity = skillRepository.save(entity);
				log.info("updateDetails Method End:in SkillServiceImpl");
				return entityToBean(savedEntity);
			} else {
				throw new SkillNotFoundException("Skill not found with UUID: " + skillBean.getUuid());
			}
		} else {
			throw new NullPointerException("skillBean  is null:in SkillServiceImpl");
		}

	}

	/**
	 * Retrieves all skills from the database and converts each SkillEntity object
	 * to its corresponding SkillBean representation.
	 * 
	 * @return A list of SkillBean objects containing all skills retrieved from the
	 *         database.
	 * @throws SkillNotFoundException If no skills are found in the database.
	 */
	@Override
	public List<SkillBean> getAllSkills() {
		log.info("getAllSkills Method Start:in SkillServiceImpl");

		List<SkillEntity> skillEntities = skillRepository.findAll();
		List<SkillBean> skillBeans = new ArrayList<>();
		if (skillEntities != null) {
			for (SkillEntity entity : skillEntities) {
				skillBeans.add(entityToBean(entity));
			}
			log.info("getAllSkills Method End:in SkillServiceImpl");
			return skillBeans;
		} else {
			throw new SkillNotFoundException("Skills not found");
		}

	}

	/**
	 * Retrieves skills from the database based on the provided technical category
	 * ID.
	 * 
	 * @param categoryId The ID of the technical category.
	 * @return A list of SkillBean objects containing skills associated with the
	 *         provided category ID.
	 * @throws SkillNotFoundException If no skills are found in the database with
	 *                                the provided category ID.
	 * @throws NullPointerException   If the provided category ID is null.
	 */
	@Override
	public List<SkillBean> getSkillsByTechCategoryId(int categoryId) {
		log.info("getSkillsByTechCategoryId Method Start:in SkillServiceImpl");
		if (categoryId != CommonConstants.FALSE) {
			List<SkillEntity> entityList = skillRepository.findByTechinicalCategoryId((long)categoryId);
			if (entityList != null) {
				List<SkillBean> beanList = new ArrayList<>();
				for (SkillEntity e : entityList) {
					beanList.add(entityToBean(e));
				}
				log.info("getSkillsByTechCategoryId Method End:in SkillServiceImpl");
				return beanList;
			} else {
				throw new SkillNotFoundException("Skills not found with this " + categoryId + ":in SkillServiceImpl");
			}
		} else {
			throw new NullPointerException("skillBean categoryId is null:in SkillServiceImpl");
		}

	}

	/**
	 * Retrieves a skill from the database based on the provided skill ID.
	 * 
	 * @param skillId The ID of the skill.
	 * @return The SkillBean object corresponding to the provided skill ID.
	 * @throws SkillNotFoundException If no skill is found in the database with the
	 *                                provided skill ID.
	 * @throws NullPointerException   If the provided skill ID is null.
	 */
	@Override
	public SkillBean getSkillBySkillId(int skillId) {
		log.info("getBySkillId Method Start:in SkillServiceImpl");
		if (skillId != CommonConstants.FALSE) {
			SkillEntity entity = skillRepository.findById(skillId).orElseThrow(
					() -> new SkillNotFoundException("Skill not found with id-" + skillId + ":in SkillServiceImpl"));
			log.info("getBySkillId Method End:in SkillServiceImpl");
			return entityToBean(entity);
		} else {
			throw new NullPointerException("skillBean skillId is null:in SkillServiceImpl");

		}
	}

	/**
	 * Retrieves skill names from the database based on the provided array of skill
	 * IDs.
	 * 
	 * @param skillIds An array of skill IDs.
	 * @return A list of SkillNamesDto objects containing skill names associated
	 *         with the provided skill IDs.
	 * @throws SkillNotFoundException If no records are found in the database with
	 *                                the provided skill IDs.
	 * @throws NullPointerException   If the provided array of skill IDs is null.
	 */
	@Override
	public List<SkillNamesDto> getSkillNamesBySkillId(int[] skillIds) {
		log.info("getSkillNames Method Start:in SkillServiceImpl");
		if (skillIds != null) {
			List<SkillNamesDto> skillNamesDtosList = skillRepository.getSkills(skillIds);
			if (skillNamesDtosList != null) {
				for (SkillNamesDto dto : skillNamesDtosList) {
					if (dto.getSkillType().equals(CommonConstants.PRIMARY)) {
						dto.setSkillType(CommonConstants.SKILLTYPEONE);
					} else {
						dto.setSkillType(CommonConstants.SKILLTYPETWO);
					}
				}
				log.info("getSkillNames Method End:in SkillServiceImpl");
				return skillNamesDtosList;
			} else {
				throw new SkillNotFoundException(
						"no record found with this id's: " + skillIds + " :in SkillServiceImpl");
			}
		} else {
			throw new NullPointerException("skillBean skillIds are null :in SkillServiceImpl");
		}

	}

	/**
	 * Retrieves a skill from the database based on the provided UUID.
	 * 
	 * @param uuid The UUID of the skill.
	 * @return The SkillBean object corresponding to the provided UUID.
	 * @throws SkillNotFoundException If no skill is found in the database with the
	 *                                provided UUID.
	 * @throws NullPointerException   If the provided UUID is null.
	 */
	@Override
	public SkillBean getSkillByUuid(String uuid) throws SkillNotFoundException {
		log.info(" getByUuid Method Start:in SkillServiceImpl");
		if (uuid != null) {
			SkillEntity skillEntity = skillRepository.findByUuid(uuid).orElseThrow(
					() -> new SkillNotFoundException("Skill not found with uuid-" + uuid + " :in SkillServiceImpl"));
			log.info("getByUuid Method End:in SkillServiceImpl");
			return entityToBean(skillEntity);

		} else {
			throw new NullPointerException("skillBean uuid is null:in SkillServiceImpl");
		}

	}

	/**
	 * Converts a SkillEntity object to its corresponding SkillBean representation.
	 * 
	 * @param entity The SkillEntity object to be converted.
	 * @return The SkillBean object converted from the provided SkillEntity.
	 * @throws NullPointerException If the provided SkillEntity object is null.
	 */
	public SkillBean entityToBean(SkillEntity entity) {
		if (entity != null) {
			log.info("entityToBean start:in SkillServiceImpl");
			SkillBean skillBean = new SkillBean();
			skillBean.setSkillId(entity.getSkillId());
			skillBean.setSkillName(entity.getSkillName());
			skillBean.setTechinicalCategoryId(entity.getTechinicalCategoryId());
			skillBean.setSkillCategoryId(entity.getSkillCategoryId());
			skillBean.setDescription(entity.getDescription());
			skillBean.setIsDeleted(entity.getIsDeleted());
			skillBean.setUuid(entity.getUuid());
			skillBean.setCreatedBy(entity.getCreatedBy());
			skillBean.setCreatedOn(entity.getCreatedOn());
			skillBean.setModifiedBy(entity.getModifiedBy());
			skillBean.setModifiedOn(entity.getModifiedOn());
			skillBean.setStatus(entity.getStatus());
			log.info("entityToBean end:in SkillServiceImpl");
			return skillBean;
		} else {
			throw new NullPointerException("failed to convert entity to bean: in SkillServiceImpl");
		}
	}

	/**
	 * Converts a SkillBean object to its corresponding SkillEntity representation.
	 * 
	 * @param bean The SkillBean object to be converted.
	 * @return The SkillEntity object converted from the provided SkillBean.
	 * @throws NullPointerException If the provided SkillBean object is null.
	 */
	public SkillEntity beanToEntity(SkillBean bean) {
		if (bean != null) {
			log.info("beanToEntity start:in SkillServiceImpl");
			SkillEntity entity = new SkillEntity();
			entity.setSkillId(bean.getSkillId());
			entity.setSkillName(bean.getSkillName());
			entity.setTechinicalCategoryId(bean.getTechinicalCategoryId());
			entity.setSkillCategoryId(bean.getSkillCategoryId());
			entity.setDescription(bean.getDescription());
			entity.setIsDeleted(bean.getIsDeleted());
			entity.setUuid(bean.getUuid());
			entity.setCreatedBy(bean.getCreatedBy());
			entity.setCreatedOn(bean.getCreatedOn());
			entity.setModifiedBy(bean.getModifiedBy());
			entity.setModifiedOn(bean.getModifiedOn());
			entity.setStatus(bean.getStatus());
			entity.setIsDeleted((byte)CommonConstants.FALSE);
			log.info("beanToEntity end:in SkillServiceImpl");
			return entity;
		} else {
			throw new NullPointerException("failed to convert entity to bean: in SkillServiceImpl");
		}
	}

	
	@Override
	public List<SkillBean> getSkillsByTechCategoryIdForEmployee(int categoryId) {
		log.info("getSkillsByTechCategoryId Method Start:in SkillServiceImpl");
		if (categoryId != CommonConstants.FALSE) {
			List<SkillEntity> entityList = skillRepository.findByTechinicalCategoryIdForEmployee(categoryId);
			if (entityList != null) {
				List<SkillBean> beanList = new ArrayList<>();
				for (SkillEntity e : entityList) {
					beanList.add(entityToBean(e));
				}
				log.info("getSkillsByTechCategoryId Method End:in SkillServiceImpl");
				return beanList;
			} else {
				throw new SkillNotFoundException("Skills not found with this " + categoryId + ":in SkillServiceImpl");
			}
		} else {
			throw new NullPointerException("skillBean categoryId is null:in SkillServiceImpl");
		}

	}
	
	@Override
	public List<SkillEntity> updateStatusBySkillId(List<Integer> skillIds, List<Byte> status) throws RecordNotFoundException 
	{
		List<SkillEntity> findAllById = skillRepository.findAllById(skillIds);
		log.info(skillIds.toString());
		log.info(status.toString());

        if (findAllById!=null) {
            List<SkillEntity> skillEntity = findAllById;
            for (int index=0;index<skillEntity.size();index++) {
            	for(int index2=0;index2<skillIds.size();index2++)
            	{
            		if(skillEntity.get(index).getSkillId()==skillIds.get(index2))
                    	skillEntity.get(index).setStatus(status.get(index2));
            	}
			}
             return skillRepository.saveAll(skillEntity);
        } else {
        	throw new RecordNotFoundException("Skill with skillId " + skillIds + " does not exist.");
        }
	}

	@Override
	public List<SkillEntity> deleteSkillBySubSkillCategoryId(Long subSkillCategoryId, Byte isDeleted)
	{
		if(subSkillCategoryId!=null)
		{
			skillRepository.deleteSkill(subSkillCategoryId,isDeleted);
			List<SkillEntity> findByTechinicalCategoryId = skillRepository.findDeletedSkillsByTechinicalCategoryId(subSkillCategoryId);
			return  findByTechinicalCategoryId;
		}
		else {
			throw new InputNotFoundException("input is null");
		}
	}


}
