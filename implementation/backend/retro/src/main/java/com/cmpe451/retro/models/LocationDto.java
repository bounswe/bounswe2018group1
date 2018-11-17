package com.cmpe451.retro.models;


import com.cmpe451.retro.data.entities.Location;

public class LocationDto {

    private double longitude;

    private double latitude;

    private String locationName;

    public LocationDto() {
    }

    public LocationDto(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.locationName = location.getLocationName();
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
