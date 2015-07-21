package es.hol.galvisoft.aerolina.model;

import java.util.HashMap;

/**
 * Creado por Juan Carlos el dia 27/05/2015.
 */
public class Flight {
    public static final String ID = "id";
    public static final String DEPARTURE_DATE = "departure_date";
    public static final String ARRIVAL_DATE = "arrival_date";
    public static final String PRICE = "price";
    public static final String AIRPLANE_ID = "airplane_id";
    public static final String ORIGIN_AIRPORT_ID = "origin_airport_id";
    public static final String DESTINATION_AIRPORT_ID = "destination_airport_id";

    private long id;
    private String departure_date, arrival_date;
    private double price;
    private int airplane_id;
    private int origin_airport_id, destination_airport_id;

    public Flight() {
    }

    public Flight(long id, String departure_date, String arrival_date, double price, int airplane_id, int origin_airport_id, int destination_airport_id) {
        this.id = id;
        this.departure_date = departure_date;
        this.arrival_date = arrival_date;
        this.price = price;
        this.airplane_id = airplane_id;
        this.origin_airport_id = origin_airport_id;
        this.destination_airport_id = destination_airport_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(String departure_date) {
        this.departure_date = departure_date;
    }

    public String getArrival_date() {
        return arrival_date;
    }

    public void setArrival_date(String arrival_date) {
        this.arrival_date = arrival_date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAirplane_id() {
        return airplane_id;
    }

    public void setAirplane_id(int airplane_id) {
        this.airplane_id = airplane_id;
    }

    public int getOrigin_airport_id() {
        return origin_airport_id;
    }

    public void setOrigin_airport_id(int origin_airport_id) {
        this.origin_airport_id = origin_airport_id;
    }

    public int getDestination_airport_id() {
        return destination_airport_id;
    }

    public void setDestination_airport_id(int destination_airport_id) {
        this.destination_airport_id = destination_airport_id;
    }
}
