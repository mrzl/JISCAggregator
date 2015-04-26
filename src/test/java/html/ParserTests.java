package html;

import jisc.event.EventSource;
import jisc.input.html.JciEventsHtmlParser;
import jisc.input.html.RoyalAcademyHtmlParser;
import jisc.input.rss.RSSFeedParser;
import jisc.misc.HelperMethods;

import java.util.logging.Level;

import static org.junit.Assert.assertTrue;

/**
 * Created by mrzl on 15.04.2015.
 */
public class ParserTests {

    @org.junit.Test
    public void testRead() {
        HelperMethods.setGlobalLogLevel( Level.SEVERE );

        EventSource jciLondon = new EventSource( "JCI London", "http://www.jcilondon.org.uk/events/index.html" );
        JciEventsHtmlParser parser = new JciEventsHtmlParser();
        parser.parse();
        assertTrue( parser.getEvents( ).size( ) > 0 );
    }

    @org.junit.Test
    public void testNetworkingEventsLondon() {
        HelperMethods.setGlobalLogLevel( Level.SEVERE );

        EventSource networkingEventsLondon = new EventSource( "Networking Events London", "http://feeds2.feedburner.com/Networking-Events-In-London" );

        RSSFeedParser parser = new RSSFeedParser( networkingEventsLondon );
        parser.parse();
        assertTrue( parser.getEvents().size( ) > 0 );
    }

    @org.junit.Test
    public void testRoyalAcademy() {
        HelperMethods.setGlobalLogLevel( Level.SEVERE );

        RoyalAcademyHtmlParser royalAcademy = new RoyalAcademyHtmlParser();
        royalAcademy.parse();
        assertTrue( royalAcademy.getEvents().size( ) > 0 );
    }
}
