package com.feuji.accountservice.service;

import java.util.List;

import com.feuji.accountservice.bean.AccountBean;
import com.feuji.accountservice.bean.EmployeeBean;
import com.feuji.accountservice.dto.AccountDTO;
import com.feuji.accountservice.dto.UpdateAccountDto;
import com.feuji.accountservice.entity.AccountEntity;
import com.feuji.accountservice.exception.SaveUniqueAccountException;

public interface AccountService {

	AccountEntity saveAccount(AccountBean accountBean) throws SaveUniqueAccountException;

	AccountEntity beanToEntity(AccountBean accountBean);

	public List<AccountEntity> getAllAcount();

	AccountEntity findByUUId(String uuId);

	List<EmployeeBean> getEmployeeBean();

	List<AccountDTO> accountDto();

	List<UpdateAccountDto> fetchByUuID(String uuId);

	public AccountEntity updateAccount(AccountBean accountBean);
	AccountEntity delete(Integer accountId);

;


}
