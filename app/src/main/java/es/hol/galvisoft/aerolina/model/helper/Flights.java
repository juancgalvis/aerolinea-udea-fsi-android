package es.hol.galvisoft.aerolina.model.helper;

import es.hol.galvisoft.aerolina.model.Flight;

/**
 * Creado por Juan Carlos el dia 27/05/2015.
 */
public class Flights {
    private Flight[] flights;

    public Flights() {
    }

    public Flights(Flight[] flights) {
        this.flights = flights;
    }

    public Flight[] getFlights() {
        return flights;
    }

    public void setFlights(Flight[] flights) {
        this.flights = flights;
    }
}
