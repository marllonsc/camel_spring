package com.marllon.sc.camelexample;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@ImportResource("${classpath:spring/camel-context.xml}")
public class CamelExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelExampleApplication.class, args);
	}

	@Bean
	SpringCamelContext camelContext(ApplicationContext context) {
		SpringCamelContext camelContext = null;

		try {
			camelContext = new SpringCamelContext(context);
			camelContext.addRoutes(createRoutes());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return camelContext;
	}

	private RouteBuilder createRoutes() {
		return new CustomRouterBuilder();
	}


}
