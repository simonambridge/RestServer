package com.datastax.restserver.webservice;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.restserver.model.Event;
import com.datastax.restserver.service.SearchService;
import com.datastax.restserver.service.SearchServiceImpl;

@WebService
@Path("/")
public class MainWS {

	private Logger logger = LoggerFactory.getLogger(MainWS.class);
	private SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyyMMdd");

	//Service Layer.
	private SearchService service = new SearchServiceImpl();
	
//	@GET
//	@Path("/getalltransactionsbyccnoanddates/{creditcardno}/{from}/{to}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getAllTransactionsByCCnoAndDates(@PathParam("creditcardno") String ccNo, @PathParam("from") String fromDate,
//			@PathParam("to") String toDate) {
//
//		DateTime from = DateTime.now();
//		DateTime to = DateTime.now();
//		try {
//			from = new DateTime(inputDateFormat.parse(fromDate));
//			to = new DateTime(inputDateFormat.parse(toDate));
//		} catch (ParseException e) {
//			String error = "Caught exception parsing dates " + fromDate + "-" + toDate;
//
//			logger.error(error);
//			return Response.status(Status.BAD_REQUEST).entity(error).build();
//		}
//
//		List<Transaction> result = service.getAllTransactionsByCCnoAndDates(ccNo, null, from, to);
//
//		return Response.status(Status.OK).entity(result).build();
//	}

	///////////////////////
	// CQL Queries
	///////////////////////
    @GET
	@Path("/getallevents/")    // SA
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllEvents() {

		logger.info("WebService: getallevents - <no params>");

		List<Event> result = service.getAllEvents();

	   return Response.status(Status.OK).entity(result).build();
    }

	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_HTML)
	public String getMyHTMLAsString() {

		logger.info("WebService: test - <no params>");

		List<Event> result = service.getAllEvents();

		String stringOutput = "<!DOCTYPE html> <html lang=''en''> <head> <meta charset=''utf-8''> <title>Hello World</title> </head>";
		stringOutput = stringOutput + "<H1>sparksensordata.sensordata</H1>";
		stringOutput = stringOutput + "<TABLE border=\"1\">";
		int i = 0;
		while (i < result.size()) {
			stringOutput = stringOutput + "<TR><TD>";
			stringOutput = stringOutput + (result.get(i));
			stringOutput = stringOutput + "</TD></TR>";

			i++;
		}
		stringOutput = stringOutput + "</TABLE>";

	return stringOutput;
	}
}
