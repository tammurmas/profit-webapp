# profit-webapp
A small test exercise for profitsoftware.com using the Spring framework and H2 database.
The system allows to perform CRUD-operations on a database table holding customer data.
Provided customer usernames have to be unique. To check the validity of data a custom validator has been implemented in addition to validating data with Hibernate validation.

To run from command line clone and execute:

Jetty(mvn jetty:run) - address: localhost:8080

JBoss(mvn wildfly:run) - address: localhost:8080/profit-webapp

Tomcat(mvn clean install tomcat7:run) - address: localhost:8080/profit-webapp/customer

H2 in-memory database is used to store data, meaning all the data is going to be discarded on server restart.
