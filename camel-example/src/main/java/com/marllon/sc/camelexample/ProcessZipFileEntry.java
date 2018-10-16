package com.marllon.sc.camelexample;


import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Header;

import com.marllon.sc.camelexample.CustomRouterBuilder.ItemHolder;

import java.util.List;

/**
 * We now have a pdf in the exchange from the zip file. Decide what to do with it....
 */
public class ProcessZipFileEntry {

    @Handler
    public void processFile(final Exchange exchange, @Header("camelFileName") String filename) {
        List<ItemHolder> items = (List) exchange.getProperty("expectedFiles");

        System.out.println("Process File : " + filename);

        // Mark the item as processed
        ItemHolder item = items.stream()
                .filter(e -> e.getFilename().equals(filename))
                .findFirst()
                .get();

        item.setProcessed(true);


        // Send the file to the target system here
    }
}