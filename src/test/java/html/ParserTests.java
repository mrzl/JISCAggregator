package html;

import jisc.input.html.JciEventsHtmlParser;
import jisc.input.html.RoyalAcademyHtmlParser;
import jisc.input.rss.NetworkingEventsLondonRssParser;
import jisc.misc.HelperMethods;

import java.util.logging.Level;

import static org.junit.Assert.assertTrue;

/**
 * Tests all parses of the project.
 */
public class ParserTests {

    @org.junit.Test
    public void testRead() {
        HelperMethods.setGlobalLogLevel( Level.SEVERE );

        JciEventsHtmlParser parser = new JciEventsHtmlParser();
        parser.parse();
        System.out.println( "JCI events: " + parser.getEvents().size() );
        assertTrue( parser.getEvents( ).size( ) > 0 );
    }

    @org.junit.Test
    public void testNetworkingEventsLondon() {
        HelperMethods.setGlobalLogLevel( Level.SEVERE );

        NetworkingEventsLondonRssParser parser = new NetworkingEventsLondonRssParser( );
        parser.parse();
        System.out.println( "NetworkingEventsLondon events: " + parser.getEvents().size() );
        assertTrue( parser.getEvents().size( ) > 0 );
    }

    @org.junit.Test
    public void testRoyalAcademy() {
        HelperMethods.setGlobalLogLevel( Level.SEVERE );

        RoyalAcademyHtmlParser royalAcademy = new RoyalAcademyHtmlParser();
        royalAcademy.parse();
        System.out.println( "Royal Academy events: " + royalAcademy.getEvents().size() );
        assertTrue( royalAcademy.getEvents().size( ) > 0 );
    }
}
