package model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private int ticket_id;
    private List<Passanger> passengers = new ArrayList<>();
    private String boarding_point;
    private String dropping_point;
    private int price;
    private Timestamp date;
    private Date bookedfor;

    public Ticket(){}
    public Ticket(int ticket_id, String boarding_point, String dropping_point, int price,List<Passanger> passengers) {
        this.ticket_id = ticket_id;
        this.boarding_point = boarding_point;
        this.dropping_point = dropping_point;
        this.price = price;
        this.passengers = passengers;
    }


    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getBoarding_point() {
        return boarding_point;
    }

    public void setBoarding_point(String boarding_point) {
        this.boarding_point = boarding_point;
    }

    public String getDropping_point() {
        return dropping_point;
    }

    public void setDropping_point(String dropping_point) {
        this.dropping_point = dropping_point;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Passanger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passanger> passengers) {
        this.passengers = passengers;
    }


    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Date getBookedfor() {
        return bookedfor;
    }

    public void setBookedfor(Date bookedfor) {
        this.bookedfor = bookedfor;
    }
}
