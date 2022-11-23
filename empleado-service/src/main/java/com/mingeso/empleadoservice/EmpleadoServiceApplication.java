package com.mingeso.empleadoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class EmpleadoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpleadoServiceApplication.class, args);
	}

}
