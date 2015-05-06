package jisc.event;

/**
 * An instance of this class represents a source for events. Most likely being an external website/service that has
 * a database of events happening. For every source of events only one instance of this class should be created.
 *
 * DAO- no need for testing and logging
 */
public class EventSource {
    private String prettyName;
    private String feedUrl;

    /**
     * The constructor initializes all fields
     *
     * @param _prettyName The name of the feed that's expressive and human readable
     * @param _url The url of the event source
     */
    public EventSource ( String _prettyName, String _url ) {
        this.prettyName = _prettyName;
        this.feedUrl = _url;
    }

    /**
     * @return Returns the url of the source
     */
    public String getUrl () {
        return this.feedUrl;
    }

    /**
     * @return Returns the human readable name of the source
     */
    public String getPrettyName() {
        return this.prettyName;
    }

    @Override
    public String toString() {
        return getPrettyName();
    }
}
