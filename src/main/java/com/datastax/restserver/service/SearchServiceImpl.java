package com.datastax.restserver.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.datastax.restserver.dao.TransactionDao;
import com.datastax.restserver.model.Event;
import com.datastax.restserver.utils.PropertyHelper;
import com.datastax.restserver.utils.Timer;

public class SearchServiceImpl implements SearchService {

	private TransactionDao dao;
	private long timerSum = 0;
	private AtomicLong timerCount= new AtomicLong();

	public SearchServiceImpl() {		
		String contactPointsStr = PropertyHelper.getProperty("contactPoints", "localhost");
		this.dao = new TransactionDao(contactPointsStr.split(","));
	}	

	@Override
	public double getTimerAvg(){
		return timerSum/timerCount.get();
	}

//	@Override
//	public List<Transaction> getAllTransactionsByCCnoAndDates(String ccNo, DateTime from, DateTime to) {
		
//		Timer timer = new Timer();
//		List<Transaction> transactions;

//		//If the to and from dates are within the 3 months we can use the latest transactions table as it should be faster.
//		if (from.isAfter(DateTime.now().minusMonths(3))){
//			transactions = dao.getAllTransactionsByCCnoAndDates(ccNo, from, to);
//		}else{
//			transactions = dao.getAllTransactionsByCCnoAndDates(ccNo, from, to);
//		}
			
//		timer.end();
//		timerSum += timer.getTimeTakenMillis();
//		timerCount.incrementAndGet();
//		return transactions;
//	}

	///////////////////////
    // CQL Queries
	///////////////////////
	@Override
	public List<Event> getAllEvents() {           // SA

		Timer timer = new Timer();
		List<Event> transactions;
		transactions = dao.getAllEvents();
		timer.end();
		timerSum += timer.getTimeTakenMillis();
		timerCount.incrementAndGet();
		return transactions;
	}




}
