package jisc;

import jisc.event.EventContainer;
import jisc.input.Parser;
import jisc.input.html.JciEventsHtmlParser;
import jisc.input.html.RoyalAcademyHtmlParser;
import jisc.misc.HelperMethods;
import jisc.input.rss.NetworkingEventsLondonRssParser;

import jisc.output.html.HtmlOutput;

import java.util.ArrayList;
import java.util.logging.*;

/**
 * This is where the JISC Aggregator is started.
 *
 * As of 15.05.2015 this aggregates all events publicly available at
 *
 * 1. JCI London - http://www.jciuk.org.uk/events/
 * 2. Royal Academy of Arts London - https://www.royalacademy.org.uk/exhibitions-and-events#events-index
 * 3. Networking Events London - http://www.findnetworkingevents.com/in/central-london/
 * 
 */
public class Main {
    public static void main ( String[] args ) {
        HelperMethods.setGlobalLogLevel( Level.SEVERE );

        EventContainer container = new EventContainer( );

        ArrayList< Parser > sources = new ArrayList<>();
        sources.add( new NetworkingEventsLondonRssParser() ); // adds network events london
        sources.add( new JciEventsHtmlParser( ) ); // adds jci events
        sources.add( new RoyalAcademyHtmlParser( ) ); // adds royal academy events

        // parse all sources
        sources.forEach( jisc.input.Parser::parse );
        for( Parser p : sources ) {
            container.addEvents( p.getEvents(), p.getEventSource() );
        }

        // prints all events parsed
        for( Parser p : sources ) {
            HelperMethods.printEvents( container, p );
        }

        // starts the web server and populates a html table with all events inside the container
        new HtmlOutput( container );
    }
}
