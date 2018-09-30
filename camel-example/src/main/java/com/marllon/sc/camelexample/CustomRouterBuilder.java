package com.marllon.sc.camelexample;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomRouterBuilder extends RouteBuilder {

	@Autowired
	MyFileProcessor myFileProcessor;
	
	@Autowired
	MyProcessorBody myProcessorBody;


	@Override
	public void configure() throws Exception {
		myFileProcessor = new MyFileProcessor();
		
		myProcessorBody = new MyProcessorBody();

		from("file:C:/ROUTS_TEST/inbox").choice().when().simple("${file:ext} == 'txt'").process(myFileProcessor)
				.to("file:C:/ROUTS_TEST/outboxTXT").otherwise().to("file:C:/ROUTS_TEST/outbox");


		from("jetty://http://localhost:8888/greeting").log("Received a request").process(myProcessorBody).to("file:C:/ROUTS_TEST/outboxTXT");



	}


}
