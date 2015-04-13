package jisc.test.rss;

import jisc.rss.Feed;
import jisc.rss.FeedMessage;
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
        assertTrue( feed.getMessages().size() > 0 );
        //for (FeedMessage message : feed.getMessages()) {
        //    System.out.println(message);
        //}
    }
}