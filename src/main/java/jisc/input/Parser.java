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
    protected EventSource eventSource;
    protected ArrayList< Event > events;

    /**
     * All parsers have a list which contain their parsed events
     */
    public Parser () {
        this.events = new ArrayList<>( );
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
