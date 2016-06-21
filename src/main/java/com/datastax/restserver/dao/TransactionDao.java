package com.datastax.restserver.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.restserver.model.Event;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;


/**
 * @author simonambridge based on original idea by patrickcallaghan
 */
public class TransactionDao {

	private static Logger logger = LoggerFactory.getLogger(TransactionDao.class);
	private Session session;

    private static String sourceKeyspaceName = "sparksensordata";
	private static String sourceTableName    = "sensordata";
    private static String sourceEventTable = sourceKeyspaceName + "." + sourceTableName;
	private AtomicLong count = new AtomicLong(0);


	///////////////////////
	// CQL Queries
	///////////////////////
	// The individual parameters are passed as bind variables to the prepared statement
	//
	private static final String GET_ALL_EVENTS = "select * from " + sourceEventTable + " limit 100;";


	///////////////////////
	// CQL-Solr Queries
	///////////////////////
	// The entire where clause is passed as a parameter to the prepared statement
	//
	private static final String GET_ALL_EVENTS_BY_VALUE = "select * from " + sourceEventTable
			+ " where solr_query = ? limit 100";



	private PreparedStatement getAllEvents;                       // SA - CQL query


	public TransactionDao(String[] contactPoints) {

		Cluster cluster = Cluster.builder().addContactPoints(contactPoints).build();

		this.session = cluster.connect();
        // generate a prepared statement based on the contents of string e.g. GET_ALL_FRAUDULENT_EVENTS_BY_CCNO
		try {
			this.getAllEvents = session.prepare(GET_ALL_EVENTS);    // SA

			logger.info("Preparing statement");
			// logger.info("Query String=" + this.getAllTransactionsByCCno);

		} catch (Exception e) {
			e.printStackTrace();
			session.close();
			cluster.close();
		}
	}


	///////////////////////
	// CQL Queries
	///////////////////////

	public List<Event> getAllEvents() {                    // SA
		// execute the prepared statement using the supplied bind variable(s)
		// For cql, specify individual bind variable(s)(or nothing if one isn't required)
		logger.info(">> getAllEvents: - <no params>");
		ResultSet resultSet = this.session.execute(getAllEvents.bind());
		return processEventResultSet(resultSet);
	}


    private List<Event> processEventResultSet(ResultSet resultSet) {   // SA
        List<Row> rows = resultSet.all();
        List<Event> events = new ArrayList<Event>();

        for (Row row : rows) {

			Event event = rowToEvent(row);
            events.add(event);
        }
        return events;
	}


	private Event rowToEvent(Row row) {

		Event t = new Event();

		t.setName(row.getString("name"));
		t.setTime(row.getDate("time"));
		t.setValue(row.getDouble("value"));
		return t;
	}
}
