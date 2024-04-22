package com.feuji.employeeservice.service;

import java.util.List;

import com.feuji.employeeservice.bean.EmployeeBean;
import com.feuji.employeeservice.dto.AddEmployee;
import com.feuji.employeeservice.dto.EmployeeDisplayDto;
import com.feuji.employeeservice.dto.EmployeeDto;
import com.feuji.employeeservice.dto.SaveEmployeeDto;
import com.feuji.employeeservice.dto.SaveEmployeeUserDto;
import com.feuji.employeeservice.dto.UpadteEmployeeDto;
import com.feuji.employeeservice.entity.EmployeeEntity;
import com.feuji.employeeservice.exception.RecordsNotFoundException;

public interface EmployeeService {

//	public EmployeeEntity saveEmployee(EmployeeBean employeeBean);

	public EmployeeEntity getById(Integer id);

	public List<EmployeeDto> getByUserEmpId(Integer id);

	public EmployeeEntity getByUuid(String uuid);

	public void updateEmployeeDetails(EmployeeEntity updateEmpolyee, Integer id) throws Throwable;
	public List<EmployeeEntity> getAllEmployees();
	public boolean isEmployeeCodeUnique(String empCode);

	public EmployeeBean getReportingMngIdByEmpId(Integer id);
	
	public List<AddEmployee> getAllReportingManager();
	
	
	List<EmployeeEntity> searchEmployeesByFirstName(String firstName);
	
	public List<SaveEmployeeDto> getByReferenceTypeId(Integer referenceTypeId);
//	public List<ReferenceDto> getAll();
	
	List<EmployeeDisplayDto> getEmployeeDetails();
	List<UpadteEmployeeDto> getEmployeeDetailByUUiD( String uuid);
	
	public EmployeeEntity updateEmployee(EmployeeBean employeeBean) ;

	EmployeeEntity delete(Integer employeeId);
	public EmployeeEntity saveEmployeeAndUser(SaveEmployeeUserDto employeeUserDTO);
	public  boolean isEmailUnique(String email);
	public EmployeeBean findByEmail(String email) throws RecordsNotFoundException;
	
}