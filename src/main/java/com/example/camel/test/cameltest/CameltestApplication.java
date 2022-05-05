package com.example.camel.test.cameltest;

import org.apache.camel.RoutesBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import com.example.camel.test.cameltest.routers.CustomRouterBuilder;

@SpringBootApplication
@RestController
public class CameltestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CameltestApplication.class, args);
	}

	@Bean
	RoutesBuilder myRouter() {
		return new CustomRouterBuilder();
	}

}
