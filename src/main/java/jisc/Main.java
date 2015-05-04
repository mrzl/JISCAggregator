package jisc;

import jisc.event.EventContainer;
import jisc.input.html.JciEventsHtmlParser;
import jisc.input.html.RoyalAcademyHtmlParser;
import jisc.misc.HelperMethods;
import jisc.input.rss.NetworkingEventsLondonRssParser;

import jisc.output.html.HtmlOutput;

import java.util.logging.*;

/**
 * Created by mrzl on 13.04.2015.
 * <p>
 * This is where the JISC Aggregator is started.
 * <p>
 * As of 03.05.2015 this aggregates all events publicly available at
 * 1. JCI London - http://www.jciuk.org.uk/events/
 * 2. Royal Academy of Arts London - https://www.royalacademy.org.uk/exhibitions-and-events#events-index
 * 3. Networking Events London - http://www.findnetworkingevents.com/in/central-london/
 */
public class Main {
    public static void main ( String[] args ) {
        HelperMethods.setGlobalLogLevel( Level.SEVERE );

        EventContainer container = new EventContainer( );

        // adds network events london
        NetworkingEventsLondonRssParser nelRssParser = new NetworkingEventsLondonRssParser( );
        nelRssParser.parse( );
        container.addEvents( nelRssParser.getEvents( ), nelRssParser.getEventSource() );

        // adds jci events
        JciEventsHtmlParser jciEventsHtmlParser = new JciEventsHtmlParser( );
        jciEventsHtmlParser.parse( );
        container.addEvents( jciEventsHtmlParser.getEvents( ), jciEventsHtmlParser.getEventSource( ) );

        // adds royal academy events
        RoyalAcademyHtmlParser royalAcademy = new RoyalAcademyHtmlParser( );
        royalAcademy.parse( );
        container.addEvents( royalAcademy.getEvents( ), royalAcademy.getEventSource( ) );

        // prints all events parsed
        //HelperMethods.printEvents( container, networkingEventsLondon );
        //HelperMethods.printEvents( container, jciEventsHtmlParser.getEventSource());
        //HelperMethods.printEvents( container, royalAcademy.getEventSource() );

        // starts the web server and populates a html table with all events inside the container
        new HtmlOutput( container );
    }
}
