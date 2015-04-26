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
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * Created by mrzl on 26.04.2015.
 */
public class RoyalAcademyHtmlParser extends Parser implements ParserInterface {

    private static final Logger logger = Logger.getLogger( RoyalAcademyHtmlParser.class.getName( ) );

    private final String TOP_EVENTS_HTML_CLASS = "div.exhibition-information";
    private final String RELATED_EVENTS_HTML_CLASS = ".related-items__event";

    public RoyalAcademyHtmlParser () {
        super.eventSource = new EventSource( "Royal Academy London", "https://www.royalacademy.org.uk/exhibitions-and-events#events-index" );
    }

    @Override
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
            System.out.println( "Events found: " + eventDiv.size( ) );
            for ( Iterator< Element > iterator = eventDiv.iterator( ); iterator.hasNext( ); ) {
                Element e = iterator.next( );
                //System.out.println( "Event " + i );
                //System.out.println( e );
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
            System.out.println( "Elements found: " + eventDiv.size( ) );
            for ( Iterator< Element > iterator = eventDiv.iterator( ); iterator.hasNext( ); ) {
                Element e = iterator.next( );
                //System.out.println( "Event " + i );
                //System.out.println( e );
            }
        } catch ( Exception e ) {
            this.logger.severe( "Couldn't get the div " + eventBoxDivName + " from the document " + _rootDocument );
        }

        return _events;
    }
}
