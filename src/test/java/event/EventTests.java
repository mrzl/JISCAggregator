package event;

import jisc.event.Event;

import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * Created by mrzl on 26.04.2015.
 */
public class EventTests {
    @org.junit.Test
    public void simpleEvent () {

        Date date = new Date();
        Event event = new Event("title", "desc", "url11", date.toString(), "author" );

        boolean titleRight = event.getTitle() == "title" ? true : false;
        boolean descRight = event.getDescription( ) == "desc" ? true : false;
        boolean urlRight = event.getUrl( ) == "url11" ? true : false;
        boolean dateRight = event.getDate( ).equals( date.toString() ) ? true : false;
        boolean authorRight = event.getAuthor( ) == "author" ? true : false;

        assertTrue( titleRight && descRight && urlRight && dateRight && authorRight );
    }
}
