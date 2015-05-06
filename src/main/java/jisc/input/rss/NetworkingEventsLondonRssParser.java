package jisc.input.rss;

import jisc.event.Event;
import jisc.event.EventSource;
import jisc.input.Parser;
import jisc.input.ParserInterface;
import jisc.misc.HelperMethods;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

/**
 *
 */
public class NetworkingEventsLondonRssParser extends Parser implements ParserInterface {

    private static final Logger logger = Logger.getLogger( NetworkingEventsLondonRssParser.class.getName( ) );

    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String CHANNEL = "channel";
    private static final String LANGUAGE = "language";
    private static final String COPYRIGHT = "copyright";
    private static final String LINK = "link";
    private static final String AUTHOR = "author";
    private static final String ITEM = "item";
    private static final String PUB_DATE = "pubDate";
    private static final String GUID = "guid";

    private URL url;
    private RssFeed currentRssFeed;


    /**
     * Extends the parses with a custom {@link jisc.event.EventSource}
     */
    public NetworkingEventsLondonRssParser () {
        super( new EventSource( "Networking Events London", "http://feeds2.feedburner.com/Networking-Events-In-London" ) );

        this.currentRssFeed = null;
        try {
            this.url = new URL( super.eventSource.getUrl() );
        } catch ( MalformedURLException e ) {
            logger.severe( "Couldn't construct a URL from this string: " + super.eventSource.getUrl() );
            logger.severe( HelperMethods.getStackTraceFromException( e ) );
        }
    }

    /**
     * Parses all events from that source.
     */
    public void parse() {
        try {
            boolean isFeedHeader = true;
            String description = "";
            String title = "";
            String link = "";
            Date date = new Date();
            String language = "";
            String copyright = "";
            String author = "";
            String pubdate = "";
            String guid = "";

            XMLEventReader eventReader = setupXmlReader( this.url );

            while ( eventReader.hasNext( ) ) {
                XMLEvent event = eventReader.nextEvent( );
                if ( event.isStartElement( ) ) {
                    String localPart = event.asStartElement( ).getName( )
                            .getLocalPart( );
                    switch ( localPart ) {
                        case ITEM:
                            if ( isFeedHeader ) {
                                isFeedHeader = false;

                                currentRssFeed = new RssFeed( title, link, description, language,
                                        copyright, pubdate );
                            }
                            event = eventReader.nextEvent( );
                            break;
                        case TITLE:
                            title = getCharacterData( eventReader );

                            Pattern pattern = Pattern.compile( "... \\d\\d ... \\d\\d\\d\\d" );
                            Matcher matcher = pattern.matcher( title );
                            if (matcher.find())
                            {
                                String extractedDateString = matcher.group( 0 );
                                try {
                                    date = new SimpleDateFormat( "EEE dd MMM yyyy", Locale.ENGLISH ).parse( extractedDateString );
                                } catch ( ParseException e ) {
                                    e.printStackTrace( );
                                }
                            }

                            break;
                        case DESCRIPTION:
                            description = getCharacterData( eventReader );
                            break;
                        case LINK:
                            link = getCharacterData( eventReader );
                            break;
                        case GUID:
                            guid = getCharacterData( eventReader );
                            break;
                        case LANGUAGE:
                            language = getCharacterData( eventReader );
                            break;
                        case AUTHOR:
                            author = getCharacterData( eventReader );
                            break;
                        case PUB_DATE:
                            pubdate = getCharacterData( eventReader );
                            break;
                        case COPYRIGHT:
                            copyright = getCharacterData( eventReader );
                            break;
                    }
                } else if ( event.isEndElement( ) ) {
                    if ( event.asEndElement( ).getName( ).getLocalPart( ) == ( ITEM ) ) {
                        RssFeedMessage message = new RssFeedMessage( );
                        message.setAuthor( author );
                        message.setDescription( description );
                        message.setGuid( guid );
                        message.setLink( link );
                        message.setTitle( title );
                        message.setDate( date );
                        currentRssFeed.add( message );
                        event = eventReader.nextEvent( );
                        continue;
                    }
                }
            }
        } catch ( XMLStreamException e ) {
            this.logger.severe( "Couldn't read XML from this URL: " + this.url );
            throw new RuntimeException( e );
        }

        for( RssFeedMessage m : this.currentRssFeed.getMessages( ) ) {
            String _date = parseDateFromTitle( m.getTitle() );

            String title = m.getTitle().substring( 0, getIndexOfDateStart( m.getTitle() ) - 2 );

            Event _e = new Event( title, m.getDescription(), m.getLink(), _date, this.eventSource.getPrettyName() );
            super.events.add( _e );
        }
    }

    /**
     * parses a date from the NEL rss feed
     *
     * @param _title
     * @return
     */
    private String parseDateFromTitle( String _title ) {
        return _title.substring( getIndexOfDateStart( _title ) );
    }

    /**
     * returns the index of the start of the date from the passed String
     * @param _title
     * @return
     */
    private int getIndexOfDateStart( String _title ) {
        ArrayList< String > dayOfWeek = new ArrayList<>();
        dayOfWeek.add( "Mon" );
        dayOfWeek.add( "Tue" );
        dayOfWeek.add( "Wed" );
        dayOfWeek.add( "Thu" );
        dayOfWeek.add( "Fri" );
        dayOfWeek.add( "Sat" );
        dayOfWeek.add( "Sun" );
        int _dateIndex = _title.length();
        for( String day : dayOfWeek ) {
            int index = _title.indexOf( day );
            // if it exists
            if( index != -1 ) {
                _dateIndex = index;
            }
        }

        return _dateIndex;
    }

    /**
     * Sets op the XML helper instances
     * @param _url the url opened
     * @return
     */
    private XMLEventReader setupXmlReader ( URL _url ) {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance( );
        InputStream in = readFromUrl( _url );
        try {
            return inputFactory.createXMLEventReader( in );
        } catch ( XMLStreamException e ) {
            this.logger.severe( "Couldn't setup XML reader from this URL: " + _url );
            this.logger.severe( HelperMethods.getStackTraceFromException( e ) );
        }

        return null;
    }

    /**
     * Opens a URL
     * @param _url URL to be opened
     * @return the input stream from the URL
     */
    private InputStream readFromUrl ( URL _url ) {
        try {
            return _url.openStream( );
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }
    }

    private String getCharacterData ( XMLEventReader eventReader )
            throws XMLStreamException {
        String result = "";
        XMLEvent event = eventReader.nextEvent( );
        if ( event instanceof Characters ) {
            result = event.asCharacters( ).getData( );
        }
        return result;
    }
}
