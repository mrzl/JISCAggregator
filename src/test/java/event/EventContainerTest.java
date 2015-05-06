package event;

import jisc.event.Event;
import jisc.event.EventContainer;
import jisc.event.EventSource;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Created by mrzl on 05.05.2015.
 */
public class EventContainerTest {

    /**
     * Tests methods all of {@link jisc.event.EventContainer}
     *
     * @throws Exception ignored
     */
    @org.junit.Test
    public void addEventTest () throws Exception {
        EventContainer container = new EventContainer( );

        Event event = new Event( "title", "desc", "url", "date", "author" );
        EventSource eventSource = new EventSource( "TestEventSource", "eventurl" );

        // container should still be empty
        assertTrue( container.getEvents( ).size( ) == 0 );

        // adds an event
        container.addEvent( event, eventSource );

        // container should contain only one element
        assertTrue( container.getEvents( ).size( ) == 1 );
        assertTrue( container.getEvents( eventSource ).size( ) == 1 );

        // and the only event being contained is the one we've added before
        assertTrue( container.getEvents( eventSource ).get( 0 ).equals( event ) );

        // fail when asking for events from an eventsource that is unknown to the container
        EventSource unknownEventSource = new EventSource( "SecondTestEventSoure", "anothereventurl" );
        try {
            container.getEvents( unknownEventSource );
            assertTrue( false );
        } catch ( Exception e ) {
            assertTrue( true );
        }

        ArrayList< Event > events = new ArrayList<>( );
        events.add( new Event( "1", "2", "3", "4", "5" ) );
        events.add( new Event( "1", "2", "3", "4", "6" ) );
        events.add( event );

        // test clearing
        container.clear( );
        assertTrue( container.getEvents( ).size( ) == 0 );
        try {
            container.getEvents( unknownEventSource );
            assertTrue( false );
        } catch ( Exception e ) {
            assertTrue( true );
        }

        container.addEvents( events, unknownEventSource );

        // tests getEvents()
        assertTrue( container.getEvents( ).size( ) == events.size( ) );
        assertTrue( container.getEvents( unknownEventSource ).size( ) == events.size( ) );

        container.addEvent( new Event( "a", "b", "c", "d", "e" ), eventSource );

        // test getEventSources()
        assertTrue( container.getEventSources( ).size( ) == 2 );
    }
}
