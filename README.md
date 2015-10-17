# profit-webapp
A small test exercise for profitsoftware.com using the Spring framework and H2 database.

To run from command line clone and execute:

Jetty(mvn clean install jetty:run) - address: localhost:80/profit-webapp/customer

Tomcat(mvn clean install tomcat7:run) - address: localhost:8080/profit-webapp/customer

JBoss(mvn clean install jboss-as:run) - address: localhost:8080/profit-webapp/customer

H2 in-memory database is used to store data, meaning all the data is going to be discarded on server restart.
