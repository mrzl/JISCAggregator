package jisc.input.rss;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrzl on 13.04.2015.
 *
 * DAO- no need for testing and logging
 */
public class RssFeed {

    private String title;
    private String link;
    private String description;
    private String language;
    private String copyright;
    private String pubDate;
    private List< RssFeedMessage > entries;

    public RssFeed ( String title, String link, String description, String language,
                     String copyright, String pubDate ) {

        this.entries = new ArrayList<>();
        this.title = title;
        this.link = link;
        this.description = description;
        this.language = language;
        this.copyright = copyright;
        this.pubDate = pubDate;
    }

    public void add( RssFeedMessage _message ) {
        this.entries.add( _message );
    }

    public List< RssFeedMessage > getMessages () {
        return entries;
    }

    public String getTitle () {
        return title;
    }

    public String getLink () {
        return link;
    }

    public String getDescription () {
        return description;
    }

    public String getLanguage () {
        return language;
    }

    public String getCopyright () {
        return copyright;
    }

    public String getPubDate () {
        return pubDate;
    }

    @Override
    public String toString () {
        return "Feed [copyright=" + getCopyright() + ", description=" + getDescription()
                + ", language=" + getLanguage() + ", link=" + getLink() + ", pubDate="
                + getPubDate() + ", title=" + getTitle() + "]";
    }

}
