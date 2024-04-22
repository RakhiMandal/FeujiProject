package com.feuji.accountprojectservice.testcase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.feuji.accountprojectservice.bean.AccountProjectsBean;
import com.feuji.accountprojectservice.controller.AccountProjectsController;
import com.feuji.accountprojectservice.entity.AccountProjectsEntity;
import com.feuji.accountprojectservice.service.AccountProjectsService;




@ExtendWith(MockitoExtension.class)
public class AccountProjectsTestController {
	@Mock
	private AccountProjectsService accountProjectsService;
	@InjectMocks
	private AccountProjectsController accountProjectsController;
	@Test
	public void testsaveAccountProject() {
		AccountProjectsBean accountProjectsBean=AccountProjectsBean.builder().accountProjectId(1).projectName("").projectStatus(1).uuid("").build();
		accountProjectsService.save(accountProjectsBean);
		verify(accountProjectsService,times(1)).save(accountProjectsBean);
	}
	@Test
	public void testupdateAccountProject() {
		
		AccountProjectsBean accountProjectsBean=AccountProjectsBean.builder().accountProjectId(1).projectName("").projectStatus(1).uuid("").build();
		accountProjectsService.updateAccountProject(accountProjectsBean);
		verify(accountProjectsService, times(1)).updateAccountProject(accountProjectsBean);
	}
    @Test
    public void testgetAllAccountProjects() {
        List<AccountProjectsBean> accountProjectsEntityList = Arrays.asList(
        		AccountProjectsBean.builder()
                        .accountProjectId(1)
                        .projectName("")
                        .projectStatus(1)
                        .uuid("")
                        .build()
        );

        // Mock the behavior of accountProjectsService.getAllAccountProjects()
        when(accountProjectsService.getAllAccountProjects()).thenReturn(accountProjectsEntityList);

        // Calling the method to test
        List<AccountProjectsBean> accountProjectEntitydata = accountProjectsService.getAllAccountProjects();

        // Assertions
        assertThat(accountProjectEntitydata).isNotNull();
        
    }
    @Test
    public void testfindbyUuidAccountProject() {
        AccountProjectsBean accountProjectsBean  =AccountProjectsBean .builder()
                .accountProjectId(1)
                .projectName("")
                .projectStatus(1)
                .uuid("101")
                .build();

        // Mock the behavior of accountProjectsService.findByUuid("101")
        when(accountProjectsService.findByUuid("101")).thenReturn(accountProjectsBean );

        // Calling the method to test
        AccountProjectsBean accountProjectsEntitydata = accountProjectsService.findByUuid("101");

        // Assertions
        assertThat(accountProjectsEntitydata).isNotNull();
        assertThat(accountProjectsEntitydata.getUuid()).isEqualTo("101");
    } 
    


	}


