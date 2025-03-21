package view;


import controller.databasecontroller.DataBase;

import java.util.Scanner;

public class LogOut {
    private static Scanner sc = MyScanner.getInstance();
    public static void logOut(){
        System.out.println("1.Continue\t2.Exit");
        if(sc.nextInt()==1){
            new LogIn().login();
        }else{
            DataBase.close();
            sc.close();
            System.out.println("Thank You");
            System.exit(0);
        }
    }
}
