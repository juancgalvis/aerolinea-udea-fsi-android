package es.hol.galvisoft.aerolina.model;

/**
 * Creado por Juan Carlos el dia 12/07/2015.
 */
public class User {
    private int id;
    private String email, authentication_token;

    public User() {
    }

    public User(String email, String authentication_token) {
        this.email = email;
        this.authentication_token = authentication_token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthentication_token() {
        return authentication_token;
    }

    public void setAuthentication_token(String authentication_token) {
        this.authentication_token = authentication_token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
