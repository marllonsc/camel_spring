package com.marllon.sc.camelexample;

import java.util.Iterator;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.zipfile.ZipSplitter;
import org.apache.camel.model.dataformat.ZipDataFormat;
import org.apache.camel.model.dataformat.ZipFileDataFormat;
import org.springframework.beans.factory.annotation.Autowired;

import com.marllon.sc.camelexample.CustomRouterBuilder.ItemHolder;

public class CustomRouterBuilder extends RouteBuilder {

	
	@Autowired
	MyFileProcessor myFileProcessor;

	@Autowired
	MyProcessorBody myProcessorBody;
	
	@Autowired
	ProcessZipFileEntry fileProcessor;

	@Override
	public void configure() throws Exception {
		
		myFileProcessor = new MyFileProcessor();
		myProcessorBody = new MyProcessorBody();
		
		fileProcessor = new ProcessZipFileEntry();

		
//		from("file:C:/SpringCamel/inbox").to("file:C:/SpringCamel/outbox");
		 
		
		from("file:C:/SpringCamel/File_inbox?noop=true").choice().when().simple("${file:ext} == 'txt'")
				.process(myFileProcessor).to("file:C:/SpringCamel/outboxTXT").otherwise()
				.to("file:C:/SpringCamel/File_outbox_outrosArquivos");

		
		from("jetty://http://localhost:8888/greeting").log("Received a request").process(myProcessorBody)
				.to("file:C:/ROUTS_TEST/outboxTXT");

//		from("file:C:/SpringCamel/File_inbox/?noop=true").log("Uploading file ...").to(
//				"ftp://172.22.9.3:21?autoCreate=false&username=root&password=123456&passiveMode=true&binary=true\\&resumeDownload=true&localWorkDirectory=target/ftp-work\\&transferLoggingLevel=INFO&transferLoggingIntervalSeconds=1&transferLoggingVerbose=false")
//				.log("Uploaded file !!!");
//		
//
//		from("file:C:/SpringCamel/File_inbox_FTP2/?noop=true").log("Uploading file ...")
//				.to("ftp://root@172.22.9.3:21/ftp/?password=123456&passiveMode=true").log("Uploaded file !!!");
		

		
//		from("file:C:/SpringCamel/inbox?noop=true&delay=5000&moveFailed=error")
//				.split(new ZipSplitter()).streaming() .choice()
//                .when(new IsInManifest())
//                .bean(fileProcessor)
//                .otherwise().to("file:C:/SpringCamel/outbox").end();
		
		
		    from("file:C:/SpringCamel/inbox?consumer.delay=1000&noop=true")
		    .split(new ZipSplitter())
		    .streaming().convertBodyTo(String.class)
		            .to("file:C:/SpringCamel/outbox").end();
//		
//		from("file:C:/SpringCamel/inbox?noop=true&delay=5000&moveFailed=error").unmarshal(new ZipDataFormat())
//		.to("file:C:/SpringCamel/outbox");
		
//		from("file:C:/SpringCamel/inbox?noop=true").unmarshal(new ZipDataFormat()).split(new ZipSplitter()).streaming()
//		.to("file:C:/SpringCamel/outbox?fileName=${file:parent}/${file:name.noext}.log");
//		

//		from("file:C:/SpringCamel/inbox?noop=true").unmarshal().zipFile().to("file:C:/SpringCamel/outbox");
		
	}
	
	/**
     * Custom predicate that checks if the filename from the zip file is in the manifest.
     */
    class IsInManifest implements Predicate {

        @Override
        public boolean matches(final Exchange exchange) {
            List<ItemHolder> items = (List) exchange.getProperty("expectedFiles");
            String pdfFilename = (String) exchange.getIn().getHeader("camelFilename");

            long count = items.stream()
                    .filter(e -> e.getFilename().equals(pdfFilename))
                    .count();

            return count != 0;
        }
    }
    
    
    public class ItemHolder {

        private String filename;

        private  boolean processed = false;

        public ItemHolder(String filename) {
            this.filename = filename;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public boolean isProcessed() {
            return processed;
        }

        public void setProcessed(boolean processed) {
            this.processed = processed;
        }

        public String toString() {
            return this.filename;
        }
    }
}
    
    