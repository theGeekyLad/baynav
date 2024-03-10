package com.thegeekylad.baynav.presentation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Departure {
    @JsonProperty("global_route_id")
    public String globalRouteId;
    @JsonProperty("route_short_name")
    public String routeShortName;
    @JsonProperty("direction_headsign")
    public String directionHeadsign;

    @JsonProperty("departure_interval")
    public String departureInterval;

    public Departure(String globalRouteId, String routeShortName, String directionHeadsign, String departureInterval) {
        this.globalRouteId = globalRouteId;
        this.routeShortName = routeShortName;
        this.directionHeadsign = directionHeadsign;
        this.departureInterval = departureInterval;
    }
}
