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
import java.util.logging.Logger;

/**
 * Created by mrzl on 26.04.2015.
 */
public class RoyalAcademyHtmlParser extends Parser implements ParserInterface {

    private static final Logger logger = Logger.getLogger( RoyalAcademyHtmlParser.class.getName( ) );

    public RoyalAcademyHtmlParser () {
        this.eventSource = new EventSource( "Royal Academy London", "https://www.royalacademy.org.uk/exhibitions-and-events#events-index" );
    }

    @Override
    public void parse () {
        String eventSourceUrl = eventSource.getUrl( );
        try {
            URL url = new URL( eventSourceUrl );
            try {
                Connection connection = Jsoup.connect( url.toString( ) );
                Document htmlDocument = connection.get( );
                String eventBoxDivName = "div.exhibition-information";
                try {
                    Elements eventDiv = htmlDocument.select( eventBoxDivName );
                    System.out.println( "Events found: " + eventDiv.size() );
                    for ( int i = 0; i < eventDiv.size( ); i++ ) {
                        Element e = eventDiv.get( i );
                        //System.out.println( "Event " + i );
                        //System.out.println( e );
                    }
                } catch ( Exception e ) {
                    this.logger.severe( "Couldn't get the div " + eventBoxDivName + " from the document " + htmlDocument );
                }

                String secondEventBoxName = ".related-items__event";
                try {
                    Elements eventDiv = htmlDocument.select( secondEventBoxName );
                    System.out.println( "Elements found: " + eventDiv.size( ) );
                    for ( int i = 0; i < eventDiv.size( ); i++ ) {
                        Element e = eventDiv.get( i );
                        //System.out.println( "Event " + i );
                        //System.out.println( e );
                    }
                } catch ( Exception e ) {
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
}
