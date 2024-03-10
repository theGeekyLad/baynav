package com.thegeekylad.baynav.presentation.model;

public class LatLong {
    public String latitude;
    public String longitude;

    public LatLong(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        return (latitude + ":" + longitude).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        LatLong latLong = (LatLong) obj;
        return this.latitude.equals(latLong.latitude) && this.longitude.equals(latLong.longitude);
    }
}
