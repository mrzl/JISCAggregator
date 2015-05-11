JISC Aggregator
===============
In order to run the thing, open the JISCAggregator.iml with IntelliJ IDEA, compile and run. At this stage of the project
no deployment/build setup is being taken care of. This is an working prototype, no production software.

What this software essentially does at this point is to collect all events from the three sources independently:
* JCI London - http://www.jcilondon.org.uk/events/
* Networking Events London - http://www.findnetworkingevents.com/events/index.cfm?action=eventslist&towncity=London
* Royal Academy of Arts - https://www.royalacademy.org.uk/exhibitions-and-events#events-index

Internally all events of these sources are being unified into one data structure that treats all events equally- it 
unifies them in one system, that can potentially be searched and makes it easier to find events that are interesting
to you.

Once the web server is running, you can access all aggregated events at http://localhost:4567/

If you just want to see the output of this prototype download and open this website with your browser.
https://github.com/mrzl/JISCAggregator/releases/download/v0.01a/06052015.zip

Todo
----
- [x] reflect about scalability
- [x] write sufficient amount of tests
- [ ] re-read the scalability section

Dependencies
------------
Tested under Windows 8.1 and Ubuntu 14.04.

Maven mependencies:
- Java 1.8
- JUnit 4.4
- Apache Commons Lang 3.1
- JSoup 1.8.1
- Rendersnake 1.8
- SparkJava 2.1

Scalability
-----------
Additional sources of events can be integrated easily, as the structure of the software allows to simply extend the
Parser interface, which gives the base structure for adding hundreds of sources.

##### Persistency
At this point, the persistency of the aggregated events, is being kept on a very minimal level, the events are simply exported into a html page for display. Creating a proper persistent system that stores events in a database, the future of this project needs to be sketched out with more detail. Storing data over and over again in a database turned out to be a sensitive task that went out of scope of this prototype. Some of the problems of keeping an database of aggregated events up to date are sketchd out in the following:
* Whenever the databse is being 'refreshed' ( meaning, all sources are being aggregated for new events ) the current state of the database needs to be compared with the 'new' event set, skipping duplicates.
* While skipping duplicates is important, but at the same time, possible updates of the events need to be integrated into the excisting event instance. This merge of data is highly sensitive and needs to be tested extensively.
* The databse system needs to be flexible in order to add more fields of information for each event type and be fast to access/write.

##### Maintenance
The more event sources are added, the more needs to be maintained. Imagining the future, where dozens of sources
are being scraped for events, some websites change their feed, which make the aggregator fail to retrieve events
from this particular source. This can be easily detected via the tests provided in this version of the software 
already, though this is something to keep in mind, as the cost of keeping the system running will raise in 
relation to the amount of sources which are being aggregated. With forward thinking software design and 
rock solid testing this maintenance effort can be kept very low.

##### Searchability
The existing structure opens up the possibility to search the database. This requires actions like developing a user
interface and criteria to access the database. It's out of scope from the goal of creating a prototype and not implemented.

##### Legal issues
For the sake of reliability of the service, it would be advised to contact the sources that are being aggregated and 
ask for their official permission and possible collaboration.

##### Infrastructure
For next iterations of this project a working infrastructure to deploy should be developed. 

##### Flexiblity
Another possible branch of development would be to create a system for other, similar aggregation methods. Collection data of other kinds, and summarize them in one database.

Tests
-----
All tests are contained in the src/test/java package.

Author
------
Marcel Schwittlick

marcel.schwittlick@gmail.com

License
-------
This project is licensed under Apache License Version 2.0 - January 2004
http://www.apache.org/licenses/
