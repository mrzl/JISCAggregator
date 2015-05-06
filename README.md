JISC Aggregator
===============
In order to run the thing, open the JISCAggreagot.iml with IntelliJ IDEA, compile and run. At this stage of the project
no deployment/build setup is being taken care of. This is an working prototype, no production software.

Once the web server is running, you can access all aggregated events at http://localhost:45678/

Todo
----
- [ ] reflect about scalability
- [ ] write sufficient amount of tests

Dependencies
------------
All dependencies are handled via Maven.

- Java 1.8
- JUnit 4.4
- Apache Commons Lang 3.1
- JSoup 1.8.1
- Rendersnake 1.8
- SparkJava 2.1

Tests
-----
All tests are contained in the src/test/java package.

Test cover the following entities:
- [x] network connection
- [x] possibility to create the webserver
- [x] html page creation
- [x] each parser
- [ ] all classes should be tested in general

Scalability
-----------
Additional sources of events can be integrated easily, as the structure of the software allows to simply extend the
Parser interface, which gives the base structure for adding hundreds of even sources. The problem really lies in two aspects:

1. When the data of events is saved in a database and the software updates the database on a specific interval, it is
important develop a good system to manage old/outdated and new events.
2. The more event sources are added, the more needs to be maintained. Imagining the future, where dozens of sources
are being scraped for events, some websites change their feed. This can be easily detected via the tests provided in this
version of the software already, though this is something to keep in mind, as the cost of keeping the system running
will raise in relation to the amount of sources which are being aggregated. With forward thinking software design
and rock solid testing this maintanance effort can be kept very low.

Author
------
Marcel Schwittlick
marcel.schwittlick@gmail.com

License
-------
This project is licensed under Apache License Version 2.0 - January 2004
http://www.apache.org/licenses/
