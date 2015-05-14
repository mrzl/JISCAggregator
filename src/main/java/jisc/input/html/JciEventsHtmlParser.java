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
 * This parses the events from the JCI event page
 * http://www.jcilondon.org.uk/events/
 *
 */
public class JciEventsHtmlParser extends Parser {

    private static final Logger logger = Logger.getLogger( JciEventsHtmlParser.class.getName( ) );

    private final String MAIN_EVENT_DIV = "div.event_box";
    private final String EVENT_TITLE_ELEMENT = "p.eee_title";
    private final String EVENT_VENUE_ELEMENT = "div.event_type_details tbody";
    private final String EVENT_TYPE_ELEMENT = "div.event_type_details tbody";
    private final String EVENT_URL_ELEMENT = "p.eee_title";
    private final String EVENT_DATE_ELEMENT = "div.event_type_details tbody";
    private final String EVENT_PRICE_ELEMENT = "div.event_type_details tbody";

    /**
     * Extends the parser with a custom {@link jisc.event.EventSource}
     */
    public JciEventsHtmlParser () {
        super( new EventSource( "JCI London", "http://www.jcilondon.org.uk/events/index.html" ) );
    }

    /**
     * Parses all events.
     */
    @Override
    public void parse () {
        String eventSourceUrl = super.eventSource.getUrl( );
        try {
            URL url = new URL( eventSourceUrl );
            try {
                Connection connection = Jsoup.connect( url.toString() );
                Document htmlDocument = connection.get( );
                String eventBoxDivName = this.MAIN_EVENT_DIV;
                try {
                    Elements eventDiv = htmlDocument.select( eventBoxDivName );
                    // looping through all events listed on the site
                    for ( Iterator< Element > iterator = eventDiv.iterator( ); iterator.hasNext( ); ) {
                        Element _element = iterator.next();
                        Event _event = parseEventFromDiv( _element );
                        super.events.add( _event );
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
     * Parses the collection of different information associated with one event
     *
     * @param _element
     * @return
     */
    private Event parseEventFromDiv ( Element _element ) {
        String _title = getEventTitle( _element );
        String _venue = getEventVenue( _element );
        String _type = getEventType( _element );
        String _price = getEventPrice( _element );
        String _url = getEventUrl( _element );
        Date _date = getEventDate( _element );

        return new Event( _title, _venue + " " + _type + " Price: " + _price, _url, _date.toString(), this.eventSource.getPrettyName() );
    }

    /**
     * Parses the event title.
     *
     * @param _element the html element from which the title is being extracted from
     * @return
     */
    private String getEventTitle ( Element _element ){
        this.logger.entering( getClass().getName(), "getEventTitle" );
        String eventTitle = null;

        try {
            eventTitle = _element.select( this.EVENT_TITLE_ELEMENT ).first( ).select( "a" ).html( );
        } catch ( Exception e ) {
            this.logger.severe( "Couldn't load the event title from document: " + _element );
            this.logger.severe( HelperMethods.getStackTraceFromException( e ) );
        }

        this.logger.exiting( getClass().getName(), "getEventTitle" );
        return eventTitle;
    }

    /**
     * Parses the event venue name.
     *
     * @param _element the html element from which the venue is being extracted from
     * @return
     */
    private String getEventVenue ( Element _element ) {
        this.logger.entering( getClass().getName(), "getEventVenue" );
        String eventVenue = null;

        try {
            eventVenue = _element.select( this.EVENT_VENUE_ELEMENT ).first( ).children( ).get( 1 ).child( 1 ).html( );
        } catch ( Exception e ) {
            this.logger.severe( "Couldn't load the event venue from document: " + _element );
            this.logger.severe( HelperMethods.getStackTraceFromException( e ) );
        }

        this.logger.exiting( getClass().getName(), "getEventVenue" );
        return eventVenue;
    }

    /**
     * Parses the event type.
     *
     * @param _element the html element from which the type is being extracted from
     * @return
     */
    private String getEventType ( Element _element ) {
        this.logger.entering( getClass().getName(), "getEventType" );
        String eventType = null;

        try {
            eventType = _element.select( this.EVENT_TYPE_ELEMENT ).first( ).children( ).get( 0 ).child( 1 ).html( );
        } catch ( Exception e ) {
            this.logger.severe( "Couldn't load the event type from document: " + _element );
            this.logger.severe( HelperMethods.getStackTraceFromException( e ) );
        }

        this.logger.exiting( getClass().getName(), "getEventType" );
        return eventType;
    }

    /**
     * Parses the event url.
     *
     * @param _element the html element from which the venue is being extracted from
     * @return
     */
    private String getEventUrl ( Element _element ) {
        this.logger.entering( getClass().getName(), "getEventUrl" );
        String eventUrl = null;

        try {
            eventUrl = _element.select( this.EVENT_URL_ELEMENT ).first( ).select( "a" ).attr( "href" );

            // fixes the irregular urls, which sometimes come without the website as part of the url
            String websiteAdress = "http://www.jciuk.org.uk";
            String secondWebsiteAdress = "http://www.jcilondon.org.uk";
            if( !eventUrl.contains( websiteAdress ) ) {
                eventUrl = secondWebsiteAdress + eventUrl;
            }
        } catch ( Exception e ) {
            this.logger.severe( "Couldn't load the event url from document: " + _element );
            this.logger.severe( HelperMethods.getStackTraceFromException( e ) );
        }

        this.logger.exiting( getClass().getName(), "getEventUrl" );
        return eventUrl;
    }

    /**
     * Parses the event date.
     *
     * @param _element the html element from which the date is being extracted from
     * @return
     */
    private Date getEventDate ( Element _element ) {
        this.logger.entering( getClass().getName(), "getEventDate" );
        Date eventDate = null;

        try {
            String eventDateString = _element.select( this.EVENT_DATE_ELEMENT ).first( ).children( ).get( 2 ).child( 1 ).html( );
            eventDate = this.parseDate( eventDateString );
        } catch ( Exception e ) {
            this.logger.severe( "Couldn't load the event date from document: " + _element );
            this.logger.severe( HelperMethods.getStackTraceFromException( e ) );
        }

        this.logger.exiting( getClass().getName(), "getEventDate" );
        return eventDate;
    }

    /**
     * Parses the price of an event ticket.
     *
     * @param _element the html element from which the price is being extracted from
     * @return
     */
    private String getEventPrice ( Element _element ) {
        this.logger.entering( getClass().getName(), "getEventPrice" );
        String eventPrice = null;

        try {
            Element e = _element.select( this.EVENT_PRICE_ELEMENT ).first( ).children( ).get( 3 ).child( 1 );
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
