package com.feuji.skillset.test;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.skillset.controller.SkillSetController;
import com.skillset.dto.GapDto;
import com.skillset.exception.RecordNotFoundException;
import com.skillset.service.SkillSetService;

public class SkillSetControllerTest {

	@Mock
	private SkillSetService skillSetServiceMock;

	@InjectMocks
	private SkillSetController skillSetController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	/**
	 * Test case to verify the successful retrieval of skill details.
	 * 
	 * <p>Mocks the behavior of the {@code fetchSkillDto} method from {@code SkillSetService} to return a list of {@code GapDto} objects.
	 * The test verifies that the controller method {@code fetchSkillDetails} returns an {@code HttpStatus.OK} response with the expected list of skill details.</p>
	 */
	@Test
	public void testFetchSkillDetails_Success() {
		String email = "example@example.com";
		Integer skillCategoryId = 1;
		List<GapDto> expectedList = new ArrayList<>();
		expectedList.add(new GapDto());

		try {
			when(skillSetServiceMock.fetchSkillDto(email, skillCategoryId)).thenReturn(expectedList);
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}

		ResponseEntity<List<GapDto>> responseEntity = skillSetController.fetchSkillDetails(email, skillCategoryId);

		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assertions.assertEquals(expectedList, responseEntity.getBody());
	}
	/**
	 * Test case to verify the handling of RecordNotFoundException when fetching skill details.
	 * 
	 * <p>Mocks the behavior of the {@code fetchSkillDto} method from {@code SkillSetService} to throw a {@code RecordNotFoundException}.
	 * The test verifies that the controller method {@code fetchSkillDetails} handles the exception properly and returns an {@code HttpStatus.OK} response with a null body.</p>
	 */
	@Test
	public void testFetchSkillDetails_RecordNotFoundException() {
		String email = "example@example.com";
		Integer skillCategoryId = 1;

		try {
			when(skillSetServiceMock.fetchSkillDto(email, skillCategoryId))
					.thenThrow(new RecordNotFoundException("Record not found"));
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}

		ResponseEntity<List<GapDto>> responseEntity = skillSetController.fetchSkillDetails(email, skillCategoryId);
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assertions.assertNull(responseEntity.getBody());
	}
}
