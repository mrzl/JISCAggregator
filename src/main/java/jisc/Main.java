package jisc;

import jisc.event.Event;
import jisc.event.EventContainer;
import jisc.event.EventSource;
import jisc.htmlparse.JciEventsHtmlParser;
import jisc.output.ConsoleOutput;
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
        //EventSource jciLondon = new EventSource( "JCI London", "http://www.jcilondon.org.uk/events/index.html" );

        RSSFeedParser parser = new RSSFeedParser( networkingEventsLondon.getUrl( ) );
        Feed feed = parser.readFeed();

        for( FeedMessage m : feed.getMessages() ) {
            Event e = new Event( m.getTitle(), m.getDescription(), m.getLink(), m.getDate(), m.getAuthor() );
            container.addEvent( e, networkingEventsLondon );
        }

        JciEventsHtmlParser jciEventsHtmlParser = new JciEventsHtmlParser();
        jciEventsHtmlParser.parse();
        container.addEvents( jciEventsHtmlParser.getEvents(), jciEventsHtmlParser.getEventSource() );

        ConsoleOutput.printEvents( container, networkingEventsLondon );
        ConsoleOutput.printEvents( container, jciEventsHtmlParser.getEventSource());
    }
}
