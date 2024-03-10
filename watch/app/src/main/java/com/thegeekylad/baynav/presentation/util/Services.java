
package com.thegeekylad.baynav.presentation.util;

import com.google.gson.JsonObject;
import com.thegeekylad.baynav.service.TransitService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class Services {
    public static Logger logger = new Logger("Services");
    private static Retrofit retrofit;

    // services
    private static TransitService transitService;

    public static TransitService getTransitService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://external.transitapp.com/v3/public/")
                    .build();

            transitService = retrofit.create(TransitService.class);
        }

        return transitService;
    }

    public static class Helper {
        public static JsonObject getNearbyStops(String lat, String lon) {
            try {
                return getTransitService().nearbyStops(lat, lon, Constants.MAX_DISTANCE_STOPS).execute().body();
            } catch (IOException e) {
                logger.printHeader(String.format("Error: \"%s\"", e.getMessage()), 0);
                e.printStackTrace();
                logger.println("", 0);

                return null;
            }
        }

        public static JsonObject getStopDepartures(String globalStopId) {
            try {
                return getTransitService().stopDepartures(globalStopId).execute().body();
            } catch (IOException e) {
                logger.printHeader(String.format("Error: \"%s\"", e.getMessage()), 0);
                e.printStackTrace();
                logger.println("", 0);

                return null;
            }
        }
    }
}
