package com.marllon.sc.camelexample;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomRouterBuilder extends RouteBuilder {
	
	@Autowired
	MyFileProcessor myFileProcessor;

	@Override
	public void configure() throws Exception {
		myFileProcessor = new MyFileProcessor();
		
		from("file:C:/ROUTS_TEST/inbox").choice().when().simple("${file:ext} == 'txt'")
		.process(myFileProcessor)
		.to("file:C:/ROUTS_TEST/outboxTXT").otherwise().to("file:C:/ROUTS_TEST/outbox");
		
//		from("file:C:/ROUTS_TEST/outboxTXT").to(endpoint);
		
		from("jetty:http://www.google.com") .to("file:C:/ROUTS_TEST/outboxTXT");
		
	}

}
