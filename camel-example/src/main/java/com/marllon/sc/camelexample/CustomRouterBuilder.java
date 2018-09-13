package com.marllon.sc.camelexample;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomRouterBuilder extends RouteBuilder {
	
	@Autowired
	MyFileProcessor myFileProcessor;

	@Override
	public void configure() throws Exception {
		from("file://src/test/data/inbox")//.process(myFileProcessor)
		.to("file://src/test/data/outbox");
	}

}
