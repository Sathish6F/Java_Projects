package controller.passengercontroller;

import controller.buscontroller.Bus_info;
import controller.databasecontroller.DataBaseOperation;
import controller.databasecontroller.Queries;
import exception.DBUpdationFailedException;
import model.*;
import view.PassengerView;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class BookTicket {
    private static DataBaseOperation dbo = DataBaseOperation.getInstance();
    private static BookTicket bt = null;
    private BookTicket(){}

    public static BookTicket getInstance(){
        if(bt==null){
            bt = new BookTicket();
        }
        return bt;
    }

    public void bookTicket(String from,String to,String date){
        int fromId = getCityId(from.toLowerCase());
        int toId = getCityId(to.toLowerCase());
        if(fromId != -1 && toId != -1){
            int routeId=0;
            try {
                ResultSet rs = dbo.executeQuery(Queries.GET_ROUTE_ID.getQuery(), new Object[]{fromId, toId});
                if(rs.next()){
                    routeId = rs.getInt("id");
                }else{
                    System.out.println("No Service available");
                }
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
            if(routeId != 0){
                PassengerView.getInstance().listOfBuses(getBuses(routeId,date),date);
            }
        }
    }

    public int getCityId(String city){
        ResultSet rs = null;
        try{
            rs = dbo.executeQuery(Queries.GET_CITY_ID.getQuery(),new Object[]{city});
            if(rs.next()){
                return rs.getInt("id");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public ResultSet getBuses(int routeId,String date){
        return dbo.executeQuery(Queries.GET_BUSES.getQuery(),new Object[]{routeId,date});
    }

    public ResultSet getStoppings(int cityId){
        return dbo.executeQuery(Queries.GET_STOPPINGS.getQuery(),new Object[]{cityId});
    }

    public ResultSet getStop(int stopId){
        return dbo.executeQuery(Queries.GET_STOP.getQuery(),new Object[]{stopId});
    }

    public ResultSet getLastTicketId(){
        return dbo.executeQuery(Queries.GET_LAST_TICKET.getQuery(),new Object[]{});
    }

    public boolean updateDB(Bus bus, Ticket ticket, Passanger passanger,String date){
        //1.update availability of the bus
        //2.update seats
        //3.update payments
        //4.update bookings
        //5.update booked_tickets
        //6.update tickets
        dbo.startTransaction();
        try {
            if (updateAvailability(ticket.getPassengers(), bus.getId(),date)) {
                updateSeats(ticket.getPassengers(), bus,date);
                if(updateTickets(passanger.getId(),bus.getId(),ticket.getPrice(),ticket.getBookedfor())){
                    int paymentId = updatePayments(ticket,passanger.getId());
                    if(paymentId != 0){
                        int travelsId=Bus_info.getInstance().getTravelsId(bus);
                        if(updateBookings(passanger.getId(),ticket.getTicket_id(),travelsId,paymentId)){
                            if(updateBookedTickets(ticket.getTicket_id(),ticket.getPassengers())){
                                    return true;
                            }
                        }else {
                            throw new DBUpdationFailedException("Booking Passengers Operation Failed");
                        }
                    }else{
                        throw new DBUpdationFailedException("Can't Get the Payment ID");
                    }
                }else {
                    throw new DBUpdationFailedException("Ticket Allocation Failed");
                }
            } else {
                throw new DBUpdationFailedException("Seat Updation Failed");
            }
        }catch (SQLException|DBUpdationFailedException e){
            dbo.rollBack();
            System.out.println("Ticket Processing Failed!");
            System.out.println("Due to: "+e.getMessage());
        }finally {
            dbo.closeTransaction();
        }
        return false;
    }

    public boolean updateAvailability(List<Passanger> passangerList,int bus_id,String date){
        //id | bus_id | avl_sleeper | avl_semisleeper
        int sleeperSeats=0;
        int semiSeleeperSeats=0;
        for(Passanger p: passangerList){
            if(p.getSeatNumber().startsWith("Ss")){
                semiSeleeperSeats++;
            }else if(p.getSeatNumber().startsWith("Sl")){
                sleeperSeats++;
            }
        }
        int r = dbo.executeUpdate(Queries.UPDATE_AVAILABILITY.getQuery(),new Object[]{sleeperSeats,semiSeleeperSeats,bus_id,date});
        return r != 0;
    }

    public void updateSeats(List<Passanger> passangerList,Bus bus,String date){
        // id | seat_no | bus_id | status | price | gender
        int sleeperPrice = Bus_info.getInstance().getSeatPrice(bus,new SleeperSeat());
        int semiSleeperPrice = Bus_info.getInstance().getSeatPrice(bus,new SemiSleeperSeat());
        int bus_id = bus.getId();
        for(Passanger p:passangerList){
            int price = (p.getSeatNumber().startsWith("Sl"))?sleeperPrice:semiSleeperPrice;
            dbo.executeUpdate(Queries.UPDATE_SEATS.getQuery(),new Object[]{p.getSeatNumber(),bus_id,true,price,p.getGender(),date});
        }
    }

    public int updatePayments(Ticket ticket,int passengerId) throws SQLException {
        // id | passenger_id | ticket_id | price | date
        int paymentId = 0;
        Timestamp d = new Timestamp(System.currentTimeMillis());
        ResultSet rs = dbo.executeQuery(Queries.UPDATE_PAYMENT.getQuery(),new Object[]{passengerId,ticket.getTicket_id(),ticket.getPrice(),d.toString()});
        if(rs.next()){
            paymentId = rs.getInt("id");
        }
        return paymentId;
    }

    public boolean updateBookings(int passenger_id,int ticket_id,int travels_id,int payment_id){
        // id | passenger_id | ticket_id | travels_id | payment_id
        int r = 0;
        r = dbo.executeUpdate(Queries.UPDATE_BOOKINGS.getQuery(),new Object[]{passenger_id,ticket_id,travels_id,payment_id});
        return r != 0;
    }

    public boolean updateBookedTickets(int ticket_id,List<Passanger> passangerList) throws SQLException {
        // id | ticket_id | seat_no | passenger_name | gender | age
        int r = 0;
        for(Passanger passanger:passangerList){
            r = dbo.executeUpdate(Queries.UPDATE_BOOKED_TICKETS.getQuery(),new Object[]{ticket_id,passanger.getSeatNumber(),passanger.getName(),passanger.getGender(),passanger.getAge()});
            if(r == 0){
                throw new SQLException();
            }
        }
        return true;
    }

    public boolean updateTickets(int passenger_id, int busId, int price, Date bookedfor){
        // id | passenger_id | bus_id | price | status | date
        int r = 0;
        r = dbo.executeUpdate(Queries.UPDATE_TICKET.getQuery(),new Object[]{passenger_id,busId,price,bookedfor});
        return r!=0;
    }
}
