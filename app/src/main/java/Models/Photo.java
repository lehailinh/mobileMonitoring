package Models;

/**
 * Created by thanh on 5/9/2016.
 */
public class Photo {
    private String path;
    private String name;
    private String mime;
    private String date;

    public Photo() {
    }

    public Photo(String path, String name, String mime, String date) {
        this.path = path;
        this.name = name;
        this.mime = mime;
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getMime() {
        return mime;
    }

    public String getDate() {
        return date;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
