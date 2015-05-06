package jisc.misc;

import jisc.event.Event;
import jisc.event.EventContainer;
import jisc.event.EventSource;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.*;

/**
 * A collection of helper methods to perform small, global tasks.
 */
public class HelperMethods {

    /**
     * Creates a String from the stack trace of an exception.
     * Usually used to construct a string that's passed to the logger.
     *
     * @param _e the exception this method returns the stack trace of
     *
     * @return a String containing the complete stack trace of the passed exception
     */
    public static String getStackTraceFromException( Exception _e ) {
        StringWriter sw = new StringWriter( );
        _e.printStackTrace( new PrintWriter( sw ) );
        return sw.toString();
    }

    /**
     * Sets the overall log level of all loggers in the project.
     *
     * @param _logLevel the log level all loggers are set to
     */
    public static void setGlobalLogLevel( Level _logLevel ) {
        Logger anonymousLogger = LogManager.getLogManager( ).getLogger( "" );
        Handler[] handlers = anonymousLogger.getHandlers( );

        anonymousLogger.setLevel( _logLevel );
        for ( Handler h : handlers ) {
            if ( h instanceof FileHandler )
                h.setLevel( _logLevel );
        }
    }

    /**
     * Prints all events of a container of the passed @link{just.event.EventSource}
     *
     * @param _container the container that contains all events that are being printed
     * @param _source only events who are associated to this @link{just.event.EventSource} are printed
     */
    @SuppressWarnings( "unused" )
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
