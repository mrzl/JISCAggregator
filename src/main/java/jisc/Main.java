package jisc;

import jisc.event.EventContainer;
import jisc.event.EventSource;
import jisc.input.html.JciEventsHtmlParser;
import jisc.misc.HelperMethods;
import jisc.input.rss.RSSFeedParser;

import java.util.logging.*;

/**
 * Created by mrzl on 13.04.2015.
 */
public class Main {
    public static void main ( String[] args ) {
        HelperMethods.setGlobalLogLevel( Level.SEVERE );

        EventContainer container = new EventContainer( );

        EventSource networkingEventsLondon = new EventSource( "Networking Events London", "http://feeds2.feedburner.com/Networking-Events-In-London" );
        //EventSource jciLondon = new EventSource( "JCI London", "http://www.jcilondon.org.uk/events/index.html" );

        RSSFeedParser nelRssParser = new RSSFeedParser( networkingEventsLondon.getUrl( ) );
        nelRssParser.parse( );
        container.addEvents( nelRssParser.getEvents( ), networkingEventsLondon );

        JciEventsHtmlParser jciEventsHtmlParser = new JciEventsHtmlParser( );
        jciEventsHtmlParser.parse( );
        container.addEvents( jciEventsHtmlParser.getEvents( ), jciEventsHtmlParser.getEventSource( ) );

        //HelperMethods.printEvents( container, networkingEventsLondon );
        //HelperMethods.printEvents( container, jciEventsHtmlParser.getEventSource());
    }


}
