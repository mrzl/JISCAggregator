package jisc.rss;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

/**
 * Created by mrzl on 13.04.2015.
 */
public class RSSFeedParser {
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String CHANNEL = "channel";
    static final String LANGUAGE = "language";
    static final String COPYRIGHT = "copyright";
    static final String LINK = "link";
    static final String AUTHOR = "author";
    static final String ITEM = "item";
    static final String PUB_DATE = "pubDate";
    static final String GUID = "guid";

    final URL url;

    public RSSFeedParser ( String feedUrl ) {
        try {
            this.url = new URL( feedUrl );
        } catch ( MalformedURLException e ) {
            throw new RuntimeException( e );
        }
    }

    public Feed readFeed () {
        Feed feed = null;
        try {
            boolean isFeedHeader = true;
            // Set header values intial to the empty string
            String description = "";
            String title = "";
            String link = "";
            Date date = null;
            String language = "";
            String copyright = "";
            String author = "";
            String pubdate = "";
            String guid = "";

            // First create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance( );
            // Setup a new eventReader
            InputStream in = read( );
            XMLEventReader eventReader = inputFactory.createXMLEventReader( in );
            // read the XML document
            while ( eventReader.hasNext( ) ) {
                XMLEvent event = eventReader.nextEvent( );
                if ( event.isStartElement( ) ) {
                    String localPart = event.asStartElement( ).getName( )
                            .getLocalPart( );
                    switch ( localPart ) {
                        case ITEM:
                            if ( isFeedHeader ) {
                                isFeedHeader = false;

                                feed = new Feed( title, link, description, language,
                                        copyright, pubdate );
                            }
                            event = eventReader.nextEvent( );
                            break;
                        case TITLE:
                            title = getCharacterData( event, eventReader );

                            Pattern pattern = Pattern.compile( "... \\d\\d ... \\d\\d\\d\\d" );
                            Matcher matcher = pattern.matcher( title );
                            if (matcher.find())
                            {
                                String extractedDateString = matcher.group( 0 );
                                try {
                                    //System.out.println( title );
                                    Date result = new SimpleDateFormat( "EEE dd MMM yyyy", Locale.ENGLISH ).parse( extractedDateString );
                                    //System.out.println( "date parse: " + result );
                                    date = result;
                                } catch ( ParseException e ) {
                                    e.printStackTrace( );
                                }
                            }

                            break;
                        case DESCRIPTION:
                            description = getCharacterData( event, eventReader );
                            break;
                        case LINK:
                            link = getCharacterData( event, eventReader );
                            break;
                        case GUID:
                            guid = getCharacterData( event, eventReader );
                            break;
                        case LANGUAGE:
                            language = getCharacterData( event, eventReader );
                            break;
                        case AUTHOR:
                            author = getCharacterData( event, eventReader );
                            break;
                        case PUB_DATE:
                            pubdate = getCharacterData( event, eventReader );
                            break;
                        case COPYRIGHT:
                            copyright = getCharacterData( event, eventReader );
                            break;
                    }
                } else if ( event.isEndElement( ) ) {
                    if ( event.asEndElement( ).getName( ).getLocalPart( ) == ( ITEM ) ) {
                        FeedMessage message = new FeedMessage( );
                        message.setAuthor( author );
                        message.setDescription( description );
                        message.setGuid( guid );
                        message.setLink( link );
                        message.setTitle( title );
                        message.setDate( date );
                        feed.getMessages( ).add( message );
                        event = eventReader.nextEvent( );
                        continue;
                    }
                }
            }
        } catch ( XMLStreamException e ) {
            throw new RuntimeException( e );
        }
        return feed;
    }

    private String getCharacterData ( XMLEvent event, XMLEventReader eventReader )
            throws XMLStreamException {
        String result = "";
        event = eventReader.nextEvent( );
        if ( event instanceof Characters ) {
            result = event.asCharacters( ).getData( );
        }
        return result;
    }

    private InputStream read () {
        try {
            return url.openStream( );
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }
    }
}
