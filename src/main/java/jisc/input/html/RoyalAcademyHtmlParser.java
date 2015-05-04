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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * Created by mrzl on 26.04.2015.
 */
public class RoyalAcademyHtmlParser extends Parser implements ParserInterface {

    private static final Logger logger = Logger.getLogger( RoyalAcademyHtmlParser.class.getName( ) );

    private final String TOP_EVENTS_HTML_CLASS = "div.exhibition-information";
    private final String TOP_EVENTS_TITLE_ELEMENT = "h1.exhibition-title";
    private final String TOP_EVENTS_SUBTITLE_ELEMENT = "h2.exhibition-subtitle";
    private final String TOP_EVENTS_DATE_ELEMENT = "h2.exhibition-dates";
    private final String TOP_EVENTS_DESCRIPTION_ELEMENT = "p.exhibition-description";
    private final String TOP_EVENTS_URL_ELEMENT = "button.more-info";

    private final String RELATED_EVENTS_HTML_CLASS = ".related-items__event";
    private final String RELATED_EVENTS_TITLE_ELEMENT = "h2.event-title";
    private final String RELATED_EVENTS_SUBTITLE_ELEMENT = "h3.event-subtitle";
    private final String RELATED_EVENTS_DESCRIPTION_ELEMENT = "p.event-summary";
    private final String RELATED_EVENTS_DATE_ELEMENT = "h3.event-date";
    private final String RELATED_EVENTS_PRICE_ELEMENT = "p.event-pricing";
    private final String RELATED_EVENTS_TYPE_ELEMENT = "h5.event-type";
    private final String RELATED_EVENTS_LOCATION_ELEMENT = "p.event-location";

    public RoyalAcademyHtmlParser () {
        super( new EventSource( "Royal Academy London", "https://www.royalacademy.org.uk/exhibitions-and-events#events-index" ) );
    }

    public void parse () {
        String eventSourceUrl = super.eventSource.getUrl( );
        try {
            URL url = new URL( eventSourceUrl );
            try {
                Connection connection = Jsoup.connect( url.toString( ) );
                Document htmlDocument = connection.get( );

                ArrayList< Event > topEvents = parseTopEvents( htmlDocument );
                ArrayList< Event > relatedEvents = parseRelatedEvents( htmlDocument );
                super.events.addAll( topEvents );
                super.events.addAll( relatedEvents );

            } catch ( IOException e ) {
                this.logger.severe( "Couldn't get document from the passed URL: " + url );
                this.logger.severe( HelperMethods.getStackTraceFromException( e ) );
            }
        } catch ( MalformedURLException e ) {
            this.logger.severe( "Couldn't construct a valid URL from the EventSource url string: " + eventSourceUrl );
            this.logger.severe( HelperMethods.getStackTraceFromException( e ) );
        }
    }

    private ArrayList< Event > parseTopEvents( Document _rootDocument ) {
        ArrayList< Event > _events = new ArrayList<>();

        String eventBoxDivName = TOP_EVENTS_HTML_CLASS;
        try {
            Elements eventDiv = _rootDocument.select( eventBoxDivName );
            for ( Iterator< Element > iterator = eventDiv.iterator( ); iterator.hasNext( ); ) {
                Element e = iterator.next( );
                String _title = e.select( this.TOP_EVENTS_TITLE_ELEMENT ).html();
                String _subtitle = e.select( this.TOP_EVENTS_SUBTITLE_ELEMENT ).html();
                // TODO: parse date
                String _date = e.select( this.TOP_EVENTS_DATE_ELEMENT ).html();
                String _description = e.select( this.TOP_EVENTS_DESCRIPTION_ELEMENT ).html();
                String _url = "https://www.royalacademy.org.uk" + e.select( this.TOP_EVENTS_URL_ELEMENT ).attr( "href" );

                Event _event = new Event( _title + " - " + _subtitle, _description, _url, new Date(), this.eventSource.getPrettyName() );
                _events.add( _event );
            }

        } catch ( Exception e ) {
            this.logger.severe( "Couldn't get the div " + eventBoxDivName + " from the document " + _rootDocument );
        }

        return _events;
    }

    private ArrayList< Event > parseRelatedEvents( Document _rootDocument ) {
        ArrayList< Event > _events = new ArrayList<>();

        String eventBoxDivName = RELATED_EVENTS_HTML_CLASS;
        try {
            Elements eventDiv = _rootDocument.select( eventBoxDivName );
            for ( Iterator< Element > iterator = eventDiv.iterator( ); iterator.hasNext( ); ) {
                Element e = iterator.next( );
                String _url = "https://www.royalacademy.org.uk" + e.child( 0 ).attr( "href" );
                String _title = e.select( this.RELATED_EVENTS_TITLE_ELEMENT ).html();
                String _subtitle = e.select( this.RELATED_EVENTS_SUBTITLE_ELEMENT ).html();
                // TODO: parse date
                String _date = e.select( this.RELATED_EVENTS_DATE_ELEMENT ).html();
                String _description = e.select( this.RELATED_EVENTS_DESCRIPTION_ELEMENT ).html();
                String _price = e.select( this.RELATED_EVENTS_PRICE_ELEMENT ).html();
                String _location = e.select( this.RELATED_EVENTS_LOCATION_ELEMENT).html();
                String _type = e.select( this.RELATED_EVENTS_TYPE_ELEMENT ).html();
                Event _event = new Event( _title + " - " + _subtitle, _description, _url, new Date(), this.eventSource.getPrettyName() );
                this.logger.info( "Event date of " + _title + " is: " + _date );
                _events.add( _event );
            }
        } catch ( Exception e ) {
            this.logger.severe( "Couldn't get the div " + eventBoxDivName + " from the document " + _rootDocument );
        }

        return _events;
    }
}
