package com.thegeekylad.baynav.presentation.service;

import com.google.gson.JsonObject;
import com.thegeekylad.baynav.presentation.model.Departure;
import com.thegeekylad.baynav.presentation.model.Stop;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TransitService {

    @GET("nearby_stops")
    Call<List<Stop>> nearbyStops(
            @Query("lat") String lat,
            @Query("lon") String lon
    );

    @GET("stop_departures")
    Call<List<Departure>> stopDepartures(
            @Query("global_stop_id") String global_stop_id
    );
}
