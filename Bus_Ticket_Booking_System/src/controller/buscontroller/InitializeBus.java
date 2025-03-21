package controller.buscontroller;

import controller.databasecontroller.DataBaseOperation;
import controller.databasecontroller.Queries;
import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InitializeBus {
    private static DataBaseOperation dbo = DataBaseOperation.getInstance();
    private static InitializeBus ib = null;
    private InitializeBus(){}

    public static InitializeBus getInstance(){
        if(ib == null){
            ib = new InitializeBus();
        }
        return ib;
    }

    public Bus initializeBus(int id,String date){
        Bus b =null;
        ResultSet rs = null;
        try{
            Trip t = null;
            rs = dbo.executeQuery(Queries.GET_TRIP.getQuery(),new Object[]{id});
            while(rs.next()){
                t = new Trip(id,rs.getInt("route_id"),rs.getTime("arrival_time"),rs.getTime("depature_time"));
            }
            rs = dbo.executeQuery(Queries.GET_BUS.getQuery(),new Object[]{id});
            while(rs.next()){
                b = new Bus(rs.getInt("id"),rs.getString("bus_no"),rs.getInt("total_seats"),t);
            }
            rs = dbo.executeQuery(Queries.GET_BUS_EMPLOYEES.getQuery(),new Object[]{id});
            if(rs.next()){
                Employee emp = new Employee(rs.getInt("id"),rs.getString("emp_name"),rs.getString("emp_mobile"));
                b.setDriver(emp);
            }
            if(rs.next()){
                Employee emp = new Employee(rs.getInt("id"),rs.getString("emp_name"),rs.getString("emp_mobile"));
                b.setHelper(emp);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        b.setSeats(initializeSeats(b,date));
        return b;
    }

    public List<Seat> initializeSeats(Bus b,String date){
        ResultSet rs = null;
        b.setSeats(new ArrayList<>());
        List<Seat> seats=b.getSeats();
        try{
           rs = dbo.executeQuery(Queries.GET_ALL_SEATS.getQuery(),new Object[]{b.getId(),date});
           int cnt=1;
           while(rs.next()){
               String seatno=rs.getString("seat_no");
               String gender = rs.getString("gender");
               if(seatno.contains("Sl")){
                   seats.add(new SleeperSeat(Integer.parseInt(seatno.substring(2)),gender));
               }
               if(seatno.contains("Ss")){
                   seats.add(new SemiSleeperSeat(Integer.parseInt(seatno.substring(2)),gender));
               }
           }
           return seats;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
