package com.feuji.accountprojectservice.service;

import java.util.List;


import com.feuji.accountprojectservice.bean.AccountBean;
import com.feuji.accountprojectservice.bean.AccountProjectsBean;
import com.feuji.accountprojectservice.bean.EmployeeBean;
import com.feuji.accountprojectservice.dto.AccountDto;
import com.feuji.accountprojectservice.dto.UpdateAccountProjectDto;
import com.feuji.accountprojectservice.entity.AccountProjectsEntity;


public interface AccountProjectsService {
	
public AccountProjectsEntity save(AccountProjectsBean accountProjectsBean);
	
	public AccountProjectsBean findByUuid(String uuid);
	
	public AccountProjectsBean updateAccountProject(AccountProjectsBean accountProjectsBean) ;
	
	public List<AccountProjectsBean> getAllAccountProjects();
	
	public  List<AccountBean>getAccountBean();
	
	public  List<EmployeeBean>getEmployeeBean();
	
	List<AccountDto> accountProjectDto();
	
	List<Integer> getAccountProjectIdByAccountId(Integer accountId);
	
	String updateDeleteStatus(Integer accountProjectId);
	
	public AccountProjectsBean getAccountProjectBean(Integer id);
	
	
	List<UpdateAccountProjectDto> getAccountProjectUpdate(String uuid);
	
	AccountProjectsEntity delete(Integer accountProjectId);
}
