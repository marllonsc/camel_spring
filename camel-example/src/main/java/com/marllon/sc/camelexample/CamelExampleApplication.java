package com.marllon.sc.camelexample;

import javax.jms.JMSException;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

//@SpringBootApplication
//@ImportResource("${classpath:spring/camel-context.xml}")
@SpringBootApplication
@RestController
@EnableJms
public class CamelExampleApplication {
	
	@Autowired
    private JmsTemplate jmsTemplate;

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
	
	@GetMapping("send")
	String send(){
	    try{
	        jmsTemplate.convertAndSend("DEV.QUEUE.1", "Hello World!");
	        return "OK";
	    }catch(JmsException ex){
	        ex.printStackTrace();
	        return "FAIL";
	    }
	}
	
	
//	@Bean
//	public JmsTemplate jmsQueueTemplate() {
//		
//		JmsTemplate jms = new JmsTemplate();
//		//jms.getConnectionFactory().createConnection(iportSettings.user, iportSettings.password);
//		return jms;
//		
//	}
	
	@Bean
	public MQQueueConnectionFactory mqQueueConnectionFactory(@Autowired IportSettings iportSettings) { 
	        MQQueueConnectionFactory mqQueueConnectionFactory = new MQQueueConnectionFactory(); 
	        try { 
	            mqQueueConnectionFactory.setHostName(iportSettings.host); 
	            mqQueueConnectionFactory.setQueueManager(iportSettings.queueManager); 
	            mqQueueConnectionFactory.setPort(Integer.parseInt(iportSettings.port)); 
	            mqQueueConnectionFactory.setChannel(iportSettings.channel); 
	            mqQueueConnectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT); 
	            mqQueueConnectionFactory.setCCSID(1208); 
	            mqQueueConnectionFactory.setStringProperty(WMQConstants.USERID, iportSettings.user);
	            mqQueueConnectionFactory.setStringProperty(WMQConstants.PASSWORD, iportSettings.password);
	        } catch (Exception e) { 
	            e.printStackTrace(); 
	        } 
	        return mqQueueConnectionFactory; 
	    } 
	
	
	
	@GetMapping("recv")
	String recv(){
	    try{
	        return jmsTemplate.receiveAndConvert("DEV.QUEUE.1").toString();
	    }catch(JmsException ex){
	        ex.printStackTrace();
	        return "FAIL";
	    }
	}

	private RouteBuilder createRoutes() {
		return new CustomRouterBuilder();
	}


}
