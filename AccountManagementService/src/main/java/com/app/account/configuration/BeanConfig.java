package com.app.account.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {

	@Bean
	public RestTemplate restTemplateBean() {
		return new RestTemplate();
	}

	@Bean
	public ModelMapper modelMapperBean() {
		return new ModelMapper();
	}

}
