package com.feuji.accountprojectservice.serviceimpl;


import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.feuji.accountprojectservice.bean.AccountBean;
import com.feuji.accountprojectservice.bean.AccountProjectsBean;
import com.feuji.accountprojectservice.bean.EmployeeBean;
import com.feuji.accountprojectservice.dto.AccountDto;
import com.feuji.accountprojectservice.dto.UpdateAccountProjectDto;
import com.feuji.accountprojectservice.entity.AccountProjectsEntity;
import com.feuji.accountprojectservice.exception.AccountProjectNotFoundException;
import com.feuji.accountprojectservice.exception.UUIDNotFoundException;
import com.feuji.accountprojectservice.repository.AccountProjectsRepo;
import com.feuji.accountprojectservice.service.AccountProjectsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountProjectsServiceImpl implements AccountProjectsService{
private static Logger log = LoggerFactory.getLogger(AccountProjectsServiceImpl.class);
	
	@Autowired 
    private RestTemplate restTemplate;
	@Autowired
	private AccountProjectsRepo  accountProjectsRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	
    @Override
	public List<AccountBean> getAccountBean() {
	    String url = "http://localhost:8081/api/accountSave/getAll";
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> httpEntity = new HttpEntity<>(headers);
	    
	    ResponseEntity<List<AccountBean>> responseEntity = restTemplate.exchange(
	        url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<AccountBean>>() {}
	    );
	    
	    List<AccountBean> accountBeans = responseEntity.getBody();
	    return accountBeans;
	}
    
    
    
    

	

	@Override
	public AccountProjectsEntity save(AccountProjectsBean accountProjectsBean) {
	
		AccountProjectsEntity	accountProjectsEntity = modelMapper.map(accountProjectsBean, AccountProjectsEntity.class);
		log.info("saving account project entity "+accountProjectsEntity);
		accountProjectsEntity = accountProjectsRepo.save(accountProjectsEntity);
		 return accountProjectsEntity;
	}




	@Override
	public AccountProjectsBean findByUuid(String uuid) {
	    AccountProjectsEntity accountProjectEntity = accountProjectsRepo.findByUuid(uuid)
	            .orElseThrow(() -> new IllegalArgumentException("Project not found with id-" + uuid));

	    return modelMapper.map(accountProjectEntity, AccountProjectsBean.class);
	}



	@Override
	public AccountProjectsBean updateAccountProject(AccountProjectsBean accountProjectsBean) {
	    if (accountProjectsBean == null) {
	        throw new IllegalArgumentException("Account project bean object is null");
	    }
	    try {
	        // Retrieve the existing project entity by UUID
	        AccountProjectsEntity existingProjectEntity = accountProjectsRepo.findByUuid(accountProjectsBean.getUuid())
	                .orElseThrow(() -> new IllegalArgumentException("Project not found with UUID-" + accountProjectsBean.getUuid()));

	        log.info("Updating account project entity " + existingProjectEntity);

	        // Convert the updated bean to an entity
	        AccountProjectsEntity updatedProjectEntity = beanToEntity(accountProjectsBean);

	        // Set the ID of the existing entity to ensure the update operation targets the correct record
	        updatedProjectEntity.setAccountProjectId(existingProjectEntity.getAccountProjectId());

	        // Set the modified timestamp
	        updatedProjectEntity.setModifiedOn(new Timestamp(System.currentTimeMillis()));

	        // Save the updated entity
	        AccountProjectsEntity savedEntity = accountProjectsRepo.save(updatedProjectEntity);

	        // Convert the updated entity back to a bean and return it
	        return entityToBean(savedEntity);
	    } catch (IllegalArgumentException e) {
	        throw e; 
	    }
	}
	



	@Override
	public List<AccountProjectsBean> getAllAccountProjects() {
	    List<AccountProjectsEntity> accountProjectsEntity = accountProjectsRepo.findAll();
	    
	    return accountProjectsEntity.stream()
	            .map(entity -> modelMapper.map(entity, AccountProjectsBean.class))
	            .collect(Collectors.toList());
	}





	
//	 conversion entity to bean and visa versa
		public AccountProjectsBean entityToBean(AccountProjectsEntity entity) {
			AccountProjectsBean accountProjectsBean = new AccountProjectsBean();

		
			accountProjectsBean.setAccountProjectId(entity.getAccountProjectId());	
		accountProjectsBean.setAccountId(entity.getAccountId());
			accountProjectsBean.setActualEndDate(entity.getActualEndDate());
			accountProjectsBean.setActualStartDate(entity.getActualStartDate());
			accountProjectsBean.setCreatedBy(entity.getCreatedBy());
			accountProjectsBean.setCreatedOn(entity.getCreatedOn());
			accountProjectsBean.setIsActive(entity.getIsActive());
			accountProjectsBean.setIsDeleted(entity.getIsDeleted());
			accountProjectsBean.setIsRed(entity.getIsRed());
			accountProjectsBean.setModifiedBy(entity.getModifiedBy());
			accountProjectsBean.setModifiedOn(entity.getModifiedOn());
			accountProjectsBean.setUuid(entity.getUuid());
			accountProjectsBean.setProjectStatus(entity.getProjectStatus());
			accountProjectsBean.setProjectAId(entity.getProjectAId());
			accountProjectsBean.setProjectManagerId(entity.getProjectManagerId());
			accountProjectsBean.setPriority(entity.getPriority());
			accountProjectsBean.setPlannedStartDate(entity.getPlannedStartDate());
			accountProjectsBean.setPlannedEndDate(entity.getPlannedEndDate());
			accountProjectsBean.setNoOfBillingHours(entity.getNoOfBillingHours());
			accountProjectsBean.setProjectName(entity.getProjectName());
			return accountProjectsBean;
		}

		public AccountProjectsEntity beanToEntity(AccountProjectsBean accountProjectsBean) {
		    AccountProjectsEntity accountProjectsEntity = new AccountProjectsEntity();

		    accountProjectsEntity.setAccountProjectId(accountProjectsBean.getAccountProjectId());
		    accountProjectsEntity.setAccountId(accountProjectsBean.getAccountId());
		    accountProjectsEntity.setActualEndDate(accountProjectsBean.getActualEndDate());
		    accountProjectsEntity.setActualStartDate(accountProjectsBean.getActualStartDate());
		    accountProjectsEntity.setCreatedBy(accountProjectsBean.getCreatedBy());
		    accountProjectsEntity.setCreatedOn(accountProjectsBean.getCreatedOn());
		    accountProjectsEntity.setIsActive(accountProjectsBean.getIsActive());
		    accountProjectsEntity.setIsDeleted(accountProjectsBean.getIsDeleted());
		    accountProjectsEntity.setIsRed(accountProjectsBean.getIsRed());
		    accountProjectsEntity.setModifiedBy(accountProjectsBean.getModifiedBy());
		    accountProjectsEntity.setModifiedOn(accountProjectsBean.getModifiedOn());
		    accountProjectsEntity.setUuid(accountProjectsBean.getUuid());
		    accountProjectsEntity.setProjectStatus(accountProjectsBean.getProjectStatus());
		    accountProjectsEntity.setProjectAId(accountProjectsBean.getProjectAId());
		    accountProjectsEntity.setProjectManagerId(accountProjectsBean.getProjectManagerId());
		    accountProjectsEntity.setPriority(accountProjectsBean.getPriority());
		    accountProjectsEntity.setPlannedStartDate(accountProjectsBean.getPlannedStartDate());
		    accountProjectsEntity.setPlannedEndDate(accountProjectsBean.getPlannedEndDate());
		    accountProjectsEntity.setNoOfBillingHours(accountProjectsBean.getNoOfBillingHours());
		    accountProjectsEntity.setProjectName(accountProjectsBean.getProjectName());

		    return accountProjectsEntity;
		}





		@Override
		public List<EmployeeBean> getEmployeeBean() {
			   String url = "http://localhost:8082/api/employee/getAll";
			    HttpHeaders headers = new HttpHeaders();
		       headers.setContentType(MediaType.APPLICATION_JSON);
			   HttpEntity<String> httpEntity = new HttpEntity<>(headers);
			    
			    ResponseEntity<List<EmployeeBean>> responseEntity = restTemplate.exchange(
			        url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<EmployeeBean>>() {}
			    );
			    
			    List<EmployeeBean> employeeBeans = responseEntity.getBody();
			    
			    List<EmployeeBean> filteredEmployees = employeeBeans.stream()
			            .filter(employee -> employee.getDesignation().equalsIgnoreCase("Manager" ))
			            .collect(Collectors.toList());
		    return filteredEmployees;
		}





	    @Override
	    public List<AccountDto> accountProjectDto() {
	        return accountProjectsRepo.accountProjectDto();
	    }
	    
		@Override
		public List<Integer> getAccountProjectIdByAccountId(Integer accountId) {
		return accountProjectsRepo.getAccountProjectIdByAccountId(accountId);
		}








		
		@Override
		public String updateDeleteStatus(Integer accountProjectId) {
		    try {
		        accountProjectsRepo.updateIsDeleted(accountProjectId);
		        return "updated isDeleted successfully";
		    } catch(Exception e) {
		        e.printStackTrace(); // Log the exception
		        return null;
		    }
		}







		@Override
		public AccountProjectsBean getAccountProjectBean(Integer id) {
			AccountProjectsEntity accountProjectsEntity = accountProjectsRepo.findById(id).orElseThrow();
			return modelMapper.map(accountProjectsEntity, AccountProjectsBean.class);
		}


		 @Override
		    public List<UpdateAccountProjectDto> getAccountProjectUpdate(String uuid) {
		        return accountProjectsRepo.accountProjectUpdate(uuid);
		    }


		 @Override
			public AccountProjectsEntity delete(Integer accountProjectId) {
				log.info("service method{}", accountProjectId);
				AccountProjectsEntity optional = accountProjectsRepo.findById(accountProjectId)
						.orElseThrow(() -> new IllegalArgumentException("id not found"));
				optional.setIsDeleted(true);
				AccountProjectsBean accountBean = modelMapper.map(optional, AccountProjectsBean.class);
				 AccountProjectsBean deletedEmployee = updateAccountProject(accountBean);
				 AccountProjectsEntity accountProjectsEntity = modelMapper.map(deletedEmployee, AccountProjectsEntity.class);
				return accountProjectsEntity;


				
			}
}


