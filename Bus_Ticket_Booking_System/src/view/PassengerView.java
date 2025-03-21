package view;

import Validation.UserValidator;
import com.sun.xml.internal.ws.addressing.WsaActionUtil;
import controller.buscontroller.Bus_info;
import controller.buscontroller.InitializeBus;
import controller.passengercontroller.BookTicket;
import controller.passengercontroller.CancelTicket;
import controller.passengercontroller.Edit_PassengerInfo;
import controller.passengercontroller.ViewTicket;
import model.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class PassengerView extends UserView {

    private static PassengerView pv = null;
    private static final Scanner sc = MyScanner.getInstance();
    private static final CancelTicket ct = CancelTicket.getInstance();
    private static final BookTicket bk = BookTicket.getInstance();
    private static final ViewTicket vt = ViewTicket.getInstance();
    private static final Edit_PassengerInfo editor = Edit_PassengerInfo.getInstance();
    private PassengerView() {
    }

    private Passanger passanger;
    UserValidator validator = new UserValidator();
    public static PassengerView getInstance() {
        if (pv == null) {
            pv = new PassengerView();
        }
        return pv;
    }

    private String from;
    private String to;

    @Override
    public void functionalityList(User user) {

        this.passanger = (Passanger) user;
        System.out.println("------------------------------------------------------------------------");
        System.out.println("                          Welcome " + user.getName());
        System.out.println();
        System.out.println("                     1.Book Ticket");
        System.out.println("                     2.View Ticket");
        System.out.println("                     3.Cancel Ticket");
        System.out.println("                     4.Edit Details");
        System.out.println("                     5.Log out");
        int choice = 0;
        try {
            choice = sc.nextInt();
            while (choice <= 0 || choice > 5) {
                System.out.println("Please enter valid choice");
                choice = sc.nextInt();
            }
        }catch (InputMismatchException e){
            System.out.println("Enter Valid Inputs");
            functionalityList(user);
        }
        switch (choice) {
            case 1:
                bookTicket((Passanger) user);
                break;
            case 2:
                viewTicket();
                functionalityList(user);
                break;
            case 3:
                cancelTicket();
                break;
            case 4:
                editDetails();
                break;
            case 5:
                LogOut.logOut();
        }
    }

    public void bookTicket(Passanger p) {
        try {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("From:");
            String from = sc.next();
            System.out.println("To:");
            String to = sc.next();
            this.from = from;
            this.to = to;
            System.out.println("Date[yyyy-mm-dd]:");
            String date = new UserValidator().getValidDate();
            BookTicket.getInstance().bookTicket(from, to,date);

        } catch (InputMismatchException e){
            sc.next();
            System.out.println("Enter the Valid Inputs");
            bookTicket(p);
        }
        catch (Exception e) {
            sc.next();
            System.out.println("Please enter valid data");
            functionalityList(p);
        }
    }

    public void cancelTicket(){
        System.out.println("Enter the Ticket ID to Cancel:");
        int ticketId=0;
        try {
            ticketId = sc.nextInt();
        }catch (InputMismatchException e){
            System.out.println("Please Enter Valid Inputs");
            cancelTicket();
        }
        //1.check is valid ticket
        while(ct.isValidTicket(ticketId)){
            System.out.println("Please enter a valid Ticket ID");
            ticketId = sc.nextInt();
        }
        int busId = ct.getBusId(ticketId);
        List<Seat> seatList = ct.getSeatsToCancel(ticketId);
        int sleeper = 0;
        int semiSleeper = 0;
        for(Seat s : seatList){
            if(s.getType().startsWith("Sl")){
                sleeper++;
            }else{
                semiSleeper++;
            }
        }
        if(ct.updateDB(ticketId,busId,seatList,sleeper,semiSleeper, passanger.getId())){
            System.out.println("Cancelled Tickets : "+seatList);
        }
    }

    public void viewTicket(){
        upComingTrips(passanger.getId());
        System.out.println("Enter the Ticket ID to View:");
        int ticketId=0;
        try {
            ticketId = sc.nextInt();
        }catch (InputMismatchException e){
            sc.next();
            System.out.println("Please Enter Valid Inputs");
            cancelTicket();
        }
        //1.check is valid ticket
        while(ct.isValidTicket(ticketId)){
            System.out.println("Please enter a valid Ticket ID");
            ticketId = sc.nextInt();
        }
        if(!vt.isActiveTicket(ticketId)){
            System.out.println("Enter Ticket Id is cancelled Ticket");
            return;
        }
        //Ticket->ticket id,boarding point,dropping point,passengers list,price
        Bus bus = vt.getBusInfo(ticketId);
        Ticket ticket = vt.getTicketInfo(ticketId);
        printTicket(ticket,bus);
    }
    public void upComingTrips(int passangerId){
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Upcoming Trips");
        ResultSet rs = vt.getUpcomingTrips(passangerId);
        //id |price|booked_for |   bus_no   |   travels_name   | from_city | to_city
        System.out.printf("%-20s%-20s%-20s%-20s%-25s%-25s%-20s%n","Ticket Id","Date","From","To","Travels Name","Bus Number","Price");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
        try{
            while(rs.next()){
                System.out.printf("%-20s%-20s%-20s%-20s%-25s%-25s%-20s%n",rs.getInt("id"),rs.getDate("booked_for"),rs.getString("from_city"),rs.getString("to_city"),rs.getString("travels_name"),rs.getString("bus_no"),rs.getInt("price"));
                System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("No Upcoming Trips Found");
        }

    }
    public void editDetails(){
        System.out.println("------------------------------------------------------------------------");
        System.out.println("1.Edit User Name\n2.Edit Password");
        System.out.println("3.Edit Mobile Number\n4.Edit Address");
        int choice = sc.nextInt();
        while(choice <= 0 || choice >4){
            System.out.println("Please Enter valid choice");
            choice = sc.nextInt();
        }
        switch (choice){
            case 1:
                System.out.println("Enter New User Name:");
                String newName;
                try{
                    newName = sc.next();
                }catch (InputMismatchException e){
                    System.out.println("Please Enter valid User name");
                    newName = sc.next();
                }
                if(editor.editUserName(passanger.getId(),newName)){
                    System.out.println("User Name Updated Successfully !");

                }else{
                    System.out.println("User Name Updation Failed");
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
                if(editor.editPassword(passanger.getId(),newPassword)){
                    System.out.println("Password Updated SuccessFully");
                }else{
                    System.out.println("Password Updation Failed");
                }
                break;
            case 3:
                System.out.println("Enter New Mobile Number :");
                String mobileNumber = validator.getValidatedMobileNumber();
                if(editor.editMobileNumber(passanger.getId(),mobileNumber)){
                    System.out.println("Mobile Number Updated Successfully !");
                }else{
                    System.out.println("Mobile Number Updation Failed");
                }
                break;
            case 4:
                System.out.println("Enter City Name :");
                String newAddress = sc.next();
                if(editor.editAddress(passanger.getId(),newAddress)){
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
            editDetails();
        }else{
            functionalityList(passanger);
        }
    }

    public void listOfBuses(ResultSet rs,String date) {
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Available Buses");
        System.out.println();
        System.out.printf("%-20s%-20s%-20s%-20s%-25s%-25s%-20s%n", "Bus ID", "Bus Number", "Travels Name", "Available Sleeper", "Available Semi Sleeper","Sleeper Seat Price","Semi Sleeper Price");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
        boolean flag = false;
        try {
            while (rs.next()) {
                //id |  bus_no   |       name       | avl_sleeper | avl_semisleeper | sleeper_price | semisleeper_price
                System.out.printf("%-20s%-20s%-20s%-20s%-25s%-25s%-20s%n", rs.getInt("id"), rs.getString("bus_no"), rs.getString("name"), rs.getInt("avl_sleeper"), rs.getInt("avl_semisleeper"),rs.getInt("sleeper_price"),rs.getInt("semisleeper_price"));
                System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
                flag = true;
            }
            if (flag) {
                System.out.println("------------------------------------------------------------------------");
                System.out.println("Choose the Bus Id You want:");
                int bus_id = sc.nextInt();
                Bus b = InitializeBus.getInstance().initializeBus(bus_id,date);
                displayBusSeats(b,new Ticket(),date);
            } else {
                System.out.println("No Buses available");
                functionalityList(passanger);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayBusSeats(Bus b,Ticket t,String date) {
        String[][] sleeper = b.getSleeperSeats();
        String[][] semiSleeper = b.getSemiSleeperSeats();
        setseats(sleeper);
        setseats(semiSleeper);
        List<Seat> seatList = b.getSeats();

        for (Seat s : seatList) {
            if (s.getType().equals("Ss")) {
                String seat;
                if (s.getNumber() < 10) {
                    seat = s.getType() + "0" + s.getNumber();
                } else {
                    seat = s.getType() + s.getNumber();
                }
                Point p = null;
                if (Bus.seatMap.containsKey(seat)) {
                    p = Bus.seatMap.get(seat);
                    semiSleeper[p.getR()][p.getC()] = (s.getBookedGender().equals("M")) ? "M" : (s.getBookedGender().equals("F")) ? "F" : "";
                }
            } else if (s.getType().equals("Sl")) {
                String seat;
                if (s.getNumber() < 10) {
                    seat = s.getType() + "0" + s.getNumber();
                } else {
                    seat = s.getType() + s.getNumber();
                }
                Point p = null;
                if (Bus.seatMap.containsKey(seat)) {
                    p = Bus.seatMap.get(seat);
                    sleeper[p.getR()][p.getC()] = (s.getBookedGender().equals("M")) ? "M" : (s.getBookedGender().equals("F")) ? "F": "";
                }
            }
        }
        b.setSemiSleeperSeats(semiSleeper);
        b.setSleeperSeats(sleeper);
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Sleeper Deck");
        System.out.println("------------------------------------------------------------------------");
        printSleeper(sleeper);
        System.out.println("------------------------------------------------------------------------");
        System.out.println();
        System.out.println("Semi Sleeper Deck");
        System.out.println("------------------------------------------------------------------------");
        printSemiSleeper(semiSleeper);
        System.out.println("------------------------------------------------------------------------");
        System.out.println("[M] -> Booked By Male Passenger");
        System.out.println("[F] -> Booked By Female Passenger");
        System.out.println();
        getBookingDetails(b,t,date);
    }

    public void printSemiSleeper(String[][] grid) {
        int gap =grid.length - 2;
        for (int i = 0; i < grid.length; i++) {
            if (i == gap) {
                System.out.println();
            }
            for (int j = 0; j < grid[0].length; j++) {
                System.out.printf("%s%s%-10s","\uD83D\uDCBA", grid[i][j],"L");
            }
            System.out.println();
        }
    }
    public void printSleeper(String[][] grid) {
        int gap = grid.length - 1;
        for (int i = 0; i < grid.length; i++) {
            if (i == gap) {
                System.out.println();
            }
            for (int j = 0; j < grid[0].length; j++) {
                System.out.printf("%s%s%-10s","\uD83D\uDECF", grid[i][j],"U");
            }
            System.out.println();
        }
    }

    public void setseats(String[][] seat) {
        int num = 1;
        for (int i = 0; i < seat[0].length; i++) {
            for (int j = 0; j < seat.length; j++) {
                seat[j][i] = "" + num;
                num++;
            }
        }
    }

    public void getBookingDetails(Bus b,Ticket t,String date) {
        System.out.println("Choose The Seat Type");
        System.out.println("1.Sleeper Seat\t2.Semi Sleeper Seat");
        Seat s = null;
        int ch = sc.nextInt();
        while (ch < 0 || ch > 2) {
            System.out.println("Enter a valid Choice");
            ch = sc.nextInt();
        }
        switch (ch) {
            case 1:
                System.out.println("Choose Seat Number:");
                int num = sc.nextInt();
                while(num<=0 || num>18){
                    System.out.println("Please choose a valid seat number");
                    num = sc.nextInt();
                }
                s = new SleeperSeat();
                s.setNumber(num);
                s.setType("Sl");
                break;
            case 2:
                System.out.println("Choose Seat Number:");
                int no = sc.nextInt();
                while(no<=0 || no>30){
                    System.out.println("Please choose a valid seat number");
                    no = sc.nextInt();
                }
                s = new SemiSleeperSeat();
                s.setNumber(no);
                s.setType("Ss");
                break;
        }
        System.out.println("Enter Passenger Gender:[M-Male,F-Female]");
        String gender = sc.next();
        while(gender.length()>1 || (gender.charAt(0)!='M' && gender.charAt(0)!='F')){
            System.out.println("Please Enter Valid Gender Choice[M-Male,F-Female]");
            gender = sc.next();
        }
        s.setBookedGender(gender);
        if (isBookable(b, s)) {
            System.out.println("Available");
            getPassengerDetails(b, s,t,date);
        } else {
            System.out.println("Not Available....Choose another seat");
            System.out.println("1.Choose another seat\t2.Exit");
            int choice = sc.nextInt();
            while (choice < 0 || choice > 2) {
                System.out.println("Enter a valid Choice");
                choice = sc.nextInt();
            }
            switch (choice) {
                case 1:
                    getBookingDetails(b,t,date);
                    break;
                case 2:
                    functionalityList(passanger);
            }
        }

    }

    public boolean isBookable(Bus bus, Seat seat) {
        if (bus.getSeats().contains(seat))
            return false;
        if (seat instanceof SleeperSeat) {
            return isValidSleeperSeat(bus, seat);
        }
        if (seat instanceof SemiSleeperSeat) {
            return isValidSemisleeperSeat(bus, seat);
        }
        return true;
    }

    public boolean isValidSleeperSeat(Bus b, Seat s) {
        String number = "" + s.getNumber();
        if (s.getNumber() % 3 == 0) return true;
        else {
            String[][] pos = b.getSleeperSeats();
            l1:
            for (int i = 0; i < pos.length; i++) {
                for (int j = 0; j < pos[0].length; j++) {
                    if (pos[i][j].equals(number)) {
                        if (i == 1 && (pos[i - 1][j].contains("F") || pos[i - 1][j].contains("M"))) {
                            if (pos[i - 1][j].contains("F") && s.getBookedGender().equals("F")) {
                                return true;
                            } else if (pos[i - 1][j].contains("M") && s.getBookedGender().equals("M")) {
                                return true;
                            }
                        } else if (i == 1 && (!(pos[i - 1][j].contains("F") || pos[i - 1][j].contains("M")))) {
                            return true;
                        }
                        if (i == 0 && (pos[i + 1][j].contains("F") || pos[i + 1][j].contains("M"))) {
                            if (pos[i + 1][j].contains("F") && s.getBookedGender().equals("F")) {
                                return true;
                            } else if (pos[i + 1][j].contains("M") && s.getBookedGender().equals("M")) {
                                return true;
                            }
                        } else if (i == 0 && (!(pos[i + 1][j].contains("F") || pos[i + 1][j].contains("M")))) {
                            return true;
                        } else {
                            break l1;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isValidSemisleeperSeat(Bus b, Seat s) {
        int r = -1;
        int c = -1;
        String number = "" + s.getNumber();
        String[][] pos = b.getSemiSleeperSeats();
        l1:
        for (int i = 0; i < pos.length; i++) {
            for (int j = 0; j < pos[0].length; j++) {
                if (pos[i][j].contains(number)) {
                    r = i;
                    c = j;
                    break l1;
                }
            }
        }
        if (r == -1) {
            return false;
        }
        switch (r) {
            case 0:
            case 3:
                if (pos[r + 1][c].contains("M") && s.getBookedGender().equals("M")) {
                    return true;
                } else if (pos[r + 1][c].contains("F") && s.getBookedGender().equals("F")) {
                    return true;
                } else if (!(pos[r + 1][c].contains("M") || pos[r + 1][c].equals("F"))) {
                    return true;
                }
                break;
            case 1:
                if (pos[r + 1][c].contains("M") && s.getBookedGender().equals("M")) {
                    return true;
                } else if (pos[r + 1][c].contains("F") && s.getBookedGender().equals("F")) {
                    return true;
                } else if (pos[r - 1][c].contains("M") && s.getBookedGender().equals("M")) {
                    return true;
                } else if (pos[r - 1][c].contains("F") && s.getBookedGender().equals("F")) {
                    return true;
                } else if (!(pos[r + 1][c].contains("M") || pos[r + 1][c].equals("F"))) {
                    return true;
                } else if (!(pos[r - 1][c].contains("M") || pos[r - 1][c].equals("F"))) {
                    return true;
                }
                break;
            case 2:
            case 4:
                if (pos[r - 1][c].contains("M") && s.getBookedGender().equals("M")) {
                    return true;
                } else if (pos[r - 1][c].contains("F") && s.getBookedGender().equals("F")) {
                    return true;
                } else if (!(pos[r - 1][c].contains("M") || pos[r - 1][c].equals("F"))) {
                    return true;
                }
                break;

        }
        return false;
    }

    public void getPassengerDetails(Bus b, Seat s,Ticket t,String date) {

        int from = bk.getCityId(this.from);
        int to = bk.getCityId(this.to);
        b.setFrom(this.from);
        b.setTo(this.to);

        System.out.println("Enter Passenger Name:");
        String pName = sc.next();
        System.out.println("Enter Passenger Age:");
        int pAge = sc.nextInt();
        while (pAge <= 0 || pAge > 70) {
            System.out.println("Please Enter a Age between 1-70");
            pAge = sc.nextInt();
        }
        int boardingPoint = 0;
        int droppingPoint = 0;
        try {
            System.out.println("Choose Boarding Point ID:");
            System.out.println("--------------------------------");
            System.out.printf("%-10s%-10s%n", "ID", "Stop Name");
            System.out.println("--------------------------------");
            ResultSet rs = bk.getStoppings(from);
            Set<Integer> stoppingsSet = new HashSet<>();
            while (rs.next()) {
                stoppingsSet.add(rs.getInt("id"));
                System.out.printf("%-10s%-10s%n", rs.getInt("id"), rs.getString("name"));
                System.out.println("--------------------------------");
            }
            boardingPoint = sc.nextInt();
            while(!stoppingsSet.contains(boardingPoint)){
                System.out.println("Please Choose a Valid Boarding Point Id From Above Table");
                boardingPoint = sc.nextInt();
            }
            stoppingsSet.clear();
            rs = bk.getStoppings(to);
            System.out.println("Choose Dropping Point ID:");
            System.out.println("--------------------------------");
            System.out.printf("%-10s%-10s%n", "ID", "Stop Name");
            System.out.println("--------------------------------");
            while (rs.next()) {
                stoppingsSet.add(rs.getInt("id"));
                System.out.printf("%-10s%-10s%n", rs.getInt("id"), rs.getString("name"));
                System.out.println("--------------------------------");
            }
            droppingPoint = sc.nextInt();
            while(!stoppingsSet.contains(droppingPoint)){
                System.out.println("Please Choose a Valid Dropping Point Id From Above Table");
                droppingPoint = sc.nextInt();
            }
            rs = bk.getStop(boardingPoint);
            String boarding = "";
            String dropping = "";
            if (rs.next()) {
                boarding = rs.getString("name");
            }
            rs = bk.getStop(droppingPoint);
            if (rs.next()) {
                dropping = rs.getString("name");
            }
            int id = 0;
            rs = bk.getLastTicketId();
            if (rs.next())
                id = rs.getInt("id");
            if (isPaymentCompletes(b, s)) {
                System.out.println("Payment Successfully Done");
                System.out.println("------------------------------------------------------------------------");
                Timestamp today = new Timestamp(System.currentTimeMillis());
                t.setDate(today);
                Date datefor = Date.valueOf(date);
                t.setBookedfor(datefor);
                t.setTicket_id(++id);
                t.setBoarding_point(boarding);
                t.setDropping_point(dropping);
                int price = t.getPrice()+Bus_info.getInstance().getSeatPrice(b, s);
                t.setPrice(price);
                String seatnum = s.getType()+((s.getNumber()<=9)?"0":"")+s.getNumber();
                List<Passanger> l = t.getPassengers();
                l.add(new Passanger(pName, pAge,s.getBookedGender(),seatnum));
                t.setPassengers(l);
                passanger.setTicket(t);
                s.setIs_booked(true);
                System.out.println("1.Book Ticket\t2.Exit");
                int c = sc.nextInt();
                while(c<=0 || c>2){
                    System.out.println("Please enter valid choice");
                    c = sc.nextInt();
                }
                switch (c){
                    case 1:
                        getBookingDetails(b,t,date);
                        break;
                    case 2:
                        //update all info into db;
                        if(bk.updateDB(b,t, passanger,date)){
                            printTicket(t, b);
                        }else{
                            System.out.println("Ticket Booking Failed");
                            System.out.println("1.Retry\t2.Exit");
                            int ch = sc.nextInt();
                            if(ch==1){
                                getBookingDetails(b,t,date);
                            }else{
                                LogOut.logOut();
                            }
                        }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isPaymentCompletes(Bus b, Seat s) {
        int seat_price = Bus_info.getInstance().getSeatPrice(b, s);
        System.out.println("------------------------------------------------------------------------");
        System.out.println("The Seat Price is:" + seat_price);
        System.out.println("Enter the amount:");
        int price = sc.nextInt();
        while (price <= 0 || price != seat_price) {
            System.out.println("Please Enter a Valid Amount:");
            price = sc.nextInt();
        }
        return true;
    }


    public void printTicket(Ticket ticket, Bus bus) {
        System.out.println("Your Ticket");
        System.out.println("----------------------------------------------------------");
        System.out.printf("%s%s%-24s%s%s%n", "\uD834\uDD03\uD834\uDD03\uD834\uDD02\uD834\uDD02\uD834\uDD00\uD834\uDD01\uD834\uDD03\uD834\uDD02\uD834\uDD02\uD834\uDD03", "ID:", "" + ticket.getTicket_id(), "Date:", ticket.getDate().toString());
        System.out.printf("Travels Name :%s%n", Bus_info.getInstance().getTravelsName(bus));
        System.out.printf("Bus Number   :%s%n", bus.getBus_no());
        System.out.println("----------------------------------------------------------");
        System.out.printf("%-23s%24s%n", "Arrival", "Dropping");
        System.out.printf("%-39s%s%n",  bus.getFrom(), bus.getTo());
        System.out.printf("%-29s%16s%n",ticket.getBoarding_point(), ticket.getDropping_point());
        System.out.printf("%s%-31s%s%s%n", bus.getTrip().getDeparture(),"PM", bus.getTrip().getArrival(),"AM");
        System.out.println("----------------------------------------------------------");
        System.out.printf("%-10s%-20s%-15s%-10s%n","Seat","Name","Age","Gender");
        System.out.println("----------------------------------------------------------");
        for(Passanger p:ticket.getPassengers()){
            System.out.printf("%-10s%-20s%-15s%-10s%n",p.getSeatNumber(),p.getName(),p.getAge(),p.getGender());
            System.out.println("----------------------------------------------------------");
        }
        System.out.printf("%-45s%-5s%s%n","Amount",ticket.getPrice(),"✅️");
        System.out.println("----------------------------------------------------------");
        functionalityList(passanger);
    }
}
