package event;

import jisc.event.Event;

import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link jisc.event.Event} class
 */
public class EventTests {
    @org.junit.Test
    public void simpleEvent () {

        Date date = new Date();
        Event event = new Event("title", "desc", "url11", date.toString(), "author" );

        boolean titleRight = event.getTitle( ) == "title";
        boolean descRight = event.getDescription( ) == "desc";
        boolean urlRight = event.getUrl( ) == "url11";
        boolean dateRight = event.getDate( ).equals( date.toString( ) );
        boolean authorRight = event.getAuthor( ) == "author";

        assertTrue( titleRight && descRight && urlRight && dateRight && authorRight );
    }
}
