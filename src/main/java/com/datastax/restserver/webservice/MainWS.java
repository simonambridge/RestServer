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
import com.datastax.restserver.pages.*;

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
	// CQL Query - HTML render of getallevents
	///////////////////////
	@GET
	@Path("/html")
	@Produces(MediaType.TEXT_HTML)
	public String getMyHTMLAsString() {
		getHTML g = new getHTML();
		String stringOutput = g.getHTML();

	return stringOutput;
	}

	//////////////////////////
	// CQL-Solr Query - JSON
	//////////////////////////
	@GET
	@Path("/json/")    // SA
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllEvents() {

		logger.info("WebService: getallevents - <no params>");

		List<Event> result = service.getAllEvents();

		return Response.status(Status.OK).entity(result).build();
	}

	///////////////////////
	// HTML status page
	///////////////////////
	@GET
	@Path("/status")
	@Produces(MediaType.TEXT_HTML)
	public String getStatusPage() {
		getStatus g = new getStatus();
		String stringOutput = g.getStatus();

		return stringOutput;
	}



}
