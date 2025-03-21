package controller.databasecontroller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseOperation {
    private static DataBaseOperation dbo = null;
    private static Connection con = DataBase.getConnection();
    private DataBaseOperation(){}

    public static DataBaseOperation getInstance(){
        if(dbo == null){
            dbo = new DataBaseOperation();
        }
        return dbo;
    }

    public int executeUpdate(String query){
        int row = 0;
        try(PreparedStatement pst = con.prepareStatement(query)){
            row = pst.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return row;
    }

    public int executeUpdate(String query,Object[] values){
        int row = 0;
        try (PreparedStatement pst = con.prepareStatement(query)){
            for(int i = 1;i <= values.length; i++){
                pst.setObject(i,values[i-1]);
            }
            row = pst.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return row;
    }

    public ResultSet executeQuery(String query,Object[] values){
        ResultSet rs = null;
        try{
            PreparedStatement pst = con.prepareStatement(query);
            for(int i=0;i<values.length;i++){
                pst.setObject(i+1 , values[i]);
            }
            rs = pst.executeQuery();
        }catch (SQLException e ){System.out.println(e.getMessage());}
        return rs;
    }

    public void startTransaction(){
        try {
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Can't Begin Transaction");
        }
    }

    public void rollBack(){
        try {
            con.rollback();
        } catch (SQLException e) {
            System.out.println("Roll Back Failed");
        }
    }

    public void closeTransaction(){
        try{
            con.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Transaction Closing Failed");
        }
    }

}
