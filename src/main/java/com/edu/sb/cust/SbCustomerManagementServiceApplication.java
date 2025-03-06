package com.edu.sb.cust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class SbCustomerManagementServiceApplication {
	
	

	public static void main(String[] args) {
		SpringApplication.run(SbCustomerManagementServiceApplication.class, args);
	}
	

}
