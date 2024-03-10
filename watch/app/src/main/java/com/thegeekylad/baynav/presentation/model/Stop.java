package com.thegeekylad.baynav.presentation.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class Stop {
    @SerializedName("stop_name")
    public String stopName;
    @SerializedName("global_stop_id")
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
