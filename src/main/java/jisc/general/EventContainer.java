package jisc.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mrzl on 13.04.2015.
 *
 * The @link{jisc.general.EventContainer} is the main container for all events. This is where all events are collected
 * in relation to their @link{jisc.general.EventOrigin}.
 */
public class EventContainer {
    private HashMap< EventSource, List< Event > > events;

    /**
     * The constructor.
     */
    public EventContainer() {
        this.events = new HashMap<>();
    }

    /**
     * Adds an event in relation to a source. The source of where once event is taken from should always be stored.
     *
     * @param _event The event object containing all information about an event
     * @param _origin The source of the event
     */
    public void addEvent( Event _event, EventSource _origin ){
        // if the event source doesn't exist in the map yet, create it.
        if( !this.events.containsKey( _origin ) ) {
            this.events.put( _origin, new ArrayList<>( ) );
        }

        this.events.get( _origin ).add( _event );
    }

    public void addEvents( ArrayList< Event > _events, EventSource _origin ) {
        for( Event e : _events ) {
            addEvent( e, _origin );
        }
    }

    /**
     * Returns all Events associated with a given @link{jisc.general.EventSource} instance.
     *
     * @param _origin The @link{jisc.general.EventSource} this function returns all associated events of
     * @return Returns a list of all Events associated with the passed @link{jisc.general.EventSource}
     * @throws Exception Throws an Exception if the passed @link{jisc.general.EventSource} doesn't exist in this container
     */
    public List<Event> getEvents( EventSource _origin ) throws Exception {
        // whenever the given event source doesn't exist, throw an error
        if( !this.events.containsKey( _origin ) ) {
            throw new Exception( "Couldn't get events of " + _origin );
        } else {
            return this.events.get( _origin );
        }
    }

    /**
     * @return Returns a List of all origins known to the container
     */
    public List< EventSource > getOrigins() {
        return events.keySet( ).stream( ).collect( Collectors.toList( ) );
    }
}
