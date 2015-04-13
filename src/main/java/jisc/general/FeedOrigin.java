package jisc.general;

/**
 * Created by mrzl on 13.04.2015.
 */
public class FeedOrigin {
    private String prettyName;
    private String adress;

    public FeedOrigin( String _prettyName, String _adress ) {
        this.prettyName = _prettyName;
        this.adress = _adress;
    }

    public String getAdress () {
        return this.adress;
    }

    public String getPrettyName() {
        return this.prettyName;
    }

    public String toString() {
        return getPrettyName();
    }
}
