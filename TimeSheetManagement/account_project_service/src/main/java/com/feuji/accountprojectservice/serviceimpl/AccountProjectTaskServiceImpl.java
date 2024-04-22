package com.feuji.accountprojectservice.serviceimpl;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.feuji.accountprojectservice.bean.AccountTaskBean;
import com.feuji.accountprojectservice.entity.AccountProjectTaskEntity;
import com.feuji.accountprojectservice.exception.IdNotFoundException;
import com.feuji.accountprojectservice.repository.AccountProjectTaskRepo;

import com.feuji.accountprojectservice.service.AccountProjectTaskService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountProjectTaskServiceImpl implements AccountProjectTaskService{
	@Autowired
	private AccountProjectTaskRepo repository;

//	@Autowired
//	private GenerateUUID generateUUID;

	public AccountProjectTaskEntity convertBeanToEntity(AccountTaskBean bean) {
		AccountProjectTaskEntity entity = new AccountProjectTaskEntity();
		entity.setTaskId(bean.getTaskId());
		entity.setTaskTypeId(bean.getTaskTypeId());
		entity.setTask(bean.getTask());
		entity.setTaskDescription(bean.getTaskDescription());
		entity.setIsActive(bean.getIsActive());
		entity.setIsDeleted(bean.getIsDeleted());
		entity.setUuid(bean.getUuid());
		entity.setCreatedBy(bean.getCreatedBy());
		entity.setCreatedOn(bean.getCreatedOn());
		entity.setModifiedBy(bean.getModifiedBy());
		entity.setModifiedOn(bean.getModifiedOn());
		return entity;
	}

	public AccountTaskBean convertEntityToBean(AccountProjectTaskEntity entity) {
		AccountTaskBean bean = new AccountTaskBean();
		bean.setTaskId(entity.getTaskId());
		bean.setTaskTypeId(entity.getTaskTypeId());
		bean.setTask(entity.getTask());
		bean.setTaskDescription(entity.getTaskDescription());
		bean.setIsActive(entity.getIsActive());
		bean.setIsDeleted(entity.getIsDeleted());
		bean.setUuid(entity.getUuid());
		bean.setCreatedBy(entity.getCreatedBy());
		bean.setCreatedOn(entity.getCreatedOn());
		bean.setModifiedBy(entity.getModifiedBy());
		bean.setModifiedOn(entity.getModifiedOn());
		return bean;
	}

	public List<AccountTaskBean> convertEntitiesToBeans(List<AccountProjectTaskEntity> entities) {
		List<AccountTaskBean> beans = new ArrayList<AccountTaskBean>();
		AccountTaskBean bean = null;
		for (AccountProjectTaskEntity entity : entities) {
			bean = new AccountTaskBean();
			bean.setTaskId(entity.getTaskId());
			bean.setTaskTypeId(entity.getTaskTypeId());
			bean.setTask(entity.getTask());
			bean.setTaskDescription(entity.getTaskDescription());
			bean.setIsActive(entity.getIsActive());
			bean.setIsDeleted(entity.getIsDeleted());
			bean.setUuid(entity.getUuid());
			bean.setCreatedBy(entity.getCreatedBy());
			bean.setCreatedOn(entity.getCreatedOn());
			bean.setModifiedBy(entity.getModifiedBy());
			bean.setModifiedOn(entity.getModifiedOn());

			beans.add(bean);
		}
		return beans;
	}

//	@Override
//	public AccountTaskBean save(AccountTaskBean bean) {
//		log.info("service start");
//		try {
//			AccountProjectTaskEntity entity = convertBeanToEntity(bean);
//			if (convertBeanToEntity(bean).getUuid() == null) {
//
//				String newUuid = generateUUID.generateUniqueId();
//				log.info(newUuid);
//				entity.setUuid(newUuid);
//
//			}
//			if (convertBeanToEntity(bean).getCreatedOn() == null) {
//
//				entity.setCreatedOn(new Timestamp(System.currentTimeMillis()));
//
//			}
//			if (convertBeanToEntity(bean).getModifiedOn() == null) {
//
//				entity.setModifiedOn(new Timestamp(System.currentTimeMillis()));
//
//			}
//
//			AccountProjectTaskEntity save = repository.save(entity);
//
//			return convertEntityToBean(save);
//
//		} catch (NullPointerException e) {
//			throw new NullPointerException(e.getMessage());
//		}
//	}
//
//	@Override
//	public List<AccountProjectTaskBean> getByTaskTypeId(int taskTypeId) throws IdNotFoundException {
//		try {
//			List<AccountProjectTaskEntity> entities = repository.findByTaskTypeId(taskTypeId);
//			List<AccountProjectTaskBean> beans = convertEntitiesToBeans(entities);
//			return beans;
//
//		} catch (Exception e) {
//			throw new IdNotFoundException("Invalid taskid");
//		}
//	}

	@Override
	public AccountTaskBean getById(Integer taskId) throws IdNotFoundException {
		try {
			Optional<AccountProjectTaskEntity> entity = repository.findById(taskId);
			AccountTaskBean bean = convertEntityToBean(entity.get());
			if (bean == null) {
				throw new IdNotFoundException("Invalid taskid");
			}else {
				
			return bean;
			}
		} catch (Exception e) {
			throw new IdNotFoundException("Invalid taskid");
		}
		
	}
}