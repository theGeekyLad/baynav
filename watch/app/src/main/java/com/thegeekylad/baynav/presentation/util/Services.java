
package com.thegeekylad.baynav.presentation.util;

import com.google.gson.JsonObject;
import com.thegeekylad.baynav.presentation.model.Departure;
import com.thegeekylad.baynav.presentation.model.Stop;
import com.thegeekylad.baynav.presentation.service.TransitService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class Services {
    public static Logger logger = new Logger("Services");
    private static Retrofit retrofit;

    // services
    private static TransitService transitService;

    public static TransitService getTransitService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(String.format(
                            "%s://%s:%s/",
                            Constants.BE_PROTO,
                            Constants.BE_IP,
                            Constants.BE_PORT
                    ))
                    .build();

            transitService = retrofit.create(TransitService.class);
        }

        return transitService;
    }

    public static class Helper {
        public static List<Stop> getNearbyStops(String lat, String lon) {
            try {
                return getTransitService().nearbyStops(lat, lon).execute().body();
            } catch (IOException e) {
                logger.printHeader(String.format("Error: \"%s\"", e.getMessage()), 0);
                e.printStackTrace();
                logger.println("", 0);

                return null;
            }
        }

        public static List<Departure> getStopDepartures(String globalStopId) {
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
