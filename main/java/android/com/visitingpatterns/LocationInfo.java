package android.com.visitingpatterns;

/**
 * Created by srinu on 2/19/2016.
 */
public class LocationInfo {

    double latitude,longitude,altitude,accuracy;
    long time;
    String address;
    public LocationInfo(double latitude, double longitude, double altitude, long time, String locationAddress)
    {

    }

    public  LocationInfo()
    {

    }
    public LocationInfo(String address)
    {
        this.address=address;
    }

    public LocationInfo(long time)
    {
        this.time=time;
    }
    public LocationInfo(double latitude,double longitude,double altitude,double accuracy,long time,String address)
    {
        this.latitude=latitude;
        this.longitude=longitude;
        this.altitude=altitude;
        this.accuracy=accuracy;
        this.time=time;
        this.address=address;

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
