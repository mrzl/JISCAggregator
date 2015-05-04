package jisc.output.html;

import jisc.event.Event;
import jisc.event.EventContainer;
import jisc.misc.HelperMethods;
import org.rendersnake.HtmlAttributes;
import org.rendersnake.HtmlCanvas;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by mrzl on 04.05.2015.
 */
public class HtmlOutput {

    private static final Logger logger = Logger.getLogger( HtmlOutput.class.getName( ) );

    /**
     * Creates a webserver using the Spark framework and populates a table with all events
     *
     * @param _container the container that contains all events from all sources being listed in the table
     */
    public HtmlOutput( final EventContainer _container ) {
        spark.Spark.get( "/", ( request, response ) -> "<input type=\"submit\" value=\"View events\" \n" +
                "    onclick=\"window.location='/events';\" />       " );
        spark.Spark.get( "/events", ( request, response ) -> {

            HtmlAttributes attributes = new HtmlAttributes( "target", "_blank" );

            HtmlCanvas html = new HtmlCanvas( );
            html.head( ).macros( ).stylesheet( "http://bersk.iapetus.feralhosting.com/table_style.css" )._head( );
            HtmlCanvas htmlCanvas = null;
            try {
                htmlCanvas = html.div( org.rendersnake.HtmlAttributesFactory.class_( "datagrid" ) ).table( )
                        .thead( )
                        .tr( )
                        .th( ).write( "Title" )._th( )
                        .th( ).write( "Description" )._th( )
                        .th( ).write( "Date" )._th( )
                        .th( ).write( "Source" )._th( )
                        .th( ).write( "Event Link" )._th( )
                        ._tr( )
                        ._thead( )
                        .tbody( );

                int counter = 1;
                for ( Event e : _container.getEvents( ) ) {
                    counter++;
                    HtmlAttributes _attr = new HtmlAttributes( attributes );
                    _attr.add( "href", e.getUrl( ) );
                    if ( counter % 2 == 0 ) {
                        htmlCanvas.tr( org.rendersnake.HtmlAttributesFactory.class_( "alt" ) );
                    } else {
                        htmlCanvas.tr( );
                    }

                    htmlCanvas.td( ).write( e.getTitle( ) ).close( )
                            .td( new HtmlAttributes( "width", "100" ) ).write( e.getDescription( ) ).close( )
                            .td( ).write( String.valueOf( e.getDate( ) ) ).close( )
                            .td( ).write( e.getAuthor( ) ).close( )
                            .td( ).a( _attr ).content( e.getUrl( ) ).close( )
                            ._tr( );
                }
                htmlCanvas._tbody( )._table( )._div( );


            } catch ( IOException e ) {
                logger.severe( "Could not populate HTML table with events." );
                logger.severe( HelperMethods.getStackTraceFromException( e ) );
            }

            return htmlCanvas.toHtml( );
        } );
    }
}
