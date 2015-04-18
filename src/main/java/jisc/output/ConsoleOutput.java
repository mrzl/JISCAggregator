package jisc.output;

import jisc.general.Event;
import jisc.general.EventContainer;
import jisc.general.EventSource;

import java.util.ArrayList;

/**
 * Created by mrzl on 18.04.2015.
 */
public class ConsoleOutput {
    public static void printEvents( EventContainer _container, EventSource _source ) {
        System.out.println( "Events of " + _source );
        try {
            ArrayList< Event > eventsNetworkingEventsLondon = ( ArrayList< Event > ) _container.getEvents( _source );
            for( Event e : eventsNetworkingEventsLondon ) {
                System.out.println( e );
            }
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }
}
