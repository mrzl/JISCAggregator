package jisc.event;

import java.util.Date;

/**
 * Created by mrzl on 13.04.2015.
 *
 * This class encapsulates all information regarding an event.
 */
public class Event {
    private String title;
    private String description;
    private String url;
    private String author;
    private Date date;

    /**
     * Via the constructor all fields are initialized
     *
     * @param _title the title of the event
     * @param _description a small description of the event
     * @param _url the url to the event
     * @param _author the author of the event
     */
    public Event( String _title, String _description, String _url, Date _date, String _author ) {
        this.title = _title;
        this.description = _description;
        this.url = _url;
        this.date = _date;
        this.author = _author;
    }

    /**
     * @return The title of the event
     */
    public String getTitle () {
        return title;
    }

    /**
     * @return The description of the event
     */
    public String getDescription () {
        return description;
    }

    /**
     * @return The url of the event
     */
    public String getUrl () {
        return url;
    }

    /**
     * @return The date when the event will happen
     */
    public Date getDate () {
        return date;
    }

    /**
     * @return The author of the event
     */
    public String getAuthor () {
        return author;
    }

    @Override
    public String toString() {
        return "Event [\ntitle=" + getTitle( ) + " \ndescription=" + getDescription( ) + " \nauthor=" + getAuthor() + " \ndate=" + getDate() + " \nurl=" + getUrl( ) + "]";
    }
}
