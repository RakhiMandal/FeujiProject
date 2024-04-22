package com.feuji.accountprojectservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableJpaRepositories(basePackages = {"com.feuji.accountprojectservice"})
@EnableTransactionManagement
public class JPAConfig {
	
	 @Bean
	    public ModelMapper modelMapper() {
	        return new ModelMapper();
	 }
		@Bean
	    public RestTemplate restTemplate() {
	        return new RestTemplate();
	    }


}
