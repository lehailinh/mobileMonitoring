package Models;


public class Contact {
    private String name;
    private String phoneNuber;

    public Contact(String name, String phoneNuber) {
        this.name = name;
        this.phoneNuber = phoneNuber;
    }

    public Contact() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSdt(String sdt) {
        this.phoneNuber = phoneNuber;
    }

    public String getSdt() {
        return phoneNuber;
    }

    public String getName() {
        return name;
    }

}