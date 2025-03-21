package controller.travelscontroller;

import controller.databasecontroller.DataBaseOperation;
import controller.databasecontroller.Queries;
import exception.DBUpdationFailedException;
import model.Bus;
import model.Employee;
import model.Trip;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddBus {

    private static final DataBaseOperation dbo = DataBaseOperation.getInstance();
    private static AddBus ab = null;
    private AddBus(){}

    public static AddBus getInstance() {
        if(ab == null){
            ab = new AddBus();
        }
        return ab;
    }

    public boolean addBusToDB(int travelsId,Bus bus,int slPrice,int ssPrice,int distance){
        //1.Update Employee table
        //2.Update cities table
        //3.Update Route info
        //4.Update price info
        //5.Update Bus info
        dbo.startTransaction();
        try {
            if (updateEmployees(travelsId, bus.getDriver(), bus.getHelper())) {
                if (updateCities(bus.getFrom(), bus.getTo(), distance, bus.getTrip())) {
                    int id = updateBusInfo(bus.getBus_no(), bus.getDriver().getEmp_id(),bus.getHelper().getEmp_id(),travelsId,48,bus.getTrip().getRoute_id());
                    if(id==0){
                        return false;
                    }
                    bus.setId(id);
                        if (updatePriceInfo(id, slPrice, ssPrice)) {
                            updateTrip(id,bus.getTrip());
                            return true;
                        } else {
                            throw new DBUpdationFailedException("Price Details Updation Failed");
                        }
                } else {
                    throw new DBUpdationFailedException("City Updation Failed");
                }
            } else {
                throw new DBUpdationFailedException("Employee Updation Failed");
            }
        } catch (DBUpdationFailedException e) {
            dbo.rollBack();
            System.out.println(e.getMessage());
        }finally {
            dbo.closeTransaction();
        }
        return false;
    }

    public boolean updateEmployees(int id,Employee driver,Employee helper){
        // id | travels_id | emp_name | emp_mobile
        ResultSet rs = dbo.executeQuery(Queries.UPDATE_EMPLOYEE.getQuery(), new Object[]{id,driver.getEmp_name(),driver.getMobile()});
        try{
            if(rs.next()){
                driver.setEmp_id(rs.getInt("id"));
            }
            rs = dbo.executeQuery(Queries.UPDATE_EMPLOYEE.getQuery(), new Object[]{id,helper.getEmp_name(),helper.getMobile()});
            if(rs.next()){
                helper.setEmp_id(rs.getInt("id"));
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updateCities(String from, String to, int distance, Trip trip){
        // id | city_name
        int fromId = 0,toId = 0;
        ResultSet rs = dbo.executeQuery(Queries.UPDATE_CITIES.getQuery(), new Object[]{from,from,from});
        try{
            if(rs.next()){
                fromId = rs.getInt("id");
            }
            rs = dbo.executeQuery(Queries.UPDATE_CITIES.getQuery(), new Object[]{to,to,to});
            if(rs.next()){
                toId = rs.getInt("id");
            }

        } catch (Exception e) {
            return false;
        }
        return updateRoutes(fromId,toId,distance,trip);
    }
    public boolean updateRoutes(int fromId,int toId,int distance,Trip trip){
        // id | from_ | to_ | distance
        ResultSet rs = null;
        try{
            rs = dbo.executeQuery(Queries.UPDATE_ROUTE_INFO.getQuery(), new Object[]{fromId,toId,distance});
            if(rs.next()){
                trip.setRoute_id(rs.getInt("id"));
            }
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean updatePriceInfo(int busId,int sleeperPrice,int semiSleeperPrice){
       // id | bus_id | seat_type | price
        int r = 0;
        r = dbo.executeUpdate(Queries.UPDATE_PRICE_INFO.getQuery(),new Object[]{busId,1,sleeperPrice});
        if(r == 0) return false;
        r = dbo.executeUpdate(Queries.UPDATE_PRICE_INFO.getQuery(),new Object[]{busId,2,semiSleeperPrice});
        return r != 0;
    }

    public int updateBusInfo(String busNo,int driverId,int helperId,int travelsId,int totalSeats,int routeId){
        //id |  bus_no   | driver_id | helper_id | travels_id | total_seats | route_id
        ResultSet r = null;
        r = dbo.executeQuery(Queries.UPDATE_BUS_INFO.getQuery(),new Object[]{busNo,driverId,helperId,travelsId,totalSeats,routeId});
        try{
            if(r.next()){
                return r.getInt("id");
            }
        } catch (SQLException e) {
            return 0;
        }
        return 0;
    }

    public void updateTrip(int busId,Trip trip){
        //id | bus_id | depature_time | arrival_time | route_id
        dbo.executeUpdate(Queries.UPDATE_TRIPS.getQuery(),new Object[]{busId,trip.getArrival(),trip.getDeparture(),trip.getRoute_id()});
    }
}
