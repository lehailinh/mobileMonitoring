package Models;

/**
 * Created by thanh on 5/9/2016.
 */
public class GPS {
    private Double latitude;
    private Double longtitude;

    public GPS() {
    }

    public GPS(Double latitude, Double longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
