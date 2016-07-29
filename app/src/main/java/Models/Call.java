package Models;



public class Call {
    private String phoneNumber;
    private String type;
    private String date;
    private String duration;

    public Call() {
    }

    public Call(String phoneNumber, String type, String date, String duration) {
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.date = date;
        this.duration = duration;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getDuration() {
        return duration;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
