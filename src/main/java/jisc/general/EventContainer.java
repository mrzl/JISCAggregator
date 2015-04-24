package jisc.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by mrzl on 13.04.2015.
 *
 * The @link{jisc.general.EventContainer} is the main container for all events. This is where all events are collected
 * in relation to their @link{jisc.general.EventOrigin}.
 */
public class EventContainer {
    private static final Logger logger = Logger.getLogger( EventContainer.class.getName() );
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
        this.logger.entering( getClass().getName(), "addEvent" );
        // if the event source doesn't exist in the map yet, create it.
        if( !this.events.containsKey( _origin ) ) {
            this.events.put( _origin, new ArrayList<>( ) );
            this.logger.info( "The event origin " + _origin + " did not exist yet, it was added to the event HashMap." );
        }

        this.events.get( _origin ).add( _event );
        this.logger.exiting( getClass().getName(), "addEvent" );
    }

    /**
     * Adds multiple events
     *
     * @param _events a @link{java.util.ArrayList} that contains the events being added
     * @param _origin the @link[jisc.general.EventSource} the events are in relation to
     */
    public void addEvents( ArrayList< Event > _events, EventSource _origin ) {
        this.logger.entering( getClass().getName(), "addEvents" );
        for( Event e : _events ) {
            this.addEvent( e, _origin );
        }
        this.logger.exiting( getClass().getName(), "addEvents" );
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
            String errorMessage = "There are no events in association to the passed EventSource" + _origin;
            this.logger.severe( errorMessage );
            throw new Exception( errorMessage );
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
