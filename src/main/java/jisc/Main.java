package jisc;

import jisc.event.Event;
import jisc.event.EventContainer;
import jisc.event.EventSource;
import jisc.input.html.JciEventsHtmlParser;
import jisc.input.html.RoyalAcademyHtmlParser;
import jisc.misc.HelperMethods;
import jisc.input.rss.RSSFeedParser;
import jisc.testing.Book;
import org.rendersnake.HtmlAttributes;
import org.rendersnake.HtmlCanvas;

import java.io.IOException;
import java.util.logging.*;

import static spark.Spark.get;

/**
 * Created by mrzl on 13.04.2015.
 */
public class Main {
    public static void main ( String[] args ) {
        HelperMethods.setGlobalLogLevel( Level.SEVERE );

        EventContainer container = new EventContainer( );

        EventSource networkingEventsLondon = new EventSource( "Networking Events London", "http://feeds2.feedburner.com/Networking-Events-In-London" );
        //EventSource jciLondon = new EventSource( "JCI London", "http://www.jcilondon.org.uk/events/index.html" );

        // adds network events london
        RSSFeedParser nelRssParser = new RSSFeedParser( networkingEventsLondon );
        nelRssParser.parse( );
        container.addEvents( nelRssParser.getEvents( ), networkingEventsLondon );

        // adds jci events
        JciEventsHtmlParser jciEventsHtmlParser = new JciEventsHtmlParser( );
        jciEventsHtmlParser.parse( );
        container.addEvents( jciEventsHtmlParser.getEvents( ), jciEventsHtmlParser.getEventSource( ) );

        // adds royal academy events
        RoyalAcademyHtmlParser royalAcademy = new RoyalAcademyHtmlParser();
        royalAcademy.parse();
        container.addEvents( royalAcademy.getEvents(), royalAcademy.getEventSource() );

        //HelperMethods.printEvents( container, networkingEventsLondon );
        //HelperMethods.printEvents( container, jciEventsHtmlParser.getEventSource());
        //HelperMethods.printEvents( container, royalAcademy.getEventSource() );

        htmlOutput( container );
    }

    public static void htmlOutput( EventContainer _container ) {

        get("/", ( request, response) -> "<input type=\"submit\" value=\"View events\" \n" +
                "    onclick=\"window.location='/events';\" />       " );
        get("/events", ( request, response) -> {

            HtmlAttributes attributes = new HtmlAttributes( "target", "_blank" );

            HtmlCanvas html = new HtmlCanvas();
            HtmlCanvas htmlCanvas = null;
            try {
                htmlCanvas = html.table( new HtmlAttributes( "border", "1" ) );
                        for( Event e : _container.getEvents() ) {
                            HtmlAttributes _attr = new HtmlAttributes( attributes );
                            _attr.add( "href", e.getUrl() );
                            htmlCanvas.tr( )
                                    .td( ).write( e.getTitle() ).close( )
                                    .td( new HtmlAttributes( "width", "100" )  ).write( e.getDescription() ).close( )
                                    .td( ).write( String.valueOf( e.getDate() ) ).close( )
                                    .td( ).write( e.getAuthor() ).close( )

                                    .td( ).a( _attr ).content( e.getUrl( ) ).close( )
                                    ._tr( );

                        }
                htmlCanvas._table( );
            } catch ( IOException e ) {
                e.printStackTrace( );
            }

            String output = "";
            for( Event e : _container.getEvents()) {
                //output += e.getTitle() + " - " + e.getDescription() + " - " + e.getDate() + " - " + e.getAuthor() + " - " + e.getUrl() + "</br>";
            }
            output += htmlCanvas.toHtml();
            return output;
        });
    }
}
