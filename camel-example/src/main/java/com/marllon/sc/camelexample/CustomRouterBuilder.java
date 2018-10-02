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

		from("file:C:/SpringCamel/File_inbox?noop=true").choice().when().simple("${file:ext} == 'txt'")
				.process(myFileProcessor).to("file:C:/SpringCamel/outboxTXT").otherwise()
				.to("file:C:/SpringCamel/File_outbox_outrosArquivos");

		from("jetty://http://localhost:8888/greeting").log("Received a request").process(myProcessorBody)
				.to("file:C:/ROUTS_TEST/outboxTXT");

		from("file:C:/SpringCamel/File_inbox/?noop=true").log("Uploading file ...").to(
				"ftp://172.22.9.3:21?autoCreate=false&username=root&password=123456&passiveMode=true&binary=true\\&resumeDownload=true&localWorkDirectory=target/ftp-work\\&transferLoggingLevel=INFO&transferLoggingIntervalSeconds=1&transferLoggingVerbose=false")
				.log("Uploaded file !!!");

		from("file:C:/SpringCamel/File_inbox_FTP2/?noop=true").log("Uploading file ...")
				.to("ftp://root@172.22.9.3:21/ftp/?password=123456&passiveMode=true").log("Uploaded file !!!");
		
	}

}
