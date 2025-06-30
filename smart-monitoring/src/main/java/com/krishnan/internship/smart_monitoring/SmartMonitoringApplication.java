package com.krishnan.internship.smart_monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SmartMonitoringApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartMonitoringApplication.class, args) ;
		System.out.println("Application started successfully") ; 
	}

}
