package com.test.mosun.map;

public class LocationList {

    private String name;
    private double latitude;
    private double longitude;

    public LocationList(){
        name = "0";
        latitude = 0.0;
        longitude=0.0;
    }

    public LocationList(String name, double latitude, double longitude){
        this.name = name;
        this.latitude= latitude;
        this.longitude = longitude;
    }

    public String getName() { return name; }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }
}
