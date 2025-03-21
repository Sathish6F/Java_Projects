package controller.passengercontroller;

import controller.databasecontroller.DataBaseOperation;
import controller.databasecontroller.Queries;
import exception.DBUpdationFailedException;
import model.Seat;
import model.SemiSleeperSeat;
import model.SleeperSeat;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CancelTicket {

    private static CancelTicket ct = null;
    private static DataBaseOperation dbo = DataBaseOperation.getInstance();
    private CancelTicket(){}

    public static CancelTicket getInstance(){
        if(ct == null){
            ct = new CancelTicket();
        }
        return ct;
    }

    public boolean isValidTicket(int ticketId){
        ResultSet rs = null;
        rs = dbo.executeQuery(Queries.CHECK_TICKET.getQuery(),new Object[]{ticketId});
        try{
            if(rs.next()){
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
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

    public List<Seat> getSeatsToCancel(int ticketId){
        List<Seat> seatstoCancel = new ArrayList<>();
        ResultSet rs = dbo.executeQuery(Queries.GET_SEATS_TO_CANCEL.getQuery(),new Object[]{ticketId});
        try {
            while (rs.next()) {
                String seat = rs.getString("seat_no");
                int num = Integer.parseInt(seat.substring(2));
                Seat s = (seat.startsWith("Ss")) ? new SemiSleeperSeat(num,rs.getString("gender")) : new SleeperSeat(num,rs.getString("gender"));
                seatstoCancel.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return seatstoCancel;
    }

    public boolean updateDB(int ticketId,int busId,List<Seat> seatList,int sleeper,int semisleeper,int passengerId){
        dbo.startTransaction();
        try{
            if(updateBookedTickets(ticketId)){
                if(updateSeats(busId,seatList,getBookedfor(ticketId))){
                    if(updateAvailability(busId,sleeper,semisleeper,getBookedfor(ticketId))){
                        if(updateCancel(passengerId,ticketId)){
                            if(updateTickets(ticketId)){
                                return true;
                            }else {
                                throw new DBUpdationFailedException("Ticket Status Updation Failed");
                            }
                        }else {
                            throw new DBUpdationFailedException("Cancel Details, Updation Failed");
                        }
                    }else{
                        throw new DBUpdationFailedException("Seat Updation Failed");
                    }
                }else {
                    throw new DBUpdationFailedException("Seat Deallocation Failed");
                }
            }else {
                throw new DBUpdationFailedException("Removing Passengers Failed");
            }
        } catch (DBUpdationFailedException e) {
            dbo.rollBack();
            System.out.println("Ticket Cancelation Failed");
            System.out.println("Due to: "+e.getMessage());
        } finally {
            dbo.closeTransaction();
        }
        return false;
    }

    public boolean updateBookedTickets(int ticketId){
        int r = 0;
        r = dbo.executeUpdate(Queries.REMOVE_FROM_BOOKED_TICKETS.getQuery(),new Object[]{ticketId});
        return r != 0;
    }

    public Date getBookedfor(int ticketId){
        ResultSet rs = dbo.executeQuery(Queries.GET_BOOKED_FOR.getQuery(), new Object[]{ticketId});
        try{
            if(rs.next()){
                return rs.getDate("booked_for");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Date(System.currentTimeMillis());
    }
    public boolean updateSeats(int busId,List<Seat> seatList,Date date){
        int r = 0;
        for(Seat s : seatList){
            String seat_no = s.getType()+((s.getNumber()<=9)?"0":"")+s.getNumber();
            r = dbo.executeUpdate(Queries.REMOVE_SEATS.getQuery(),new Object[]{seat_no,busId,date});
            if(r == 0){
                return false;
            }
        }
        return true;
    }

    public boolean updateAvailability(int busId,int sleeper,int semisleeper,Date date){
        int r = 0;
        r = dbo.executeUpdate(Queries.UPDATE_SEAT_COUNT.getQuery(),new Object[]{sleeper,semisleeper,busId,date});
        return r != 0;
    }

    public boolean updateCancel(int passengerId,int ticketId){
        int r = 0;
        Timestamp date = new Timestamp(System.currentTimeMillis());
        r = dbo.executeUpdate(Queries.UPDATE_CANCEL.getQuery(),new Object[]{passengerId,ticketId,date});
        return r != 0;
    }

    public boolean updateTickets(int ticketId){
        int r = 0;
        r = dbo.executeUpdate(Queries.UPDATE_TICKET_STATUS.getQuery(),new Object[]{ticketId});
        return r != 0;
    }

}

