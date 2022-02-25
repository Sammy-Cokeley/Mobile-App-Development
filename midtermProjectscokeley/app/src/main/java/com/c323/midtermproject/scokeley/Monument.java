package com.c323.midtermproject.scokeley;

import android.widget.ImageView;

public class Monument {

    private ImageView monumentPhoto;
    private String monumentLocation, monumentName, monumentVisit, monumentPrice, monumentHistory;
    private double lat, lon;

    public Monument(ImageView mp, String ml, String mn, String mh, String mv, String mpr, double latitude, double longitude){
        monumentPhoto = mp;
        monumentLocation = ml;
        monumentName = mn;
        monumentVisit = mv;
        monumentPrice = mpr;
        monumentHistory = mh;
        lat = latitude;
        lon = longitude;
    }

    public ImageView getMonumentPhoto() {
        return monumentPhoto;
    }

    public void setMonumentPhoto(ImageView monumentPhoto) {
        this.monumentPhoto = monumentPhoto;
    }

    public String getMonumentLocation() {
        return monumentLocation;
    }

    public void setMonumentLocation(String monumentLocation) {
        this.monumentLocation = monumentLocation;
    }

    public String getMonumentName() {
        return monumentName;
    }

    public void setMonumentName(String monumentName) {
        this.monumentName = monumentName;
    }

    public String getMonumentVisit() {
        return monumentVisit;
    }

    public void setMonumentVisit(String monumentVisit) {
        this.monumentVisit = monumentVisit;
    }

    public String getMonumentPrice() {
        return monumentPrice;
    }

    public void setMonumentPrice(String monumentPrice) {
        this.monumentPrice = monumentPrice;
    }

    public String getMonumentHistory() {
        return monumentHistory;
    }

    public void setMonumentHistory(String monumentHistory) {
        this.monumentHistory = monumentHistory;
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
