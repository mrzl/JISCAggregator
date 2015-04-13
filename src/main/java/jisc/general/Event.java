package jisc.general;

/**
 * Created by mrzl on 13.04.2015.
 */
public class Event {
    private String title;
    private String description;
    private String link;
    private String author;

    public Event( String _title, String _description, String _link, String _author ) {
        this.title = _title;
        this.description = _description;
        this.link = _link;
        this.author = _author;
    }

    public String getTitle () {
        return title;
    }

    public String getDescription () {
        return description;
    }

    public String getLink () {
        return link;
    }

    public String getAuthor () {
        return author;
    }

    @Override
    public String toString() {
        return "Event [title=" + getTitle() + " description=" + getDescription() + " author=" + getAuthor() + " link=" + getLink() + "]";
    }
}
