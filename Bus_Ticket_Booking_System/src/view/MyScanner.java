package view;

import java.util.Scanner;

public class MyScanner {
    private static Scanner sc=null;
    private MyScanner(){}
    public static Scanner getInstance(){
        if(sc==null){
            sc=new Scanner(System.in);
        }
        return sc;
    }
    public static void closeScanner(){
        if(sc != null){
            sc.close();
        }
    }
}
