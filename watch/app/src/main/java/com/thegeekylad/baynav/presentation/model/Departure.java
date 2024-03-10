package com.thegeekylad.baynav.presentation.model;


import com.google.gson.annotations.SerializedName;

public class Departure {
    @SerializedName("global_route_id")
    public String globalRouteId;
    @SerializedName("route_short_name")
    public String routeShortName;
    @SerializedName("direction_headsign")
    public String directionHeadsign;

    @SerializedName("departure_interval")
    public String departureInterval;

    public Departure(String globalRouteId, String routeShortName, String directionHeadsign, String departureInterval) {
        this.globalRouteId = globalRouteId;
        this.routeShortName = routeShortName;
        this.directionHeadsign = directionHeadsign;
        this.departureInterval = departureInterval;
    }
}
