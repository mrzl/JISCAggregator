import jisc.event.EventContainer;
import jisc.input.html.JciEventsHtmlParser;
import jisc.input.html.RoyalAcademyHtmlParser;
import jisc.input.rss.NetworkingEventsLondonRssParser;
import jisc.output.html.HtmlOutput;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 */
public class NetworkTest {

    @Test
    public void testGeneralNetworkConnection () throws MalformedURLException {
        URL url = new URL( "http://www.google.com" );
        boolean isConnected = NetworkTest.isAddressReachable( url );
        assertTrue( "Checking for general internet connection", isConnected );
    }

    @Test
    public void testNetworkEventsLondonReachabilityTest () throws MalformedURLException {
        NetworkingEventsLondonRssParser nel = new NetworkingEventsLondonRssParser( );
        URL url = new URL( nel.getEventSource( ).getUrl( ) );
        assertTrue( NetworkTest.isAddressReachable( url ) );
    }

    @Test
    public void testJciReachabilityTest () throws MalformedURLException {
        JciEventsHtmlParser jci = new JciEventsHtmlParser( );
        URL url = new URL( jci.getEventSource( ).getUrl( ) );
        assertTrue( NetworkTest.isAddressReachable( url ) );
    }

    @Test
    public void testRoyalAcademyReachabilityTest () throws MalformedURLException {
        RoyalAcademyHtmlParser ra = new RoyalAcademyHtmlParser( );
        URL url = new URL( ra.getEventSource( ).getUrl( ) );
        assertTrue( NetworkTest.isAddressReachable( url ) );
    }

    @Test
    public void localhostWebserverTest () throws MalformedURLException {
        new HtmlOutput( new EventContainer( ) );
        assertTrue( NetworkTest.isWebserverReachable( new URL( "http://localhost:4567" ) ) );
    }

    @Test
    public void isHtmlValid() throws IOException {
        new HtmlOutput( new EventContainer() );

        boolean isHtmlValid = NetworkTest.isTablePresent( new URL( "http://localhost:4567/events" ) );



        assertTrue( isHtmlValid );
    }

    /**
     * Opens a connection to the passed url. If the connection is possible,
     * the host is online and you are connected to the internet.
     *
     * @param _url the URL to the website that is being checked
     * @return boolean weather the address is reachable or not
     */
    private static boolean isAddressReachable ( final URL _url ) {
        try {
            _url.openConnection( );
            return true;
        } catch ( IOException e ) {
            e.printStackTrace( );
        }

        return false;
    }

    /**
     * Opens the passed url and tests if the localhost web server responds positive
     *
     * @param _url the URL to the website that is being checked
     * @return boolean weather the address is reachable or not
     */
    private static boolean isWebserverReachable ( final URL _url ) {
        if ( !isAddressReachable( _url ) ) {
            return false;
        }

        try {
            HttpURLConnection connection = ( HttpURLConnection ) _url.openConnection( );
            connection.setRequestMethod( "HEAD" );
            int responseCode = connection.getResponseCode( );
            if ( responseCode == 200 ) {
                return true;
            }
        } catch ( IOException e ) {
            e.printStackTrace( );
        }


        return false;
    }

    /**
     * parses the html tree weather is contains an html table
     *
     * not parsing for anything else except from the actual
     * '<table>' element. Not even testing weather there is a closing
     * element to that able. Testing for this could be improved, but probably
     * not worth it, as the html table output is really only for the first stage
     * presentation of the prototype.
     *
     * @param _url the URL to the website that is being checked
     * @return boolean weather the table is present on the passed web server
     * @throws IOException
     */
    private static boolean isTablePresent( final URL _url ) throws IOException {
        if( !isWebserverReachable( _url ) ) {
            return false;
        }

        BufferedReader in = new BufferedReader( new InputStreamReader( _url.openStream() ) );

        ArrayList< String > line = new ArrayList<>();
        String inputLine;

        // no other way to only read one line
        while( ( inputLine = in.readLine() ) != null ) {
            line.add( inputLine );
        }

        return line.get( 0 ).contains( "<table>" );
    }
}
