package jisc.htmlparse;

import jisc.general.Event;
import jisc.general.EventSource;

import java.util.ArrayList;

/**
 * Created by mrzl on 18.04.2015.
 */
public class HtmlParser {
    protected EventSource eventSource;
    protected ArrayList< Event > events;

    public HtmlParser() {
        events = new ArrayList<>( );
    }

    public EventSource getEventSource() {
        return eventSource;
    }

    public ArrayList< Event > getEvents () {
        return events;
    }
}
