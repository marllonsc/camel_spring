package com.example.camel.test.cameltest.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MyProcessorBody implements Processor{

	private String body;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		setBody("'{'\\\"expiryDate\\\": \\\"{0}\\\",\\\"note\\\": '{'\\\"text\\\": \\\"{1}\\\"'}}'");
		exchange.getOut().setBody(getBody());	
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
}
