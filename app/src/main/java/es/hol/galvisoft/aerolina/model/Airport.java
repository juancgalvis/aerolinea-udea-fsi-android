package es.hol.galvisoft.aerolina.model;

/**
 * Creado por Juan Carlos el dia 27/05/2015.
 */
public class Airport {
    private String id, name, iata, city;

    public Airport() {
    }

    public Airport(String id, String name, String iata, String city) {
        this.id = id;
        this.name = name;
        this.iata = iata;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
