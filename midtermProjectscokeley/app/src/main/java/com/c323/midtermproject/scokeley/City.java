package com.c323.midtermproject.scokeley;

import android.widget.ImageView;

public class City {
    private ImageView cityPhoto;
    private String cityLocation, cityName, cityVisit, cityTourist;
    private double lat, lon;

    public City(ImageView cp, String cl, String cn, String cv, String ct, double latitude, double longitude){
        cityPhoto = cp;
        cityLocation = cl;
        cityName = cn;
        cityName = cv;
        cityTourist = ct;
        lat = latitude;
        lon = longitude;
    }

    public ImageView getCityPhoto() {
        return cityPhoto;
    }

    public void setCityPhoto(ImageView cityPhoto) {
        this.cityPhoto = cityPhoto;
    }

    public String getCityLocation() {
        return cityLocation;
    }

    public void setCityLocation(String cityLocation) {
        this.cityLocation = cityLocation;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityVisit() {
        return cityVisit;
    }

    public void setCityVisit(String cityVisit) {
        this.cityVisit = cityVisit;
    }

    public String getCityTourist() {
        return cityTourist;
    }

    public void setCityTourist(String cityTourist) {
        this.cityTourist = cityTourist;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
