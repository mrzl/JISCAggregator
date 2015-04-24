package jisc.misc;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by mrzl on 24.04.2015.
 */
public class HelperMethods {

    public static String getStackTraceFromException( Exception _e ) {
        StringWriter sw = new StringWriter( );
        _e.printStackTrace( new PrintWriter( sw ) );
        return sw.toString();
    }
}
