package model;

import java.sql.Time;

public class Trip {

    private int bus_id;
    private  int  route_id;
    private Time arrival;
    private Time departure;
    public Trip(Time arrival,Time departure){
        this.arrival = arrival;
        this.departure = departure;
    }
    public Trip(int bus_id, int route_id, Time arrival, Time departure) {
        this.bus_id = bus_id;
        this.route_id = route_id;
        this.arrival = arrival;
        this.departure = departure;
    }

    public Trip(int bus_id,Time arrival,Time departure){
        this.bus_id = bus_id;
        this.arrival = arrival;
        this.departure = departure;
    }
    public int getBus_id() {
        return bus_id;
    }

    public void setBus_id(int bus_id) {
        this.bus_id = bus_id;
    }

    public int getRoute_id() {
        return route_id;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    public Time getArrival() {
        return arrival;
    }

    public void setArrival(Time arrival) {
        this.arrival = arrival;
    }

    public Time getDeparture() {
        return departure;
    }

    public void setDeparture(Time departure) {
        this.departure = departure;
    }
}
