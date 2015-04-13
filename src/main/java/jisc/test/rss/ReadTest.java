package jisc.test.rss;

import jisc.rss.Feed;
import jisc.rss.RSSFeedParser;

import static org.junit.Assert.*;


/**
 * Created by mrzl on 13.04.2015.
 */
public class ReadTest {

    @org.junit.Test
    public void testRead() {
        RSSFeedParser parser = new RSSFeedParser("http://www.vogella.com/article.rss");
        Feed feed = parser.readFeed();
        //printFeed( feed );
        assertTrue( feed.getMessages().size() > 0 );
    }

    @org.junit.Test
    public void testNetworkingEventsLondon() {
        RSSFeedParser parser = new RSSFeedParser("http://feeds2.feedburner.com/Networking-Events-In-London");
        Feed feed = parser.readFeed();
        //printFeed( feed );
        assertTrue( feed.getMessages().size() > 0 );
    }

    private void printFeed( Feed feed ) {
        feed.getMessages( ).forEach( System.out::println );
    }
}