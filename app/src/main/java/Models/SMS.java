package Models;

/**
 * Created by thanh on 5/9/2016.
 */
public class SMS {
    private String phoneNuber;
    private String type;
    private String date;
    private String body;

    public SMS() {
    }

    public SMS(String phoneNuber, String type, String date, String body) {
        this.phoneNuber = phoneNuber;
        this.type = type;
        this.date = date;
        this.body = body;
    }

    public String getPhoneNuber() {
        return phoneNuber;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getBody() {
        return body;
    }

    public void setPhoneNuber(String phoneNuber) {
        this.phoneNuber = phoneNuber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

