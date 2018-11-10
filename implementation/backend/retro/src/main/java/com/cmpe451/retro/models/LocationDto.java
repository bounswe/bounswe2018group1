package com.cmpe451.retro.models;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;


/**
 * Since we are not changing any fields, why do we use DTO?
 */
public class LocationDto {

    @Id
    @NotNull
    private long id;

    private double longitude;

    private double latitude;


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
}
