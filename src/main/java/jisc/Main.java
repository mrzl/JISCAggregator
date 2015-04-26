package jisc;

import jisc.event.EventContainer;
import jisc.event.EventSource;
import jisc.input.html.JciEventsHtmlParser;
import jisc.input.html.RoyalAcademyHtmlParser;
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

        // adds network events london
        RSSFeedParser nelRssParser = new RSSFeedParser( networkingEventsLondon );
        nelRssParser.parse( );
        container.addEvents( nelRssParser.getEvents( ), networkingEventsLondon );

        // adds jci events
        JciEventsHtmlParser jciEventsHtmlParser = new JciEventsHtmlParser( );
        jciEventsHtmlParser.parse( );
        container.addEvents( jciEventsHtmlParser.getEvents( ), jciEventsHtmlParser.getEventSource( ) );

        // adds royal academy events
        RoyalAcademyHtmlParser royalAcademy = new RoyalAcademyHtmlParser();
        royalAcademy.parse();
        container.addEvents( royalAcademy.getEvents(), royalAcademy.getEventSource() );

        //HelperMethods.printEvents( container, networkingEventsLondon );
        //HelperMethods.printEvents( container, jciEventsHtmlParser.getEventSource());
        //HelperMethods.printEvents( container, royalAcademy.getEventSource() );
    }


}
