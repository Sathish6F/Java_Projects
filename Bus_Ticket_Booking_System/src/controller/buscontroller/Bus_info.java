package controller.buscontroller;

import controller.databasecontroller.DataBaseOperation;
import controller.databasecontroller.Queries;
import model.Bus;
import model.Seat;
import view.MyScanner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Bus_info {
    private static DataBaseOperation dbo = DataBaseOperation.getInstance();
    private static Scanner sc = MyScanner.getInstance();
    private static Bus_info bi = null;
    private Bus_info(){}

    public static Bus_info getInstance(){
        if(bi==null){
            bi = new Bus_info();
        }
        return bi;
    }

    public int getSeatPrice(Bus b, Seat s){
        ResultSet rs = null;
        int price =0;
        int seat_type = (s.getType().equals("Sl"))?1:2;
        try{
            rs = dbo.executeQuery(Queries.GET_SEAT_PRICE.getQuery(),new Object[]{b.getId(),seat_type});
            if(rs.next()){
                price = rs.getInt("price");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return price;
    }

    public String getTravelsName(Bus b){
        ResultSet rs = null;
        String name ="";
        try{
            rs = dbo.executeQuery(Queries.GET_TRAVELS_NAME.getQuery(),new Object[]{b.getId()});
            if(rs.next()){
                name = rs.getString("name");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return name;
    }

    public int getTravelsId(Bus b){
        int id = 0;
        ResultSet rs = null;
        try{
            rs = dbo.executeQuery(Queries.GET_TRAVELS_ID.getQuery(),new Object[]{b.getId()});
            if(rs.next()){
                id = rs.getInt("travels_id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }
}
