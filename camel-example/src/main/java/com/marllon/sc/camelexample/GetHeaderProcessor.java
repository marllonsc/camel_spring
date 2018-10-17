package com.marllon.sc.camelexample;

import java.io.File;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class GetHeaderProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		String name = exchange.getIn().getBody(File.class).getParentFile().getName();
		exchange.getIn().setHeader("folder", name);
		exchange.getIn().setHeader(Exchange.FILE_NAME,  name + ".txt");
	}

	

	

}
