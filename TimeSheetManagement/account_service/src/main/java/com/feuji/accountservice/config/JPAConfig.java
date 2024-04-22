package com.feuji.accountservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableJpaRepositories(basePackages = {"com.feuji.accountservice"})
@EnableTransactionManagement
public class JPAConfig {
	
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
 }
}
