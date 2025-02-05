package com.search.specification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.search.specification.dao")
public class SpecificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpecificationApplication.class, args);
	}

}
