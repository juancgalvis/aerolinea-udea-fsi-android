package es.hol.galvisoft.aerolina.model;

/**
 * Creado por Juan Carlos el dia 12/07/2015.
 */
public class Reservation {
    private int id;
    private boolean check_in;
    private String reserve_date;
    private int flight_id;
    private int user_id;
    private double total_price;
    private int seat_id;
    private int class_type_id;

    public Reservation() {
    }

    public Reservation(int id, boolean check_in, String reserve_date, int flight_id, int user_id, double total_price, int seat_id, int class_type_id) {
        this.id = id;
        this.check_in = check_in;
        this.reserve_date = reserve_date;
        this.flight_id = flight_id;
        this.user_id = user_id;
        this.total_price = total_price;
        this.seat_id = seat_id;
        this.class_type_id = class_type_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCheck_in() {
        return check_in;
    }

    public void setCheck_in(boolean check_in) {
        this.check_in = check_in;
    }

    public String getReserve_date() {
        return reserve_date;
    }

    public void setReserve_date(String reserve_date) {
        this.reserve_date = reserve_date;
    }

    public int getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id = flight_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public int getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(int seat_id) {
        this.seat_id = seat_id;
    }

    public int getClass_type_id() {
        return class_type_id;
    }

    public void setClass_type_id(int class_type_id) {
        this.class_type_id = class_type_id;
    }
}
