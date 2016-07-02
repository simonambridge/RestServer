

# ReST Server - Query Cassandra Data Quickly

## Querying Data Using A ReST Web Interface

A ReSTful web interface provides an API for calling programs to query the data in Cassandra.
To use the web service, use the following url’s. These will return a json representation of the data using the ReST service.

The sample queries are served by a web service written in Java. The code for this web service is provided in the repo.
The web service adopts a framework that separates the web, service and data access layers into individual, easily maintainable components.

##Pre-Requisites

###DataStax Enterprise

You'll need DataStax Enterprise (4.8.4 or above recommended) for the integrated Cassandra, Spark and Solr platform.

You will need DSE to be running in "SearchAnalytics" mode - meaning that both Solr and Spark are started when DSE starts. The default behavious is to start only Cassandra, so to change this edit the default dse file ```/etc/default/dse```.

Find where it says ```SPARK_ENABLED=0``` and change it to ```SPARK_ENABLED=1```.
Do the same for ```SOLR_ENABLED```, setting it to one also.

Save and exit the editor
Restart DSE
Run ```dsetool status```. You should see SearchAnalytics as your DC/workload type:

```
dsetool status
Note: Ownership information does not include topology, please specify a keyspace. 
Address       DC      Rack    Workload              Status  State    Load         Owns         Token
127.0.0.1     DC1     RAC1    SearchAnalytics(JT)   Up      Normal   141.92 MB    100.00%      -1250424544841641002 
````

###Maven
You'll also need to install Maven to compile the code. As the root account use apt-get to install it:
```
$ apt-get install maven
```

Compile the code:

```
$ mvn clean compile
```

To start the web service use the command:
```
$ mvn jetty:run
```
To bind to a specific interface or port (other than localhost and the default of 8080) use:
```
$ mvn jetty:run -DcontactPoints=<server IP address> -Djetty.port=<port number>
```
For example - to run on a server with an IP of 10.0.0.4 and run the service on port 7001, and persist the web service after logging out use:
```
$ nohup mvn jetty:run -DcontactPoints=10.0.0.4 -Djetty.port=7001 &
```

The RestServer provides hooks to create entry points that can return either of the following:
* JSON
* HTML


##Set Up Solr with DSE

We use dsetool this time to create a Solr core based on the table that we want to index. In this case it's ```SparkSensoreData.SensorData```.

In a production environment we would only index the columns that we would want to query on. Tip - the schema must exist to run this exercise. If you've jumped here then you've missed creating it.

By default, when you automatically generate resources, existing data is not re-indexed so that you can check and customize the resources before indexing. To override the default and reindex existing data, use the reindex=true option:
```
dsetool create_core SparkSensoreData.SensorData generateResources=true reindex=true
```

You can check that DSE Search is up and running sucessfully by going to ```http://[DSE node]:8983/solr/``` and running queries via the GUI.

With CQL you can query the sensor data table using the primary key (name) and clustering column range scan (time) like so:
```
SELECT * FROM sparksensordata.sensordata WHERE name='p100' AND time>'2016-07-01 22:52:52';
```

But you cant query on the value column because it isn't indexed.

Solr integration allows you to search on any column, for example, values greater than 1:
```
SELECT * FROM sparksensordata.sensordata WHERE solr_query='{"q":"*:*",  "fq":["value:[1 TO *]"]}';
```

Solr has lots of flexible searching options. 
All events in the last day:
```
SELECT * FROM sparksensordata.sensordata where solr_query='{"q":"name:p100",  "fq":["time:[NOW-1DAY TO *]"]}';
```
Or a faceted search to build a distribution graph:
```
SELECT * FROM sparksensordata.sensordata where solr_query='{"q":"name:p100", "facet":{"field":"value"}}';
```

We can bucket those results using facet ranges:
```
 SELECT * FROM sparksensordata.sensordata where solr_query='{"q":"name:p100", "facet":{"range":"value", "range.start":-1, "range.end":5, "range.gap":0.1}}';
 ```

##Notes On ReST, Prepared Statements, And The Differences Between CQL And Solr

We can now query the data in our Cassandra database table ```SparkSensoreData.SensorData``` using either CQL or CQL-Solr.

Time for a quick code review...

###CQL

Using CQL simply substitutes one or more bind variables from the ReST call into a prepared CQL statement.

In this example we create a prepared statement with subsitutions for the Merchant ID and a query date:
```
private static final String GET_DAILY_TRANSACTIONS_BY_MERCHANT = "select * from " + merchantDailyRollupTable // SA
			+ " where merchant = ? and day = ? limit 100";
```
And then we can pass in the Merchant ID and a date as bind variables:
```
ResultSet resultSet = this.session.execute(getDailyTransactionsByMerchant.bind(merchant, day));
```

###CQL-Solr

Calls with CQL-Solr behave slightly differently - the entire where clause has to be subsituted as a single variable.

As for a CQL query, we prepare a statement with a substitution variable, but this time for the entire where cause (```solr_query```).
```
private static final String GET_ALL_TRANSACTIONS_BY_AMOUNT = "select * from " + rtfapTransactionTable     // SA
			+ " where solr_query = ? limit 100";
```		
We build a Solr query string to bind:
```
String solrBindString = "{\"q\":\"*:*\", \"fq\":\"amount:[" + amount + " TO *]\"}}";
```
And then bind it to the prepared statement:
```
ResultSet resultSet = this.session.execute(getAllTransactionsByAmount.bind(solrBindString));
```			

#!!put a solr query back in to the demo

##Accessing the Interface

Assuming that you're running on a single node
http://127.0.0.1:7001/restserver/rest/html


