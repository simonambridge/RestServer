package com.datastax.restserver.pages;

import com.datastax.restserver.model.Event;
import com.datastax.restserver.service.SearchService;
import com.datastax.restserver.service.SearchServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by dse on 6/21/16.
 */
public class getStatus {
    public String getStatus() {

        final Logger logger = LoggerFactory.getLogger(getStatus.class);
        //Service Layer.
        final SearchService service = new SearchServiceImpl();

        logger.info("WebService: test - <no params>");

        List<Event> result = service.getAllEvents();

        String stringOutput = "<!DOCTYPE html> <html lang=''en''>" +
          "<head> <meta charset=''utf-8''> <title>ReST Server Status Page</title> " +
          "<link rel=\"stylesheet\" href=\"/restserver/resources/styles.css\" /></head>" +
          "<BODY> <img src=\"/restserver/images/datastax_logo.png\"/>" +
          "<H1>DataStax ReST Server - Status Page</H1><P>" +
          "If you can see this page then the server is working!<P><BR><BR><BR>" +
          "<H2>The following services are available:</H2>" +
          "<H3>ReST Services: CQL query</H3>" +
          "<TABLE border=\"1\">" +
          "<TR><a href='http://127.0.0.1:7001/restserver/rest/json'>http://127.0.0.1:7001/restserver/rest/json</a></TR>" +
          "</TABLE>" +
          "<H3>ReST Services: CQL-Solr query</H3>" +
          "<TABLE border=\"1\">" +
          "<TR><a href='http://127.0.0.1:7001/restserver/rest/json'>http://127.0.0.1:7001/restserver/rest/json</a></TR>" +
          "</TABLE>" +
          "<H3>ReST Services: CQL query formatted in HTML</H3>" +
          "<TABLE border=\"1\">" +
          "<TR><a href='http://127.0.0.1:7001/restserver/rest/html'>http://127.0.0.1:7001/restserver/rest/html</a></TR>" +
          "</TABLE>";

        return stringOutput;
    }
}
