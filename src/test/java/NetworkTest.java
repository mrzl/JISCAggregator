import jisc.input.html.JciEventsHtmlParser;
import jisc.input.html.RoyalAcademyHtmlParser;
import jisc.input.rss.NetworkingEventsLondonRssParser;

import java.io.IOException;
import java.net.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by mrzl on 05.05.2015.
 */
public class NetworkTest {

    @org.junit.Test
    public void testGeneralNetworkConnection(){
        String url = new String( "http://www.google.com" );
        boolean isConnected = this.isAddressReachable( url );
        assertTrue( "Checking for general internet connection", isConnected );
    }

    @org.junit.Test
    public void testNetworkEventsLondonReachabilityTest(){
        NetworkingEventsLondonRssParser nel = new NetworkingEventsLondonRssParser();
        String url = new String( nel.getEventSource().getUrl() );
        assertTrue( this.isAddressReachable( url ) );
    }

    @org.junit.Test
    public void testJciReachabilityTest(){
        JciEventsHtmlParser jci = new JciEventsHtmlParser();
        String url = new String( jci.getEventSource().getUrl() );
        assertTrue( this.isAddressReachable( url ) );
    }

    @org.junit.Test
    public void testRoyalAcademyReachabilityTest(){
        RoyalAcademyHtmlParser ra = new RoyalAcademyHtmlParser();
        String url = new String( ra.getEventSource().getUrl() );
        assertTrue( this.isAddressReachable( url ) );
    }

    /**
     * Opens a connection to the passed url. If the connection is possible,
     * the host is online and you are connected to the internet.
     * @param _url
     * @return
     */
    private boolean isAddressReachable( final String _url ) {
        try {
            URL url = new URL( _url );
            try {
                url.openConnection();
                return true;
            } catch ( IOException e ) {
                e.printStackTrace( );
            }
        } catch ( MalformedURLException e ) {
            e.printStackTrace( );
        }

        return false;
    }
}
