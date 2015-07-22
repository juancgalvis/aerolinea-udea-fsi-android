package es.hol.galvisoft.aerolina.model;

/**
 * Creado por Juan Carlos el dia 12/07/2015.
 */
public class ClassType {
    private int id;
    private String name;
    private float percentage;

    public ClassType() {
    }

    public ClassType(int id, String name, float percentage) {
        this.id = id;
        this.name = name;
        this.percentage = percentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
}
