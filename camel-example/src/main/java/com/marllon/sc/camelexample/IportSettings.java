package com.marllon.sc.camelexample;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IportSettings {
	
	
	@Value("${dna.be.iport.mq.queueManager}")
	String queueManager;

	@Value("${dna.be.iport.mq.channel}")
	String channel;

	@Value("${dna.be.iport.mq.host}")
	String host;

	@Value("${dna.be.iport.mq.port}")
	String port;

	@Value("${dna.be.iport.mq.user}")
	String user;

	@Value("${dna.be.iport.mq.password}")
	String password;


}
