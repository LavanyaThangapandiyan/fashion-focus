package com.project.fashion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class FashionApplication
{
	public static void main(String[] args) {
		SpringApplication.run(FashionApplication.class, args);
		System.out.println("Start Application");
	}
}
