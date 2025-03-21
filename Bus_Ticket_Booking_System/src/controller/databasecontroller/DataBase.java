package controller.databasecontroller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBase {
    private DataBase(){}
    private static Connection con = null;

    public static Connection getConnection(){
        if(con == null) {
            try {
                Properties prop = new Properties();
                FileInputStream fis = new FileInputStream("/home/zoho/Documents/Basics/Exercise/Bus_Ticket_Booking_System/src/DBProperties.properties");
                prop.load(fis);
                con = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("userName"), prop.getProperty("passWord"));
            }catch(SQLException | IOException e){
                System.out.println("Connection Failed");
            }
        }
        return con;
    }

    public static void close(){
        if(con != null){
            try{
                con.close();
            }catch (SQLException e){
                System.out.println("Connection Close Failed");
            }
        }
    }

}
