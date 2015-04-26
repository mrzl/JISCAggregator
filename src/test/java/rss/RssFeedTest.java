package rss;

import jisc.input.rss.Feed;
import jisc.input.rss.RSSFeedParser;

import static org.junit.Assert.*;


/**
 * Created by mrzl on 13.04.2015.
 */
public class RssFeedTest {

    @org.junit.Test
    public void testNetworkingEventsLondon() {
        RSSFeedParser parser = new RSSFeedParser("http://feeds2.feedburner.com/Networking-Events-In-London");
        parser.parse();
        Feed feed = parser.getParsedFeed( );
        printFeed( feed );
        assertTrue( feed.getMessages().size() > 0 );
    }

    private void printFeed( Feed feed ) {
        feed.getMessages( ).forEach( System.out::println );
    }
}