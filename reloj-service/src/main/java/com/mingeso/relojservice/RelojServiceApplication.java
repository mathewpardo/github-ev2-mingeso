package com.mingeso.relojservice;

import com.mingeso.relojservice.service.FileUploadService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import javax.annotation.Resource;

@EnableEurekaClient
@SpringBootApplication

public class RelojServiceApplication implements CommandLineRunner {

	@Resource
	FileUploadService fileUploadService;

	public static void main(String[] args) {

		SpringApplication.run(RelojServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		fileUploadService.delete();
		fileUploadService.init();
	}

}
