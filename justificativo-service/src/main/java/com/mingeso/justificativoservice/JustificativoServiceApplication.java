package com.mingeso.justificativoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication

public class JustificativoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JustificativoServiceApplication.class, args);
	}

}
