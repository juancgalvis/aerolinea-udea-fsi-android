package es.hol.galvisoft.aerolina.model;

/**
 * Creado por Juan Carlos el dia 27/05/2015.
 */
public class Flight {
    private long id;
    private String departure_time, arrival_time;
    private double price;
    private Airport origin_airport, destination_airport;

    public Flight() {
    }

    public Flight(long id, String departure_time, String arrival_time, double price, Airport origin_airport, Airport destination_airport) {
        this.id = id;
        this.departure_time = departure_time;
        this.arrival_time = arrival_time;
        this.price = price;
        this.origin_airport = origin_airport;
        this.destination_airport = destination_airport;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Airport getOrigin_airport() {
        return origin_airport;
    }

    public void setOrigin_airport(Airport origin_airport) {
        this.origin_airport = origin_airport;
    }

    public Airport getDestination_airport() {
        return destination_airport;
    }

    public void setDestination_airport(Airport destination_airport) {
        this.destination_airport = destination_airport;
    }
}
