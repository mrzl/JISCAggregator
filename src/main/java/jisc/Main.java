package jisc;

import jisc.general.Event;
import jisc.general.EventContainer;
import jisc.general.EventSource;
import jisc.rss.Feed;
import jisc.rss.FeedMessage;
import jisc.rss.RSSFeedParser;

/**
 * Created by mrzl on 13.04.2015.
 */
public class Main {
    public static void main ( String[] args ) {
        EventContainer container = new EventContainer();

        EventSource networkingEventsLondon = new EventSource( "Networking Events London", "http://feeds2.feedburner.com/Networking-Events-In-London" );

        RSSFeedParser parser = new RSSFeedParser( networkingEventsLondon.getUrl( ) );
        Feed feed = parser.readFeed();

        for( FeedMessage m : feed.getMessages() ) {
            Event e = new Event( m.getTitle(), m.getDescription(), m.getLink(), m.getAuthor() );
            container.addEvent( e, networkingEventsLondon );
        }
    }
}
