package jisc.rss;

import java.util.Date;

/**
 * Created by mrzl on 13.04.2015.
 *
 * An instance of this object contains all information that was retrieved about one RSS feed
 */
public class FeedMessage {

    String title;
    String description;
    String link;
    String author;
    String guid;
    Date date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Date getDate () {
        return date;
    }

    public void setDate( Date _date ) {
        this.date = _date;
    }

    @Override
    public String toString() {
        return "FeedMessage [title=" + title + ", description=" + description
                + ", link=" + link + ", author=" + author + ", date: " + date + ", guid=" + guid
                + "]";
    }
}