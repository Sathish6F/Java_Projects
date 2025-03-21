package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Bus {

    public static Map<String,Point> seatMap = new HashMap<>();

    private int id;
    private String bus_no;
    private Employee driver;
    private Employee helper;
    private int total_seats;
    private String from;
    private String to;
    private Trip trip;
    private List<Seat> seats;

    private String[][] sleeperSeats = new String[3][6];
    private String[][] semiSleeperSeats = new String[5][6];

    public Bus(){
        //Sleeper seat position mapping
        seatMap.put("Sl01",new Point(0,0));
        seatMap.put("Sl02",new Point(1,0));
        seatMap.put("Sl03",new Point(2,0));

        seatMap.put("Sl04",new Point(0,1));
        seatMap.put("Sl05",new Point(1,1));
        seatMap.put("Sl06",new Point(2,1));

        seatMap.put("Sl07",new Point(0,2));
        seatMap.put("Sl08",new Point(1,2));
        seatMap.put("Sl09",new Point(2,2));

        seatMap.put("Sl10",new Point(0,3));
        seatMap.put("Sl11",new Point(1,3));
        seatMap.put("Sl12",new Point(2,3));

        seatMap.put("Sl13",new Point(0,4));
        seatMap.put("Sl14",new Point(1,4));
        seatMap.put("Sl15",new Point(2,4));

        seatMap.put("Sl16",new Point(0,5));
        seatMap.put("Sl17",new Point(1,5));
        seatMap.put("Sl18",new Point(2,5));
        //Semi sleeper seat position mapping
        seatMap.put("Ss01",new Point(0,0));
        seatMap.put("Ss02",new Point(1,0));
        seatMap.put("Ss03",new Point(2,0));
        seatMap.put("Ss04",new Point(3,0));
        seatMap.put("Ss05",new Point(4,0));

        seatMap.put("Ss06",new Point(0,1));
        seatMap.put("Ss07",new Point(1,1));
        seatMap.put("Ss08",new Point(2,1));
        seatMap.put("Ss09",new Point(3,1));
        seatMap.put("Ss10",new Point(4,1));;

        seatMap.put("Ss11",new Point(0,2));
        seatMap.put("Ss12",new Point(1,2));
        seatMap.put("Ss13",new Point(2,2));
        seatMap.put("Ss14",new Point(3,2));
        seatMap.put("Ss15",new Point(4,2));

        seatMap.put("Ss16",new Point(0,3));
        seatMap.put("Ss17",new Point(1,3));
        seatMap.put("Ss18",new Point(2,3));
        seatMap.put("Ss19",new Point(3,3));
        seatMap.put("Ss20",new Point(4,3));

        seatMap.put("Ss21",new Point(0,4));
        seatMap.put("Ss22",new Point(1,4));
        seatMap.put("Ss23",new Point(2,4));
        seatMap.put("Ss24",new Point(3,4));
        seatMap.put("Ss25",new Point(4,4));

        seatMap.put("Ss26",new Point(0,5));
        seatMap.put("Ss27",new Point(1,5));
        seatMap.put("Ss28",new Point(2,5));
        seatMap.put("Ss29",new Point(3,5));
        seatMap.put("Ss30",new Point(4,5));
    }
    public Bus(int id,String bus_no,int total_seats,Trip trip){
        this();
        this.id = id;
        this.total_seats = total_seats;
        this.bus_no = bus_no;
        this.trip = trip;
    }

    public Bus(int id,String bus_no,String from,String to,Trip trip){
        this.id = id;
        this.bus_no = bus_no;
        this.from = from;
        this.to = to;
        this.trip = trip;
    }

    public Bus(String bus_no,String from,String to,Employee driver,Employee helper,int total_seats){
        this.bus_no = bus_no;
        this.from = from;
        this.to = to;
        this.driver = driver;
        this.helper = helper;
        this.total_seats = total_seats;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBus_no() {
        return bus_no;
    }

    public void setBus_no(String bus_no) {
        this.bus_no = bus_no;
    }

    public Employee getDriver() {
        return driver;
    }

    public void setDriver(Employee driver) {
        this.driver = driver;
    }

    public Employee getHelper() {
        return helper;
    }

    public void setHelper(Employee helper) {
        this.helper = helper;
    }

    public int getTotal_seats() {
        return total_seats;
    }

    public void setTotal_seats(int total_seats) {
        this.total_seats = total_seats;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
    public String[][] getSleeperSeats() {
        return sleeperSeats;
    }

    public void setSleeperSeats(String[][] sleeperSeats) {
        this.sleeperSeats = sleeperSeats;
    }

    public String[][] getSemiSleeperSeats() {
        return semiSleeperSeats;
    }

    public void setSemiSleeperSeats(String[][] semiSleeperSeats) {
        this.semiSleeperSeats = semiSleeperSeats;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
