package jisc.input;

import jisc.event.Event;
import jisc.event.EventSource;

import java.util.ArrayList;

/**
 * The base parser class for all parsers.
 *
 * Created by mrzl on 18.04.2015.
 */
public class Parser {
    protected ArrayList< Event > events;
    protected EventSource eventSource;

    /**
     * All parsers have a list which contain their parsed events
     *
     * @param _eventSource the source of the events
     */
    public Parser ( EventSource _eventSource ) {
        this.events = new ArrayList<>( );
        this.eventSource = _eventSource;
    }

    /**
     * @return the EventSource of this parser, it represents a website/feed that contains events
     */
    public EventSource getEventSource() {
        return this.eventSource;
    }

    /**
     * @return Returns all events parsed by this parser
     */
    public ArrayList< Event > getEvents () {
        return this.events;
    }
}
