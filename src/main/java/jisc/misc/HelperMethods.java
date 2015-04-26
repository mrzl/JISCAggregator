package jisc.misc;

import jisc.event.Event;
import jisc.event.EventContainer;
import jisc.event.EventSource;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.*;

/**
 * Created by mrzl on 24.04.2015.
 */
public class HelperMethods {

    public static String getStackTraceFromException( Exception _e ) {
        StringWriter sw = new StringWriter( );
        _e.printStackTrace( new PrintWriter( sw ) );
        return sw.toString();
    }

    public static void setGlobalLogLevel( Level _logLevel ) {
        Logger anonymousLogger = LogManager.getLogManager( ).getLogger( "" );
        Handler[] handlers = anonymousLogger.getHandlers( );

        anonymousLogger.setLevel( _logLevel );
        for ( Handler h : handlers ) {
            if ( h instanceof FileHandler )
                h.setLevel( _logLevel );
        }
    }

    public static void printEvents( EventContainer _container, EventSource _source ) {
        System.out.println( "Events of " + _source );
        try {
            ArrayList< Event > eventsNetworkingEventsLondon = ( ArrayList< Event > ) _container.getEvents( _source );
            eventsNetworkingEventsLondon.forEach( System.out::println );
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }
}
