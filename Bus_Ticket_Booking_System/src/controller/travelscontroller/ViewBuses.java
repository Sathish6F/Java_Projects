package controller.travelscontroller;

import controller.databasecontroller.DataBaseOperation;
import controller.databasecontroller.Queries;

import java.sql.ResultSet;


public class ViewBuses {
    private static final DataBaseOperation dbo = DataBaseOperation.getInstance();
    private static ViewBuses vb = null;
    private ViewBuses(){}

    public static ViewBuses getInstance(){
        if(vb == null){
            vb = new ViewBuses();
        }
        return  vb;
    }

    public ResultSet viewAllBuses(int travelsId){
       return dbo.executeQuery(Queries.VIEW_ALL_BUSES.getQuery(), new Object[]{travelsId});
    }

    public ResultSet getBuses(int travelsId){
        return dbo.executeQuery(Queries.GET_ALL_BUSES.getQuery(), new Object[]{travelsId});
    }
}
