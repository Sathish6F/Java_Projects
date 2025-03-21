package controller.passengercontroller;


import controller.databasecontroller.DataBaseOperation;
import controller.databasecontroller.Queries;
import exception.DBUpdationFailedException;
import model.Passanger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PassengerSignUp {
    private static PassengerSignUp ps = null;
    private static final DataBaseOperation dbo = DataBaseOperation.getInstance();
    private PassengerSignUp(){}

    public static PassengerSignUp getInstance(){
        if(ps == null){
            ps = new PassengerSignUp();
        }
        return ps;
    }

    public boolean addNewPassenger(Passanger passanger){
        dbo.startTransaction();
        try{
            int id = updatePassangerInfo(passanger.getName(),passanger.getAge(),passanger.getGender(), passanger.getMobileNumber(), passanger.getAddress());
            if(id == 0){
                throw new DBUpdationFailedException("Updating User Data Failed");
            }
            if(updatePassengerCredentials(id, passanger.getMail(), passanger.getPassWord())){
                return true;
            }else{
                throw new DBUpdationFailedException("Updating User Credentials Failed");
            }
        }catch (DBUpdationFailedException e){
            dbo.rollBack();
            System.out.println(e.getMessage());
        }finally {
            dbo.closeTransaction();
        }
        return false;
    }

    public int updatePassangerInfo(String name,int age,String gender,String mobile_number,String address){
        ResultSet rs = dbo.executeQuery(Queries.UPDATE_PASSENGER_INFO.getQuery(),new Object[]{name,age,gender,mobile_number,address});
        int id = 0;
        try{
            if(rs.next()){
                id = rs.getInt("passenger_id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public boolean updatePassengerCredentials(int id,String mail,String password){
        //user_id | user_role |       mail       |  password
        int r = dbo.executeUpdate(Queries.UPDATE_PASSENGER_CRED.getQuery(),new Object[]{id,mail,password});
        return r != 0;
    }
}
