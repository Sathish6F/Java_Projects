package view;

import model.Admin;
import model.User;

import java.util.Scanner;

public class AdminView extends UserView{

    private static Scanner sc = MyScanner.getInstance();
    private static AdminView av = null;
    private AdminView(){}

    public static AdminView getInstance() {
        if(av == null){
            av = new AdminView();
        }
        return av;
    }

    @Override
    public void functionalityList(User user) {
        System.out.println("------------------------------------------------------------------------");
        System.out.println("                          Welcome " + user.getUserType());
        System.out.println();
        System.out.println("                     1.Add New Travels");
        System.out.println("                     2.View All Travels");
        System.out.println("                     3.Log Out");
        LogOut.logOut();
    }
}
