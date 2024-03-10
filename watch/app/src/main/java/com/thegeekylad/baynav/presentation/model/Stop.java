package com.thegeekylad.baynav.presentation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;

public class Stop {
    @JsonProperty("stop_name")
    public String stopName;
    @JsonProperty("global_stop_id")
    public String globalStopId;
    public String distance;

    public Stop(String stopName, String globalStopId, String distance) {
        this.stopName = stopName;
        this.globalStopId = globalStopId;
        this.distance = distance;
    }

    public static Stop fromJson(JsonObject jsonObject) {
        return new Stop(
                jsonObject.get("stop_name").getAsString(),
                jsonObject.get("global_stop_id").getAsString(),
                jsonObject.get("distance").getAsString()
        );
    }
}
