package jisc.general;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mrzl on 13.04.2015.
 */
public class EventContainer {
    private HashMap< FeedOrigin, List< Event > > events;

    public EventContainer() {
        this.events = new HashMap<>();
    }

    public void addEvent( Event _event, FeedOrigin _origin ) throws Exception {
        if( !this.events.containsKey( _origin ) ) {
            throw new Exception( "Couldn't add event to " + this.getClass() + " - " + _origin + " doesn't exist, yet." );
        } else {
            this.events.get( _origin ).add( _event );
        }
    }

    public List<Event> getEvents( FeedOrigin _origin ) throws Exception {
        if( !this.events.containsKey( _origin ) ) {
            throw new Exception( "Couldn't get events of " + _origin );
        } else {
            return this.events.get( _origin );
        }
    }
}
