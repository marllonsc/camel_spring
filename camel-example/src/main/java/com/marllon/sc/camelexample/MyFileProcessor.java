package com.marllon.sc.camelexample;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MyFileProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		String fileContext = exchange.getIn().getBody(String.class);
		exchange.getIn().setHeader(Exchange.FILE_NAME, "UpperCaseFile.txt");
		exchange.getIn().setBody(fileContext.toUpperCase());

	}

}
