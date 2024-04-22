package com.feuji.accountprojectservice.service;

import java.util.List;

import com.feuji.accountprojectservice.bean.AccountTaskBean;
import com.feuji.accountprojectservice.exception.IdNotFoundException;

public interface AccountProjectTaskService {
//	public AccountTaskBean save(AccountTaskBean bean);
//	public List<AccountTaskBean> getByTaskTypeId(int taskTypeId) throws IdNotFoundException;
	AccountTaskBean getById(Integer taskId) throws IdNotFoundException;
}
