package Models;

/**
 * Created by thanh on 5/9/2016.
 */
public class Bookmark {
    private String title;
    private String url;
    private String date;

    public Bookmark() {
    }

    public Bookmark(String title, String url, String date) {
        this.title = title;
        this.url = url;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDate(String date) {
        this.date = date;
    }
}