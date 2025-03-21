package view;

import Validation.UserValidator;
import controller.travelscontroller.AddBus;
import controller.travelscontroller.Edit_PriceInfo;
import controller.travelscontroller.Edit_TravelsInfo;
import controller.travelscontroller.ViewBuses;
import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class TravelsView extends UserView{
    private static Scanner sc = MyScanner.getInstance();
    private static TravelsView tv = null;
    private static final AddBus ab = AddBus.getInstance();
    private static final ViewBuses vb = ViewBuses.getInstance();
    private static UserValidator validator = new UserValidator();
    private static Edit_TravelsInfo editor = Edit_TravelsInfo.getInstance();
    private TravelsView(){}

    public static TravelsView getInstance() {
        if(tv == null){
            tv = new TravelsView();
        }
        return tv;
    }
    private Travels travels = null;
    @Override
    public void functionalityList(User user) {
        this.travels = (Travels) user;
        System.out.println("------------------------------------------------------------------------");
        System.out.println("                          Welcome " + user.getName());
        System.out.println();
        System.out.println("                     1.Add Buses");
        System.out.println("                     2.View Buses");
        System.out.println("                     3.Edit Travels Details");
        System.out.println("                     4.Edit Bus Details");
        System.out.println("                     5.Log out");
        int choice = sc.nextInt();
        while(choice <=0 || choice >5){
            System.out.println("Please Enter a Valid choice:");
            choice = sc.nextInt();
        }
        switch (choice){
            case 1:
                addBus();
                break;
            case 2:
                viewBuses(this.travels.getId());
                break;
            case 3:
                editTravelsDetails();
                break;
            case 4:
                editBusDetails();
                break;
            case 5:
                LogOut.logOut();
        }
        System.out.println("1.Continue\t2.Exit");
        int ch = sc.nextInt();
        while(ch<=0 || ch>2){
            System.out.println("Please enter valid choice");
            ch = sc.nextInt();
        }
        if(ch==1){
            functionalityList(travels);
        }else{
            LogOut.logOut();
        }
    }

    public void addBus(){
        //To add a bus first get bus no,total seats,from and to cities,get employee details, if the cities not available
        //then add to the cities ,fix the price ranges for seat types
        System.out.println("Enter Bus Number :");
        System.out.println("The Format Must be TNXXGXXXX  X - 0 to 9");
        String busNo = sc.next();
        System.out.println("Enter Total number of seats:");
        int totalSeats = sc.nextInt();
        System.out.println("Enter From City :");
        String from = sc.next();
        System.out.println("Enter To City :");
        String to = sc.next();
        System.out.println("Enter Distance:");
        int distance = sc.nextInt();
        while(distance <=60 || distance >1000){
            System.out.println("Please Enter Valid Distance");
            distance = sc.nextInt();
        }
        System.out.println("Enter Driver Name:");
        String driver = sc.next();
        System.out.println("Enter Driver Mobile Number:");
        String driverMobile = new UserValidator().getValidatedMobileNumber();

        System.out.println("Enter Helper Name:");
        String helper = sc.next();
        System.out.println("Enter Helper Mobile Number:");
        String helperMobile = new UserValidator().getValidatedMobileNumber();

        System.out.println("Enter Price for Sleeper Seat:");
        int sleeperPrice = sc.nextInt();
        while(sleeperPrice<=0 || sleeperPrice>=2500){
            System.out.println("Please Enter a Valid Price");
            sleeperPrice = sc.nextInt();
        }
        System.out.println("Enter Price for Semi Sleeper Seat:");
        int semiSleeperPrice = sc.nextInt();
        while(semiSleeperPrice<=0 || semiSleeperPrice>=2500){
            System.out.println("Please Enter a Valid Price");
            semiSleeperPrice = sc.nextInt();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime;
        System.out.println("Enter Arrival Time:[HH:MM:SS]");
        String arrival = sc.next();
        localTime = LocalTime.parse(arrival, formatter);
        Time arrivalTime = Time.valueOf(localTime);
        System.out.println("Enter Dropping Time:[HH:MM:SS]");
        String dropping = sc.next();
        localTime = LocalTime.parse(dropping,formatter);
        Time droppingTime = Time.valueOf(localTime);
        Bus newBus = new Bus(busNo,from.toLowerCase(),to.toLowerCase(),new Employee(driver,driverMobile),new Employee(helper,helperMobile),totalSeats);
        newBus.setTrip(new Trip(arrivalTime,droppingTime));
        if(ab.addBusToDB(travels.getId(),newBus,sleeperPrice,semiSleeperPrice,distance)){
            System.out.println("Bus Added Successfully");
            functionalityList(travels);
        }else{
            System.out.println("Adding Bus service Failed");
            System.out.println("1.Retry\t2.Exit");
            int ch = sc.nextInt();
            while(ch<=0 || ch>2){
                System.out.println("Please Enter Valid Choice");
                ch = sc.nextInt();
            }
            if(ch == 1){
                addBus();
            }else{
                functionalityList(this.travels);
            }
        }
    }

    public void viewBuses(int travelsId){
        //get all the buses runned by this travels
        System.out.println("------------------------------------------------------------------------");
        ResultSet rs = null;
        rs = vb.viewAllBuses(travelsId);
        //id |   bus_no   |    date    | available_seats | total_seats | from_city |  to_city
        System.out.printf("%-20s%-20s%-20s%-20s%-25s%-25s%-20s%n","Bus Number","Bus Id","Date","Available Seats","Total Seats","From","To");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
        try{
            while (rs.next()){
                System.out.printf("%-20s%-20s%-20s%-20s%-25s%-25s%-20s%n",rs.getString("bus_no"),rs.getInt("id"),rs.getDate("date"),rs.getInt("available_seats"),rs.getInt("total_seats"),rs.getString("from_city"),rs.getString("to_city"));
                System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void editTravelsDetails(){
        System.out.println("------------------------------------------------------------------------");
        System.out.println("1.Edit Travels Name\n2.Edit Password");
        System.out.println("3.Edit Mobile Number\n4.Edit Address");
        int choice = sc.nextInt();
        while(choice <= 0 || choice >4){
            System.out.println("Please Enter valid choice");
            choice = sc.nextInt();
        }
        switch (choice){
            case 1:
                System.out.println("Enter New Travels Name:");
                String newName;
                try{
                    newName = sc.next();
                }catch (InputMismatchException e){
                    System.out.println("Please Enter valid User name");
                    newName = sc.next();
                }
                if(editor.editUserName(travels.getId(),newName)){
                    System.out.println("Travels Name Updated Successfully !");

                }else{
                    System.out.println("Travels Name Updation Failed");
                }
                break;
            case 2:
                System.out.println("Enter New Password:");
                String newPassword = validator.getValidatedPassword();
                System.out.println("Re Enter The Password:");
                String dupPassword = validator.getValidatedPassword();
                while(!newPassword.equals(dupPassword)){
                    System.out.println("Re Enter The Correct Password");
                    dupPassword = validator.getValidatedPassword();
                }
                if(editor.editPassword(travels.getId(),newPassword)){
                    System.out.println("Password Updated SuccessFully");
                }else{
                    System.out.println("Password Updation Failed");
                }
                break;
            case 3:
                System.out.println("Enter New Mobile Number :");
                String mobileNumber = validator.getValidatedMobileNumber();
                if(editor.editMobileNumber(travels.getId(),mobileNumber)){
                    System.out.println("Mobile Number Updated Successfully !");
                }else{
                    System.out.println("Mobile Number Updation Failed");
                }
                break;
            case 4:
                System.out.println("Enter City Name :");
                String newAddress = sc.next();
                if(editor.editAddress(travels.getId(),newAddress)){
                    System.out.println("Address Updated Successfully !");
                }else{
                    System.out.println("Address Updation Failed");
                }
                break;
        }
        System.out.println("1.Continue\t2.Main Menu");
        int ch = sc.nextInt();
        while(ch<=0 || ch>2){
            System.out.println("Please enter valid choice");
            ch = sc.nextInt();
        }
        if(ch == 1){
            editTravelsDetails();
        }else{
            functionalityList(travels);
        }
    }

    public void editBusDetails(){
        Set<Integer> busIdSet = new HashSet<>();
        ResultSet rs = vb.getBuses(travels.getId());
        //bus_no   | bus_id | driver_name | helper_name | from_city |  to_city
        System.out.println("------------------------------------------------------------------------");
        try{
            System.out.printf("%-20s%-20s%-20s%-20s%-25s%-25s%n","Bus Number","Bus Id","Driver Name","Helper Name","From","To");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------");
            while(rs.next()){
                busIdSet.add(rs.getInt("bus_id"));
                System.out.printf("%-20s%-20s%-20s%-20s%-25s%-25s%n",rs.getString("bus_no"),rs.getInt("bus_id"),rs.getString("driver_name"),rs.getString("helper_name"),rs.getString("from_city"),rs.getString("to_city"));
                System.out.println("-----------------------------------------------------------------------------------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error in Getting Bus details");
            return;
        }
        System.out.println("Choose The Bus ID To Edit:");
        int busId = sc.nextInt();
        while(!busIdSet.contains(busId)){
            System.out.println("No Such Bus Id found");
            System.out.println("Please Choose The Valid Bus Id From Above Table");
            busId = sc.nextInt();
        }
        System.out.println("1.Update Sleeper Seat Price\n2.Update Semi Sleeper Seat Price");
        int choice = sc.nextInt();
        while(choice<=0 || choice>2){
            System.out.println("Please Enter Valid Choice");
            choice = sc.nextInt();
        }

        System.out.println("Enter The New Price :");
        int newPrice = sc.nextInt();
        while(newPrice<=100 || newPrice>2000){
            System.out.println("Enter The Price Greater than 100 and Less than 2000");
            newPrice = sc.nextInt();
        }
        if(Edit_PriceInfo.getInstance().updatePriceInfo(busId,choice,newPrice)){
            System.out.println("Price Updated Successfully !");
        }else{
            System.out.println("Price Updation Failed");
        }
    }

}
