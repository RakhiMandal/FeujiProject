package com.feuji.skillgapservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.feuji.skillgapservice.bean.SkillCompetencyBean;
import com.feuji.skillgapservice.controller.SkillCompetencyController;
import com.feuji.skillgapservice.dto.PaginationDto;
import com.feuji.skillgapservice.dto.TrainigRecommendedEmployeesDto;
import com.feuji.skillgapservice.exception.RecordNotFoundException;
import com.feuji.skillgapservice.service.SkillCompetencyService;

/**
 * Unit tests for the SkillCompetencyController class.
 */
@SpringBootTest
class SkillCompetencyControllerTest {

	@Mock
	private SkillCompetencyService skillCompetencyServiceMock;

	@InjectMocks
	private SkillCompetencyController skillCompetencyController;

	@Before(value = "")
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		skillCompetencyController = new SkillCompetencyController();
		skillCompetencyController.setSkillCompetencyService(skillCompetencyServiceMock);
	}

	/**
	 * Test for the saveSkillCompetency method in SkillCompetencyController.
	 */
	@Test
	void testSaveSkillCompetency_Success() {
		SkillCompetencyBean skillCompetencyBean = new SkillCompetencyBean();
		ResponseEntity<String> expectedResponse = new ResponseEntity<>("record saved successfully", HttpStatus.CREATED);

		doNothing().when(skillCompetencyServiceMock).saveSkillCompetency(skillCompetencyBean);

		ResponseEntity<String> actualResponse = skillCompetencyController.saveSkillCompetency(skillCompetencyBean);
		assertEquals(expectedResponse, actualResponse);
	}

	/**
	 * Test for the updateAllSkillCompetencyRecords method in
	 * SkillCompetencyController.
	 */
	@Test
	void testUpdateAllSkillCompetencyRecords_Success() {
		SkillCompetencyBean competencyBean = new SkillCompetencyBean();
		ResponseEntity<SkillCompetencyBean> expectedResponse = new ResponseEntity<>(competencyBean, HttpStatus.OK);

		try {
			when(skillCompetencyServiceMock.updateAllSkillCompetencyRecords(competencyBean)).thenReturn(competencyBean);
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}

		ResponseEntity<SkillCompetencyBean> actualResponse = skillCompetencyController
				.updateAllSkillCompetencyRecords(competencyBean, "uuid");
		assertEquals(expectedResponse, actualResponse);
	}

	/**
	 * Test for the findSkillCompetencyByUuid method in SkillCompetencyController.
	 */
	@Test
	void testFindSkillCompetencyByUuid_Success() {
		SkillCompetencyBean bean = new SkillCompetencyBean();
		ResponseEntity<SkillCompetencyBean> expectedResponse = new ResponseEntity<>(bean, HttpStatus.FOUND);

		try {
			when(skillCompetencyServiceMock.findSkillCompetencyByUuid("uuid")).thenReturn(bean);
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}

		ResponseEntity<SkillCompetencyBean> actualResponse = skillCompetencyController
				.findSkillCompetencyByUuid("uuid");
		assertEquals(expectedResponse, actualResponse);
	}

	/**
	 * Test for the getSkillCompetencyBySkillId method in SkillCompetencyController.
	 */
	@Test
	void testGetSkillCompetencyBySkillId_Success() {
		SkillCompetencyBean bean = new SkillCompetencyBean();
		ResponseEntity<SkillCompetencyBean> expectedResponse = new ResponseEntity<>(bean, HttpStatus.OK);

		try {
			when(skillCompetencyServiceMock.getSkillCompetencyBySkillId(1)).thenReturn(bean);
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}

		ResponseEntity<SkillCompetencyBean> actualResponse = skillCompetencyController.getSkillCompetencyBySkillId(1);
		assertEquals(expectedResponse, actualResponse);
	}

	/**
	 * Test for the getRolesBySkillId method in SkillCompetencyController.
	 */
	@Test
	void testGetRolesBySkillId_Success() {
		List<SkillCompetencyBean> beans = new ArrayList<>();
		ResponseEntity<List<SkillCompetencyBean>> expectedResponse = new ResponseEntity<>(beans, HttpStatus.OK);

		try {
			when(skillCompetencyServiceMock.findSkillCompetencyByTechId(1)).thenReturn(beans);
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}

		ResponseEntity<List<SkillCompetencyBean>> actualResponse = skillCompetencyController.getRolesBySkillId(1);
		assertEquals(expectedResponse, actualResponse);
	}

	/**
	 * Test for the fetcAllSkillIdWise method in SkillCompetencyController.
	 */
	@Test
	void testFetcAllSkillIdWise_Success() {
		PaginationDto employeeSkillsBySkillIds = new PaginationDto();
		ResponseEntity<PaginationDto> expectedResponse = new ResponseEntity<>(employeeSkillsBySkillIds, HttpStatus.OK);

		try {
			when(skillCompetencyServiceMock.getAllEmployeeSkillsBySkillIds(new int[] { 1 }, 1, 10, "role"))
					.thenReturn(employeeSkillsBySkillIds);
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}

		ResponseEntity<PaginationDto> actualResponse = skillCompetencyController.fetcAllSkillIdWise(new int[] { 1 }, 1,
				10, "role");
		assertEquals(expectedResponse, actualResponse);
	}

	/**
	 * Test for the getAllTrainingRecommendedEmp method in
	 * SkillCompetencyController.
	 */
	@Test
	void testGetAllTrainingRecommendedEmp_Success() {
		List<TrainigRecommendedEmployeesDto> list = new ArrayList<>();
		ResponseEntity<List<TrainigRecommendedEmployeesDto>> expectedResponse = new ResponseEntity<>(list,
				HttpStatus.OK);

		when(skillCompetencyServiceMock.getAllTrainingRecommendedEmp(new int[] { 1 })).thenReturn(list);

		ResponseEntity<List<TrainigRecommendedEmployeesDto>> actualResponse = skillCompetencyController
				.getAllTrainingRecommendedEmp(new int[] { 1 });
		assertEquals(expectedResponse, actualResponse);

	}
}
