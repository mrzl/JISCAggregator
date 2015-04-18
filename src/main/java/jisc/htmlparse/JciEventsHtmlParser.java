package jisc.htmlparse;

import jisc.general.Event;
import jisc.general.EventSource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mrzl on 15.04.2015.
 */
public class JciEventsHtmlParser extends HtmlParser implements HtmlParserInterface {

    public JciEventsHtmlParser () {
        super();

        eventSource = new EventSource( "JCI London", "http://www.jcilondon.org.uk/events/index.html" );
    }

    public void parse() {
        try {
            URL url = new URL( eventSource.getUrl( ) );
            try {
                Document htmlDocument = Jsoup.connect( url.toString( ) ).get( );
                Elements eventDiv = htmlDocument.select( "div.event_box" );

                // looping through all events listed on the site
                for ( int i = 0; i < eventDiv.size( ); i++ ) {

                    String finalTitle = null;
                    String finalVenue = null;
                    String finalType = null;
                    String finalPrice = null;
                    String finalUrl = null;
                    Date finalDate = null;

                    Element singleEventHtml = eventDiv.get( i );
                    Element titleElement = singleEventHtml.select( "p.eee_title" ).first( );
                    finalTitle = titleElement.select( "a" ).html( );
                    finalUrl = titleElement.select( "a" ).attr( "href" );
                    Elements eventTypeDetailsBaseElements = singleEventHtml.select( "div.event_type_details tbody" ).first( ).children( );


                    finalType = eventTypeDetailsBaseElements.get( 0 ).child( 1 ).html( );
                    finalVenue = eventTypeDetailsBaseElements.get( 1 ).child( 1 ).html( );
                    finalDate = parseDate( eventTypeDetailsBaseElements.get( 2 ).child( 1 ).html( ) );
                    try {
                        finalPrice = eventTypeDetailsBaseElements.get( 3 ).child( 1 ).html( );
                    } catch ( IndexOutOfBoundsException e ) {
                        //System.out.println( "No Price associated with the event" );
                    }

                    Event e = new Event( finalTitle, finalVenue + " " + finalType + " Price: " + finalPrice, finalUrl, finalDate, "JCI" );
                    events.add( e );
                }

            } catch ( IOException e ) {
                e.printStackTrace( );
            }

        } catch ( MalformedURLException e ) {
            e.printStackTrace( );
        }
    }

    /**
     * This parses a String that's being extracted from the JCI website into a proper java.util.Date
     *
     * @param _date String containing a date in the following format -> "1st Apr, 2015"
     * @return a proper java.util.Date object
     */
    private Date parseDate ( String _date ) {
        DateFormat df = new SimpleDateFormat( "dd MMM yyyy", Locale.ENGLISH );
        _date = _date.replace( "th", "" );
        _date = _date.replace( "st", "" );
        _date = _date.replace( "nd", "" );
        _date = _date.replace( "rd", "" );
        _date = _date.replace( ",", "" );
        Date parsedDate = null;
        try {
            parsedDate = df.parse( _date );
        } catch ( ParseException e ) {
            e.printStackTrace( );
        }

        return parsedDate;
    }
}
