package jisc.input.html;

import jisc.event.Event;
import jisc.event.EventSource;
import jisc.input.Parser;
import jisc.input.ParserInterface;
import jisc.misc.HelperMethods;
import org.jsoup.Connection;
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
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Created by mrzl on 15.04.2015.
 */
public class JciEventsHtmlParser extends Parser implements ParserInterface {

    private static final Logger logger = Logger.getLogger( JciEventsHtmlParser.class.getName( ) );

    /**
     *
     */
    public JciEventsHtmlParser () {
        super( );

        this.eventSource = new EventSource( "JCI London", "http://www.jcilondon.org.uk/events/index.html" );
    }

    /**
     *
     */
    @Override
    public void parse () {
        String eventSourceUrl = eventSource.getUrl();
        try {
            URL url = new URL( eventSourceUrl );
            try {
                Connection connection = Jsoup.connect( url.toString() );
                Document htmlDocument = connection.get( );
                String eventBoxDivName = "div.event_box";
                try {
                    Elements eventDiv = htmlDocument.select( eventBoxDivName );
                    // looping through all events listed on the site
                    for ( Iterator< Element > iterator = eventDiv.iterator( ); iterator.hasNext( ); ) {
                        Element _element = iterator.next();
                        Event e = parseEventFromDiv( _element );
                        events.add( e );
                    }
                }catch( Exception e ) {
                    this.logger.severe( "Couldn't get the div " + eventBoxDivName + " from the document " + htmlDocument );
                }
            } catch ( IOException e ) {
                this.logger.severe( "Couldn't get document from the passed URL: " + url );
                this.logger.severe( HelperMethods.getStackTraceFromException( e ) );
            }
        } catch ( MalformedURLException e ) {
            this.logger.severe( "Couldn't construct a valid URL from the EventSource url string: " + eventSourceUrl );
            this.logger.severe( HelperMethods.getStackTraceFromException( e ) );
        }
    }

    /**
     *
     *
     * @param _element@return
     */
    private Event parseEventFromDiv ( Element _element ) {
        String finalTitle = getEventTitle( _element );
        String finalVenue = getEventVenue( _element );
        String finalType = getEventType( _element );
        String finalPrice = getEventPrice( _element );
        String finalUrl = getEventUrl( _element );
        Date finalDate = getEventDate( _element );

        return new Event( finalTitle, finalVenue + " " + finalType + " Price: " + finalPrice, finalUrl, finalDate, this.eventSource.getPrettyName() );
    }

    /**
     *
     *
     * @param _element@return
     */
    private String getEventTitle ( Element _element ){
        this.logger.entering( getClass().getName(), "getEventTitle" );
        String eventTitle = null;

        try {
            eventTitle = _element.select( "p.eee_title" ).first( ).select( "a" ).html( );
        } catch ( Exception e ) {
            this.logger.severe( "Couldn't load the event title from document: " + _element );
            this.logger.severe( HelperMethods.getStackTraceFromException( e ) );
        }

        this.logger.exiting( getClass().getName(), "getEventTitle" );
        return eventTitle;
    }

    /**
     *
     *
     * @param _element@return
     */
    private String getEventVenue ( Element _element ) {
        this.logger.entering( getClass().getName(), "getEventVenue" );
        String eventVenue = null;

        try {
            eventVenue = _element.select( "div.event_type_details tbody" ).first( ).children( ).get( 1 ).child( 1 ).html( );
        } catch ( Exception e ) {
            this.logger.severe( "Couldn't load the event venue from document: " + _element );
            this.logger.severe( HelperMethods.getStackTraceFromException( e ) );
        }

        this.logger.exiting( getClass().getName(), "getEventVenue" );
        return eventVenue;
    }

    /**
     *
     *
     * @param _element@return
     */
    private String getEventType ( Element _element ) {
        this.logger.entering( getClass().getName(), "getEventType" );
        String eventType = null;

        try {
            eventType = _element.select( "div.event_type_details tbody" ).first( ).children( ).get( 0 ).child( 1 ).html( );
        } catch ( Exception e ) {
            this.logger.severe( "Couldn't load the event type from document: " + _element );
            this.logger.severe( HelperMethods.getStackTraceFromException( e ) );
        }

        this.logger.exiting( getClass().getName(), "getEventType" );
        return eventType;
    }

    /**
     *
     *
     * @param _element@return
     */
    private String getEventUrl ( Element _element ) {
        this.logger.entering( getClass().getName(), "getEventUrl" );
        String eventUrl = null;

        try {
            eventUrl = _element.select( "p.eee_title" ).first( ).select( "a" ).attr( "href" );
        } catch ( Exception e ) {
            this.logger.severe( "Couldn't load the event url from document: " + _element );
            this.logger.severe( HelperMethods.getStackTraceFromException( e ) );
        }

        this.logger.exiting( getClass().getName(), "getEventUrl" );
        return eventUrl;
    }

    /**
     *
     *
     * @param _element@return
     */
    private Date getEventDate ( Element _element ) {
        this.logger.entering( getClass().getName(), "getEventDate" );
        Date eventDate = null;

        try {
            String eventDateString = _element.select( "div.event_type_details tbody" ).first( ).children( ).get( 2 ).child( 1 ).html( );
            eventDate = this.parseDate( eventDateString );
        } catch ( Exception e ) {
            this.logger.severe( "Couldn't load the event date from document: " + _element );
            this.logger.severe( HelperMethods.getStackTraceFromException( e ) );
        }

        this.logger.exiting( getClass().getName(), "getEventDate" );
        return eventDate;
    }

    /**
     *
     *
     * @param _element@return
     */
    private String getEventPrice ( Element _element ) {
        this.logger.entering( getClass().getName(), "getEventPrice" );
        String eventPrice = null;

        try {
            Element e = _element.select( "div.event_type_details tbody" ).first( ).children( ).get( 3 ).child( 1 );
            eventPrice = e.html( );
        } catch ( Exception e ) {
            this.logger.warning( "Couldn't load the event price from document: " + _element );
            //this.logger.severe( HelperMethods.getStackTraceFromException( e ) );
        }

        this.logger.exiting( getClass().getName(), "getEventPrice" );
        return eventPrice;
    }

    /**
     * This parses a String that's being extracted from the JCI website into a proper java.util.Date
     *
     * @param _date String containing a date in the following format -> "1st Apr, 2015"
     * @return a proper java.util.Date object or null when the date could not be parsed
     */
    private Date parseDate ( String _date ) {
        _date = _date.replace( "th", "" );
        _date = _date.replace( "st", "" );
        _date = _date.replace( "nd", "" );
        _date = _date.replace( "rd", "" );
        _date = _date.replace( ",", "" );

        DateFormat df = new SimpleDateFormat( "dd MMM yyyy", Locale.ENGLISH );
        try {
            return df.parse( _date );
        } catch ( ParseException e ) {
            this.logger.severe( "Couldn't parse the date string " + _date + " with the date format: " + df );
            this.logger.severe( HelperMethods.getStackTraceFromException( e ) );
        }

        return null;
    }
}
