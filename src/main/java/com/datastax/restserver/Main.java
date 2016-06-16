package com.datastax.restserver;

import com.datastax.restserver.service.SearchService;
import com.datastax.restserver.service.SearchServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {

	private static Logger logger = LoggerFactory.getLogger(Main.class);

	public Main() {
		SearchService service=new SearchServiceImpl();
		//logger.info(service.getAllrTFAPTransactionsByCC("1234123412341235", DateTime.now().withDayOfMonth(8)).toString());
		//logger.info(service.getAllFraudulentTransactionsByCC("1234123412341235"));
        //logger.info(service.getDailyTransactionsByMerchant("GAP",20160309).toString());
	}

	private void sleep(int i) {
		try {
			Thread.sleep(i*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Main();

		System.exit(0);
	}

}
