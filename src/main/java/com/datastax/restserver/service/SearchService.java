package com.datastax.restserver.service;

import java.util.List;

import com.datastax.restserver.model.Event;

public interface SearchService {

	public double getTimerAvg();


	///////////////////////
	// CQL Queries
	///////////////////////
	List<Event> getAllEvents();                                                       // SA - CQL only

}
