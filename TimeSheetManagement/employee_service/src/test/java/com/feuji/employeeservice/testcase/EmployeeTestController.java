package com.feuji.employeeservice.testcase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.feuji.employeeservice.bean.EmployeeBean;
import com.feuji.employeeservice.controller.EmployeeController;
import com.feuji.employeeservice.dto.AddEmployee;
import com.feuji.employeeservice.dto.EmployeeDisplayDto;
import com.feuji.employeeservice.dto.EmployeeDto;
import com.feuji.employeeservice.dto.SaveEmployeeDto;
import com.feuji.employeeservice.dto.SaveEmployeeUserDto;
import com.feuji.employeeservice.entity.EmployeeEntity;
import com.feuji.employeeservice.repository.EmployeeRepository;
import com.feuji.employeeservice.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
public class EmployeeTestController {
	@Mock
private EmployeeService employeeService;
	@Mock
	private EmployeeRepository employeeRepository;
	@InjectMocks
	private EmployeeController employeeController;
//	@Test
//	public void testsaveEmployee() {
//		EmployeeBean employeeBean=EmployeeBean.builder().employeeId((Integer) 101).email("").firstName("").gender(1).build();
//		employeeService.saveEmployee(employeeBean);
//		verify(employeeService,times(1)).saveEmployee(employeeBean);
//		
//	}
	
