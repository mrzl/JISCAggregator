package html;


import jisc.event.EventSource;
import jisc.input.html.JciEventsHtmlParser;

import static org.junit.Assert.assertTrue;

/**
 * Created by mrzl on 15.04.2015.
 */
public class HtmlParseTest {
    @org.junit.Test
    public void testRead() {
        EventSource jciLondon = new EventSource( "JCI London", "http://www.jcilondon.org.uk/events/index.html" );
        JciEventsHtmlParser parser = new JciEventsHtmlParser();
        assertTrue( false );
    }
}
