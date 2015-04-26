package rss;

import jisc.event.EventSource;
import jisc.input.rss.RSSFeedParser;

import static org.junit.Assert.*;


/**
 * Created by mrzl on 13.04.2015.
 */
public class RssFeedTest {

    @org.junit.Test
    public void testNetworkingEventsLondon() {
        EventSource networkingEventsLondon = new EventSource( "Networking Events London", "http://feeds2.feedburner.com/Networking-Events-In-London" );

        RSSFeedParser parser = new RSSFeedParser( networkingEventsLondon );
        parser.parse();
        assertTrue( parser.getEvents().size() > 0 );
    }
}