package jisc.output;

import jisc.event.Event;
import jisc.event.EventContainer;
import jisc.event.EventSource;

import java.util.ArrayList;

/**
 * Created by mrzl on 18.04.2015.
 */
public class ConsoleOutput {
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
