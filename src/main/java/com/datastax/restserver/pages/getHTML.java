package com.datastax.restserver.pages;

import com.datastax.restserver.model.Event;
import java.util.List;

import com.datastax.restserver.service.SearchService;
import com.datastax.restserver.service.SearchServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dse on 6/17/16.
 */
public class getHTML {

    public String getHTML() {

        final Logger logger = LoggerFactory.getLogger(getHTML.class);
        //Service Layer.
        final SearchService service = new SearchServiceImpl();

        logger.info("WebService: test - <no params>");

        List<Event> result = service.getAllEvents();

        String stringOutput = "<!DOCTYPE html> <html lang=''en''> <head> <meta charset=''utf-8''> <title>Hello World</title> </head>";
        stringOutput = stringOutput + "<H1>sparksensordata.sensordata</H1>";
        stringOutput = stringOutput + "<TABLE border=\"1\">";
        stringOutput = stringOutput + "<TH>Sensor ID</TH><TH>Timestamp</TH><TH>Value</TH>";
        int i = 0;
        while (i < result.size()) {
            stringOutput = stringOutput + "<TR>";

			 /* String to split. */
            String tempString = "";
            String[] tempStringArray;

            tempString = tempString + (result.get(i));

            String delimiter = ",";
            tempStringArray = tempString.split(delimiter);

            for(int j =0; j < tempStringArray.length ; j++) {
                stringOutput = stringOutput + "<TD>";
                stringOutput = stringOutput + tempStringArray[j] + "</TD>";
            }
            stringOutput = stringOutput + "</TR>";

            i++;
        }
        stringOutput = stringOutput + "</TABLE>";

        return stringOutput;
    }
}
