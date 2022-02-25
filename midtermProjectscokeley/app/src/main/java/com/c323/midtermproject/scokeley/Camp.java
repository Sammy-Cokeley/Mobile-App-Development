package com.c323.midtermproject.scokeley;

import android.media.Image;
import android.widget.ImageView;

public class Camp {

    private ImageView campPhoto;
    private String campLocation, campName, campVisit, campNearestCity;
    private double lat, lon;

    public Camp(ImageView cp, String cl, String cn, String cv, String cnc, double latitude, double longitude){
        campPhoto = cp;
        campLocation = cl;
        campName = cn;
        campVisit = cv;
        campNearestCity = cnc;
        lat = latitude;
        lon = longitude;
    }

    public ImageView getCampPhoto() {
        return campPhoto;
    }

    public void setCampPhoto(ImageView campPhoto) {
        this.campPhoto = campPhoto;
    }

    public String getCampLocation() {
        return campLocation;
    }

    public void setCampLocation(String campLocation) {
        this.campLocation = campLocation;
    }

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

    public String getCampVisit() {
        return campVisit;
    }

    public void setCampVisit(String campVisit) {
        this.campVisit = campVisit;
    }

    public String getCampNearestCity() {
        return campNearestCity;
    }

    public void setCampNearestCity(String campNearestCity) {
        this.campNearestCity = campNearestCity;
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
