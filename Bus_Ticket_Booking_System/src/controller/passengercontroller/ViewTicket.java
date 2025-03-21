package controller.passengercontroller;

import controller.databasecontroller.DataBaseOperation;
import controller.databasecontroller.Queries;
import model.Bus;
import model.Passanger;
import model.Ticket;
import model.Trip;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ViewTicket {
    private static DataBaseOperation dbo = DataBaseOperation.getInstance();
    private static ViewTicket vt = null;
    private ViewTicket(){}

    public static ViewTicket getInstance(){
        if(vt == null){
            vt = new ViewTicket();
        }
        return vt;
    }
    public boolean isActiveTicket(int ticketId){
        ResultSet rs = dbo.executeQuery(Queries.CHECK_TICKET_STATUS.getQuery(),new Object[]{ticketId});
        try{
            if(rs.next()){
                return true;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    public Bus getBusInfo(int ticketId){
        ResultSet rs = null;
        Bus bus = null;
        int busId = getBusId(ticketId);
        rs = dbo.executeQuery(Queries.GET_BUS_INFO.getQuery(),new Object[]{busId});
        // id | bus_no | depature_time | arrival_time | departure_city | arrival_city
        try{
            if(rs.next()){
                Trip t = new Trip(busId,rs.getTime("arrival_time"),rs.getTime("depature_time"));
                bus = new Bus(busId,rs.getString("bus_no"),rs.getString("departure_city"),rs.getString("arrival_city"),t);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bus;
    }

    public int getBusId(int ticketId){
        int id =0;
        ResultSet rs = dbo.executeQuery(Queries.GET_BUS_ID.getQuery(),new Object[]{ticketId});
        try{
            if(rs.next()){
                id = rs.getInt("bus_id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public Ticket getTicketInfo(int ticketId){
        //Ticket(int ticket_id, String boarding_point, String dropping_point, int price)
        int price = 0;
        ResultSet rs = dbo.executeQuery(Queries.GET_TICKET_PRICE.getQuery(),new Object[]{ticketId});
        try{
            if(rs.next()){
                price = rs.getInt("price");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Ticket t = new Ticket(ticketId,"","",price,getPassengersList(ticketId));
        t.setDate(getDate(ticketId));
        return t;
    }

    public List<Passanger> getPassengersList(int ticketId){
        //Passanger(String name,int age,String gender,String seatNumber)
        List<Passanger> passangerList = new ArrayList<>();
        ResultSet rs = null;
        rs = dbo.executeQuery(Queries.GET_PASSENGES.getQuery(),new Object[]{ticketId});
        try {
            while (rs.next()){
                passangerList.add(new Passanger(rs.getString("passenger_name"),rs.getInt("age"),rs.getString("gender"),rs.getString("seat_no")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return passangerList;
    }

    public Timestamp getDate(int ticketId){
        Timestamp date = null;
        ResultSet rs = dbo.executeQuery(Queries.GET_BOOKING_DATE.getQuery(),new Object[]{ticketId});
        try{
            if(rs.next()){
                date = rs.getTimestamp("date");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

    public ResultSet getUpcomingTrips(int passengerId){
        return  dbo.executeQuery(Queries.GET_UPCOMMING_TRIPS.getQuery(), new Object[]{passengerId});
    }
}
