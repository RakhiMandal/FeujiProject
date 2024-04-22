package com.feuji.skillgapservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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

import com.feuji.skillgapservice.bean.SkillBean;
import com.feuji.skillgapservice.controller.SkillController;
import com.feuji.skillgapservice.dto.SkillNamesDto;
import com.feuji.skillgapservice.entity.SkillEntity;
import com.feuji.skillgapservice.exception.RecordNotFoundException;
import com.feuji.skillgapservice.exception.SkillNotFoundException;
import com.feuji.skillgapservice.service.SkillService;

/**
 * Unit tests for the SkillController class.
 */
@SpringBootTest
class SkillgapServiceApplicationTests {

	@Mock
	private SkillService skillService;

	@InjectMocks
	private SkillController skillController;

	@Before(value = "")
	public void setup() {
		skillService = mock(SkillService.class);
		skillController = new SkillController();
		skillController.setSkillService(skillService);
	}

	/**
	 * Test for the saveSkill method in skillController.
	 */
	@Test
	public void testSaveSkill() {
		SkillBean skillBean = new SkillBean();
		when(skillService.saveSkill(any())).thenReturn(skillBean);

		ResponseEntity<SkillBean> response = skillController.saveSkill(skillBean);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(skillBean, response.getBody());
	}

	/**
	 * Test for the updateSkillDetails method in skillController.
	 */
	@Test
	public void testUpdateSkillDetails() {
		SkillBean skillBean = new SkillBean();
		when(skillService.updateSkillDetails(any())).thenReturn(skillBean);

		ResponseEntity<SkillBean> response = skillController.updateSkillDetails("uuid", skillBean);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(skillBean, response.getBody());
	}

	/**
	 * Test for the getSkillsByTechCategoryId method in skillController.
	 */
	@Test
	public void testGetSkillsByTechCategoryId() {
		List<SkillBean> skillBeanList = new ArrayList<>();
		try {
			when(skillService.getSkillsByTechCategoryId(1)).thenReturn(skillBeanList);

			ResponseEntity<List<SkillBean>> response = skillController.getSkillsByTechCategoryId(1);

			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertEquals(skillBeanList, response.getBody());
		} catch (SkillNotFoundException e) {

			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	/**
	 * Test for the getSkillBySkillId method in skillController.
	 */
	@Test
	public void testGetSkillBySkillId() {
		SkillBean skillBean = new SkillBean();

		try {
			when(skillService.getSkillBySkillId(1)).thenReturn(skillBean);
			ResponseEntity<SkillBean> response = skillController.getSkillBySkillId(1);
			assertEquals(HttpStatus.FOUND, response.getStatusCode());
			assertEquals(skillBean, response.getBody());
		} catch (SkillNotFoundException e) {

			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	/**
	 * Test for the getSkillNamesBySkillId method in skillController.
	 */
	@Test
	public void testGetSkillNamesBySkillId() {
		List<SkillNamesDto> skillNamesDtoList = new ArrayList<>();
		try {
			when(skillService.getSkillNamesBySkillId(any())).thenReturn(skillNamesDtoList);
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}

		ResponseEntity<List<SkillNamesDto>> response = skillController.getSkillNamesBySkillId(new int[] { 1, 2, 3 });

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(skillNamesDtoList, response.getBody());
	}

	/**
	 * Test for the getSkillBean method in skillController.
	 */
	@Test
	public void testGetSkillBean() {
		SkillBean skillBean = new SkillBean();
		when(skillService.getSkillByUuid(any())).thenReturn(skillBean);

		ResponseEntity<SkillBean> response = skillController.getSkillBean("uuid");

		assertEquals(HttpStatus.FOUND, response.getStatusCode());
		assertEquals(skillBean, response.getBody());
	}

	/**
	 * Test for the updateSkillStatus method in skillController.
	 */
	@Test
	public void testUpdateSkillStatus() throws RecordNotFoundException {
		List<Integer> skillIds = new ArrayList<>();
		skillIds.add(1);
		List<Byte> status = new ArrayList<>();
		status.add((byte) 1);
		when(skillService.updateStatusBySkillId(eq(skillIds), eq(status))).thenReturn(new ArrayList<>());

		ResponseEntity<SkillEntity> response = skillController.updateSkillStatus(skillIds, status);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(skillService).updateStatusBySkillId(eq(skillIds), eq(status));
	}

	/**
	 * Test for the getSkillsByTechCategoryIdForEmployee method in skillController.
	 */
	public void testGetSkillsByTechCategoryIdForEmployee() {
		List<SkillBean> skillBeanList = new ArrayList<>();
		skillBeanList.add(new SkillBean());

		try {

			when(skillService.getSkillsByTechCategoryIdForEmployee(any())).thenReturn(skillBeanList);
			ResponseEntity<List<SkillBean>> response = skillController.getSkillsByTechCategoryIdForEmployee(1);
			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertEquals(skillBeanList, response.getBody());
		} catch (SkillNotFoundException e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	/**
	 * Test for the deleteSkillBySubSkillCategoryId method in skillController.
	 */
	@Test
	public void testDeleteSkillBySubSkillCategoryId() {

		List<SkillEntity> deletedSkills = new ArrayList<>();

		when(skillService.deleteSkillBySubSkillCategoryId(any(), any())).thenReturn(deletedSkills);

		ResponseEntity<SkillEntity> response = skillController.deleteSkillBySubSkillCategoryId(1L);
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}
}
