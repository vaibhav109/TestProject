package com.capgemini;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
//http://localhost:9090/project-api/v2/api-docs - all the end points information
//http://localhost:9090/project-api/swagger-ui/

@SpringBootApplication
public class InterviewTrackingSystemApplication
{
	public static void main(String[] args) 
	{
		SpringApplication.run(InterviewTrackingSystemApplication.class, args);
	}
}
