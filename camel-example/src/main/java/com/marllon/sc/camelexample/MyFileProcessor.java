package com.marllon.sc.camelexample;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MyFileProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		String fileContext = exchange.getIn().getBody(String.class);
		exchange.getIn().setHeader(exchange.FILE_NAME, "MARLLON_TEST_OUTPUT.txt");
		exchange.getIn().setBody(fileContext.toUpperCase());

	}

}
