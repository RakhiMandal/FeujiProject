package com.feuji.employeeskillservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.feuji.employeeskillservice.bean.EmployeeSkillBean;
import com.feuji.employeeskillservice.bean.EmployeeSkillGet;
import com.feuji.employeeskillservice.bean.EmployeeUiBean;
import com.feuji.employeeskillservice.controller.EmployeeSkillController;
import com.feuji.employeeskillservice.exception.InvalidInputException;
import com.feuji.employeeskillservice.exception.NoRecordFoundException;
import com.feuji.employeeskillservice.service.EmployeeSkillService;

/**
 * Unit tests for the EmployeeSkillController class.
 */
@SpringBootTest
class EmployeeskillServiceApplicationTests {

	@Mock
	private EmployeeSkillService employeeSkillService;

	@InjectMocks
	private EmployeeSkillController employeeSkillController;

	@Before(value = "")
	public void setUp() {
		employeeSkillService = mock(EmployeeSkillService.class);
		employeeSkillController = new EmployeeSkillController();
		employeeSkillController.setEmployeeSkillService(employeeSkillService);
	}

	/**
	 * Test for the saveEmployeeSkillBean method in employeeSkillController.
	 */
	@Test
	public void testSaveEmployeeSkillBean() {
		List<EmployeeUiBean> employeeUiBeans = new ArrayList<>();
		List<EmployeeSkillBean> employeeSkillBeans = new ArrayList<>();

		try {
			when(employeeSkillService.convertUiBeanToSkillBean(any())).thenReturn(employeeSkillBeans);
		} catch (NoRecordFoundException e) {
			e.printStackTrace();
		}
		when(employeeSkillService.saveAll(any())).thenReturn(employeeSkillBeans);

		ResponseEntity<List<EmployeeSkillBean>> response = employeeSkillController
				.saveEmployeeSkillBean(employeeUiBeans);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(employeeSkillBeans, response.getBody());
	}

	/**
	 * Test for the getEmployeeSkillById method in employeeSkillController.
	 */
	@Test
	public void testGetEmployeeSkillById() {
		List<EmployeeSkillBean> employeeSkillBeans = new ArrayList<>();

		try {
			when(employeeSkillService.getEmployeeSkillById(any())).thenReturn(employeeSkillBeans);
		} catch (NoRecordFoundException e) {
			e.printStackTrace();
		}

		ResponseEntity<List<EmployeeSkillBean>> response = employeeSkillController.getEmployeeSkillById(101);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(employeeSkillBeans, response.getBody());
	}

	/**
	 * Test for the getBySkillId method in employeeSkillController.
	 */
	@Test
	public void testGetBySkillId() {

		List<EmployeeSkillBean> employeeSkillBeanList = new ArrayList<>();

		try {
			when(employeeSkillService.findBySkillId(105)).thenReturn(employeeSkillBeanList);
		} catch (NoRecordFoundException e) {
			e.printStackTrace();
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}

		ResponseEntity<List<EmployeeSkillBean>> response = employeeSkillController.getBySkillId(105);

		assertEquals(HttpStatus.FOUND, response.getStatusCode());
		assertEquals(employeeSkillBeanList, response.getBody());
	}

	/**
	 * Test for the getAllEmployeeSkills method in employeeSkillController.
	 */
	@Test
	public void testGetAllEmployeeSkills() {
		String email = "test@example.com";
		List<EmployeeSkillGet> allEmployeeSkills = new ArrayList<>();

		try {
			when(employeeSkillService.getAllEmployeeSkills(any())).thenReturn(allEmployeeSkills);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResponseEntity<List<EmployeeSkillGet>> response = employeeSkillController.getAllEmployeeSkills(email);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(allEmployeeSkills, response.getBody());
	}

	/**
	 * Test for the deleteEmployeeSkill method in employeeSkillController.
	 */
	@Test
	public void testDeleteEmployeeSkill() {
		Long employeeSkillId = 1L;
		String result = "Deleted successfully";

		try {
			when(employeeSkillService.updateDeleteStatus(employeeSkillId)).thenReturn(result);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}

		ResponseEntity<String> response = employeeSkillController.deleteEmployeeSkill(employeeSkillId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(result, response.getBody());
	}

	/**
	 * Test for the updateEmployeeSkill method in employeeSkillController.
	 */
	@Test
	public void testUpdateEmployeeSkill() throws Exception {
		Long employeeSkillId = 1L;
		EmployeeSkillGet set = new EmployeeSkillGet();
		EmployeeSkillBean bean = new EmployeeSkillBean();

		when(employeeSkillService.updateEmployeeSkill(set, employeeSkillId)).thenReturn(bean);

		ResponseEntity<EmployeeSkillBean> response = employeeSkillController.updateEmployeeSkill(set, employeeSkillId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(bean, response.getBody());
	}

}
