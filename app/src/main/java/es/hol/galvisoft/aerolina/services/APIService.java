package es.hol.galvisoft.aerolina.services;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.hol.galvisoft.aerolina.model.Flight;
import es.hol.galvisoft.aerolina.model.helper.Flights;

/**
 * Creado por Juan Carlos el dia 27/05/2015.
 */
public class APIService {
    private final static String API_URL = "https://udea-mejialvarez.c9.io/api/v1/";
    private final static String FLIGHT_PATH = "flights";

    public static List<Flight> getFlights(String originAirport, String destinationAirport, String departureTime) {
        String url = API_URL + FLIGHT_PATH
                + "?departure_time=" + departureTime
                + "&origin_airport_id=" + originAirport
                + "&destination_airport_id=" + destinationAirport;

        BufferedReader br = HttpManager.getInstance().get(url);
        if (br != null) {
            StringBuilder json = new StringBuilder();
            String line;
            try {
                while (true) {
                    line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    json.append(line);
                }
                Gson gson = new Gson();
                Flights flights = gson.fromJson(json.toString(), Flights.class);
                if (flights != null && flights.getFlights().length > 0) {
                    return Arrays.asList(flights.getFlights());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }
}