	@Test
	public void testsaveEmployeeAdUser() {
		SaveEmployeeUserDto employeeUserDto = new SaveEmployeeUserDto();
        // Set up the necessary data in the DTO
        
        // Create a mock EmployeeEntity object (expected return value)
        EmployeeEntity expectedEmployeeEntity = new EmployeeEntity();
        // Set up the expected data in the entity
        
        // Stub the behavior of the employeeService.saveEmployeeAndUser() method
        when(employeeService.saveEmployeeAndUser(employeeUserDto)).thenReturn(expectedEmployeeEntity);
        
        // Call the method under test
        ResponseEntity<EmployeeEntity> responseEntity = employeeController.saveEmployee(employeeUserDto);
        
        // Verify that the method was called with the correct argument
        verify(employeeService).saveEmployeeAndUser(employeeUserDto);
        
        // Assert the response status code
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        
        // Assert the returned entity
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedEmployeeEntity, responseEntity.getBody());
    }
	
	
	@Test
	public void testupdateEmployee() throws Throwable {
		EmployeeEntity employeeBean=EmployeeEntity.builder().employeeId((Integer) 101).email("").firstName("").gender(1).build();
		employeeService.updateEmployeeDetails(employeeBean, (Integer)101);
		verify(employeeService,times(1)).updateEmployeeDetails(employeeBean, (Integer)101);;
		
	}
	@Test
	public void testgetByIdEmployee() {
		EmployeeEntity employeeBean=EmployeeEntity.builder().employeeId((Integer) 101).email("").firstName("").gender(1).build();
		 when(employeeService.getById((Integer)101)).thenReturn(employeeBean);

	        // Calling the method to test
	       
	        EmployeeEntity employeeEntitydata=employeeService.getById((Integer)101);

	        // Assertions
	        assertThat(employeeEntitydata).isNotNull();
	        assertThat(employeeEntitydata.getEmployeeId()).isEqualTo(101);
	}
    @Test
    public void testGetByReferenceTypeId() {
        // Mocking input parameters
        Integer referenceTypeId = 123; // example reference type ID

        // Mocking expected output
        List<SaveEmployeeDto> expectedEmployees = new ArrayList<>();
        // Add some example SaveEmployeeDto objects to the list

        // Stubbing the behavior of the employeeRepository.findByReferenceTypeId() method
        when(employeeService.getByReferenceTypeId(referenceTypeId)).thenReturn(expectedEmployees);

        // Calling the method under test
        List<SaveEmployeeDto> actualEmployees = employeeService.getByReferenceTypeId(referenceTypeId);

        // Verifying that the employeeRepository.findByReferenceTypeId() method was called with the correct argument
        verify(employeeService).getByReferenceTypeId(referenceTypeId);

        // Asserting that the actual result matches the expected result
        assertEquals(expectedEmployees.size(), actualEmployees.size());
        // You might want to add more detailed assertions based on your specific requirements
    }
    
    @Test
    public void testCheckEmailUnique_UniqueEmail() {
        // Mocking input parameters
        String uniqueEmail = "unique@example.com";

        // Stubbing the behavior of the employeeService.isEmailUnique() method
        when(employeeService.isEmailUnique(uniqueEmail)).thenReturn(true);

        // Calling the method under test
        ResponseEntity<?> responseEntity = employeeController.checkEmailUnique(uniqueEmail);

        // Verifying that the employeeService.isEmailUnique() method was called with the correct argument
        verify(employeeService).isEmailUnique(uniqueEmail);

        // Asserting that the response status code is OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Asserting that the response body is "true"
        assertEquals("true", responseEntity.getBody());
    }
    
    @Test
    public void testGetReportingMngIdByEmpId_ReportingManagerFound() {
        // Mocking input parameters
        Integer employeeId = 123; // Example employee ID

        // Mocking behavior of employeeService.getReportingMngIdByEmpId() to return a non-null EmployeeBean
        EmployeeBean employeeBean = new EmployeeBean();
        employeeBean.setReportingManagerId(456); // Example reporting manager ID
        when(employeeService.getReportingMngIdByEmpId(employeeId)).thenReturn(employeeBean);

        // Calling the method under test
        ResponseEntity<EmployeeBean> responseEntity = employeeController.getReportingMngIdByEmpId(employeeId);

        // Verifying that employeeService.getReportingMngIdByEmpId() was called with the correct argument
        verify(employeeService).getReportingMngIdByEmpId(employeeId);

        // Asserting that the response status code is OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Asserting that the response body contains the expected EmployeeBean with a reporting manager ID
        assertNotNull(responseEntity.getBody());
        assertEquals(456, responseEntity.getBody().getReportingManagerId());
    }
 
    @Test
    public void testGetAllEmployees_Success() {
        // Mocking the behavior of employeeService.getAllEmployees() to return a list of employee entities
        List<EmployeeEntity> employeeEntities = new ArrayList<>();
        // Add some EmployeeEntity objects to the list
        when(employeeService.getAllEmployees()).thenReturn(employeeEntities);

        // Calling the method under test
        ResponseEntity<List<EmployeeEntity>> responseEntity = employeeController.getAllEmployees();

        // Verifying that employeeService.getAllEmployees() was called
        verify(employeeService).getAllEmployees();

        // Asserting that the response status code is OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Asserting that the response body contains the list of employee entities
        assertEquals(employeeEntities, responseEntity.getBody());
    }
    
    
    @Test
    public void testGetEmployeeByUserEmpId_Success() {
        // Mocking the input parameter
        Integer userEmpId = 123; // Example user employee ID

        // Mocking the behavior of employeeService.getByUserEmpId() to return a list of employee DTOs
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        // Add some EmployeeDto objects to the list
        when(employeeService.getByUserEmpId(userEmpId)).thenReturn(employeeDtos);

        // Calling the method under test
        List<EmployeeDto> result = employeeController.getEmployeeByUserEmpId(userEmpId);

        // Verifying that employeeService.getByUserEmpId() was called
        verify(employeeService).getByUserEmpId(userEmpId);

        // Asserting that the result matches the expected list of employee DTOs
        assertEquals(employeeDtos, result);
    }
    
    @Test
    public void testGetAllReportingManagers_Success() {
        // Mocking the behavior of employeeService.getAllReportingManager() to return a list of AddEmployee
        List<AddEmployee> reportingManagers = new ArrayList<>();
        // Add some AddEmployee objects to the list
        when(employeeService.getAllReportingManager()).thenReturn(reportingManagers);

        // Calling the method under test
        List<AddEmployee> result = employeeController.getAllReportingManagers();

        // Verifying that employeeService.getAllReportingManager() was called
        verify(employeeService).getAllReportingManager();

        // Asserting that the result matches the expected list of reporting managers
        assertEquals(reportingManagers, result);
    }

    @Test
    public void testGetEmployeeDetails_Exception() {
        // Mocking the behavior of employeeService.getEmployeeDetails() to throw an exception
        when(employeeService.getEmployeeDetails()).thenThrow(new RuntimeException("Test exception"));

        // Calling the method under test
        ResponseEntity<List<EmployeeDisplayDto>> responseEntity = employeeController.getEmployeeDetails();

        // Verifying that employeeService.getEmployeeDetails() was called
        verify(employeeService).getEmployeeDetails();

        // Asserting that the response status code is INTERNAL SERVER ERROR
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        // Asserting that the response body is null
        assertNull(responseEntity.getBody());
    }
    
    
    @Test
    public void testSearchEmployees_Success() {
        // Mocking input parameters
        String firstName = "John";

        // Mocking the behavior of employeeService.searchEmployeesByFirstName() to return a list of EmployeeEntity
        List<EmployeeEntity> employees = new ArrayList<>();
        // Add some EmployeeEntity objects to the list
        when(employeeService.searchEmployeesByFirstName(firstName)).thenReturn(employees);

        // Calling the method under test
        ResponseEntity<List<EmployeeEntity>> responseEntity = employeeController.searchEmployees(firstName);

        // Verifying that employeeService.searchEmployeesByFirstName() was called
        verify(employeeService).searchEmployeesByFirstName(firstName);

        // Asserting that the response status code is OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Asserting that the response body contains the list of employees
        assertEquals(employees, responseEntity.getBody());
    }

    
    @Test
    public void testDeleteEmployee_Exception() {
        // Mocking input parameters
        Integer employeeId = 123; // Example employee ID

        // Mocking the behavior of employeeService.delete() to throw an exception
        when(employeeService.delete(employeeId)).thenThrow(new RuntimeException("Test exception"));

        // Calling the method under test
        ResponseEntity<EmployeeEntity> responseEntity = employeeController.delete(employeeId);

        // Verifying that employeeService.delete() was called
        verify(employeeService).delete(employeeId);

        // Asserting that the response status code is INTERNAL SERVER ERROR
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        // Asserting that the response body is null
        assertNull(responseEntity.getBody());
    }
    
    }




