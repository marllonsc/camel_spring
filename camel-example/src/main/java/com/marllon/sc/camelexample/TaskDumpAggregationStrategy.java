package com.marllon.sc.camelexample;

import org.apache.camel.Exchange;

public class TaskDumpAggregationStrategy {
	
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
	    if (oldExchange == null) {
	      return newExchange;
	    }
	    String oldBody = oldExchange.getIn().getBody(String.class);
	    String newBody = newExchange.getIn().getBody(String.class);
	    oldExchange.getIn().setBody(oldBody + "\n" + newBody);
	    return oldExchange;
	  }


}
