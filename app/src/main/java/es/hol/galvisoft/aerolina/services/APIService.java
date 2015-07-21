package es.hol.galvisoft.aerolina.services;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.owlike.genson.Genson;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.hol.galvisoft.aerolina.model.Flight;
import es.hol.galvisoft.aerolina.model.Reservation;

/**
 * Creado por Juan Carlos el dia 27/05/2015.
 */
public class APIService {
    private final static String API_URL = "https://airportfsi.herokuapp.com/api/v1/";
    private final static String FLIGHT_PATH = "flights";
    private final static String RESERVATION_PATH = "reservations";
    private final static String QR_PATH = "https://chart.googleapis.com/chart?cht=qr&chs=170x170&chl=";

    private final static String USER = "?user_email=juankgalvis-9a@hotmail.com";
    private final static String TOKEN = "&user_token=2YtxKrpAFzQKSJUGyKyB";

    public static List getFlights(String originAirport, String destinationAirport, String departureTime) {
        List<Flight> flights = new ArrayList<>();
        String url = API_URL + FLIGHT_PATH + USER + TOKEN
                + "&departure_date=" + departureTime
                + "&arrival_date=" + departureTime
                + "&origin_airport_id=" + originAirport
                + "&destination_airport_id=" + destinationAirport;
        Log.e("url", url);

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
                Log.e("json", json.toString());
                Genson genson = new Genson();
                List<HashMap> hashMaps = genson.deserialize(json.toString(), List.class);
                for (HashMap hashMap : hashMaps) {
                    flights.add(genson.deserialize(genson.serialize(hashMap), Flight.class));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flights;
    }

    public static List getReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String url = API_URL + FLIGHT_PATH + USER + TOKEN;
        Log.e("url", url);

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
                Log.e("json", json.toString());
                Genson genson = new Genson();
                List<HashMap> hashMaps = genson.deserialize(json.toString(), List.class);
                for (HashMap hashMap : hashMaps) {
                    reservations.add(genson.deserialize(genson.serialize(hashMap), Reservation.class));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return reservations;
    }

    public static Drawable generateQR(String data) {
        return HttpManager.getInstance().downloadImage(QR_PATH + data);
    }
}
