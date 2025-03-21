package Validation;

import view.MyScanner;

import java.sql.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements Validator{
    private static Scanner sc= MyScanner.getInstance();
    @Override
    public boolean validateEmail(String email) {
        String regex = "^[a-z0-9.]+@[a-z.]+\\.[a-z]{3}$";
        Pattern pattern = Pattern.compile(regex);
        if(email == null){
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public boolean validateMobileNumber(String number) {
        String regex = "^[6-9]\\d{9}$";
        Pattern pattern = Pattern.compile(regex);
        if (number == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    @Override
    public boolean validatePassword(String passWord) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        if (passWord == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(passWord);
        return matcher.matches();
    }
    public String getValidatedPassword(){
        String passWord = sc.next();
        while(!validatePassword(passWord)){
            System.out.println("Invalid Syntax for Password.......\nRe Enter the Password");
            passWord = sc.next();
        }
        return passWord;
    }
    public String getValidatedMobileNumber(){
        String number= sc.next();
        while(!validateMobileNumber(number)){
            System.out.println("Invalid syntax for mobile number.......\nRe Enter the MobileNumber");
            number=sc.next();
        }
        return number;
    }
    public String getValidatedEmail(){
        String email = sc.next();
        while(!validateEmail(email)){
            System.out.println("Invalid syntax for Email.......\nRe Enter the Email");
            email=sc.next();
        }
        return email;
    }

    public String getValidDate() {
        String date = sc.next();
        Date current = new Date(System.currentTimeMillis());
        if(date.equals(current.toString())){
            return date;
        }
        while((Date.valueOf(date).before(current))){
            System.out.println("Enter a Valid date");
            date = sc.next();
            current.setTime(System.currentTimeMillis());
        }
        return date;
    }
}
