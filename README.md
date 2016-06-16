

# ReST Server - Query Cassandra Data Quickly

## Querying Data Using A ReST Web Interface

A ReSTful web interface provides an API for calling programs to query the data in Cassandra.
To use the web service, use the following url’s. These will return a json representation of the data using the ReST service.

The sample queries are served by a web service written in Java. The code for this web service is provided in the repo.
The web service adopts a framework that separates the web, service and data access layers into individual, easily maintainable components.

You'll need to install Maven to compile the code. As the root account use apt-get to install it:
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

