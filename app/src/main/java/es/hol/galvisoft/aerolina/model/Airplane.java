package es.hol.galvisoft.aerolina.model;

/**
 * Creado por Juan Carlos el dia 27/05/2015.
 */
public class Airplane {
    private String model, make;

    public Airplane() {
    }

    public Airplane(String model, String make) {
        this.model = model;
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }
}
