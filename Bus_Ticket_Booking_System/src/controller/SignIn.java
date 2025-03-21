package controller;

import controller.databasecontroller.DataBaseOperation;
import controller.databasecontroller.Queries;
import model.Admin;
import model.Passanger;
import model.Travels;
import view.AdminView;
import view.PassengerView;
import view.TravelsView;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SignIn {
    private static DataBaseOperation dbo = DataBaseOperation.getInstance();
    private static SignIn in = null;
    private SignIn(){}

    public static SignIn getInstance(){
        if(in == null){
            in = new SignIn();
        }
        return in;
    }
    public static void createTables(){
        dbo.executeUpdate(Queries.CREATE_TRAVELS_INFO.getQuery());
        dbo.executeUpdate(Queries.CREATE_CITIES.getQuery());
        dbo.executeUpdate(Queries.CREATE_ROUTES.getQuery());
        dbo.executeUpdate(Queries.CREATE_ROLES.getQuery());
        dbo.executeUpdate(Queries.CREATE_EMPLOYEE_INFO.getQuery());
        dbo.executeUpdate(Queries.CREATE_BUS_INFO.getQuery());
        dbo.executeUpdate(Queries.CREATE_PASSENGER_INFO.getQuery());
        dbo.executeUpdate(Queries.CREATE_AVAILABILITY.getQuery());
        dbo.executeUpdate(Queries.CREATE_SEATS.getQuery());
        dbo.executeUpdate(Queries.CREATE_PRICE_INFO.getQuery());
        dbo.executeUpdate(Queries.CREATE_USER_CRED.getQuery());
        dbo.executeUpdate(Queries.CREATE_TRIP.getQuery());
        dbo.executeUpdate(Queries.CREATE_STOPPINGS.getQuery());
        dbo.executeUpdate(Queries.CREATE_REVIEWS.getQuery());
        dbo.executeUpdate(Queries.CREATE_TICKET.getQuery());
        dbo.executeUpdate(Queries.CREATE_BOOKED_LIST.getQuery());
        dbo.executeUpdate(Queries.CREATE_PAYMENTS.getQuery());
        dbo.executeUpdate(Queries.CREATE_BOOKINGS.getQuery());
        dbo.executeUpdate(Queries.CREATE_CANCELS.getQuery());
        dbo.executeUpdate(Queries.AVAILABILITY_FUNCTION.getQuery());
        //dbo.executeUpdate(Queries.TRIGGER.getQuery());
    }

    public boolean signIn(String mail,String password){
        if(!userLogin(mail,password)){
            System.out.println("No User Found");
            return false;
        }
        try{
            int id =0;
            int role=0;
            ResultSet rs = dbo.executeQuery(Queries.CHECK_USER.getQuery(),new Object[]{mail,password});

            if(rs.next()){
                id = rs.getInt("user_id");
                role = rs.getInt("user_role");
            }
            if(role==2 && id!=0){
                rs = dbo.executeQuery(Queries.GET_PASSENGER.getQuery(),new Object[]{id});
                if(rs.next()){
                    //Passanger(int id,String name,String address,String mobileNumber,String mail,int age,String gender)
                    Passanger p = new Passanger(rs.getInt("passenger_id"),rs.getString("name"),rs.getString("address"),rs.getString("mobile_number"),rs.getString("mail"),rs.getInt("age"),rs.getString("gender"));
                    PassengerView.getInstance().functionalityList(p);
                }

            }
            else if(role==3 && id !=0){
                rs = dbo.executeQuery(Queries.GET_TRAVELS.getQuery(),new Object[]{id});
                if(rs.next()){
                    //Travels(int id, String name, String address, String mobileNumber, String mail,String owner_name,int totalBuses)
                    Travels t = new Travels(rs.getInt("id"),rs.getString("name"),rs.getString("address"),rs.getString("mobile_Number"),rs.getString("mail"),rs.getString("owner_name"),rs.getInt("total_buses"));
                    TravelsView.getInstance().functionalityList(t);
                }
            }else if(role==1 && id !=0){
                AdminView.getInstance().functionalityList(new Admin());
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return true;
    }

    public boolean userLogin(String mail,String password){
        try{
            ResultSet rs = dbo.executeQuery(Queries.CHECK_USER.getQuery(),new Object[]{mail,password});
            if(rs.next()){
                return true;
            }
        } catch (SQLException ignored) {}
        return false;
    }

    public boolean isExistingUser(String mail,String password){
        try{
            ResultSet rs = dbo.executeQuery(Queries.EXISTING_USER_CHECK.getQuery(),new Object[]{mail,password});
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
