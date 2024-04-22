package com.feuji.accountservice.serviceimpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.feuji.accountservice.bean.AccountBean;
import com.feuji.accountservice.bean.EmployeeBean;
import com.feuji.accountservice.dto.AccountDTO;
import com.feuji.accountservice.dto.UpdateAccountDto;
import com.feuji.accountservice.entity.AccountEntity;
import com.feuji.accountservice.exception.SaveUniqueAccountException;
import com.feuji.accountservice.repository.AccountRepository;
import com.feuji.accountservice.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired 
    private RestTemplate restTemplate;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public AccountEntity saveAccount(AccountBean accountBean) throws SaveUniqueAccountException {
		
		AccountEntity accountEntity1 = beanToEntity(accountBean);
		log.info("Save the data"+accountEntity1);
		AccountEntity	accountEntity2= accountRepository.findByAccountName(accountEntity1.getAccountName());
		if (accountEntity2 == null) {
			accountRepository.save(accountEntity1);
			
			return accountEntity1;
		} else {
			throw new SaveUniqueAccountException("Account Name already Exist");
		}


	}



	@Override
	public AccountEntity beanToEntity(AccountBean accountBean) {
		AccountEntity entity = new AccountEntity();
		entity.setAccountId(accountBean.getAccountId());
		entity.setAccountName(accountBean.getAccountName());
		entity.setOwnerId(accountBean.getOwnerId());
		entity.setRelationshipManagerId(accountBean.getRelationshipManagerId());
		entity.setBusinessDevelopmentManagerId(accountBean.getBusinessDevelopmentManagerId());
		entity.setParentAccountId(accountBean.getParentAccountId());
		entity.setAccountBuId(accountBean.getAccountBuId());
		entity.setPlannedStartDate(accountBean.getPlannedStartDate());
		entity.setPlannedEndDate(accountBean.getPlannedEndDate());
		entity.setActualStartDate(accountBean.getActualStartDate());
		entity.setActualEndDate(accountBean.getActualEndDate());
		entity.setAddress(accountBean.getAddress());
		entity.setCity(accountBean.getCity());
		entity.setState(accountBean.getState());
		entity.setZipcode(accountBean.getZipcode());
		entity.setCountry(accountBean.getCountry());
		entity.setIsRed(accountBean.getIsRed());
		entity.setAccountStatus(accountBean.getAccountStatus());
		entity.setComments(accountBean.getComments());
		entity.setIsDeleted(accountBean.getIsDeleted());
		entity.setUuId(accountBean.getUuId());
		entity.setCreatedBy(accountBean.getCreatedBy());
		entity.setCreatedOn(accountBean.getCreatedOn());
		entity.setModifiedBy(accountBean.getModifiedBy());
		entity.setModifiedOn(accountBean.getModifiedOn());
		return entity;
	}
	
	@Override
	public AccountEntity findByUUId(String uuId) {
		
		return accountRepository.findByuuId(uuId);
	}
	
	
	
	
	public List<EmployeeBean> getEmployeeBean() {
		 String url = "http://localhost:8082/api/employee/getAll";
		    HttpHeaders headers = new HttpHeaders();
	       headers.setContentType(MediaType.APPLICATION_JSON);
		   HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		    
		    ResponseEntity<List<EmployeeBean>> responseEntity = restTemplate.exchange(
		        url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<EmployeeBean>>() {}
		    );
		    
		    List<EmployeeBean> employeeBeans = responseEntity.getBody();
	    return employeeBeans;
	}


	@Override
	public List<AccountDTO> accountDto() {
		
		return accountRepository.accountDto();
	}

	@Override
	public List<UpdateAccountDto> fetchByUuID(String uuId) {
		try
		{
	
		List<UpdateAccountDto>   updatedData=accountRepository.fetchByUuID(uuId);
		log.info(" updatedData :" , updatedData);
		return  updatedData;
		}
		catch (Exception e) {

			log.info(e.getMessage());
		}
		return null;


	

}

	@Override
	public AccountEntity updateAccount(AccountBean accountBean) {
		AccountEntity accountEntity1 = beanToEntity(accountBean);
		if (accountBean == null) {
	        throw new IllegalArgumentException("Account bean object is null");
	    }
	    try {
	    	log.info("updating account  before from front end entity "+accountBean);
	        AccountEntity existingEntity = accountRepository.findByuuId(accountBean.getUuId());
	        
        log.info("updating account after find entity "+existingEntity);
	        AccountEntity savedEntity = accountRepository.save(accountEntity1);
	        log.info("updating account  after save "
	        		+ "entity "+ savedEntity);
	      return existingEntity;
	    } catch (IllegalArgumentException e) {
	        throw e; 
	    }
}
	@Override
	public AccountEntity delete(Integer accountId) {
		log.info("service method{}", accountId);
		AccountEntity optional = accountRepository.findById(accountId)
				.orElseThrow(() -> new IllegalArgumentException("id not found"));
		optional.setIsDeleted(true);
		 AccountBean accountBean = modelMapper.map(optional, AccountBean.class);
		AccountEntity deletedEmployee = updateAccount(accountBean);
		
		return deletedEmployee;


		
	}




	@Override
	public List<AccountEntity> getAllAcount() {
		
		return accountRepository.findAll();
	}


	
}
