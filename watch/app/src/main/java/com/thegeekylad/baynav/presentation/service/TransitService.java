package com.thegeekylad.baynav.service;

import com.google.gson.JsonObject;
import com.thegeekylad.baynav.util.Constants;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TransitService {

    @Headers("apiKey: " + Constants.TRANSIT_API_KEY)
    @GET("nearby_stops")
    Call<JsonObject> nearbyStops(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("max_distance") String distance
    );

    @Headers("apiKey: " + Constants.TRANSIT_API_KEY)
    @GET("stop_departures")
    Call<JsonObject> stopDepartures(
            @Query("global_stop_id") String global_stop_id
    );
}
