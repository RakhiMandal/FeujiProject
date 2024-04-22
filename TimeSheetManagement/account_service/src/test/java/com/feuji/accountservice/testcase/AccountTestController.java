package com.feuji.accountservice.testcase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.feuji.accountservice.bean.AccountBean;
import com.feuji.accountservice.entity.AccountEntity;
import com.feuji.accountservice.service.AccountService;



@ExtendWith(MockitoExtension.class)
public class AccountTestController {
	@Mock
  private  AccountService accountService;
	@InjectMocks
	private AccountTestController accountController;
	@Test
	public void testsaveAccount() {
		AccountBean accountBean	=AccountBean.builder().accountId(1).accountName("").address("").accountStatus((Integer) 101).build();
		accountService.saveAccount(accountBean);
		verify(accountService,times(1)).saveAccount(accountBean);
	}
	@Test
	public void testupdateAccount() {
		AccountBean accountBean	=AccountBean.builder().accountId(1).accountName("").address("").accountStatus((Integer) 101).build();
		accountService.updateAccount(accountBean);
		verify(accountService,times(1)).updateAccount(accountBean);
	}
	
    @Test
    public void testfindbyUuidAccount() {
        AccountEntity accountEntity = AccountEntity.builder()
                .accountId(1)
                .accountName("")
                .address("")
                .uuId("101")
                .accountStatus((Integer) 101)
                .build();
        
        // Mock the behavior of accountService.findByUUId("101")
        when(accountService.findByUUId("101")).thenReturn(accountEntity);
        
        // Calling the method to test
        AccountEntity accountEntitydata = accountService.findByUUId("101");
        
        // Assertions
        assertThat(accountEntitydata).isNotNull();
        assertThat(accountEntitydata.getUuId()).isEqualTo("101"); // Corrected assertion to match uuId instead of accountId
    }
	
	


}
