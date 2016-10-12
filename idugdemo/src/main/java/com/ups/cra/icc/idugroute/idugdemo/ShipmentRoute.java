package com.ups.cra.icc.idugroute.idugdemo;

import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apacheextras.camel.component.couchbase.CouchbaseConstants;

public class ShipmentRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
	     from("activemq:topic:mqtt.ShipmentInfo")	
		 .process(new Processor(){

			@Override
			public void process(Exchange exchange) throws Exception {
				//System.out.println("JSON is :" + exchange.getIn().getBody(String.class));	
			}
		})
		
		.setHeader(CouchbaseConstants.HEADER_ID,constant(new Random(System.currentTimeMillis()).nextInt())).id("shipmentHeaderId")
		.to("couchbase:http://localhost:11210/idug-sample?operation=" + CouchbaseConstants.COUCHBASE_PUT)
		.end();
		 
		
		/* sample code
		//from("direct:deviceInsert").marshal().json(JsonLibrary.Jackson).to("couchbase:http://localhost:11210/idug-sample?operation=" + CouchbaseConstants.COUCHBASE_PUT);
		from("direct:deviceInsert")
		.setHeader(CouchbaseConstants.HEADER_ID, constant(2))
		.to("couchbase:http://localhost:11210/idug-sample?operation=" + CouchbaseConstants.COUCHBASE_PUT);
		*/
		
		
	}

}